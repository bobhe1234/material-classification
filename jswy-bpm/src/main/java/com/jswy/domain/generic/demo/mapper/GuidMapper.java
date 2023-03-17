package com.jswy.domain.generic.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;

import lombok.Data;

@Mapper
public interface GuidMapper {

	/**
	 * 获取全局唯一ID
	 * 
	 * @return
	 */
	// replace into afs_guid(stub) values('a');
	// select last_insert_id();
	@Insert("REPLACE INTO guid (stub) VALUES('a')")
	@SelectKey(statement = {
			"SELECT LAST_INSERT_ID()" }, keyProperty = "guidHolder.id", before = false, resultType = long.class)
	int getGuid(@Param("guidHolder") GuidHolder guidHolder);

	public static void main(String[] args) {
//		GuidMapper.GuidHolder guidHolder = new GuidMapper.GuidHolder();
//		int i = guidMapper.getGuid(guidHolder);
//		long guid = guidHolder.getId();
		// guid 就是返回的ID
	}

	@Data
	public static class GuidHolder {
		private long id;
		private String stub;
	}
}
