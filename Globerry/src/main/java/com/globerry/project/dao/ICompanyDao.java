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
    public List<Company> getCompanyList();
    /**
     * Обновляет newCompany по id
     * @param newCompany
     */
    public void updateCompany(Company newCompany);
    
    public Company getCompanyByLogin(String login);
    
    public Company getCompanyByEmail(String email);
    
    public Company getCompanyById(int id);

}
