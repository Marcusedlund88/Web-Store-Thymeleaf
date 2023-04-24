package com.example.webstorethymeleaf.Controller;


import com.example.webstorethymeleaf.POJO.Customer;
import com.example.webstorethymeleaf.Repositories.CustomerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CustomerController {
    private final CustomerRepo customerRepo;
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    @RequestMapping("/test")
    public String test(Model model) {
        model.addAttribute("name", "Thymeleaf");
        return "static";
    }

    public CustomerController(CustomerRepo customerRepo){
        this.customerRepo = customerRepo;
    }
    @RequestMapping("/customers")
    public String getAllCustomer(Model model){
        List<Customer> customers = customerRepo.findAll();
        model.addAttribute("customers", customers);
        return "test.html";
    }
    @RequestMapping("customers/{id}")
    public Customer findById(@PathVariable long id){
        return customerRepo.findById(id).get();
    }
    @RequestMapping("customers/{id}/delete")
    public List<Customer> deleteById(@PathVariable long id){
        customerRepo.deleteById(id);
        return customerRepo.findAll();
    }
    @PostMapping("customers/add")
    public List<Customer> addCustomer(@RequestBody Customer c){
        try {
            customerRepo.save(c);
            log.info("POST request was successful.");
        } catch (Exception e) {
            log.error("POST request failed: " + e.getMessage());
        }
        return customerRepo.findAll();
    }
}



