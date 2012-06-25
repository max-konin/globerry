/**
 * 
 */
package com.globerry.project.service.admin;

import com.globerry.project.Excel;
import com.globerry.project.ExcelParserException;
import com.globerry.project.MySqlException;

/**
 * @author Artem
 *
 */
public interface IAdminParser
{
    public void updateCities(Excel excel) throws MySqlException, ExcelParserException;
    public void updateWikiContent();
}
