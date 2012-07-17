package com.globerry.project.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.globerry.project.dao.ICompanyDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Tour;
import com.globerry.project.service.admin.CompanyPage;
import com.globerry.project.service.admin.EventPage;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CompanyPageTest
{
    @Mock
    private ICompanyDao mockCompanyDao;
    
    @InjectMocks
    private CompanyPage page = new CompanyPage();

    private Map<String, Object> map = new HashMap<String, Object>();
    @Before
    public void init()
    {
	MockitoAnnotations.initMocks(this);
    }
    @Test
    public void setListTest()
    {
	page.setList(map);
	verify(mockCompanyDao).getCompanyList();
    }
    @Test
    public void testRemoveElem()
    {
	//when(mockCityDao.removeCity(1))
	page.removeElem(Mockito.anyInt());
	verify(mockCompanyDao).removeCompany(Mockito.anyInt());
    }
    @Test
    public void testGetElemById()
    {
	page.getElemById(map, Mockito.anyInt());
	verify(mockCompanyDao).getCompanyById(Mockito.anyInt());
    }
    @Test
    public void testUpdateCity()
    {
	page.updateElem(Mockito.anyObject());
	verify(mockCompanyDao).updateCompany((Company) Mockito.anyObject());
    }
    @Test
    public void testGetRelations()
    {
	Company company = new Company();
	Tour tour = new Tour();
	company.getTourList().add(tour);
	when(mockCompanyDao.getCompanyById(Mockito.anyInt())).thenReturn(company);
	page.getRelation(map, Mockito.anyInt());
	verify(mockCompanyDao).getCompanyById(Mockito.anyInt());
	assertTrue(map.size() == 1);
	
    }

}
