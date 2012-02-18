package com.globerry.project.controllers;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.globerry.project.domain.Company;
import com.globerry.project.service.CompanyService;

//TODO
@Controller
public class CompanyController
{
    @Autowired
    private CompanyService companyService;
    
    //@RequestMapping("/company")
    public String companyList(Map<String,Object> map){
	//map.put("company",new Company());
	//map.put("companyList",companyService.getCompanyList());
	
	return "company";
    }
    
    @RequestMapping("/")
    public String home(){
	Company company = new Company();//"123", "123", "123");
	company.setName("name");
	company.setDescription("afdsdfasfd");
	company.setLogin("login");
	company.setEmail("email");
	company.setPassword("555555");
	companyService.addCompany(company);
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
