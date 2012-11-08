/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.admin;


import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Max
 */
public class AdminParserTest {
    
    AdminParser parser = new AdminParser();
    
    @Test
    public void coordsTransformTest()
    {
        //Arrange
        String westLng  = "12 30W";
        String eastLng  = "12 30 E";
        String northLat = "45N";
        String southLat = "120 30S";
        //Act
        float westLngResult  = parser.coordsTransform(westLng);
        float eastLngResult  = parser.coordsTransform(eastLng);
        float northLatResult = parser.coordsTransform(northLat);
        float southLatResult = parser.coordsTransform(southLat);
        //Asserts
        assertTrue((westLngResult  == -12.5));
        assertTrue((eastLngResult  == 12.5)); 
        assertTrue((northLatResult == 45)); 
        assertTrue((southLatResult == -120.5)); 
        


                
    }
}
