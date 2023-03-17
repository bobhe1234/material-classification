package com.jswy.domain.generic.demo.exception;

import lombok.Data;

/**
 * 自定义异常
 */
@Data
public class BaseException extends RuntimeException {
	private static final long serialVersionUID = -5573919961663001854L;
	private Integer code;
	private String message;

	public BaseException(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}