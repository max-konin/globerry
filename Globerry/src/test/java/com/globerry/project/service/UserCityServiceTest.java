package com.globerry.project.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
    WebContextTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    ContextLoaderListener.class
})
public class UserCityServiceTest
{
    @Autowired
    UserCityService userCityService;
    @Test
    public void initTest(){
	//TODO
    }
    @Test
    public void clickOnActiveCityTest(){
	//TODO
    }
    @Test
    public void changeRangeTest(){
	//TODO
    }
    @Test
    public void sliderOnChangeHandlerTest(){
	//TODO
    }
    @Test
    public void getCityList(){
	//TODO
    }

}
