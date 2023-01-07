package com.jswy.interfaces.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jswy.application.demo.TradeManager;
import com.jswy.domain.generic.demo.repository.TradeRepository;
import com.jswy.interfaces.assembler.TradeDTO;

@RequestMapping("/trade")
@RestController
public class TradeController {

	@Autowired
	private TradeManager tradeManager;

	@Autowired
	private TradeRepository tradeRepository;

	@PostMapping(path = "/recharge")
	public TradeDTO recharge(@RequestBody TradeDTO tradeDTO) {
		return TradeDTO.toDto(tradeManager.recharge(tradeDTO.toEntity()));
	}

	@PostMapping(path = "/consume")
	public TradeDTO consume(@RequestBody TradeDTO tradeDTO) {
		return TradeDTO.toDto(tradeManager.consume(tradeDTO.toEntity()));
	}

	@GetMapping(path = "/{tradeNumber}")
	public TradeDTO findByTradeNumber(@PathVariable("tradeNumber") String tradeNumber) {
		return TradeDTO.toDto(tradeRepository.findByTradeNumber(tradeNumber));
	}

}