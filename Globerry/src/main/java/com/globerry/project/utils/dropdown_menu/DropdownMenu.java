package com.globerry.project.utils.dropdown_menu;

import java.util.HashMap;
import java.util.Stack;

import org.hibernate.transform.RootEntityResultTransformer;

public class DropdownMenu implements IDropdownMenu
{

    DropdownMenuItem rootItem;
    String name, id;
    
    public DropdownMenu()
    {
	rootItem = new DropdownMenuItem("root");
    }
    
    @Override
    public DropdownMenuItem getRootElement()
    {
	return rootItem;
    }

    @Override
    public DropdownMenuItem getItem(String name)
    {
	return DropdownMenu.searchItemByName(name, rootItem);
	
    }

    
    @Override
    public void addItem(DropdownMenuItem item, DropdownMenuItem parent)
	    throws IllegalArgumentException, NullPointerException
    {
	if(item == null || parent == null)
	    throw new NullPointerException("Parent and/or child cannot be null");
	parent.children.add(item);
	
    }

    @Override
    public void addItem(String path, DropdownMenuItem item)
	    throws IllegalArgumentException, NullPointerException
    {
	if(item == null)
	    throw new NullPointerException("Added item cannot be null");
	if(DropdownMenu.searchItem(item, rootItem) != null)
	    throw new IllegalArgumentException("Such element already exists in this tree");
	String[] chunks = path.split("/");
	DropdownMenuItem currentItem = rootItem;
	
	boolean flag = true;
	for(String chunk : chunks)
	{
	    for(DropdownMenuItem children : currentItem.getChildren())
	    {
		if(children.getName().equals(chunk))
		{
		    //System.out.println(children.getName() + " " + chunk);
		    currentItem = children;
		    flag = false;
		    break;
		}
	    }
	    if(flag)
		throw new IllegalArgumentException("Invalid path");
	}
	this.addItem(item, currentItem);
	
    }

    @Override
    public void addItemToRoot(DropdownMenuItem item)
	    throws IllegalArgumentException, NullPointerException
    {
	rootItem.children.add(item);
    }

    @Override
    public void removeItem(String name) throws IllegalArgumentException,
	    NullPointerException
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void removeItem(DropdownMenuItem item)
	    throws IllegalArgumentException, NullPointerException
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public String getName()
    {
	return name;
    }

    @Override
    public void setName(String name)
    {
	this.name = name;
    }

    @Override
    public String getId()
    {
	return id;
    }

    @Override
    public void setId(String id)
    {
	this.id = id;
	
    }

    
    
    private static DropdownMenuItem searchItem(DropdownMenuItem goal, DropdownMenuItem item)
    {
	if(goal == item)
	    return item;
	for(int i = 0; i < item.children.size(); i++)
	{
	    DropdownMenuItem result = DropdownMenu.searchItem(goal, item.children.get(i));
	    if(result != null)
		return result;
	}
	return null;
    }
    
    
    private static DropdownMenuItem searchItemByName(String goal, DropdownMenuItem item)
    {
	if(goal == item.getName())
	    return item;
	for(int i = 0; i < item.children.size(); i++)
	{
	    DropdownMenuItem result = DropdownMenu.searchItemByName(goal, item.children.get(i));
	    if(result != null)
		return result;
	   
	}
	return null;
    }
    
    private static String getItemAsString(int tabCount, DropdownMenuItem item)
    {
	String ret = "";
	String tab = "";
	for(int i = 0; i < tabCount; i++)
	    tab += "\t";
	ret += tab + item.getName() + "\n";
	if (item.children.size() > 0)
	    for (int i = 0; i < item.children.size(); i++)
		ret += DropdownMenu.getItemAsString(tabCount + 1, item.children.get(i));
	    
	return ret;
    }
    
    private static String getItemAsHtml(DropdownMenuItem item)
    {
	String ret = "";
	ret += "<ul>\n";
	ret += "<li>" + item.getName() + "</li>";
	for(DropdownMenuItem child : item.children)
	    ret += "<li>" + DropdownMenu.getItemAsHtml(child) + "</li>";
	ret += "</ul>\n";
	return ret;
    }
    
    
    public String getMenuAsString()
    {
	return DropdownMenu.getItemAsString(0, rootItem);
    }
    
    public String getMenuAsHtml()
    {
	return DropdownMenu.getItemAsHtml(rootItem);
    }
    
}
