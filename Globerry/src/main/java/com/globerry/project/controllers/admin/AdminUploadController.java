/**
 * 
 */
package com.globerry.project.controllers.admin;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import java.io.IOException;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
    private final Logger logger = Logger.getLogger(AdminUploadController.class);
    
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
          logger.error("Error: " + error.getCode() +  " - " + error.getDefaultMessage());
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
	    
	} 
	catch(ExcelParserException e)
	{
	    File bugReportFile = new File("bugs.txt");
	    System.err.println(bugReportFile.getAbsolutePath());
	    if(!bugReportFile.exists()) bugReportFile.createNewFile();
	    PrintWriter writer = new PrintWriter(
		    new BufferedOutputStream(
			    new FileOutputStream(bugReportFile)));
	    String errorMessage = e.getDescription();
	    request.setAttribute("errorMessage", errorMessage);
	    logger.error(e.getDescription());
	    writer.println(e.getDescription());
	    writer.close();
	    return "admin/errorForm";  
	}
      }
      catch (IOException e)
      {
	  logger.error("Проблема с файлом. Возможно, его нельзя прочесть");
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
	try
	{
	    adminParser.updateWikiContent();
	}
	catch(IOException e)
	{
	    e.printStackTrace();
	    logger.error("Что-то неправильно с файлами");
	}
	return "admin/wikiparse";
    }
    
 
   
}


