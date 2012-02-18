package com.globerry.project.service;

import java.util.List;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;
import com.globerry.project.dao.CompanyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CompanyService implements ICompanyService
{
    @Autowired
    private CompanyDao companyDao;
    
    
    public void addCompany(Company company){
	companyDao.addCompany(company);
    }
    
    
    public List<Company> getCompanyList(){
	return companyDao.getCompanyList();
    }

    @Override
    public void removeCompany(Company company)
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void update()
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void companyUpdate(Company oldCompany, Company newCompany)
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void addTour(Tour tour)
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void removeTour(Tour tour)
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public List<Tour> getTourList()
    {
	// TODO Auto-generated method stub
	return null;
    }
}
