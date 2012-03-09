package com.globerry.project.service;

import java.util.Observable;

import org.springframework.stereotype.Service;

import com.globerry.project.domain.Month;

@Service
public class Calendar extends Observable implements ICalendar
{

    private Month currentMonth;
    @Override
    public void changeMonth(Month month)
    {
	super.notifyObservers(new EventUI(this));
	
    }
    public Month getMonth(){
	return currentMonth;
    }

}
