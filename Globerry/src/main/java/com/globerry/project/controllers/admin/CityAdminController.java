package com.globerry.project.controllers.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.globerry.project.dao.CityDao;
import com.globerry.project.domain.City;
import com.globerry.project.service.AbstractTypeFactory;
import com.globerry.project.service.CityPage;
import com.globerry.project.service.IEntityCreator;

@Controller
@RequestMapping("/admin")
public class CityAdminController
{
    static final String url= "cityadminpage";
    
    
    @Autowired
    private CityDao cityDao;
    
    @RequestMapping("/" + url)
    public String createForm(Map<String, Object> map)
    {

	map.put("cityList", cityDao.getCityList());
	return "admin/citypage";
    }
    @RequestMapping("/" + url + "/delete/{cityId}")
    public String removeCompany(@PathVariable("cityId") Integer cityId)
    {
	cityDao.removeCity(cityId);
	return "redirect:/admin/" + url;
    }
}
