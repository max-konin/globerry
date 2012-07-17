/**
 * 
 */
package com.globerry.project.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tag;

/**
 * @author Artem
 * 
 */
@Repository
public class TagDao implements ITagDao
{
    @Autowired
    private SessionFactory sessionFactory;
    

    @Override
    public List<Tag> getTagList()
    {
	return sessionFactory.getCurrentSession().createQuery("from Tag").list();
    }

    @Override
    public void addTag(Tag tag, City city) throws MySqlException
    {
	if (!city.getTagList().contains(tag))
	{
	    city.getTagList().add(tag);
	    try
	    {
		sessionFactory.getCurrentSession().save(tag);
	    } catch (ConstraintViolationException e)
	    {
		MySqlException mySqlExc = new MySqlException();
		mySqlExc.setMyClass(tag);
		mySqlExc.setDescription("Name of tag is dublicated");
		throw mySqlExc;
	    } catch (Exception e)
	    {
		city.getTagList().remove(tag);
		throw new RuntimeException(e);
	    }
	} else
	{
	    throw new RuntimeException();
	}
    }

    @Override
    public void addTag(Tag tag) throws MySqlException
    {
	Transaction tx = null;
	try
	{
	    tx = sessionFactory.getCurrentSession().beginTransaction();
	    sessionFactory.getCurrentSession().save(tag);
	    tx.commit();
	    sessionFactory.close();
	} catch (ConstraintViolationException e)
	{
	    if (tx != null)
		tx.rollback();
	    MySqlException mySqlExc = new MySqlException();
	    mySqlExc.setMyClass(tag);
	    mySqlExc.setDescription("Name of tag is dublicated");
	    throw mySqlExc;
	}
    }

    @Override
	@Transactional
    public void removeTag(Tag tag)
    {
	// Retrieve session from Hibernate
	Session session = sessionFactory.getCurrentSession();
	// Delete person
	session.delete(tag);
    }

    @Override
    public void updateTag(Tag newTag)
    {

	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	Session session = sessionFactory.getCurrentSession();
	session.update(newTag);
	tx.commit();

    }

    @Override
    public void updateTag(Tag oldTag, Tag newTag)
    {
	oldTag.setImg(newTag.getImg());
	oldTag.setName(newTag.getName());
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	Session session = sessionFactory.getCurrentSession();
	session.update(oldTag);
	tx.commit();

    }

    @Override
	@Transactional
    public void removeTag(int id)
    {
	Session session = sessionFactory.getCurrentSession();
	Tag tag = (Tag) session.get(Tag.class, id);
	// Delete person
	session.delete(tag);

    }

    @Override
    public Tag getTagById(int id)
    {
	Tag tag = (Tag) sessionFactory.getCurrentSession().get(Tag.class, id);
	return tag;
    }

}
