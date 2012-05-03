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
	 city.getEvents().clear();
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
    public List<City> getCityList(CityRequest request)
    {
	List<City> result;
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
	result = criteria.list();
	weightCalculation(result,request);
	tx.commit();
	return result;
    }
    private void weightCalculation(List<City> result, CityRequest request){
	Iterator<City> itCity = result.iterator();
	while (itCity.hasNext()){
	    City city = itCity.next();
	    city.setWeight(1);
	    Iterator<PropertySegment> itProperty = request.getOption().iterator();
	    while (itProperty.hasNext()){
		PropertySegment propertyRequest = itProperty.next();
		PropertySegment propertyCity = city.getValueByPropertyType(propertyRequest.getPropertyType());
		if (propertyCity != null){
		    //TODO
		    float a,b;
		    if (propertyRequest.getPropertyType().isBetterWhenLess()){
			a = propertyRequest.getMaxValue() - propertyRequest.getMinValue();
			b = propertyCity.getMaxValue();
		    }else{
			a = (propertyRequest.getMaxValue() - propertyRequest.getMinValue())/2;
			b = Math.abs(propertyCity.getMaxValue()-a);
		    }
		    float k = b/a;
		    if (k < 0.2) k = (float) 0.2;
		    city.setWeight(city.getWeight()*k);		    
		}
	    }
	    city.setWeight((float) Math.pow(city.getWeight(),1/((double) request.getOption().size())));
	}
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

    @Override
    public List<City> getDamagedCities()
    {
	List<City> cityDamagedList;
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	cityDamagedList =  sessionFactory.getCurrentSession().createQuery("from City as city where city.message is not null")
	            .list();
	tx.commit();
	sessionFactory.close();
	return cityDamagedList;
    }

}
