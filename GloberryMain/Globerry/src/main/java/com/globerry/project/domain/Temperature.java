/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.domain;

import com.globerry.project.domain.Month;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Set;
import javax.persistence.*;

/**
 *  Сущьность для температуры
 * @author max
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Temperature")
public class Temperature extends DependingMonthPropertyBase
{
    
}
