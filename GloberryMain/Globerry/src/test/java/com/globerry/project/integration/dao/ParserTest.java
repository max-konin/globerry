/**
 * 
 */
package com.globerry.project.integration.dao;

import static org.junit.Assert.*;

import java.io.IOException;
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
import com.globerry.project.dao.IDao;
import com.globerry.project.domain.Tag;
import com.globerry.project.service.admin.AdminParser;
import org.junit.Ignore;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Artem
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/parserTestContext.xml")
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
    @Test
    @Ignore
    public void test() throws IOException
    {
	Excel exc = new Excel("C:\\таблица для аналитика 18.09.12.xlsx");
	    adminParser.updateCities(exc);
            adminParser.updateWikiContent();
            
	    /*City city = new;
	    verify(dao).add(city );*/
	
    }
    @Test
    @Ignore
    public void testWiki() throws IOException
    {
	adminParser.updateWikiContent();
    }

}
