package com.globerry.project.domain;

import java.util.List;

public class Option
{
    private Temperature temperature;
    private int population;
    private int funFactor;
    private int security;
    private int livingCost;
    private int foodCost;
    private int alchogolCost;
    private int touristsNumber;
    private boolean visa;
    private int englishSpeaking;
    private int attractionsNumber;
    private List<Tag> tags;
    public Temperature getTemperature()
    {
	return temperature;
    }
    public void setTemperature(Temperature temperature)
    {
	this.temperature = temperature;
    }
    public int getPopulation()
    {
	return population;
    }
    public void setPopulation(int population)
    {
	this.population = population;
    }
    public int getFunFactor()
    {
	return funFactor;
    }
    public void setFunFactor(int funFactor)
    {
	this.funFactor = funFactor;
    }
    public int getSecurity()
    {
	return security;
    }
    public void setSecurity(int security)
    {
	this.security = security;
    }
    public int getLivingCost()
    {
	return livingCost;
    }
    public void setLivingCost(int livingCost)
    {
	this.livingCost = livingCost;
    }
    public int getAlchogolCost()
    {
	return alchogolCost;
    }
    public void setAlchogolCost(int alchogolCost)
    {
	this.alchogolCost = alchogolCost;
    }
    public int getFoodCost()
    {
	return foodCost;
    }
    public void setFoodCost(int foodCost)
    {
	this.foodCost = foodCost;
    }
    public int getTouristsNumber()
    {
	return touristsNumber;
    }
    public void setTouristsNumber(int touristsNumber)
    {
	this.touristsNumber = touristsNumber;
    }
    public boolean isVisa()
    {
	return visa;
    }
    public void setVisa(boolean visa)
    {
	this.visa = visa;
    }
    public int getEnglishSpeaking()
    {
	return englishSpeaking;
    }
    public void setEnglishSpeaking(int englishSpeaking)
    {
	this.englishSpeaking = englishSpeaking;
    }
    public int getAttractionsNumber()
    {
	return attractionsNumber;
    }
    public void setAttractionsNumber(int attractionsNumber)
    {
	this.attractionsNumber = attractionsNumber;
    }
    public List<Tag> getTags()
    {
	return tags;
    }
    public void setTags(List<Tag> tags)
    {
	this.tags = tags;
    } 
}
