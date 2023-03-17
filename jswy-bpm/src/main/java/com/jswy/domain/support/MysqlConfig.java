package com.jswy.domain.support;

import org.hibernate.dialect.MySQL57Dialect;

/**
 * 设置自动建表为utf8
 * @author admin
 *
 */
public class MysqlConfig extends MySQL57Dialect {
	@Override
	public String getTableTypeString() {
		return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
	}
}