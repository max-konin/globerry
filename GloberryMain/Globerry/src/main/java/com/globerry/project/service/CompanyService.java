package com.globerry.project.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;

import com.globerry.project.service.interfaces.ICompanyService;
import com.globerry.project.MySqlException;
import com.globerry.project.dao.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("companyService")
public class CompanyService implements ICompanyService
{
    @Autowired
    private IDao<Company> companyDao;
    
    @Autowired 
    private IDao<Tour> tourDao;
    
    
    public void addCompany(Company company) throws MySqlException
    {	
	    companyDao.add(company);	
    }
    
    
    public List<Company> getCompanyList(){
	return companyDao.getAll(Company.class);
    }

    @Override
    public void removeCompany(Company company)
    {
	companyDao.remove(company);
	
    }

    @Override
    public void update()
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void companyUpdate(Company oldCompany, Company newCompany) throws MySqlException
    {
	companyDao.update(newCompany);
    }

    @Override
    public void addTour(Company company, Tour tour)
    {
	company.getTourList().add(tour);
	companyDao.update(company);
	
    }

    @Override
    public void removeTour(Tour tour)
    {
	tourDao.remove(tour);
	
    }

    @Override
    public Set<Tour> getTourList(Company company)
    {
	// TODO Auto-generated method stub
	return company.getTourList();
    }

    @Override
    public void removeCompany(int id)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Company getCompanyByName(String name)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Company getCompanyByEmail(String email)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    
    
   
    
}
