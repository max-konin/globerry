/**
 * 
 */
package com.globerry.project.dao;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;

/**
 * @author Artem
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/daoTestContext.xml")
@TestExecutionListeners({

    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    ContextLoaderListener.class

})
public class TourTest
{

    	final Logger logger = Logger.getRootLogger();
        @Autowired
        private TourDao tourDao;
        @Autowired
        private CompanyDao companyDao;
        
        Tour tour = new Tour();
        
        @Test(timeout=1000)
        public void AddTest() throws Exception
        {
        	tour.setDescription("Its yandex");
        	tour.setName("yandex@yandex.ru");
        	tour.setCost(123);
        	tourDao.addTour(tour);
    	
        }
        @Test(timeout=1000)
        public void removeTourTest()
        {
        	Tour tour1 = new Tour();
        	tourDao.addTour(tour1);
        	tourDao.removeTour(tour1);
        }
        @Test(timeout=1000)
        public void updateTour()
        {
        	Tour tour = new Tour();
        	tourDao.addTour(tour);
        	Tour tr1 = new Tour();
        	tr1.setDescription("shokoladnie batonchiky snikers");
        	tr1.setName("Snikers");
        	tourDao.updateTour(tour, tr1);
    	
        }
        /*@Test(timeout=1000)
        public void updateTour()
        {
        	Tour tour = new Tour();
        	tourDao.addTour(tour);
        	Tour tr1 = new Tour();
        	tr1.setId(tour.getId());
        	tr1.setDescription("shokoladnie batonchiky snikers");
        	tr1.setName("Snikers");
        	tourDao.updateTour(tour, tr1);
    	
        }*/
        @Test(timeout=1000)
        public void getAndAddTour()
        {
            try{
        	Tour tour = tourDao.getTour(144);
        	tourDao.addTour(tour);
        	
            }catch(Exception e){
        	//fail("Откуда 144ый тур?О_о");
            }
        }
        @Test(timeout=1000)
        public void removeCompanyTourTest() throws MySqlException
        {
        	Tour tour1 = new Tour();
        	tour1.setDescription("DESCRIPTION1");
        	Company company = new Company();
        	tourDao.addTour(tour1);
        	company.getTourList().add(tour1);
        	companyDao.addCompany(company);
        	logger.info(tour1.getId());
        	tourDao.removeTour(tour1.getId());
        	//tourDao.removeTour(tour1);
        	companyDao.updateCompany(company);
        }


}

