package com.globerry.project.service.admin;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globerry.project.dao.ICityDao;
import com.globerry.project.domain.City;

@Service
public class DamagedCities
{
    @Autowired
    private ICityDao cityDao;
    
    private List<City> damagedCitiesList; 
    
    public void createFile()
    {
	//InputStream inp = new FileInputStream("workbook.xls");
	//InputStream inp = new FileInputStream("workbook.xlsx");
	//FileOutputStream fileOut = new FileOutputStream("workbook.xls");
	damagedCitiesList = cityDao.getDamagedCities(); 
	Workbook wb= new XSSFWorkbook();  
	Sheet sheet = wb.createSheet();
	for (int i = 0; i < damagedCitiesList.size(); i++)
	{
		Row row = sheet.getRow(1);
		Cell cell = row.getCell(i);
		cell = row.createCell(i);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(damagedCitiesList.get(i).getName());
	}

	FileOutputStream fileOut;
	try
	{
	    fileOut = new FileOutputStream("workbook.xls");
	    wb.write(fileOut);
	    fileOut.close();
	} catch (FileNotFoundException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }
}
