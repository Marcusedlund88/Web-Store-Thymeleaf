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

@SpringBootApplication
public class WebStoreThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebStoreThymeleafApplication.class, args);
	}

	@Bean
	@Transactional
	public CommandLineRunner initializeDatabase(CustomerRepo customerRepo, ItemRepo itemRepo, OrderRepo orderRepo){
		return args -> {
			customerRepo.deleteAll();
			itemRepo.deleteAll();
			orderRepo.deleteAll();
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
}
