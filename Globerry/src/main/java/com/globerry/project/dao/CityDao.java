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
		logger.debug("\n-------------LISTSIZEхуя-------------->" + citytest.getEvents().size() + " \n\n");
		city = citytest;
		Iterator<Event> it = city.getEvents().iterator();
		while (it.hasNext()) {
			Event event = it.next();
			city.getEvents().remove(event);
			logger.debug("\n------------количество_ХУев_В_ЕВЕНТЕ--------------->" + event.getCities().size() + " \n\n");
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
	public List<City> getCityList(CityRequest request) {
	    	//кусок кода который нифига не делает
		Iterator<PropertySegment> iteratorProperty = request.getOption().iterator();
		while (iteratorProperty.hasNext()) {
                    PropertyType propertyType = 
                            propertyTypeDao.getById(iteratorProperty.next().getPropertyType().getId());
		}

		List<City> result;
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(City.class);
		// select by tag
		//TODO 
		//this is hack for tag
		//Что за фигня? Закоментил этот if и ничего не поменялось
		//для чего это? Коммент, "this is hack for tag" - божественнен. После него я сразу всё понял
		if (request.getTags().size() > 0) {
			Criteria tagCriteria = criteria.createCriteria("tagList");
			Criterion tagRestrictions = Restrictions.eq("id", request.getTags().get(0).getId());
			
		/* Restrictions.or( Restrictions.eq("id",
			 * request.getTags().get(0).getId()),
			 * Restrictions.not(Restrictions.eq("tagsType",
			 * request.getTags().get(0).getTagsType()))
		    	);
			 
			
			 * Iterator<Tag> iteratorTag = request.getTags().iterator();
			 * Criterion tagRestrictions = null; while(iteratorTag.hasNext()){
			 * Tag tag = iteratorTag.next(); if (tagRestrictions == null){
			 * tagRestrictions = Restrictions.or( Restrictions.eq("id",
			 * tag.getId()), Restrictions.not(Restrictions.eq("tagsType",
			 * tag.getTagsType())) ); }else{ tagRestrictions = Restrictions.and(
			 * tagRestrictions, Restrictions.or( Restrictions.eq("id",
			 * tag.getId()), Restrictions.not(Restrictions.eq("tagsType",
			 * tag.getTagsType())) ) ); }
            }*/
			 
			tagCriteria.add(tagRestrictions);
		}
		//select by property
		iteratorProperty = request.getOption().iterator();
		Criterion criterionDmpList = null;
		Criterion criterionPropertyList = null;
		while (iteratorProperty.hasNext()) {
			PropertySegment propertySegment = iteratorProperty.next();
			if (propertySegment.getPropertyType().isDependingMonth()) {
				Criterion tmp = Restrictions.and(
							Restrictions.eq(
								"propType.id",
								propertySegment.getPropertyType().getId()
								),
							Restrictions.and(
								Restrictions.eq(
									"Month",
									request.getMonth()
									),
								Restrictions.between(
									"value",
									propertySegment.getMinValue(),
									propertySegment.getMaxValue())
									)
								);
				if (criterionDmpList == null) {
					criterionDmpList = tmp;//criteria.createCriteria("dmpList");
					criterionDmpList = Restrictions.or(criterionDmpList, tmp);
					//criteriaDmpList.createAlias("propertyType", "propType");
				}
			} else {
				Criterion tmp =
						Restrictions.and(
							Restrictions.eq(
								"propType.id",
								propertySegment.getPropertyType().getId()
								),
							Restrictions.between(
								"value",
								propertySegment.getMinValue(),
								propertySegment.getMaxValue()
								)
							);
				if (criterionPropertyList == null) {
					criterionPropertyList = tmp;
					criterionPropertyList = Restrictions.or(criterionPropertyList, tmp);
					//criteriaPropertyList.createAlias("propertyType", "propType");
				}
			}
		}
		
		if (criterionDmpList != null) {
			Criteria criteriaDmpList = criteria.createCriteria("dmpList");
			criteriaDmpList.createAlias("propertyType", "propType");
		}
		if (criterionPropertyList != null) {
			Criteria criteriaPropertyList = criteria.createCriteria("propertyList");
			criteriaPropertyList.createAlias("propertyType", "propType");
		}
		//select by range TODO
		//longitude широта
		if (request.getRange().getMinX() < request.getRange().getMaxX()) {
			criteria.add(Restrictions.between(
					"longitude",
					request.getRange().getMinX(),
					request.getRange().getMaxX()));
		} else {
			criteria.add(Restrictions.or(
					Restrictions.le("longitude", request.getRange().getMinX()),
					Restrictions.ge("longitude", request.getRange().getMaxX())));
		}
		//latitude долгота
		criteria.add(Restrictions.between(
				"latitude",
				request.getRange().getMinY(),
				request.getRange().getMaxY()));

		//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		result = criteria.list();
		tx.commit();
		//TODO
		//this is hack for tag
		List<City> finalResult = new ArrayList();
		Iterator<City> iteratorCity = result.iterator();
		while (iteratorCity.hasNext()) {
			City city = iteratorCity.next();
			//check second tag
			{
				Iterator<Tag> iteratorTag = city.getTagList().iterator();
				while (iteratorTag.hasNext()) {
					if (iteratorTag.next().getId() == request.getTags().get(1).getId()) {
						//TODO hack for unique result
						if (finalResult.size() == 0 || 
                                                    finalResult.get(finalResult.size() - 1).getId() != city.getId()) 
                                                {
							finalResult.add(city);
						}
					}
				}
			}
		}

		weightCalculation(finalResult, request);
		return finalResult;
	}
	public List<City> getCityList2(CityRequest request)
	{
	    List<City> resultCityList;
	    logger.info(createPropertyQuery(request));
	    String queryString = "select city from City city " + createPropertyQuery(request);
	    logger.info(queryString);
	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	    //Criteria criteria = sessionFactory.getCurrentSession().createCriteria(City.class);
	    resultCityList = sessionFactory.getCurrentSession().createQuery(queryString).list(); 
		    //criteria.createCriteria("propertyList").add(Restrictions.conjunction().add(createPropertyCriteria(request))).list();
	    tx.commit();
	    sessionFactory.close();
	    //logger.info(resultCityList.get(0));
	    logger.info(createPropertyQuery(request));
	    
	    return null;
	}
	private String createPropertyQuery(CityRequest request)
	{
	    String dmpQuery = "";//= "inner join city.dmpList dmpList where";
	    String propQuery = "";//= "inner join city.propertyList propList where ";
	    for(PropertySegment elem: request.getOption())
	    {
		String singleDmpQuery= "";
		String singlePropertyQuery = "";
		PropertyType propertyType = elem.getPropertyType();
		//очень грубая зависимость от DependingMonth. Везде должно стоять всё правильно
		//Ситуацию осложняет то, что гибернейт не умеет маппать булеаны и их приходится менять в ручную через phpMyAdmin
		if(propertyType.isDependingMonth())
		{	
		    singleDmpQuery = "(dmpList.propertyType.id =" + propertyType.getId() +
			    " and dmpList.month = " + request.getMonth() +
			    "and (dmpList.value between " + elem.getMinValue() +" and " + elem.getMaxValue() + ")) ";
		}
		else
		{
		    singlePropertyQuery = "(propList.propertyType.id = " + propertyType.getId() +
			    " and (propList.value between " + elem.getMinValue() +" and " + elem.getMaxValue() + ")) ";
		}
		
		if(dmpQuery == "" && singleDmpQuery != "")
		{
		    dmpQuery = "inner join city.dmpList dmpList where " + singleDmpQuery;
		}
		else
		{
		    dmpQuery = dmpQuery + singleDmpQuery;
		}
		if(propQuery == "" && singlePropertyQuery != "")
		{
		    propQuery = "inner join city.propertyList propList where " + singlePropertyQuery;
		}
		else
		{
		    propQuery = propQuery + singlePropertyQuery;
		}
	    }
	    return dmpQuery.concat(propQuery);
	}
	private Criterion createPropertyCriteria(CityRequest request)
	{
	    Criterion criterion = null;
	    //Criteria PropertyTypeCriteria = sessionFactory.getCurrentSession().createCriteria(Property.class);
	    for(PropertySegment elem: request.getOption())
	    {
		
		PropertyType propertyType = elem.getPropertyType();
		logger.error(propertyType.getId());
		Criterion propertyCriterion = Restrictions.eq("propertyType.id", propertyType.getId());
		logger.info(propertyCriterion.toString());	
		if(criterion == null)
		{
		    criterion = propertyCriterion;
		}
		else
		{
		    criterion = Restrictions.and(criterion, propertyCriterion);
		}

	    }
	    logger.info(criterion.toString());
	    return criterion;
	}*/

	private void weightCalculation(List<City> result, CityRequest request) {
		List<City> cityForRemove = new ArrayList<City>();
		Iterator<City> itCity = result.iterator();
		while (itCity.hasNext()) {
                    City city = itCity.next();
                    city.setWeight(1);
                    Iterator<PropertySegment> itProperty = request.getOption().iterator();
                    while (itProperty.hasNext()) {
                        PropertySegment propertyRequest = itProperty.next();
                        float propertyCity;
                        try 
                        {
                            propertyCity = city.getValueByPropertyType(propertyRequest.getPropertyType());


                            if (propertyRequest.getMaxValue() < propertyCity
                                            || propertyCity < propertyRequest.getMinValue()) {
                                    cityForRemove.add(city);
                            }

                            float a, b, sizeBetween;

                            // Очередня быдло арифметика                    
                            sizeBetween = propertyRequest.getMaxValue() - propertyRequest.getMinValue();
                            if (sizeBetween <= 0) {
                                    sizeBetween = (float) 0.1 * (propertyCity - propertyCity);
                            }
                            if (propertyRequest.getPropertyType().isBetterWhenLess()) {
                                    a = sizeBetween;
                                    b = propertyCity - propertyRequest.getMinValue();
                            } else {
                                    a = sizeBetween / (float) 2.0;
                                    b = Math.abs((propertyCity - propertyRequest.getMinValue()) - a);
                            }
                            float k = b / a;
                            if (k < 0.2) {
                                    k = (float) 0.2;
                            }
                            city.setWeight(city.getWeight() * k);


                            if (request.getOption().size() > 0) {
                                    city.setWeight((float) Math.pow(city.getWeight(), 
                                                   1 / ((double) request.getOption().size()))
                                                   );
                            }
                        }
                        catch (IllegalArgumentException e) 
                        {
                                //For release mode
                                //cityForRemove.add(city);     
                                //For debug mode
                                // propertyCity = (propertyRequest.getMaxValue() + propertyRequest.getMinValue() / 2);
                        }
                    }
		}

		Iterator<City> itCityRemove = cityForRemove.iterator();
		while (itCityRemove.hasNext()) {
			result.remove(itCityRemove.next());
		}
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
