/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.domain;

import java.io.Serializable;

/**
 *
 * @author signal
 */
public interface Cachable<T> extends Serializable {
	
	public String toJSON();
	
	public T fromJSON(String json);
	
}
