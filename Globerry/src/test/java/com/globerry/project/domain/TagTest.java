package com.globerry.project.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class TagTest
{

    @Test
    public void TagEqualsTest()
    {

	Tag tag1= new Tag();
	tag1.setName("where");
	tag1.setImg("asdasdasdasd");
	tag1.setTagsType(1);
	Tag tag2 = new Tag();
	tag2.setImg("asdasdasdasd");
	tag2.setName("where");
	tag2.setTagsType(1);
	//System.out.println(property1.getName().equals(property2.getName()));
	assertEquals(tag1, tag2);
	assertEquals(tag2, tag1);
	assertEquals(tag1, tag1);
	assertEquals(tag2, tag2);
	assertEquals(tag1.hashCode(), tag2.hashCode());
	//property2.setCost(123);
	tag1.setImg("asdasdasdasda");
	//property2.setName("mars");
	
	assertFalse(tag1.equals(tag2));
	assertFalse(tag1.hashCode() == tag2.hashCode());
    }

}
