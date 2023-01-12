package com.jswy.infrastructure.util;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 搜索过滤对象
 * 
 * @author bobhe123@foxmail.com
 * @version 2.0.0
 * @date 2022/05/17 11:23
 * @description
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SearchFilter {

	@ApiModelProperty("查询方式,目前支持:like,in")
	private String opt;

	@ApiModelProperty("查询值,多个值用英文逗号‘,’分割 ")
	private String values;
}
