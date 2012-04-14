package com.globerry.project;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.globerry.project.dao.CityDao;
import com.globerry.project.dao.CityRequest;
import com.globerry.project.dao.Range;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Hotel;
import com.globerry.project.domain.Tag;
import com.globerry.project.service.UserCityService;

 
/**
 * Handles requests for the application home page.
 */
@Controller
@Scope("session")
public class HomeController {
    @Autowired
    UserCityService userCityService;

    @Autowired
    CityDao cityDao;
 
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
        city.setId(1);
        city.setLatitude((float)51.508);
        city.setLongitude((float)-0.12);
        City city1 = new City();
        city1.setName("London ");
        city1.setId(2);
        city1.setLatitude((float)51.505);
        city1.setLongitude((float)-0.09);
        City city2 = new City();
        city2.setName("Novosibirsk ");
        city2.setId(3);
        city2.setLatitude((float)60.505);
        city2.setLongitude((float)-5.09);
        if (cityDao.getCityById(1) == null)
        try
	{
	    cityDao.addCity(city);
	    cityDao.addCity(city1);
	    cityDao.addCity(city2);
	} catch (MySqlException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
        System.out.println("Запрос городов от клиента");
        Set<City> cityList = userCityService.getCityList();
        cities = new City[cityList.size()];
        userCityService.getCityList().toArray(cities);
        System.out.println("Найдено "+((Integer)cities.length).toString()+" города");
        
        return cities;
    }
    //TODO delete this 
    @RequestMapping(value="/selecttag", method= RequestMethod.POST)
    public void testPost(Tag tag) {
	
	
	System.out.println(tag.getId());
        
    }
    //TODO delete this 
    @RequestMapping(value="/rangechange", method= RequestMethod.POST)
    public void range(Range range) {
	userCityService.changeRange(range);        
    }
}

