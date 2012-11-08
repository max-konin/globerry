package com.globerry.project.domain;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.*;
import javax.vecmath.Point2d;
import org.hibernate.annotations.Index;

/**
 * 
 * @author max
 */
@Entity
@Table(name = "City")
@org.hibernate.annotations.Table(appliesTo = "City", indexes =
{ @Index(name = "idx", columnNames =
{ "security", "sex", "food_value_left", "food_value_right", "alco_value_left", "alco_value_right", "visa" }) })
public class City implements Serializable
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="latitude",  column = @Column(name="latitude") ),
            @AttributeOverride(name="name",  column = @Column(name="name") ),
            @AttributeOverride(name="ru_name",  column = @Column(name="ru_name") ),
            @AttributeOverride(name="longitude", column = @Column(name="longitude") )
    } )
    private CityShort cityShort;
    @Column
    private float area;
    @Column
    private int population;
    @Column
    private boolean isValid;
    @Column
    private String message;
    @Transient
    private Integer hashCode = null;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="left",  column = @Column(name="food_value_left") ),
            @AttributeOverride(name="right", column = @Column(name="food_value_right") )
    } )
    private Interval foodCost = new Interval();
	
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="left",  column = @Column(name="alco_value_left") ),
            @AttributeOverride(name="right", column = @Column(name="alco_value_right") )
    } )
    private Interval alcoCost = new Interval();
    @Column      
    private int security;
    @Column
    private int sex;
    @Column
    private boolean visa = true;
    @Column
    private boolean isRussian = false;
    @OneToOne(cascade = CascadeType.ALL)
    private Temperature temperature;
    @OneToOne(cascade = CascadeType.ALL)
    private Mood mood;
    @OneToOne(cascade = CascadeType.ALL)
    private LivingCost livingCost;
    @ManyToMany(fetch = FetchType.EAGER, cascade =
    {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, targetEntity = Tag.class)
    private Set<Tag> tagList = new HashSet<Tag>();

    @Transient
    private double potential = 0;

    public City()
    {
    }

    /**
     * Инициализирует все notnull поля
     * 
     * @param name
     * @param area
     * @param latitude
     * @param longitude
     * @param population
     * @param foodCost
     * @param alcoCost
     * @param security
     * @param sex
     * @param visa
     * @param temperature
     * @param mood
     * @param livingCost
     * @param tagList
     */
    public City(String name, float area, float latitude, float longitude, int population, Interval foodCost, Interval alcoCost,
	    int security, int sex, boolean visa, boolean isRussian, Temperature temperature, Mood mood, LivingCost livingCost,
	    Collection<Tag> tagList)
    {
        this.cityShort = new CityShort();
	this.cityShort.setName(name);
	this.alcoCost = alcoCost;
	this.area = area;
	this.cityShort.setLatitude(latitude);
	this.cityShort.setLongitude(longitude);
	this.population = population;
	this.foodCost = foodCost;
	this.security = security;
	this.sex = sex;
	this.visa = visa;
	this.isRussian = isRussian;
	this.temperature = temperature;
	this.mood = mood;
	this.livingCost = livingCost;
	this.tagList = new HashSet(tagList);
    }
    public CityShort getCityShort()
    {
        cityShort.setId(id);
        return cityShort;
    }
    public void setCityShort(CityShort cityShort)
    {
        this.cityShort = cityShort;
    }
	
    public float getLatitude() {
		return cityShort.getLatitude();
	}

	public void setLatitude(float latitude) {
		this.cityShort.setLatitude(latitude);
	}

	public float getLongitude() {
		return cityShort.getLongitude();
	}

	public void setLongitude(float longitude) {
		this.cityShort.setLongitude(longitude);
	}

	public String getName() {
		return cityShort.getName();
	}

	public void setName(String name) {
		this.cityShort.setName(name);
	}

	public String getRu_name() {
		return cityShort.getRu_name();
	}

	public void setRu_name(String ru_name) {
		this.cityShort.setRu_name(ru_name);
	}

	public float getWeight() {
		return cityShort.getWeight();
	}

	public void setWeight(float weight) {
		this.cityShort.setWeight(weight);
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    /**
     * @return the area
     */
    public float getArea()
    {
	return area;
    }

    /**
     * @param area
     *            the area to set
     */
    public void setArea(float area)
    {
	this.area = area;
    }

    /**
     * @return the population
     */
    public int getPopulation()
    {
	return population;
    }

    /**
     * @param population
     *            the population to set
     */
    public void setPopulation(int population)
    {
	this.population = population;
    }

    /**
     * @return the isValid
     */
    public boolean isIsValid()
    {
	return isValid;
    }

    /**
     * @param isValid
     *            the isValid to set
     */
    public void setIsValid(boolean isValid)
    {
	this.isValid = isValid;
    }

    /**
     * @return the message
     */
    public String getMessage()
    {
	return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message)
    {
	this.message = message;
    }

    /**
     * @return the foodCost
     */
    public Interval getFoodCost()
    {
	return foodCost;
    }

    /**
     * @param foodCost
     *            the foodCost to set
     */
    public void setFoodCost(Interval foodCost)
    {
	this.foodCost = foodCost;
    }

    /**
     * @return the alcoCost
     */
    public Interval getAlcoCost()
    {
	return alcoCost;
    }

    /**
     * @param alcoCost
     *            the alcoCost to set
     */
    public void setAlcoCost(Interval alcoCost)
    {
	this.alcoCost = alcoCost;
    }

    /**
     * @return the security
     */
    public int getSecurity()
    {
	return security;
    }

    /**
     * @param security
     *            the security to set
     */
    public void setSecurity(int security)
    {
	this.security = security;
    }

    /**
     * @return the sex
     */
    public int getSex()
    {
	return sex;
    }

    /**
     * @param sex
     *            the sex to set
     */
    public void setSex(int sex)
    {
	this.sex = sex;
    }

    /**
     * @return the visa
     */
    public boolean isVisa()
    {
	return visa;
    }

    /**
     * @param visa
     *            the visa to set
     */
    public void setVisa(boolean visa)
    {
	this.visa = visa;
    }

    public boolean isRussian()
    {
	return isRussian;
    }

    public void setRussian(boolean isRussian)
    {
	this.isRussian = isRussian;
    }

    /**
     * @return the temperature
     */
    public Temperature getTemperature()
    {
	return temperature;
    }

    /**
     * @param temperature
     *            the temperature to set
     */
    public void setTemperature(Temperature temperature)
    {
	this.temperature = temperature;
    }

    /**
     * @return the mood
     */
    public Mood getMood()
    {
	return mood;
    }

    /**
     * @param mood
     *            the mood to set
     */
    public void setMood(Mood mood)
    {
	this.mood = mood;
    }

    /**
     * @return the livingCost
     */
    public LivingCost getLivingCost()
    {
	return livingCost;
    }

    /**
     * @param livingCost
     *            the livingCost to set
     */
    public void setLivingCost(LivingCost livingCost)
    {
	this.livingCost = livingCost;
    }

    /**
     * @return the tagList
     */
    public Set<Tag> getTagList()
    {
	return tagList;
    }

    /**
     * @param tagList
     *            the tagList to set
     */
    public void setTagList(Set<Tag> tagList)
    {
	this.tagList = tagList;
    }

    /**
     * @return the weight
     */
    @Override
    public String toString()
    {
	String str = this.getClass().getName() + ": \n";
	for (Field field : this.getClass().getDeclaredFields())
        {
            try
            {
                if (field.get(this) != null)
                {
                    str += "    " + field.getName() + " = " + field.get(this).toString() + ";\n";
                }
            }
            catch (IllegalArgumentException ex)
            {
                Logger.getLogger(City.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IllegalAccessException ex)
            {
                Logger.getLogger(City.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
	return str;
    }

    /**
     * @return the potential
     */
    public double getPotential()
    {
	return potential;
    }

    /**
     * @param potential
     *            the potential to set
     */
    public void setPotential(double potential)
    {
	this.potential = potential;
    }

    public void addPotential(double potential)
    {
	this.potential += potential;
    }
    /**
     * @return LatLng Point
     *            
     */
    public Point2d getCoord()
    {
	return new Point2d(cityShort.getLatitude(), cityShort.getLongitude());
    }
}
