<%-- 
    Document   : home_new_design
    Created on : 27.06.2012, 20:10:49
    Author     : Ed
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<TITLE>Globerry (v<spring:message code="buildNumber" />)
	</TITLE>
	<link rel="stylesheet" href="resources/styles/fonts.css" type="text/css" />
        
	<link rel="stylesheet" href="resources/lib/leaflet-v0.3.1/dist/leaflet.css" />
        
	<link href="resources/javascripts/kendoui/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
	<link href="resources/javascripts/kendoui/styles/globerry.min.css"     rel="stylesheet" type="text/css" />
	<link href="resources/styles/globerry.css"                             rel="stylesheet"  type="text/css" />
	
	
	<script	src="resources/javascripts/jquery-1.7.2.min.js"></script>
	<script	src="resources/javascripts/Bottom.js"></script>
	<script	src="resources/javascripts/jquery-ui-1.8.23.custom.min.js"></script>
        
	<script src="resources/lib/leaflet-v0.3.1/debug/leaflet-include.js"></script>
    <script type="text/javascript" src="resources/javascripts/Bing.js"></script>

	<script type="text/javascript" src="resources/javascripts/ui-static-init.js"></script>
	<script type="text/javascript" src="resources/javascripts/AjaxRequestController.js"></script>
    <script type="text/javascript" src="resources/javascripts/bindings.js"></script>
      
	<script type="text/javascript" src="resources/javascripts/kendoui/js/kendo.web.min.js"></script>

	<script type="text/javascript" src="resources/javascripts/jquery.ui-slider.js"></script>
	<script type="text/javascript" src="resources/javascripts/bezier.js"></script>
	
	<script type="text/javascript" src="resources/javascripts/jquery.ui-slider.js"></script>
	<script type="text/javascript" src="resources/javascripts/jquery.easing.1.3.js"></script>
	<script type="text/javascript" src="resources/javascripts/jquery.mousewheel.js"></script>
	<script type="text/javascript" src="resources/javascripts/spans.js"></script>
	<script type="text/javascript" src="resources/javascripts/jhints.js"></script>
	<script type="text/javascript" src="resources/javascripts/ExPolygon.js"></script>
	<script type="text/javascript" src="resources/javascripts/curves.js"></script>
	<script type="text/javascript" src="resources/javascripts/jquery.tinyscrollbar.min.js"></script>
</head>
<body>
<div id="modal"><img src="resources/img/pac-man.gif"/></div>
	
	<div id='top'>
		<div id='invisibleHeadBlock'></div>
		<div id='head'>
			<div id='header'>
				<div id='headTop'>
					<div id="globerryTitle"></div>
					<div id='headText'>
						<input type="text" id="hText" value="" readonly="">
					</div>
                    <div id="upperHeaderBlockWithCircle">
                        <div id='circle'></div>
                    </div>
				</div>
				<div id='headContent1' class='headSwitcharable changeHeaderByClick'>
					<div id='whoText' class='requestText'>
						<span id="menuNumber">01.</span>
						<spring:message code="label.who" />
					</div>
					<div id='whoDropDownList' class='whoWhatWhenDropDownList'>
						<select id='' class='whoSelect gui_element' style="width: 100px"
								guiId="${who.getId()}">
							<c:forEach items="${who.getOptionAvaliable()}" var="value">
								<option value="${value}">
									<spring:message code="label.who${value}" />
								</option>
							</c:forEach>
						</select>
					</div>
					<div id='whatText' class='requestText'>
						<span id="menuNumber">02.</span>
						<spring:message code="label.what" />
					</div>
					<div id='whatDropDownList' class='whoWhatWhenDropDownList'>
						<select id='' class='whatSelect gui_element' style="width: 100px"
								guiId="${what.getId()}">
							<c:forEach items="${what.getOptionAvaliable()}" var="value">
								<option value="${value}">
									<spring:message code="label.what${value}" />
								</option>
							</c:forEach>
						</select>
					</div>
					<div id='whenText' class='requestText'>
						<span id="menuNumber">03.</span>
						<spring:message code="label.when" />
					</div>
					<div id='whenDropDownList' class='whoWhatWhenDropDownList'>
						<select id='whenSelect' class='whenSelect gui_element'
								style="width: 100px" guiId="${when.getId()}">
							<c:forEach items="${when.getOptionAvaliable()}" var="value">
								<option value="${value}">
									<spring:message code="label.m${value}" />
								</option>
							</c:forEach>
						</select>
					</div>
					<div id='headerButton'>
						<div id='headerButtonText' class='headerButton'>
							<spring:message code="label.go" />
						</div>
						<!--<div id='headerButtonTBackGround' class='headerButton'></div>-->
					</div>
				</div>
                                                
                                                
				<div id='headContent2' class='headSwitcharable'>
					<div id="firstBlock" class="secondHeaderBlocks">
						<div id='temperatureBlock' class='sliderBlocks gui_element'
							 guiId="${temperature.getId()}">
							<div class="formCost">
								<div class="bucks">
									<div id="minCost" class="left"><fmt:formatNumber value="${temperature.getMinValue()}"
															 minFractionDigits="0" maxFractionDigits="0"/></div>
								</div>
								<div id='temperatureText' class='blocksText' title="<spring:message code="label.title_temperature" />">
									<spring:message code="label.temperature" />
								</div>
								<div class="bucks rightBucks">
									<div id="maxCost" class="right">+<fmt:formatNumber value="${temperature.getMaxValue()}"
															 minFractionDigits="0" maxFractionDigits="0"/></div>
								</div>
							</div>
							<div id='temperatureSlider' class='slider-range'></div>
						</div>
						<div id='alcBlock'
							 class='sliderBlocks bottomLineInBlocks gui_element' guiId="${alcohol.getId()}">
							<div class="formCost">
								<div class="bucks">
									<div id="alcMinCost"
											 class='left bucksInputPosition'><fmt:formatNumber value="${alcohol.getMinValue()}" 
															   minFractionDigits="0" maxFractionDigits="0"/></div>
								</div>
								<div id='alcText' class='blocksText'  title="<spring:message code="label.title_alchCost" />">
									<spring:message code="label.alcohol" />
								</div>
								<div class="bucks rightBucks">
									<div id="alcMaxCost"
											 class='right bucksInputPosition'><fmt:formatNumber value="${alcohol.getMaxValue()}"  
															   minFractionDigits="0" maxFractionDigits="0"/></div>
								</div>
							</div>
							<div id='alcSlider' class='slider-range'></div>
						</div>
					</div>
					<div id="secondBlock" class="secondHeaderBlocks">
						<div id='timeInPathBlock' class='sliderBlocks gui_element' guiId="${travelTime.getId()}">
							<div class="formCost">
								<div class="bucks">
									<div id="timeMinV" class='left'><fmt:formatNumber value="${travelTime.getMinValue()}"
                                                             minFractionDigits="0" maxFractionDigits="0"/></div>
									<spring:message code="label.hour" />
								</div>
								<div id='timeInPathText' class='blocksText' title="<spring:message code="label.title_travelTime" />">
									<spring:message code="label.travel_time" />
								</div>
								<div class="bucks rightBucks">
									<div id="timeMaxV" class='right'><fmt:formatNumber value="${travelTime.getMaxValue()}" 
                                                             minFractionDigits="0" maxFractionDigits="0"/></div>
									<spring:message code="label.hour" />
								</div>
							</div>
							<div id='timeSlider' class='slider-range'></div>
						</div>
						<div id='moodBlock'
							 class='sliderBlocks bottomLineInBlocks gui_element' guiId="${mood.getId()}">
							<div class="formCost">
								<div class="bucks" style="display: none;">
									<div id="moodMin" class='left'><fmt:formatNumber value="${mood.getMinValue()}" minFractionDigits="0"
                                                             maxFractionDigits="0"/></div>
								</div>
								<div id='moodText' class='blocksText' title="<spring:message code="label.title_mood" />">
									<spring:message code="label.mood" />
								</div>
								<div class="bucks rightBucks" style="display: none;">
									<div id="moodMax" class='right'><fmt:formatNumber value="${mood.getMaxValue()}" minFractionDigits="0"
                                                             maxFractionDigits="0"/></div>
								</div>
							</div>
							<div id='moodSlider' class='slider-range'></div>
						</div>
					</div>
					<div id="thirdBlock" class="secondHeaderBlocks">
						<div id='liveCostBlock' class='sliderBlocks gui_element' guiId="${livingCost.getId()}">
							<div class="formCost" >
								<div class="bucks">
									<div id="livMinV"
											 class='left bucksInputPosition'><fmt:formatNumber value="${livingCost.getMinValue()}"
															   minFractionDigits="0" maxFractionDigits="0"/></div>
								</div>
								<div id='liveText' class='blocksText' title="<spring:message code="label.title_livingCost" />">
									<spring:message code="label.living_cost" />
								</div>
								<div class="bucks rightBucks">
									<div id="livMaxV"
											 class='right bucksInputPosition'><fmt:formatNumber value="${livingCost.getMaxValue()}"
															   minFractionDigits="0" maxFractionDigits="0"/></div>
								</div>
							</div>
							<div id='livecostSlider' class='slider-range'></div>
						</div>
						<div id='securityBlock'
							 class='sliderBlocks bottomLineInBlocks gui_element' guiId="${security.getId()}">
							<div class="formCost">
								<div class="bucks" style="display: none;">
									<div id="securityMin"
										   class='left bucksInputPosition'><fmt:formatNumber value="${security.getMinValue()}"
                                                             minFractionDigits="0" maxFractionDigits="0"/></div>
								</div>
								<div id='securityText' class='blocksText' title="<spring:message code="label.title_security" />">
									<spring:message code="label.security" />
								</div>
								<div class="bucks rightBucks" style="display: none;">
									<div id="securityMax"
										   class='right bucksInputPosition'><fmt:formatNumber value="${security.getMaxValue()}"
                                                             minFractionDigits="0" maxFractionDigits="0"/></div>
								</div>
							</div>
							<div id='securitySlider' class='slider-range'></div>
						</div>
					</div>
					<div id="forthBlock" class="secondHeaderBlocks">
						<div id='foodBlock' class='sliderBlocks gui_element' guiId="${cost.getId()}">
							<div class="formCost" >
								<div class="bucks">
									<div id="foodMinV"
											 class='left bucksInputPosition'><fmt:formatNumber value="${cost.getMinValue()}" minFractionDigits="0"
															   maxFractionDigits="0"/></div>
								</div>
								<div id='foodText' class='blocksText' title="<spring:message code="label.title_eatCost" />">
									<spring:message code="label.food" />
								</div>
								<div class="bucks rightBucks">
									<div id="foodMaxV"
											 class='right bucksInputPosition'><fmt:formatNumber value="${cost.getMaxValue()}" minFractionDigits="0"
															   maxFractionDigits="0"/></div>
								</div>
							</div>
							<div id='foodSlider' class='slider-range'></div>
						</div>
						<div id='sexBlock'
							 class='sliderBlocks bottomLineInBlocks gui_element'
							 style='width: 150px;' guiId="${sex.getId()}">
							<div class="formCost">
								<div class="bucks" style="display: none;">
									<div id="sexMin" class='left bucksInputPosition'><fmt:formatNumber value="${sex.getMinValue()}" minFractionDigits="0"
                                                             maxFractionDigits="0"/></div>
								</div>
								<div id='sexText' class='blocksText' title="<spring:message code="label.title_sex" />">
									<spring:message code="label.sex" />
								</div>
								<div class="bucks rightBucks" style="display: none;">
									<div id="sexMax" class='right bucksInputPosition'><fmt:formatNumber value="${sex.getMaxValue()}" minFractionDigits="0"
                                                             maxFractionDigits="0"/></div>
								</div>
							</div>
							<div id='sexSlider' class='slider-range'></div>
						</div>
					</div>
					<!--
                    Чекбоксы для Виза и Язык.
                    input'ы скрыты, вместо них div'ы
                    value в input задаются в JS 0 и 1 для unchecked и checked соответственно
                    -->
					<div id="fifthBlock" class="secondHeaderBlocks">
						<div id="visaText" class="blocksText">
							<spring:message code="label.visa" />
							<div id="visaButtonActive">
								<div class="check<c:choose><c:when test="${visa.isChecked()}"> checkOn</c:when><c:otherwise></c:otherwise></c:choose>" id="visaCheckbox" guiId="${visa.getId()}">
                                                                    <input name="visaCheck" id="visaCheckInput" value="<c:choose><c:when test="${visa.isChecked()}">1</c:when><c:otherwise>0</c:otherwise></c:choose>"
										   type="checkbox" />
								</div>
							</div>
						</div>
						<br />
						<div id="languageText" class="blocksText">
							<spring:message code="label.lang" />
							<div id="langButtonActive">
								<div class="check<c:choose><c:when test="${rusLang.isChecked()}"> checkOn</c:when><c:otherwise></c:otherwise></c:choose>" id="langCheckbox" guiId="${rusLang.getId()}">
									<input name="langCheck" id="langCheckInput" value="<c:choose><c:when test="${rusLang.isChecked()}">1</c:when><c:otherwise>0</c:otherwise></c:choose>"
										   type="checkbox" />
								</div>
							</div>
						</div>

					</div>
				</div>
				<div id='headBottom'>
					<div id='headerButtonUp' class='headerUpSwitcher'></div>
				</div>
				<div id="calendar" style="display: none; height: 26px; top: -16px;">
					<!--<div id="calendarIMG"></div>-->
					<div id='allMonths' class='months changeHeaderByClick gui_element' guiId="${when.getId()}">
						<c:forEach items="${when.getOptionAvaliable()}" var="value">
						<div value="${value}" id="month${value}"
	                                                           class="upperClickableCaendarBG month<c:if test="${when.getValue() == value}"> activeMonth</c:if>">
							<spring:message code="label.m${value}" />
						</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="bottomContainer">
		<div id="invisibleBottom"></div>
		<div id="bottom" class="roundBorder" style="display: block;">
			<div id="bottomButtons">
				<div id="tour" class="bottomButton">
					<div class="bottomHeadText">Туры</div>
				</div>
				<div id="avia" class="bottomButton">
					<div class="bottomHeadText">Авиабилеты</div>
				</div>
				<div id="hotel" class="bottomButton">
					<div class="bottomHeadText">Отели</div>
				</div>
			</div>
			<div id="gradLine"></div>
			<div id="whiteBottom">
				<div id="tourB" class="bottomB">
					<div id="tourScrollBar" class="scrollbarY">

						<div class="scrollbar">
							<div class="track">
								<div class="thumb">
									<div class="end"></div>
								</div>
							</div>
						</div>

						<div class="viewport">

							<div class="overview"> 
							</div>

						</div>

					</div>

				</div>


				<div id="aviaB" class="bottomB">

					<div id="aviaScrollBar" class="scrollbarY">

						<div class="scrollbar">
							<div class="track">
								<div class="thumb">
									<div class="end"></div>
								</div>
							</div>
						</div>

						<div class="viewport">

							<div class="overview"></div>

						</div>

					</div>
				</div>
				<div id="hotelB" class="bottomB">
					<div id="hotelScrollBar" class="scrollbarY">
						<div class="scrollbar">
							<div class="track">
								<div class="thumb">
									<div class="end"></div>
								</div>
							</div>
						</div>
						<div class="viewport">
							<div class="overview"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id='map'></div>
</body>
<script type="text/javascript">
	$('#modal').hide();

    var path = '<%=request.getContextPath()%>';
    var cities = [
    <c:forEach items="${cities}" var="city">
            {"id":${city.getId()},"name":"${city.getName()}","ru_name":"${city.getRu_name()}","area":${city.getArea()},
                "population":${city.getPopulation()},"longitude":${city.getLongitude()},
                "latitude":${city.getLatitude()},"message":"${city.getMessage()}",
                "weight":${city.getWeight()}
            },
    </c:forEach>
		];
		var bubbles, params;
		var points = [];
		var globalMap;
		var globalPolygons = [];
		var curves;
		var zlevelGlobal = 3;
		var changeStep;
		var ajaxRequestController = new AjaxRequestController;
         
		$(document).ready(function() {

			//Устанавливаем текщуя месяц + 1 активным в выдвигающимся меню.
            var whenBox = $('#whenSelect').data("kendoComboBox");
			whenBox.select(${when.getValue()});
			
			cloudmadeUrl = 'http://{s}.tile.cloudmade.com/BC9A493B41014CAABB98F0471D759707/997/256/{z}/{x}/{y}.png'
			var cloudmadeAttribution = 'Map data &copy; 2011 OpenStreetMap contributors, Imagery &copy; 2011 CloudMade';
			var southWest = new L.LatLng(-90, 180); 
			var northEast = new L.LatLng(90, 180);
			
			var map = new L.Map('map');
			globalMap = map;
			globalMap.guiId = ${mapZoom.getId()};
			var cloudmade = new L.TileLayer(cloudmadeUrl, {
				maxZoom: 8, 
				minZoom: 2, 
				attribution: cloudmadeAttribution
			});
            var bing = new L.BingLayer("Atv2RUkslT7ih1WTiTywNklpZEN0-s8ppHvYa2LfcS7RH6wYavva7_dplMOgzvpT", {
				maxZoom: 8, 
				minZoom: 2
			});
			map.setView(new L.LatLng(25.505, -0.09), ${mapZoom.getValue()}).addLayer(bing);
			map.on("click", function(e) {
				(new Bottom).close();
			});

			globalMap.on('zoomend', function() {
			    $('#modal').show();
			    ajaxRequestController.sendRequest(globalMap.guiId , {value : globalMap.getZoom()})
			    //curves.redrawCurves(cities);
			    setTimeout(function(){$('#modal').hide();},200);
			   });
			
			
			curves = Curves(cities, map);
			curves.appendDefs();
		});
    
</script>
</html>
