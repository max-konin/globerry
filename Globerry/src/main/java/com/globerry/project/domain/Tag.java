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
    @Override
    public boolean equals(Object obj)
    {
	if(obj == null) return false;
	if(!(obj instanceof Tag)) return false;
	Tag tag = (Tag) obj;
	if(!((this.name == null && tag.getName() == null) || this.name.equals(tag.getName()))) return false; 
	if(!((this.img == null && tag.getImg() == null) || this.img.equals(tag.getImg()))) return false;
	if(!((this.tagsType == null && tag.getTagsType() == null) || this.tagsType.equals(tag.getTagsType()))) return false;
	return true;
    }
    @Override
    public int hashCode()
    {
	int result = 13;
	result = 3*result + (name == null ? 0 : name.hashCode());
	result = 3*result + (img == null ? 0 : img.hashCode());
	result = 3*result + (tagsType == null ? 0 : tagsType.hashCode());
	return result;
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
    //TODO Artem comment code with bags, KOSTIL
    public Set<City> getCityList()
    {
	// TODO Auto-generated method stub
	return null;
    }
    public void setCityList(Set<City> cityList)
    {
	// TODO Auto-generated method stub
	
    }
    public String toString()
    {
        return String.format("id: %d; name: %s;\n", id, name);
    }
}
