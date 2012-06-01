package com.globerry.project.controllers.agent;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.globerry.project.dao.TourDao;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;
import com.globerry.project.service.AgentService;

@Controller
@RequestMapping("/agent")
@Scope("session")
public class AgentController	
{
    @Autowired
    private AgentService agentService;
    
    @Autowired
    private TourDao tourDao;
    
    
    @RequestMapping("/")
    public String agent(Map<String, Object> map)
    {
	agentService.TEST_METHOD();
	Company company = agentService.returnCurrentCompany();
	System.err.println("CURRENT COMPANY: " + (company == null));
	map.put("Name", company.getName());
	map.put("TourList", company.getTourList());
	return "agent";
    }
    
    @RequestMapping("/add")
    public String addTour(Map<String, Object> map)
    {
	map.put("Operation", "Add");
	return "agent/addoredittour";
    }
    
    @RequestMapping(value = "/appendAddTour", method = RequestMethod.POST)
    public String appendAddTour(@RequestParam(value="tourName", required=true) String name,
	    			@RequestParam(value="tourCost", required=true) Float cost,
	    			@RequestParam(value="tourStartDate", required=true) String startDate,
	    			@RequestParam(value="tourEndDate", required=true) String endDate,
	    			@RequestParam(value="tourDesription", required=false) String description,
	    			ModelMap model) throws ParseException
    {
	Tour tour = new Tour();
	tour.setName(name);
	tour.setCost(cost);
	tour.setDescription(description);
	
	DateFormat df = new SimpleDateFormat("yyyy-MM-DD");
	tour.setDateStart(new java.sql.Date(df.parse(startDate).getTime()));
	tour.setDateEnd(new java.sql.Date(df.parse(endDate).getTime()));
	
	agentService.addTour(tour);
	return "redirect:/agent/";
    }
    
    @RequestMapping("/edit/{id}")
    public String editTour(@PathVariable("id") Integer id, Map<String, Object> map)
    {
	map.put("Operation", "Edit");
	Tour tour = tourDao.getTour(id);
	map.put("Name", tour.getName());
	map.put("Cost", tour.getCost());
	map.put("Description", tour.getDescription());
	map.put("StartDate", tour.getDateStart());
	map.put("EndDate", tour.getDateEnd());
	map.put("Tourid", id);
	return "agent/addoredittour";
    }
    
    @RequestMapping(value = "/appendEditTour", method = RequestMethod.POST)
    public String appendEditTour(@RequestParam(value="tourName", required=false) String name,
	    			@RequestParam(value="tourCost", required=false) Float cost,
	    			@RequestParam(value="tourStartDate", required=false) String startDate,
	    			@RequestParam(value="tourEndDate", required=false) String endDate,
	    			@RequestParam(value="tourDesription", required=false) String description,
	    			@RequestParam(value="tourID", required=true) Integer id,
	    			ModelMap model) throws ParseException
    {
	Tour tour = new Tour();
	tour.setName(name);
	tour.setCost(cost);
	tour.setDescription(description);
	
	DateFormat df = new SimpleDateFormat("yyyy-MM-DD");
	tour.setDateStart(new java.sql.Date(df.parse(startDate).getTime()));
	tour.setDateEnd(new java.sql.Date(df.parse(endDate).getTime()));
	
	agentService.updateTour(tourDao.getTour(id), tour);
	
	return "redirect:/agent/";
    }
    
    @RequestMapping("/delete/{id}")
    public String removeTour(@PathVariable("id") Integer id)
    {
	System.err.println(id+": "+tourDao.getTour(id).hashCode());
	agentService.removeTour(tourDao.getTour(id));
	return "redirect:/agent/";
    }
}
