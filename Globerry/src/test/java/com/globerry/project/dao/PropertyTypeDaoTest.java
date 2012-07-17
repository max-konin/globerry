package com.globerry.project.dao;

import static org.junit.Assert.*;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;

import com.globerry.project.MySqlException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.runner.RunWith;

import com.globerry.project.domain.PropertyType;
import java.util.ArrayList;
import java.util.List;


import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/daoTestContext.xml")
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	ContextLoaderListener.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class PropertyTypeDaoTest {

	@Autowired
	private IPropertyTypeDao propertyTypeDao;
	@Autowired
	private SessionFactory sessionFactory;
	private static PropertyType propertyType1;
	private static PropertyType propertyType2;

	/**
	 * Create a random string contains 8 characters.
	 *
	 * @return Random String
	 */
	private static String getGeneratedString() {
		final int LENGHT = 8;
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < LENGHT; x++) {
			sb.append((char) ((int) (Math.random() * 26) + 97));
		}
		return sb.toString();
	}

	@BeforeClass
	public static void propertyTypeDaoBeforeTestClass() {
		propertyType1 = new PropertyType();
		propertyType2 = new PropertyType();
		propertyType1.setName(getGeneratedString());
		propertyType1.setMinValue(0);
		propertyType1.setMaxValue(150);
		propertyType1.setBetterWhenLess(true);
		propertyType1.setDependingMonth(true);
		propertyType2.setName(getGeneratedString());
		propertyType2.setMinValue(-50);
		propertyType2.setMaxValue(0);
		propertyType2.setBetterWhenLess(false);
		propertyType2.setDependingMonth(false);
	}

	@Test
	public void addPropertyTypeTest() throws MySqlException {
		int originalPropertyTypeSize = sessionFactory.getCurrentSession().createQuery("from PropertyType")
				.list().size();
		propertyTypeDao.addPropertyType(propertyType1);
		assertTrue(sessionFactory.getCurrentSession().createQuery("from PropertyType").list().size() - 1
				== originalPropertyTypeSize);
		assertTrue(sessionFactory.getCurrentSession().createQuery("from PropertyType").list().contains(propertyType1));
	}

	@Test
	public void removePropertyTypeByPropertyType() throws MySqlException {
		propertyTypeDao.addPropertyType(propertyType1);
		int originalPropertyTypeSize = sessionFactory.getCurrentSession().createQuery("from PropertyType")
				.list().size();
		propertyTypeDao.removePropertyType(propertyType1);
		assertTrue(sessionFactory.getCurrentSession().createQuery("from PropertyType").list().size() + 1
				== originalPropertyTypeSize);
		assertFalse(sessionFactory.getCurrentSession().createQuery("from PropertyType").list().contains(propertyType1));
	}
	
	@Test
	public void removePropertyTypeById() throws MySqlException {
		propertyTypeDao.addPropertyType(propertyType1);
		int originalPropertyTypeSize = sessionFactory.getCurrentSession().createQuery("from PropertyType")
				.list().size();
		propertyTypeDao.removePropertyType(propertyType1.getId());
		assertTrue(sessionFactory.getCurrentSession().createQuery("from PropertyType").list().size() + 1
				== originalPropertyTypeSize);
		assertFalse(sessionFactory.getCurrentSession().createQuery("from PropertyType").list().contains(propertyType1));
	}
	
	@Test
	public void getPropertyTypeListTest() throws MySqlException {
		propertyTypeDao.addPropertyType(propertyType1);
		propertyTypeDao.addPropertyType(propertyType2);
		List<PropertyType> propertyTypeList = new ArrayList();
		propertyTypeList.add(propertyType1);
		propertyTypeList.add(propertyType2);
		assertTrue(propertyTypeDao.getPropertyTypeList().size() == 2);
		assertTrue(propertyTypeDao.getPropertyTypeList().equals(propertyTypeList));		
	}
	
	@Test
	public void getPropertyTypeByIdTest() throws MySqlException {
		propertyTypeDao.addPropertyType(propertyType1);
		assertTrue(propertyTypeDao.getById(propertyType1.getId()).equals(propertyType1));
		assertTrue(propertyTypeDao.getById(666) == null);
	}
	
	@Test
	public void getPropertyTypeByNameTest() throws MySqlException {
		propertyTypeDao.addPropertyType(propertyType1);
		assertTrue(propertyTypeDao.getPropertyTypeByName(propertyType1.getName()).equals(propertyType1));
		assertTrue(propertyTypeDao.getPropertyTypeByName("UnexisitingName") == null);
	}
	
	@Test
	public void updatePropertyTypeTest() throws MySqlException {
		propertyTypeDao.addPropertyType(propertyType1);
		propertyType1.setName(getGeneratedString());
		propertyTypeDao.updatePropertyType(propertyType1);
		assertTrue(sessionFactory.getCurrentSession().createQuery("from PropertyType where id =" + propertyType1.getId()).list().get(0).equals(propertyType1));
	}
}