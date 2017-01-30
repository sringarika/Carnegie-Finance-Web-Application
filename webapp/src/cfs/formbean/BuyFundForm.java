package cfs.formbean;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.mybeans.form.FormBean;

import cfs.databean.Position;

public class BuyFundForm extends FormBean {
    private String fundId;
    private String amount;
    private int fundIdVal;
    private BigDecimal amountVal;

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
            amountVal = Position.sharesFromStr(amount);
        } catch (ArithmeticException e) {
            return Collections.singletonList("Amount can not be more than 2 decimal places!");
        } catch (Exception e) {
            return Collections.singletonList("Invalid amount!");
        }

        if (amountVal.compareTo(new BigDecimal("10.00")) < 0) {
            return Collections.singletonList("Amount must be at least $10.00!");
        }
        if (amountVal.compareTo(new BigDecimal("1000000.00")) > 0) {
            return Collections.singletonList("Amount must not be more than $1,000,000.00!");
        }

        return Collections.emptyList();
    }

    public int getFundIdVal() {
        return fundIdVal;
    }

    public BigDecimal getAmountVal() {
        return amountVal;
    }

}