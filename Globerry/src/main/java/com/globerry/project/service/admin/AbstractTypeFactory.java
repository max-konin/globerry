package com.globerry.project.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Artem
 * factory - класс который возвращает нужный класс исходя из запроса контроллера
 */
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
    /**
     * Возвращает класс
     * @param type текстовое поля для определения
     * @return IentityCreator - один из 3х классов
     */
    public  IEntityCreator responsePage(String type)
    {
	if(type.toLowerCase().equals("cityadminpage")) return cityPage;
	else if(type.toLowerCase().equals("companyadminpage")) return companyPage;
	else if(type.toLowerCase().equals("eventadminpage")) return eventPage;
	else return wrongPage;
    }
}
