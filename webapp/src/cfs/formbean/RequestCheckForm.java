package cfs.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class RequestCheckForm extends FormBean{
	private String requestAmount;
	public double getRequestAmount() {
		try {
			return Double.parseDouble(requestAmount);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	public void setRequestAmount(String s ) {
		this.requestAmount = s;
	}
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if(requestAmount == null || requestAmount.length() == 0) {
			errors.add("Please enter the amount you want to withdraw.");
		}
		try {
			Double.parseDouble(requestAmount);
		} catch (NumberFormatException e) {
			errors.add("The amount should be numbers.");
		}
		return errors;
	}
}
