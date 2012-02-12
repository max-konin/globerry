package com.globerry.project;


import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
/**
 * Handles requests for the application home page.
 */
@Controller
@Scope("session")
public class HomeController {
 
    @RequestMapping(value = "/globerry")
    public String home(Model model) {
        System.out.println("HomeController: Passing through...");
        staticClass st = new staticClass();
        int i = staticClass.i;
        model.addAttribute("i", i );
        model.addAttribute("hash", this.hashCode());
        return "WEB-INF/views/home.jsp";
        
        
    }
}

