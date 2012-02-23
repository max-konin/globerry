package com.globerry.project.dao;

import java.util.List;

import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;
/**
 * @author Сергей Крупин
 */
public interface ICompanyDao
{
    /**
     * Добавляет компанию
     * @param company
     */
    public void addCompany(Company company);
    /**
     * Удаляет компанию
     * @param company
     */
    public void removeCompany(Company company);
    /**
     * Удаляет Company по id
     * @param id Id в таблице
     */
    public void removeCompany(int id);
    /**
     * 
     * @return Возвращает список копаний
     */
    public List<Company> getCompanyList();
    /**
     * Заменяет oldCompany на newCompany
     * @param oldCompany
     * @param newCompany
     */
    public void updateCompany(Company oldCompany, Company newCompany);
    /**
     * Функция для получения списка туров
     * @return Получает список туров компании
     */
    public List<Tour> getCompanyTourList();
}
