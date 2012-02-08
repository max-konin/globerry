package com.globerry.project.dao;

import java.util.List;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Proposals;
/**
 * @author ������ ������
 *
 */
public interface IProposalsDao
{
    /**
     * ��������� �����������
     * @param proposals
     */
    public void addProposals(Proposals proposals);
    /**
     * ������� �����������
     * @param proposals
     */
    public void removeProposals(Proposals proposals);
    /**
     * ���������� ������ ����������� ��� ��������� ������
     * @param city
     */
    public List<City> getPropListByCity(City city);
    /**
     * �������� oldProposals �� newProposals
     * @param oldProposals
     * @param newProposals
     */
    public void updateProposals(Proposals oldProposals,Proposals newProposals);
}
