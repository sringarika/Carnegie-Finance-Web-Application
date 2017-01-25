package cfs.viewbean;

import java.util.List;

import cfs.databean.Fund;
import cfs.databean.FundPriceHistory;

public class ResearchFund {
	 private int fundId;
	 private String fundName;
	 private String symbol;
	 private List<Double> priceList;
	 
	 public int getFundId() {
		 return fundId;
	 }
	 public void setFundId(int fundId) {
		 this.fundId = fundId;
	 }
	 public String getFundName() {
		 return fundName;
	 }
     public void setFundName(String fundName) {
    	 this.fundName = fundName;
	 }
     public String getSymbol() {
         return symbol;
     }
     public void setSymbol(String symbol) {
         this.symbol = symbol;
     }
     public List<Double> getPriceList() {
         return priceList;
     }
     public void setPriceList(Double price) {
         priceList.add(price);
     }
}

	
