package com.globerry.project.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Proposals;

@Repository
public class ProposalsDao implements IProposalsDao
{
    @Autowired
    SessionFactory sessionFactory;
    
    @Override
    public void addProposals(Proposals proposals)
    {
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	if (proposals.getCity().getId() > 0)
	    proposals.setId(proposals.getCity().getId());
	else
	    throw new RuntimeException();
	sessionFactory.getCurrentSession().save(proposals);
	tx.commit();
	sessionFactory.close();
    }

    @Override
    public void removeProposals(Proposals proposals)
    {
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	sessionFactory.getCurrentSession().delete(proposals);
	tx.commit();
	sessionFactory.close();
    }

    @Override
    public void updateProposals(Proposals oldProposals, Proposals newProposals)
    {
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	newProposals.setId(oldProposals.getId());
	sessionFactory.getCurrentSession().update(newProposals);
	tx.commit();
	sessionFactory.close();
    }

}
