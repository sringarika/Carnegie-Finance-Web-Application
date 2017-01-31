package cfs.formbean;

import java.util.ArrayList;
import java.util.List;

//import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;

public class CreateFundForm extends FormBean{
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


	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		//all error validations are done in jsp itself
		if (fund == null || fund.trim().length() == 0) //required field is used for validation henceforth
			errors.add("Fundname is required");
		
		if (ticker == null || ticker.trim().length() == 0)
			errors.add("Tickername is required");
		
    	if(fund.length() > 20) {
    		errors.add("Fund name should be lesser than 20 characters!");
    	}
    	if (ticker.trim().length() < 1 || ticker.trim().length() > 5) 
    		errors.add("Ticker Name should be between 1-5 characters");
        
		if (errors.size() > 0)
			return errors;

		return errors;
	}
}
