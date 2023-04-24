package com.example.webstorethymeleaf.Controller;
import com.example.webstorethymeleaf.POJO.Customer;
import com.example.webstorethymeleaf.Repositories.CustomerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexController {


    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
