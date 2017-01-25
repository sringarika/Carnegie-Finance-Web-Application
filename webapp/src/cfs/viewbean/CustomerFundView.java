package cfs.viewbean;

public class CustomerFundView {
	    private int fundId;
	    private String fundName;
	    private String symbol;
	    private double numOfShares;
	    private double price;
	    private double value;
	    
	    public CustomerFundView(int fundId, String fundName, String symbol, double numOfShares, double price) {
	        this.fundId = fundId;
	        this.fundName = fundName;
	        this.symbol = symbol;
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
	    
	    public String getSymbol() {
	        return symbol;
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
