/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.gui;

import com.globerry.project.domain.PropertyType;
import com.globerry.project.utils.PropertySegment;

/**
 * Класс, представляющий компонент типа Slider. Имеет значение слева и справа, а также минимальное и максимальное
 * значение.
 * @author Ed
 */
public class Slider implements ISlider {

    private int id;
    private PropertySegment state;
    
    /**
     * Создает новый компонент типа {@link Slider} с заданными значениями.
     * <p>
     * Note: Если leftValue окажется больше rightValue, leftValue окажется меньше minValue или rightValue больше maxValue,
     * то меньшее из значений будет принято за minValue, большее за maxValue и т.д.
     * @param id уникальный идентификатор элемента.
     * @param leftValue значение ползунка слева, которое будет задано.
     * @param rightValue значение ползунка справа, которое будет задано.
     * @param propertyType тип слайдера из бд
     */
    public Slider(int id, int leftValue, int rightValue, PropertyType propertyType){
        this.id = id;
        state = new PropertySegment(propertyType, Math.min(leftValue, rightValue), Math.max(leftValue, rightValue));        
    }
    
    /**
     * Создает новый компонент типа {@link Slider} с заданным propertyType. Текущие значения ползунков
     * устанавливаются на уровне minValue и maxValue.
     * @param id уникальный идентификатор элемента.
     *  @param propertyType тип слайдера из бд
     */
    public Slider(int id, PropertyType propertyType) {
        this(id, propertyType.getMinValue(),  propertyType.getMaxValue(), propertyType);
    }    
    /**
     * Конструктор копирования
     * @param slider 
     */ 
    public Slider(ISlider slider)
    {
        id = slider.getId();
        state = new PropertySegment(slider.getPropertyType(), slider.getLeftValue(), slider.getRightValue());
    }
    
    @Override
    public IGuiComponent clone()
    {
        return new Slider(this);
    }
    @Override
    public int getId() {
        return id;
    }

    
    @Override
    public int getLeftValue() {
        return getState().getLeftValue();
    }

    @Override
    public void setLeftValue(int leftValue) {
        if(leftValue <  getState().getPropertyType().getMinValue())
            throw new IllegalArgumentException("Left value should be upper or equal minValue");
        getState().setLeftValue(leftValue);
    }

    @Override
    public int getRightValue() {
        return getState().getRightValue();
    }

    @Override
    public void setRightValue(int rightValue) {
        if(rightValue > getState().getPropertyType().getMaxValue())
            throw new IllegalArgumentException("Right value should be lower or equal maxValue");
        getState().setRightValue(rightValue);
    }

    @Override
    public void setValues(IGuiComponent component) {
        ISlider slider = (ISlider) component;
        this.setLeftValue(slider.getLeftValue());
        this.setRightValue(slider.getRightValue());
    }
    
    @Override
    public String toString() {
        
        return "Slider, left value: " + getState().getLeftValue() + " right value:" + getState().getRightValue();
    }
    public float getMinValue() {
        return getState().getPropertyType().getMinValue();
    }
    public float getMaxValue() {
        return getState().getPropertyType().getMaxValue();
    }

    /**
     * @return the propertyType
     */
    public PropertyType getPropertyType() {
        return getState().getPropertyType();
    }

    /**
     * @return the state
     */
    public PropertySegment getState() {
        return state;
    }
    
}
