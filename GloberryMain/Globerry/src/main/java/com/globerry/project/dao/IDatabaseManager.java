/**
 * 
 */
package com.globerry.project.dao;

/**
 * @author Artem
 * Интерфейс для класса, который работает напрямую с базой данных
 */
public interface IDatabaseManager
{
    /**
     * Очищает базу данных полностью
     */
    public void cleanDatabase();
}
