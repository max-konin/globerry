/**
 * 
 */
package com.globerry.project.service.admin;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.globerry.project.Excel;
import com.globerry.project.ExcelParserException;
import com.globerry.project.MySqlException;

/**
 * @author Artem
 *
 */
public interface IAdminParser
{
    public void updateCities(Excel excel) throws IOException, ExcelParserException;
    public void updateWikiContent() throws IOException;
}
