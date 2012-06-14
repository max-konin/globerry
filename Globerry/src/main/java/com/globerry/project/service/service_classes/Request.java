/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.service_classes;

/**
 * Класс, на который мапается запрос от клиента.
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
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }
        
    
}
