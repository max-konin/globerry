package com.globerry.project.controllers;

import java.sql.Date;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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

    
    //@RequestMapping("/company")
    public String companyList(Map<String,Object> map){
	//map.put("company",new Company());
	//map.put("companyList",companyService.getCompanyList());
	
	return "company";
    }
    
    @RequestMapping("/")
    public String home(){
		
	Company company = new Company();
	company.setName("name");
	company.setDescription("afdsdfasfd");
	company.setLogin("login");
	company.setEmail("email");
	company.setPassword("555555");
	
	cmpService.addCompany(company);
	
	
	
	return "WEB-INF/views/company.jsp";
    }
    
    //@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addCompany(@ModelAttribute("company") Company company, BindingResult result){
	//companyService.addCompany(company);
	
	return "redirect:/index";
    }
    
    //@RequestMapping("/delete/{companyId}")
    public String removeCompany()
    {
	return null;
    }//TODO
    
}
