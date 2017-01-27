package cfs.formbean;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

//import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;

public class DepositCheckForm extends FormBean {
    private String amount;
    private String customerId;

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public double getRequestAmount() {
        try {
            return Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            return -1;
        }
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

        double a = 0;
        try {
            //insert check for 2 decimal digits heres
            

            if (a <= 0) {
                errors.add("Amount must be greater than zero!");
            }

            if (a > 1000000) {
                errors.add("Amount cannot be greater than $1,000,000.00!");
            }
        } catch (NumberFormatException num) {
            errors.add("Amount must be a valid number!");
        }

        int b = 0;
        try {
            b = Integer.parseInt(customerId);
        } catch (NumberFormatException num) {
            errors.add("Amount must be a valid number!");
        }

        if (errors.size() > 0)
            return errors;

        return errors;
    }

}
