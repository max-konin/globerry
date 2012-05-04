package com.globerry.project.controllers.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.Element;
import javax.xml.ws.Response;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.JSONPObject;
import org.codehaus.jackson.type.TypeReference;
import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Month;
import com.globerry.project.service.CityService;
import com.globerry.project.service.admin.AbstractTypeFactory;
import com.globerry.project.service.admin.IEntityCreator;
import com.globerry.project.service.interfaces.ICityService;
import com.globerry.project.service.interfaces.IEventService;



@Controller
@RequestMapping("/admin")
public class AdminController
{
   
    @Autowired
    private AbstractTypeFactory abstrFactory;
    
    @Autowired
    private IEventService eventService;
    
    @Autowired
    private CityService cityService;
    
    private IEntityCreator page = null;
    
    private String url;
    
    @RequestMapping("/{url}")
    public String createForm(@PathVariable("url") String _url, Map<String, Object> map)
    {
	page = abstrFactory.responsePage(_url);
	url = _url;
	page.setList(map);
	return page.getJspListFile();
    }
    @RequestMapping("/delete/{id}")
    public String removeCompany(@PathVariable("id") Integer id)
    {
	page.removeElem(id);
	return "redirect:/admin/" + url;
    }
    
    @RequestMapping("/update/{id}")
    public String updateElement(@PathVariable("id") Integer id, Map<String, Object> map)
    {
	page.getElemById(map, id);
	page.getRelation(map, id);
	return page.getJspUpdateFile();
    }

    @RequestMapping(value = "/update/update", method = RequestMethod.POST)
    public String addContact(@ModelAttribute("object") Object object, BindingResult result, Map<String, Object> map) {
	map.put("months", Month.values());
        page.updateElem(object);
        return "redirect:/admin/" + url;
    }
    @RequestMapping(value="/getevents", method=RequestMethod.GET)
    public @ResponseBody List<Event> getEvents() {
	//БЫДЛОКОД
        return eventService.getEventList();
    }
    @RequestMapping(value="/getcities", method=RequestMethod.GET)
    public @ResponseBody List<City> getCities() {
	//БЫДЛОКОД
        return cityService.getCityList();
    }
    @RequestMapping(value="/getrelations/{id}", method=RequestMethod.GET)
    public @ResponseBody Map<String, Object> getRelations(@PathVariable("id") Integer id, Map<String, Object> map) {
	
         return page.getRelation(map, id);
    }
    /*HttpServletRequest request*/
    /*@RequestMapping(value="/delete", method=RequestMethod.GET)
    public void delete(HttpServletRequest request)
    {
	JsonFactory j = new JsonFactory();
	JsonParser json = null;
	try
	{
	    //System.out.println();
	    json = j.createJsonParser(request.getParameter("event"));
	} catch (JsonParseException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	Event event = null;
	try
	{
	   event = new ObjectMapper().readValue(json, Event.class);
	} catch (JsonParseException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (JsonMappingException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	System.out.println(event.getId());
	//System.out.println(event.getId());
	//System.out.println(event.getDescription());

	//JSONPObject jsonpobj = new JSONPObject(request.getParameter("models"), Event.class);
	//Event event = (Event)jsonpobj.getValue();
	//System.out.println(event.getId());
	//System.out.println(jsonpobj.getClass());
	//System.err.println(event.getDescription());
	//ObjectMapper mapper = new ObjectMapper();
	//Event event = mapper.readValue(, Event.class);
	//System.err.println(event.getDescription());
	//System.err.println(request.getParameter("models"));
	//System.err.println(request.getParameter("model"));
	//System.err.println(request.getParameter("event"));
	System.err.println("sdaf");
	
    }*/
    
    
   /* @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String addContact(@PathVariable("id") Integer id, @ModelAttribute("admin") Company company, BindingResult result) {

	try
	{
	    cmpService.addCompany(company);
	}
	catch(MySqlException e)
	{
	    //???? ?? ??????? ??? ?????? ???? ???????????? ??? ????????????? ??????
	}
        return "redirect:/";
    }*/
}
