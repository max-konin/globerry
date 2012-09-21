/**
 * 
 */
package com.globerry.project.integration.dao;

import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import org.springframework.orm.hibernate3.SessionHolder;

import org.springframework.test.context.TestContext;

import org.springframework.test.context.support.AbstractTestExecutionListener;

import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Artem
 *
 */

public class ContextLoaderListener extends AbstractTestExecutionListener {

    private static final String SESSION_FACTORY_BEAN = "sessionFactory";

    private SessionFactory _sessionFactory;

    private Session _session;  


    @Override
    @Before
    public void beforeTestMethod(TestContext testContext) throws Exception {

        _sessionFactory = (SessionFactory) testContext.getApplicationContext()

                .getBean(SESSION_FACTORY_BEAN);

        _session = SessionFactoryUtils.getSession(_sessionFactory, true);

        TransactionSynchronizationManager.bindResource(_sessionFactory,

                new SessionHolder(_session));
        
        
    }@Autowired
    SessionFactory sessionFactory;

    @Override
    @After
    public void afterTestMethod(TestContext testContext) throws Exception {

        TransactionSynchronizationManager.unbindResource(_sessionFactory);

        SessionFactoryUtils.releaseSession(_session, _sessionFactory);

    }

}

 