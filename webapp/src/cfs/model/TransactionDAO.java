package cfs.model;

import java.util.Map;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import cfs.databean.Transactions;

public class TransactionDAO extends GenericDAO<Transactions> {
    public TransactionDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(Transactions.class, tableName, cp);
    }
    
    public void processTransaction(Map<Integer, Double> closingPrice, String transitionDate) throws RollbackException {
    	Transactions[] pendings = match(MatchArg.equals("status", "Pending"));
    	if (pendings == null) {
    		return;
    	}
    	for (Transactions transaction : pendings) {
    		if (closingPrice.get(transaction.getFundId()) == null) {
    			throw new RollbackException("Error!!!");
    		}
    		transaction.setPrice(closingPrice.get(transaction.getFundId()));
    		transaction.setExecuteDate(transitionDate);
    		double shares = transaction.getAmount() / transaction.getPrice();
    		String s = String.format("#.###", shares);
    		shares = Double.valueOf(s);
    		if (shares > 0) {
    			transaction.setShares(shares);
    			transaction.setStatus("Processed");
    		} else {
    			transaction.setStatus("Rejected");
    			// need to return the reserved money back to the customer
    		}
    		try {
	    		Transaction.begin();
	    		super.update(transaction);
	    		Transaction.commit();
    		} finally {
                if (Transaction.isActive()) Transaction.rollback();
            }
    	}
    }
}
