package cfs.formbean;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.mybeans.form.FormBean;

import cfs.databean.Customer;

public class RequestCheckForm extends FormBean {
    private String amount;
    private BigDecimal amountVal;

    @Override
    public List<String> getValidationErrors() {
        if (amount == null || amount.length() == 0) {
            return Collections.singletonList("Please enter the amount you want to withdraw.");
        }

        try {
            amountVal = Customer.amountFromStr(amount);
        } catch (ArithmeticException e) {
            return Collections.singletonList("Amount can not be more than 2 decimal places!");
        } catch (Exception e) {
            return Collections.singletonList("Invalid amount!");
        }

        if (amountVal.compareTo(BigDecimal.ZERO) <= 0) {
            return Collections.singletonList("Amount must be positive!");
        }

        return Collections.emptyList();
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountVal() {
        return amountVal;
    }
}
