package cfs.databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("fundId")
public class Fund {
    private int fundId;
    private String fund;
    private String ticker;
    
    public Fund() {
    }
    
    public Fund(String fund, String ticker) {
        this.fund = fund;
        this.ticker = ticker;
    }
    
    public int getFundId() {
        return fundId;
    }
    public void setFundId(int fundId) {
        this.fundId = fundId;
    }
    public String getName() {
        return fund;
    }
    public void setName(String name) {
        this.fund = name;
    }
    public String getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
