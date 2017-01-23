package cfs.databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("fundId")
public class Fund {
    private int fundId;
    private String name;
    private String symbol;
    
    public Fund() {
    }
    
    public Fund(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }
    
    public int getFundId() {
        return fundId;
    }
    public void setFundId(int fundId) {
        this.fundId = fundId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
