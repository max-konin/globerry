package com.globerry.project.controllers.admin;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.Element;
import javax.xml.ws.Response;
import org.apache.log4j.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Company;

//import com.globerry.project.domain.IRelationsQualifier;
import com.globerry.project.domain.Month;
import com.globerry.project.service.interfaces.ICityService;
import com.globerry.project.service.interfaces.ICompanyService;
import com.globerry.project.service.admin.AbstractTypeFactory;
import com.globerry.project.service.admin.IEntityCreator;
import com.globerry.project.service.CityService;
import com.globerry.project.service.DefaultDatabaseCreator;




/**
 * 
 * @author Artem
 * Контроллер для админки. Содержит в себе маппинги и функции, которые запускаются при запросе
 * Весь класс описан в маппинге "/admin", например "http://Globerry/admin/eventadminpage" 
 * Большинство страниц работает через factory, т.е. чтобы класс определил что с каким предметом ей нужно работать
 * надо зайти в одну из ссылок eventadminpage, companyadminpage, cityadminpage для Event, Company и city соответственно
 * После этого определяется IEntityCreator page, и он вызывает нужный класс
 * 
 * 
 */
@Controller
@RequestMapping("/admin")
public class AdminController
{
    /**
     * Factory для определения сущности
     */
    @Autowired
    private AbstractTypeFactory abstrFactory;
    
    @Autowired
    private DefaultDatabaseCreator databaseCreator;
    
     
    @Autowired
    private CityService cityService;
    
    @Autowired
    private ICompanyService companyService;
    /**
     * Элемент, который может быть EventPage, CityPage, CompanyPage. Определяется в функции createForm
     */
    private IEntityCreator page = null;
    
    private String url;
    
    private static final Logger logger = Logger.getLogger(AdminController.class);
    
    private int updatedElementId;
    /**
     * 
     * @param _url Это адрес, который идёт после "/admin"
     * @param map Элемент куда кладутся через фабрику нужные элементы.
     * @return jsp файл, он получается через factory
     */
    @RequestMapping("/{url}")
    public String createForm(@PathVariable("url") String _url, Map<String, Object> map)
    {
	page = abstrFactory.responsePage(_url);
	url = _url;
	page.setList(map);
	return page.getJspListFile();
    }
    /**
     * Запрос на удаление элемента
     * @param id id элемента
     * @return возвращает ту страницу с которой был послан запрос
     */
    @RequestMapping("/delete/{id}")
    public String removeElement(@PathVariable("id") Integer id)
    {
	page.removeElem(id);
	return "redirect:/admin/" + url;
    }
    /**
     * Обновление элемента
     * @param id id обновляемого элемента 
     * @param map Сюда сливается сам элемент и его связи типа ManyToMany, OneToMany, ManyToOne
     * @return jsp файл который отвечает за обновление
     */
    @RequestMapping("/update/{id}")
    public String updateElement(@PathVariable("id") Integer id, Map<String, Object> map)
    {
	updatedElementId = id;
	page.getElemById(map, id);
	page.getRelation(map, id);
	page.getRelation(map);
	return page.getJspUpdateFile();
    }
    /**
     * Метод для отправки запроса на сервер с данными обновлённого города. 
     * Класс работает без фабрики
     * @param city Город который нужно обновить
     * @param result 
     * @param map
     * @return
     */
    //Дублирование кода, из-за того, что сринг не хочет нормально принимать файл типа Object, или какого то ещё
    @RequestMapping(value = "/update/updateCity", method = RequestMethod.POST)
    public String addCity(@ModelAttribute("city") City city, BindingResult result, Map<String, Object> map) {
	//map.put("months", Month.values());
        page.updateElem(city);
        return "redirect:/admin/" + url;
    }
   
    @RequestMapping(value = "/update/updateCompany", method = RequestMethod.POST)
    public String addCompany(@ModelAttribute("company") Company company, BindingResult result, Map<String, Object> map) {
	//map.put("months", Month.values());
        page.updateElem(company);
        return "redirect:/admin/" + url;
    }
    
    @RequestMapping(value="/getcities", method=RequestMethod.GET)
    public @ResponseBody List<City> getCities() {
	//БЫДЛОКОД
        return cityService.getCityList();
    }
    @RequestMapping(value="/getcompanies", method=RequestMethod.GET)
    public @ResponseBody List<Company> getCompanies() {
	//БЫДЛОКОД
        return companyService.getCompanyList();
    }
    @RequestMapping(value="/getrelations/{id}", method=RequestMethod.GET)
    public @ResponseBody Map<String, Object> getRelations(@PathVariable("id") Integer id, Map<String, Object> map) {
	
         return page.getRelation(map, id);
    }
    @RequestMapping(value="/update/{type}/{relation}", method=RequestMethod.GET)
    public String joinElem(@PathVariable("type") String type, @PathVariable("relation") String relation, HttpServletRequest request) 
    {
	logger.info("Hello worlds");
	if(type.toLowerCase().equals("join"))
	{
	    page.addRelaion(relation, updatedElementId, Integer.parseInt(request.getParameter("id")));
	}
	if(type.toLowerCase().equals("remove"))
	{
            logger.info("------------------------" + request.getParameter("id") + "----------------------");
	    //System.err.println("------------------------" + request.getParameter("id") + "----------------------");
	    page.removeRelation(relation, updatedElementId, Integer.parseInt(request.getParameter("id")));
            logger.info(request.getParameter("id"));
	  //  System.err.println(request.getParameter("id"));
	}
	System.err.println(request.getParameter("id"));
	return "redirect:/admin/update/" + updatedElementId;
    }
    @RequestMapping(value="/clearDatabase")
    public String clearDatabase()
    {
	databaseCreator.clearDatabase();
	return "redirect:/admin/" + url;
    }
    @RequestMapping(value="/initTags")
    public String initTags()
    {
	databaseCreator.initTags();
	return "redirect:/admin/" + url;
    }
/*    @RequestMapping(value="/initProperties")
    public String initProperties()
    {
	databaseCreator.initPropertyType();
	return "redirect:/admin/" + url;
    }
    
    /*HttpServletRequest request*/
    /*@RequestMapping(value="/delete", method=RequestMethod.GET)
    public void delete(HttpServletRequest request)
    {
	JsonFactory j = new JsonFactory();
	JsonParser json = null;
	try
	{
	    //System.out.println();
	    json = j.createJsonParser(request.getParameter("event"));
	} catch (JsonParseException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	Event event = null;
	try
	{
	   event = new ObjectMapper().readValue(json, Event.class);
	} catch (JsonParseException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (JsonMappingException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	System.out.println(event.getId());
	//System.out.println(event.getId());
	//System.out.println(event.getDescription());

	//JSONPObject jsonpobj = new JSONPObject(request.getParameter("models"), Event.class);
	//Event event = (Event)jsonpobj.getValue();
	//System.out.println(event.getId());
	//System.out.println(jsonpobj.getClass());
	//System.err.println(event.getDescription());
	//ObjectMapper mapper = new ObjectMapper();
	//Event event = mapper.readValue(, Event.class);
	//System.err.println(event.getDescription());
	//System.err.println(request.getParameter("models"));
	//System.err.println(request.getParameter("model"));
	//System.err.println(request.getParameter("event"));
	System.err.println("sdaf");
	
    }*/
    
    
   /* @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String addContact(@PathVariable("id") Integer id, @ModelAttribute("admin") Company company, BindingResult result) {

	try
	{
	    cmpService.addCompany(company);
	}
	catch(MySqlException e)
	{
	    //???? ?? ??????? ??? ?????? ???? ???????????? ??? ????????????? ??????
	}
        return "redirect:/";
    }*/
}
