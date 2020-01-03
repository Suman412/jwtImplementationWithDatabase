package com.suman.project1.common;

public class CustomSuccessResponse {
	private String message;
	private int httpStatus;
	private Object userData;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
	public Object getUserData() {
		return userData;
	}
	public void setUserData(Object userData) {
		this.userData = userData;
	}
	public CustomSuccessResponse(int httpStatus, String message,Object userData) {
		super();
		this.message=message;
		this.httpStatus = httpStatus;
		this.userData = userData;
	}
}
