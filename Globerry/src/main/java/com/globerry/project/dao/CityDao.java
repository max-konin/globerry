package com.globerry.project.dao;

import com.globerry.project.utils.PropertySegment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.util.SystemOutLogger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Property;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.TagsType;
import com.globerry.project.domain.Tour;
import org.apache.log4j.Logger;

@Repository
public class CityDao implements ICityDao {

	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	IPropertyTypeDao propertyTypeDao;
        public static final Logger logger = Logger.getLogger(CityDao.class);
        
        final String dmpQuery = " inner join city.dmpList dmpList"; //Strings For creating HQL request in getCityList Method
        final String propQuery= " inner join city.propertyList propList";
	@Override
	public void addCity(City city) throws MySqlException {
		Transaction tx = null;
		try {
			tx = sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().save(city);
			tx.commit();
			sessionFactory.close();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			MySqlException mySqlExc = new MySqlException();
			mySqlExc.setMyClass(city);
			mySqlExc.setDescription("unexpected error");
			throw mySqlExc;
		}

	}

	@Override
	public void removeCity(City city) {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();

		logger.debug("\n------------ID--------------->" + city.getId() + " \n\n");
		logger.debug("\n-------------LISTSIZE-------------->" + city.getEvents().size() + " \n\n");
		Object object = sessionFactory.getCurrentSession().get(City.class, city.getId());
		City citytest = null;
		if (object != null) {
			citytest = (City) object;
		}
		logger.debug("\n------------IDхуя--------------->" + citytest.getId() + " \n\n");
		logger.debug("\n-------------LISTSIZEвторой-------------->" + citytest.getEvents().size() + " \n\n");
		city = citytest;
		Iterator<Event> it = city.getEvents().iterator();
		while (it.hasNext()) {
			Event event = it.next();
			city.getEvents().remove(event);
			logger.debug("\n------------количество_ччч_В_ЕВЕНТЕ--------------->" + event.getCities().size() + " \n\n");
			if (event.getCities().size() < 2) {
				logger.debug("\n------------ИМЯ_ХУЯ--------------->" + event.getName() + " \n\n");
				sessionFactory.getCurrentSession().delete(event);
			} else {
				event.getCities().remove(city);
				sessionFactory.getCurrentSession().update(event);
			}
		}
		city.getEvents().clear();
		sessionFactory.getCurrentSession().delete(city);
		tx.commit();
		sessionFactory.close();
	}

	@Override
	public void removeCity(int id) {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		City city = (City) sessionFactory.getCurrentSession().load(City.class,id);
		removeCity(city);

	}

	@Override
	public List<City> getCityList(CityRequest request)
	{
	    List<City> resultCityList;
	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	    //Query Generation Block for Properties
	    
	    String multipleDmpQuery = "";
	    String multiplePropertyQuery = "";  
	    String finalPropertyQuery = "";
	    String joinPropertiesQuery = ""; //for part of query, that provides table joins
	    int i = 0;//property join counter
	    int j = 0;//depending month property join counter
	    int month = 0;
	    for(PropertySegment elem: request.getOption())
	    {
		String singleDmpQuery= "";
		String singlePropertyQuery = "";

		PropertyType propertyType = elem.getPropertyType();
		if(propertyType.isDependingMonth())
		{	
		    j++;
		    joinPropertiesQuery+=dmpQuery + j + " ";
		    if(request.getMonth() == null)
		    {
			month = 1;
		    }
		    else
		    {
			month = request.getMonth().ordinal();
		    }
		    singleDmpQuery = "(dmpList" + j + ".propertyType.id =" + propertyType.getId() +
			    " and dmpList" + j + ".month = " + month +
			    " and(dmpList" + j + ".value between " + elem.getMinValue() +" and " + elem.getMaxValue() + ")) ";
		}
		else
		{
		    i++;
		    joinPropertiesQuery+=propQuery + i + " ";
		    singlePropertyQuery = "(propList" + i + ".propertyType.id = " + propertyType.getId() +
			    " and(propList" + i + ".value between " + elem.getMinValue() +" and " + elem.getMaxValue() + ")) ";
		}
		if(multipleDmpQuery.equals(""))
		{
		    multipleDmpQuery = singleDmpQuery;
		}
		else
		{
		    if(singleDmpQuery.equals(""))
			multipleDmpQuery = multipleDmpQuery + singleDmpQuery;
		    else
			multipleDmpQuery = multipleDmpQuery + " and " + singleDmpQuery;
		}
		if(multiplePropertyQuery.equals(""))
		{
		    multiplePropertyQuery = singlePropertyQuery;
		}
		else
		{
		    if(singlePropertyQuery.equals(""))
			 multiplePropertyQuery = multiplePropertyQuery  + singlePropertyQuery;
		    else
			multiplePropertyQuery = multiplePropertyQuery + " and " + singlePropertyQuery;
		}
	    }
	    if(multipleDmpQuery.equals("") || multiplePropertyQuery.equals(""))
		finalPropertyQuery = " (" + multipleDmpQuery + multiplePropertyQuery + ")";
	    else 
		finalPropertyQuery = " (" + multipleDmpQuery + " and " + multiplePropertyQuery + ")";
	    //end of Properties Query Generation Block
	    Iterator<Tag> it = request.getTags().iterator();
	    String stringQuery = "select distinct city from City city inner join city.tagList t1 inner join city.tagList t2 " + joinPropertiesQuery
		    + "where t1.id=" +it.next().getId() + " and t2.id="+ it.next().getId()+ " and" + finalPropertyQuery;// + " and " + createPropertyQuery(request);//createTagQuery(request);//+ ") and " +;inner join city.tagList t 
	    logger.debug(stringQuery);
	    Query query = sessionFactory.getCurrentSession().createQuery(stringQuery);
	    List<City> cityList =  query.list();
	    tx.commit();
	    return cityList;
	}
	@Override
	public void updateCity(City city) {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		sessionFactory.getCurrentSession().update(city);
		tx.commit();
		sessionFactory.close();

	}

	@Override
	public City getCityById(int id) {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		City city = (City) sessionFactory.getCurrentSession().get(City.class, id);
		tx.commit();
		sessionFactory.close();
		return city;
	}

	@Override
	public List<City> getCityList() {
		List<City> cityList;
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		cityList = sessionFactory.getCurrentSession().createQuery("from City").list();
		tx.commit();
		sessionFactory.close();
		return cityList;

	}

	@Override
	public List<City> getDamagedCities() {
		List<City> cityDamagedList;
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		cityDamagedList = sessionFactory.getCurrentSession().createQuery("from City as city where city.message is not null").list();
		tx.commit();
		sessionFactory.close();
		return cityDamagedList;
	}
}
