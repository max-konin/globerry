/**
 *
 */
package com.globerry.project.dao;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.*;
import com.globerry.project.service.DefaultDatabaseCreator;
import java.util.ArrayList;
import java.util.HashSet;
import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Artem
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/daoTestContext.xml")
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	ContextLoaderListener.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TagDaoTest {

	@Autowired
	private ITagDao tagDao;
	@Autowired
	private ICityDao cityDao;
	@Autowired
	private SessionFactory sessionFactory;
	
	private static Tag tag1 = new Tag();
	private static Tag tag2 = new Tag();

	@BeforeClass
	public static void BeforeClassTest() {
		tag1.setImg(getGeneratedString());
		tag1.setName(getGeneratedString());
		tag1.setTagsType(TagsType.WHERE);
		tag2.setImg(getGeneratedString());
		tag2.setName(getGeneratedString());
		tag2.setTagsType(TagsType.WHO);
	}

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

	@Test
	public void addTagTest() throws MySqlException {
		int originalTagSize = sessionFactory.getCurrentSession().createQuery("from Tag").list().size();
		tagDao.addTag(tag1);
		assertTrue(sessionFactory.getCurrentSession().createQuery("from Tag").list().size() - 1 == 
				originalTagSize);
		assertTrue(sessionFactory.getCurrentSession().createQuery("from Tag").list().contains(tag1));
	}
	
	@Test
	public void getTagByIdTest() throws MySqlException {
		tagDao.addTag(tag1);
		assertTrue(tagDao.getTagById(tag1.getId()).equals(tag1));
		assertTrue(tagDao.getTagById(666) == null);
	}
	
	@Test
	@Transactional
	public void removeTagByTagTest() throws MySqlException {
		tagDao.addTag(tag1);
		int originalTagSize = sessionFactory.getCurrentSession().createQuery("from Tag").list().size();
		tagDao.removeTag(tag1);
		assertTrue(sessionFactory.getCurrentSession().createQuery("from Tag").list().size() + 1 == 
				originalTagSize);
		assertFalse(sessionFactory.getCurrentSession().createQuery("from Tag").list().contains(tag1));
	}
	
	@Test
	@Transactional
	public void removeTagByIdTest() throws MySqlException {
		tagDao.addTag(tag1);
		int originalTagSize = sessionFactory.getCurrentSession().createQuery("from Tag").list().size();
		tagDao.removeTag(tag1.getId());		
		assertTrue(sessionFactory.getCurrentSession().createQuery("from Tag").list().size() + 1 == 
				originalTagSize);
		assertFalse(sessionFactory.getCurrentSession().createQuery("from Tag").list().contains(tag1));
	}
	
	@Test
	public void updateTagTest() throws MySqlException {
		tagDao.addTag(tag1);
		tag1.setName(getGeneratedString());
		tagDao.updateTag(tag1);
		assertTrue(sessionFactory.getCurrentSession().createQuery("from Tag where id = " + tag1.getId()).list().get(0).equals(tag1));
	}
	
	@Test
	public void getTagListTest() throws MySqlException {
		tagDao.addTag(tag1);
		tagDao.addTag(tag2);
		List<Tag> tagList = new ArrayList();
		tagList.add(tag1);
		tagList.add(tag2);
		assertTrue(tagDao.getTagList().size() == 2);
		assertTrue(tagDao.getTagList().equals(tagList));
	}
}
