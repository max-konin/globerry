/**
 * 
 */
package com.globerry.project.service.admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
import com.globerry.project.dao.Dao;
import com.globerry.project.dao.IDao;
import com.globerry.project.domain.City;

import com.globerry.project.domain.Interval;
import com.globerry.project.domain.LivingCost;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.Mood;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.Temperature;
import com.globerry.project.service.DefaultDatabaseCreator;

/**
 * @author Artem
 *
 */
@Service("adminParser")
public class AdminParser implements IAdminParser
{

    
    @Autowired
    private IDao<City> cityDao;
    @Autowired 
    private IDao<Tag> tagDao;
    @Autowired
    private IDao<PropertyType> propertyTypeDao;
    @Autowired
    private DefaultDatabaseCreator defaultDatabaseCreator;
    
    private List<String> excelBugsList = new ArrayList<String>();
    
    private List<String> wikiBugsList = new ArrayList<String>();
    
    private Excel exc;
    
    final int livingCostStartPosition = 17;
    
    final int moodStartPosition = 31;
    
    final int tempStartPosition = 5;
  
    

    private Boolean flag = true;
    //Здесь создается таблица PropertyType. В качестве name берётся заголовок столбца в экселе
    final int sheetNumber = 0;
    final int startPositionProperty = 5; //Стартовая позиция для property
    final int devider = 11; // Перменная которая разделяет Property от DependingMonthProperty в excel. Cтартовая позиция для Dmp
	//final int tagsDevider = 47;//переменная которая отделяет dependingMonthProperty от tag.
    
    protected static Logger logger = Logger.getLogger(AdminParser.class);

    @Override
    public void updateCities(Excel exc) throws IOException
    {
	
	this.exc = exc;
	if(tagDao.getAll(Tag.class).isEmpty())
	    defaultDatabaseCreator.initTags();
	if(propertyTypeDao.getAll(PropertyType.class).isEmpty())
	    defaultDatabaseCreator.initPropertyType();
	cityParse2();
	//cityParse();
	//eventParse();
	//tagParse();
    }
    /**
     * Обновляет ВСЕ города в таблице. Обновляются area, lattitude, longitude, population 
     * и два вспомогательных - isValid и message
     * @throws IOException 
     */
    @Override
    public void updateWikiContent() throws IOException
    {
	File bugReportFile = new File("WikipediaBugs.txt");
	bugReportFile.createNewFile();
	PrintWriter writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(bugReportFile)));
	List<City> cityList = new ArrayList<City>();
	cityList = cityDao.getAll(City.class);
	Iterator<City> it = cityList.iterator();
	while(it.hasNext())
	{
	    City city = it.next();
	    com.globerry.htmlparser.City cityWiki;
	    try
	    {
		cityWiki = new com.globerry.htmlparser.City(city.getName().replace(", an island", "")
                                                                          .replaceAll(" ","_"));
	    }
	    catch(NullPointerException e)
	    {
		logger.error("Обработанный эксепшн");
		break;
	    }
	    logger.info(cityWiki.getLatitude());
            
            if((city.getLongitude() == 0) && (city.getLatitude() == 0))
            {
                city.setLatitude(coordsTransform(cityWiki.getLatitude()));
                city.setLongitude(coordsTransform(cityWiki.getLongitude()));
            }
	    city.setPopulation(cityWiki.getPopulation());
	    city.setArea(cityWiki.getArea());
	   
           
	    if(city.getLatitude() == 0)
	    {
		wikiBugsList.add("Ошибка в городе " + city.getName() 
                                + " этот город не найден в википедии, возможно у него неправильное имя");
		writer.println("Ошибка в городе " + city.getName() 
                                + " этот город не найден в википедии, возможно у него неправильное имя");
	    }
	    else if(!city.isIsValid())
	    {
		wikiBugsList.add("Ошибка в городе " + city.getName()
                                + " возможно это поможет Вам. " + city.getMessage());
		writer.println("Ошибка в городе " + city.getName() 
                                + " возможно это поможет Вам. " + city.getMessage());
	    }
	    
            if (city.getTemperature() == null)
            {
                city.setMessage(cityWiki.getMessage());
                city.setIsValid(cityWiki.getIsValid());
                float[] temperature = cityWiki.getTemperature();
                if(temperature != null)
                {
                    Temperature temp = new Temperature();
                    for(int i = 0; i < 12; i++)
                    {

                        temp.setValue(Month.values()[i], new Interval((int)temperature[i],(int) temperature[i]));
                        city.setTemperature(temp);
                    }
                }
                else 
                {
                    wikiBugsList.add("Ошибка в городе " + city.getName() + ": не найденна температура");
                    writer.println("Ошибка в городе " + city.getName() + ": не найденна температура");
                }
            }
	    cityDao.update(city);              
            
	}
	writer.close();
	
    }

    /**
     * Извлекает и добавляет тэги к городу
     * Добавляем к городу тег Один Если секс от 1 до 3, безопасность от 1 до 3, настроение от 2 до 3
	Добавляем к городу тег Вдвоем Если секс от 2 до 3, безопасность от 1 до 3, настроение от 1 до 3
	Добавляем к городу тег Семья - секс от 1 до 3, безопасность 3, настроение от 1 до 3
	Добавляем к городу тег компания - секс от 2 до 3, настроение от 2 до 3, безопасность от 1 до 3
     * @param cell
     */
    private void stringToExcelDocumentToAddTag(City city, int i, int j) throws ExcelParserException
    {

	try
	{

	    float security = city.getSecurity();
	    float sex = city.getSex();
	    
	    //Добавляем к городу тег Один Если секс от 1 до 3, безопасность от 1 до 3, настроение от 2 до 3
	    if(sex > 0 && security > 0)
	    {
		Tag tagAlone = (Tag) tagDao.getByQuery("FROM Tag tag WHERE tag.id='" + 1 + "'").get(0);//tagDao.getTagById(1);
		city.getTagList().add(tagAlone);
	    }
	    if(sex > 1 && security > 0) 
	    {
		Tag tagCouple = (Tag) tagDao.getByQuery("FROM Tag tag WHERE tag.id='" + 4 + "'").get(0);
		Tag tagFriends =  (Tag) tagDao.getByQuery("FROM Tag tag WHERE tag.id='" + 2 + "'").get(0);
		city.getTagList().add(tagFriends);
		city.getTagList().add(tagCouple);
	    }
	    if(security == 3 && sex > 0)
	    {
		Tag tagFamily =  (Tag) tagDao.getByQuery("FROM Tag tag WHERE tag.id='" + 3 + "'").get(0);
		city.getTagList().add(tagFamily);
	    }
	    try
	    {
		String cell = exc.getStringField(sheetNumber, i, j);
		String[] tagsString = cell.split(",");
		for(int k = 0; k < tagsString.length; k++)
		{
		    int number = Integer.parseInt(tagsString[k]) + 4;
		    Tag tag = (Tag) tagDao.getByQuery("FROM Tag tag WHERE tag.id='" + number + "'").get(0);
		    city.getTagList().add(tag);
		}
	    }
	    catch(IllegalStateException e)
	    {
		int number = (int)exc.getFloatField(sheetNumber, i, j) + 4;
		Tag tag = (Tag) tagDao.getByQuery("FROM Tag tag WHERE tag.id='" + number + "'").get(0);
		city.getTagList().add(tag);
	    }

	}
	catch(Exception e)
	{
	    //e.printStackTrace();
	    excelBugsList.add("Ошибка в"  + i+1 + ", " + j+1 + " " + e.toString());
	    ExcelParserException excelException = new ExcelParserException("Ошибка в"  + i+1 + ", " + j+1 + " " + e.toString(), i);
	    throw excelException;
	}
	
    }


    private void cityParse2() throws IOException
    {
	
	File bugReportFile = new File("ExcelBugs.txt");
	if(!bugReportFile.exists()) bugReportFile.createNewFile();
	PrintWriter writer = new PrintWriter(
		    new BufferedOutputStream(
			    new FileOutputStream(bugReportFile)));
	int i = 2;
        logger.info(exc.getLenght(0));
	while (i < exc.getLenght(0))
	{
	    try
	    {
                    
        	    City city = new City();
        	    city.setName(exc.getStringField(sheetNumber, i, 2));
        	    city.setRu_name(exc.getStringField(sheetNumber, i, 3));
        	    city.setFoodCost(getIntervalFromCell(i, 29, true));
        	    city.setAlcoCost(getIntervalFromCell(i,30, true));
        	    city.setRussian(cellToBool(i, 43));
        	    city.setVisa(cellToBool(i, 44));
        	    city.setSex((int)exc.getFloatField(sheetNumber, i, 45));
        	    city.setSecurity((int)exc.getFloatField(sheetNumber, i, 46));
        	    logger.info(i);                    
                    Temperature temp = new Temperature();                    
                    try{
                        for(int k = tempStartPosition; k < 12 + tempStartPosition; k++)
                            temp.setValue(Month.values()[k - tempStartPosition], 
                                          getIntervalFromCell(i, k, false));
                    }
                    catch(Exception e)
                    {
                        temp = null;
                    }                    
                    city.setTemperature(temp);
                    
        	    LivingCost livinCost = new LivingCost();
        	    for(int k = livingCostStartPosition; k < 12 + livingCostStartPosition; k++)
        	    {
        		livinCost.setValue(Month.values()[k - livingCostStartPosition], getIntervalFromCell(i, k, false));
        	    }
        	    city.setLivingCost(livinCost);
        	    Mood mood = new Mood();
        	    for(int k = moodStartPosition; k < 12 + moodStartPosition; k++)
        	    {
        		mood.setValue(Month.values()[k - moodStartPosition], getIntervalFromCell(i, k, false));
        	    }
        	    city.setMood(mood);
        	    stringToExcelDocumentToAddTag(city, i, 4);
        	    cityDao.add(city);
	    }
	    catch(ExcelParserException e)
	    {
		 writer.println(e.getDescription());
	    }
	    catch(IllegalStateException e)
	    {
		int si = i+1;
		excelBugsList.add("Ошибка в"  + si + " строке " + e.toString());
		writer.println("Ошибка в"  + si + " строке " + e.toString());
	    }
	    catch(NullPointerException e)
	    {
		int si = i+1;
		excelBugsList.add("Ошибка в"  + si + " строке " + e.toString());
		writer.println("Ошибка в"  + si + " строке " + e.toString());
	    }
	    i++;
	    
	}
	writer.close();
    }
    private boolean cellToBool(int i, int j) throws ExcelParserException
    {
	try
	{
	    double result = exc.getFloatField(sheetNumber, i, j);
	    if(result > 0.5) return true;
	    else return false;
	}
	catch(IllegalStateException e)
	{
	    String result = exc.getStringField(sheetNumber, i, j);
	    if(result.toLowerCase().equals("да")) return true;
	    else return false;
	}
	catch(Exception e)
	{
	    int si = i+1;
	    int sj = j+1;
	    excelBugsList.add("Ошибка в "  + si + ", " + sj + " " + e.toString());
	    ExcelParserException excelException = new ExcelParserException("Ошибка в "  + si + ", " + sj + " " + e.toString(), i);
	    throw excelException;
	}
    }
    private Interval getIntervalFromCell(int i, int j, Boolean isNeedToMaximize) throws ExcelParserException
    {
	Interval interval = new Interval();
	try
	{
	    Double cell = exc.getFloatField(sheetNumber, i, j);
	    interval.setLeft((int)Math.round(cell));
	    if(isNeedToMaximize)
		interval.setRight(30); // Специально для foodcost и для alchogol
	    else
		interval.setRight((int)Math.round(cell));
	   
	}
	catch(IllegalStateException e)
	{
	    String cell = exc.getStringField(sheetNumber, i, j);
	    Pattern pattern = Pattern.compile("\\d{1,3}\\s?-\\s?\\d{1,4}");
	    Matcher matcher = pattern.matcher(cell);
	    if(matcher.find())
	    {
		String[] devider = cell.split("-");
                try {
                    interval.setLeft(Integer.parseInt(devider[0].trim()));
                    interval.setRight(Integer.parseInt(devider[1].trim()));
                }
                catch(NumberFormatException ex)
                {
                    int si = i+1;
                    int sj = j+1;
                    excelBugsList.add(" Ошибка в  " + si + ", " + sj + " " + ex.toString());
                }
                
	    }
	    else
	    {
		int si = i+1;
		int sj = j+1;
		excelBugsList.add(" Ошибка в  " + si + ", " + sj + " " + e.toString());
		ExcelParserException excelException = new ExcelParserException(" Ошибка в  " + si + ", " + sj + " " + e.toString(), i);
		throw excelException;
	    }
	}
	catch(Exception e)
	{
	    int si = i+1;
	    int sj = j+1;
	    excelBugsList.add(" Ошибка в " + si + ", " + sj + " " + e.toString());
	    ExcelParserException excelException = new ExcelParserException(" Ошибка в " + si + ", " + sj + " " + e.toString(), i);
	    throw excelException;
	}
	return interval;
    }

    
    /**
     * Функция осуцествляет поиск по таблице event_id city_id в документе
     * @param event_id передаётся номер евента
     * @param exc Сам файл. 
     * @return	массив int где содержаться все id городов связанных с этим event
     */
    @Deprecated
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
     * преобразует координаты из типа String из стандарта в float не стандарт 
     * @return float координаты не стандарта
     */
    public float coordsTransform(String coordsStr)
    {
	
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(coordsStr);
        int start= 0, j = 0;
	float[] splitFloat = new float[3];
        while (matcher.find(start) && j < 3) {
            String value = coordsStr.substring(matcher.start(), matcher.end());
            splitFloat[j] = Integer.parseInt(value);            
            start = matcher.end();     
            j++;
        }
	
	if ((coordsStr.indexOf('N') != 0) || (coordsStr.indexOf('E') != 0))
        {
	    return splitFloat[0] + splitFloat[1]/60 + splitFloat[2]/3600;
	}
	else
	    return -(splitFloat[0] + splitFloat[1]/60 + splitFloat[2]/3600);
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
    public List<String> getExcelBugsList()
    {
	return excelBugsList;
    }
    public List<String> getWikiBugsList()
    {
	return wikiBugsList;
    }

}

