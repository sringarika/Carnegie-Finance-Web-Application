package cfs.model;

import java.util.Map;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import cfs.databean.Customer;
import cfs.databean.Transactions;

public class TransactionDAO extends GenericDAO<Transactions> {
    public TransactionDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(Transactions.class, tableName, cp);
    }
    
    // show the transaction history for a given customer ID
    public Transactions[] showHistory(int CustomerId) throws RollbackException {
    	Transactions[] history = match(MatchArg.equals("customerId", CustomerId));
    	//should we sort the history based on the date?
    	return history;
    }
    public void processTransaction(Map<Integer, Double> closingPrice, String transitionDate,
    		CustomerDAO customerDAO, CustomerPositionDAO customerPositionDAO) throws RollbackException {
    	Transactions[] pendings = match(MatchArg.equals("status", "Pending"));
    	if (pendings == null) {
    		return;
    	}
    	for (Transactions transaction : pendings) {
    		if (transaction.getType() == "Deposit Check" || transaction.getType() == "Request Check" ) {
    			transaction.setExecuteDate(transitionDate);
    			transaction.setStatus("Processed");
    		} else if (transaction.getType() == "Sell") {
    			if (closingPrice.get(transaction.getFundId()) == null) {
        			throw new RollbackException("Error!!!");
        		}
    			transaction.setExecuteDate(transitionDate);
    			transaction.setStatus("Processed");
    			double amount = closingPrice.get(transaction.getFundId()) * transaction.getShares();
    			transaction.setAmount(amount);
    			Customer customer = customerDAO.findByUsername(String.valueOf(transaction.getCustomerId()));
    			customer.setCash(customer.getCash() + amount);
    			try {
    	    		Transaction.begin();
    	    		customerDAO.update(customer);
    	    		Transaction.commit();
        		} finally {
                    if (Transaction.isActive()) Transaction.rollback();
                }
    		} else {
    			if (closingPrice.get(transaction.getFundId()) == null) {
        			throw new RollbackException("Error!!!");
        		}
        		transaction.setPrice(closingPrice.get(transaction.getFundId()));
        		transaction.setExecuteDate(transitionDate);
        		double shares = transaction.getAmount() / transaction.getPrice();
        		// ask Jeff about rounding here
        		String s = String.format("#.###", shares);
        		shares = Double.valueOf(s);
        		if (shares > 0) {
        			transaction.setShares(shares);
        			transaction.setStatus("Processed");
        		} else {
        			Customer customer = customerDAO.findByUsername(String.valueOf(transaction.getCustomerId()));
        			customer.setCash(customer.getCash() + transaction.getAmount());
        			transaction.setStatus("Rejected");
        			try {
        	    		Transaction.begin();
        	    		customerDAO.update(customer);
        	    		Transaction.commit();
            		} finally {
                        if (Transaction.isActive()) Transaction.rollback();
                    }
        		}
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
