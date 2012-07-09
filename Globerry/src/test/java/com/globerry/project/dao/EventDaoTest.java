package com.globerry.project.dao;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.Tour;
import java.util.HashSet;
import org.junit.BeforeClass;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/daoTestContext.xml")
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	ContextLoaderListener.class
})
/**
 * @author Sergey Krupin
 *
 */
public class EventDaoTest {

	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	IEventDao eventDao;
	@Autowired
	ICityDao cityDao;
	@Autowired
	ICompanyDao companyDao;
	
	final String imageEvent = "5";
	final Month monthEvent = Month.DECEMBER;
	final String nameEvent = "new year";
	final String descriptionEvent = "adskjlfnh";
	final String nameCity = "magadan";
	final String imageEvent2 = "3";
	final Month monthEvent2 = Month.SEPTEMBER;
	final String nameEvent2 = "1st september";
	
	private static Event event1;
	private static Event event2;
	private static City city1;
	private static City city2;

	@BeforeClass
	public static void BeforeClassTestInitialize() {
		event1 = new Event();
		event2 = new Event();
		event1.setName(getGeneratedString());
		event1.setRu_name(getGeneratedString());
		event1.setMonth(Month.MARCH);
		event1.setDescription(getGeneratedString());
		event1.setRu_description(getGeneratedString());
		event1.setCities(new HashSet());
		event1.setImage(getGeneratedString());
		event2.setName(getGeneratedString());
		event2.setRu_name(getGeneratedString());
		event2.setMonth(Month.MARCH);
		event2.setDescription(getGeneratedString());
		event2.setRu_description(getGeneratedString());
		event2.setCities(new HashSet());
		event2.setImage(getGeneratedString());
		city1 = new City();
		city2 = new City();
		city1.setName(getGeneratedString());
		city1.setRu_name(getGeneratedString());
		city1.setArea(50);
		city1.setEvents(new HashSet<Event>());
		city1.setIsValid(Boolean.TRUE);
		city1.setLatitude(5);
		city1.setLongitude(30);
		city1.setMessage(null);
		city2.setName(getGeneratedString());
		city2.setRu_name(getGeneratedString());
		city2.setArea(50);
		city2.setEvents(new HashSet<Event>());
		city2.setIsValid(Boolean.TRUE);
		city2.setLatitude(50);
		city2.setLongitude(10);
		city2.setMessage(getGeneratedString());
	}

	/**
	 * Create a random string contains 8 characters.
	 *
	 * @return Random String
	 */
	private static String getGeneratedString() {
		final int LENGHT = 8;
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < LENGHT; x++) {
			sb.append((char) ((int) (Math.random() * 26) + 97));
		}
		return sb.toString();
	}
	
	@Test(expected=NullPointerException.class)
	public void addEventWithCityOutDatabaseTest() {
		eventDao.addEvent(event1, city1);
	}
	
	@Test
	public void addEventWithCityTest() {
		try {
			cityDao.addCity(city1);
			eventDao.addEvent(event1, city1);
		} catch(MySqlException mse) {
			mse.printStackTrace(System.err);
		}
	}
	
	@Test
	public void initCityTest() {
		Event event = new Event();
		event.setImage(imageEvent);
		event.setMonth(monthEvent);
		event.setName(nameEvent);
		event.setDescription(descriptionEvent);

		City city = new City();
		city.setName(nameCity);

		City city2 = new City();
		city2.setName(nameCity);

		Event event2 = new Event();
		event2.setImage(imageEvent2);
		event2.setMonth(monthEvent2);
		event2.setName(nameEvent2);
		event2.setCities(event.getCities());
	}

	@Test
	public void removeEventTest() throws MySqlException {
		try {
			Event event = new Event();
			event.setImage(imageEvent);
			event.setMonth(monthEvent);
			event.setName(nameEvent);
			event.setDescription(descriptionEvent);

			City city = new City();
			city.setName(nameCity);

			cityDao.addCity(city);
			eventDao.addEvent(event, city);
			eventDao.removeEvent(event);
		} catch (MySqlException e) {
			fail(e.getDescription());
		}
	}

	@Test
	public void getEventListTest() throws MySqlException //не Работает разобраться!
	{
		Event event = new Event();
		event.setImage(imageEvent);
		event.setMonth(monthEvent);
		event.setName(nameEvent);
		event.setDescription(descriptionEvent);

		City city = new City();
		city.setName(nameCity);

		cityDao.addCity(city);
		eventDao.addEvent(event, city);
		Set<Event> listEvents = city.getEvents();
		assertTrue(listEvents.contains(event));

		List<Event> listEventsMonth = eventDao.getEventList(monthEvent, city);
		assertEquals(nameEvent, listEventsMonth.iterator().next().getName());

		List<Event> allEvent = eventDao.getEventList();
		for (int i = 0; i < allEvent.size(); i++) {
			System.out.println(allEvent.get(i).getName());
		}
	}

	@Test
	public void getEventListWithMonth() throws MySqlException {
		try {
			Event event = new Event();
			event.setImage("1");
			event.setMonth(Month.DECEMBER);
			event.setName("Посещение музея");
			event.setDescription("test");
			City city = new City();
			city.setName("Novosibirsk");
			cityDao.addCity(city);
			eventDao.addEvent(event, city);

			List<Event> listEvent = eventDao.getEventList(Month.DECEMBER, city);
			Iterator<Event> it = listEvent.iterator();
			while (it.hasNext()) {
				System.out.println(it.next().getName());
			}
		} catch (MySqlException e) {
			fail(e.getDescription());
		}
	}

	@Test
	public void getEventById() {
		try {
			Event eventNew = new Event();
			eventNew.setDescription("sdhdsfghdh");
			System.err.println(eventNew.getId());
			Event event = eventDao.getEventById(eventDao.addEvent(eventNew));
			System.err.print(event.getDescription());
		} catch (Exception e) {
			e.printStackTrace();
			fail("not found 1 event");
		}
	}

	@Test
	public void updateTest() {
		Event oldEvent = new Event();
		oldEvent.setName("GoodByeWorld");
		eventDao.addEvent(oldEvent);

		oldEvent.setName("HelloWorld");

		eventDao.updateEvent(oldEvent);
		assert (oldEvent.getName().equals(eventDao.getEventById(oldEvent.getId())));
	}

	@Test
	public void removeRelationTest() {
		Event event = new Event();
		event.setName("Remove relationship from event");
		City city = new City();
		city.setName("testName");
	}
}
