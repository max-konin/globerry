/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.dao;



import com.globerry.project.domain.Month;
import com.globerry.project.domain.Tag;
import com.globerry.project.service.service_classes.IApplicationContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Фабрика создания запросов
 * @author max
 */
@Component
public class QueryFactory
{   
    
    public  String createCityRequest(IApplicationContext appContext)
    {
        String query = "select distinct city from City city ";   
        //Тэги
        String joinClause =   "inner join city.tagList t1 "
                            + "inner join city.tagList t2 ";
        String whereClause = "where t1.id = " + appContext.getWhatTag().getValue() +
                               " and t2.id = " + appContext.getWhatTag().getValue();
        //Сладеры
        whereClause += " and city.security <=" + appContext.getSlidersByName("security").getRightValue() +
                       " and city.security >=" + appContext.getSlidersByName("security").getLeftValue();        
        whereClause += " and city.sex <=" + appContext.getSlidersByName("sex").getRightValue() +
                       " and city.sex >=" + appContext.getSlidersByName("sex").getLeftValue();        
        
        whereClause += " and (city.alcoCost.left <=" + appContext.getSlidersByName("alcohol").getRightValue() +
                       " or city.alcoCost.right <=" + appContext.getSlidersByName("alcohol").getLeftValue() + ")";
        
        whereClause += " and (city.foodCost.left <=" + appContext.getSlidersByName("cost").getRightValue() +
                       " or city.foodCost.right <=" + appContext.getSlidersByName("cost").getLeftValue() + ")";
        
        String currentMonth = Month.values()[appContext.getWhenTag().getValue()].toString();
        
        
        whereClause += " and (city.temperature." + currentMonth + "Value.left <= " + 
                             appContext.getSlidersByName("temperature").getRightValue() +
                       " or city.temperature." + currentMonth + "Value.right <= " +  
                             appContext.getSlidersByName("temperature").getLeftValue() + ")";
        
        whereClause += " and (city.mood." + currentMonth + "Value.left <= " + 
                             appContext.getSlidersByName("mood").getRightValue() +
                       " or city.mood." + currentMonth + "Value.right <= " +  
                             appContext.getSlidersByName("mood").getLeftValue() + ")";
         
        whereClause += " and (city.livingCost." + currentMonth + "Value.left <= " + 
                             appContext.getSlidersByName("livingCost").getRightValue() +
                       " or city.livingCost." + currentMonth + "Value.right <= " +  
                             appContext.getSlidersByName("livingCost").getLeftValue() + ")";
        return query + joinClause + whereClause;
    }
    public String getCityById(int id)
    {
	return "from City where id="+id;
    }
    public String getCompanyById(int id)
    {
	return "from Company where id="+id;
    }
    
}
