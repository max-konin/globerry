package com.globerry.project.domain;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

@Entity
@Table
public class DependingMonthOptions
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    @Enumerated(EnumType.ORDINAL)
    private Month month;
    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER )
    @JoinTable(name="OptionsTypeDepending",
        joinColumns = @JoinColumn(name="DMonthOptions_id"),
        inverseJoinColumns = @JoinColumn(name="OptionsType_id")
    )
    private PropertyType optionsType;
    @Column
    private float val;
    public int getId()
    {
	return id;
    }
    public void setId(int id)
    {
	this.id = id;
    }
    public Month getMonth()
    {
	return month;
    }
    public void setMonth(Month month)
    {
	this.month = month;
    }
    public float getVal()
    {
	return val;
    }
    public void setVal(float val)
    {
	this.val = val;
    }
    public PropertyType getOptionsType()
    {
	return optionsType;
    }
    public void setOptionsType(PropertyType optionsType)
    {
	this.optionsType = optionsType;
    }
}
