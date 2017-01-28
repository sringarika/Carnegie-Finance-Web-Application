package cfs.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.GenericViewDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import cfs.databean.Customer;
import cfs.databean.Position;
import cfs.databean.Transactions;
import cfs.viewbean.TransactionHistoryView;

public class TransactionDAO extends GenericDAO<Transactions> {
    GenericViewDAO<TransactionHistoryView> viewDAO;
    public TransactionDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(Transactions.class, tableName, cp);
        viewDAO = new GenericViewDAO<TransactionHistoryView>(TransactionHistoryView.class, cp);
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

    public TransactionHistoryView[] showHistoryView(int customerId) throws RollbackException {
        String sql = "SELECT T.transactionId AS transactionId," +
                     "       T.type          AS transactionType," +
                     "       T.status        AS transactionStatus," +
                     "       T.customerId    AS customerId," +
                     "       C.username      AS customerName," +
                     "       CAST(F.fundId AS CHAR(50))        AS fundId," +
                     "       F.name          AS fundName," +
                     "       F.ticker        AS ticker," +
                     "       T.executeDate   AS date," +
                     "       CAST(T.shares AS CHAR(50))        AS shares," +
                     "       CAST(P.price AS CHAR(50))         AS price," +
                     "       CAST(T.amount AS CHAR(50))        AS amount" +
                     "  FROM customer C, transactions T" +
                     "    LEFT JOIN fund F      ON T.fundId = F.fundId" +
                     "    LEFT JOIN fundprice P ON T.fundId = P.fundId AND T.executeDate = P.executeDate" +
                     "  WHERE C.customerId = T.customerId" +
                     "    AND T.customerId = ?";
        TransactionHistoryView[] history = viewDAO.executeQuery(sql, customerId);
        Arrays.sort(history, (a, b) -> b.getTransactionId() - a.getTransactionId());
        return history;
    }

    public TransactionHistoryView[] showAll() throws RollbackException {
        String sql = "SELECT T.transactionId AS transactionId," +
                "       T.type          AS transactionType," +
                "       T.status        AS transactionStatus," +
                "       T.customerId    AS customerId," +
                "       C.username      AS customerName," +
                "       CAST(F.fundId AS CHAR(50))        AS fundId," +
                "       F.name          AS fundName," +
                "       F.ticker        AS ticker," +
                "       T.executeDate   AS date," +
                "       CAST(T.shares AS CHAR(50))        AS shares," +
                "       CAST(P.price AS CHAR(50))         AS price," +
                "       CAST(T.amount AS CHAR(50))        AS amount" +
                "  FROM customer C, transactions T" +
                "    LEFT JOIN fund F      ON T.fundId = F.fundId" +
                "    LEFT JOIN fundprice P ON T.fundId = P.fundId AND T.executeDate = P.executeDate" +
                "  WHERE C.customerId = T.customerId";
        TransactionHistoryView[] history = viewDAO.executeQuery(sql);
        Arrays.sort(history, (a, b) -> b.getTransactionId() - a.getTransactionId());
        return history;
    }

    // calculate the pending amount for updating available cash
    public BigDecimal pendingAmount(int customerId) throws RollbackException {
        BigDecimal result = BigDecimal.ZERO;
        Transactions[] transactions = match(MatchArg.and(MatchArg.equals("customerId", customerId),
                MatchArg.equals("status", "Pending"), MatchArg.lessThan("amount", 0.0)));
        if (transactions == null) {
            return result;
        } else {
            for (Transactions transaction : transactions) {
                result = result.add(Customer.amountFromDouble(transaction.getAmount()));
            }
            return result;
        }
    }

    // calculate the pending shares for updating available shares for each fund of each customer
    public BigDecimal pendingShares(int customerId, int fundId) throws RollbackException {
        BigDecimal pendingShares = BigDecimal.ZERO;
        Transactions[] transactions = match(MatchArg.and(MatchArg.equals("customerId", customerId),
                MatchArg.equals("status", "Pending"), MatchArg.equals("fundId", fundId), MatchArg.lessThan("shares", 0.0)));
        if (transactions == null) {
            return pendingShares;
        } else {
            for (Transactions transaction : transactions) {
                pendingShares = pendingShares.add(Position.sharesFromDouble(transaction.getShares()));
            }
            return pendingShares;
        }
    }
}