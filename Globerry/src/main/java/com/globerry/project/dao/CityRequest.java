package com.globerry.project.dao;

import java.util.List;

import com.globerry.project.domain.Property;
/**
 * @author Сергей
 * Запрос на подбор городов по критериям
 */
public class CityRequest
{
    private Range range;
    private List<Property> properties;
    /**
     * Конструктор запроса
     * @param range область поиска
     * @param option опции поиска
     */
    public CityRequest(Range range, List<Property> properties){
	this.range = range;
	this.properties = properties;
    }
    public Range getRange()
    {
	return range;
    }
    public void setRange(Range range)
    {
	this.range = range;
    }
    public List<Property> getOption()
    {
	return properties;
    }
    public void setOption(List<Property> properties)
    {
	this.properties = properties;
    }
}
