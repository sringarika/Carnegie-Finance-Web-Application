package cfs.viewbean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TransactionHistoryView {
    private int transactionId;
    private String transactionType;
    private String transactionStatus;
    private int customerId;
    private String customerName;
    private int fundId;
    private String fundName;
    private String ticker;
    private String date;
    private double shares;
    private double price;
    private double amount;
    
//    public TransactionHistoryView() {
//        return transactionId;
//    }
    
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
    public int getTransactionId() {
        return transactionId;
    }
    public String getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    public String getTransactionStatus() {
        return transactionStatus;
    }
    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
    public String getDate() {
        return date;
    }
    public String getDateDisplay() {
        LocalDate date1 = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        return (date1.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", new Locale("en", "US"))));
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName= customerName;
    }
    public int getFundId() {
        return fundId;
    }
    public void setFundId(int fundId) {
        this.fundId = fundId;
    }
    public double getShares() {
        return shares;
    }
    public void setShares(double shares) {
        this.shares= shares;
    }
    public String getFundName() {
        return fundName;
    }
    public void setFundName(String fundName) {
        this.fundName= fundName;
    }
    public String getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker= ticker;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount= amount;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price= price;
    }
}