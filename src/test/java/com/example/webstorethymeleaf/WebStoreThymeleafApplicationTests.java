package com.example.webstorethymeleaf;

import com.example.webstorethymeleaf.POJO.Customer;
import com.example.webstorethymeleaf.POJO.Item;
import com.example.webstorethymeleaf.POJO.Order;
import com.example.webstorethymeleaf.Repositories.CustomerRepo;
import com.example.webstorethymeleaf.Repositories.ItemRepo;
import com.example.webstorethymeleaf.Repositories.OrderRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
	@MockBean
	private OrderRepo orderRepo;


	@Test
	public void testGetAllCustomer() throws Exception {
		// Prepare mock data
		List<Customer> customers = new ArrayList<>();
		Customer customer1 = new Customer("John Doe", "000000-0000");
		Customer customer2 = new Customer("Jane Doe", "111111-1111");
		customers.add(customer1);
		customers.add(customer2);


		when(customerRepo.findAll()).thenReturn(customers);


		mockMvc.perform(get("/customers"))
				.andExpect(status().isOk())
				.andExpect(view().name("customers.html"))
				.andExpect(model().attribute("customers", customers));
	}

	@Test
	public void testGetCustomerById() throws Exception {

		TestCustomer customer = new TestCustomer();
		customer.setId(1L);
		customer.setName("John Doe");
		customer.setSsn("000000-0000");

		when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));


		mockMvc.perform(get("/customers/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("customer.html"))
				.andExpect(model().attribute("customer", customer));
	}

	@Test
	public void deleteCustomerById() throws Exception {

		List<Customer> customers = new ArrayList<>();

		Customer customer1 = new Customer();
		customer1.setId(1L);
		customer1.setName("John Doe");
		customer1.setSsn("000000-0000");

		Customer customer2 = new Customer();
		customer2.setId(2L);
		customer2.setName("Jane Doe");
		customer2.setSsn("111111-1111");

		customers.add(customer1);
		customers.add(customer2);


		when(customerRepo.findById(2L)).thenReturn(Optional.of(customer2));
		when(customerRepo.findAll()).thenReturn(customers);


		mockMvc.perform(post("/customers/2/delete"))
				.andExpect(status().isOk())
				.andExpect(view().name("delete.html"))
				.andExpect(model().attribute("customer", customer2));


		verify(customerRepo).deleteById(2L);

		Optional<Customer> deletedCustomer = customerRepo.findById(1L);
		assertThat(deletedCustomer).isEmpty();

	}

	@Test
	public void testUpdateCustomer() throws Exception {
		TestCustomer customer = new TestCustomer();
		customer.setId(1L);
		customer.setName("John Doe");
		customer.setSsn("000000-0000");

		when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));

		mockMvc.perform(post("/customers/1/update")
						.param("name", "John Doe")
						.param("ssn", "000000-0000"))
				.andExpect(status().isOk())
				.andExpect(view().name("updateCustomer.html"));
	}

	@Test
	public void testUpdateCustomerForm() throws Exception {
		TestCustomer customer = new TestCustomer();
		customer.setId(1L);
		customer.setName("John Doe");
		customer.setSsn("000000-0000");

		when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));

		mockMvc.perform(post("/customers/1/update/form")
						.param("name", "John Doe")
						.param("ssn", "000000-0000"))
				.andExpect(status().isOk())
				.andExpect(view().name("updateCustomerForm.html"));


	}

	@Test
	public void testUpdateCustomerFormExecute() throws Exception {
		// create a test customer
		TestCustomer testCustomer = new TestCustomer();
		testCustomer.setId(1L);
		testCustomer.setName("John Doe");
		testCustomer.setSsn("000000-0000");

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> formData = new HashMap<>();
		formData.put("name", "Jane Doe");
		formData.put("ssn", "111111-1111");
		formData.put("id", "1");

		String json = objectMapper.writeValueAsString(formData);

		when(customerRepo.findById(anyLong())).thenReturn(Optional.of(testCustomer));
		when(customerRepo.save(testCustomer)).thenReturn(testCustomer);

		System.out.println(json);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/customers/1/update/form/execute")
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		assertThat(testCustomer.getName()).isEqualTo("Jane Doe");
		assertThat(testCustomer.getSsn()).isEqualTo("111111-1111");

	}

	@Test
	public void testAddCustomer() throws Exception {

		Customer newCustomer = new Customer("John Doe", "222222-2222");

		when(customerRepo.save(newCustomer)).thenReturn(newCustomer);

		mockMvc.perform(post("/customers/sd")
						.param("name", "John Doe")
						.param("ssn", "222222-2222"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/customers"));

		verify(customerRepo).save(newCustomer);
	}
	/*CONTROLLER TESTING CUSTOMERS*/

	@Test
	public void testAddItem() throws Exception {

		Item newItem = new Item("Example Item", 50.00);

		when(itemRepo.save(newItem)).thenReturn(newItem);

		mockMvc.perform(post("/items/sd")
						.param("name", "Example Item")
						.param("price", "50.00"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/items"));

		verify(itemRepo).save(newItem);
	}

	@Test
	public void deleteItemById() throws Exception {

		List<Item> items = new ArrayList<>();

		Item item1 = new Item("Bäverpäls", 1000);
		item1.setId(1L);

		Item item2 = new Item("Sälpäls", 500);
		item2.setId(2L);

		items.add(item1);
		items.add(item2);

		when(itemRepo.findById(2L)).thenReturn(Optional.of(item2));
		when(itemRepo.findAll()).thenReturn(items);

		mockMvc.perform(get("/items/2/delete"))
				.andExpect(status().isOk())
				.andExpect(view().name("deleteItem.html"))
				.andExpect(model().attribute("item", item2));

		verify(itemRepo).deleteById(2L);
	}

	@Test
	public void testGetAllItem() throws Exception {

		List<Item> items = new ArrayList<>();
		Item item1 = new Item("Björnpäls", 1000.00);
		Item item2 = new Item("Pingvinpäls", 1000.00);
		items.add(item1);
		items.add(item2);

		when(itemRepo.findAll()).thenReturn(items);

		mockMvc.perform(get("/items"))
				.andExpect(status().isOk())
				.andExpect(view().name("items.html"))
				.andExpect(model().attribute("items", items));
	}

	@Test
	public void testGetItemById() throws Exception {

		TestItem item = new TestItem();
		item.setId(1L);
		item.setName("Kamelpäls");
		item.setPrice(999.00);

		when(itemRepo.findById(1L)).thenReturn(Optional.of(item));

		mockMvc.perform(get("/items/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("item.html")) // Change "items.html" to "item.html"
				.andExpect(model().attribute("item", item));
	}

	@Test
	public void testUpdateItemFormExecute() throws Exception {

		TestItem testItem = new TestItem();
		testItem.setId(1L);
		testItem.setName("Vimpel");
		testItem.setPrice(100);

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> formData = new HashMap<>();
		formData.put("name", "Flagga");
		formData.put("price", "200");
		formData.put("id", "1");

		String json = objectMapper.writeValueAsString(formData);

		when(itemRepo.findById(anyLong())).thenReturn(Optional.of(testItem));
		when(itemRepo.save(testItem)).thenReturn(testItem);

		System.out.println(json);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/items/1/update/form/execute")
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		assertThat(testItem.getName()).isEqualTo("Flagga");
		assertThat(testItem.getPrice()).isEqualTo(200);

	}

	@Test
	public void testGetAllOrders() throws Exception {

		List<Order> orders = new ArrayList<>();
		Order order1 = new Order(1L, LocalDate.now(), new Customer("John Doe", "222222-2222"), Arrays.asList(new Item("Björnpäls", 1000.00)));
		Order order2 = new Order(2L, LocalDate.now(), new Customer("Jane Doe ", "111111-1111"), Arrays.asList(new Item("Sälpäls", 500.00)));
		orders.add(order1);
		orders.add(order2);

		when(orderRepo.findAll()).thenReturn(orders);

		mockMvc.perform(get("/orders"))
				.andExpect(status().isOk())
				.andExpect(view().name("orders.html"))
				.andExpect(model().attribute("orders", orders));
	}
	@Test
	public void testGetOrderById() throws Exception {

		TestCustomer customer = new TestCustomer();
		customer.setName("John Doe");
		customer.setSsn("000000-0000");
		customer.setId(1L);

		Item item = new Item("Sälpäls", 500.00);
		item.setId(1L);

		List<Item> items = new ArrayList<>();
		items.add(item);

		Order order = new Order(1L, LocalDate.now(), customer, items);

		when(customerRepo.save(customer)).thenReturn(customer);
		when(itemRepo.save(item)).thenReturn(item);
		when(orderRepo.findById(1L)).thenReturn(Optional.of(order));

		mockMvc.perform(get("/orders/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("order.html"))
				.andExpect(model().attribute("order", order));
	}
	@Test
	public void testCreateNewOrderByCustomer() throws Exception {

		Long customerId = 1L;
		Customer customer = new Customer(customerId, "Jane Doe", "1111111-1111");

		Item item1 = new Item(1L, "Item1", 100.00);
		Item item2 = new Item(2L, "Item2", 200.00);
		List<Item> items = Arrays.asList(item1, item2);

		when(customerRepo.findById(customerId)).thenReturn(Optional.of(customer));
		when(itemRepo.findAll()).thenReturn(items);

		mockMvc.perform(post("/orders/" + customerId + "/create"))
				.andExpect(status().isOk())
				.andExpect(view().name("placeOrderByCustomerId"))
				.andExpect(model().attribute("customer", customer))
				.andExpect(model().attribute("items", items));
	}
/*	@Test
	public void testBuy() throws Exception {
		Long customerId = 1L;
		Customer customer = new Customer(customerId, "Jane Doe", "1111111-1111");

		Item item1 = new Item(1L, "Item1", 100.00);
		Item item2 = new Item(2L, "Item2", 200.00);
		List<Item> items = Arrays.asList(item1, item2);

		when(customerRepo.findById(customerId)).thenReturn(Optional.of(customer));
		when(itemRepo.findAll()).thenReturn(items);

		ObjectMapper objectMapper = new ObjectMapper();
		String itemsJson = objectMapper.writeValueAsString(items);

		mockMvc.perform(post("/orders/buy/" + customerId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(itemsJson));

		List<Order> order = orderRepo.findAll();

		Optional<Order> orders = orderRepo.findById(1L);
		List<Optional> orders1 = new ArrayList<>();
		orders1.add(orders);
		assertThat(orders1.size()).isEqualTo(1);
		}*/
}
   /*CATEGORY CONTROLLER TESTING*/






