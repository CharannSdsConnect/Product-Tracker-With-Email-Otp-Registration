package com.charann.producttrackerapi.entity;

import java.util.Date;

import lombok.Data;

@Data
public class ErrorObject {
	
	private Integer statusCode;
	
	private String message;
	
	private Date timestamp;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
