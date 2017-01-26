package cfs.viewbean;

public class TransactionListView {
	    private int fundId;
	    private int customerId;
	    private String date;
	    private double numOfShares;
	    private String transactiontype;
	    private double amount;
	    private String transactionstatus;
	    
	    //if you want to set all at once use this, else I have provided for individual setters as well
	    public TransactionListView(int fundId, int customerId, String date, double numOfShares,
	    		String transactiontype, double amount,String transactionstatus) {
	        this.fundId = fundId;
	        this.customerId = customerId;
	        this.date = date;
	        this.numOfShares = numOfShares;
	        this.transactiontype = transactiontype;
	        this.amount = amount;
	        this.transactionstatus = transactionstatus;
	    }
	    
	    //getters
	    public int getFundId() {
	        return fundId;
	    }
	    
	    public double getCustomerId() {
		    return customerId;
		}
	    
	    public String getDate() {
	        return date;
	    }
	    
	    public double getNumOfShares() {
	        return numOfShares;
	    }

	    public String getTransactiontype() {
	        return transactiontype;
	    }
	    
	    public double getAmount() {
		    return amount;
	    }
	    
	    public String getTransactionstatus() {
		    return transactionstatus;
		}
	    
	    
	    //setters
	    public void setFundId(int fundId) {
	    	this.fundId = fundId;
	    }
	    
	    public void setCustomerId(int customerId) {
	    	this.customerId = customerId;
		}
	    
	    public void setDate(String date) {
	    	this.date = date;	    }
	    
	    public void setNumOfShares(int numOfShares) {
	        this.numOfShares = numOfShares;
	    }

	    public void setTransactiontype(String transactiontype) {
	        this.transactiontype = transactiontype;
	    }
	    
	    public void setAmount(int amount) {
		    this.amount = amount;
	    }
	    
	    public void setTransactionstatus(String transactionstatus) {
		    this.transactionstatus = transactionstatus;
		}
}
