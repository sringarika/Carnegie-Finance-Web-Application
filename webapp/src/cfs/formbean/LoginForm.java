package cfs.formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class LoginForm {
	private String username; 
	private String password;
	private String button;

	public LoginForm(HttpServletRequest request) {
		username = request.getParameter("username");
		password = request.getParameter("password");
		button = request.getParameter("Loginbutton");

	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getButton() {
		return button;
	}

	public boolean isPresent() {		
		if(button == null) return false;
		return true;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (username == null || username.trim().length() == 0)
			errors.add("Username is required");
		if (password == null || password.length() == 0)
			errors.add("Password is required");
		if (button == null)
			errors.add("Button is required");

		if (errors.size() > 0)
			return errors;
 
		if (username.matches(".*[<>\"].*"))
			errors.add("Username may not contain angle brackets or quotes");

		return errors;
	}
}
