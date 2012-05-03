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
import com.globerry.project.domain.Tag;

/**
 * 
 * @author Sergey Krupin
 *
 */
public class BlockItemTest
{
    @Test
    public void initTest()
    {
	Tag tag = new Tag();
	tag.setName("testTag");
	tag.setImg("");
	BlockItem blockItem = new BlockItem(tag );
	if (!blockItem.getTag().equals(tag))
	    fail();
    }
    @Test
    public void onClickTest()
    {
	Tag tag = new Tag();
	tag.setName("testTag");
	tag.setImg("");
	BlockItem blockItem = new BlockItem(tag );
	blockItem.onClick();
	if (!blockItem.isSelected())
	    fail();
    }

}
