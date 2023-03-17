package com.jswy.interfaces.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jswy.application.service.CustomerService;
import com.jswy.domain.generic.demo.model.Customer;
import com.jswy.interfaces.assembler.CustomerDTO;
import com.jswy.interfaces.assembler.DataResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "客户管理")
@RestController
@RequestMapping("/customer")
public class CustomerController {

	private static Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
	@Autowired
	private CustomerService customerService;

	/**
	 * 从URI中取page/size参数值
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@ApiOperation(response = DataResponse.class, value = "客户列表", notes = "提交一组识别的标签id，返回生成的客户详情")
	@GetMapping(value = "/page{page}-size{size}")
	@ApiResponses({
			@ApiResponse(code = 400, message = "If one of the URL or query parameters is not in the correct format."),
			@ApiResponse(code = 404, message = "If the specified objects does not exist."),
			@ApiResponse(code = 412, message = "If the CSRF nonce is not provided."),
			@ApiResponse(code = 500, message = "If an unexpected error occurs.") })
	public DataResponse list(@PathVariable(required = false, value = "page") Integer page,
			@PathVariable(required = false, value = "size") Integer size) {
		LOGGER.debug("list -->Beg");
		DataResponse reps = new DataResponse();
		try {
			Page<Customer> pageCustomers = customerService.listByPage(page, size);
			LOGGER.debug("list customer-->pageCustomers:" + pageCustomers);
			if (pageCustomers != null) {
				reps.setData(pageCustomers);
				reps.setStatusMessage("Success");
				reps.setStatusCode(200);
			} else {
				reps.setStatusMessage("Action [list] Failed: " + pageCustomers);
				reps.setStatusCode(500);
			}
		} catch (Exception e) {
			LOGGER.error("Error{}", e);
			reps.setStatusMessage("Error:" + e.getMessage());
			reps.setStatusCode(500);
			reps.setData(null);
		}
		LOGGER.debug("list -->End");
		return reps;
	}

	@ApiOperation(response = DataResponse.class, value = "查询客户", notes = "提交客户编号，返回生成的客户详情")
	@GetMapping(value = "/{name}")
	@ApiResponses({
			@ApiResponse(code = 400, message = "If one of the URL or query parameters is not in the correct format."),
			@ApiResponse(code = 404, message = "If the specified objects does not exist."),
			@ApiResponse(code = 412, message = "If the CSRF nonce is not provided."),
			@ApiResponse(code = 500, message = "If an unexpected error occurs.") })
	public DataResponse findByName(@PathVariable("name") String name) {
		LOGGER.debug("findByName-->Beg name:" + name);
		DataResponse reps = new DataResponse();
		try {
			List<Customer> customers = customerService.findByName(name);
			LOGGER.debug("findByName customer-->customers:" + customers);
			List<CustomerDTO> dtos = CustomerDTO.toList(customers);
			if (dtos != null) {
				reps.setData(dtos);
				reps.setStatusMessage("Success");
				reps.setStatusCode(200);
			} else {
				reps.setStatusMessage("Action [findBy] Failed: " + dtos);
				reps.setStatusCode(500);
			}
		} catch (Exception e) {
			LOGGER.error("Error{}", e);
			reps.setStatusMessage("Error:" + e.getMessage());
			reps.setStatusCode(500);
			reps.setData(null);
		}
		LOGGER.debug("findByName-->End");
		return reps;
	}

	@ApiOperation(response = DataResponse.class, value = "提交客户", notes = "提交客户数据，生成客户,更新客户")
	@PostMapping(value = "/")
	@ApiResponses({
			@ApiResponse(code = 400, message = "If one of the URL or query parameters is not in the correct format."),
			@ApiResponse(code = 404, message = "If the specified objects does not exist."),
			@ApiResponse(code = 412, message = "If the CSRF nonce is not provided."),
			@ApiResponse(code = 500, message = "If an unexpected error occurs.") })
	public DataResponse save(CustomerDTO dto) {
		LOGGER.debug("save customer-->Beg dto:");
		DataResponse reps = new DataResponse();
		try {
			Customer customer = dto.toEntity();
			LOGGER.debug("save customer-->customer:" + customer);
			if (customer != null) {
				customerService.saveOrUpdate(customer);
				reps.setData(customer);
				reps.setStatusMessage("Success");
				reps.setStatusCode(200);
			} else {
				reps.setStatusMessage("Action [save] Failed: " + dto);
				reps.setStatusCode(500);
			}
		} catch (Exception e) {
			LOGGER.error("Error{}", e);
			reps.setStatusMessage("Error:" + e.getMessage());
			reps.setStatusCode(500);
			reps.setData(null);
		}
		LOGGER.debug("save customer-->End");
		return reps;
	}

	@ApiOperation(response = DataResponse.class, value = "更新客户", notes = "已有客户,更新数据")
	@PutMapping(value = "/")
	@ApiResponses({
			@ApiResponse(code = 400, message = "If one of the URL or query parameters is not in the correct format."),
			@ApiResponse(code = 404, message = "If the specified objects does not exist."),
			@ApiResponse(code = 412, message = "If the CSRF nonce is not provided."),
			@ApiResponse(code = 500, message = "If an unexpected error occurs.") })
	public DataResponse updateNameById(Integer id, String updateName) {
		LOGGER.debug("save customer-->Beg dto:");
		DataResponse reps = new DataResponse();
		try {
			LOGGER.debug("save customer-->customer:" + id);
			Optional<Customer> opts = customerService.updateNameById(id, updateName);
			Customer customer = opts.get();
			CustomerDTO dto = null;
			if (customer != null) {
				dto = new CustomerDTO().toDto(customer);
			}
			if (dto != null) {
				reps.setData(dto);
				reps.setStatusMessage("Success");
				reps.setStatusCode(200);
			} else {
				reps.setStatusMessage("Action [save] Failed: " + id);
				reps.setStatusCode(500);
			}
		} catch (Exception e) {
			LOGGER.error("Error{}", e);
			reps.setStatusMessage("Error:" + e.getMessage());
			reps.setStatusCode(500);
			reps.setData(null);
		}
		LOGGER.debug("save customer-->End");
		return reps;
	}

	@ApiOperation(response = DataResponse.class, value = "删除客户", notes = "提交客户id，删除客户")
	@DeleteMapping(value = "/")
	@ApiResponses({
			@ApiResponse(code = 400, message = "If one of the URL or query parameters is not in the correct format."),
			@ApiResponse(code = 404, message = "If the specified objects does not exist."),
			@ApiResponse(code = 412, message = "If the CSRF nonce is not provided."),
			@ApiResponse(code = 500, message = "If an unexpected error occurs.") })
	public DataResponse<String> delete(CustomerDTO dto) {
		LOGGER.debug("delete customer-->Beg dto:" + dto);
		DataResponse reps = new DataResponse();
		try {
			Customer customer = dto.toEntity();
			LOGGER.debug("delete customer-->customer:" + customer);
			if (customer != null) {
				customerService.delete(customer);
				reps.setData(customer);
				reps.setStatusMessage("Success");
				reps.setStatusCode(200);
			} else {
				reps.setStatusMessage("Action [save] Failed: " + dto);
				reps.setStatusCode(500);
			}
		} catch (Exception e) {
			LOGGER.error("Error{}", e);
			reps.setStatusMessage("Error:" + e.getMessage());
			reps.setStatusCode(500);
			reps.setData(null);
		}
		LOGGER.debug("delete customer-->End");
		return reps;
	}

}