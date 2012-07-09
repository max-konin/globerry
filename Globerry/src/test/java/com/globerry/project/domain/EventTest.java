/**
 * 
 */
package com.globerry.project.domain;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Artem
 *
 */
public class EventTest
{


    @Test
    public void EqualsTest()
    {
	Event  event1= new Event();
	event1.setName("Snikers");
	
	Event event2 = new Event();
	
	event2.setName("Snikers");
	//System.out.println(event1.getName().equals(event2.getName()));
	assertEquals(event1, event2);
	assertEquals(event1.hashCode(), event2.hashCode());
	//event2.setCost(123);
	event1.setName("mars");
	event2.setName("mars");
	event2.setDescription("asdadasdasdasdasd");
	assertFalse(event1.equals(event2));
	assertFalse(event1.hashCode() == event2.hashCode());
    }
}
