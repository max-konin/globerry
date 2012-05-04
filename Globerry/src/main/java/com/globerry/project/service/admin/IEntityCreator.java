/**
 * 
 */
package com.globerry.project.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;



/**
 * @author Artem
 *
 */
public interface IEntityCreator
{
    /**
     * Конструирует файл jsp.
     */
    public String getJspListFile();
    /**
     * Создаёт лист и добавляет его в Map
     * @return
     */
    public void  setList(Map<String, Object> map);
    
    
    public void removeElem(int id);
    
    public void getElemById(Map<String, Object> map, int id);
    
    public  String getJspUpdateFile();
    
    public void updateElem(Object object);
    /**
     * Запихивает в map все list связанные с элементом
     */
    public Map<String, Object> getRelation(Map<String, Object> map, int id);
    /**
     * 
     */
    
}