package cfs.viewbean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TransactionListView {
	    private int fundId;
	    private int customerId;
	    private String customername;
	    private String date;
	    private double numOfShares;
	    private String transactiontype;
	    private double amount;
	    private String transactionstatus;
	    
	    //if you want to set all at once use this, else I have provided for individual setters as well
	    public TransactionListView() {
	    }
	    
	    //getters
	    public int getFundId() {
	        return fundId;
	    }
	    
	    public double getCustomerId() {
		    return customerId;
		}
	    
	    public String getExecuteDate() {
	    	return date;
	    }
	    public String getExecuteDatedisp() {
	    	String var = date;
            LocalDate date = LocalDate.parse(var, DateTimeFormatter.ISO_DATE);
            return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", new Locale("en", "US")));
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
	    public String getCustomername() {
	    	return customername;
	    }
	    
	    
	    //setters
	    public void setFundId(int fundId) {
	    	this.fundId = fundId;
	    }
	    
	    public void setCustomerId(int customerId) {
	    	this.customerId = customerId;
		}
	    
	    public void setDate(String date) {
	    	this.date = date;	    
	    	}
	    
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
	    public void setCustomername(String customername) {
	    	this.customername = customername;
	    }
}
