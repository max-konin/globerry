
package com.globerry.project.integration.dao;

import com.globerry.project.dao.IDao;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;
import static org.junit.Assert.*;

import com.globerry.project.domain.*;
import com.globerry.project.service.gui.ISlider;
import com.globerry.project.service.gui.SelectBox;
import java.util.HashSet;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

/**
 * Тест Dao для сущности City
 * @author max
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/daoTestContext.xml")
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	ContextLoaderListener.class
})
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DaoForCityTest extends DaoTest<City>
{
    @Autowired
    IDao<City> cityDao;
    
    City city;       
    
    public DaoForCityTest()
    {
        super(City.class);
    }
    
    @Before
    public void setUp()
    {
        //Инициализация города------------------------------------------------------------------------------------------
        Interval[] values = {
                               new Interval(1,4),
                               new Interval(1,4),
                               new Interval(1,4),
                               new Interval(1,4),
                               new Interval(1,4),
                               new Interval(1,4),
                               new Interval(1,4),
                               new Interval(1,4),
                               new Interval(1,4),
                               new Interval(1,4),
                               new Interval(1,4),
                               new Interval(1,4),            
        };
        HashSet<Tag>    tags = new HashSet<Tag>();
        Temperature     temp = new Temperature();
        Mood            mood = new Mood();
        LivingCost      cost = new LivingCost();
        
        temp.init(values);
        mood.init(values);
        cost.init(values); 
        
        Tag tag1 = new Tag("1");
        Tag tag2 = new Tag("2");
        
        
        tags.add(tag1);
        tags.add(tag2);      
       
        city = new City(  "Berlin", 
                                2, 
                                1, 
                                2, 
                                3, 
                                new Interval (1, 5) , 
                                new Interval (1, 5),
                                2,    
                                2,
                                true,
                                true,
                                temp,
                                mood,
                                cost,
                                tags);
        //Конец инициализации города------------------------------------------------------------------------------------
        
        //Запись тегов в бд---------------------------------------------------------------------------------------------
        Transaction tx = null;
        try {
                tx = sessionFactory.getCurrentSession().beginTransaction();                       
                sessionFactory.getCurrentSession().save(tag1);
                sessionFactory.getCurrentSession().save(tag2);
                tx.commit();
        } catch (Exception e) {
                if (tx != null) {
                        tx.rollback();
                }
                e.printStackTrace();
        } 
        System.out.println("I'm there!");
       
        System.out.println(city);
    }   
  

    @Override
    protected City getEntity()
    {
        return city;
    }

    @Override
    protected IDao getDAO()
    {
        return cityDao;
    }

    @Override
    protected City getModifyEntity()
    {
        city.setArea(666);
        return city;
    }
    

    @Test
    @Override
    public void testUpdate()
    {
        System.out.println("I'm here!");
        
        City Old = getEntity();
        sessionFactory.getCurrentSession().save(Old);
        
        List<City> stateBefore = sessionFactory.getCurrentSession().createQuery("from City").list();              
        
        getDAO().update(getModifyEntity());
        
        City newCity = (City) sessionFactory.getCurrentSession().get(City.class, Old.getId());
        
        List<City> stateAfter  = sessionFactory.getCurrentSession().createQuery("from City").list(); 
        
        assertTrue(newCity.getArea() == 666);
        
        assertTrue((stateAfter.size() - stateBefore.size()) == 0);
    }
    
    
}
