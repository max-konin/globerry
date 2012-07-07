package com.globerry.project.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import com.globerry.project.MySqlException;
import com.globerry.project.dao.ICompanyDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.Tour;
import org.apache.log4j.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.stereotype.Repository;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

@Repository
public class CompanyDao implements ICompanyDao {

	public static final Integer ROLE_USER = 2;
	public static final Integer ROLE_ADMIN = 1;
	@Autowired
	private SessionFactory sessionFactory;
	protected static final Logger logger = Logger.getLogger(CompanyDao.class);

	@Override
	public void addCompany(Company company) throws MySqlException {
		try {
			Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().save(company);
			tx.commit();
			sessionFactory.close();
		} catch (ConstraintViolationException e) {
			MySqlException mySqlExc = new MySqlException();
			mySqlExc.setMyClass(company);
			mySqlExc.setDescription("Email or login is unique. Change email or login");
			throw mySqlExc;
		}
	}

	@Override
	public void removeCompany(Company company) {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Delete person
		session.delete(company);
		tx.commit();
		sessionFactory.close();
	}

	@Override
	public void removeCompany(int id) {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		Company company = (Company) sessionFactory.getCurrentSession().load(
				Company.class, id);
		if (null != company) {

			sessionFactory.getCurrentSession().delete(company);

		}
		tx.commit();
		sessionFactory.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getCompanyList() {
		List<Company> companiesList;
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		companiesList = sessionFactory.getCurrentSession().createQuery("from Company").list();
		tx.commit();
		sessionFactory.close();
		return companiesList;
	}

	@Override
	public void updateCompany(Company newCompany) {
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		Session session = sessionFactory.getCurrentSession();
		session.update(newCompany);
		tx.commit();
	}

	@Override
	public List<Tour> getCompanyTourList(Company company) {

		/*
		 * Session session = sessionFactory.getCurrentSession();
		 *
		 * Query query = session.createQuery("FROM Person as p WHERE
		 * p.id="+personId);
		 *
		 * Person person = (Person) query.uniqueResult();
		 *
		 * // Retrieve all return new ArrayList<CreditCard>(person.getCreditCards());//
		 */
		return null;
	}

	@Override
	public Company getCompanyByLogin(String login) {
		Company company;
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		try {
			company = (Company) sessionFactory.getCurrentSession().createQuery("FROM Company C WHERE C.login='" + login + "'").list().get(0);
		} catch (SQLGrammarException e) {
			logger.error("LOGIN IS NOT EXIST");
			e.printStackTrace(System.err);
			return null;
		} catch (IndexOutOfBoundsException ioobe) {
			logger.error("LOGIN IS NOT EXIST");
			ioobe.printStackTrace(System.err);
			return null;
		}
		tx.commit();
		sessionFactory.close();
		return company;
		/*
		 * List<Company> companyList = this.TestDB(); Company company = null;
		 * for(Company companyBuff:companyList) {
		 * if(companyBuff.getLogin().equals(login)) { company = companyBuff;
		 * break; } } return company;
		 */
	}

	@Override
	public Company getCompanyByEmail(String email) {
		Company company;
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		try {
			company = (Company) sessionFactory.getCurrentSession().createQuery("FROM Company C WHERE C.email='" + email + "'").list().get(0);
		} catch (SQLGrammarException e) {
			logger.error("EMAIL IS NOT FOUND");
			e.printStackTrace(System.err);
			return null;
		} catch (IndexOutOfBoundsException ioobe) {
			logger.error("EMAIL IS NOT FOUND");
			ioobe.printStackTrace(System.err);
			return null;
		}
		tx.commit();
		sessionFactory.close();
		return company;
	}

	//Что это?
	public List<Company> TestDB() {
		List<Company> companyList = new ArrayList<Company>();
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();

		Company company1 = new Company();
		company1.setLogin("User");
		company1.setAccess(ROLE_USER);
		company1.setPassword(md5.encodePassword("User", null));

		Company company2 = new Company();
		company2.setLogin("Admin");
		company2.setAccess(ROLE_ADMIN);

		company2.setPassword(md5.encodePassword("Admin", null));
//	    user.setPassword("21232f297a57a5a743894a0e4a801fc3");

		companyList.add(company1);
		companyList.add(company2);
		return companyList;
	}

	@Override
	public Company getCompanyById(int id) {
		logger.error("SESSION FACTORY: " + (sessionFactory == null));
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		Company company = (Company) sessionFactory.getCurrentSession().get(Company.class, id);
		tx.commit();
		sessionFactory.close();
		return company;
	}
}
