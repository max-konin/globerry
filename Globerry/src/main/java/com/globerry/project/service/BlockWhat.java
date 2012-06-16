package com.globerry.project.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globerry.project.dao.ITagDao;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.TagsType;
import com.globerry.project.service.interfaces.IOptionBlock;

@Service
@Scope("session")
public class BlockWhat extends IOptionBlock
{
    @Autowired
    ITagDao tagDao;
    @Override
    protected void tagsLoad(){
	Set<Tag> tagList = tagDao.getTagList();
	Iterator<Tag> tagIterator = tagList.iterator();
	while (tagIterator.hasNext()){
	    Tag tag = tagIterator.next();
	    if (tag.getTagsType() == TagsType.WHERE)
		this.addBlockItem(new BlockItem(tag));
	}
    }
}