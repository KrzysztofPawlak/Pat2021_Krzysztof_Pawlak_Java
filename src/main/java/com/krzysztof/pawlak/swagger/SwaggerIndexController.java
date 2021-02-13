package com.krzysztof.pawlak.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


//@Controller
public class SwaggerIndexController {

    @RequestMapping("/swagger-ui")
    public String index() {
        System.out.println("elo");
        return "index";
    }
}