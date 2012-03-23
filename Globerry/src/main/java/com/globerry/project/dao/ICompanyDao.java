package com.globerry.project.dao;

import java.util.List;
import java.util.Set;

import com.globerry.project.MySqlException;
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
     * @throws MySqlException когда поля не уникальные
     */
    public void addCompany(Company company) throws MySqlException;
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
    public Set<Company> getCompanyList();
    /**
     * Заменяет oldCompany на newCompany
     * @param oldCompany
     * @param newCompany
     */
    public void updateCompany(Company oldCompany, Company newCompany) throws MySqlException;
    public void updateCompany(Company newCompany);
    /**
     * Функция для получения списка туров
     * @param company компания
     * @return Получает список туров компании
     */
    public Set<Tour> getCompanyTourList(Company company);
    
    public Company getCompanyByLogin(String login);

}
