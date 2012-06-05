/**
 * 
 */
package com.globerry.project.controllers.admin;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import com.globerry.project.domain.UploadItem;
import com.globerry.project.service.admin.AdminParser;

/**
 * @author Artem
 *
 */
@Controller
@RequestMapping(value = "/admin/upload")
public class AdminUploadController
{
    @Autowired
    private AdminParser adminParser;

    
    private List<String> excList = new ArrayList<String>();
    /**
     * Функция вычисляющая, где лежит проект
     * @return путь к проекту
     */
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
    //дальнейшие 2 функции были спёрты с интрнета, поэтому за их работу я не несу никакой ответственности
    //Но они загружают файл и даже правильно
    @RequestMapping(method = RequestMethod.GET)
    public String getUploadForm(Model model)
    {
      model.addAttribute(new UploadItem());
      return "admin/uploadForm";
    }
   
    @RequestMapping(method = RequestMethod.POST)
    public String create(UploadItem uploadItem, BindingResult result, HttpServletRequest request)
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
	filePath = request.getSession().getServletContext().getRealPath("/resources/upload") + "/" + uploadItem.getFileData().getOriginalFilename();
	File file = new File(filePath);
	System.err.println(request.getSession().getServletContext().getRealPath("/"));
	uploadItem.getFileData().transferTo(file); 
	
	try
	{
	    Excel exc = new Excel(file.getAbsolutePath());
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
    /**
     * Кнопка, которая запускает парсер википедии
     * @return отпарсенные города
     * 
     */
    @RequestMapping("wikiparse")
    public String wikiParseBtn()
    {
	adminParser.updateWikiContent();
	return "admin/wikiparse";
    }
    
 
   
}


