package com.globerry.project.service;

import java.util.List;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;

public interface ICompanyService
{
    public void addCompany(Company company);
    
    public void removeCompany(Company company);
    
    public List<Company> getCompanyList();
    
    public void update();
    
    public void companyUpdate(Company oldCompany, Company newCompany);
    
    public void addTour(Tour tour);
    
    public void removeTour(Tour tour);
    
    public List<Tour> getTourList();

}
