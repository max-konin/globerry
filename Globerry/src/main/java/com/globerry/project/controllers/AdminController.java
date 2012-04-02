/**
 * 
 */
package com.globerry.project.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.globerry.project.Excel;
import com.globerry.project.ExcelParserException;
import com.globerry.project.MySqlException;
import com.globerry.project.dao.CityDao;
import com.globerry.project.dao.EventDao;
import com.globerry.project.dao.PropertyTypeDao;
import com.globerry.project.dao.TagDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.Property;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.DependingMonthProperty;
import com.globerry.project.domain.UploadItem;
import com.globerry.project.service.AdminParser;

/**
 * @author Artem
 *
 */
@Controller
@RequestMapping(value = "/admin/upload")
public class AdminController
{
    @Autowired
    private AdminParser adminParser;

    
    private List<String> excList = new ArrayList<String>();
    
    public String getProjectRoot() {
	URL u = this.getClass().getProtectionDomain().getCodeSource()
			.getLocation();
	File f;
	try
	{
	    f = new File(u.toURI());
	    return f.getParent();
	} catch (URISyntaxException e)
	{
	    e.printStackTrace();
	}
	return null;
}
    @RequestMapping(method = RequestMethod.GET)
    public String getUploadForm(Model model)
    {
      model.addAttribute(new UploadItem());
      return "admin/uploadForm";
    }
   
    @RequestMapping(method = RequestMethod.POST)
    public String create(UploadItem uploadItem, BindingResult result)
    {
      if (result.hasErrors())
      {
        for(ObjectError error : result.getAllErrors())
        {
          System.err.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());
        }
        return "admin/uploadForm";
      }
   
      // Some type of file processing...
      String filePath;
      try
      {
	filePath = getProjectRoot() + "/../../../../../../resources/upload/" + uploadItem.getFileData().getOriginalFilename();
	uploadItem.getFileData().transferTo(new File(filePath)); 
	
	try
	{
	    Excel exc = new Excel(filePath);
	    adminParser.updateCities(exc);
	    
	} catch (MySqlException e)
	{
	   excList.add(e.getDescription());
	   System.out.println(e.getDescription());
	} catch (ExcelParserException e)
	{
	    excList.add(e.getDescription());
	    System.out.println(e.getDescription());
	}
	if(excList.size() != 0)
	{
	    for(int i = 0; i < excList.size(); i++)
	    {
		System.err.println(excList.get(i));
	    }
	    return "admin/errorForm";   
	}
      }
      catch (IllegalStateException e)
      {
	  System.err.println("Hello World--------------------------------------------------");
	e.printStackTrace();
      } catch (IOException e)
      {
	  System.err.println("IOEXC--------------------------------------------------");
	e.printStackTrace();
      }
      
      return "redirect:upload";
    }
    @RequestMapping("wikiparse")
    public String wikiParseBtn()
    {
	adminParser.updateWikiContent();
	return "admin/wikiparse";
    }
    
 
   
}


