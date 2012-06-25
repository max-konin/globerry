package com.globerry.project.utils.dropdown_menu;

public interface IDropdownMenu
{
    DropdownMenuItem getRootElement();
    DropdownMenuItem getItem(String name);
    
    void addItem(DropdownMenuItem item, DropdownMenuItem parent) throws IllegalArgumentException, NullPointerException;
    void addItem(String path, DropdownMenuItem item) throws IllegalArgumentException, NullPointerException;
    
    void addItemToRoot(DropdownMenuItem item) throws IllegalArgumentException, NullPointerException;
    
    void removeItem(String name) throws IllegalArgumentException, NullPointerException;
    void removeItem(DropdownMenuItem item) throws IllegalArgumentException, NullPointerException;
    
    String getName();
    void setName(String name);
    
    String getId();
    void setId(String id);
    
}
