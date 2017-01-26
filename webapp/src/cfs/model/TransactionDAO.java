package cfs.model;

import java.util.Map;

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
        // TODO
        // sort the transaction history based on transaction id
        return history;
    }
    // a while loop for looking for all the pending transactions smaller than max transactionId
    // for that closing date
    public void processTransaction(Map<Integer, Double> closingPrice, String transitionDate,
            CustomerDAO customerDAO, CustomerPositionDAO customerPositionDAO) throws RollbackException {
        Transactions[] maxTransaction = match(MatchArg.max("transactionId"));
        if (maxTransaction == null) {
            return;
        }
        int maxId = maxTransaction[0].getTransactionId();
        while (match(MatchArg.and(MatchArg.min("transactionId"), MatchArg.equals("status", "Pending"))) != null) {
            Transactions[] minTransaction = match(MatchArg.and(MatchArg.min("transactionId"), MatchArg.equals("status", "Pending")));
            if (minTransaction[0].getFundId() <= maxId) {
                if (minTransaction[0].getStatus().equals("Pending")) {
                    Transactions transaction = minTransaction[0];
                    Customer customer = customerDAO.findByUsername(String.valueOf(transaction.getCustomerId()));
                    if (transaction.getType().equals("Deposit Check")) {
                        transaction.setExecuteDate(transitionDate);
                        transaction.setStatus("Processed");
                        // add the transaction amount to account balance here
                        customer.setCash(customer.getCash() + transaction.getAmount());
                        try {
                            Transaction.begin();
                            customerDAO.update(customer);
                            super.update(transaction);
                            Transaction.commit();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else if (transaction.getType().equals("Request Check")) {
                        transaction.setExecuteDate(transitionDate);
                        transaction.setStatus("Processed");
                        // delete the transaction amount from the account balance here
                        customer.setCash(customer.getCash() + transaction.getAmount());
                        try {
                            Transaction.begin();
                            customerDAO.update(customer);
                            super.update(transaction);
                            Transaction.commit();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else if (transaction.getType().equals("Sell")) {
                        if (closingPrice.get(transaction.getFundId()) == null) {
                            throw new RollbackException("Fund does not exist!!!");
                        }
                        transaction.setExecuteDate(transitionDate);
                        transaction.setStatus("Processed");
                        double amount = closingPrice.get(transaction.getFundId()) * transaction.getShares();
                        transaction.setAmount(amount);
                        // add the transaction amount to account balance here
                        customer.setCash(customer.getCash() + amount);
                        try {
                            Transaction.begin();
                            customerDAO.update(customer);
                            super.update(transaction);
                            updatePos(customer.getCustomerId(), transaction.getFundId(), transaction.getShares(),
                                    "Sell", customerPositionDAO);
                            Transaction.commit();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else { // buy
                        if (closingPrice.get(transaction.getFundId()) == null) {
                            throw new RollbackException("Fund does not exist!!!");
                        }
                        transaction.setExecuteDate(transitionDate);
                        double shares = transaction.getAmount() / closingPrice.get(transaction.getFundId());
                        // ask Jeff about rounding here
                        String s = String.format("#.###", shares);
                        shares = Double.valueOf(s);
                        if (shares > 0) {
                            transaction.setShares(shares);
                            transaction.setStatus("Processed");
                            customer.setCash(customer.getCash() + transaction.getAmount());
                            try {
                                Transaction.begin();
                                customerDAO.update(customer);
                                super.update(transaction);
                                updatePos(customer.getCustomerId(), transaction.getFundId(), transaction.getShares(),
                                        "Sell", customerPositionDAO);
                                Transaction.commit();
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        } else {
                            transaction.setShares(0.000);
                            customer.setCash(customer.getCash() + transaction.getAmount());
                            transaction.setStatus("Rejected");
                            try {
                                Transaction.begin();
                                customerDAO.update(customer);
                                super.update(transaction);
                                updatePos(customer.getCustomerId(), transaction.getFundId(), transaction.getShares(),
                                        "Sell", customerPositionDAO);
                                Transaction.commit();
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
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
        try {
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
        } catch (RollbackException e) {
            e.printStackTrace();
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