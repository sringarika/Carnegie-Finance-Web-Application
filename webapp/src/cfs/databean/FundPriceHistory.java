package cfs.databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("fundId,executeDate")
public class FundPriceHistory {
    private String executeDate;
    private int fundId;
    private double price;

    public FundPriceHistory() {
    }

    public FundPriceHistory(String executeDate, int fundId, double price) {
        this.executeDate = executeDate;
        this.fundId = fundId;
        this.price = price;
    }

    public String getExecuteDate() {
        return executeDate;
    }
    public void setExecuteDate(String executeDate) {
        this.executeDate = executeDate;
    }

    public int getFundId() {
        return fundId;
    }
    public void setFundId(int fundId) {
        this.fundId = fundId;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
