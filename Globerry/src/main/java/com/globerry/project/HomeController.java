package com.globerry.project;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.globerry.project.dao.ICityDao;
import com.globerry.project.dao.IPropertyTypeDao;
import com.globerry.project.dao.Range;
import com.globerry.project.dao.ITagDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.TagsType;
import com.globerry.project.service.*;
import com.globerry.project.service.gui.IGuiComponent;
import com.globerry.project.service.gui.ISlider;
import com.globerry.project.service.interfaces.ISliders;
import com.globerry.project.service.interfaces.IUserCityService;
import com.globerry.project.service.service_classes.GloberryGuiContext;
import com.globerry.project.service.service_classes.IApplicationContext;
import java.util.Map;
import java.util.ArrayList;
import javax.xml.ws.Dispatch;
import org.apache.log4j.Priority;
import org.springframework.web.bind.annotation.*;

/**
 * Handles requests for the application home page.
 */
@Controller
@Scope("session")
public class HomeController {

    public static final Logger logger = Logger.getLogger(HomeController.class);
    @Autowired
    IUserCityService userCityService;
    @Autowired
    ICityDao cityDao;
    @Autowired
    ISliders sliders;
    @Autowired
    IPropertyTypeDao propertyTypeDao;
    @Autowired
    BlockWhat blockWhat;
    @Autowired
    BlockWho blockWho;
    @Autowired
    ITagDao tagDao;
    @Autowired
    Calendar calendar;
    @Autowired
    DefaultDatabaseCreator defaultDatabaseCreator;
    @Autowired
    IApplicationContext appContext;   
    

    /*
     * Only for debug mode
     */
    @RequestMapping(value = "/CreateDefaultDatabase")
    public String CreateDefaultDatabase() {

        defaultDatabaseCreator.initPropertyType();
        defaultDatabaseCreator.initTags();
        defaultDatabaseCreator.initCities();
        return "redirect:/globerry";
    }

    @RequestMapping(value = "/globerry")
    public String home(Model model) {
        logger.info("HomeController: Passing through...");
        //---Инициализация тэгов----
        Tag withWhoTag = tagDao.getTagById(1);
        Tag whereTag = tagDao.getTagById(5);
        if (withWhoTag != null && whereTag != null) {
            who(withWhoTag);
            who(whereTag);
        }
        //---Инициализация слайдеров
        appContext.init();
        sliders.init();
        model.addAttribute("hash", this.hashCode());
        return "home";
    }
    //TODO delete this 

    @RequestMapping(value = "/getcities", method = RequestMethod.GET)
    public @ResponseBody
    City[] test() {
        //this.cityInit();
        logger.debug("GUI State: " + appContext.toString());
        
        City[] cities = null;
        
        logger.debug("Запрос городов от клиента");
        
        List<City> cityList = userCityService.getCityList(appContext);
        cities = new City[cityList.size()];
        cityList.toArray(cities);
        
        logger.debug("Найдено " + ((Integer) cities.length).toString() + " города");
        logger.debug("Найдено " + cityList.size());
        
        
        
        for (int i = 0; i < cities.length; i++) {
            logger.debug(cities[i].getName() + " weight: " + cities[i].getWeight());
        }      
        return cities;
    }

    public void cityInit() {
        defaultDatabaseCreator.initTags();
        defaultDatabaseCreator.initPropertyType();
        defaultDatabaseCreator.initCities();
        System.out.println("/cityinit");
    }
    //TODO delete this 

    @RequestMapping(value = "/rangechange", method = RequestMethod.POST)
    public void range(Range range) {
        System.out.println("Передвинули карту");
        userCityService.changeRange(range);
    }

    @RequestMapping(value = "/sliderchange", method = RequestMethod.POST)
    public void slider(SliderData sliderData) {
        //sliders.init();
        PropertyType type = propertyTypeDao.getById(sliderData.getId());
        if (type != null) {
            System.out.println("Сдвинули слайдер: " + type.getName()
                    + " Новые значения: (" + sliderData.getLeftValue() + ","
                    + sliderData.getRightValue() + ")");
            sliders.changeOrCreate(type, sliderData.getLeftValue(), sliderData.getRightValue());
        } else {
            System.out.println("Сдвинули несущестнующий слайдер. Проигнорировано");
        }
    }

    @RequestMapping(value = "/tagchange", method = RequestMethod.POST)
    public void who(Tag tag) {
        tag = tagDao.getTagById(tag.getId());
        if (tag != null) {
            System.out.println("выбрали тег: " + tag.getName());
            if (tag.getTagsType() == TagsType.WHO) {
                blockWho.setSelected(tag);
            } else {
                blockWhat.setSelected(tag);
            }
        } else {
            System.out.println("выбрали несуществующий тег. изменение тега проигнорировано");
        }
    }

    @RequestMapping(value = "/monthchange", method = RequestMethod.POST)
    public void month(MyDate myDate) {
        System.out.println("Выбрали месяц: " + myDate.getMonth());
        calendar.changeMonth(myDate.getMonth());
    }

    @RequestMapping(value = "/feature_test", method = RequestMethod.POST)
    public void testSmth(@RequestBody com.globerry.project.service.service_classes.Request[] request) {
        try
        {
            for (com.globerry.project.service.service_classes.Request r : request) {
                appContext.getObjectById(r.getId()).сopyValues((IGuiComponent) r.getValue());
            }
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage() + " Current appcontext is " + appContext.toString());
        }
        System.out.println(appContext);
        
    }
    
    @RequestMapping(value = "/globerry_new")
    public String home(Map<String,Object> map) {
        
       /* map.put("who", appContext.getWhoTag());
        map.put("when", appContext.getWhenTag());
        map.put("what", appContext.getWhatTag());
        
        map.put("alcohol", appContext.getAlcoholSlider());
        map.put("travelTime", appContext.getTravelTimeSlider());
        map.put("livingCost", appContext.getLivingCostSlider());
        map.put("foodCost", appContext.getFoodCostSlider());
        map.put("temperature", appContext.getTemperatureSlider());*/       
        appContext.init();
        map.put("who", appContext.getWhoTag());
        map.put("when", appContext.getWhenTag());
        map.put("what", appContext.getWhatTag());        
        
        List<City> cities = userCityService.getCityList(appContext);
        map.put("cities", cities);
        for(String sliderName: appContext.getSliders().keySet())
            map.put(sliderName,(ISlider)appContext.getSlidersByName(sliderName));
                
        //return "home_new";
        return "home_new";
    }
    
    @RequestMapping(value = "/gui_changed", method = RequestMethod.POST)
    @ResponseBody
    public City[] guiChanged(@RequestBody com.globerry.project.service.service_classes.Request[] request){
        try
        {
            for (com.globerry.project.service.service_classes.Request r : request) {
                //appContext.getObjectById(r.getId()).сopyValues((IGuiComponent) r.getValue());
                IGuiComponent component = appContext.getObjectById(r.getId());
                component.сopyValues((IGuiComponent) r.getValue());
                if (r.getId() <= 2) userCityService.onTagChangeHandler();
            }
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage() + " Current appcontext is " + appContext.toString());
        }
        logger.debug("Запрос городов от клиента");
       
        List<City> cityList = userCityService.getCityList(appContext);
       
        City[] cities = new City[cityList.size()];
        cityList.toArray(cities);
        logger.debug("Найдено " + ((Integer) cities.length).toString() + " города");
        logger.debug("Найдено " + cityList.size());        
       
        for (int i = 0; i < cities.length; i++) {
            logger.debug(cities[i].getName() + " weight: " + cities[i].getWeight());
        }
        logger.debug(appContext.toString());
        return cities;
    }
    @RequestMapping(value = "/bezier")
    public String bezier() {
        return "bezier";
    }
}
