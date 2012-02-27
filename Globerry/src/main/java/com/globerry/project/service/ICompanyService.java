package com.globerry.project.service;

import java.util.List;
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
    
    public List<Company> getCompanyList();
    
    public void update();
    
    public void companyUpdate(Company oldCompany, Company newCompany);
    
    public void addTour(Company company, Tour tour);
    
    public void removeTour(Tour tour);
    
    public List<Tour> getTourList(Company company);

}
