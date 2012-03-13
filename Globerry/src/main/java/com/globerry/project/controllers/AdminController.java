/**
 * 
 */
package com.globerry.project.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.globerry.project.Excel;
import com.globerry.project.MySqlException;
import com.globerry.project.dao.CityDao;
import com.globerry.project.dao.EventDao;
import com.globerry.project.dao.PropertyTypeDao;
import com.globerry.project.dao.TagDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.Property;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.DependingMonthProperty;
import com.globerry.project.domain.UploadItem;

/**
 * @author Artem
 *
 */
@Controller
@RequestMapping(value = "/admin/upload")
public class AdminController
{
    @Autowired
    private CityDao cityDao;
    @Autowired
    private EventDao eventDao;
    @Autowired
    private PropertyTypeDao propertyTypeDao;
    @Autowired
    private TagDao tagDao;
    //@Autowired
    //private Depending
    
    public String getProjectRoot() {
	URL u = this.getClass().getProtectionDomain().getCodeSource()
			.getLocation();
	File f;
	try
	{
	    f = new File(u.toURI());
	    return f.getParent();
	} catch (URISyntaxException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;
}
    @RequestMapping(method = RequestMethod.GET)
    public String getUploadForm(Model model)
    {
      model.addAttribute(new UploadItem());
      return "admin/uploadForm";
    }
   
    @RequestMapping(method = RequestMethod.POST)
    public String create(UploadItem uploadItem, BindingResult result)
    {
      if (result.hasErrors())
      {
        for(ObjectError error : result.getAllErrors())
        {
          System.err.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());
        }
        return "admin/uploadForm";
      }
   
      // Some type of file processing...
      System.err.println("-------------------------------------------");
      System.err.println("Test upload: " + uploadItem.getName());
      System.err.println("Test upload: " + uploadItem.getFileData().getOriginalFilename());
      System.err.println(getProjectRoot() + "../../../../../" + uploadItem.getFileData().getOriginalFilename());
      System.err.println("-------------------------------------------");
      String filePath;
      try
      {
	filePath = getProjectRoot() + "/../../../../../../resources/upload/" + uploadItem.getFileData().getOriginalFilename();
	uploadItem.getFileData().transferTo(new File(filePath)); 
	try
	{
	    updateCities(filePath);
	} catch (MySqlException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
      } catch (IllegalStateException e)
      {
	  System.err.println("Hello World--------------------------------------------------");
	e.printStackTrace();
      } catch (IOException e)
      {
	  System.err.println("IOEXC--------------------------------------------------");
	e.printStackTrace();
      }
      return "redirect:/";
    }
    
    public void updateCities(String filePath) throws MySqlException
    {
	//Здесь создается таблица PropertyType. В качестве name берётся заголовок столбца в экселе
	//БЫДЛОКОД.
	Excel exc = new Excel(filePath);
	final int startPositionProperty = 3; //Стартовая позиция для property
	final int devider = 10; // Перменная которая разделяет Property от DependingMonthProperty в excel. Cтартовая позиция для Dmp
	
	List<PropertyType> ptList = new ArrayList<PropertyType>(); 
	List<PropertyType> ptDmpList = new ArrayList<PropertyType>(); 
	
	for(int j = startPositionProperty; j < devider; j++) //Количество столбцов property. В данном случае в эксельнике 8 столбцов property
	{
	    PropertyType propType = new PropertyType();
	    propType.setName(exc.getStringField(0, 0, j)); // j - startPositionProperty на ноль переход
		try
		{
		    ptList.add(propType);
		    propertyTypeDao.addPropertyType(propType);
		} catch (MySqlException e)
		{
    		   e.setDescription("Ошибка парсинга эксель файла в листе 0 строка 0 столбец " + j);
    		   throw e;
		} // чтобы лист начинался с 0
		
	}
	//Создание propertyType для Dmp
	for(int j = devider; j < exc.getRowLenght(0); j+=12)
	{
	    PropertyType propType = new PropertyType();
	    propType.setName(exc.getStringField(0, 0, j));
	    try
	    {
		ptDmpList.add(propType);
		propertyTypeDao.addPropertyType(propType);
	    } catch (MySqlException e)
	    {
		e.setDescription("Ошибка парсинга эксель файла в листе 0 строка 0 столбец " + j);
		throw e;
		}// получение 10 + j в случае и записывание в list[j]
	}
	
	int i = 2;
	//while(i<exc.getLenght(0))
	while (i<5)
	{
	    	City city = new City();
	    	//Standart properties
		city.setName(exc.getName(i));
		city.setRu_name(exc.getRussianName(i));
		
		//Properties
	    	for(int j = startPositionProperty; j < devider; j++)
	    	{
	    	    Property prop = new Property();
	    	    try
	    	    {
	    		prop.setValue((float)exc.getFloatField(0, i, j));
	    	    }
	    	    catch(NullPointerException e)
		    {
	    		prop.setValue(0);
		    }
	    	    prop.setPropertyType(ptList.get(j - startPositionProperty));
	    	    city.getPropertyList().add(prop);
	    	}
	    	//dmp
	    	for(int k = 0; k < ptDmpList.size(); k++)
	    	{
        	    	for(int j = 0; j < 12; j++)
        	    	{
        		    DependingMonthProperty dmpFunFactorType = new DependingMonthProperty();
        		    try
        		    {
        			dmpFunFactorType.setVal((float)exc.getFloatField(0, i, j + devider + 12*k)); // fun factor в файле начинается с 10
        		    }
        		    catch(NullPointerException e)
        		    {
        			dmpFunFactorType.setVal(0);
        		    }
        		    dmpFunFactorType.setMonth(j);
        		    dmpFunFactorType.setPropertysType(ptList.get(k));
        		    city.getDmpList().add(dmpFunFactorType);
        	    	}
	    	}
		

		try
		{
		    cityDao.addCity(city);    
		}
		catch(MySqlException except)
		{
		    System.out.println(exc.getLenght(0));
		}
		catch(NullPointerException e)
		{
		    break;
		}
		i++;
		
	 }
	i = 2;
	/*while(i<exc.getLenght(1))
	{
		/*Event event = new Event();
		event.setName(exc.getNameSheet2(i));
		event.setDescription(exc.getDescription(i));
		event.setRu_description(exc.getRuDescription(i));
		event.setImage(exc.getImage(i)); //Почему у нас Image float??
		event.setMonth(exc.getEventsMonths(i));// Что то переделать, либо эксел парсер либо геттер
		event.setRu_name(exc.getRussianName(i));

		try
		{
		  //  eventDao.addEvent(event, cityDao.getCityById(exc.getCityIdSheet2(i))); // что делает event_id

		}
		catch(NullPointerException e)
		{
		    break;
		}
		i++;
		
	 }*/
	/*i=2;
	while(i<exc.getLenght(2))
	{/*
		Tag tag = new Tag();
		tag.setName(exc.getTag(i));
		tag.setTagsType(exc.getTagsTypeSheet3(i));
		tag.getCityList().add(cityDao.getCityById(exc.getCityIdSheet3(i)));
		tagDao.addTag(tag);
	

		//event.setImage(exc.getImage(i)); Почему у нас Image float??
		//event.setMonth(exc.getEventsMonths(i)); Что то переделать, либо эксел парсер либо геттер
/*
		try
		{
		    eventDao.addEvent(event, cityDao.getCityById(exc.getCityIdSheet2(i))); // что делает event_id

		}
		catch(NullPointerException e)
		{
		    
		}
		i++;
		
	 }
	return;
	
    }*/
}
}
