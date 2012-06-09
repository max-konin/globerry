package com.globerry.project.utils.dropdown_menu;

import java.util.*;

public class DropdownMenuItem
{
    private String name;

    protected List<DropdownMenuItem> children;
    
    public DropdownMenuItem()
    {
	children = new ArrayList<DropdownMenuItem>();	
    }
    
    public DropdownMenuItem(String name)
    {
	children = new ArrayList<DropdownMenuItem>();	
	this.name = name;
    }

    public Iterable<DropdownMenuItem> getChildren()
    {
	return children;
    }

    public String getName()
    {
	return name;
    }

    protected void setName(String name)
    {
	this.name = name;
    }
    


}
