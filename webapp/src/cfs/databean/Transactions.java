package cfs.databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("transactionId")
public class Transactions {
    public static final String PENDING = "Pending";
    public static final String PROCESSED = "Processed";
    public static final String REJECTED = "Rejected";

    public static final String DEPOSIT_CHECK = "Deposit Check";
    public static final String REQUEST_CHECK = "Request Check";
    public static final String BUY = "Buy";
    public static final String SELL = "Sell";

    private int transactionId;
    private int customerId;
    private int fundId;
    private double shares;
    private double amount;
    private String executeDate;
    private String status;
    private String type;

    public int getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public int getFundId() {
        return fundId;
    }
    public void setFundId(int fundId) {
        this.fundId = fundId;
    }
    public String getExecuteDate() {
        return executeDate;
    }
    public void setExecuteDate(String executeDate) {
        this.executeDate = executeDate;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public double getShares() {
        return shares;
    }
    public void setShares(double shares) {
        this.shares = shares;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
}