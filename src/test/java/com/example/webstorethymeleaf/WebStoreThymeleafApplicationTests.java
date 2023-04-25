package com.example.webstorethymeleaf;

import com.example.webstorethymeleaf.POJO.Customer;
import com.example.webstorethymeleaf.Repositories.CustomerRepo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
class WebStoreThymeleafApplicationTests {

	@Test
	void contextLoads() {
	}

	/*CUSTOMER CONTROLLER TESTING*/

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerRepo customerRepo;

	@Test
	public void testGetAllCustomer() throws Exception {
		// Prepare mock data
		List<Customer> customers = new ArrayList<>();
		Customer customer1 = new Customer("John Doe", "000000-0000");
		Customer customer2 = new Customer("Jane Doe", "111111-1111");
		customers.add(customer1);
		customers.add(customer2);

		// Set up mock behavior
		when(customerRepo.findAll()).thenReturn(customers);

		// Perform the request
		mockMvc.perform(get("/customers"))
				.andExpect(status().isOk())
				.andExpect(view().name("customers.html"))
				.andExpect(model().attribute("customers", customers));
	}

	@Test
	public void testGetCustomerById() throws Exception {
		// Prepare mock data
		TestCustomer customer = new TestCustomer();
		customer.setId(1L);
		customer.setName("John Doe");
		customer.setSsn("000000-0000");
		// Set up mock behavior
		when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));

		// Perform the request
		mockMvc.perform(get("/customers/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("customer.html"))
				.andExpect(model().attribute("customer", customer));
	}

	@Test
	public void  deleteCustomerById() throws Exception {
		// Prepare mock data
		List<Customer> customers = new ArrayList<>();

		TestCustomer customer1 = new TestCustomer();
		customer1.setId(1L);
		customer1.setName("John Doe");
		customer1.setSsn("000000-0000");

		TestCustomer customer2 = new TestCustomer();
		customer2.setId(2L);
		customer2.setName("Jane Doe");
		customer2.setSsn("111111-1111");

		customers.add(customer1);
		customers.add(customer2);

		// Set up mock behavior
		when(customerRepo.findById(2L)).thenReturn(Optional.of(customer2));
		when(customerRepo.findAll()).thenReturn(customers);

		// Perform the request to the controller
		mockMvc.perform(post("/customers/2/delete"))
				.andExpect(status().isOk())
				.andExpect(view().name("delete.html"))
				.andExpect(model().attribute("customer", customer2));

		// Verify that deleteById was called with the correct ID
		verify(customerRepo).deleteById(2L);
	}
	/*CATEGORY CONTROLLER TESTING*/
}

