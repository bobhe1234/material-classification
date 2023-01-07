package com.jswy.domain.support;

/**
 * 可扩展标识符接口：可标志为ID序列，比如：EntityID就表示为Entity对应的序列ID类
 * 
 * @author admin
 *
 * @param <ID>
 */
public interface Identifiable<ID extends Identifier> {
	ID getId();
}