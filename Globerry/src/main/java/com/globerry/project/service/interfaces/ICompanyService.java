package com.globerry.project.service.interfaces;

import java.util.List;
import java.util.Set;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;
/**
 * 
 * @author Artem
 *
 */
public interface ICompanyService
{
    public void addCompany(Company company) throws MySqlException;
    
    public void removeCompany(Company company);
    
    public void removeCompany(int id);
    
    public Set<Company> getCompanyList();
    
    public void update();
    
    public void companyUpdate(Company oldCompany, Company newCompany) throws MySqlException;
    
    public void addTour(Company company, Tour tour);
    
    public void removeTour(Tour tour);
    
    public Set<Tour> getTourList(Company company);

}
