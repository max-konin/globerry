package com.globerry.project.controllers.user;

import com.globerry.project.domain.City;
import com.globerry.project.domain.CityShort;
import com.globerry.project.domain.Curve;
import com.globerry.project.domain.Hotel;
import com.globerry.project.domain.LatLng;
import com.globerry.project.domain.Ticket;
import com.globerry.project.domain.Tour;
import com.globerry.project.service.CurveService;
import com.globerry.project.service.DefaultDatabaseCreator;
import com.globerry.project.service.ICSHellGateService;
import com.globerry.project.service.gui.IGuiComponent;
import com.globerry.project.service.gui.ISlider;
import com.globerry.project.service.interfaces.ICurveService;
import com.globerry.project.service.interfaces.IProposalsManager;
import com.globerry.project.service.interfaces.IUserCityService;
import com.globerry.project.service.service_classes.ApplicationContextFactory;
import com.globerry.project.service.service_classes.IApplicationContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javax.vecmath.Point2f;

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

    List<CityShort> cityShortList;

    protected static final Logger logger = Logger.getLogger(HomeController.class);
    @Autowired
    IUserCityService userCityService;

    @Autowired
    IProposalsManager proposalsManager;

    @Autowired
    DefaultDatabaseCreator defaultDatabaseCreator;

    @Autowired
    ApplicationContextFactory factory;
    
    @Autowired
    ICurveService curveService;
	
	@Autowired
	ICSHellGateService icsService;

    IApplicationContext appContext;

    /*
     * Only for debug mode
     */
    @RequestMapping(value = "/CreateDefaultDatabase")
    public String CreateDefaultDatabase()
    {

	defaultDatabaseCreator.initTags();
	return "redirect:/";
    }

    @RequestMapping(value = "/initTours")
    public String CreateDatabase()
    {
	defaultDatabaseCreator.initHotels();
	defaultDatabaseCreator.initTours();
	defaultDatabaseCreator.initTickets();
	return "redirect:/";
    }

    @RequestMapping(value = "/globerry")
    public String home(Model model)
    {
	logger.info("HomeController: Passing through...");
	// appContext.init();
	model.addAttribute("hash", this.hashCode());
	return "home";
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

    @RequestMapping(value = "/")
    public String home(Map<String, Object> map)
    {

	appContext = factory.createAppContext();

	map.put("who", appContext.getWhoTag());
	map.put("when", appContext.getWhenTag());
	map.put("what", appContext.getWhatTag());
	map.put("mapZoom", appContext.getMapZoom());

	for (String sliderName : appContext.getSliders().keySet())
	    map.put(sliderName, (ISlider) appContext.getSlidersByName(sliderName));
	map.put("visa", appContext.getVisa());
	map.put("rusLang", appContext.getRusLanguage());
	return "home_new";
    }

    @RequestMapping(value = "/gui_changed", method = RequestMethod.POST)
    @ResponseBody
    public List<CityShort> guiChanged(@RequestBody com.globerry.project.service.service_classes.Request[] request)
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

        cityShortList = userCityService.getCityList(appContext);


	logger.debug("Найдено " + ((Integer) cityShortList.size()).toString() + " города");

	for (CityShort cityShort : cityShortList)
	{
	    logger.debug(cityShort.getName() + " weight: " + cityShort.getWeight());
	}
	logger.debug(appContext.toString());
	return cityShortList;
    }
	
    @RequestMapping(value = "/gui_changed_new", method = RequestMethod.POST)
    @ResponseBody
    public Collection<Curve> guiChangedNew(@RequestBody com.globerry.project.service.service_classes.Request[] request)
    {
		logger.debug(request);
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
        
        
        Collection<Curve> curves = curveService.getCurves(appContext);

	logger.debug(appContext.toString());
	return curves;
    }
	
	@RequestMapping(value = "/getTours", method = RequestMethod.POST)
	@ResponseBody
	public String getTours(@RequestBody Integer[] request) {
		logger.debug(request);
		String retTours = icsService.getTours(request, appContext);
		return retTours;
	}
	
    @RequestMapping(value = "/initPropertyType")
    public String initPropertyType()
    {
	defaultDatabaseCreator.initPropertyType();
	return "redirect:/";
    }

    @RequestMapping(value = "/bezier")
    public String bezier()
    {
	return "bezier";
    }
}
