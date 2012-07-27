/**
 * 
 */
package com.globerry.project.integration.dao;

import com.globerry.project.dao.IDatabaseManager;
import static org.junit.Assert.*;

import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.globerry.project.domain.Hotel;
import com.globerry.project.domain.Tour;
import com.globerry.project.service.DefaultDatabaseCreator;
import org.junit.Ignore;
import org.springframework.test.annotation.DirtiesContext;

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
    
    private final Logger logger = Logger.getLogger(DatabaseManagerTest.class);

    @Test
    @Ignore
    public void clearDB()
    {
		databaseManager.cleanDatabase();
    }
    @Ignore
    @Test
    public void testTours()
    {
	/*ddc.initCities();
	ddc.initTours();
	Tour tour = ddc.generateTour();
	logger.info("\n" + tour.getName() +
		"\n" + tour.getDescription() +
		"\n" + tour.getTargetCityId() + 
		"\n" + tour.getDateEnd());
	Hotel hotel = ddc.generateHotel();
	logger.info("\n" + hotel.getName() +
		"\n" + hotel.getDescription() +
		"\n" + hotel.getCost());*/
    }

}
