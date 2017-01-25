package cfs.databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("fundId")
public class Fund {
    private int fundId;
    private String name;
    private String ticker;
    
    public Fund() {
    }
    
    public Fund(String name, String ticker) {
        this.name = name;
        this.ticker = ticker;
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
    public String getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
