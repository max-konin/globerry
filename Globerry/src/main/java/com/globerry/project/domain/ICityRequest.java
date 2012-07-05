package com.globerry.project.domain;

import java.util.List;

public interface ICityRequest
{
    public String getHQLQuery();
    public List<Tag> getTags();
    public void setTags(List<Tag> tags);
}
