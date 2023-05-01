package com.example.webstorethymeleaf.Controller;


import com.example.webstorethymeleaf.POJO.Customer;
import com.example.webstorethymeleaf.POJO.Item;
import com.example.webstorethymeleaf.POJO.Order;
import com.example.webstorethymeleaf.Repositories.CustomerRepo;
import com.example.webstorethymeleaf.Repositories.ItemRepo;
import com.example.webstorethymeleaf.Repositories.OrderRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.sql.Array;
import java.sql.ClientInfoStatus;
import java.time.LocalDate;
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
    @RequestMapping("orders/getById")
    public String getCustomersByIdForm(){
        return "getOrderById";
    }

    @RequestMapping("orders/{id}")
    public String findById(@PathVariable long id, Model model){
        List<Order> orders = orderRepo.findAll();
        Order result = orders.stream()
                .filter(order -> order.getId() == id)
                .findFirst()
                .orElse(null);
      model.addAttribute("order", result);
        return "order.html";
    }
    @RequestMapping("orders/{id}/delete")
    public String deleteById(@PathVariable long id, Model model){
        Order order = orderRepo.findById(id).get();
        model.addAttribute("order", order);
        orderRepo.deleteById(id);
        return "deleteOrder.html";
    }

    @PostMapping(value = "/orders/buy/")
    public String buyItem(@RequestBody Order o){
        try{
            orderRepo.save(o);
            log.info("New order was successfully created.");
        }catch (Exception e) {
            log.error("Could not create new orderr. " + e.getMessage());
        }
        return "orders";
    }
    @RequestMapping("orders/{id}/create")
    public String createNewOrderByCustomer(@PathVariable long id, Model model){
        Customer customer = customerRepo.findById(id).get();
        model.addAttribute("customer", customer);

        List<Item> items = itemRepo.findAll();
        model.addAttribute("items", items);
        return "placeOrderByCustomerId";
    }

    @PostMapping(value = "/orders/buy/{id}")
    public String testBuyItem(@PathVariable long id, @RequestBody String itemJson){
        try{
            log.info(itemJson);

            Customer customer = customerRepo.findById(id).get();

            Gson gson = new Gson();

            Type itemListType = new com.google.gson.reflect.TypeToken<List<Item>>(){}.getType();
            List<Item> items = gson.fromJson(itemJson, itemListType);


            LocalDate currentDate = LocalDate.now();
            Order order = new Order();
            order.setDate(currentDate);
            order.setCustomer(customer);
            order.setItems(items);
            orderRepo.save(order);
            log.info("New order was successfully created.");
        }catch (Exception e) {
            log.error("Could not create new orderr. " + e.getMessage());
        }
        return "redirect:/orders";
    }
}








