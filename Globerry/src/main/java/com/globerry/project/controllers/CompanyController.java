package com.globerry.project.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.globerry.project.Excel;
import com.globerry.project.MySqlException;
import com.globerry.project.dao.CityDao;
import com.globerry.project.dao.CompanyDao;
import com.globerry.project.dao.ICompanyDao;
import com.globerry.project.dao.TourDao;
//import com.globerry.project.dao.TourDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.Tour;
import com.globerry.project.service.CompanyService;

//TODO
@Controller
public class CompanyController
{
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private CompanyService cmpService;

    
    @RequestMapping("/admin")
    public String companyList(Map<String,Object> map){
	map.put("company",new Company());
	map.put("companyList",cmpService.getCompanyList());
	
	return "admin";
    }
    
    @RequestMapping("/")
    public String home(){
		
	Company company = new Company();
	company.setName("name");
	company.setDescription("afdsdfasfd");
	company.setLogin("login");
	company.setEmail("email");
	company.setPassword("555555");
	try
	{
	    cmpService.addCompany(company);
	}
	catch(MySqlException e)
	{

	}
	
	
	
	return "company";
    }
    
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addContact(@ModelAttribute("admin") Company company,
            BindingResult result) {

	try
	{
	    cmpService.addCompany(company);
	}
	catch(MySqlException e)
	{
	    //Пока не понятно что писать если пользователь ввёл повторяющиеся данные
	}
        return "redirect:/";
    }
    
    @RequestMapping("/delete/{companyId}")
    public String removeCompany(@PathVariable("companyId") Integer companyId)
    {
	cmpService.removeCompany(companyId);
	return "redirect:/admin";
    }//TODO
   /* @RequestMapping("/updateCities")
    public String updateCities()
    {
	File file = new File("classpath:content.xlsx");
	try
	{
	    System.out.println(file.getCanonicalPath());
	    System.out.println(file.getAbsolutePath());
	} catch (IOException e)
	{
	    System.out.println(file.getAbsolutePath());
	    System.out.println(file.getPath());
	    e.printStackTrace();
	}
	return "HELLO WORLD";//*/
	/*Excel exc = new Excel("content.xlsx");
	int i = 1;
	while(i<exc.getLenght(0))
	{
	    	System.out.println("Lenght file: " + exc.getLenght(0));
		long timeout= System.currentTimeMillis();
		City city = new City();
		city.setName(exc.getName(i));
		city.setRu_name(exc.getRussianName(i));
		timeout = System.currentTimeMillis() - timeout;
		System.out.println("Parsing time: " + timeout);
		try
		{
		    long time = System.currentTimeMillis();
		    cityDao.addCity(city);
		    time = System.currentTimeMillis() - time;
		    System.out.println("Mapping:time " + time);
		}
		catch(MySqlException except)
		{
		    System.out.println(exc.getLenght(0));
		}
		catch(NullPointerException e)
		{
		    return "redirect:/";
		}
		i++;
		
	    }
	return "redirect:/";
    }
    //*/

}
