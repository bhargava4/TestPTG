package com.ln.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class LoginUser {

	private String userId;

	private String name;

	private String emailId;

	private String phoneNum;

	private Boolean emailAuthenticated;

	private List<String> roles = new ArrayList<>();

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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Boolean getEmailAuthenticated() {
		return emailAuthenticated;
	}

	public void setEmailAuthenticated(Boolean emailAuthenticated) {
		this.emailAuthenticated = emailAuthenticated;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
