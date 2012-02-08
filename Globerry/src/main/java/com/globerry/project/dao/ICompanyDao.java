package com.globerry.project.dao;

import java.util.List;

import com.globerry.project.domain.Company;
/**
 * @author ������ ������
 */
public interface ICompanyDao
{
    /**
     * ��������� ��������
     * @param company
     */
    public void addCompany(Company company);
    /**
     * ������� ��������
     * @param company
     */
    public void removeCompany(Company company);
    /**
     * ���������� ������ ��������
     * @return
     */
    public List<Company> getCompanyList();
    /**
     * �������� oldCompany �� newCompany
     * @param oldCompany
     * @param newCompany
     */
    public void updateCompany(Company oldCompany, Company newCompany);
}
