/**
 * 
 */
package com.globerry.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globerry.project.Excel;
import com.globerry.project.ExcelParserException;
import com.globerry.project.MySqlException;
import com.globerry.project.dao.CityDao;
import com.globerry.project.dao.EventDao;
import com.globerry.project.dao.PropertyTypeDao;
import com.globerry.project.dao.TagDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.DependingMonthProperty;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Property;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;

/**
 * @author Artem
 *
 */
@Service("adminParser")
public class AdminParser implements IAdminParser
{

    
    @Autowired
    private CityDao cityDao;
    @Autowired
    private EventDao eventDao;
    @Autowired
    private PropertyTypeDao propertyTypeDao;
    @Autowired
    private TagDao tagDao;
    
    private Excel exc;
    /* (non-Javadoc)
     * @see com.globerry.project.service.IAdminParser#updateCities()
     */
    @Override
    public void updateCities(Excel exc) throws MySqlException, ExcelParserException
    {
	
	this.exc = exc;
	cityParse();
	eventParse();
	tagParse();
    }
    /**
     * Функция которая парсит первую страницу 
     * @throws MySqlException в случае если property или city не уникально
     * @throws ExcelParserException Провал валидации файла. В определённых случаях в базу заноситься либо null либо -1
     */
    private void cityParse() throws MySqlException, ExcelParserException
    {
	//Здесь создается таблица PropertyType. В качестве name берётся заголовок столбца в экселе
	//БЫДЛОКОД.
	final int sheetNumber = 0;
	final int startPositionProperty = 3; //Стартовая позиция для property
	final int devider = 10; // Перменная которая разделяет Property от DependingMonthProperty в excel. Cтартовая позиция для Dmp
	
	List<PropertyType> ptList = new ArrayList<PropertyType>(); 
	List<PropertyType> ptDmpList = new ArrayList<PropertyType>(); 
	
	for(int j = startPositionProperty; j < devider; j++) //Количество столбцов property. В данном случае в эксельнике 8 столбцов property
	{
	    PropertyType propType = new PropertyType();
	    	try
	    	{
	    	    propType.setName(exc.getStringField(sheetNumber, 0, j)); // j - startPositionProperty на ноль переход
	    	}
	    	catch(NullPointerException e)
	    	{
	    	    ExcelParserException excParseExc = new ExcelParserException("Error in name of property sheet: " 
	    		    			+ exc.getSheetName(sheetNumber) + 
	    		    			"line 0" +
	    		    			"column" + j, j);
	    	    throw excParseExc;
	    	    
	    	}
		try
		{
		    ptList.add(propType);
		    propertyTypeDao.addPropertyType(propType);
		} catch (MySqlException e)
		{
	   	    throw e;
		} // чтобы лист начинался с 0
			
	}
	//Создание propertyType для Dmp
	for(int j = devider; j < exc.getRowLenght(0); j+=12)
	{
	    PropertyType propType = new PropertyType();
	    propType.setName(exc.getStringField(sheetNumber, 0, j));
	    try
	    {
		ptDmpList.add(propType);
		propertyTypeDao.addPropertyType(propType);
	    } catch (MySqlException e)
	    {
		throw e;
	    }// получение 10 + j в случае и записывание в list[j]
	    catch(NullPointerException e)
	    {
	        ExcelParserException excParseExc = new ExcelParserException("Error in name of property sheet: " 
	    	    			+ exc.getSheetName(sheetNumber) + 
	    	    			"line 0" +
	    	    			"column" + j, j);
	        throw excParseExc;
	        
	    }
	}
		
	int i = 2;
	//while(i<exc.getLenght(0))
	while (i < exc.getLenght(0))
	{
	    	//Standart properties
	    	City city = new City();
		city.setName(exc.getName(i));
		city.setRu_name(exc.getRussianName(i));
		
		//Properties
	    	for(int j = startPositionProperty; j < devider; j++)
	    	{
	    	    Property prop = new Property();
	    	    try
	    	    {
	    		prop.setValue((float)exc.getFloatField(sheetNumber, i, j));
	    	    }
	    	    catch(NullPointerException e)
		    {
	    		ExcelParserException excParseExc = new ExcelParserException("Error in name of property sheet: " 
	    	    			+ exc.getSheetName(sheetNumber) + 
	    	    			"line 0" +
	    	    			"column" + i, i);
	    		throw excParseExc;
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
	       			ExcelParserException excParseExc = new ExcelParserException("Error in name of property sheet: " 
	    	    			+ exc.getSheetName(sheetNumber) + 
	    	    			"line 0" +
	    	    			"column" + j, j);
	       			throw excParseExc;
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
		    throw except;
		}
		catch(NullPointerException e)
		{
		    break;
		}
		i++;
		
	 }
    }
    /**
     * Функция парсит event
     * @throws ExcelParserException в случае провала валидации
     */
    private void eventParse() throws ExcelParserException
    {
	final int sheetNumber =1;
	int i = 1;
	while(exc.getEventId(i) != -1)
	{   
	    	ArrayList<Integer> cityId = searchCity(exc.getEventIdSheet2(i));
	    	
	    	Event event = new Event();
	    	try
	    	{
	    	    event.setName(exc.getNameSheet2(i));
	    	    event.setDescription(exc.getDescription(i));
	    	    event.setRu_description(exc.getRuDescription(i));
	    	    event.setImage(exc.getImage(i));
	    	}
		 catch(NullPointerException e)
		    {
		        ExcelParserException excParseExc = new ExcelParserException("Error in name of property sheet: " 
		    	    			+ exc.getSheetName(sheetNumber) + 
		    	    			"line 0" +
		    	    			"column" + i, i);
		        throw excParseExc;
		        
		    }
	    	
	    	
	    	try
	    	{
	    	    event.setMonth(exc.getEventsMonths(i));
	    	}
	    	catch (java.lang.ArrayIndexOutOfBoundsException e)
	    	{
	    	    throw new ExcelParserException("Month", i);
	    	}
	    	event.setRu_name(exc.getRussianName(i));
		
		try
		{
		   for (int j = 0; j < cityId.size(); j++)
		   {
		       City city = cityDao.getCityById(cityId.get(j));
		       eventDao.addEvent(event, city); 
		   }
		}
		catch(NullPointerException e)
		{
		    break;
		}
		i++;
		
	 }
    }
    /**
     * Парсер второй страницы
     * @throws MySqlException в случаесовпадения двух тэгов
     * @throws ExcelParserException в случае провала валидации
     */
    private void tagParse() throws MySqlException, ExcelParserException
    {
	final int sheetNumber = 2;
	try
	{
            	ArrayList<String> tagsList = getTagsList();
        	int i = 1;
        	int index;
        	while(exc.getCityIdSheet3(i) != -1)
        	{
        	    try
        	    {
        	    	index = tagsList.indexOf(exc.getTag(i));
        		City city = cityDao.getCityById(exc.getCityIdSheet3(i));
        		city.getTagList().add(tagDao.getTagById(index + 1));
        		cityDao.updateCity(city);
        	    }
   		 	catch(NullPointerException e)
		    {
		        ExcelParserException excParseExc = new ExcelParserException("Error in name of property sheet: " 
		    	    			+ exc.getSheetName(sheetNumber) + 
		    	    			"line 0" +
		    	    			"column" + i, i);
		        throw excParseExc;
		        
		    }

        		i++;
        	}
	}
	catch (MySqlException e)
	{
	    throw e;
	}
    }
    
    /**
     * Функция осуцествляет поиск по таблице event_id city_id в документе
     * @param event_id передаётся номер евента
     * @param exc Сам файл. 
     * @return	массив int где содержаться все id городов связанных с этим event
     */
    private ArrayList<Integer> searchCity(int event_id) 
    {
	ArrayList<Integer> cityId = new ArrayList<Integer>();
	
	int i = 1;
	while (i < exc.getLenght(1))
	{
	    if(exc.getEventIdSheet2(i) == event_id)
	    {
		cityId.add(exc.getCityIdSheet2(i));
	    }
	    i++;
	}
	if(cityId.size() == 0) throw new NullPointerException("there is no any connection to event by number " + event_id + ".Empty event!!!");
	return cityId;
    }
    /**
     * функция заполняющая массив тэгов и добавляющая их в БД
     * @return массив тэгов который находятся в 4 столбце
     * @throws MySqlException
     */
    private ArrayList<String> getTagsList() throws MySqlException
    {
        final int tagsInfoColumn = 3;
        int i = 1;
        ArrayList<String> tagsArr = new ArrayList<String>(); 
        while(exc.getStringField(2, i, tagsInfoColumn) != null)
        {
            Tag tag = new Tag();
            tag.setName(exc.getStringField(2, i, tagsInfoColumn));
            tagsArr.add(tag.getName());
            try 
	    {
		tagDao.addTag(tag);
	    } catch (MySqlException e)
	    {
		throw e;
	    }
            i++;
        }
        return tagsArr;
    }


}

