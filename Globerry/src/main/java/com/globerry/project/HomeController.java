package com.globerry.project;



import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.globerry.project.domain.City;

 
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
        return "home";
        
        
    }
    //TODO delete this 
    @RequestMapping(value="/testjson/{sexLevel}", method= RequestMethod.GET)
    public @ResponseBody City test(@PathVariable String sexLevel) {
        City city = new City();
        city.setName("New Orlean "+sexLevel);
        return city;
    }
}

