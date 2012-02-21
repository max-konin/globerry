package com.globerry.project.dao;

import java.util.List;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.Temperature;
/**
 * @author Сергей Крупин
 */
public interface ITemperatureDao
{
    /**
     * Получить среднемесячную температуру для конкретного города в конкретный месяц
     * @param month Месяц
     * @param city Город
     * @return Среднемесячная температура
     */
    Temperature getTemp(Month month, City city);
    /**
     * Получить список среднемесячных температур для конкретного города по месяцам
     * @param city Город
     * @return Список температур
     */
    List<Temperature> getTempList(City city);
    /**
     * Получить список температур для всех городов в конкретный месяц
     * @param month Месяц
     * @return Список температур
     */
    List<Temperature> getTempList(Month month);
    void setTemp(Temperature temperature);
}
