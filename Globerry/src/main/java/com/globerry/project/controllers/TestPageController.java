package com.globerry.project.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.globerry.project.service.admin.AbstractTypeFactory;
import com.globerry.project.utils.dropdown_menu.DropdownMenu;
import com.globerry.project.utils.dropdown_menu.DropdownMenuItem;
import com.globerry.project.utils.dropdown_menu.IDropdownMenu;

@Controller
@RequestMapping("/test_page")
public class TestPageController
{
    @Autowired
    private AbstractTypeFactory abstrFactory;
    
       
    private DropdownMenu menu;
    
    @RequestMapping("/")
    public String createForm(Map<String, Object> map)
    {
	if(menu == null)
	    fillMenu();
	map.put("menu", menu);
	
	//System.out.println(menu.getMenuAsString());
	//menu.getRootElement().getChildren().iterator().next()
	
	return "admin/header";
    }
    
    private void fillMenu()
    {
	menu = new DropdownMenu();
	
	menu.setName("test_menu");
	DropdownMenuItem element1 = new DropdownMenuItem("element1");
	DropdownMenuItem element2 = new DropdownMenuItem("element2");
	
	menu.addItemToRoot(element1);
	menu.addItemToRoot(element2);
	
	menu.addItem("element1/", new DropdownMenuItem("element4"));
	menu.addItem("element1/", new DropdownMenuItem("element3"));
	menu.addItem("element1/element4", new DropdownMenuItem("element5"));
	
	//menu.addItem(element2, new DropdownMenuItem("element6"));
	menu.addItem(new DropdownMenuItem("element6"), element2);
	menu.addItem("element2/element6", new DropdownMenuItem("element7"));
    }
}
