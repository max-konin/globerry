/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.dao;

import com.globerry.project.domain.City;
import java.util.List;

/**
 * Унифицированный интерфейс доступа к данным
 * @author max
 */
public interface IDao<T> 
{
    /**
     * Добавляет сущность в бд
     * @param entity 
     */
    void add(T entity);
    
    /**
     * Удаляет сущность из бд
     * @param entity 
     */
    void remove(T entity);
    
    /**
     * Обновляет
     * @param entity 
     */
    void update(T entity);
    
    /**
     * Возврощает все содержание таблицы
     * @return 
     */
    List<T> getAll(Class clazz);
    
    /**
     * Выполняет HQL запрос и возвращает результат
     * @param query hql запрос
     * @return список сущностей
     */
    List<T> getByQuery(String query);
    
    /**
     * Выполняет HQL запрос и возвращает результат
     * @param query hql запрос
     * @return список сущностей
     */
    T getById(Class clazz, int id);
}
