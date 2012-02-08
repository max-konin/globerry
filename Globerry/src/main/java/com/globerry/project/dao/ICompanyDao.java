package com.globerry.project.dao;

import java.util.List;

import com.globerry.project.domain.Company;
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
     * Возвращает список компаний
     * @return
     */
    public List<Company> getCompanyList();
    /**
     * Заменяет oldCompany на newCompany
     * @param oldCompany
     * @param newCompany
     */
    public void updateCompany(Company oldCompany, Company newCompany);
}
