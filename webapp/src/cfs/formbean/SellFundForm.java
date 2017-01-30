package cfs.formbean;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.mybeans.form.FormBean;

import cfs.databean.Position;

public class SellFundForm extends FormBean {
    private String fundId;
    private String shares;
    private int fundIdVal;
    private BigDecimal sharesVal;

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
            sharesVal = Position.sharesFromStr(shares);
        } catch (ArithmeticException e) {
            return Collections.singletonList("Shares can not be more than 3 decimal places!");
        } catch (Exception e) {
            return Collections.singletonList("Invalid shares!");
        }

        if (sharesVal.compareTo(BigDecimal.ZERO) <= 0) {
            return Collections.singletonList("Shares must be positive!");
        }
        if (sharesVal.compareTo(new BigDecimal("1000000.000")) > 0) {
            return Collections.singletonList("Shares must not be more than 1,000,000.000!");
        }

        return Collections.emptyList();
    }

    public int getFundIdVal() {
        return fundIdVal;
    }

    public BigDecimal getSharesVal() {
        return sharesVal;
    }

}