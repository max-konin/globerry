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
import com.globerry.project.dao.PropertyTypeDao;
import com.globerry.project.dao.Range;
import com.globerry.project.dao.TagDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Hotel;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.TagsType;
import com.globerry.project.service.BlockItem;
import com.globerry.project.service.BlockWhat;
import com.globerry.project.service.BlockWho;
import com.globerry.project.service.Calendar;
import com.globerry.project.service.MyDate;
import com.globerry.project.service.SliderData;
import com.globerry.project.service.Sliders;
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
    @Autowired
    Sliders sliders;
    @Autowired
    PropertyTypeDao propertyTypeDao;
    @Autowired
    BlockWhat blockWhat;
    @Autowired
    BlockWho blockWho;
    @Autowired
    TagDao tagDao;
    @Autowired
    Calendar calendar;
    private boolean basebase = true;
 
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
        
        Tag tag1 = new Tag();
        tag1.setName("ololo");
        tag1.setTagsType(TagsType.WHO);
        
        PropertyType propertyType = new PropertyType();
        propertyType.setName("prop");
        
        if (cityDao.getCityById(1) == null || basebase ){
        try
	{
            basebase = false;
	    cityDao.addCity(city);
	    cityDao.addCity(city1);
	    cityDao.addCity(city2);
	    tagDao.addTag(tag1);
	    propertyTypeDao.addPropertyType(propertyType);
	} catch (MySqlException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}}
        System.out.println("Запрос городов от клиента");
        Set<City> cityList = userCityService.getCityList();
        cities = new City[cityList.size()];
        userCityService.getCityList().toArray(cities);
        System.out.println("Найдено "+((Integer)cities.length).toString()+" города");
        
        return cities;
    }
    //TODO delete this 
    @RequestMapping(value="/rangechange", method= RequestMethod.POST)
    public void range(Range range) {
        System.out.println("Передвинули карту");
	userCityService.changeRange(range);        
    }
    @RequestMapping(value="/sliderchange", method= RequestMethod.POST)
    public void slider(SliderData sliderData) {
        System.out.println("Сдвинули слайдер");
	PropertyType type = propertyTypeDao.getById(sliderData.getId());
	sliders.addOrCreate(type, sliderData.getLeftValue(), sliderData.getRightValue());       
    }
    @RequestMapping(value="/tagchange", method= RequestMethod.POST)
    public void who(Tag tag) {    
        System.out.println("выбрали тег");
	tag = tagDao.getTagById(tag.getId());
	System.out.println(tag.getName());
	if (tag !=null){
	    BlockItem blockItem = new BlockItem(tag);
	    if (tag.getTagsType() == TagsType.WHO)
		blockWho.setSelected(blockItem);
	    else
		blockWhat.setSelected(blockItem);
	}
    }
    @RequestMapping(value="/monthchange", method= RequestMethod.POST)
    public void month(MyDate myDate) {   
        System.out.println("Выбрали месяц");
	calendar.changeMonth(myDate.getMonth());
    }
    
    
}

