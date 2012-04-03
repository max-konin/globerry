package com.globerry.project.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.util.SystemOutLogger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.Tour;

@Repository
public class CityDao implements ICityDao
{
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public void addCity(City city) throws MySqlException
    {
	Transaction tx = null;
	try
	{
	    tx = sessionFactory.getCurrentSession().beginTransaction();
	    sessionFactory.getCurrentSession().save(city);
	    tx.commit();
	    sessionFactory.close();
	}
	catch (Exception e) {
	    if(tx !=null)
		tx.rollback();
	    MySqlException mySqlExc = new MySqlException();
	       mySqlExc.setMyClass(city);
	       mySqlExc.setDescription("unexpected error");
	       throw mySqlExc;
	}

    }

    @Override
    public void removeCity(City city)
    {
	 Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	 
	 System.out.print("\n------------ID--------------->"+city.getId()+" \n\n");
	 System.out.print("\n-------------LISTSIZE-------------->"+city.getEvents().size()+" \n\n");
	 Object object = sessionFactory.getCurrentSession().get(City.class, city.getId());
	 City citytest = null;
	 if(object != null)
	     citytest = (City)object;
	 System.out.print("\n------------IDхуя--------------->"+citytest.getId()+" \n\n");
	 System.out.print("\n-------------LISTSIZEхуя-------------->"+citytest.getEvents().size()+" \n\n");
	 city = citytest;
	 Iterator<Event> it = city.getEvents().iterator();
	while(it.hasNext()){
	     Event event = it.next();
	     city.getEvents().remove(event);
	     System.out.print("\n------------количество_ХУев_В_ЕВЕНТЕ--------------->"+event.getCities().size()+" \n\n");
	     if(event.getCities().size() < 2){
		 System.out.print("\n------------ИМЯ_ХУЯ--------------->"+event.getName()+" \n\n");
		 sessionFactory.getCurrentSession().delete(event);
	     }
	     else
	     {
		 event.getCities().remove(city);
		 sessionFactory.getCurrentSession().update(event);
	     }
	 }
         sessionFactory.getCurrentSession().delete(city);
         tx.commit();
         sessionFactory.close();
    }
    @Override
    public void removeCity(int id)
    {
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	City city = (City) sessionFactory.getCurrentSession().load(City.class,
		id);
	removeCity(city);
	
    }

    @Override
    public Set<City> getCityList(CityRequest request)
    {
	Set<City> result;
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(City.class);
	// select by tag
	if (request.getTags().size() > 0){
            List<Integer> tagIdList = new ArrayList<Integer>();
            Iterator<Tag> iteratorTag = request.getTags().iterator();
            while(iteratorTag.hasNext()){
        	tagIdList.add(iteratorTag.next().getId());
            }
            criteria.createCriteria("tagList")
        	.add(Restrictions.in("id", tagIdList));
	}
	//select by property
	Iterator<PropertySegment> iteratorProperty = request.getOption().iterator();
	while(iteratorProperty.hasNext()){
	    PropertySegment propertySegment = iteratorProperty.next();
	    if (propertySegment.getPropertyType().isDependingMonth()){
		criteria.createCriteria("dmpList")
		    .add(Restrictions.eq(
			"PropertyType",
			propertySegment.getPropertyType()
			))
		    .add(Restrictions.eq(
			"Month",
			request.getMonth()
			))
		    .add(Restrictions.between(
			"value",
	    		propertySegment.getMinValue(),
	    		propertySegment.getMaxValue()
	    	));
	    }else{
		criteria.createCriteria("propertyList")
		    .add(Restrictions.eq(
			"PropertyType",
			propertySegment.getPropertyType()
			))
		    .add(Restrictions.between(
			"value",
	    		propertySegment.getMinValue(),
	    		propertySegment.getMaxValue()
	    	));
	    }
	}
	//select by range TODO
	//longitude широта
	if(request.getRange().getMinX() < request.getRange().getMaxX()){
	    criteria.add(Restrictions.between(
    		"longitude",
    		request.getRange().getMinX() ,
    		request.getRange().getMaxX()));
	}else{
	    criteria.add(Restrictions.or(
		    Restrictions.le("longitude", request.getRange().getMinX()),
		    Restrictions.ge("longitude", request.getRange().getMaxX())
		    ));
	}
	//latitude долгота
	criteria.add(Restrictions.between(
    		"latitude",
    		request.getRange().getMinY() ,
    		request.getRange().getMaxY()));
	result = new HashSet<City>(criteria.list());
	tx.commit();
	return result;
    }


    @Override
    public void updateCity(City city)
    {
	   Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	   
	  /* City excistingCity = (City) sessionFactory.getCurrentSession().get(City.class, city.getId());
	   excistingCity.setDmpList(city.getDmpList());
	   excistingCity.setName(city.getName());
	   excistingCity.setPropertyList(city.getPropertyList());
	   excistingCity.setProposals(city.getProposals());
	   excistingCity.setRu_name(city.getRu_name());
	   excistingCity.setTagList(city.getTagList());
	   excistingCity.setEvents(city.getEvents());*/
	   sessionFactory.getCurrentSession().update(city);
	   tx.commit();
	   sessionFactory.close();
	
    }

    @Override
    public City getCityById(int id)
    {
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	City city = (City)sessionFactory.getCurrentSession().get(City.class, id);
	tx.commit();
	sessionFactory.close();
	return city;
    }

    @Override
    public List<City> getCityList()
    {
	List<City> cityList;
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	cityList =  sessionFactory.getCurrentSession().createQuery("from City")
	            .list();
	tx.commit();
	sessionFactory.close();
	return cityList;
	
    }

}
