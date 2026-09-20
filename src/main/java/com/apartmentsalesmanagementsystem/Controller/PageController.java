package com.apartmentsalesmanagementsystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/apartments")
    public String apartmentsPage() {
        return "apartments";
    }

    @GetMapping("/promotions")
    public String promotionsPage() {
        return "promotions";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }
}