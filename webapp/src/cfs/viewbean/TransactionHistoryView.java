package cfs.viewbean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import cfs.databean.Transactions;

public class TransactionHistoryView {
    private int transactionId;
    private String transactionType;
    private String transactionStatus;
    private int customerId;
    private String customerName;
    private String fundId;
    private String fundName;
    private String ticker;
    private String date;
    private String shares;
    private String price;
    private String amount;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
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
        this.customerName = customerName;
    }

    public String getFundId() {
        return fundId;
    }

    public void setFundId(String fundId) {
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

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    public Double getSharesVal() {
        if (shares == null) {
            return null;
        }
        if (!transactionType.equals(Transactions.BUY) && !transactionType.equals(Transactions.SELL)) {
            return null;
        }
        if (transactionType.equals(Transactions.BUY) && !transactionStatus.equals(Transactions.PROCESSED)) {
            return null;
        }
        return Math.abs(Double.parseDouble(shares));
    }

    public String getPrice() {
        return price;
    }

    public Double getPriceVal() {
        if (price == null) {
            return null;
        }
        if (!transactionStatus.equals(Transactions.PROCESSED)) {
            return null;
        }
        return Double.parseDouble(price);
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Double getAmountVal() {
        if (amount == null) {
            return null;
        }
        if (transactionType.equals(Transactions.SELL) && !transactionStatus.equals(Transactions.PROCESSED)) {
            return null;
        }
        return Math.abs(Double.parseDouble(amount));
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateDisp() {
        if (date == null) return null;
        LocalDate date1 = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        return (date1.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", new Locale("en", "US"))));
    }
}