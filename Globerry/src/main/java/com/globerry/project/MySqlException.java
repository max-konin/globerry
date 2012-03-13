
package com.globerry.project;

/**
 * @author Artem
 *
 */
public class MySqlException extends Exception
{
   
    private Object myClass;
    
    private String description;

    public String getMyClass()
    {
	return myClass.toString();
    }

    public void setMyClass(Object myClass)
    {
	this.myClass = myClass;
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
