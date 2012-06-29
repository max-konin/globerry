package com.globerry.project.service.gui;

/**
 * Интерфейс, который реализуют все графические элемнты в приложени. Необходим для унифицированного доступа к
 * компонентам.
 * @author Ed
 */
public interface IGuiComponent {
    
    /**
     * Возвращает уникальный идентификатор элемента.
     * @return Идентификатор элемент.
     */
    int getId();
    
    /**
     * Устанавливает значения у компонента, такие же как и у переданного.
     * После выполнения этого метода значения у текущего и переданного
     * копонентов должны совпадать либо должно сработать исключение.
     * @param component компонент, значентия которого необходимо скопировать.
     */
    void setValues(IGuiComponent component) throws IllegalArgumentException;
    
}
