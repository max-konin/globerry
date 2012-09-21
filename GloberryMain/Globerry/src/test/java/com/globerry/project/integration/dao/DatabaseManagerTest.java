/**
 * 
 */
package com.globerry.project.integration.dao;

import java.util.ArrayList;
import java.util.List;

import com.globerry.project.dao.IDao;
import com.globerry.project.dao.IDatabaseManager;
import static org.junit.Assert.*;

import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Hotel;
import com.globerry.project.domain.Tour;
import com.globerry.project.service.DefaultDatabaseCreator;
import org.junit.Ignore;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.DirtiesContext;
import static org.mockito.Mockito.when;

/**
 * @author Artem
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/controllerTestContext.xml")
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class, ContextLoaderListener.class
})
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DatabaseManagerTest
{
    @Autowired
    private IDatabaseManager databaseManager;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private DefaultDatabaseCreator ddc;
    @Mock 
    private IDao<City> mockCityDao;
    
    private final Logger logger = Logger.getLogger(DatabaseManagerTest.class);
    
    @Before
    public void init()
    {
	MockitoAnnotations.initMocks(this);
    }

    @Test
    @Ignore
    public void clearDB()
    {
		databaseManager.cleanDatabase();
    }
   // @Ignore
    @Test
    public void testTours()
    {
	List<City> cityList = new ArrayList<City>();
	cityList.add(new City());
	when(mockCityDao.getAll(City.class)).thenReturn(cityList);
	ddc.initTours();
	Tour tour = ddc.generateTour();
	logger.info("\n" + tour.getName() +
		"\n" + tour.getDescription() +
		"\n" + tour.getTargetCityId() + 
		"\n" + tour.getDateEnd());
	Hotel hotel = ddc.generateHotel("Kremlin");
	logger.info("\n" + hotel.getName() +
		"\n" + hotel.getDescription() +
		"\n" + hotel.getCost());
    }

}
