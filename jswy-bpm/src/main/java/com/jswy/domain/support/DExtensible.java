package com.jswy.domain.support;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 所有参数请求DTO的父类,子类继承即可,无需每个实现
 * 
 * @author admin
 *
 */
@Data
public class DExtensible {

	/**
	 * 扩展属性
	 */
	@JsonIgnore
	private Map<String, String> extended;

	/**
	 * 向扩展信息中添加属性值,如果key存在覆盖,key/value为空则忽略
	 * 
	 * @param key
	 * @param value
	 */
	@JsonAnySetter
	public void setExtended(String key, String value) {
		if (extended == null) {
			extended = new HashMap<>();
		}
		if (!StringUtils.hasLength(key) || !StringUtils.hasLength(value)) {
			return;
		}
		extended.put(key, value);
	}

	/**
	 * @return the extended
	 */
	public Map<String, String> getExtended() {
		return extended;
	}

	/**
	 * @param extended the extended to set
	 */
	public void setExtended(Map<String, String> extended) {
		this.extended = extended;
	}
}
