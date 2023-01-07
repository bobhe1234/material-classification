package com.jswy.infrastructure.general.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页查询请求参数
 * 
 * @author bobhe123@foxmail.com
 * @version 2.0.0
 * @date 2022/05/17 09:48
 * @description
 */
@Data
public class PageParam<T> implements Serializable {

	@ApiModelProperty("分页查询对象,主要用于精确查询")
	private T vo;

	@ApiModelProperty("页码,如果不传默认1")
	@JsonProperty("page_index")
	private Integer pageIndex = 1;

	@ApiModelProperty("页数,如果不传默认10")
	@JsonProperty("page_size")
	private Integer pageSize = 10;

	@ApiModelProperty("排序方式,只支持asc和desc. eg: \"create_date\":\"desc/asc\" ")
	private Map<String, String> sorts = new HashMap<>();

	@JsonProperty("search_date_map")
	@ApiModelProperty("Date类型日期段查询,eg createDate:[startDate,endDate]")
	private Map<String, List<String>> searchDateMap = new HashMap<>();

	@JsonProperty("search_local_time_date_map")
	@ApiModelProperty("LocalTimeDate类型日期段查询, eg createDate:[startDate,endDate]")
	private Map<String, List<String>> searchLocalTimeDateMap = new HashMap<>();

	@JsonProperty("search_map")
	@ApiModelProperty("查询map, eg id:{in:1,2,3}")
	private Map<String, SearchFilter> searchMap = new HashMap<>();

}