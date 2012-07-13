package com.globerry.project.dao;

import java.lang.reflect.Field;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.globerry.project.domain.City;
import com.globerry.project.domain.DependingMonthProperty;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Month;

@Repository
public class EventDao implements IEventDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Transactional
	public void removeEvent(Event event) {
		sessionFactory.getCurrentSession().delete(event);
	}

	@Override
	public void addEvent(Event event, City city) {
		if (!event.getCities().contains(city) && !city.getEvents().contains(event)) {
			event.getCities().add(city);
			city.getEvents().add(event);
			try {
				if (event.getId() == 0) {
					sessionFactory.getCurrentSession().save(event);
				} else {
					sessionFactory.getCurrentSession().update(event);
				}
				sessionFactory.getCurrentSession().update(city);
				// int id =
				// ((Event)sessionFactory.getCurrentSession().merge(event)).getId();
				// event.setId(id);
			} catch (Exception e) {
				event.getCities().remove(city);
				city.getEvents().remove(event);
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException();
		}
	}

	@Override
	public int addEvent(Event event) {
		int id = 0;
		Iterator<City> IT = event.getCities().iterator();
		while (IT.hasNext()) {
			City city = IT.next();
			sessionFactory.getCurrentSession().saveOrUpdate(city);
		}
		try {
			id = (Integer) sessionFactory.getCurrentSession().save(event);
			System.err.println(id);
		} catch (Exception e) {
		}
		return id;
	}

	@Override
	public List<Event> getEventList(Month month, City city) {
		List<Event> result;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Event.class).add(Restrictions.eq("month", month)).createCriteria("cityList").add(Restrictions.eq("id", city.getId()));
		result = (List<Event>) criteria.list();
		return result;
	}

	@Override
	public List<Event> getEventList() {
		return sessionFactory.getCurrentSession().createQuery("from Event").list();
	}

	@Override
	@Transactional
	public void removeEvent(int id) {
		Event event = (Event) sessionFactory.getCurrentSession().load(Event.class, id);
		if (null != event) {
			sessionFactory.getCurrentSession().delete(event);
		}
	}

	@Override
	public Event getEventById(int id) {
		return (Event) sessionFactory.getCurrentSession().get(Event.class, id);
	}

	@Override
	public void updateEvent(Event newEvent) {
		try {
			sessionFactory.getCurrentSession().update(newEvent);
		} catch (HibernateException he) {
			throw new IllegalArgumentException("Bad Event(may not exist in Database)" + he.getStackTrace(), he);
		}
	}

	@Override
	public void saveOrUpdateEvent(Event newEvent) {
		sessionFactory.getCurrentSession().saveOrUpdate(newEvent);
	}
}
