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
    @Override
    public boolean equals(Object obj)
    {
	if(obj == null) return false;
	if(!(obj instanceof CityRequest)) return false;
	CityRequest cityRequest = (CityRequest) obj;
	
	if(this.tags == null ^ cityRequest.getTags() == null) return false;
	if(!((this.tags == null && cityRequest.getTags() == null) || this.tags.equals(cityRequest.getTags()))) return false;
	
	if(this.queryBase == null ^ cityRequest.getHQLQuery() == null) return false;
	if(!((this.queryBase == null && cityRequest.getHQLQuery() == null) || this.queryBase.equals(cityRequest.getHQLQuery()))) return false;
	return true;
    }
    @Override
    public int hashCode()
    {
	int result = 17;
	result = 3 * result + (queryBase == null ? 0 : queryBase.hashCode());
	for(Tag elem: tags)
	{
	    result = result + elem.hashCode();
	}
	return result;
    }
}
