/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.domain;

import javax.persistence.*;
import org.hibernate.annotations.NaturalId;


/**
 *
 * @author Max
 */
@Entity
@Table(name="Country")
public class Country
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NaturalId
    private String name;
    
    @Embedded
    private OperatorMapping operatorMapping;
    
    public Country()
    {
        operatorMapping = new OperatorMapping();
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
     * @return the operatorMapping
     */
    public OperatorMapping getOperatorMapping()
    {
        return operatorMapping;
    }

    /**
     * @param operatorMapping the operatorMapping to set
     */
    public void setOperatorMapping(OperatorMapping operatorMapping)
    {
        this.operatorMapping = operatorMapping;
    }
}
