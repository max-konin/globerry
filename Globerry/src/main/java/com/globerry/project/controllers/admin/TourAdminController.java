/**
 * 
 */
package com.globerry.project.controllers.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.globerry.project.service.CompanyService;

/**
 * @author Artem
 *
 */
//@Controller
//@RequestMapping("/admin")
public class TourAdminController
{
   /* static final String url = "touradminpage";
    
    
    @Autowired
    private CompanyService cmpService;
    
    @RequestMapping("/" + url)
    public String createForm(Map<String, Object> map)
    {
	//map.put("city", new City());
	//map.put("tourList", cmpService.getTourList(company));
	return "admin/tagpage";
    }
    @RequestMapping("/" + url + "/delete/{cityId}")
    public String removeCompany(@PathVariable("cityId") Integer cityId)
    {
	cmpService.removeCompany(cityId);
	return "redirect:/admin/" + url;
    }//*/
}
