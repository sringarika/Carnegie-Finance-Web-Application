package cfs.formbean;

import java.util.Collections;
import java.util.List;

import org.mybeans.form.FormBean;

public class BuyFundForm extends FormBean {
    private String fundId;
    private String amount;
    private int fundIdVal;
    private double amountVal;

    public String getFundId() {
        return fundId;
    }

    public void setFundId(String fundId) {
        this.fundId = fundId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    @Override
    public List<String> getValidationErrors() {
        if (fundId == null || fundId.isEmpty()) {
            return Collections.singletonList("Fund ID is required!");
        }
        if (amount == null || amount.isEmpty()) {
            return Collections.singletonList("Amount is required!");
        }
        
        try {
            fundIdVal = Integer.parseInt(fundId);
        } catch (Exception e) {
            return Collections.singletonList("Invalid fundId!");
        }
        try {
            amountVal = Double.parseDouble(amount);
        } catch (Exception e) {
            return Collections.singletonList("Invalid amount!");
        }
        
        if (amountVal < 1.00) {
            return Collections.singletonList("Amount must be at least $1.00!");
        }
        if (amountVal > 1000000.00) {
            return Collections.singletonList("Amount must not be more than $1,000,000.00!");
        }
        
        return Collections.emptyList();
    }

    public int getFundIdVal() {
        return fundIdVal;
    }

    public double getAmountVal() {
        return amountVal;
    }

}