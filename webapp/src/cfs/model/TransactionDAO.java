package cfs.model;

import java.util.Map;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import cfs.databean.Position;
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
    			transaction.setStatus("Processed"); //where is the amount being added/removed?
    		} else if (transaction.getType() == "Sell") {
    			if (closingPrice.get(transaction.getFundId()) == null) {
        			throw new RollbackException("Error!!!"); //Fund does not exist?(should specify)
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
        			throw new RollbackException("Error!!!"); //???
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
    		// TODO 
    		// update customer's position according to the type of the transaction
    		// if position for this customer and this fund exists, update it
    		// otherwise, create a position for the customer and fund
    		
    	}
    }
    
    //could call this method from prev code
    public void updatepos(int custId, int fundId, double shares, String fundtrans,CustomerPositionDAO customerPositionDAO ) { //fund trans can be buy or sell
		//include this in above cases sell fund so that this happens simultaneously
    	//will be the same for any type of transaction
			try {
				Position[] posc = customerPositionDAO.findPositionscid(custId);
				Position[] posf = customerPositionDAO.findPositionsfid(fundId);
				if(posc == null) {//position does not exist
				    Position posn = new Position();
				    posn.setShares(shares);
				    customerPositionDAO.updatepos(posn);
				} else {
				    for(Position p : posc) {
				    	p.setShares(shares);
				    	customerPositionDAO.updatepos(p);
				    }
				}
					
                if(posf != null) { //if null then already generated with customer id
				    for(Position p : posc) {
				    	p.setShares(shares);
				    	customerPositionDAO.updatepos(p);
				    }
                }
				
			} catch (RollbackException e) {
				e.printStackTrace();
			}

    }
}
