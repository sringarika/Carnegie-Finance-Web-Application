package cfs.viewbean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TransactionHistoryView {
    private int transactionId;
    private String transactionType;
    private String date;
    private double numOfShares;
    private String fundName;
    private String ticker;
    private double amount;
    private String transactionStatus;
    
//    public TransactionHistoryView() {
//        return transactionId;
//    }
    
    public void setTransactionId(int transactionId) {
        this.transactionId= transactionId;
    }
    public int getTransactionId(int transactionId) {
        return transactionId;
    }
    public String getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(String transactionType) {
        this.transactionType= transactionType;
    }
    public String getDate() {
        return date;
    }
    public String getDateDisplay() {
        LocalDate date1 = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        return (date1.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", new Locale("en", "US"))));
    }
    public void setDate(String date) {
        this.date= date;
    }
    public double getNumOfShares() {
        return numOfShares;
    }
    public void setNumOfShares(double numOfShares) {
        this.numOfShares= numOfShares;
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
    public String getTransactionStatus() {
        return transactionStatus;
    }
    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus= transactionStatus;
    }
    
    }