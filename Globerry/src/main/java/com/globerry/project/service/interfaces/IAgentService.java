package com.globerry.project.service.interfaces;

import java.util.Set;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;

public interface IAgentService
{   
    public void addTour(Tour tour);
    
    public void removeTour(Tour tour);
    
    public Company returnCurrentCompany();
    
    public void updateTour(Tour oldTour, Tour newTour);
    
    public void companyUpdate(Company company) 	throws MySqlException;
}
