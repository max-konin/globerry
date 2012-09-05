
package com.globerry.project.integration.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.globerry.project.controllers.user.HomeController;
import com.globerry.project.integration.dao.ContextLoaderListener;
import com.globerry.project.service.gui.SelectBox;
import com.globerry.project.service.service_classes.Request;
import com.globerry.project.utils.ExecuteQueryTimer;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.SessionScope;

/**
 * Тестит сценарии поведения пользователя на сайте.
 * 
 * @author max
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/controllerTestContext.xml"})
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	ContextLoaderListener.class
})
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HomeControllerIntegrationTest
{
    //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("controllerTestContext.xml");
    @Inject
    private ApplicationContext applicationContext;
    
    @Before   
    public void setUp() {    
                

        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) 
                                                                    applicationContext.getAutowireCapableBeanFactory();
        configurableBeanFactory.registerScope("session", new SessionScope());        
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
    }
   
    @Test 
    public void Scenario_1() 
    {
       HomeController instance = (HomeController) applicationContext.getBean("homeController");
       Map map = new HashMap<String, Object>();
       instance.home(map);
       assertTrue(map.get("cities") != null );
       Request requestTagChange = mock(Request.class);
       SelectBox sb = mock(SelectBox.class);      
       when(sb.getValue()).thenReturn(8);
       when(requestTagChange.getId()).thenReturn(2);
       when(requestTagChange.getValue()).thenReturn(sb);
       Request[] r = new Request[1];
       r[0] = requestTagChange;
       
       for(int i = 0; i<1; i++)
       {
            instance.guiChanged(r);
       }
       ExecuteQueryTimer.getInstanse().closeSpy();
       
       
       
    }
}
