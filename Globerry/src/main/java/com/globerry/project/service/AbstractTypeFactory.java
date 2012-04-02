package com.globerry.project.service;


import org.springframework.stereotype.Service;

import com.globerry.project.service.CityPage;



public class AbstractTypeFactory
{
    
    public static IEntityCreator responsePage(String type)
    {
	if(type.toLowerCase().equals("cityadminpage")) return new CityPage();
	else if(type.toLowerCase().equals("companyadminpage")) return new CompanyPage();
	else if(type.toLowerCase().equals("eventadminpage")) return new EventPage();
	else return new WrongPage();
    }
}
