package cfs.viewbean;

public class TransactionListView {
	    private int fundId;
	    private int customerId;
	    private String date;
	    private double numOfShares;
	    private String transactiontype;
	    private double amount;
	    private String transactionstatus;
	    
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
}
