package com.globerry.project.service.interfaces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.globerry.project.domain.Tag;

public class IOptionBlock
{
    private List<IBlockItem> items = new ArrayList<IBlockItem>();
    private IBlockItem selected;

    public List<IBlockItem> getBlockItems()
    {
	if (items.size() == 0)
	    tagsLoad();
	return items;
    }
    protected void addBlockItem(IBlockItem blockItem){
	items.add(blockItem);
	if (items.size() == 1)
	    selected = blockItem;
    }

    public Tag getSelected()
    {
	if (items.size() == 0)
	    tagsLoad();
	return selected.getTag();
    }

    public void setSelected(Tag selected)
    {
	if (items.size() == 0)
	    tagsLoad();
	Iterator<IBlockItem> it = items.iterator();
	while (it.hasNext()){
	    IBlockItem iBlockItem = it.next();
	    if (iBlockItem.getTag().equals(selected)){
		this.selected = iBlockItem;
		break;
	    }
	}
    }
    protected void tagsLoad(){
    }
}
