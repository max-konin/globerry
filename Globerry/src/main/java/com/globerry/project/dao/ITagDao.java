package com.globerry.project.dao;


import java.util.List;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Tag;

/**
 * Interface tag
 * @author Artem
 *
 */
public interface ITagDao
{
    public List<Tag> getTagList();
    public void addTag(Tag tag) throws MySqlException;
    public void removeTag(Tag tag);
    public void removeTag(int id);
    public void updateTag(Tag newTag);
    public void updateTag(Tag oldTag, Tag newTag);
}
