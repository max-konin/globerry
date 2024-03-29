package com.globerry.project.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.globerry.project.MySqlException;
import com.globerry.project.dao.*;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;
import com.globerry.project.service.interfaces.IAgentService;
import org.springframework.context.annotation.Scope;

/**
 * A custom service for retrieving users from a custom datasource, such as a
 * database.
 * <p>
 * This custom service must implement Spring's {@link UserDetailsService}
 */
@Service("AgentService")
@Scope("session")
public class AgentService implements UserDetailsService, IAgentService {
	
	protected static Logger logger = Logger.getLogger(AgentService.class);

	@Autowired
	private IDao<Company> companyDao;
	
	@Autowired 
	private IDao<Tour> tourDao;

	private Company currentCompany;	    

		
	@Override
	public void addTour(Tour tour)
	{
	    currentCompany.getTourList().add(tour);
		companyDao.update(currentCompany);
	}
	
	@Override
	public void updateTour(Tour oldTour, Tour newTour)
	{
	    if (oldTour.getCompany().getId() == currentCompany.getId())
	    {
		//TODO Быдлятский цикл. нужно переделать (а для этого надо переписать 
			//Contains или Equals у турлиста или тура соответственно)
		Iterator<Tour> iterator = currentCompany.getTourList().iterator();
		while(iterator.hasNext())
		{
		    Tour _tour = iterator.next();
		    if (_tour.getId() == oldTour.getId())
		    {
			oldTour = _tour;
			break;
		    }
		}
		tourDao.update(newTour);
	    }
	    else
		throw new IllegalArgumentException();
	}

	@Override
	public void removeTour(Tour tour)
	{
	    if (tour.getCompany().getId() == currentCompany.getId())
	    {
		//TODO Быдлятский цикл. нужно переделать (а для этого надо переписать
			//Contains или Equals у турлиста или тура соответственно)
		Iterator<Tour> iterator = currentCompany.getTourList().iterator();
		while(iterator.hasNext())
		{
		    Tour _tour = iterator.next();
		    if (_tour.getId() == tour.getId())
		    {
			//currentCompany.getTourList().remove(_tour);
			iterator.remove();
			//break;
		    }
		}
		tourDao.remove(tour);
		companyDao.update(currentCompany);
	    }
	    else
		throw new IllegalArgumentException();
	}
	
	@Override
	public Company returnCurrentCompany()
	{
	    return currentCompany;
	}

	@Override
	public void companyUpdate(Company company) throws MySqlException
	{
		companyDao.update(company);
	    currentCompany = company;
	}
	
	
	//CompanyService cmpService;
	/**
	 * Retrieves a user record containing the user's credentials and access. 
	 */
	public UserDetails loadUserByUsername(String login)
			/*throws UsernameNotFoundException, DataAccessException*/ {
		
		// Declare a null Spring User
               
		/*UserDetails user = null;
		
		try {
		    	logger.error("Login - " + login);
			// Search database for a user that matches the specified username
			// You can provide a custom DAO to access your persistence layer
			// Or use JDBC to access your database
			// DbUser is our custom domain user. This is not the same as Spring's User
                        // TODO
                        // В QueryFactory сделать метод, который получает компанию по логину
			Company company = companyDao.getCompanyByLogin(login);
			
			logger.error("Company Login - " + company.getLogin());
			// Populate the Spring User object with details from the dbUser
			// Here we just pass the username, password, and access level
			// getAuthorities() will translate the access level to the correct role type
			user =  new User(
					company.getLogin(), 
					company.getPassword().toLowerCase(),
					true,
					true,
					true,
					true,
					getAuthorities(company.getAccess()));
			currentCompany = company;

		} catch (Exception e) {
			logger.error("Error in retrieving user");
			
			throw new UsernameNotFoundException("Error in retrieving user");
		}
		
		// Return user to Spring for processing.
		// Take note we're not the one evaluating whether this user is authenticated or valid
		// We just merely retrieve a user that matches the specified username
		return user;*/
            return null;
	}
	
	/**
	 * Retrieves the correct ROLE type depending on the access level, where access level is an Integer.
	 * Basically, this interprets the access value whether it's for a regular user or admin.
	 * 
	 * @param access an integer value representing the access of the user
	 * @return collection of granted authorities
	 */
	 public Collection<GrantedAuthority> getAuthorities(Integer access) {
			// Create a list of grants for this user
			List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
			
			// All users are granted with ROLE_USER access
			// Therefore this user gets a ROLE_USER by default
			logger.debug("Grant ROLE_USER to this user");
			authList.add(new GrantedAuthorityImpl("ROLE_USER"));
			
			// Check if this user has admin access 
			// We interpret Integer(1) as an admin user
			if ( access.compareTo(1) == 0) {
				// User has admin access
				logger.debug("Grant ROLE_ADMIN to this user");
				authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
			}

			// Return list of granted authorities
			return authList;
	  }
}
