package com.jswy.interfaces.assembler;

public class DataResponse<S> {
	// COMMON_ACTION_SUCCESS(),
	// COMMON_ACTION_EXCEPTION(500, "System Inner Error!"),
	// COMMON_ACTION_UNAUTHORIZED(401, "Please Login First!");
	public S data;
	private int statusCode;
	private String statusMessage;

	public DataResponse() {
	}

	public DataResponse(S data) {
		this.statusCode = 200;
		this.statusMessage = "success";
		this.data = data;
	}

	public DataResponse(int code, String message) {
		this.statusCode = code;
		this.statusMessage = message;
	}

	public DataResponse(int code, S data, String message) {
		this.statusCode = code;
		this.data = data;
		this.statusMessage = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public S getData() {
		return data;
	}

	public void setData(S data) {
		this.data = data;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

}
