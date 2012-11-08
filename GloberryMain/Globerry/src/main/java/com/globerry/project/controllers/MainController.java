package com.globerry.project.controllers;

import com.globerry.project.dao.CurveMongoDao;
import com.globerry.project.dao.ICurveDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Company;
import com.globerry.project.service.CityService;
import com.globerry.project.service.CompanyService;
import com.globerry.project.service.CurveService;
import com.globerry.project.service.interfaces.ICityService;
import com.globerry.project.service.interfaces.ICompanyService;
import com.globerry.project.service.interfaces.ICurveService;
import com.globerry.project.utils.dropdown_menu.DropdownMenu;
import com.globerry.project.utils.dropdown_menu.DropdownMenuItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * Handles and retrieves the common or admin page depending on the URI template.
 * A user must be log-in first he can access these pages.  Only the admin can see
 * the adminpage, however.
 */
@Controller
@RequestMapping("/main")
@Scope("session")
public class MainController {

	protected static Logger logger = Logger.getLogger(MainController.class);
	
	/**
	 * Company Service for admin page.
	 */
	@Autowired
	private ICompanyService companyService;
	
	/**
	 * City Service for admin page.
	 */
	@Autowired
	private ICityService cityService;
	
	@Autowired
	private ICurveService curveService;
	
	@Autowired
	private ICurveDao curveMongoDao;
	
	/**
	 * Handles and retrieves the common JSP page that everyone can see
	 * 
	 * @return the name of the JSP page
	 */
    @RequestMapping(value = "/common", method = RequestMethod.GET)
    public String getCommonPage() {
    	logger.debug("Received request to show common page");
    
    	// Do your work here. Whatever you like
    	// i.e call a custom service to do your business
    	// Prepare a model to be used by the JSP page
    	
    	// This will resolve to /WEB-INF/jsp/commonpage.jsp
    	return "commonpage";
	}
    
    /**
     * Handles and retrieves the admin JSP page that only admins can see
     * 
	 * @param map Container for variables
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getAdminPage(ModelMap map) {
    	logger.debug("Received request to show admin page");
    
    	DropdownMenu ddm = new DropdownMenu();
		DropdownMenuItem ddmiCompany = new DropdownMenuItem("Company");
		DropdownMenuItem ddmiCity = new DropdownMenuItem("City");
		DropdownMenuItem ddmiFor;
		ddm.setName("AdminMenu");
		ddm.addItemToRoot(ddmiCity);
		for (City city : cityService.getCityList()) {
			ddmiFor = new DropdownMenuItem(city.getName());
			ddm.addItem(ddmiCity, ddmiFor);
		}
		ddm.addItemToRoot(ddmiCompany);
		for (Company company : companyService.getCompanyList()) {
			ddmiFor = new DropdownMenuItem(company.getName());
			ddm.addItem(ddmiCompany, ddmiFor);
		}
		map.put("logged", true);
		map.put("loggedName", "admin");
		map.put("menu", ddm);
		map.put("stepLat", curveService.getStepLat());
		map.put("stepLng", curveService.getStepLng());
		return "admin/adminMainPage";
	}
	
    @RequestMapping(value = "admin/uploadpage", method = RequestMethod.POST)
    public String getAdminUploadPage()
    {
	logger.info("get upload page");
	return "admin/uploadForm";
    }
	
	@RequestMapping(value = "admin/dmongo", method = RequestMethod.POST)
	public String dropMongo(ModelMap map) {
		logger.info("dropMongoDB");
		curveMongoDao.dropDB();
		map.put("stepLat", curveService.getStepLat());
		map.put("stepLng", curveService.getStepLng());
		return "admin/adminMainPage";
	}
	
	@RequestMapping(value = "admin/rMongo", method = RequestMethod.POST)
	public String recreateMongo(
			@RequestParam(value = "stepLat") float stepLat, 
			@RequestParam(value = "stepLng") float stepLng,
			ModelMap map
		) {
		logger.info("recreate");
		dropMongo(map);
		curveService.setStepLat(stepLat);
		curveService.setStepLng(stepLng);
		map.put("stepLat", curveService.getStepLat());
		map.put("stepLng", curveService.getStepLng());
		return "admin/adminMainPage";
	}
}
