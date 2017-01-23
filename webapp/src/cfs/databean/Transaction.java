package cfs.databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("transactionId")
public class Transaction {
    private int transactionId;
    private int customerId;
    private int fundId;
    private double shares;
    private double amount;
    private double price;
    private String executeDate;
    private String status;
    
    public Transaction() {
    }
    
    // when a transaction is created, required input should include all three ids and the amount
    // the shares and price will be available after transition day
    // status should be pending when a transaction is created
    public Transaction(int transactionId, int customerId, int fundId, double amount) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.fundId = fundId;
        this.amount = amount;
        this.status = "Pending";
    }

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
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
