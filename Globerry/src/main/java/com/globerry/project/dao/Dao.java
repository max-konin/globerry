/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.dao;

import com.globerry.project.MySqlException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import javax.annotation.PostConstruct;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Реализацая унифицированного доступа к данным
 * 
 * @author max
 */
@Repository
public class Dao<T> implements IDao<T>
{
    private Class<T> type;

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void add(T entity)
    {
	Transaction tx = null;
	try
	{
	    tx = sessionFactory.getCurrentSession().beginTransaction();
	    sessionFactory.getCurrentSession().save(entity);
	    tx.commit();
	}
	catch (Exception exp)
	{
	    if (tx != null)
		tx.rollback();
	    exp.printStackTrace();
	    throw new HibernateException("Что то пошло не так", exp);
	}
    }

    @Override
    public void remove(T entity)
    {
	Transaction tx = null;
	try
	{
	    tx = sessionFactory.getCurrentSession().beginTransaction();
	    sessionFactory.getCurrentSession().delete(entity);
	    tx.commit();
	}
	catch (Exception exp)
	{
	    if (tx != null)
		tx.rollback();
	    throw new HibernateException("Что то пошло не так", exp);
	}
    }

    @Override
    public void update(T entity)
    {
	Transaction tx = null;
	try
	{
	    tx = sessionFactory.getCurrentSession().beginTransaction();
	    sessionFactory.getCurrentSession().update(entity);
	    tx.commit();
	}
	catch (Exception exp)
	{
	    if (tx != null)
		tx.rollback();
	    throw new HibernateException("Что то пошло не так", exp);
	}
    }

    @Override
    public List<T> getAll(Class clazz)
    {

	Transaction tx = null;
	try
	{
	    tx = sessionFactory.getCurrentSession().beginTransaction();
	    List<T> result = sessionFactory.getCurrentSession().createQuery("from " + clazz.getName()).list();
	    tx.commit();
	    return result;
	}
	catch (Exception exp)
	{
	    if (tx != null)
		tx.rollback();
	    throw new HibernateException("Что то пошло не так", exp);
	}
    }

    @Override
    public List<T> getByQuery(String query)
    {
	Transaction tx = null;
	try
	{
	    tx = sessionFactory.getCurrentSession().beginTransaction();
	    List<T> result = sessionFactory.getCurrentSession().createQuery(query).list();
	    tx.commit();
	    return result;
	}
	catch (Exception exp)
	{
	    if (tx != null)
		tx.rollback();
	    throw new HibernateException("Что то пошло не так", exp);
	}
    }

    public T getById(Class clazz, int id)
    {
	Transaction tx = null;
	try
	{
	    tx = sessionFactory.getCurrentSession().beginTransaction();
	    T result = (T) sessionFactory.getCurrentSession().load(clazz, id);
	    tx.commit();
	    return result;
	}
	catch (Exception exp)
	{
	    if (tx != null)
		tx.rollback();
	    throw new HibernateException("Что то пошло не так", exp);
	}
    }
}
