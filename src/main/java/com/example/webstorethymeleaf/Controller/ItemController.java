package com.example.webstorethymeleaf.Controller;


import com.example.webstorethymeleaf.POJO.Item;
import com.example.webstorethymeleaf.Repositories.ItemRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ItemController {
    private final ItemRepo itemRepo;
    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    public ItemController(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }
    @RequestMapping("/items")
    public String getAllItems(Model model){
        List<Item> items = itemRepo.findAll();
        model.addAttribute("items", items);
        return "items.html";
    }
    @RequestMapping("items/{id}")
    public Item findById(@PathVariable long id){
        return itemRepo.findById(id).get();
    }
    @RequestMapping("items/{id}/delete")
    public List<Item> deleteById(@PathVariable long id){
        itemRepo.deleteById(id);
        return itemRepo.findAll();
    }
    @PostMapping("items/add")
    public List<Item> addItem(@RequestBody Item i){
        try {
            itemRepo.save(i);
            log.info("New item was successfully created.");
        }catch (Exception e) {
            log.error("Could not create new item. " + e.getMessage());
        }
        return itemRepo.findAll();
    }}

//TODO: items/buy endpoint så att nytt köp görs för specifik kund baserat på id.









