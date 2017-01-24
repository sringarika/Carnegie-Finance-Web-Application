package cfs.viewbean;

public class CustomerFundView {
	    private int fundId;
	    private String fundName;
	    private String ticker;
	    private double numOfShares;
	    private double price;
	    private double value;
	    
	    public CustomerFundView(int fundId, String fundName, String ticker, double numOfShares, double price) {
	        this.fundId = fundId;
	        this.fundName = fundName;
	        this.ticker = ticker;
	        this.numOfShares = numOfShares;
	        this.price = price;
	        this.value = numOfShares * price;
	    }
	    
	    public int getFundId() {
	        return fundId;
	    }
	    
	    public String getFundName() {
	        return fundName;
	    }
	    
	    public String getTicker() {
	        return ticker;
	    }
	    
	    public double getNumOfShares() {
	        return numOfShares;
	    }
	    public double getPrice() {
	      return price;
	    }
	    public double getValue() {
	      return value;
	    }
}
