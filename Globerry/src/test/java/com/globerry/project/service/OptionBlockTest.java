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

import com.globerry.project.MySqlException;
import com.globerry.project.dao.ContextLoaderListener;
import com.globerry.project.dao.TagDao;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.TagsType;
import com.globerry.project.service.interfaces.IBlockItem;

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
public class OptionBlockTest
{
    @Autowired
    BlockWhat blockWhat;
    @Autowired
    BlockWho blockWho;
    @Autowired
    TagDao tagDao;
    private String getStringGenerator()
    {  
	
      final int LENGHT = 8;  
      StringBuffer sb = new StringBuffer();  
      for (int x = 0; x <LENGHT; x++)  
      {  
        sb.append((char)((int)(Math.random()*26)+97));  
      }  
      return sb.toString();  
    } 
    @Test
    public void initTest()
    {
	Tag tagWhat = new Tag();
	tagWhat.setName("testTagWhat"+getStringGenerator());
	tagWhat.setImg("");
	tagWhat.setTagsType(TagsType.WHERE);
	
	Tag tagWhat2 = new Tag();
	tagWhat2.setName("testTagWhat2"+getStringGenerator());
	tagWhat2.setImg("");
	tagWhat2.setTagsType(TagsType.WHERE);
	
	Tag tagWho = new Tag();
	tagWho.setName("testTagWho"+getStringGenerator());
	tagWho.setImg("");
	tagWho.setTagsType(TagsType.WHO);
	
	Tag tagWho2 = new Tag();
	tagWho2.setName("testTagWho2"+getStringGenerator());
	tagWho2.setImg("");
	tagWho2.setTagsType(TagsType.WHO);
	
	try
	{
	    tagDao.addTag(tagWhat);
	    tagDao.addTag(tagWhat2);
	    tagDao.addTag(tagWho);
	    tagDao.addTag(tagWho2);
	} catch (MySqlException e)
	{
	    e.printStackTrace();
	}
	if (blockWhat.getSelected() == null)
		   fail();
	blockWhat.setSelected(tagWhat2);
	if (!blockWhat.getSelected().equals(tagWhat2))
	   fail();
	blockWhat.setSelected(tagWho);
	if (!blockWhat.getSelected().equals(tagWhat2))
	   fail();
	
	if (blockWho.getSelected() == null)
		   fail();
	blockWho.setSelected(tagWho2);
	if (!blockWho.getSelected().equals(tagWho2))
	   fail();
	blockWho.setSelected(tagWhat);
	if (!blockWho.getSelected().equals(tagWho2))
	   fail();
    }

}
