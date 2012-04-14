package com.globerry.project.service;

import java.util.Observable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globerry.project.domain.Month;
import com.globerry.project.service.interfaces.ICalendar;

@Service
@Scope("session")
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
