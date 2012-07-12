/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service;

import static org.mockito.Mockito.*;

import com.globerry.project.dao.ICityDao;
import com.globerry.project.dao.IPropertyTypeDao;
import com.globerry.project.dao.ITagDao;
import com.globerry.project.dao.Range;
import com.globerry.project.domain.*;
import com.globerry.project.service.gui.SelectBox;
import com.globerry.project.domain.Tag;
import com.globerry.project.service.gui.SelectBox;
import com.globerry.project.service.gui.Slider;
import com.globerry.project.service.service_classes.IApplicationContext;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author max
 */
public class UserCityServiceTest
{   
    @Mock
    ICityDao cityDao;
    
    @Mock
    IPropertyTypeDao propertyTypeDao;
     
    @Mock
    ITagDao tagDao;   
    
    
    @InjectMocks
    UserCityService service = new UserCityService();
    
    IApplicationContext appContext = mock(IApplicationContext.class);
    
    HashMap<String, Slider> sliders = new HashMap<String, Slider>();
    
    List<City> cityList = new ArrayList<City>();   
    
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        
        //ќпредел€ем состо€ни€ тегов в контексте
        SelectBox boxWho = new SelectBox(0);
        boxWho.addValue(1);
        boxWho.setValue(1);
        
        SelectBox boxWhat = new SelectBox(1);        
        boxWhat.addValue(1);
        boxWhat.setValue(1);
        
        SelectBox boxWhen = new SelectBox(2);
        boxWhen.addValue(1);
        boxWhen.setValue(1);
        
        when(appContext.getWhoTag()).thenReturn(boxWho);
        when(appContext.getWhatTag()).thenReturn(boxWhat);
        when(appContext.getWhenTag()).thenReturn(boxWhen);
        
        //ќпредел€ем, какие теги возвращает tagDao
        List<Tag> tags = new ArrayList<Tag>();        
        for(int i = 0; i < 5; i++)
        {
            Tag tag = new Tag();
            tag.setId(i); 
            tag.setName(String.format("tag-%d", i));
        }     
        when(tagDao.getTagList()).thenReturn(tags);
        //ќпердел€ем список городов, которые возвращает CityDao   
        for(int i = 0; i < 100; i++)
        {
            City city = new City();
            city.setId(i);
            city.setName(String.valueOf(i));
            city.setPropertyList(new HashSet());
        }
        
        //ќпредел€ем состо€ние слайдеров, по которым будет провер€тс€ фильтраци€ городов.             
        
        for(int i = 0; i < 6; i++)
        {
            PropertyType prType = new PropertyType();
            prType.setId(i);
            prType.setMinValue(i);
            prType.setMaxValue(i+10);
            Slider slider = new Slider(i, prType);
            sliders.put(String.format("slider-%d", i), slider);
            when(appContext.getSlidersByName(String.format("slider-%d", i))).thenReturn(slider);
            
            for(City city: cityList)
            {
                Property prop = new Property();
                prop.setId(i);
                prop.setPropertyType(prType);
                prop.setValue((i + 5) + city.getId() % 100);
                city.getPropertyList().add(prop);
            }            
        } 
        when(appContext.getSliders()).thenReturn(sliders);
       
        when(cityDao.getCityListByTagsOnly(any(ICityRequest.class))).thenReturn(cityList);
        
       
    }    
    /**
     * Test of getCityList method, of class UserCityService.
     */
    @Test
    public void testGetCityList_IApplicationContext()
    {
        System.out.println("getCityList");      
                
        List<City> result = service.getCityList(appContext);  
        verify(cityDao).getCityListByTagsOnly(any(ICityRequest.class));
        List<City> trueResult = new ArrayList();
        for(City city: cityList)
        {     
            boolean f = true;
            for(Slider slider: sliders.values())
            {
                float val = city.getValueByPropertyType(slider.getState().getPropertyType(), 
                                                        Month.values()[appContext.getWhenTag().getValue()]);
                if(val < slider.getLeftValue() || val > slider.getRightValue())
                    f = false;
            }
            if (f) trueResult.add(city);
        }
        for(City city: result)
            assertTrue(city.getWeight() != 0);
        assertTrue(result.equals(trueResult));  
        assertTrue(result.size() == trueResult.size()); 
        
        //ѕровер€ем, что при повторном запросе городов, без изменени€ тегов не просходит оращени€ к cityDao
        service.getCityList(appContext);  
        verify(cityDao, times(1)).getCityListByTagsOnly(any(ICityRequest.class));     
        
        
    }

    /**
     * Test of onTagChangeHandler method, of class UserCityService.
     * “ест провер€ет, что при tagChanged = true проходит запрос к бд 
     */
    @Test
    public void testOnTagChangeHandler()
    {       
        service.onTagChangeHandler();
        service.getCityList(appContext);                  
        verify(cityDao).getCityListByTagsOnly(any(ICityRequest.class));
        
    } 

    /**
     * Test of getCityList method, of class UserCityService.
     */
    @Test
    public void testGetCityList_0args()
    {
        System.out.println("getCityList");
        service.getCityList();
        verify(cityDao).getCityList();
    }

    /**
     * Test of clickOnPassiveCity method, of class UserCityService.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testClickOnPassiveCity()
    {
        System.out.println("clickOnPassiveCity");
        UserCityService instance = new UserCityService();
        instance.clickOnPassiveCity();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clickOnActiveCity method, of class UserCityService.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testClickOnActiveCity()
    {
        System.out.println("clickOnActiveCity");
        UserCityService instance = new UserCityService();
        instance.clickOnActiveCity();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeRange method, of class UserCityService.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testChangeRange()
    {
        System.out.println("changeRange");
        Range newRange = null;
        UserCityService instance = new UserCityService();
        instance.changeRange(newRange);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of sliderOnChangeHandler method, of class UserCityService.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testSliderOnChangeHandler()
    {
        System.out.println("sliderOnChangeHandler");
        UserCityService instance = new UserCityService();
        instance.sliderOnChangeHandler();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
    
}
