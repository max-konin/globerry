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
    private String message;
    private int row;
    public ExcelParserException(String message, int row)
    {
	this.message = message;
	this.row = row;
    }
    public ExcelParserException()
    {
	
    }
    public String getDescription()
    {
	return message;
    }
    public int getRow()
    {
	return row;
    }
}
