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

import com.globerry.project.dao.IDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;
import com.globerry.project.service.admin.CompanyPage;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CompanyPageTest
{
    @Mock
    private IDao<Company> mockCompanyDao;
    
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
	verify(mockCompanyDao).getAll(Company.class);
    }

    @Test
    public void testGetElemById()
    {
	page.getElemById(Mockito.anyMap(), Mockito.anyInt());
	verify(mockCompanyDao).getById((Class)Mockito.any(), Mockito.anyInt());
    }
    @Test
    public void testUpdateCity()
    {
	page.updateElem(Mockito.anyObject());
	verify(mockCompanyDao).update((Company) Mockito.anyObject());
    }


}