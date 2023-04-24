package com.example.webstorethymeleaf.Controller;


import com.example.webstorethymeleaf.POJO.Customer;
import com.example.webstorethymeleaf.POJO.Item;
import com.example.webstorethymeleaf.POJO.Order;
import com.example.webstorethymeleaf.Repositories.CustomerRepo;
import com.example.webstorethymeleaf.Repositories.ItemRepo;
import com.example.webstorethymeleaf.Repositories.OrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    private final OrderRepo orderRepo;
    private final CustomerRepo customerRepo;
    private final ItemRepo itemRepo;
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderRepo orderRepo, CustomerRepo customerRepo, ItemRepo itemRepo){
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.itemRepo = itemRepo;
    }
    @RequestMapping("/orders")
    public String getAllOrder(Model model){
        List<Order> orders = orderRepo.findAll();
        model.addAttribute("orders", orders);
        return "orders.html";
    }
    @RequestMapping("orders/{id}")
    public Order findById(@PathVariable long id){
        return orderRepo.findById(id).get();
    }
    @RequestMapping("orders/{id}/delete")
    public List<Order> deleteById(@PathVariable long id){
        orderRepo.deleteById(id);
        return orderRepo.findAll();
    }

    @PostMapping(value = "/orders/buy/")
    public List buyItem(@RequestBody Order o){
        try{
            orderRepo.save(o);
            log.info("New order was successfully created.");
        }catch (Exception e) {
            log.error("Could not create new orderr. " + e.getMessage());
        }
        return orderRepo.findAll();
    }
}








