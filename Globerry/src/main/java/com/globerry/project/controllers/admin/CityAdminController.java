package com.globerry.project.controllers.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	return page.getJspUpdateFile();
    }
    @RequestMapping(value="/getevents", method=RequestMethod.GET)
    public @ResponseBody List<Event> test() {
	
        return eventService.getEventList();
    }
    
    
   /* @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String addContact(@PathVariable("id") Integer id, @ModelAttribute("admin") Company company, BindingResult result) {

	try
	{
	    cmpService.addCompany(company);
	}
	catch(MySqlException e)
	{
	    //���� �� ������� ��� ������ ���� ������������ ��� ������������� ������
	}
        return "redirect:/";
    }*/
}
