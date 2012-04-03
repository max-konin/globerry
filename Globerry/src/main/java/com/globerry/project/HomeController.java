package com.globerry.project;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.globerry.project.dao.CityRequest;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Hotel;
import com.globerry.project.domain.Tag;

 
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
    @RequestMapping(value="/getcities", method= RequestMethod.GET)
    public @ResponseBody City[] test() {
	City[] cities = null;
        City city = new City();
        city.setName("New York ");
        city.setLatitude(1);
        city.setLongitude(1);
        City city1 = new City();
        city1.setName("London ");
        city1.setLatitude((float)51.505);
        city1.setLongitude((float)-0.09);
        List<City> cityList = new ArrayList<City>();
        cityList.add(city);
        cities = new City[2];//cityList.toArray(cities);
        cities[0] = city;
        cities[1] = city1;
        System.out.println(cities[1]);
        return cities;
    }
    //TODO delete this 
    @RequestMapping(value="/selecttag", method= RequestMethod.POST)
    public void testPost(Tag tag) {
	
	
	System.out.println(tag.getId());
        
    }
}

