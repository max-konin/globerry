/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.gui;

/**
 * Класс, представляющий компонент типа Slider. Имеет значение слева и справа, а также минимальное и максимальное
 * значение.
 * @author Ed
 */
public class Slider implements ISlider {

    private int id;
    private float leftValue, rightValue, maxValue, minValue;
    
    /**
     * Создает новый компонент типа {@link Slider} с заданными значениями.
     * <p>
     * Note: Если leftValue окажется больше rightValue, leftValue окажется меньше minValue или rightValue больше maxValue,
     * то меньшее из значений будет принято за minValue, большее за maxValue и т.д.
     * @param id уникальный идентификатор элемента.
     * @param leftValue значение ползунка слева, которое будет задано.
     * @param rightValue значение ползунка справа, которое будет задано.
     * @param maxValue минимальное значение ползунков для данного экземпляра.
     * @param minValue максимальное значение ползунков для данного экземпляра.
     * 
     */
    public Slider(int id, float leftValue, float rightValue, float minValue, float maxValue){
        this.id = id;
        this.leftValue = Math.min(leftValue, rightValue);
        this.rightValue = Math.max(leftValue, rightValue);
        this.minValue = Math.min(this.leftValue, minValue);
        this.maxValue = Math.max(this.rightValue, maxValue);    
    }
    
    /**
     * Создает новый компонент типа {@link Slider} с заданными значениями minValue и maxValue. Текущие значения ползунков
     * устанавливаются на уровне minValue и maxValue.
     * @param id уникальный идентификатор элемента.
     * @param minValue
     * @param maxValue 
     */
    public Slider(int id, float minValue, float maxValue) {
        this(id, minValue, maxValue, minValue, maxValue);
    }
    
    @Override
    public int getId() {
        return id;
    }

    
    @Override
    public float getLeftValue() {
        return leftValue;
    }

    @Override
    public void setLeftValue(float leftValue) {
        if(leftValue < minValue)
            throw new IllegalArgumentException("Left value should be upper or equal minValue");
        this.leftValue = leftValue;
    }

    @Override
    public float getRightValue() {
        return rightValue;
    }

    @Override
    public void setRightValue(float rightValue) {
        if(rightValue > maxValue)
            throw new IllegalArgumentException("Right value should be lower or equal maxValue");
        this.rightValue = rightValue;
    }

    @Override
    public void сopyValues(IGuiComponent component) {
        ISlider slider = (ISlider) component;
        this.setLeftValue(slider.getLeftValue());
        this.setRightValue(slider.getRightValue());
    }
    
    @Override
    public String toString() {
        
        return "Slider, left value: " + leftValue + " right value:" + rightValue;
    }
    public float getMinValue() {
        return minValue;
    }
    public float getMaxValue() {
        return maxValue;
    }
    
}
