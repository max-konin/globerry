<%-- 
    Document   : home_new
    Created on : 18.06.2012, 12:47:05
    Author     : Ed
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Globerry <spring:message code="buildNumber"></spring:message></title>
        <link type="text/css" href="resources/lib/jquery-ui-1.8.21/css/ui-lightness/jquery-ui-1.8.21.custom.css" rel="stylesheet" />
        <link rel="stylesheet" href="resources/javascripts/CloudMade-Leaflet-538dfb4/dist/leaflet.css" />
        <script src="resources/javascripts/CloudMade-Leaflet-538dfb4/debug/leaflet-include.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
        <script src="resources/lib/jquery-ui-1.8.21/js/jquery-ui-1.8.21.custom.min.js" type="text/javascript"></script>
        <script src="resources/javascripts/bubbles.js"></script>
        <style>
            #form {
                z-index: 1;
                position: relative;
                border-width: 1px;
                padding: 1px;
                border-style: inset;
                border-color: gray;
                background-color: white;
                margin-left: auto;
                margin-right: auto;
                opacity: 0.7
            }
            #map {
                bottom : 0%;
                right: 0%;
                width: 100%;
                min-width : 900px;
                min-height : 600px;
                height : 100%; 
                position : absolute;
                margin: 0 auto;
                z-index : 0;
            }
            body {
                margin: 0;
                padding: 0;
            }
            td {
                padding: 10px;
            }
        </style>
    </head>
    <body>
        <table id="form" width="1000px">
            <tbody>
                <tr>
                    <td>
                        <spring:message code="label.who"></spring:message>
                        <select id="${who.getId()}" class="gui_element" guiId="${who.getId()}">
                            <c:forEach items="${who.getOptionAvaliable()}" var="value">
                                <option value="${value}">
                                    <spring:message code="label.who${value}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <spring:message code="label.what"></spring:message>
                        <select id="${what.getId()}" class="gui_element" guiId="${what.getId()}">
                            <c:forEach items="${what.getOptionAvaliable()}" var="value">
                                <option value="${value}">
                                    <spring:message code="label.what${value}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <spring:message code="label.when"></spring:message>
                        <select id="${when.getId()}" class="gui_element">
                            <c:forEach items="${when.getOptionAvaliable()}" var="value">
                                <option value="${value}">
                                    <spring:message code="label.m${value}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <spring:message code="label.temperature"></spring:message>
                        <div class="slider" id="${temperature.getId()}">
                            <input value="<fmt:formatNumber value="${temperature.getMinValue()}"
                                              minFractionDigits="0" maxFractionDigits="0"/>">
                            <input value="<fmt:formatNumber value="${temperature.getMaxValue()}"
                                              minFractionDigits="0" maxFractionDigits="0"/>">
                            <div></div>
                        </div>
                    </td>
                    <td>
                        <spring:message code="label.travel_time"></spring:message>
                        <div class="slider" id="${travelTime.getId()}">
                            <input value="<fmt:formatNumber value="${travelTime.getMinValue()}"
                                              minFractionDigits="0" maxFractionDigits="0"/>">
                            <input value="<fmt:formatNumber value="${travelTime.getMaxValue()}"
                                              minFractionDigits="0" maxFractionDigits="0"/>">
                            <div></div>
                        </div>
                    </td>
                    <td>
                        <spring:message code="label.living_cost"></spring:message>
                        <div class="slider" id="${livingCost.getId()}">
                            <input value="<fmt:formatNumber value="${livingCost.getMinValue()}"
                                              minFractionDigits="0" maxFractionDigits="0"/>">
                            <input value="<fmt:formatNumber value="${livingCost.getMaxValue()}"
                                              minFractionDigits="0" maxFractionDigits="0"/>">
                            <div></div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <spring:message code="label.food"></spring:message>
                        <div class="slider" id="${cost.getId()}">
                            <input value="<fmt:formatNumber value="${cost.getMinValue()}"
                                              minFractionDigits="0" maxFractionDigits="0"/>">
                            <input value="<fmt:formatNumber value="${cost.getMaxValue()}"
                                              minFractionDigits="0" maxFractionDigits="0"/>">
                            <div></div>
                        </div>
                    </td>
                    <td>
                        <spring:message code="label.alcohol"></spring:message>
                        <div class="slider" id="${alcohol.getId()}">
                            <input value="<fmt:formatNumber value="${alcohol.getMinValue()}"
                                              minFractionDigits="0" maxFractionDigits="0"/>">
                            <input value="<fmt:formatNumber value="${alcohol.getMaxValue()}"
                                              minFractionDigits="0" maxFractionDigits="0"/>">
                            <div></div>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
        <div id ="map"/>
    </body>
    <script>
        /**
         * Стиль заливки кругов.
         **/
        var circleOptions = {
            color: 'url(#grad1)',
            opacity: 0,
            fillOpacity : 0.8
        }
    </script>
    <!--[if lte IE 9]>
    <script>
        /**Специально для эксплореров.**/
        circleOptions = {color: 'orange', opacity: 0, fillOpacity: 0.2}
    </script>
<   ![endif]-->
    <script>
        var path = '<%= request.getContextPath() %>';
        var initCities = [
        <c:forEach items="${cities}" var="city">
                {"id":${city.getId()},"name":"${city.getName()}","ru_name":"${city.getRu_name()}","area":${city.getArea()},
                    "population":${city.getPopulation()},"longitude":${city.getLongitude()},
                    "latitude":${city.getLatitude()},"isValid":${city.getIsValid()},"message":"${city.getMessage()}",
                    "weight":${city.getWeight()}
                },
        </c:forEach>
        ];
        var bubbles;
        $(document).ready(function(){
            $('select.gui_element').change(function() {
                var selectValue = $(this).select().val();
                var request = [{id : $(this).attr('id'), value : {value : parseInt(selectValue)}}];
                console.log(request);
                $.ajax({
                    url: path + '/gui_changed',
                    dataType: 'json',
                    type: 'POST',
                    data: JSON.stringify(request),
                    contentType: "application/json",
                    success: function (response) {
                        bubbles.update(response);
                    }
                });
            }
        ); 
        });
        
        $(document).ready(function() {
            $(".slider>div").each(function(){
                var minVal = parseInt($(this).parent().find('input:first').val());
                var maxVal = parseInt($(this).parent().find('input:last').val());
                var params = {
                    range: true,
                    min: minVal,
                    max: maxVal,
                    values: [ minVal, maxVal ],
                    step : 1,
                    stop : sliderStopCallback,
                    slide : function(event, ui) {
                        $(this).parent().find('input:first').val(ui.values[0]);
                        $(this).parent().find('input:last').val(ui.values[1]);
                    }
                };
                $(this).slider(params);
            });
        });
        function sliderStopCallback(event, ui) {
            
            $(this).parent().find('input:first').val(ui.values[0]);
            $(this).parent().find('input:last').val(ui.values[1]);
            var id = $(this).parent().attr('id');
            var request = [{id : id, value : {leftValue : ui.values[0], rightValue : ui.values[1]}}];
            $.ajax({
                url: path +  '/gui_changed',
                dataType: 'json',
                type: 'POST',
                data: JSON.stringify(request),
                contentType: "application/json",
                success: function (response) {
                    bubbles.update(response)
                },
                error: function(response) {
                    console.log(response);
                }
            });
        }
        $(document).ready(function(){ 
            var map = new L.Map('map');
            cloudmadeUrl = 'http://grwe.net/osm/{z}/{x}/{y}.png';
            var cloudmadeAttribution = 'Map data &copy; 2011 OpenStreetMap contributors, Imagery &copy; 2011 CloudMade';
            
            var cloudmade = new L.TileLayer(cloudmadeUrl, {
                maxZoom: 8, 
                minZoom: 3, 
                attribution: cloudmadeAttribution,
                maxBounds : bounds
            });
            map.setView(new L.LatLng(0, 0), 4).addLayer(cloudmade);
            
            var canvas = BubbleFieldProvider(map);
            bubbles = BubblesInit(canvas, initCities);
            
            
            bubbles.draw();
            //Запиливаем тэг defs к svg, чтобы была возможность рисовать круги с градиентом.
            appendSVGGradientData();
        }
        
    );
    </script>
</html>
