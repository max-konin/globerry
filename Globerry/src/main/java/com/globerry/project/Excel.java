/**
 * 
 */
package com.globerry.project;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import java.util.Iterator;



import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class Excel {
private String fileName;
private Workbook wb;


//
public Excel(String fileName)
{
	this.fileName = fileName;
	try
	{
	    wb = WorkbookFactory.create(new FileInputStream(fileName));
	} catch (InvalidFormatException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
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

//Cell Numbers											Sheets
//id					0	float						city		0
//name					1	string						events		1
//funFactor				2	hz							city-events	2
//security				3	hz							city-tags	3
//alcoholCost			4	float
//foodCost				5	float
//touristsNumber		6	float
//visa					7	hz
//englishSpeaking		8	hz
//attractionsNumber		9	float



/**
 * Метод получает численные поля
 * @param sheet 		Страница в экселе
 * @param rowNumber		Строка в выбранной странице
 * @param cellNumber	Столбец в выбранной странице
 * @return				float значение таблицы
 * 						Если какая то ошибка - то -1;
 */
public double getFloatField(int sheetNumber, int rowNumber, int cellNumber) throws NullPointerException
{
	try {
		
		org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(sheetNumber);
		Row currentRow = sheet.getRow(rowNumber);
		Cell currentCell = currentRow.getCell(cellNumber);
		return currentCell.getNumericCellValue();

		}
		catch (NullPointerException e) {
		    return -1;
		}

	//return -1;
}
/**
 * Метод получает текстовые поля
 * @param sheetNumber		Страница в экселе
 * @param rowNumber			Строка в выбранной странице
 * @param cellNumber		Столбец в выбранной странице
 * @return					string значение таблицы
 * 							если ошибка то null;
 */
public String getStringField(int sheetNumber, int rowNumber, int cellNumber) throws NullPointerException
{
	try {
		org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(sheetNumber);
		Row currentRow = sheet.getRow(rowNumber);
		Cell currentCell = currentRow.getCell(cellNumber);
		return currentCell.getStringCellValue();

	    
		}
		catch (NullPointerException e) {
		    return null;
		}

}
/**
 * Функция которая вычисляет длину строки.
 * @param sheetNumber номер страницы
 * @return	количество ячеек в таблице
 */
public int getRowLenght(int sheetNumber)//я стандартной не нашёл!
{
	int j = 0;
	try
	{
		Workbook wb = WorkbookFactory.create(new FileInputStream(fileName));
		org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(sheetNumber);
		Row currentRow = sheet.getRow(1);
		Iterator<Cell> it = currentRow.cellIterator();
		while(it.hasNext())
		{
				j++;
				it.next();
		}
	}
	catch (Exception e)
	{
		e.printStackTrace();
		return 0;
	}/* catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}/* catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}//*/
	return j;	
}

/**
 * Функция получает длину вкладки
 * @param sheetNumber номер вкладки
 * @return Возвращает длину включая первую строку!
 */
public int getLenght(int sheetNumber)
{
	int j = 0;
	try
	{
		Workbook wb = WorkbookFactory.create(new FileInputStream(fileName));
		org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(sheetNumber);
		Iterator<Row> it = sheet.rowIterator();
		while(it.hasNext())
		{
				j++;
				it.next();
		}
	}
	catch (Exception e)
	{
		e.printStackTrace();
	} /*catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}//*/
	return j;	
}
public String getSheetName(int sheetNumber)
{
    Workbook wb;
    try
    {
	wb = WorkbookFactory.create(new FileInputStream(fileName));
	org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(sheetNumber);
	return sheet.getSheetName();
    } catch (InvalidFormatException e)
    {
	// TODO Auto-generated catch block
	e.printStackTrace();
    } catch (FileNotFoundException e)
    {
	// TODO Auto-generated catch block
	e.printStackTrace();
    } catch (IOException e)
    {
	// TODO Auto-generated catch block
	e.printStackTrace();
    }
    return "UNDEFINED SHEET";
}
//sheet 1 лист 1
/**
 * Функция получает ID города из первой таблицы
 * @param row строка в этой листе 1
 * @return id города в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getCityIdSheet1(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,0);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}

/**
 * Функция получает название города в листе 1
 * @param row строка в этой листе 1
 * @return	Название города в вкладке city
 */
public String getName(int row)
{
	return getStringField(0,row,1);
}
/**
 * Название города на русском в листе 1
 * @param row строка в листе 1
 * @return название города на русском
 */
public String getRussianName(int row)
{
	return getStringField(0,row,2);
}
/**
 * Функция получает Security в листе 1
 * @param row строка в этой таблице
 * @return Security в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getSеcurity(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,3);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает AlchogolCost в листе 1
 * @param row строка в этой таблице
 * @return AlchogolCost в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getAlchogolCost(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,4);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает FoodCost в листе 1
 * @param row строка в этой таблице
 * @return FoodCost в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getFoodCost(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,5);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Получает значение sex в листе 1
 * @param row  строка в этой таблице
 * @return sex в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getSex(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,6);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}

/**
 * Функция получает Visa в листе 1
 * @param row строка в этой таблице
 * @return Visa в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getVisa(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,7);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает EnglishSpeaking в листе 1
 * @param row строка в этой таблице
 * @return
 * EnglishSpeaking в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getEnglishSpeaking(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,8);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает AttractionNumber в листе 1
 * @param row строка в этой таблице
 * @return AttractionNumber в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getAttractionNumber(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,9);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает FunFactor January в листе 1
 * @param row строка в этой таблице
 * @return FunFactor в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getFunFactorJan(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,10);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает FunFactor February в листе 1
 * @param row строка в этой таблице
 * @return FunFactor в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getFunFactorFeb(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,11);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает FunFactor March в листе 1
 * @param row строка в этой таблице
 * @return FunFactor в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getFunFactorMarch(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,12);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает FunFactor April в листе 1
 * @param row строка в этой таблице
 * @return FunFactor в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getFunFactorApr(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,13);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает FunFactor May в листе 1
 * @param row строка в этой таблице
 * @return FunFactor в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getFunFactorMay(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,14);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает FunFactor June в листе 1
 * @param row строка в этой таблице
 * @return FunFactor в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getFunFactorJune(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,15);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает FunFactor July в листе 1
 * @param row строка в этой таблице
 * @return FunFactor в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getFunFactorJuly(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,16);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает FunFactor August в листе 1
 * @param row строка в этой таблице
 * @return FunFactor в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getFunFactorAug(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,17);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает FunFactor September в листе 1
 * @param row строка в этой таблице
 * @return FunFactor в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getFunFactorSept(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,18);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает FunFactor October в листе 1
 * @param row строка в этой таблице
 * @return FunFactor в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getFunFactorOct(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,19);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает FunFactor November в листе 1
 * @param row строка в этой таблице
 * @return FunFactor в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getFunFactorNov(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,20);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает FunFactor December в листе 1
 * @param row строка в этой таблице
 * @return FunFactor в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getFunFactorDec(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,21);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает TouristsNumber January в листе 1
 * @param row строка в этой таблице
 * @return TouristNumber в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getTouristsNumberJan(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,22);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает TouristsNumber February в листе 1
 * @param row строка в этой таблице
 * @return TouristNumber в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getTouristsNumberFeb(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,23);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает TouristsNumber March в листе 1
 * @param row строка в этой таблице
 * @return TouristNumber в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getTouristsNumberMarch(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,24);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает TouristsNumber April в листе 1
 * @param row строка в этой таблице
 * @return TouristNumber в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getTouristsNumberApril(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,25);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает TouristsNumber May в листе 1
 * @param row строка в этой таблице
 * @return TouristNumber в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getTouristsNumberMay(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,26);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает TouristsNumber June в листе 1
 * @param row строка в этой таблице
 * @return TouristNumber в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getTouristsNumberJune(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,27);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает TouristsNumber July в листе 1
 * @param row строка в этой таблице
 * @return TouristNumber в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getTouristsNumberJuly(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,28);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает TouristsNumber August в листе 1
 * @param row строка в этой таблице
 * @return TouristNumber в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getTouristsNumberAug(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,29);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает TouristsNumber Sept в листе 1
 * @param row строка в этой таблице
 * @return TouristNumber в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getTouristsNumberSept(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,30);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает TouristsNumber October в листе 1
 * @param row строка в этой таблице
 * @return TouristNumber в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getTouristsNumberOct(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,31);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает TouristsNumber November в листе 1
 * @param row строка в этой таблице
 * @return TouristNumber в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getTouristsNumberNov(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,32);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает TouristsNumber December в листе 1
 * @param row строка в этой таблице
 * @return TouristNumber в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getTouristsNumberDec(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(0,row,33);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает LivingCost Jan в листе 1
 * @param row строка в этой таблице
 * @return LivingCost в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public float getLivingCostJan(int row) throws IllegalArgumentException
{
	try
	{
		return (float)getFloatField(0,row,34);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает LivingCost Feb в листе 1
 * @param row строка в этой таблице
 * @return LivingCost в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public float getLivingCostFeb(int row) throws IllegalArgumentException
{
	try
	{
		return (float)getFloatField(0,row,35);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает LivingCost March в листе 1
 * @param row строка в этой таблице
 * @return LivingCost в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public float getLivingCostMarch(int row) throws IllegalArgumentException
{
	try
	{
		return (float)getFloatField(0,row,36);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает LivingCost April в листе 1
 * @param row строка в этой таблице
 * @return LivingCost в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public float getLivingCostApril(int row) throws IllegalArgumentException
{
	try
	{
		return (float)getFloatField(0,row,37);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает LivingCost May в листе 1
 * @param row строка в этой таблице
 * @return LivingCost в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public float getLivingCostMay(int row) throws IllegalArgumentException
{
	try
	{
		return (float)getFloatField(0,row,38);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает LivingCost June в листе 1
 * @param row строка в этой таблице
 * @return LivingCost в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public float getLivingCostJune(int row) throws IllegalArgumentException
{
	try
	{
		return (float)getFloatField(0,row,39);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает LivingCost July в листе 1
 * @param row строка в этой таблице
 * @return LivingCost в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public float getLivingCostJuly(int row) throws IllegalArgumentException
{
	try
	{
		return (float)getFloatField(0,row,40);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает LivingCost August в листе 1
 * @param row строка в этой таблице
 * @return LivingCost в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public float getLivingCostAug(int row) throws IllegalArgumentException
{
	try
	{
		return (float)getFloatField(0,row,41);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает LivingCost September в листе 1
 * @param row строка в этой таблице
 * @return LivingCost в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public float getLivingCostSept(int row) throws IllegalArgumentException
{
	try
	{
		return (float)getFloatField(0,row,42);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает LivingCost October в листе 1
 * @param row строка в этой таблице
 * @return LivingCost в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public float getLivingCostOct(int row) throws IllegalArgumentException
{
	try
	{
		return (float)getFloatField(0,row,43);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает LivingCost November в листе 1
 * @param row строка в этой таблице
 * @return LivingCost в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public float getLivingCostNov(int row) throws IllegalArgumentException
{
	try
	{
		return (float)getFloatField(0,row,44);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает LivingCost December в листе 1
 * @param row строка в этой таблице
 * @return LivingCost в вкладке city
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public float getLivingCostDec(int row) throws IllegalArgumentException
{
	try
	{
		return (float)getFloatField(0,row,45);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
//sheet 2
/**
 * Функция получает ID event в листе 2
 * @param row строка в этой таблице
 * @return
 * id в вкладке events
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getEventId(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(1,row,0);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает name в листе 2
 * @param row строка в этой таблице
 * @return name в вкладке events
 */
public String getNameSheet2(int row)
{
	return getStringField(1,row,1);
}
/**
 * Функция получает ru_name в листе 2
 * @param row строка в этой таблице
 * @return name в вкладке events
 */
public String getRuNameSheet2(int row)
{
	return getStringField(1,row,2);
}
/**
 * Функция получает Description в листе 2
 * @param row строка в этой таблице
 * @return Description в вкладке events
 */
public String getDescription(int row)
{
	return getStringField(1,row,3);
}
/**
 * Функция получает Ru_Description в листе 2
 * @param row строка в этой таблице
 * @return Description в вкладке events
 */
public String getRuDescription(int row)
{
	return getStringField(1,row,4);
}
/**
 * Функция получает Image в листе 2
 * @param row строка в этой таблице
 * @return Image в вкладке events
 */
public String getImage(int row)
{
	return getStringField(1,row,5);
}
/**
 * Функция получает month в events в листе 2
 * @param row строка в этой таблице
 * @return month в вкладке events
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getEventsMonths(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(1,row,6);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает cityId в листе 2
 * @param row строка в этой таблице
 * @return month в вкладке events
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getCityIdSheet2(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(1,row,8);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает Event_id в листе 2
 * @param row строка в этой таблице
 * @return month в вкладке events
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getEventIdSheet2(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(1,row,9);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}

//sheet 2
/**
 * Функция получает cityId в вкладке city-tags в листе 3
 * @param row строка в этой таблице
 * @return cityId в вкладке city-tags
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getCityIdSheet3(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(2,row,0);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает tagsType в вкладке city-tags в листе 3
 * @param row строка в этой таблице
 * @return tagsType в вкладке city-tags
 * @throws IllegalArgumentException если в row = 0, или в таблице тип String
 */
public int getTagsTypeSheet3(int row) throws IllegalArgumentException
{
	try
	{
		return (int)getFloatField(2,row,1);
	}
	catch(IllegalStateException e)
	{
		throw new IllegalArgumentException("Не соответствует тип. Должен получиться int, а в таблице что-то другое. Может появиться если row = 0");
	}
}
/**
 * Функция получает tag в листе 3
 * @param row строка в этой таблице
 * @return tag в вкладке city-tags
 */
public String getTag(int row)
{
	return getStringField(2,row,2);
}
}

