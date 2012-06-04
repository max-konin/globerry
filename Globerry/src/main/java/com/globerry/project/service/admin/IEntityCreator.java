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
    
    /**
     * Удаляет элемент по id
     * @param id
     */
    public void removeElem(int id);
    /**
     * Получает элемент по id и кладёт его в map
     * @param map
     * @param id
     */
    public void getElemById(Map<String, Object> map, int id);
    /**
     * Возвращает страницу для update
     * @return
     */
    public  String getJspUpdateFile();
    /**
     * Обновляет объект в базе
     * @param object
     */
    public void updateElem(Object object);
    /**
     * Запихивает в map все list связанные с элементом
     */
    public Map<String, Object> getRelation(Map<String, Object> map, int id);
    /**
     * Функция получающая абсолютно все элементы. Нет зависимости от элемента
     * @param map переменная, где всё хранится
     * @return
     */
    public void getRelation(Map<String, Object> map);
    /**
     * 
     * @param type
     * @param elementId
     * @param itemId
     */
    public void addRelaion(String type , int elementId, int itemId);
    /**
     * Удаляет объект из списка связей у элемента
     * @param type Тип удаляемого объекта
     * @param elementId Идентефикатор объекта, у которого из списка нужно выкинуть связь
     * @param itemId Идентефикатор объекта, который и нужно удалить
     */
    public void removeRelation(String type, int elementId, int itemId);
    
}