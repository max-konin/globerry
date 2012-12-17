/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.dao;

import com.globerry.project.domain.Month;
import com.globerry.project.domain.Tag;
import com.globerry.project.service.StringManager;
import com.globerry.project.service.gui.ISlider;
import com.globerry.project.service.service_classes.IApplicationContext;

import org.springframework.stereotype.Component;

/**
 * Фабрика создания запросов
 *
 * @author max
 */
@Component
public class QueryFactory {

    public String createCityRequest(IApplicationContext appContext) {

        StringBuilder query = new StringBuilder("select distinct city from City city ");
        StringBuilder joinClause = new StringBuilder();
        StringBuilder whereClause = new StringBuilder();
        appendTagCondition(appContext, joinClause, whereClause);
        appendSecurityCondition(whereClause, appContext);
        appendSexCondition(whereClause, appContext);
        appendAlcoholCondition(whereClause, appContext);
        appendFoodCostCondition(whereClause, appContext);
        appendTemperatureCondition(appContext, joinClause, whereClause);
        appendMoodCondition(appContext, joinClause, whereClause);
        appendLivingCostCondition(appContext, joinClause, whereClause);
        appendVisaCondition(appContext, whereClause);
        appendLanguageCondition(appContext, whereClause);
        return query.append(joinClause).append(whereClause).toString();
    }

    private void appendSecurityCondition(StringBuilder whereClause, IApplicationContext appContext) {
        final int rightValue = appContext.getSlidersByName("security").getRightValue();
        final int leftValue = appContext.getSlidersByName("security").getLeftValue();
        whereClause.append(" and city.security <=");
        whereClause.append(rightValue);
        whereClause.append(" and city.security >=");
        whereClause.append(leftValue);
    }

    private void appendSexCondition(final StringBuilder whereClause, IApplicationContext appContext) {
        final int rightValue = appContext.getSlidersByName("sex").getRightValue();
        final int leftValue = appContext.getSlidersByName("sex").getLeftValue();
        whereClause.append(" and city.sex <=");
        whereClause.append(rightValue);
        whereClause.append(" and city.sex >=");
        whereClause.append(leftValue);
    }

    private void appendAlcoholCondition(final StringBuilder whereClause, IApplicationContext appContext) {
        final int rightAlcoholValue = appContext.getSlidersByName("alcohol").getRightValue();
        final int leftAlcoholValue = appContext.getSlidersByName("alcohol").getLeftValue();
        whereClause.append(" and (city.alcoCost.left <=");
        whereClause.append(rightAlcoholValue);
        whereClause.append(" and city.alcoCost.left >=");
        whereClause.append(leftAlcoholValue);
        whereClause.append(" or  city.alcoCost.right >=");
        whereClause.append(leftAlcoholValue);
        whereClause.append(" and city.alcoCost.right <=");
        whereClause.append(rightAlcoholValue);
        whereClause.append(")");
    }

    private void appendFoodCostCondition(final StringBuilder whereClause, IApplicationContext appContext) {
        final int rightValue = appContext.getSlidersByName("cost").getRightValue();
        final int leftValue = appContext.getSlidersByName("cost").getLeftValue();
        whereClause.append(" and (city.foodCost.left <=");
        whereClause.append(rightValue);
        whereClause.append(" and city.foodCost.left >=");
        whereClause.append(leftValue);
        whereClause.append(" or  city.foodCost.right >=");
        whereClause.append(leftValue);
        whereClause.append(" and city.foodCost.right <=");
        whereClause.append(rightValue + ")");
    }

    private void appendTemperatureCondition(IApplicationContext appContext, StringBuilder joinClause, StringBuilder whereClause) {
        final String currentMonth = Month.values()[appContext.getWhenTag().getValue()].toString();
        final int rightValue = appContext.getSlidersByName("temperature").getRightValue();
        final int leftValue = appContext.getSlidersByName("temperature").getLeftValue();
        joinClause.append("left join fetch city.temperature temperature ");
        final class TempQueryBuilder {

            public void buildQuery(StringBuilder whereClause, String currentMonth, int leftValue, int rightValue) {
                whereClause.append(" and (temperature.");
                whereClause.append(currentMonth);
                whereClause.append("Value.left <= ");
                whereClause.append(rightValue);
                whereClause.append(" and temperature.");
                whereClause.append(currentMonth);
                whereClause.append("Value.left >= ");
                whereClause.append(leftValue);
                whereClause.append(" or  temperature.");
                whereClause.append(currentMonth);
                whereClause.append("Value.right >= ");
                whereClause.append(leftValue);
                whereClause.append(" and temperature.");
                whereClause.append(currentMonth);
                whereClause.append("Value.right <= ");
                whereClause.append(rightValue);
                whereClause.append(")");
            }
        };
        int rightQueryValue;
        int leftQueryValue;
        if (appContext.getWhatTag().getValue() == 5 && (leftValue < 15)) { //Tag sunbathe condition
            if (rightValue < 15) {
                leftQueryValue = 15;
                rightQueryValue = 20;
            } else {
                leftQueryValue = 15;
                rightQueryValue = rightValue;
            }
        } else {
            leftQueryValue = leftValue;
            rightQueryValue = rightValue;
        }
        new TempQueryBuilder().buildQuery(whereClause, currentMonth, leftQueryValue, rightQueryValue);
    }

    private void appendMoodCondition(IApplicationContext appContext, StringBuilder joinClause, StringBuilder whereClause) {
        joinClause.append("left join fetch  city.mood mood ");
        final String currentMonth = Month.values()[appContext.getWhenTag().getValue()].toString();
        final int rightValue = appContext.getSlidersByName("mood").getRightValue();
        final int leftValue = appContext.getSlidersByName("mood").getLeftValue();
        whereClause.append(" and (mood.");
        whereClause.append(currentMonth);
        whereClause.append("Value.left <= ");
        whereClause.append(rightValue);
        whereClause.append(" and mood.");
        whereClause.append(currentMonth);
        whereClause.append("Value.left >= ");
        whereClause.append(leftValue);
        whereClause.append(" or  mood.");
        whereClause.append(currentMonth);
        whereClause.append("Value.right >= ");
        whereClause.append(leftValue);
        whereClause.append(" and mood.");
        whereClause.append(currentMonth);
        whereClause.append("Value.right <= ");
        whereClause.append(rightValue);
        whereClause.append(")");
    }

    private void appendLivingCostCondition(IApplicationContext appContext, StringBuilder joinClause, StringBuilder whereClause) {
        final String currentMonth = Month.values()[appContext.getWhenTag().getValue()].toString();
        final int rightValue = appContext.getSlidersByName("livingCost").getRightValue();
        final int leftValue = appContext.getSlidersByName("livingCost").getLeftValue();
        joinClause.append("left join fetch city.livingCost livingCost ");
        whereClause.append(" and (livingCost.");
        whereClause.append(currentMonth);
        whereClause.append("Value.left <= ");

        whereClause.append(rightValue);
        whereClause.append(" and livingCost.");
        whereClause.append(currentMonth);
        whereClause.append("Value.left >= ");
        
        whereClause.append(leftValue);
        whereClause.append(" or  livingCost.");
        whereClause.append(currentMonth);
        whereClause.append("Value.right >= ");
        whereClause.append(leftValue);
        whereClause.append(" and livingCost.");
        whereClause.append(currentMonth);
        whereClause.append("Value.right <= ");
        whereClause.append(rightValue);
        whereClause.append(")");
    }

    private void appendVisaCondition(IApplicationContext appContext, StringBuilder whereClause) {
        if (appContext.getVisa().isChecked()) {
            whereClause.append(" and city.visa = 0"); //у нас это параметр называется без визы, если он не отмечен то возращаются все города, если же нет, то только те, в которые въезд безвизовый
        }
    }

    private void appendLanguageCondition(IApplicationContext appContext, StringBuilder whereClause) {
        if (appContext.getRusLanguage().isChecked()) {
            whereClause.append(" and city.isRussian = 1");
        }
    }

    private void appendTagCondition(IApplicationContext appContext, StringBuilder joinClause, StringBuilder whereClause) {
        joinClause.append("inner join city.tagList t1 ");
        joinClause.append("inner join city.tagList t2 ");
        whereClause.append("where t1.id = ");
        whereClause.append(appContext.getWhatTag().getValue());

        if (appContext.getWhoTag().getValue() != 1) {
            whereClause.append(" and t2.id = ");
            whereClause.append(appContext.getWhoTag().getValue());
        }
    }
}
