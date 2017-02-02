package cfs.viewbean;

public class ResearchFundView {
    private int fundId;
    private String fundName;
    private String ticker;
    private String lastClosingDate;
    private Double price;

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

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLastClosingDate() {
        return lastClosingDate;
    }

    public void setLastClosingDate(String date) {
        this.lastClosingDate = date;
    }
}
