package com.globerry.project.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.mapping.Collection;
//import org.hibernate.mapping.List;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Max-NG
 *
 */
@Entity
@Table(name="Property")
public class Property implements IRelationsQualifier
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "value")
    private float value;
    
    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER )
    @JoinTable(name="PropertyPropertyType",
    joinColumns = @JoinColumn(name="property_id"),
    inverseJoinColumns = @JoinColumn(name="propertyType_id")
	    )
    private PropertyType propertyType;
    
    public int getId()
    {
	return id;
    }
    public void setId(int id)
    {
	this.id = id;
    }
    public float getValue()
    {
	return value;
    }
    public void setValue(float value)
    {
	this.value = value;
    }
    public PropertyType getPropertyType()
    {
	return propertyType;
    }
    public void setPropertyType(PropertyType propertyType)
    {
	this.propertyType = propertyType;
    }
    public String toString()
    {
        return String.format("id: %d; value: %f; Type: %s \n", id, value, propertyType.getName());
    }

}
