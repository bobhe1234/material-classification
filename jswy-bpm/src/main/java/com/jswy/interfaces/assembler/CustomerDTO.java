package com.jswy.interfaces.assembler;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.jswy.domain.generic.demo.model.Customer;
import com.jswy.domain.generic.demo.model.CustomerId;

public class CustomerDTO {
	private Integer id;
	private CustomerId customer_id;
	private String name;
	private String phone;
	private String email;
	private String description;

	public Customer toEntity() throws Exception {
		Customer customer = new Customer();
		customer.setId(getId());
		customer.setCustomer_id(getCustomer_id());

		customer.setName(name);
		customer.setPhone(phone);
		customer.setEmail(email);
		customer.setCreate_time(new Timestamp(System.currentTimeMillis()));
		customer.setModify_time(new Timestamp(System.currentTimeMillis()));
		customer.setDescription(description);
		return customer;
	}

	public static CustomerDTO toDto(Customer customer) {
		CustomerDTO dto = new CustomerDTO();
		dto.setName(customer.getName());
		dto.setPhone(customer.getPhone());
		dto.setEmail(customer.getEmail());
		customer.setCreate_time(customer.getCreate_time());
		customer.setModify_time(customer.getModify_time());
		dto.setDescription(customer.getDescription());
		return dto;
	}

	public static List<CustomerDTO> toList(List<Customer> customers) {
		List<CustomerDTO> dtoList = new ArrayList<CustomerDTO>();
		for (Customer customer : customers) {
			dtoList.add(toDto(customer));
		}
		return dtoList;
	}

	/**
	 * @return the customer_id
	 */
	public CustomerId getCustomer_id() {
		return customer_id;
	}

	/**
	 * @param customer_id the customer_id to set
	 */
	public void setCustomer_id(CustomerId customer_id) {
		this.customer_id = customer_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
