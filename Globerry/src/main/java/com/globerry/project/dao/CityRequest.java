package com.globerry.project.dao;

import com.globerry.project.domain.Property;
/**
 * @author Сергей
 * Запрос на подбор городов по критериям
 */
public class CityRequest
{
    private Range range;
    private Property option;
    /**
     * Конструктор запроса
     * @param range область поиска
     * @param option опции поиска
     */
    public CityRequest(Range range, Property option){
	this.range = range;
	this.option = option;
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
	return option;
    }
    public void setOption(Property option)
    {
	this.option = option;
    }
}
