package com.globerry.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globerry.project.service.admin.CityPage;
import com.globerry.project.service.admin.CompanyPage;
import com.globerry.project.service.admin.EventPage;
import com.globerry.project.service.admin.IEntityCreator;
import com.globerry.project.service.admin.WrongPage;

@Service
public class AbstractTypeFactory
{
    @Autowired
    private CityPage cityPage;
    @Autowired
    private CompanyPage companyPage;
    @Autowired
    private EventPage eventPage;
    @Autowired
    private WrongPage wrongPage;
    
    public  IEntityCreator responsePage(String type)
    {
	if(type.toLowerCase().equals("cityadminpage")) return cityPage;
	else if(type.toLowerCase().equals("companyadminpage")) return companyPage;
	else if(type.toLowerCase().equals("eventadminpage")) return eventPage;
	else return wrongPage;
    }
}
