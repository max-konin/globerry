/**
 * 
 */
package com.globerry.project.service.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.hibernate.NonUniqueObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globerry.project.Excel;
import com.globerry.project.ExcelParserException;
import com.globerry.project.MySqlException;
import com.globerry.project.dao.CityDao;
import com.globerry.project.dao.ICityDao;
import com.globerry.project.dao.IEventDao;
import com.globerry.project.dao.IPropertyTypeDao;
import com.globerry.project.dao.ITagDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.DependingMonthProperty;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Property;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;
import com.globerry.project.service.DefaultDatabaseCreator;

/**
 * @author Artem
 *
 */
@Service("adminParser")
public class AdminParser implements IAdminParser
{

    
    @Autowired
    private ICityDao cityDao;
    @Autowired
    private IEventDao eventDao;
    @Autowired
    private IPropertyTypeDao propertyTypeDao;
    @Autowired
    private ITagDao tagDao;
    @Autowired
    private DefaultDatabaseCreator defaultDatabaseCreator;
    private Excel exc;
    //Здесь создается таблица PropertyType. В качестве name берётся заголовок столбца в экселе
    final int sheetNumber = 0;
    final int startPositionProperty = 5; //Стартовая позиция для property
    final int devider = 11; // Перменная которая разделяет Property от DependingMonthProperty в excel. Cтартовая позиция для Dmp
	//final int tagsDevider = 47;//переменная которая отделяет dependingMonthProperty от tag.
    
    protected static Logger logger = Logger.getLogger(AdminParser.class);

    @Override
    public void updateCities(Excel exc) throws MySqlException, ExcelParserException
    {
	
	this.exc = exc;
	if(tagDao.getTagList().isEmpty())
	    defaultDatabaseCreator.initTags();
	if(propertyTypeDao.getPropertyTypeList().isEmpty())
	    defaultDatabaseCreator.initPropertyType();
	
	cityParse();
	//eventParse();
	//tagParse();
    }
    /**
     * Обновляет ВСЕ города в таблице. Обновляются area, lattitude, longitude, population 
     * и два вспомогательных - isValid и message
     */
    @Override
    public void updateWikiContent()
    {
	PropertyType tempPropType = new PropertyType();
	try
	{
	    tempPropType.setName("temperature");
	    tempPropType.setDependingMonth(true);
	    propertyTypeDao.addPropertyType(tempPropType);
	}
	catch(Exception e)
	{
	    propertyTypeDao.getPropertyTypeByName("temperature");
	}

	List<City> cityList = new ArrayList<City>();
	cityList = cityDao.getCityList();
	Iterator<City> it = cityList.iterator();
	while(it.hasNext())
	{
	    City city = it.next();
	    com.globerry.htmlparser.City cityWiki;
	    try
	    {
		cityWiki = new com.globerry.htmlparser.City(city.getName().replace(", an island", "").replaceAll(" ","_"));
	    }
	    catch(NullPointerException e)
	    {
		logger.error("Обработанный эксепшн");
		break;
	    }
	    logger.info(cityWiki.getLatitude());
	    city.setLatitude(coordsTransform(cityWiki.getLatitude()));
	    city.setLongitude(coordsTransform(cityWiki.getLongitude()));
	    city.setPopulation(cityWiki.getPopulation());
	    city.setArea(cityWiki.getArea());
	    city.setMessage(cityWiki.getMessage());
	    city.setIsValid(cityWiki.getIsValid());
	    
	    logger.info("-------------------------------------");
	    logger.info("cityWiki: " + cityWiki.getIsValid());
	    logger.info("-------------------------------------");
	    logger.info("city" + city.getIsValid());
	    logger.info("-------------------------------------");
	    float[] temperature = cityWiki.getTemperature();
	    if(temperature != null)
	    {
		for(int i = 0; i < 12; i++)
		{
		    DependingMonthProperty dmpProp = new DependingMonthProperty();
		    dmpProp.setMonth(i);
		    dmpProp.setValue(temperature[i]);
		    dmpProp.setPropertyType(tempPropType);
		    city.getDmpList().add(dmpProp);
		}
	    }
		cityDao.updateCity(city);  
	}
	
    }
/*    public void setRange(PropertyType prop)
    {
	string name = prop.getName();
	switch(name)
	{
	case "":
		prop.setDependingMonth(true);
		prop.setBetterWhenLess(false);
		prop.setMinValue(-35);
		prop.getLast().setMaxValue(+35);
	}
	
    }*/
    /**
     * Извлекает и добавляет тэги к городу
     * Добавляем к городу тег Один Если секс от 1 до 3, безопасность от 1 до 3, настроение от 2 до 3
	Добавляем к городу тег Вдвоем Если секс от 2 до 3, безопасность от 1 до 3, настроение от 1 до 3
	Добавляем к городу тег Семья - секс от 1 до 3, безопасность 3, настроение от 1 до 3
	Добавляем к городу тег компания - секс от 2 до 3, настроение от 2 до 3, безопасность от 1 до 3
     * @param cell
     */
    private void stringToExcelDocumentToAddTag(City city, String cell)
    {
	String[] tagsString = cell.split(",");
	try
	{
	    Set<Property> propertyListOfCity = city.getPropertyList();
	    float security = 0;
	    float sex = 0;
	    for(Property elem: propertyListOfCity)
	    {
		if(elem.getPropertyType().getName().toLowerCase().equals("security")) security = elem.getValue();
		if(elem.getPropertyType().getName().toLowerCase().equals("sex")) sex = elem.getValue();
	    }
	    //Добавляем к городу тег Один Если секс от 1 до 3, безопасность от 1 до 3, настроение от 2 до 3
	    if(sex > 0 && security > 0)
	    {
		Tag tagAlone = tagDao.getTagById(1);
		city.getTagList().add(tagAlone);
	    }
	    if(sex > 1 && security > 0) 
	    {
		Tag tagCouple = tagDao.getTagById(4);
		Tag tagFriends = tagDao.getTagById(2);
		city.getTagList().add(tagFriends);
		city.getTagList().add(tagCouple);
	    }
	    if(security == 3 && sex > 0)
	    {
		Tag tagFamily = tagDao.getTagById(3);
		city.getTagList().add(tagFamily);
	    }
        	for(int i = 0; i < tagsString.length; i++)
        	{
        	    int number = Integer.parseInt(tagsString[i]) + 4;
        	    Tag tag = tagDao.getTagById(number);
        	    city.getTagList().add(tag);
        	}
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
	
    }
    private void stringToExcelDocumentToAddTag(City city, Double cell)
    {
	int number = (int)Math.round(cell);
	try
	{
        	for(int i = 1; i < 5; i++)
        	{
        	    Tag tag = tagDao.getTagById(i);
        	    city.getTagList().add(tag);
        	}
        	Tag tag = tagDao.getTagById(number + 4);
        	city.getTagList().add(tag);
	}
	catch(Exception e)
	{
	    
	}
	
    }
    /**
     * Функция которая парсит первую страницу 
     * @throws MySqlException в случае если property или city не уникально
     * @throws ExcelParserException Провал валидации файла. В определённых случаях в базу заноситься либо null либо -1
     */
    private void cityParse() throws MySqlException, ExcelParserException
    {

	
	List<PropertyType> ptList = createPropertyTypeList(); 
	List<PropertyType> ptDmpList = createDependingMonthPropertyList(); 
	
	//ЗАПОЛНЕНИЕ БД
	int i = 2;
	//while(i<exc.getLenght(0))
	while (i < exc.getLenght(0))
	{
	    	//Standart properties
	    	City city = new City();
	    	if(exc.getStringField(sheetNumber, i, 2) == "") continue;
		city.setName(exc.getStringField(sheetNumber, i, 2));
		city.setRu_name(exc.getStringField(sheetNumber, i, 3));

		
		//Properties
	    	for(int j = startPositionProperty; j < devider; j++)
	    	{
	    	    Property prop = new Property();
	    	    try
	    	    {
	    		prop.setValue((float)exc.getFloatField(sheetNumber, i, j));
	    	    }
	    	    catch(Exception e)
		    {
	    		ExcelParserException excParseExc = new ExcelParserException("Error in name of property sheet: " 
	    	    			+ exc.getSheetName(sheetNumber) + 
	    	    			" line " + i +
	    	    			" column " + j +
	    	    			" name " + ptList.get(j - startPositionProperty).getName(), i);
	    		throw excParseExc;
		    }
	    	    
	    	    prop.setPropertyType(ptList.get(j - startPositionProperty));
	    	    logger.info(prop.hashCode());
	    	    city.getPropertyList().add(prop);
	    	}
	   	//dmp
	    	for(int k = 0; k < ptDmpList.size() - 1; k++) // если убрать температуру, то надо делать " - 1"
	    	{
	       	    	for(int j = 0; j < 12; j++)
	       	    	{
	       		    DependingMonthProperty dmpType = new DependingMonthProperty();
	       		    try
	       		    {
	       			try
	       			{
	       			    String cell = exc.getStringField(sheetNumber, i, j + devider + 12*k);
	       			/*    logger.info(cell);
	       			    logger.info(getAverageValue(cell));*/
	       			    dmpType.setValue(getAverageValue(cell));  
	       			}
	       			catch(IllegalStateException e)
	       			{
	       			    dmpType.setValue((float)exc.getFloatField(sheetNumber, i, j + devider + 12*k)); 
	       			}
	       		
	       		    }
	       		    catch(Exception e)
	       		    {
	       			ExcelParserException excParseExc = new ExcelParserException("Error in name of property sheet: " 
	    	    			+ exc.getSheetName(sheetNumber) +
	    	    			" type of DEPENDING MONTH PROPERTIES " + ptDmpList.get(k).getName() +
	    	    			" line " + i +
	    	    			" position " + k, i);
	       			throw excParseExc;
	       		    }
	       		    dmpType.setMonth(j);
	       		    dmpType.setPropertyType(ptDmpList.get(k));
	       		    logger.info(dmpType.hashCode());
	       		    city.getDmpList().add(dmpType);
	       	    	}
	    	}
		try
		{
		    stringToExcelDocumentToAddTag(city, exc.getStringField(sheetNumber, i, 4));
		}
		catch(IllegalStateException e)
		{
		    stringToExcelDocumentToAddTag(city, exc.getFloatField(sheetNumber, i, 4));
		}
		

		try
		{
		    for(Property elem: city.getPropertyList())
		    {
			logger.info(elem.getId() + " " + elem.getValue() + " " + elem.getPropertyType().getName());
		    }
		    for(DependingMonthProperty elem: city.getDmpList())
		    {
			logger.info(elem.getId() + " " + elem.getValue() + " " + elem.getPropertyType().getName() + " " + elem.getMonth());
		    }

		    cityDao.addCity(city);   
		    System.err.println(city.getId());
		    for(Property elem: city.getPropertyList())
		    {
			logger.info(elem.getId() + " " + elem.getValue() + " " + elem.getPropertyType().getName());
		    }
		    for(DependingMonthProperty elem: city.getDmpList())
		    {
			logger.info(elem.getId() + " " + elem.getValue() + " " + elem.getPropertyType().getName() + " " + elem.getMonth());
		    }
		    cityDao.updateCity(city);
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
   /* public void wikiParse()
    {
	List<City> cityList = new ArrayList<City>();
	PropertyType tempPropType = new PropertyType();
	tempPropType.setName("Temperature");
	cityList = cityDao.getCityList();
	Iterator<City> it = cityList.iterator();
	while(it.hasNext())
	{
	    City city = it.next();
	    com.globerry.htmlparser.City cityFromWiki = new com.globerry.htmlparser.City(city.getName());
	    city.setArea(cityFromWiki.getArea());
	    city.setPopulation(cityFromWiki.getPopulation());
	    city.setLatitude(cityFromWiki.getLatitude());
	    city.setLongitude(cityFromWiki.getLongitude());
	    city.setValid(cityFromWiki.getIsValid());
	    city.setMessage(cityFromWiki.getMessage());
	    logger.error("City Valid:" + cityFromWiki.getIsValid());
	    logger.error("City Valid:" + cityFromWiki.getIsValid());
	}
    }*/
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
		    	    			"; line 0;" +
		    	    			"column " + i, i);
		        throw excParseExc;
		        
		    }
        	    catch(NonUniqueObjectException e)
        	    {
		        ExcelParserException excParseExc = new ExcelParserException("Error in name of property sheet(NonUnique): " 
    	    			+ exc.getSheetName(sheetNumber) + 
    	    			"; line 0" +
    	    			"; column " + i, i);
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
    /**
     * преобразует координаты из типа String из стандарта в float не стандарт 
     * @return float координаты не стандарта
     */
    public float coordsTransform(String coordsStr)
    {
	String[] split;
	try
	{
	    split = coordsStr.split("°|′|″");
	}
	catch(NullPointerException e)
	{
	    return 0;
	}
	float[] splitFloat = new float[3];
	for(int i = 0; i < split.length - 1; i++)
	{
	    splitFloat[i] = Float.parseFloat(split[i]);
	}
	if(split[split.length - 1].equals("N") || split[split.length - 1].equals("E"))
	{
	    return splitFloat[0] + splitFloat[1]/60 + splitFloat[2]/3600;
	}
	else
	    return -(splitFloat[0] + splitFloat[1]/60 + splitFloat[2]/3600);
    }
    /**
     * Функция заполняющая PropertyType. Свойства находятся в том же порядке что и столбцы в экселе
     * @return Возвращает список свойст
     * @throws ExcelParserException Вызывается при ошибке парсера, какая то ошибка в экселе
     * @throws MySqlException
     */
    private List<PropertyType> createPropertyTypeList() throws ExcelParserException, MySqlException
    {
	List<PropertyType> ptList = new ArrayList<PropertyType>();
	List<PropertyType> createdList = propertyTypeDao.getPropertyTypeList();
	if(!createdList.isEmpty())
	{
	    for(int i = 0; i <createdList.size(); i++)
	    {
		if(!createdList.get(i).isDependingMonth())
		{
		    ptList.add(createdList.get(i));
		}
	    }
	    return ptList;
	}
	
	PropertyType propType = new PropertyType(); //против Stack Overflow.
	for(int j = startPositionProperty; j < devider; j++) //Количество столбцов property. В данном случае в эксельнике 8 столбцов property
	{
	    	
	    try
	    {
		if(propertyTypeDao.getPropertyTypeByName(exc.getStringField(sheetNumber, 0, j))!= null)
		{
		    ptList.add(propertyTypeDao.getPropertyTypeByName(exc.getStringField(sheetNumber, 0, j)));
		}
		else
		{
		    propType.setName(exc.getStringField(sheetNumber, 0, j)); // j - startPositionProperty на ноль переход
		    propType.setDependingMonth(false);
		    propType.setMinValue(0);
		    propType.setMaxValue(20);
		    try
		    {
			propertyTypeDao.addPropertyType(propType);
			ptList.add(propertyTypeDao.getPropertyTypeByName(exc.getStringField(sheetNumber, 0, j)));
		    } catch (MySqlException e)
		    {
		      throw e;
		    } // чтобы лист начинался с 0
		}
	    }
	    catch(NullPointerException e)
	    {
		ExcelParserException excParseExc = new ExcelParserException("Error in name of property sheet: " 
			+ exc.getSheetName(sheetNumber) + 
			"line 0" +
			"column" + j, j);
		logger.error("Error in name of property sheet: " + exc.getSheetName(sheetNumber) + 
			"line 0" +
			"column" + j);
		throw excParseExc;
	    	    
	    }			
	}
	return ptList;
    }
    private List<PropertyType> createDependingMonthPropertyList() throws MySqlException, ExcelParserException
    {
	List<PropertyType> ptDmpList = new ArrayList<PropertyType>(); 
	List<PropertyType> createdList = propertyTypeDao.getPropertyTypeList();
	if(createdList.size() > 6)
	{
	    for(int i = 0; i <createdList.size(); i++)
	    {
		if(createdList.get(i).isDependingMonth())
		{
		    ptDmpList.add(createdList.get(i));
		}
	    }
	    return ptDmpList;
	}
	PropertyType propType;
	//Создание propertyType для Dmp
	for(int j = devider; j < exc.getRowLenght(sheetNumber); j+=12)
	{
	    propType = new PropertyType();
	    if(propertyTypeDao.getPropertyTypeByName(exc.getStringField(sheetNumber, 0, j)) != null)
	    {
		ptDmpList.add(propertyTypeDao.getPropertyTypeByName(exc.getStringField(sheetNumber, 0, j)));
	    }
	    else
	    {
		propType.setName(exc.getStringField(sheetNumber, 0, j));
        	propType.setDependingMonth(true);
        	if(exc.getStringField(sheetNumber, 0, j).toLowerCase().equals("температура") || exc.getStringField(sheetNumber, 0, j).toLowerCase().equals("temperature"))
        	{
        	    propType.setMinValue(-35);
        	    propType.setMaxValue(35);
        	}
        	else
        	{
        	    propType.setMinValue(0);
        	    propType.setMaxValue(300);
        	}
        	
        	try
        	{
        	    propertyTypeDao.addPropertyType(propType);
        	    ptDmpList.add(propertyTypeDao.getPropertyTypeByName(exc.getStringField(sheetNumber, 0, j)));
        	}         	
        	catch (MySqlException e)
        	{
        	    throw e;
        	}// получение 10 + j в случае и записывание в list[j]
        	catch(NullPointerException e)
        	{
        	    ExcelParserException excParseExc = new ExcelParserException("Error in name of property sheet: " 
        		    + exc.getSheetName(sheetNumber) + 
        		    	"line 0" +
        		    	"column" + j, j);
        	    logger.error("Error in name of property sheet: " 
        		    		+ exc.getSheetName(sheetNumber) + 
        		    		"line 0" +
        		    		"column" + j);
        	    throw excParseExc;
        		        
        	}
    	}
	
    }
    return ptDmpList;

    }
    public Float getAverageValue(String cell)
    {
	Pattern pattern = Pattern.compile("\\d{1,3}\\s?-\\s?\\d{1,4}");
	Matcher matcher = pattern.matcher(cell);
	float result;
	if(matcher.find())
	{
	    String[] devider = cell.split("-");
	    result = (Float.parseFloat(devider[0]) + Float.parseFloat(devider[1]))/2;
	}
	else result = Float.parseFloat(cell);
	return result;
    }
}

