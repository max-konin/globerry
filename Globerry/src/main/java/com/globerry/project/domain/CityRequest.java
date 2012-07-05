/**
 * 
 */
package com.globerry.project.domain;

import java.util.List;

/**
 * @author Max
 *
 */
public class CityRequest implements ICityRequest
{
    private List<Tag> tags;
    private final String queryBase = "select distinct city from City city";
    
    public CityRequest(List<Tag> tagList)
    {
	tags = tagList;
    }
    
    public List<Tag> getTags()
    {
	return tags;
    }
    public void setTags(List<Tag> tags)
    {
	this.tags = tags;
    }
    public String getHQLQuery()
    {
	if(tags!=null)
	{
	    String joinClause = "";
	    String whereClause = "";
	    int i = 1;
	    for (Tag tag : tags)
	    {
		joinClause += " inner join city.tagList t" + i;
		if (whereClause == "")
		    whereClause = " where t" + i + ".id=" + tag.getId();
		else
		    whereClause += " and t" + i + ".id=" + tag.getId();
		i++;
	    }
	    return queryBase + joinClause + whereClause;
	}
	else
	    return queryBase;
    }
}
