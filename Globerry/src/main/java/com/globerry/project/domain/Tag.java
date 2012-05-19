package com.globerry.project.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Tag class
 * ����� �������� � city �������� CityTag ManyToMany
 * @author Artem
 *
 */

@Entity
@Table(name = "Tag")
public class Tag
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(name = "name", unique=true)
    private String name;
    
    @Column(name = "img")
    private String img;
    
    @Column
    @Enumerated(EnumType.ORDINAL)
    private TagsType tagsType;
    
   
    public int getId()
    {
	return id;
    }
    public void setId(int id)
    {
	this.id = id;
    }
    public String getName()
    {
	return name;
    }
    public void setName(String name)
    {
	this.name = name;
    }
    public String getImg()
    {
	return img;
    }
    public void setImg(String img)
    {
	this.img = img;
    }
    public boolean equals(Tag tag)
    {
	if(this.getId() == tag.getId() &&
		(this.getImg() == null && tag.getImg() == null) ||
			(this.getImg() != null && tag.getImg() != null && this.getImg().equals(tag.getImg())) &&
		this.getName().equals(tag.getName()))
	    return true;
	else return false;
    }
    public TagsType getTagsType()
    {
	return tagsType;
    }
    @JsonIgnore
    public void setTagsType(TagsType tagsType)
    {
	this.tagsType = tagsType;
    }
    public void setTagsType(int number)
    {
	this.tagsType = TagsType.values()[number];
    }
}
