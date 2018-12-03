package com.ln.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_details")
public class User {

	@Id
	private String id;

	@NotBlank
	private String name;

	@Email
	private String emailId;

	@NotBlank
	@Pattern(regexp = "(^$|[0-9]{10})")
	private String phoneNum;

	@NotBlank
	private String password;

	private Boolean emailAuthenticated;

	private Date createDate = new Date();

	private Date updateDate = new Date();

	private String token;

	private List<String> roles;

	public User() {
		super();
	}

	public User(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.emailId = user.getEmailId();
		this.phoneNum = user.getPhoneNum();
		this.password = user.getPassword();
		this.emailAuthenticated = user.getEmailAuthenticated();
		this.createDate = user.getCreateDate();
		this.updateDate = user.getUpdateDate();
		this.token = user.getToken();
		this.roles = user.getRoles();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getEmailAuthenticated() {
		return emailAuthenticated;
	}

	public void setEmailAuthenticated(Boolean emailAuthenticated) {
		this.emailAuthenticated = emailAuthenticated;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
