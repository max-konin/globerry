package com.globerry.project.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
