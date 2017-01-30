package cfs.formbean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;

import cfs.databean.Customer;

public class DepositCheckForm extends FormBean {
    private String amount;
    private String customerId;
    private BigDecimal amountVal;

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public int getCustomerIdVal() {
        try {
            return Integer.parseInt(customerId);
        } catch (NumberFormatException num) {
            return -1;
        }

    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (amount == null || amount.trim().length() == 0)
            errors.add("Amount is required");

        if (customerId == null || customerId.trim().length() == 0)
            errors.add("customerId is required");

        try {
            Integer.parseInt(customerId);
        } catch (NumberFormatException num) {
            errors.add("Amount must be a valid number!");
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
        if (amountVal.compareTo(new BigDecimal("1000000.00")) > 0) {
            return Collections.singletonList("Amount must not be more than $1,000,000.00!");
        }

        if (errors.size() > 0)
            return errors;

        return errors;
    }

    public BigDecimal getAmountVal() {
        return amountVal;
    }
}
