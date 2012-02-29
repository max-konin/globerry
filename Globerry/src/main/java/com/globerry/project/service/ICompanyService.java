package com.globerry.project.service;

import java.util.List;
import java.util.Set;

import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;
/**
 * 
 * @author Artem
 *
 */
public interface ICompanyService
{
    public void addCompany(Company company);
    
    public void removeCompany(Company company);
    
    public void removeCompany(int id);
    
    public Set<Company> getCompanyList();
    
    public void update();
    
    public void companyUpdate(Company oldCompany, Company newCompany);
    
    public void addTour(Company company, Tour tour);
    
    public void removeTour(Tour tour);
    
    public Set<Tour> getTourList(Company company);

}
