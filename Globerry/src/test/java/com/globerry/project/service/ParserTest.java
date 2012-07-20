/**
 * 
 */
package com.globerry.project.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.globerry.htmlparser.City;
import com.globerry.project.Excel;
import com.globerry.project.ExcelParserException;
import com.globerry.project.MySqlException;
import com.globerry.project.dao.ContextLoaderListener;
import com.globerry.project.dao.IDao;
import com.globerry.project.domain.Tag;
import com.globerry.project.service.admin.AdminParser;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Artem
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/contextForTestNewDomain.xml")
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class, ContextLoaderListener.class
})
public class ParserTest
{
    @Autowired
    private IDao<Tag> dao;
    @Autowired
    private AdminParser adminParser = new AdminParser();

/*    @Before
    public void init()
    {
	MockitoAnnotations.initMocks(this);
    }*/
   // @Test
    public void test()
    {
	Excel exc = new Excel("C:\\Users\\Artem\\Downloads\\Cities 29,06 - correct.xlsx");
	try
	{
	    adminParser.updateCities(exc);
	    /*City city = new;
	    verify(dao).add(city );*/
	}
	catch (ExcelParserException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
   // @Test
    public void testWiki()
    {
	adminParser.updateWikiContent();
    }

}
