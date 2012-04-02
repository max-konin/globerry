/**
 * 
 */
package com.globerry.project.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;



/**
 * @author Artem
 *
 */
@Service
public interface IEntityCreator
{
    /**
     * Конструирует файл jsp.
     */
    public String getJspFile();
    /**
     * Создаёт лист и добавляет его в Map
     * @return
     */
    public void  setList(Map<String, Object> map);
    
    public void removeElem(int id);
}