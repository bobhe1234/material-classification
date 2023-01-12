package com.jswy.interfaces.assembler;

import java.util.List;

public class ListResponse<T> extends Response {
	private int count = 0;
	private int pageSize = 20;
	private int pageNo = 1;
	private List<T> data;
}