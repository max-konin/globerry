package com.globerry.project.service.interfaces;

import com.globerry.project.domain.Month;

/**
 * 
 * @author Сергей Крупин
 *
 */
public interface ICalendar
{
    /**
     * изменяет значение месяца
     * @param month
     */
    public void changeMonth(Month month);
}
