package com.cloudzon.huddle.dto;

public class LoginResponse {

	String userName;

	public LoginResponse(String userName) {
		super();
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
