/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.dao;



import com.globerry.project.domain.Month;
import com.globerry.project.domain.Tag;
import com.globerry.project.service.StringManager;
import com.globerry.project.service.gui.ISlider;
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
        String whereClause = "where t1.id = " + appContext.getWhatTag().getValue();
        if (appContext.getWhoTag().getValue()!= 1)
           whereClause += " and t2.id = " + appContext.getWhoTag().getValue();
        
        whereClause += " and city.security <=" + appContext.getSlidersByName("security").getRightValue() +
                       " and city.security >=" + appContext.getSlidersByName("security").getLeftValue();   
        
        whereClause += " and city.sex <=" + appContext.getSlidersByName("sex").getRightValue() +
                       " and city.sex >=" + appContext.getSlidersByName("sex").getLeftValue();        
        
        
        
        whereClause += " and (city.alcoCost.left <=" + appContext.getSlidersByName("alcohol").getRightValue() +
                       " and city.alcoCost.left >=" + appContext.getSlidersByName("alcohol").getLeftValue() +
                       " or  city.alcoCost.right >=" + appContext.getSlidersByName("alcohol").getLeftValue() +
                       " and city.alcoCost.right <=" + appContext.getSlidersByName("alcohol").getRightValue() +")";
        
        whereClause += " and (city.foodCost.left <=" + appContext.getSlidersByName("cost").getRightValue() +
                       " and city.foodCost.left >=" + appContext.getSlidersByName("cost").getLeftValue() +
                       " or  city.foodCost.right >=" + appContext.getSlidersByName("cost").getLeftValue() +
                       " and city.foodCost.right <=" + appContext.getSlidersByName("cost").getRightValue() +")";
        
        String currentMonth = Month.values()[appContext.getWhenTag().getValue()].toString();
        
        ISlider tempSlider = appContext.getSlidersByName("temperature");       
        joinClause += "left join fetch city.temperature temperature ";
        if(appContext.getWhatTag().getValue() == 5 && (tempSlider.getLeftValue() < 15)) 
            
            if(tempSlider.getRightValue() < 15)
                whereClause += " and (temperature." + currentMonth + "Value.left <= " + 20 + 
                               " and temperature." + currentMonth + "Value.left >= "  + 15 +
                               " or  temperature." + currentMonth + "Value.right >= " + 15 +
                               " and temperature." + currentMonth + "Value.right <= " + 20 +  ")";
            else
                 whereClause += " and (temperature." + currentMonth + "Value.left <= " + 
                                     appContext.getSlidersByName("temperature").getRightValue() +
                                " and temperature." + currentMonth + "Value.left >= "  + 15 +
                                " or  temperature." + currentMonth + "Value.right >= " + 15 +
                                " and temperature." + currentMonth + "Value.right <= " +  
                                     appContext.getSlidersByName("temperature").getRightValue() + ")";        	
        else                
            whereClause += " and (temperature." + currentMonth + "Value.left <= " + 
                             appContext.getSlidersByName("temperature").getRightValue() +
                          " and temperature." + currentMonth + "Value.left >= " + 
                             appContext.getSlidersByName("temperature").getLeftValue() +
                          " or  temperature." + currentMonth + "Value.right >= " +  
                             appContext.getSlidersByName("temperature").getLeftValue() + 
                          " and temperature." + currentMonth + "Value.right <= " +  
                             appContext.getSlidersByName("temperature").getRightValue() + ")";
        
        joinClause += "left join fetch  city.mood mood ";
        
        whereClause += " and (mood." + currentMonth + "Value.left <= " + 
                             appContext.getSlidersByName("mood").getRightValue() +
                       " and mood." + currentMonth + "Value.left >= " + 
                             appContext.getSlidersByName("mood").getLeftValue() +
                       " or  mood." + currentMonth + "Value.right >= " +  
                             appContext.getSlidersByName("mood").getLeftValue() +
                       " and mood." + currentMonth + "Value.right <= " +  
                             appContext.getSlidersByName("mood").getRightValue() + ")";
        
        joinClause += "left join fetch city.livingCost livingCost ";
         
        whereClause += " and (livingCost." + currentMonth + "Value.left <= " + 
                             appContext.getSlidersByName("livingCost").getRightValue() +
                       " and livingCost." + currentMonth + "Value.left >= " + 
                             appContext.getSlidersByName("livingCost").getLeftValue() +
                       " or  livingCost." + currentMonth + "Value.right >= " +  
                             appContext.getSlidersByName("livingCost").getLeftValue() + 
                       " and livingCost." + currentMonth + "Value.right <= " +  
                             appContext.getSlidersByName("livingCost").getRightValue() + ")";
        /*
         * TODO Переделать, когда будет нормальный парсер
        int visa = 0; 
        if(appContext.getVisa().isChecked())
        	visa = 1;
        whereClause +=" and city.visa = " + visa;
        int isRus = 0;
        if(appContext.getRusLanguage().isChecked())
        {
        	whereClause +=" and city.isRussian = 1";
        }
        * 
        */
        return query + joinClause + whereClause;
    }
}
