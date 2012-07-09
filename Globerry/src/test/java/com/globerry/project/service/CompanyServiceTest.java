/**
 * 
 */
package com.globerry.project.service;

import com.globerry.project.MySqlException;
import com.globerry.project.dao.ICompanyDao;
import com.globerry.project.dao.ITourDao;
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
    private ICompanyDao mockCompanyDao;
    
    @Mock 
    private ITourDao mockTourDao;   
    
    @InjectMocks
    private CompanyService cmpService = new CompanyService();
    
    @Before
    public void init()
    {
       Company company = new Company();
       company.setName("anyLogin");
       company.setEmail("anyEmail");
       MockitoAnnotations.initMocks(this);
       
       when(mockCompanyDao.getCompanyByLogin("anyLogin")).thenReturn(company);
       when(mockCompanyDao.getCompanyByEmail("anyEmail")).thenReturn(company);
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
        verify(mockCompanyDao).addCompany(company);
    }
    
    @Test
    public void testGetCompanyList()
    {
        cmpService.getCompanyList();
        verify(mockCompanyDao).getCompanyList();
    }
    
    @Test
    public void testRemoveCompany()
    {
        Company company  = new Company();
        cmpService.removeCompany(company);
        verify(mockCompanyDao).removeCompany(company);
    }
    
    @Test
    public void testUpdateCompany() throws MySqlException
    {
        Company company  = new Company();
        cmpService.companyUpdate(company, company);
        verify(mockCompanyDao).updateCompany(company);
    }
    
    @Test
    public void addTour()
    {
        Company company  = new Company();        
        Tour tour = new Tour();
        cmpService.addTour(company, tour);        
        verify(mockCompanyDao).updateCompany(company);
        assertTrue(company.getTourList().size() == 1);
    }
    
    @Test
    public void testRemoveCompanyById() throws MySqlException
    {
        int id = 100;
        cmpService.removeCompany(id);
        verify(mockCompanyDao).removeCompany(id);
    }
    
    @Test
    public void testGetCompanyByName() throws MySqlException
    {
        int id = 100;
        Company result = cmpService.getCompanyByName("anyLogin");
        verify(mockCompanyDao).getCompanyByLogin("anyLogin");
        assertTrue("anyLogin".equals(result.getName()));
    }
    
     @Test
    public void testGetCompanyByEmail() 
    {
        int id = 100;
        Company result = cmpService.getCompanyByEmail("anyEmail");
        verify(mockCompanyDao).getCompanyByEmail("anyEmail");
        assertTrue("anyEmail".equals(result.getEmail()));
    }

}
