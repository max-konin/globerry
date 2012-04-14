package com.globerry.project.service.interfaces;

import java.util.ArrayList;
import java.util.List;

public class IOptionBlock
{
    private List<IBlockItem> items = new ArrayList<IBlockItem>();
    private IBlockItem selected;

    public List<IBlockItem> getBlockItems()
    {
	return items;
    }
    public void addBlockItem(IBlockItem blockItem){
	items.add(blockItem);
	if (items.size() == 1)
	    selected = blockItem;
    }

    public IBlockItem getSelected()
    {
	return selected;
    }

    public void setSelected(IBlockItem selected)
    {
	this.selected = selected;
    }
}
