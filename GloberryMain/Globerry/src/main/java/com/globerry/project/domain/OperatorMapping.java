/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;



/**
 *
 * @author Max
 * Maps our Country Entity with Countries Ids of our partners, for example ISC.
 */
@Embeddable
public class OperatorMapping
{
    @Column(nullable = true)
    private int ISCId;

    /**
     * @return the ISCId
     */
    public int getISCId()
    {
        return ISCId;
    }

    /**
     * @param ISCId the ISCId to set
     */
    public void setISCId(int ISCId)
    {
        this.ISCId = ISCId;
    }

}
