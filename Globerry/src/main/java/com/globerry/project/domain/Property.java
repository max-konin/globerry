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
import javax.persistence.Transient;

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
public class Property
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
    @Transient
    private Integer hashCode = null;
    
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
    @Override
    public boolean equals(Object obj)
    {
	if(obj == null) return false;
	if(!(obj instanceof Property)) return false;
	Property property = (Property) obj;
	
	/*if(this.propertyType == null ^ property.getPropertyType() == null) return false;
	if(!((this.propertyType == null && property.getPropertyType() == null) || this.propertyType.equals(property.getPropertyType()))) return false;*/
	
	if(!(value == property.getValue())) return false;
	return true;
    }
    @Override
    public int hashCode()
    {
	if(hashCode != null) return hashCode;
	int result = 10;
	result = 3 * result + (propertyType == null ? 0 : propertyType.hashCode());
	result = 3 * result + Float.floatToIntBits(value);
	return result;
	
    }

}
