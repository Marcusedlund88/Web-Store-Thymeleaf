package com.example.webstorethymeleaf.Controller;


import com.example.webstorethymeleaf.POJO.Order;
import com.example.webstorethymeleaf.Repositories.CustomerRepo;
import com.example.webstorethymeleaf.Repositories.ItemRepo;
import com.example.webstorethymeleaf.Repositories.OrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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
    public List<Order> getAllOrder(){
        return orderRepo.findAll();
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
}






