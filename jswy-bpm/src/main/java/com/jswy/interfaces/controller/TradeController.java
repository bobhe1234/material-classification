package com.jswy.interfaces.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jswy.domain.generic.demo.repository.TradeRepository;
import com.jswy.interfaces.assembler.TradeDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "交易管理")
@RestController
@RequestMapping("/trade")
public class TradeController {

	@Autowired
	private TradeRepository tradeRepository;

	@ApiOperation(value = "[PC端]提交订单", notes = "提交一组识别的标签id，返回生成的订单详情")
	@RequestMapping(value = "/{tradeNumber}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@GetMapping(path = "/{tradeNumber}")
	public TradeDTO findByTradeNumber(@PathVariable("tradeNumber") String tradeNumber) {
		return TradeDTO.toDto(tradeRepository.findByTradeNumber(tradeNumber));
	}

}