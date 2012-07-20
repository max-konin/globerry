/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Интервал
 * @author max
 */
@Embeddable
public class Interval implements Serializable
{
    private int left;
    private int right;
    
    public Interval()
    {
        
    }
    
    public Interval(int left, int right)
    {
        this.left = left;
        this.right = right;
    }
    /**
     * Проверят, является ли данный интервал подинтервалом interval
     * @param interval 
     * @return true если this подинтервал interval
     */
    public boolean isSubintrvalOf(Interval interval)
    {
        return ((left >= interval.getLeft())&& (right <= interval.getRight()));
    }
    
    /**
     * Проверят, является ли данный интервал расширением interval
     * @param interval
     * @return true if interval is subinterval of this
     */
    public boolean isExtendsOf(Interval interval)
    {
            return ((left >= interval.getLeft())&& (right <= interval.getRight()));
    }
    /**
     * @return the left
     */
    public int getLeft()
    {
        return left;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(int left)
    {
        this.left = left;
    }

    /**
     * @return the right
     */
    public int getRight()
    {
        return right;
    }

    /**
     * @param right the right to set
     */
    public void setRight(int right)
    {
        this.right = right;
    }
}
