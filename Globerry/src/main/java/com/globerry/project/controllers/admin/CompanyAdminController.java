/**
 * 
 */
package com.globerry.project.controllers.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.globerry.project.dao.CompanyDao;
import com.globerry.project.domain.City;
import com.globerry.project.service.CompanyService;
import com.globerry.project.service.admin.CityPage;
import com.globerry.project.service.admin.CompanyPage;

/**
 * @author Artem
 *
 */
//@Controller
//@RequestMapping("/admin_")
public class CompanyAdminController
{
  /*  static final String url = "companyadminpage";
    
    
    @Autowired
    private CompanyService cmpService;
    
    @RequestMapping("/" + url)
    public String createForm(Map<String, Object> map)
    {
	//map.put("city", new City());
	map.put("companyList", cmpService.getCompanyList());
	return "admin/companypage";
    }
    @RequestMapping("/" + url + "/delete/{cityId}")
    public String removeCompany(@PathVariable("cityId") Integer cityId)
    {
	cmpService.removeCompany(cityId);
	return "redirect:/admin/" + url;
    }*/
}
//*/