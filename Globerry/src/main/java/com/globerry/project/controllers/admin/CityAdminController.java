package com.globerry.project.controllers.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.util.JSONPObject;
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
import com.globerry.project.service.admin.AbstractTypeFactory;
import com.globerry.project.service.admin.IEntityCreator;
import com.globerry.project.service.interfaces.IEventService;


//@JsonAutoDetect
@Controller
@RequestMapping("/admin")
public class CityAdminController
{
   
    @Autowired
    private AbstractTypeFactory abstrFactory;
    
    @Autowired
    private IEventService eventService;
    
    private IEntityCreator page = null;
    
    private String url;
    
    @RequestMapping("/{url}")
    public String createForm(@PathVariable("url") String _url, Map<String, Object> map)
    {
	System.out.println(_url);
	System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFf");
	page = abstrFactory.responsePage(_url);
	url = _url;
	page.setList(map);
	return page.getJspListFile();
    }
    @RequestMapping("/delete/{id}")
    public String removeCompany(@PathVariable("id") Integer id)
    {
	System.err.println(")))))))))))))))))))");
	page.removeElem(id);
	System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<");
	return "redirect:/admin/" + url;
    }
    
    @RequestMapping("/update/{id}")
    public String updateElement(@PathVariable("id") Integer id, Map<String, Object> map)
    {
	page.getElemById(map, id);
	return page.getJspUpdateFile();
    }
    @RequestMapping(value="/getevents", method=RequestMethod.GET)
    public @ResponseBody List<Event> test() {
	
        return eventService.getEventList();
    }
    @RequestMapping(value="/delete", method=RequestMethod.GET)
    public void delete(HttpServletRequest request,Event event)
    {
	System.err.println(event.getDescription());
	System.err.println(request.getParameter("models"));
	System.err.println(request.getParameter("model"));
	System.err.println(request.getParameter("event"));
	System.err.println("sdaf");
	
    }
    
    
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
