package com.example.webstorethymeleaf;

import com.example.webstorethymeleaf.POJO.Customer;
import com.example.webstorethymeleaf.POJO.Item;
import com.example.webstorethymeleaf.POJO.Order;
import com.example.webstorethymeleaf.Repositories.CustomerRepo;
import com.example.webstorethymeleaf.Repositories.ItemRepo;
import com.example.webstorethymeleaf.Repositories.OrderRepo;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


@SpringBootApplication
public class WebStoreThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebStoreThymeleafApplication.class, args);
	}

	@Bean
	@Transactional
	public CommandLineRunner initializeDatabase(CustomerRepo customerRepo, ItemRepo itemRepo, OrderRepo orderRepo){
		return args -> {
			itemRepo.deleteAll();
			orderRepo.deleteAll();
			customerRepo.deleteAll();
		};
	}
	@Bean
	public CommandLineRunner customers(CustomerRepo customerRepo){
		return (args) ->{
			customerRepo.save(new Customer("Johan Andersson", "890214-1234"));
			customerRepo.save(new Customer("Sofia Lundqvist", "810730-5678"));
			customerRepo.save(new Customer("Marcus Bergström", "950815-9012"));
			customerRepo.save(new Customer("Emma Eriksson", "880601-2345"));
			customerRepo.save(new Customer("Alexander Dahlberg", "930505-6789"));
			customerRepo.save(new Customer("Hanna Lindqvist", "920423-0123"));
			customerRepo.save(new Customer("Erik Nilsson", "960101-3456"));
			customerRepo.save(new Customer("Malin Jönsson", "870727-7890"));
			customerRepo.save(new Customer("Andreas Gustavsson", "940307-1235"));
			customerRepo.save(new Customer("Amanda Karlsson", "900412-6781"));
			customerRepo.save(new Customer("Isabella Svensson", "910610-9012"));
			customerRepo.save(new Customer("Simon Johansson", "980211-2345"));
			customerRepo.save(new Customer("Maria Åberg", "860823-5678"));
			customerRepo.save(new Customer("David Persson", "960924-1234"));
			customerRepo.save(new Customer("Linnea Berglund", "920328-7890"));
			customerRepo.save(new Customer("Oscar Lundin", "930827-0123"));
			customerRepo.save(new Customer("Josefine Holm", "900214-5678"));
			customerRepo.save(new Customer("Emil Pettersson", "890607-2345"));
			customerRepo.save(new Customer("Alice Lundberg", "920915-7890"));
			customerRepo.save(new Customer("Victor Eklund", "950413-0123"));

		};
	}
	@Bean
	public CommandLineRunner items (ItemRepo itemRepo){
		return (args) ->{
			itemRepo.save(new Item("Zebrapäls", 500.99));
			itemRepo.save(new Item("Rävpälsjacka", 2500.00));
			itemRepo.save(new Item("Minkpäls", 3000.00));
			itemRepo.save(new Item("Kaninpäls", 1200.00));
			itemRepo.save(new Item("Grävlingspäls", 2200.00));
			itemRepo.save(new Item("Rådjurspäls", 1800.00));
			itemRepo.save(new Item("Vargpäls", 4000.00));
			itemRepo.save(new Item("Fårskinnspäls", 1500.00));
			itemRepo.save(new Item("Bisamråttpäls", 2800.00));
			itemRepo.save(new Item("Hjortskinnspäls", 2200.00));
			itemRepo.save(new Item("Sälpäls", 3500.00));
			itemRepo.save(new Item("Mårhundspäls", 2400.00));
			itemRepo.save(new Item("Fjällrävspäls", 4200.00));
			itemRepo.save(new Item("Ozelotpäls", 6000.00));
			itemRepo.save(new Item("Berguvspäls", 8000.00));
			itemRepo.save(new Item("Bisonoxpäls", 10000.00));
			itemRepo.save(new Item("Murmeldjurspäls", 3000.00));
			itemRepo.save(new Item("Rödrävspäls", 4500.00));
			itemRepo.save(new Item("Ekorrepäls", 2000.00));
			itemRepo.save(new Item("Järvpäls", 5000.00));

		};
	}

	@Bean
	public CommandLineRunner orders (OrderRepo orderRepo, CustomerRepo customerRepo, ItemRepo itemRepo){
		return (args) -> {
			List<Customer> customers = customerRepo.findAll();
			List<Item> items = itemRepo.findAll();

			if (customers.size() != 0) {
				//Create a random order.
				Random random = new Random();

				int customerIndex1 = random.nextInt(customers.size());
				int customerIndex2 = random.nextInt(customers.size());
				int customerIndex3 = random.nextInt(customers.size());

				int itemIndex1 = random.nextInt(items.size());
				int itemIndex2 = random.nextInt(items.size());
				int itemIndex3 = random.nextInt(items.size());

				List<Item> itemList1 = new ArrayList<>();
				List<Item> itemList2 = new ArrayList<>();
				List<Item> itemList3 = new ArrayList<>();

				itemList1.add(items.get(itemIndex1));
				itemList2.add(items.get(itemIndex1));

				itemList3.add(items.get(itemIndex3));
				itemList3.add(items.get(itemIndex1));

				Customer customer1 = customers.get(customerIndex1);
				Customer customer2 = customers.get(customerIndex2);
				Customer customer3 = customers.get(customerIndex3);

				LocalDate currentDate1 = LocalDate.now();
				LocalDate currentDate2 = LocalDate.now();
				LocalDate currentDate3 = LocalDate.now();

				Order order1 = new Order();
				Order order2 = new Order();
				Order order3 = new Order();

				order1.setDate(currentDate1);
				order1.setCustomer(customer1);
				order1.setItems(itemList1);

				order2.setDate(currentDate2);
				order2.setCustomer(customer2);
				order2.setItems(itemList2);

				order3.setDate(currentDate3);
				order3.setCustomer(customer3);
				order3.setItems(itemList3);

				orderRepo.save(order1);
				orderRepo.save(order2);
				orderRepo.save(order3);

			}
		};
	}
}
