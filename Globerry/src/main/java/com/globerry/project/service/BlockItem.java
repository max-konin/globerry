package com.globerry.project.service;

import java.util.Observable;

import com.globerry.project.domain.Tag;
import com.globerry.project.service.interfaces.IBlockItem;

public class BlockItem extends Observable implements IBlockItem
{
    private Tag tag;
    private boolean isSelected = false;
    public BlockItem(Tag tag){
	this.tag = tag;
    }
    @Override
    public void onClick()
    {
	isSelected = !isSelected;
	super.notifyObservers(new EventUI(this));
	
    }
    public boolean isSelected(){
	return isSelected;
    }
    public Tag getTag(){
	return tag;
    }

}
