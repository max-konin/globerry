package com.globerry.project.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.globerry.project.domain.Month;
import com.globerry.project.domain.Property;
import com.globerry.project.domain.Tag;
/**
 * @author Сергей
 * Запрос на подбор городов по критериям
 */
public class CityRequest
{
    private Range range;
    private List<PropertySegment> properties;
    private List<Tag> tags;
    private Month month;
    /**
     * Конструктор запроса
     * @param range область поиска
     * @param option опции поиска
     * @return 
     */
    public CityRequest(Range range, List<PropertySegment> properties,List<Tag> tags, Month month){
	this.range = range;
	this.properties = properties;
	this.tags = tags;
	this.month = month;
    }
    @Deprecated
    public static CityRequest CityRequestGenerate(Range range, List<Property> properties){
	Iterator<Property> iteratorProperties = properties.iterator();
	List<PropertySegment> propertySegment = new ArrayList<PropertySegment>();
	while(iteratorProperties.hasNext()){
	    propertySegment.add(new PropertySegment(iteratorProperties.next()));
	}
	return new CityRequest(range,propertySegment, new ArrayList<Tag>(), Month.APRIL);
    }
    public Range getRange()
    {
	return range;
    }
    public void setRange(Range range)
    {
	this.range = range;
    }
    public List<PropertySegment> getOption()
    {
	return properties;
    }
    public void setOption(List<PropertySegment> properties)
    {
	this.properties = properties;
    }
    public List<Tag> getTags()
    {
	return tags;
    }
    public void setTags(List<Tag> tags)
    {
	this.tags = tags;
    }
    public Month getMonth()
    {
	return month;
    }
    public void setMonth(Month month)
    {
	this.month = month;
    }
}
