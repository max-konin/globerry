/**
 * 
 */
package com.globerry.project.service;

import com.globerry.project.MySqlException;
import com.globerry.project.dao.IDao;

import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author max
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest
{
    @Mock 
    private IDao<Company> companyDao;
    
    @Mock 
    private IDao<Tour> tourDao;   
    
    @InjectMocks
    private CompanyService cmpService = new CompanyService();
    
    @Before
    public void init()
    {
       Company company = new Company();
       company.setName("anyLogin");
       company.setEmail("anyEmail");
       MockitoAnnotations.initMocks(this);       
      
    }
    
    private String getStringGenerator()
    {  
	
      final int LENGHT = 8;  
      StringBuffer sb = new StringBuffer();  
      for (int x = 0; x <LENGHT; x++)  
      {  
        sb.append((char)((int)(Math.random()*26)+97));  
      }  
      return sb.toString();  
    } 
    
    @Test
    public void testAddCompany() throws MySqlException
    {
        Company company  = new Company();
        cmpService.addCompany(company);
        verify(companyDao).add(company);
    }
    
    @Test
    public void testGetCompanyList()
    {
        cmpService.getCompanyList();
        verify(companyDao).getAll(Company.class);
    }
    
    @Test
    public void testRemoveCompany()
    {
        Company company  = new Company();
        cmpService.removeCompany(company);
        verify(companyDao).remove(company);
    }
    
    @Test
    public void testUpdateCompany() throws MySqlException
    {
        Company company  = new Company();
        cmpService.companyUpdate(company, company);
        verify(companyDao).update(company);
    }
    
    @Test
    public void addTour()
    {
        Company company  = new Company();        
        Tour tour = new Tour();
        cmpService.addTour(company, tour);        
        verify(companyDao).update(company);
        assertTrue(company.getTourList().size() == 1);
    }
    
}
