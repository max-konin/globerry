package com.globerry.project.service;

public class EventUI
{
    private Object parent;
    private String description;
    public EventUI(Object parent){
	this.parent = parent;
    }
    public EventUI(Object parent,String description){
	this.parent = parent;
	this.description = description;
    }
    public Object getParent()
    {
	return parent;
    }
    public void setParent(Object parent)
    {
	this.parent = parent;
    }
    public String getDescription()
    {
	return description;
    }
    public void setDescription(String description)
    {
	this.description = description;
    }
}
