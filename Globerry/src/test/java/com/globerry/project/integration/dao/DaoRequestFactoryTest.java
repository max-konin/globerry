/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.integration.dao;

import com.globerry.project.dao.QueryFactory;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;


import com.globerry.project.integration.dao.ContextLoaderListener;
import com.globerry.project.dao.QueryFactory;
import com.globerry.project.domain.*;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Interval;
import com.globerry.project.domain.LivingCost;
import com.globerry.project.domain.Mood;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.Temperature;
import com.globerry.project.service.gui.ISlider;
import com.globerry.project.service.gui.SelectBox;
import com.globerry.project.service.gui.Slider;
import com.globerry.project.service.service_classes.GloberryGuiContext;
import com.globerry.project.service.service_classes.IApplicationContext;
import java.util.HashSet;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
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
@ContextConfiguration("/daoTestContext.xml")
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	ContextLoaderListener.class
})
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DaoRequestFactoryTest
{
    City city;
    
    @Autowired
    SessionFactory sessionFactory;
    
    
    IApplicationContext appContext = mock(IApplicationContext.class);
    
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
        
        //Создание mock для ApplicationContext--------------------------------------------------------------------------
        SelectBox mockWhat = mock(SelectBox.class);
        when(mockWhat.getValue()).thenReturn(1);
        when(appContext.getWhatTag()).thenReturn(mockWhat);
        
        SelectBox mockWho = mock(SelectBox.class);
        when(mockWho.getValue()).thenReturn(2);
        when(appContext.getWhoTag()).thenReturn(mockWho);
        
        SelectBox mockWhen = mock(SelectBox.class);
        when(mockWhen.getValue()).thenReturn(1);
        when(appContext.getWhenTag()).thenReturn(mockWhen);
        ISlider mockSlider = mock(ISlider.class);
        when(appContext.getSlidersByName(anyString())).thenReturn(mockSlider);        
        when(mockSlider.getRightValue()).thenReturn(4);
        when(mockSlider.getLeftValue()).thenReturn(1);
    }
    
    
    /**
     * Test of createCityRequest method, of class QueryFactory.
     */
    @Test
    public void testCreateCityRequest()
    {
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
        QueryFactory instanse = new QueryFactory();
        System.out.println(instanse.createCityRequest(appContext));
        tx = sessionFactory.getCurrentSession().beginTransaction();
        List<City> cityList = sessionFactory.getCurrentSession()
                                            .createQuery(instanse.createCityRequest(appContext)).list();
        
        tx.commit();
        
        assertTrue(cityList.size() == 1);
        
    }
}
