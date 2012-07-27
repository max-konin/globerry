
package com.globerry.project.domain;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


/**
 *
 * @author max
 */
@Entity
@Table(name = "City")
@org.hibernate.annotations.Table(appliesTo = "City", indexes = { @Index(name="idx", columnNames = { 
                                                                                                    "security",
                                                                                                    "sex",
                                                                                                    "food_value_left",
                                                                                                    "food_value_right",
                                                                                                    "alco_value_left",
                                                                                                    "alco_value_right",
                                                                                                    "visa",
                                                                                                    })})
public class City
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(name = "name")   
    private String name;
    @Column(name = "ru_name")    
    private String ru_name;
    @Column   
    private float area;
    @Column    
    private int population;
    @Column   
    private float longitude;
    @Column    
    private float latitude;
    @Column 
    private boolean isValid;
    @Column
    private String message;
    @Transient
    private Integer hashCode = null;
    
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="left", column = @Column(name="food_value_left") ),
            @AttributeOverride(name="right", column = @Column(name="food_value_right") )
    } )
    
    private Interval foodCost = new Interval();
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="left", column = @Column(name="alco_value_left") ),
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
    
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToMany(
	    fetch = FetchType.LAZY,
	    cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
	    targetEntity = Tag.class
	    )
    private Set<Tag> tagList = new HashSet<Tag>();
    
    @Transient
    private float weight;
    
    public City(){}
    
    /**
     * Инициализирует все notnull поля
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
    public City(String name, 
                float area, 
                float latitude, 
                float longitude, 
                int population, 
                Interval foodCost, 
                Interval alcoCost,
                int security,    
                int sex,
                boolean visa,
                boolean isRussian,
                Temperature temperature,
                Mood mood,
                LivingCost livingCost,
                Collection<Tag> tagList)
    {
        this.name = name;
        this.alcoCost = alcoCost;
        this.area = area;
        this.latitude = latitude;
        this.longitude = longitude;
        this.population = population;
        this.foodCost = foodCost;
        this.security = security;
        this.sex = sex;
        this.visa = visa;
        this.setRussian(isRussian);
        this.temperature = temperature;
        this.mood = mood;
        this.livingCost = livingCost;
        this.tagList = new HashSet(tagList);
    }

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the ru_name
     */
    public String getRu_name()
    {
        return ru_name;
    }

    /**
     * @param ru_name the ru_name to set
     */
    public void setRu_name(String ru_name)
    {
        this.ru_name = ru_name;
    }

    /**
     * @return the area
     */
    public float getArea()
    {
        return area;
    }

    /**
     * @param area the area to set
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
     * @param population the population to set
     */
    public void setPopulation(int population)
    {
        this.population = population;
    }

    /**
     * @return the longitude
     */
    public float getLongitude()
    {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(float longitude)
    {
        this.longitude = longitude;
    }

    /**
     * @return the latitude
     */
    public float getLatitude()
    {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(float latitude)
    {
        this.latitude = latitude;
    }

    /**
     * @return the isValid
     */
    public boolean isIsValid()
    {
        return isValid;
    }

    /**
     * @param isValid the isValid to set
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
     * @param message the message to set
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
     * @param foodCost the foodCost to set
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
     * @param alcoCost the alcoCost to set
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
     * @param security the security to set
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
     * @param sex the sex to set
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
     * @param visa the visa to set
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
     * @param temperature the temperature to set
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
     * @param mood the mood to set
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
     * @param livingCost the livingCost to set
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
     * @param tagList the tagList to set
     */
    public void setTagList(Set<Tag> tagList)
    {
        this.tagList = tagList;
    }

    /**
     * @return the weight
     */
    public float getWeight()
    {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(float weight)
    {
        this.weight = weight;
    }
    
    public String toString()
    {
        String str = this.getClass().getName() + ": \n";
        for(Field field: this.getClass().getDeclaredFields())            
            try
            {
                if (field.get(this) != null)
                    str += "    " + field.getName() + " = " + field.get(this).toString() + ";\n";
            } catch (IllegalArgumentException ex)
            {
                Logger.getLogger(City.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex)
            {
                Logger.getLogger(City.class.getName()).log(Level.SEVERE, null, ex);
            }
        return str;
    }
}
