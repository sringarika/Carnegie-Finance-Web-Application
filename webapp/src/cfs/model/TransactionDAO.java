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
    	    Customer customer = customerDAO.findByUsername(String.valueOf(transaction.getCustomerId()));
    		if (transaction.getType().equals("Deposit Check")) {
    		    transaction.setExecuteDate(transitionDate);
                transaction.setStatus("Processed");
                // the available cash is updated here
                customer.setCash(customer.getCash() + transaction.getAmount()); 
    		} else if (transaction.getType().equals("Request Check")) {
    			transaction.setExecuteDate(transitionDate);
    			transaction.setStatus("Processed"); // the available cash is updated already
    		} else if (transaction.getType().equals("Sell")) {
    			if (closingPrice.get(transaction.getFundId()) == null) {
        			throw new RollbackException("Fund does not exist!!!");
        		}
    			transaction.setExecuteDate(transitionDate);
    			transaction.setStatus("Processed");
    			double amount = closingPrice.get(transaction.getFundId()) * transaction.getShares();
    			transaction.setAmount(amount);
    			// the available cash is updated here
    			customer.setCash(customer.getCash() + amount);
    			try {
    	    		Transaction.begin();
    	    		customerDAO.update(customer);
    	    		Transaction.commit();
        		} finally {
                    if (Transaction.isActive()) Transaction.rollback();
                }
    			try {
                    updatePos(customer.getCustomerId(), transaction.getFundId(), transaction.getShares(),
                            "Sell", customerPositionDAO);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
    		} else { // buy
    			if (closingPrice.get(transaction.getFundId()) == null) {
        			throw new RollbackException("Fund does not exist!!!");
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
        			try {
                        updatePos(customer.getCustomerId(), transaction.getFundId(), shares,
                                "Buy", customerPositionDAO);
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
    public void updatePos(int custId, int fundId, double shares, String fundType,
            CustomerPositionDAO customerPositionDAO) throws Exception { //fund trans can be buy or sell
		//include this in above cases sell fund so that this happens simultaneously
    	//will be the same for any type of transaction
			try {
				Position[] existingPosition = customerPositionDAO.findPositionsBoth(custId, fundId);
				// sell
				if (fundType.equals("Sell")) {
				    if (existingPosition == null) { // position does not exist, you can't sell
				        throw new Exception("Fund does not exist!!!");
                    } else { // position exists
                        double existingShares = existingPosition[0].getShares();
                        double newShares = existingShares - shares;
                        if (newShares <= 0) { // sold all shares had, delete position
                            Transaction.begin();
                            customerPositionDAO.delete(existingPosition[0]);
                            Transaction.commit();
                        } else { // // update position for selling 
                            existingPosition[0].setShares(existingShares - shares);
                            Transaction.begin();
                            customerPositionDAO.update(existingPosition[0]);
                            Transaction.commit();
                        }
                    }
				// buy
				} else {
    				if (existingPosition == null) { // position does not exist
    				    Position position = new Position(custId, fundId, shares);
    				    Transaction.begin();
    				    customerPositionDAO.create(position);
    				    Transaction.commit();
    				} else { // update position for buying
    				    double exsitingShares = existingPosition[0].getShares();
    				    existingPosition[0].setShares(exsitingShares + shares);
    				    Transaction.begin();
    				    customerPositionDAO.update(existingPosition[0]);
    				    Transaction.commit();
    				}
				}
			} catch (RollbackException e) {
				e.printStackTrace();
			}

    }
}
