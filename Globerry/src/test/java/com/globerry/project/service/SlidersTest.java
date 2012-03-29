package com.globerry.project.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.globerry.project.dao.ContextLoaderListener;
/**
 * 
 * @author Sergey Krupin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/app-context.xml")
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    ContextLoaderListener.class
})
public class SlidersTest
{
    @Test
    public void initTest()
    {
	//TODO
    }
    @Test
    public void blockItemOnClickHandlerTest()
    {
	//TODO
    }
    @Test
    public void onChangeTest()
    {
	//TODO
    }
    @Test
    public void addSliderTest()
    {
	//TODO
    }

}
