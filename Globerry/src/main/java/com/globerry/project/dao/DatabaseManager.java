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
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.SQLGrammarException;
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
    public void cleanDatabase(String name)
    {

	
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	Session session = sessionFactory.getCurrentSession();
	int tableCount = session.createSQLQuery("select table_name from INFORMATION_SCHEMA.tables WHERE TABLE_SCHEMA = '"+name+"'").list().size();
	/*Такой странный алгоритм происходит из-за того, что у нас каскады не очень хорошо настроены
	 * Mysql не даёт удалить родительский элемент если он связан с дочерним. Поэтому по-хорошему,
	 * надо удалять сперва Tag и PropertyType и dependingmonthproperty, потом всё остальное
	 */
	for(int i = 0; i < tableCount; i++)
	{
	    List<String> tableNames = session.createSQLQuery("select table_name from INFORMATION_SCHEMA.tables WHERE TABLE_SCHEMA = '"+name+"'").list();
	    for(int j = 0; j < tableNames.size(); j++)
	    {
        	    try
        	    {
        		logger.info(tableNames.get(j));
        		Query query = sessionFactory.getCurrentSession().createSQLQuery("truncate table " + tableNames.get(j));
        		query.executeUpdate();
        		
        	    }
        	    catch(QuerySyntaxException qse)
        	    {
        		Query query = sessionFactory.getCurrentSession().createSQLQuery("delete table " + tableNames.get(j));
        		query.executeUpdate();
        		logger.info(tableNames.get(j));
        	    }
        	   catch(SQLGrammarException sqlge)
        	    {
        		continue;
        	    }
        	    
        	    /*
        	    catch(IllegalArgumentException iae)
        	    {
        		continue;
        	    }*/
	    }
	    
	}
	tx.commit();
	sessionFactory.close();
    }
    @Override
    public void cleanDatabase()
    {
	cleanDatabase("globerry");
    }

}
