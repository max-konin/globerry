/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.gui;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author max
 */
public class SelectBoxTest
{
    
    SelectBox prototype = new SelectBox(1);
    
    @Before
    public void setUp()
    {
        prototype.addValue(3);
    }
    
    @Test
    public void copyConstructorTest()
    {
        SelectBox clone = new SelectBox(prototype);
        
        assertTrue(clone.id == prototype.id);
        assertTrue(clone != prototype);
        assertTrue(clone.values.get(0) == prototype.values.get(0));
    }
}
