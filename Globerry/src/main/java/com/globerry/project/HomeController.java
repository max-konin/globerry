package com.globerry.project;



import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;

 
/**
 * Handles requests for the application home page.
 */
@Controller
@Scope("session")
public class HomeController {
 
    @RequestMapping(value = "/globerry")
    public String home(Model model) {
        System.out.println("HomeController: Passing through...");
        model.addAttribute("hash", this.hashCode());
        return "WEB-INF/views/home.jsp";
        
        
    }
}

