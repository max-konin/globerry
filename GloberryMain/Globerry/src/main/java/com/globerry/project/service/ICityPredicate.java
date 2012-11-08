package com.globerry.project.service;

import com.globerry.project.domain.CityShort;

public interface ICityPredicate
{
    public boolean compare(CityShort city1,CityShort city2,float zLevel);
}
