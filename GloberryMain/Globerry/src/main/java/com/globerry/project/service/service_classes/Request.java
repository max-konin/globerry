/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.service_classes;

import com.globerry.project.service.gui.IGuiComponent;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс, на который мапается на запрос от клиента.
 * @author Ed
 */
public class Request {
    private int id;
    private Object value;
    
    /**
     * Устанавливает id запроса.
     * При установке id в поле value устанавливается компонент, который сооветствует id. Он берется из {@link GuiMap}.
     * @param id идентификатор входящего элемента.
     */
    void setId(int id) {
        this.id = id;
        value = GuiMap.getElementById(id);
        if(value == null)
            throw new IllegalArgumentException("There is no element with such id, id is " + id);
    }
    
    
    @Override
    public String toString() {
        return getId() + " " + getValue();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param val the value to set
     */
    public void setValue(Object val) throws IllegalArgumentException {
        LinkedHashMap<String, Object> lhm = (LinkedHashMap<String, Object>) val;
        java.lang.reflect.Method method;
        Class<?> clazz = null;
        try {
            for (String key : lhm.keySet()) {
                String methodName = "set" + Character.toUpperCase(key.charAt(0)) + key.substring(1);
                clazz = lhm.get(key).getClass();
                method = this.value.getClass().getDeclaredMethod(methodName, clazz);
                method.invoke(this.value, lhm.get(key));
            }
        } 
        catch (SecurityException e) {
            throw new IllegalArgumentException("Illegal input data, scurity exception during invokation");
        }
        catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(String.format("Method with such name doesn't exist. Value class %s, "
                    + "input param class %s, current value %s, id %s", value.getClass(), clazz, value, id));
        }
        catch (IllegalAccessException e) {
            throw new IllegalArgumentException(String.format("Illegal input data, illegal access exception during "
                    + "invokation", e.getMessage()));
        }
        catch (InvocationTargetException e) {
            throw new IllegalArgumentException(String.format("Illegal input data, invocation exception exception during "
                    + "invocation", e.getMessage()));
            
        }
    }
        
    
}
