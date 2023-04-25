package com.example.webstorethymeleaf.Controller;


import com.example.webstorethymeleaf.POJO.Customer;
import com.example.webstorethymeleaf.Repositories.CustomerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "customers.html";
    }
    @RequestMapping("customers/{id}")
    public String findById(@PathVariable long id, Model model){
        Customer customer = customerRepo.findById(id).get();
        model.addAttribute("customer", customer);
        return "customer.html";
    }
    @RequestMapping("customers/{id}/delete")
    public String deleteById(@PathVariable long id, Model model){
        Customer customer = customerRepo.findById(id).get();
        model.addAttribute("customer", customer);
        customerRepo.deleteById(id);
        return "delete.html";
    }

    @RequestMapping("customers/add")
    public String addCustomersByForm(){
        return "addCust.html";
    }


    @PostMapping("customers/sd")
    public String addCustomer(@RequestParam String name,
                              @RequestParam String ssn, RedirectAttributes redirectAttributes) {
        try {
            Customer newCustomer = new Customer(name, ssn);
            customerRepo.save(newCustomer);
            log.info("POST request was successful.");
        } catch (Exception e) {
            log.error("POST request failed: " + e.getMessage());
            e.printStackTrace(); // Add this line to print the exception stack trace
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding customer. Please try again.");
            return "redirect:/customers/add"; // Change this line to redirect back to the add form in case of an error
        }
        return "redirect:/customers";
    }
}




