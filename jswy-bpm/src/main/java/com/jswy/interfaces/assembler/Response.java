package com.jswy.interfaces.assembler;

import java.io.Serializable;
import java.util.Map;

public class Response implements Serializable {
	private boolean success;
	private String code;
	private String msg;
	private Map<String, Object> errData;
}