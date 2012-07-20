/**
 * 
 */
package com.globerry.project;

/**
 * @author Artem
 *
 */
public class ExcelParserException extends Exception
{
    private String description;
    private int row;
    public ExcelParserException(String description, int row)
    {
	this.description = description;
	this.row = row;
    }
    public ExcelParserException()
    {
	
    }
    public String getDescription()
    {
	return description;
    }
    public int getRow()
    {
	return row;
    }
}
