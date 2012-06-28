/**
 * 
 */
package com.globerry.project.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.hql.ast.QuerySyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Artem
 *
 */
@Repository
public class DatabaseManager implements IDatabaseManager
{
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public void cleanDatabase()
    {
	System.err.println("HELLO");
	
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	Session session = sessionFactory.getCurrentSession();
	List<String> tableNames = session.createSQLQuery("select table_name from INFORMATION_SCHEMA.tables WHERE TABLE_SCHEMA = 'globerry'").list();
	String deleteQuery = "";
	for(int i = 0; i < tableNames.size(); i++)
	{
	    try
	    {
		sessionFactory.getCurrentSession().createQuery("delete " + tableNames.get(i));
		System.err.println(tableNames.get(i));
	    }
	    catch(QuerySyntaxException qse)
	    {
		continue;
	    }
	    
	}
	tx.commit();
	sessionFactory.close();

    }

}
