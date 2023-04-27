package com.example.webstorethymeleaf.Controller;


import com.example.webstorethymeleaf.POJO.Customer;
import com.example.webstorethymeleaf.POJO.Item;
import com.example.webstorethymeleaf.Repositories.ItemRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

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
    @RequestMapping("items/getById")
    public String getCustomersByIdForm(){
        return "getItemById";
    }
    @RequestMapping("items/{id}")
    public String findById(@PathVariable long id, Model model){
        Item item = itemRepo.findById(id).get();
        model.addAttribute("item", item);
        return "item.html";
    }

    @RequestMapping("items/{id}/update")
    public String updateById(@PathVariable long id, Model model){
        Item item = itemRepo.findById(id).get();
        model.addAttribute("item", item);
        return "updateItem.html";
    }
    @RequestMapping("items/{id}/update/form")
    public String updateItemByForm(@PathVariable long id, Model model){
        Item item = itemRepo.findById(id).get();
        model.addAttribute("item", item);
        return "updateItemForm.html";
    }

    @PutMapping("items/{id}/update/form/execute")
    public ResponseEntity<?> proceedUpdate(@PathVariable long id, @RequestBody Map<String, String> formData) throws Exception {
        Item existingItem = itemRepo.findById(id).get();
        existingItem.setName(formData.get("name"));
        double temp = Double.valueOf(formData.get("price"));
        existingItem.setPrice(temp);
        itemRepo.save(existingItem);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("items/{id}/delete")
    public String deleteById(@PathVariable long id, Model model){
        Item item = itemRepo.findById(id).get();
        model.addAttribute("item", item);
        itemRepo.deleteById(id);
        return "deleteItem.html";
    }

    @RequestMapping("items/add")
    public String addCustomersByForm(){
        return "addItem.html";
    }


    @PostMapping("items/sd")
    public String addItem(@RequestParam String name,
                              @RequestParam Double price, RedirectAttributes redirectAttributes) {
        try {
            Item newItem = new Item(name, price);
            itemRepo.save(newItem);
            log.info("POST request was successful.");
        } catch (Exception e) {
            log.error("POST request failed: " + e.getMessage());
            e.printStackTrace(); // Add this line to print the exception stack trace
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding customer. Please try again.");
            return "redirect:/items/add"; // Change this line to redirect back to the add form in case of an error
        }
        return "redirect:/items";
    }

}










