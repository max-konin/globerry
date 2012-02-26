/**
 * 
 */
package com.globerry.project.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;

/**
 * @author Artem
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/app-context.xml")
@TestExecutionListeners({

    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    ContextLoaderListener.class

})
public class TourTest
{

        @Autowired
        private TourDao tourDao;
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
        public void updateCompany()
        {
        	Tour tour = new Tour();
        	tourDao.addTour(tour);
        	Tour tr1 = new Tour();
        	tr1.setDescription("shokoladnie batonchiky snikers");
        	tr1.setName("Snikers");
        	tourDao.updateTour(tour, tr1);
    	
        }
        @Test(timeout=1000)
        public void getAndAddTour()//идевательство
        {
            Tour tour = tourDao.getTour(14);
            tourDao.addTour(tour);
        }


}

