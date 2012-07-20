/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.dao;

import com.globerry.project.domain.City;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.SessionFactory;
import org.junit.*;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author max
 */
public abstract class DaoTest<T>
{
    private Class<T> type;
    abstract protected T getEntity();
    abstract protected T getModifyEntity();
    abstract protected IDao getDAO(); 
    
    @Autowired
    SessionFactory sessionFactory;
    
    public DaoTest(Class<T> type)
    {
        this.type = type;
    }
    

    /**
     * Test of add method, of class Dao.
     */
    @Test
    public void testAdd()
    {
        List<City> stateBefore = sessionFactory.getCurrentSession().createQuery("from " + type.getName()).list();
        
        getDAO().add(getEntity());
        
        List<City> stateAfter  = sessionFactory.getCurrentSession().createQuery("from " + type.getName()).list();       
     
        assertTrue((stateAfter.size() - stateBefore.size()) == 1);
    }
    
    /**
     * Test of update method, of class Dao.
     */
    @Test
    public void testUpdate()
    {       
         
        sessionFactory.getCurrentSession().save(getEntity());
        
        List<City> stateBefore = sessionFactory.getCurrentSession().createQuery("from " + type.getName()).list();
        
        getDAO().update(getModifyEntity());
        
        List<City> stateAfter  = sessionFactory.getCurrentSession().createQuery("from " + type.getName()).list(); 
        
        assertTrue((stateAfter.size() - stateBefore.size()) == 0);
    }

    /**
     * Test of getAll method, of class Dao.
     */
    @Test
    public void testGetAll()
    {
        sessionFactory.getCurrentSession().save(getEntity());        
        List<City> stateBefore = sessionFactory.getCurrentSession().createQuery("from " + type.getName()).list();
        List<T> result = getDAO().getAll(type);
        assertTrue(stateBefore.size() == result.size());
    }

    /**
     * Test of getByQuery method, of class Dao.
     */
    @Test
    public void testGetByQuery()
    {
        sessionFactory.getCurrentSession().save(getEntity());        
        
        List<City> stateBefore = sessionFactory.getCurrentSession().createQuery("from " + type.getName()).list();
        
        List<T> result = getDAO().getByQuery("from " + type.getName());
        
        assertTrue(stateBefore.size() == result.size());
    }
    
    /**
     * Test of remove method, of class Dao.
     */
    @Test
    public void testRemove()
    {
        sessionFactory.getCurrentSession().save(getEntity());
        
        List<City> stateBefore = sessionFactory.getCurrentSession().createQuery("from " + type.getName()).list();
        
        getDAO().remove(getEntity());
        
        List<City> stateAfter  = sessionFactory.getCurrentSession().createQuery("from " + type.getName()).list();       
     
        assertTrue((stateAfter.size() - stateBefore.size()) == -1);
    }

    
}
