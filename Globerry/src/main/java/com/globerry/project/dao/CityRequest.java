package com.globerry.project.dao;

import com.globerry.project.domain.Property;
/**
 * @author Сергей
 * Запрос на подбор городов по критериям
 */
public class CityRequest
{
    private Range range;
    private Property property;
    /**
     * Конструктор запроса
     * @param range область поиска
     * @param option опции поиска
     */
    public CityRequest(Range range, Property property){
	this.range = range;
	this.property = property;
    }
    public Range getRange()
    {
	return range;
    }
    public void setRange(Range range)
    {
	this.range = range;
    }
    public Property getOption()
    {
	return property;
    }
    public void setOption(Property property)
    {
	this.property = property;
    }
}
