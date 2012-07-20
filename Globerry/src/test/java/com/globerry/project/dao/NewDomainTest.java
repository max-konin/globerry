
package com.globerry.project.dao;


import com.globerry.project.domain.Mood;
import com.globerry.project.domain.Interval;
import com.globerry.project.domain.Temperature;
import com.globerry.project.domain.LivingCost;
import com.globerry.project.domain.City;
import com.globerry.project.MySqlException;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.Tag;
import java.util.EnumMap;
import java.util.HashSet;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

/**
 *
 * @author max
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/contextForTestNewDomain.xml")
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	ContextLoaderListener.class
})
public class NewDomainTest
{
    @Autowired
    SessionFactory sessionFactory;
    @Test
    public void doNothing()
    {
        City city = new City("22", 
                                2, 
                                1, 
                                2, 
                                3, 
                                new Interval () , 
                                new Interval (),
                                2,    
                                2,
                                true,
                                true,
                                new Temperature (),
                                new Mood (),
                                new LivingCost (),
                                new HashSet<Tag> ());
        Transaction tx = null;
		try {
			tx = sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().save(city);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
                        e.printStackTrace();
		}
                
        assertTrue(true);
    }
}
