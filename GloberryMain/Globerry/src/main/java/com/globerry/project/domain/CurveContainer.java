/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Max
 */
@Document(collection = "curves")
public class CurveContainer {
    
    
    @Id
    private String key;
    
    private Collection<Curve> curves;
    
    public CurveContainer()
    {
        curves = new ArrayList<Curve>();
    }
    
    public CurveContainer(String key, Collection<Curve> curves)
    {
        this.curves = curves;
        this.key = key;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the curves
     */
    public Collection<Curve> getCurves() {
        return curves;
    }

    /**
     * @param curves the curves to set
     */
    public void setCurves(Collection<Curve> curves) {
        this.curves = curves;
    }
}
