package cfs.model;

import java.util.Arrays;
import java.util.Comparator;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import cfs.databean.Transactions;

public class TransactionDAO extends GenericDAO<Transactions> {
    public TransactionDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(Transactions.class, tableName, cp);
    }

    // show the transaction history for a given customer ID
    public Transactions[] showHistory(int CustomerId) throws RollbackException {
        Transactions[] history = match(MatchArg.equals("customerId", CustomerId));
        Comparator<Transactions> tranComparator = new Comparator<Transactions>() {
            @Override
            public int compare(Transactions tran1, Transactions tran2) {
                return tran1.getTransactionId() - tran2.getTransactionId();
            }
        };
        Arrays.sort(history, tranComparator);
        return history;
    }

    // calculate the pending amount for updating available cash
    public double pendingAmount(int customerId) throws RollbackException {
        double result = 0.00;
        Transactions[] pendingAmounts = match(MatchArg.and(MatchArg.equals("customerId", customerId),
                MatchArg.equals("status", "Pending"), MatchArg.lessThan("amount", 0.0)));
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
                MatchArg.equals("status", "Pending"), MatchArg.equals("fundId", fundId), MatchArg.lessThan("shares", 0.0)));
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