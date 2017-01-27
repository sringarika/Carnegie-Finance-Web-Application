package cfs.model;

import java.util.Arrays;
import java.util.Comparator;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import cfs.databean.Customer;
import cfs.databean.Position;
import cfs.databean.Transactions;

public class TransactionDAO extends GenericDAO<Transactions> {
    public TransactionDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(Transactions.class, tableName, cp);
    }

    // show the transaction history for a given customer ID
    public Transactions[] showHistory(int CustomerId) throws RollbackException {
        Transactions[] history = match(MatchArg.equals("customerId", CustomerId));
        Comparator<Transactions> tranComparator = new Comparator<Transactions>() {
            public int compare(Transactions tran1, Transactions tran2) {
                return tran1.getTransactionId() - tran2.getTransactionId();
            }
        };
        Arrays.sort(history, tranComparator);
        return history;
    }
    
    // TODO fix bugs for the initial conditions and buy and sell fund
    public void processTransaction(FundPriceHistoryDAO fundPriceHistoryDAO,
            CustomerDAO customerDAO, CustomerPositionDAO customerPositionDAO) throws RollbackException {
        Comparator<Transactions> tranComparator = new Comparator<Transactions>() {
            public int compare(Transactions tran1, Transactions tran2) {
                return tran1.getTransactionId() - tran2.getTransactionId();
            }
        };
        System.out.println("This method got called");
        Transactions[] maxTransaction = match(MatchArg.max("transactionId"));
        if (maxTransaction.length == 0) {
            return;
        }
        int maxId = maxTransaction[0].getTransactionId();
        System.out.println(maxId);
        String closingDate = fundPriceHistoryDAO.getLastClosingDate();
        Transactions[] transactions = match(MatchArg.and(MatchArg.lessThanOrEqualTo("transactionId", maxId), MatchArg.equals("status", "Pending")));
        if (transactions.length == 0) {
            return;
        }
        Arrays.sort(transactions, tranComparator);
        for (Transactions transaction : transactions) {
            if (transaction.getFundId() <= maxId && transaction.getStatus().equals("Pending")) {
                System.out.println(transaction.getTransactionId());
                Customer customer = customerDAO.read(transaction.getCustomerId());
                if (transaction.getType().equals("Deposit Check")) {
                    transaction.setExecuteDate(closingDate);
                    transaction.setStatus("Processed");
                    // add the transaction amount to account balance here
                    customer.setCash(customer.getCash() + transaction.getAmount());
                    try {
                        Transaction.begin();
                        customerDAO.update(customer);
                        update(transaction);
                        Transaction.commit();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        System.out.println("here 3");
                        e.printStackTrace();
                    }
                } else if (transaction.getType().equals("Request Check")) {
                    transaction.setExecuteDate(closingDate);
                    transaction.setStatus("Processed");
                    // delete the transaction amount from the account balance here
                    customer.setCash(customer.getCash() + transaction.getAmount());
                    try {
                        Transaction.begin();
                        customerDAO.update(customer);
                        update(transaction);
                        Transaction.commit();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        System.out.println("here 4");
                        e.printStackTrace();
                    }
                } else if (transaction.getType().equals("Sell")) {
                    if (fundPriceHistoryDAO.latestFundPRice(transaction.getFundId(), closingDate) == null) {
                        throw new RollbackException("Fund no longer exsits!!!");
                    } else {
                        double price = fundPriceHistoryDAO.latestFundPRice(transaction.getFundId(), closingDate);
                        transaction.setExecuteDate(closingDate);
                        transaction.setStatus("Processed");
                        double amount = price * transaction.getShares();
                        transaction.setAmount(amount);
                        // add the transaction amount to account balance here
                        customer.setCash(customer.getCash() + amount);
                    }
                    try {
                        Transaction.begin();
                        customerDAO.update(customer);
                        update(transaction);
                        updatePos(customer.getCustomerId(), transaction.getFundId(), transaction.getShares(),
                                "Sell", customerPositionDAO);
                        Transaction.commit();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        System.out.println("here 5");
                        e.printStackTrace();
                    }
                } else { // buy
                    if (fundPriceHistoryDAO.latestFundPRice(transaction.getFundId(), closingDate) == null) {
                        throw new RollbackException("Fund no longer exsits!!!");
                    }
                    transaction.setExecuteDate(closingDate);
                    double price = fundPriceHistoryDAO.latestFundPRice(transaction.getFundId(), closingDate);
                    double shares = transaction.getAmount() / price;
                    // ask Jeff about rounding here
                    if (shares > 0) {
                        transaction.setShares(shares);
                        transaction.setStatus("Processed");
                        customer.setCash(customer.getCash() + transaction.getAmount());
                        try {
                            Transaction.begin();
                            customerDAO.update(customer);
                            update(transaction);
                            updatePos(customer.getCustomerId(), transaction.getFundId(), transaction.getShares(),
                                    "Sell", customerPositionDAO);
                            Transaction.commit();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            System.out.println("here 6");
                            e.printStackTrace();
                        }
                    } else {
                        transaction.setShares(0.000);
                        customer.setCash(customer.getCash() + transaction.getAmount());
                        transaction.setStatus("Rejected");
                        try {
                            Transaction.begin();
                            customerDAO.update(customer);
                            update(transaction);
                            updatePos(customer.getCustomerId(), transaction.getFundId(), transaction.getShares(),
                                    "Sell", customerPositionDAO);
                            Transaction.commit();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            System.out.println("here 7");
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    // could call this method from prev code
    public void updatePos(int custId, int fundId, double shares, String fundType,
            CustomerPositionDAO customerPositionDAO) throws Exception { 
        //fund trans can be buy or sell
        //include this in above cases sell fund so that this happens simultaneously
        //will be the same for any type of transaction
        Position[] existingPosition = customerPositionDAO.findPositionsBoth(custId, fundId);
        if (fundType.equals("Sell")) {
            if (existingPosition == null) { // position does not exist, you can't sell
                throw new Exception("Fund does not exist!!!");
            } else { // position exists
                double existingShares = existingPosition[0].getShares();
                double newShares = existingShares - shares;
                if (newShares <= 0) { // sold all shares had, delete position
                    customerPositionDAO.delete(existingPosition[0]);
                } else { // update position for selling
                    existingPosition[0].setShares(existingShares - shares);
                    customerPositionDAO.updatePosition(existingPosition[0]);
                }
            }
        // buy
        } else {
            if (existingPosition == null) { // position does not exist
                Position position = new Position(custId, fundId, shares);
                customerPositionDAO.create(position);
            } else { // update position for buying
                double exsitingShares = existingPosition[0].getShares();
                existingPosition[0].setShares(exsitingShares + shares);
                customerPositionDAO.updatePosition(existingPosition[0]);
            }
        }
    }

    // calculate the pending amount for updating available cash
    public double pendingAmount(int customerId) throws RollbackException {
        double result = 0.00;
        Transactions[] pendingAmounts = match(MatchArg.and(MatchArg.equals("customerId", customerId),
                MatchArg.equals("status", "Pending")));
        if (pendingAmounts == null) {
            return result;
        } else {
            for (Transactions transaction : pendingAmounts) {
                result += transaction.getAmount();
            }
            return result;
        }
    }
    
    // calculate the pending shares for updating available shares for each fund of each customer
    public double pendingShares(int customerId, int fundId) throws RollbackException {
        double result = 0.000;
        Transactions[] pendingShares = match(MatchArg.and(MatchArg.equals("customerId", customerId),
                MatchArg.equals("status", "Pending"), MatchArg.equals("fundId", fundId)));
        if (pendingShares == null) {
            return result;
        } else {
            for (Transactions transaction : pendingShares) {
                result += transaction.getShares();
            }
            return result;
        }
    }
}