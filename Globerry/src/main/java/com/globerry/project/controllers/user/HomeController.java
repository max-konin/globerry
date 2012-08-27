package com.globerry.project.controllers.user;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Hotel;
import com.globerry.project.domain.Ticket;
import com.globerry.project.domain.Tour;
import com.globerry.project.service.DefaultDatabaseCreator;
import com.globerry.project.service.gui.IGuiComponent;
import com.globerry.project.service.gui.ISlider;
import com.globerry.project.service.interfaces.IProposalsManager;
import com.globerry.project.service.interfaces.IUserCityService;
import com.globerry.project.service.service_classes.ApplicationContextFactory;
import com.globerry.project.service.service_classes.IApplicationContext;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Handles requests for the application home page.
 */
@Controller
@Scope("session")
public class HomeController
{

    List<City> cityList;

    protected static final Logger logger = Logger.getLogger(HomeController.class);
    @Autowired
    IUserCityService userCityService;

    @Autowired
    IProposalsManager proposalsManager;

    @Autowired
    DefaultDatabaseCreator defaultDatabaseCreator;

    @Autowired
    ApplicationContextFactory factory;

    IApplicationContext appContext;

    /*
     * Only for debug mode
     */
    @RequestMapping(value = "/CreateDefaultDatabase")
    public String CreateDefaultDatabase()
    {

	defaultDatabaseCreator.initTags();
	return "redirect:/globerry_new";
    }

    @RequestMapping(value = "/initTours")
    public String CreateDatabase()
    {
	defaultDatabaseCreator.initHotels();
	defaultDatabaseCreator.initTours();
	defaultDatabaseCreator.initTickets();
	return "redirect:/globerry_new";
    }

    @RequestMapping(value = "/globerry")
    public String home(Model model)
    {
	logger.info("HomeController: Passing through...");
	// appContext.init();
	model.addAttribute("hash", this.hashCode());
	return "home";
    }

    // TODO delete this
    @RequestMapping(value = "/getcities", method = RequestMethod.GET)
    public @ResponseBody
    City[] test()
    {
	// this.cityInit();
	logger.debug("GUI State: " + appContext.toString());

	City[] cities = null;

	logger.debug("Запрос городов от клиента");

	List<City> cityList = userCityService.getCityList(appContext);
	cities = new City[cityList.size()];
	cityList.toArray(cities);

	logger.debug("Найдено " + ((Integer) cities.length).toString() + " города");
	logger.debug("Найдено " + cityList.size());

	for (int i = 0; i < cities.length; i++)
	{
	    logger.debug(cities[i].getName() + " weight: " + cities[i].getWeight());
	}
	return cities;
    }

    public void cityInit()
    {
	defaultDatabaseCreator.initTags();
	System.out.println("/cityinit");
    }

    // TODO delete this

    @RequestMapping(value = "/feature_test", method = RequestMethod.POST)
    public void testSmth(@RequestBody com.globerry.project.service.service_classes.Request[] request)
    {
	try
	{
	    for (com.globerry.project.service.service_classes.Request r : request)
	    {
		appContext.getObjectById(r.getId()).setValues((IGuiComponent) r.getValue());
	    }
	}
	catch (IllegalArgumentException e)
	{
	    logger.error(e.getMessage() + " Current appcontext is " + appContext.toString());
	}
	System.out.println(appContext);

    }

    @RequestMapping(value = "/globerry_new")
    public String home(Map<String, Object> map)
    {

	appContext = factory.createAppContext();

	map.put("who", appContext.getWhoTag());
	map.put("when", appContext.getWhenTag());
	map.put("what", appContext.getWhatTag());

	cityList = userCityService.getCityList(appContext);
	map.put("cities", cityList);
	for (String sliderName : appContext.getSliders().keySet())
	    map.put(sliderName, (ISlider) appContext.getSlidersByName(sliderName));
	map.put("visa", appContext.getVisa());
	map.put("rusLang", appContext.getRusLanguage());
	return "home_new";
    }

    @RequestMapping(value = "/gui_changed", method = RequestMethod.POST)
    @ResponseBody
    public City[] guiChanged(@RequestBody com.globerry.project.service.service_classes.Request[] request)
    {
	try
	{
	    for (com.globerry.project.service.service_classes.Request r : request)
	    {
		// appContext.getObjectById(r.getId()).сopyValues((IGuiComponent)
		// r.getValue());
		IGuiComponent component = appContext.getObjectById(r.getId());
		component.setValues((IGuiComponent) r.getValue());
		logger.debug(component.toString());
		if (r.getId() <= 2)
		    userCityService.onTagChangeHandler();
	    }
	}
	catch (IllegalArgumentException e)
	{
	    logger.error(e.getMessage() + " Current appcontext is " + appContext.toString());
	}
	logger.debug("Запрос городов от клиента");

	cityList = userCityService.getCityList(appContext);

	City[] cities = new City[cityList.size()];
	cityList.toArray(cities);
	logger.debug("Найдено " + ((Integer) cities.length).toString() + " города");
	logger.debug("Найдено " + cityList.size());

	for (int i = 0; i < cities.length; i++)
	{
	    logger.debug(cities[i].getName() + " weight: " + cities[i].getWeight());
	}
	logger.debug(appContext.toString());
	return cities;
    }
    @RequestMapping(value = "/initPropertyType")
    public String initPropertyType()
    {
	defaultDatabaseCreator.initPropertyType();
	return "redirect:/globerry_new";
    }
    @RequestMapping(value = "/get_hotels", method = RequestMethod.POST)
    @ResponseBody
    public Hotel[] GetHotels()
    {
	Set<Hotel> hotelList = proposalsManager.getHotelsByCities(cityList);
	Hotel[] hotels = new Hotel[hotelList.size()];
	hotelList.toArray(hotels);
	return hotels;
    }

    @RequestMapping(value = "/get_avias", method = RequestMethod.POST)
    @ResponseBody
    public Ticket[] GetTickets()
    {
	Set<Ticket> ticketList = proposalsManager.getTicketsByCities(cityList);
	Ticket[] tickets = new Ticket[ticketList.size()];
	ticketList.toArray(tickets);
	return tickets;
    }

    @RequestMapping(value = "/get_tours", method = RequestMethod.POST)
    @ResponseBody
    public Tour[] GetTours()
    {
	Set<Tour> tourList = proposalsManager.getToursByCities(cityList);
	Tour[] tours = new Tour[tourList.size()];
	logger.debug(tourList.size());
	logger.info(tourList.size());
	tourList.toArray(tours);
	return tours;

    }

    @RequestMapping(value = "/bezier")
    public String bezier()
    {
	return "bezier";
    }
}
