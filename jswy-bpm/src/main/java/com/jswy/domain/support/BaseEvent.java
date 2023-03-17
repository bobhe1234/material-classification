package com.jswy.domain.support;

import java.io.Serializable;

/**
 * 数据事件抽象类:包含设置/获取数据对象的泛型方法
 * 
 * @author admin
 *
 * @param <T>
 */
public abstract class BaseEvent<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7345132955939646135L;

	/**
	 * 时间戳
	 */
	private final long timestamp;

	private T value;

	public BaseEvent() {
		this.timestamp = System.currentTimeMillis();
	}

	public BaseEvent(T value) {
		this.value = value;
		this.timestamp = System.currentTimeMillis();
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}
}