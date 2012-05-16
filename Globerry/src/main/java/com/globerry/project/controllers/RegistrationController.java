package com.globerry.project.controllers;


import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.globerry.project.MySqlException;
import com.globerry.project.dao.CompanyDao;
import com.globerry.project.domain.Company;
import com.globerry.project.service.CompanyService;

@Controller
@RequestMapping("/auth")
public class RegistrationController 
{
    @Autowired
    private CompanyService companyService; 
    
    protected static Logger logger = Logger.getLogger("controller");
    private static Date time = new Date(System.currentTimeMillis());
    
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String getRegistrationPage() {
	return "registrationpage";
    }
    
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String tryRegistration(@RequestParam(value = "name", required = false) String name,
	    @RequestParam(value = "e-mail", required = false) String email,
	    @RequestParam(value = "pass", required = false) String password,
	    @RequestParam(value = "cPass", required = false) String cPassword,
	    ModelMap model) {
	boolean timeout;
	if(time.before(new Date(System.currentTimeMillis() - 1000))) {
	    time = new Date(System.currentTimeMillis());
	    timeout = false;
	}
	else 
	    timeout = true;
	Map<String,String> errorMap = new HashMap<String, String>();
	Pattern regex = Pattern.compile("^[A-Za-z0-9.%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}");
	if(name.isEmpty()) {
	    errorMap.put("name", "Login is empty. Please fill it.");
	}
	Company temp = companyService.getCompany(name);
	if(temp != null)
	    errorMap.put("name", "Login already exsist.");
	if(email.isEmpty() || !email.replaceAll(regex.pattern(), "").isEmpty()) {
	    errorMap.put("email", "Error in email. Please fix it.");
	}
	if(password.isEmpty()) {
	    errorMap.put("password", "Password is empty. Please fill it");
	}
	else if(!password.equals(cPassword)){
	    errorMap.put("password", "Confirming password failed.");
	}
	if(errorMap.isEmpty() && !timeout)	{
	    Company company = new Company();
	    company.setName(name);
	    company.setLogin(name);
	    company.setEmail(email);
	    company.setAccess(CompanyDao.ROLE_USER);
	    Md5PasswordEncoder md5 = new Md5PasswordEncoder();
	    company.setPassword(md5.encodePassword(password, null));
	    try {
		companyService.addCompany(company);
		String success = new String("Now you will registered by name : " + name + ", you can try <a href='/project/auth/login'>login</a>");
		model.put("success", success);
		return "registrationpage";
	    } catch (MySqlException mse) {
		mse.printStackTrace();
	    }
	}
	model.put("errorList", errorMap);
	model.put("Name", name);
	model.put("Email", email);
	return "registrationpage";
    }
    
    @RequestMapping(value = "/ajax", method = RequestMethod.POST)
    public String ajaxHandler(@RequestParam(value = "name", required = true) String name, ModelMap map) {
	String retValue = "";
	Company temp = companyService.getCompany(name);
	if(temp != null)
	    retValue = "Login already exist.";
	map.put("ajax", retValue);
	return "ajax";
    }
}
