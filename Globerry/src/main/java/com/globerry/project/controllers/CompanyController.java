package com.globerry.project.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;

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
import com.globerry.project.service.PropertyTypeService;
import com.globerry.project.service.UserCityService;

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
    @Autowired
    private PropertyTypeService PrTService;
    @Autowired
    private UserCityService UCService;
    @Autowired
    private LocaleResolver localeResolver;
    
  /*  @RequestMapping("/admin")
    public String companyList(Map<String,Object> map){
	map.put("company",new Company());
	map.put("companyList",cmpService.getCompanyList());
	
	return "admin";
    }*/
    
    @RequestMapping("/")
    public String home(HttpServletRequest request, Map<String,Object> map, Locale locale){
		
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
	
	
	map.put("FIRSTHASH",PrTService.getPropertyDaoHash());
	map.put("SECONDHASH",UCService.getPropertyDaoHash());
	map.put("locale",locale);
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
	    //���� �� ������� ��� ������ ���� ������������ ��� ������������� ������
	}
        return "redirect:/";
    }
    
 /*   @RequestMapping("/delete/{companyId}")
    public String removeCompany(@PathVariable("companyId") Integer companyId)
    {
	cmpService.removeCompany(companyId);
	return "redirect:/admin";
    }*/


}
