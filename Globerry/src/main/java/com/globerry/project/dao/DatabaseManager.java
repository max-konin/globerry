/**
 * 
 */
package com.globerry.project.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
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
    private final Logger logger = Logger.getLogger(DatabaseManager.class);
    @Override
    public void cleanDatabase()
    {

	
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	Session session = sessionFactory.getCurrentSession();
	List<String> tableNames = session.createSQLQuery("select table_name from INFORMATION_SCHEMA.tables WHERE TABLE_SCHEMA = 'globerry'").list();

	for(int i = 0; i < tableNames.size(); i++)
	{
	    try
	    {
		Query query = sessionFactory.getCurrentSession().createQuery("delete from " + tableNames.get(i));
		System.err.println(tableNames.get(i));
		query.executeUpdate();
		
	    }
	    catch(QuerySyntaxException qse)
	    {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("delete from " + tableNames.get(i));
		query.executeUpdate();
		logger.info(tableNames.get(i));
		continue;
	    }
	    
	}
	tx.commit();
	sessionFactory.close();

    }

}
