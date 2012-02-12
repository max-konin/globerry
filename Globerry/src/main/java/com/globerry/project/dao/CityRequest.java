package com.globerry.project.dao;

import com.globerry.project.domain.Option;
/**
 * @author Сергей
 * Запрос на подбор городов по критериям
 */
public class CityRequest
{
    private Range range;
    private Option option;
    /**
     * Конструктор запроса
     * @param range область поиска
     * @param option опции поиска
     */
    public CityRequest(Range range, Option option){
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
    public Option getOption()
    {
	return option;
    }
    public void setOption(Option option)
    {
	this.option = option;
    }
}
