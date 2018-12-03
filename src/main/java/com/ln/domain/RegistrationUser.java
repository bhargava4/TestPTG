package com.ln.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class RegistrationUser {

	@NotBlank
	private String name;

	@Email
	private String emailId;

	@NotBlank
	@Pattern(regexp = "(^$|[0-9]{10})")
	private String phoneNum;

	@NotBlank
	private String password;

	private boolean authenticated = false;

	private boolean approver = false;

	@NotBlank
	private String recaptchaResp;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public boolean isApprover() {
		return approver;
	}

	public void setApprover(boolean approver) {
		this.approver = approver;
	}

	public String getRecaptchaResp() {
		return recaptchaResp;
	}

	public void setRecaptchaResp(String recaptchaResp) {
		this.recaptchaResp = recaptchaResp;
	}

}
