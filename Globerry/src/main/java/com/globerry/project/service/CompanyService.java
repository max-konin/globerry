package com.globerry.project.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;
import com.globerry.project.dao.CompanyDao;
import com.globerry.project.dao.ICompanyDao;
import com.globerry.project.dao.ITourDao;
import com.globerry.project.dao.TourDao;
import com.globerry.project.service.interfaces.ICompanyService;
import com.globerry.project.MySqlException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("companyService")
public class CompanyService implements ICompanyService
{
    @Autowired
    private ICompanyDao companyDao;
    
    @Autowired 
    private ITourDao tourDao;
    
    
    public void addCompany(Company company) throws MySqlException
    {
	try
	{
	    companyDao.addCompany(company);
	} catch (MySqlException e)
	{
	    throw e;
	}
    }
    
    
    public List<Company> getCompanyList(){
	return companyDao.getCompanyList();
    }

    @Override
    public void removeCompany(Company company)
    {
	companyDao.removeCompany(company);
	
    }

    @Override
    public void update()
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void companyUpdate(Company oldCompany, Company newCompany) throws MySqlException
    {
		companyDao.updateCompany(newCompany);
    }

    @Override
    public void addTour(Company company, Tour tour)
    {
	company.getTourList().add(tour);
	try
	{
	    companyDao.addCompany(company);
	} catch (MySqlException e)
	{
		companyDao.updateCompany(company);		
	}
	
    }

    @Override
    public void removeTour(Tour tour)
    {
	tourDao.removeTour(tour);
	
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
	companyDao.removeCompany(id);
	
    }
    
    @Override
    public Company getCompanyByName(String name) {
	return companyDao.getCompanyByLogin(name);
    }
    
    @Override
    public Company getCompanyByEmail(String email) {
	return companyDao.getCompanyByEmail(email);
    }
    
}
