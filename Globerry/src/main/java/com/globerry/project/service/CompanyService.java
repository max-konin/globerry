package com.globerry.project.service;

import java.util.List;

import javax.swing.JOptionPane;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;
import com.globerry.project.dao.CompanyDao;
import com.globerry.project.dao.TourDao;
import com.globerry.project.MySqlException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("companyService")
public class CompanyService implements ICompanyService
{
    @Autowired
    private CompanyDao companyDao;
    
    @Autowired 
    private TourDao tourDao;
    
    
    public void addCompany(Company company){
	try
	{
	    companyDao.addCompany(company);
	} catch (MySqlException e)
	{
	    e.printStackTrace();
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
    public void companyUpdate(Company oldCompany, Company newCompany)
    {

	    try
	    {
		companyDao.updateCompany(oldCompany, newCompany);
	    } catch (MySqlException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

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
    public List<Tour> getTourList(Company company)
    {
	// TODO Auto-generated method stub
	return company.getTourList();
    }


    @Override
    public void removeCompany(int id)
    {
	companyDao.removeCompany(id);
	
    }
}
