package com.example.webstorethymeleaf;

import com.example.webstorethymeleaf.POJO.Customer;
import com.example.webstorethymeleaf.POJO.Item;
import com.example.webstorethymeleaf.Repositories.CustomerRepo;
import com.example.webstorethymeleaf.Repositories.ItemRepo;
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

	@MockBean
	private ItemRepo itemRepo;


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
	@Test
	public void testAddCustomer() throws Exception {
		// Prepare mock data
		Customer newCustomer = new Customer("John Doe", "222222-2222");

		// Set up mock behavior
		when(customerRepo.save(newCustomer)).thenReturn(newCustomer);

		// Perform the request to the controller
		// Perform the request to the controller
		mockMvc.perform(post("/customers/sd")
						.param("name", "John Doe")
						.param("ssn", "222222-2222"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/customers"));

		// Verify that save method was called with the correct customer object
		verify(customerRepo).save(newCustomer);
	}  /*CONTROLLER TESTING CUSTOMERS*/

	@Test
	public void testAddItem() throws Exception {
		// Prepare mock data
		Item newItem = new Item("Example Item", 50.00);

		// Set up mock behavior
		when(itemRepo.save(newItem)).thenReturn(newItem);

		// Perform the request to the controller
		mockMvc.perform(post("/items/sd")
						.param("name", "Example Item")
						.param("price", "50.00"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/items"));

		// Verify that save method was called with the correct item object
		verify(itemRepo).save(newItem);
	}

	@Test
	public void deleteItemById() throws Exception {
		// Prepare mock data
		List<Item> items = new ArrayList<>();

		Item item1 = new Item("Bäverpäls", 1000);
		item1.setId(1L);

		Item item2 = new Item("Sälpäls", 500);
		item2.setId(2L);

		items.add(item1);
		items.add(item2);

		// Set up mock behavior
		when(itemRepo.findById(2L)).thenReturn(Optional.of(item2));
		when(itemRepo.findAll()).thenReturn(items);

		// Perform the request to the controller
		mockMvc.perform(get("/items/2/delete"))
				.andExpect(status().isOk())
				.andExpect(view().name("deleteItem.html"))
				.andExpect(model().attribute("item", item2));

		// Verify that deleteById was called with the correct ID
		verify(itemRepo).deleteById(2L);
	}
	@Test
	public void testGetAllItem() throws Exception {
		// Prepare mock data
		List<Item> items = new ArrayList<>();
		Item item1 = new Item("Björnpäls", 1000.00);
		Item item2 = new Item("Pingvinpäls", 1000.00);
		items.add(item1);
		items.add(item2);

		// Set up mock behavior
		when(itemRepo.findAll()).thenReturn(items);

		// Perform the request
		mockMvc.perform(get("/items"))
				.andExpect(status().isOk())
				.andExpect(view().name("items.html"))
				.andExpect(model().attribute("items", items));
	}
	@Test
	public void testGetItemById() throws Exception {
		// Prepare mock data
		TestItem item = new TestItem();
		item.setId(1L);
		item.setName("Kamelpäls");
		item.setPrice(999.00);

		// Set up mock behavior
		when(itemRepo.findById(1L)).thenReturn(Optional.of(item));

		// Perform the request
		mockMvc.perform(get("/items/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("item.html")) // Change "items.html" to "item.html"
				.andExpect(model().attribute("item", item));
	}

}
   /*CATEGORY CONTROLLER TESTING*/






