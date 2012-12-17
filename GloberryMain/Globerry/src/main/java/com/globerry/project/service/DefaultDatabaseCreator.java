package com.globerry.project.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globerry.project.MySqlException;

import com.globerry.project.dao.IDao;
import com.globerry.project.dao.IDatabaseManager;
import com.globerry.project.dao.IDao;
import com.globerry.project.service.StringManager;

import com.globerry.project.domain.*;
import com.globerry.project.service.interfaces.IProposalsManager;

import com.globerry.project.domain.Tour;
import com.globerry.project.service.interfaces.IProposalsManager;

import org.apache.log4j.Logger;

@Service
public class DefaultDatabaseCreator {

    @Autowired
    IDao<Tag> tagDao;
    @Autowired
    IDao<City> cityDao;
    @Autowired
    IDao<PropertyType> propertyTypeDao;
    @Autowired
    private IDatabaseManager databaseManager;
    @Autowired
    private IProposalsManager proposalsManager;
    protected static Logger logger = Logger.getLogger(DefaultDatabaseCreator.class);

    public void initTags() {
        String[] tagsNames = {"alone", "with friends", "with family", "double",
            "sunbathe", "ski", "looking", "shopping", "cruise"};

        for (int i = 0; i < 9; ++i) {
            Tag tag = new Tag(tagsNames[i]);
            tag.setImg("");
            if (i < 4) {
                tag.setTagsType(TagsType.WHO);
            } else {
                tag.setTagsType(TagsType.WHERE);
            }
            tagDao.add(tag);
        }

    }

    public void initPropertyType() {

        /*id	DependingMonth	betterWhenLess	maximumValue	minimumValue	name
         1		0		1		20		0	cost
         2		0		1		20		0	alcohol
         3		0		1		20		0	russian
         4		0		1		20		0	visa
         5		0		1		20		0	sex
         6		0		1		20		0	security
         7		1		1		300		0	livingCost
         8		1		1		300		0	mood
         9		1		1		35		-35	temperature*/
        createPropertyType(StringManager.foodCostPropertyTypeName, 0, 30, false, true);
        createPropertyType(StringManager.alcoholPropertyTypeName, 0, 30, false, true);
        createPropertyType(StringManager.russianPropertyTypeName, 0, 1, false, true);
        createPropertyType(StringManager.visaPropertyTypeName, 0, 1, false, true);
        createPropertyType(StringManager.sexPropertyTypeName, 0, 3, false, true);
        createPropertyType(StringManager.securityPropertyTypeName, 0, 3, false, true);
        createPropertyType(StringManager.livingCostPropertyTypeName, 0, 999, true, true);
        createPropertyType(StringManager.moodPropertyTypeName, 0, 3, true, true);
        createPropertyType(StringManager.temperaturePropertyTypeName, -35, 35, true, true);

    }

    public void initTours() {
        for (int i = 0; i < 20; i++) {
            proposalsManager.addTour(generateTour());
        }
        //System.err.println(tour.getName() + "\n " + tour.getDescription() + "\n" + tour.getTargetCityId() + "\n" + tour.getDateEnd().toString());
    }

    public void initTickets() {
        for (int i = 0; i < 20; i++) {
            proposalsManager.addTicket(generateTicket());
        }
    }

    public void initHotels() {
        String[] namesOfHotels = {"Hotel Villa Ducale", "La Fuitina", "The Levin", "The Montague on The Gardens",
            "Schlosshotel Im Grunewald", "Pullman Schweizerhof", "Hotel Primero Primera", "Patrick Hotel",
            "Ammos Hotel", "Niriis Hotel", "Sun City Studios", "Hotel Ostria Beach", "Galaxy Hotel Iraklio",
            "Ca's Curial", "Hai Au", "Hoang Ngoc Resort", "Bamboo Village", "Nitya Resort", "Neelams the Grand",
            "Don Hill Beach Resort"};
        for (int i = 0; i < 20; i++) {
            proposalsManager.addHotel(generateHotel(namesOfHotels[i]));
        }
    }

    public void clearDatabase() {
        databaseManager.cleanDatabase();
    }

    public Tour generateTour() {
        String[] descriptionArr = {"cheap", "expensive", "near sea", "with monsters", "with oil", "hot", "cold", "with skies", "with guns"};
        Tour tour = new Tour();
        Random rand = new Random();
        try {
            tour.setDescription(descriptionArr[rand.nextInt(descriptionArr.length)] + "," + descriptionArr[rand.nextInt(descriptionArr.length)]);
            tour.setCost(rand.nextInt(10000) + 1000);
            int cityCount = cityDao.getAll(City.class).size();
            tour.setTargetCityId(rand.nextInt(cityCount) + 1);
            City city = cityDao.getById(City.class, tour.getTargetCityId());
            tour.setName(city.getName());
            tour.setDateStart(new Date(rand.nextLong())); //блять артем
            tour.setDateEnd(new Date(rand.nextLong()));
            return tour;
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("В базе нет городов, не к чему привязать Tour", e);
        }
    }

    public Ticket generateTicket() {
        Random rand = new Random();
        int cityCount = cityDao.getAll(City.class).size();
        try {
            Ticket ticket = new Ticket(rand.nextInt(cityCount) + 1);
            ticket.setCost(rand.nextInt(3000) + 300);
            City city = cityDao.getById(City.class, ticket.getTargetCityId());
            ticket.setName(city.getName());
            return ticket;
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("В базе нет городов, не к чему привязать Ticket", e);
        }
    }

    public Hotel generateHotel(String name) {
        String[] descriptionArr = {"one star", "two stars", "three stars", "four stars", "five stars", "with swimming pool", "near sea", "with Russians",
            "big houses", "with eat", "all inclusive"};

        int cityCount = cityDao.getAll(City.class).size();
        Random rand = new Random();
        Hotel hotel = new Hotel();
        try {
            hotel.setCityId(rand.nextInt(cityCount) + 1);
            City city = cityDao.getById(City.class, hotel.getCityId());
            hotel.setCityName(city.getName());
            hotel.setName(name);
            hotel.setCost(rand.nextInt(1000) + 100);
            hotel.setDescription(descriptionArr[rand.nextInt(descriptionArr.length)] + "," + descriptionArr[rand.nextInt(descriptionArr.length)]);
            return hotel;
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("В базе нет городов, не к чему привязать Hotel", e);
        }
    }

    private void createPropertyType(String name, int minValue, int maxValue, Boolean isDependingMonth, Boolean isBetterWhenLess) {
        Interval interval = new Interval(minValue, maxValue);
        PropertyType propertyType = new PropertyType(name, interval, isDependingMonth, isBetterWhenLess);
        propertyTypeDao.add(propertyType);
    }
}
