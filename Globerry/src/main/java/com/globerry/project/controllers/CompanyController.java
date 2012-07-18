package com.globerry.project.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.globerry.project.Excel;
import com.globerry.project.MySqlException;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Company;

import com.globerry.project.service.interfaces.ICityService;
import com.globerry.project.service.interfaces.ICompanyService;

//TODO
@Controller
@Scope("session")
public class CompanyController
{
    @Autowired
    private ICompanyService cmpService;
    
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
	/*try
	{
	 //   cmpService.addCompany(company);
	}
	catch(MySqlException e)
	{

	}*/
	map.put("locale",locale);
        map.put("hash", this.hashCode());
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
