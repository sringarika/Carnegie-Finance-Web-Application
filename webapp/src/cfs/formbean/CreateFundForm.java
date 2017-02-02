package cfs.formbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;

public class CreateFundForm extends FormBean {
    private String fund;
    private String ticker;

    public void setFund(String fund) {
        this.fund = fund;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getFund() {
        return fund;
    }

    public String getTicker() {
        return ticker;
    }

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (fund == null || fund.trim().length() == 0)
            errors.add("Fundname is required");

        if (ticker == null || ticker.trim().length() == 0)
            errors.add("Tickername is required");

        if (errors.size() > 0)
            return errors;

        if (!fund.matches("[a-zA-Z0-9 ]+")) {
            return Collections.singletonList("Fund name can only contain letters, numbers and spaces!");

        }
        if (!ticker.matches("[a-zA-Z]+")) {
            return Collections.singletonList("Ticker can only contain letters!");
        }
        if (fund.length() > 20) {
            errors.add("Fund name should be lesser than 20 characters!");
        }
        if (ticker.trim().length() < 1 || ticker.trim().length() > 5)
            errors.add("Ticker Name should be between 1-5 characters");

        if (errors.size() > 0)
            return errors;

        return errors;
    }
}
