/**
 * 
 */
package com.globerry.project.dao;

import static org.junit.Assert.*;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.globerry.project.service.DefaultDatabaseCreator;

/**
 * @author Artem
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/daoTestContext.xml")
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class, ContextLoaderListener.class
})
public class DatabaseManagerTest
{
    @Autowired
    private IDatabaseManager databaseManager;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private DefaultDatabaseCreator ddc;

    @Test
    public void clearDB()
    {
		databaseManager.cleanDatabase();
    }
    //@Test
/*    public void testTours()
    {
	ddc.initCities();
	ddc.initTours();
    }*/

}
