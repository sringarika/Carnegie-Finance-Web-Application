package cfs.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericViewDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import cfs.databean.Customer;
import cfs.databean.FundPriceHistory;
import cfs.databean.Position;
import cfs.databean.Transactions;

public class TransactionProcessor {
    public static class LongValueBox {
        private long value;

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }

    private FundPriceHistoryDAO fundPriceHistoryDAO;
    private CustomerDAO customerDAO;
    private CustomerPositionDAO customerPositionDAO;
    private TransactionDAO transactionDAO;
    private GenericViewDAO<LongValueBox> boxViewDAO;

    public TransactionProcessor(Model model, ConnectionPool pool) throws DAOException {
        fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
        customerDAO = model.getCustomerDAO();
        customerPositionDAO = model.getCustomerPositionDAO();
        transactionDAO = model.getTransactionDAO();
        boxViewDAO = new GenericViewDAO<LongValueBox>(LongValueBox.class, pool);
    }

    public synchronized void transitionDay(String transitionDate) throws RollbackException {
        try {
            Transaction.begin();

            Transactions[] transactions = transactionDAO.match(MatchArg.equals("status", Transactions.PENDING));
            for (Transactions transaction : transactions) {
                // Schedule the pending transaction to be executed today if not scheduled.
                // This ensures that each transaction get executed using today's price.
                // This also ensures that new transactions after this point will not be processed, even if
                // they are pending.
                if (transaction.getExecuteDate() == null) {
                    transaction.setExecuteDate(transitionDate);
                    transactionDAO.update(transaction);
                }
            }

            Transaction.commit();
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }

    public synchronized void processPendingTransactions() {
        int lastTransactionId = -1;
        do {
            try {
                int transactionId = processNextPendingTransaction();
                if (transactionId > 0 && transactionId == lastTransactionId) {
                    // We are stuck on this transaction. Let's abort for now.
                    throw new RollbackException("Transaction " + transactionId + " cannot be processed!");
                }
                lastTransactionId = transactionId;
            } catch (RollbackException e) {
                lastTransactionId = -1;
                e.printStackTrace();
            }
        } while (lastTransactionId > 0);
    }

    private int processNextPendingTransaction() throws RollbackException {
        try {
            Transaction.begin();

            Integer transactionId = getNextPendingTransactionId();
            if (transactionId == null) return -1;
            this.processTransaction(transactionId);

            Transaction.commit();
            return transactionId;
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }

    private Integer getNextPendingTransactionId() throws RollbackException {
        LongValueBox[] result = boxViewDAO.executeQuery(
                "SELECT CAST(IfNull(MIN(transactionId), 0) AS SIGNED) AS value FROM transactions WHERE status = ? AND executeDate IS NOT NULL", Transactions.PENDING);
        if (result == null || result.length == 0) {
            return null;
        } else {
            long id = result[0].getValue();
            if (id == 0) return null;
            return (int) id;
        }
    }

    private void processTransaction(int transactionId) throws RollbackException {
        Transactions transaction = transactionDAO.read(transactionId);
        if (transaction == null) {
            throw new RollbackException("Transaction not found!");
        } else if (!transaction.getStatus().equals(Transactions.PENDING)) {
            // The transaction has already been processed.
            return;
        } else if (transaction.getExecuteDate() == null) {
            // The transaction should be processed later.
            return;
        }
        if (transaction.getType().equals(Transactions.DEPOSIT_CHECK)) {
            this.processDepositCheck(transaction);
        } else if (transaction.getType().equals(Transactions.REQUEST_CHECK)) {
            this.processRequestCheck(transaction);
        } else if (transaction.getType().equals(Transactions.BUY)) {
            this.processBuy(transaction);
        } else if (transaction.getType().equals(Transactions.SELL)) {
                this.processSell(transaction);
        } else {
            throw new RollbackException("Unsupported transaction type: " + transaction.getType());
        }
    }

    private void processDepositCheck(Transactions transaction) throws RollbackException {
        Customer customer = customerDAO.read(transaction.getCustomerId());
        if (customer == null) {
            throw new RollbackException("Customer does not exist!");
        }
        customer.setCash(customer.getCash() + transaction.getAmount());
        customerDAO.update(customer);
        transaction.setStatus(Transactions.PROCESSED);
        transactionDAO.update(transaction);
    }

    private void processRequestCheck(Transactions transaction) throws RollbackException {
        Customer customer = customerDAO.read(transaction.getCustomerId());
        if (customer == null) {
            throw new RollbackException("Customer does not exist!");
        }
        // Amount should be negative by definition. So just add it in.
        double newBalance = customer.getCash() + transaction.getAmount();
        if (newBalance < 0) {
            // Doing so would result in a negative balance.
            transaction.setStatus(Transactions.REJECTED);
            transactionDAO.update(transaction);
            return;
        }
        customer.setCash(newBalance);
        customerDAO.update(customer);
        transaction.setStatus(Transactions.PROCESSED);
        transactionDAO.update(transaction);
    }

    private void processBuy(Transactions transaction) throws RollbackException {
        Customer customer = customerDAO.read(transaction.getCustomerId());
        if (customer == null) {
            throw new RollbackException("Customer does not exist!");
        }

        FundPriceHistory fundPrice = fundPriceHistoryDAO.read(transaction.getFundId(), transaction.getExecuteDate());

        if (fundPrice == null) {
            throw new RollbackException("Cannot find price for Fund ID" + transaction.getFundId() +
                    " on " + transaction.getExecuteDate() + "!");
        }

        // Amount should be negative by definition.
        BigDecimal amount = new BigDecimal(-transaction.getAmount());

        // The customer is always charged the amount he/she pays. No rounding involved.
        BigDecimal newBalance = Customer.amountFromDouble(customer.getCash()).subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            // Doing so would result in a negative balance.
            transaction.setStatus(Transactions.REJECTED);
            transactionDAO.update(transaction);
            return;
        }

        BigDecimal price = new BigDecimal(fundPrice.getPrice());
        BigDecimal buyingShares = sharesForAmount(amount, price);

        // If the minimum amount is $10.00 and the maximum price is $1,000.00 per
        // share, the customer should get at least 0.010 share. But just in case...
        if (buyingShares.compareTo(BigDecimal.ZERO) <= 0) {
            // We end up with zero shares or negative shares.
            transaction.setStatus(Transactions.REJECTED);
            transactionDAO.update(transaction);
            return;
        }

        Position position = customerPositionDAO.read(customer.getCustomerId(), transaction.getFundId());
        if (position == null) {
            position = new Position();
            position.setCustomerId(customer.getCustomerId());
            position.setFundId(transaction.getFundId());
            position.setShares(buyingShares.doubleValue());
            customerPositionDAO.create(position);
        } else {
            BigDecimal positionShares = Position.sharesFromDouble(position.getShares());
            BigDecimal newShares = positionShares.add(buyingShares);
            position.setShares(newShares.doubleValue());
            customerPositionDAO.update(position);
        }

        customer.setCash(newBalance.doubleValue());
        customerDAO.update(customer);

        transaction.setStatus(Transactions.PROCESSED);
        transaction.setShares(buyingShares.doubleValue());
        transactionDAO.update(transaction);
    }

    private void processSell(Transactions transaction) throws RollbackException {
        Customer customer = customerDAO.read(transaction.getCustomerId());
        if (customer == null) {
            throw new RollbackException("Customer does not exist!");
        }

        FundPriceHistory fundPrice = fundPriceHistoryDAO.read(transaction.getFundId(), transaction.getExecuteDate());

        if (fundPrice == null) {
            throw new RollbackException("Cannot find price for Fund ID" + transaction.getFundId() +
                    " on " + transaction.getExecuteDate() + "!");
        }

        Position position = customerPositionDAO.read(customer.getCustomerId(), transaction.getFundId());
        if (position == null) {
            // The customer does not have position in the fund.
            transaction.setStatus(Transactions.REJECTED);
            transactionDAO.update(transaction);
            return;
        }

        BigDecimal positionShares = Position.sharesFromDouble(position.getShares());
        // Shares should be negative by definition.
        BigDecimal sellingShares = Position.sharesFromDouble(-transaction.getShares());

        // The customer's position should be deducted by the exact number of shares he/she wants to sell.
        BigDecimal remainingShares = positionShares.subtract(sellingShares);
        if (remainingShares.compareTo(BigDecimal.ZERO) < 0) {
            // Doing so would result in negative shares.
            transaction.setStatus(Transactions.REJECTED);
            transactionDAO.update(transaction);
            return;
        }

        BigDecimal price = Customer.amountFromDouble(fundPrice.getPrice());
        BigDecimal amount = amountForShares(sellingShares, price);

        // If the minimum price per share is $10.00, the customer should always get some money
        // even if only 0.001 share is sold. But just in case...
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            // We end up with zero amount or negative amount.
            transaction.setStatus(Transactions.REJECTED);
            transactionDAO.update(transaction);
            return;
        }

        position.setShares(remainingShares.doubleValue());
        customerPositionDAO.update(position);

        BigDecimal newBalance = Customer.amountFromDouble(customer.getCash()).add(amount);
        customer.setCash(newBalance.doubleValue());
        customerDAO.update(customer);

        transaction.setStatus(Transactions.PROCESSED);
        transaction.setAmount(amount.doubleValue());
        transactionDAO.update(transaction);
    }

    public BigDecimal sharesForAmount(BigDecimal amount, BigDecimal price) {
        // Shares are tracked to three digits after the decimal point.
        // HALF_EVEN must be used according to requirements.
        // It is okay for the customer to gain or lose some shares in rounding.
        return amount.divide(price, 3, RoundingMode.HALF_EVEN);
    }

    public BigDecimal amountForShares(BigDecimal shares, BigDecimal price) {
        // Amount are tracked to two digits after the decimal point.
        // HALF_EVEN must be used according to requirements.
        // It is okay for the customer to gain or lose less than a cent in rounding.
        return shares.multiply(price).setScale(2, RoundingMode.HALF_EVEN);
    }
}