/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.domain;

import javax.persistence.*;

/**
 * Стоимость проживания
 * @author max
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("LivingCost")
public class LivingCost extends DependingMonthPropertyBase
{
    
}
