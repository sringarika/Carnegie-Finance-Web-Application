package cfs.formbean;

import java.util.Collections;
import java.util.List;

import org.mybeans.form.FormBean;

public class SellFundForm extends FormBean {
    private String fundId;
    private String shares;
    private int fundIdVal;
    private double sharesVal;

    public String getFundId() {
        return fundId;
    }

    public void setFundId(String fundId) {
        this.fundId = fundId;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }
    
    @Override
    public List<String> getValidationErrors() {
        if (fundId == null || fundId.isEmpty()) {
            return Collections.singletonList("Fund ID is required!");
        }
        if (shares == null || shares.isEmpty()) {
            return Collections.singletonList("Shares are required!");
        }
        
        try {
            fundIdVal = Integer.parseInt(fundId);
        } catch (Exception e) {
            return Collections.singletonList("Invalid fundId!");
        }
        try {
            sharesVal = Double.parseDouble(shares);
        } catch (Exception e) {
            return Collections.singletonList("Invalid shares!");
        }
        
        if (sharesVal < 1.000) {
            return Collections.singletonList("Shares must be at least 1.000!");
        }
        if (sharesVal > 1000000.000) {
            return Collections.singletonList("Shares must not be more than 1,000,000.000!");
        }
        
        return Collections.emptyList();
    }

    public int getFundIdVal() {
        return fundIdVal;
    }

    public double getSharesVal() {
        return sharesVal;
    }

}