package com.globerry.project.dao;

import java.util.List;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Proposals;
/**
 * @author Сергей Крупин
 *
 */
public interface IProposalsDao
{
    /**
     * Добовляет предложение
     * @param proposals
     */
    public void addProposals(Proposals proposals);
    /**
     * Удаляет предложение
     * @param proposals
     */
    public void removeProposals(Proposals proposals);
    /**
     * Возвращает список предложений для определенного города
     * @param city
     */
    public List<City> getPropListByCity(City city);
    /**
     * Заменяет предложение oldProposals на newProposals
     * @param oldProposals
     * @param newProposals
     */
    public void updateProposals(Proposals oldProposals,Proposals newProposals);
}
