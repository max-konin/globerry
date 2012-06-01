<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<% String path = request.getContextPath().toString(); %>
<HTML>
<HEAD>

<meta charset="utf-8">

<TITLE>Globerry (v1.0b)</TITLE>
<!--



<link rel="stylesheet" href="resources/styles/main.css" type="text/css" />

<link rel="stylesheet" type="text/css" media="all" href="resources/styles/jquery.selectbox.css" />
-->
<link rel="stylesheet" href="resources/javascripts/CloudMade-Leaflet-538dfb4/dist/leaflet.css" />
<link rel="stylesheet" href="resources/styles/bottom.css" type="text/css" />
<link type="text/css" href="resources/styles/slidersCss.css" rel="stylesheet" media="all" />
<link rel="stylesheet" href="resources/styles/head.css" type="text/css" />
<link rel="stylesheet" href="resources/styles/map.css" type="text/css" />

<script src="resources/javascripts/CloudMade-Leaflet-538dfb4/dist/leaflet.js"></script>
<script type="text/javascript" src="resources/javascripts/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="resources/javascripts/main.js"></script>
<script type="text/javascript" src="resources/javascripts/headerSwitch.js"></script>
<script type="text/javascript" src="resources/javascripts/jquery.ui-slider.js"></script>
<script type="text/javascript" src="resources/javascripts/jquery.main.js"></script>
<script type="text/javascript" src="resources/javascripts/LeafMap.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	init("<%=path%>");
});
</script>
<!--
<script type="text/javascript" src="resources/javascripts/raphael.js"></script>
<script type="text/javascript" src="resources/javascripts/LeafMap.js"></script>
<script type="text/javascript" src="resources/javascripts/main.js"></script>

<script type="text/javascript" src="resources/javascripts/jquery-1.7.2.min.js"></script>


<script type="text/javascript" src="resources/javascripts/jquery.selectbox-0.6.1.js"></script>
-->


</HEAD>
<BODY>
<div id = 'top'>
	<div id = 'invisibleHeadBlock'></div>
	<div id = 'head'>
		<div id ='header'>
			<div id='headTop'>
				<div id="GloberryTitle"></div>
				<div id="headText">
					<input type="text" id="HText" value="" readonly />
				</div>
				<div id ="UpperHeaderBlockWithCircle">
					<div id = 'circle'></div>
				</div>
			</div>
			<div id='headContent1' class ='headSwitcharable'>
				<div id ='WhoText' class = 'requestText'></div>
				<div id='WhoSelectListBG' class = 'ListBG'>
					<div id='WhoSelect' class = 'ListButton'></div>
					<div id='aloneSelected' class ='selected'></div>
					<div id='WithFriendsSelected' class ='selected'></div>
					<div id='WithFamilySelected' class ='selected'></div>
					<div id='CoupleSelected' class ='selected'></div>
					<div id ='WhoSelectBG' class ='SelectLargeImage'>
						<div id = 'alone' class='selectItem'></div>
						<div id = 'WithFriends' class='selectItem'></div>
						<div id = 'WithFamily' class='selectItem'></div>
						<div id = 'Couple' class='selectItem'></div>
					</div>
				</div>
				
				<div id ='WhatText' class = 'requestText'></div>
				<div id='WhatSelectListBG' class='ListBG'>
					<div id='WhatSelect' class = 'ListButton'></div>
					<div id='tanSelected' class ='selected'></div>
					<div id='skiSelected' class ='selected'></div>
					<div id='watchSelected' class ='selected'></div>
					<div id='shoppingSelected' class ='selected'></div>
					<div id='cruiseSelected' class ='selected'></div>
					<div id ='WhatSelectBG' class ='SelectLargeImage'>
						<div id = 'tan' class='selectItem'></div>
						<div id = 'ski' class='selectItem'></div>
						<div id = 'watch' class='selectItem'></div>
						<div id = 'shopping' class='selectItem'></div>
						<div id = 'cruise' class='selectItem'></div>
					</div>
				</div>
				
				<div id ='WhenText' class = 'requestText'></div>
				<div id='WhenSelectListBG' class='ListBG'>
					<div id='WhenSelect' class = 'ListButton'></div>
					<div id='janSelected' class ='selected'></div>
					<div id='febSelected' class ='selected'></div>
					<div id='marSelected' class ='selected'></div>
					<div id='aprSelected' class ='selected'></div>
					<div id='maySelected' class ='selected'></div>
					<div id='junSelected' class ='selected'></div>
					<div id='julSelected' class ='selected'></div>
					<div id='augSelected' class ='selected'></div>
					<div id='sepSelected' class ='selected'></div>
					<div id='octSelected' class ='selected'></div>
					<div id='novSelected' class ='selected'></div>
					<div id='decSelected' class ='selected'></div>
					<div id ='WhenSelectBG' class ='SelectLargeImage'>
						<div id = 'jan' class='selectItem'></div>
						<div id = 'feb' class='selectItem'></div>
						<div id = 'mar' class='selectItem'></div>
						<div id = 'apr' class='selectItem'></div>
						<div id = 'may' class='selectItem'></div>
						<div id = 'jun' class='selectItem'></div>
						<div id = 'jul' class='selectItem'></div>
						<div id = 'aug' class='selectItem'></div>
						<div id = 'sep' class='selectItem'></div>
						<div id = 'oct' class='selectItem'></div>
						<div id = 'nov' class='selectItem'></div>
						<div id = 'dec' class='selectItem'></div>
					</div>	
				</div>
				<div id ='HeaderButton'>
					<div id ='HeaderButtonText' class = 'headerButton'></div>
					<div id ='HeaderButtonTBackGround' class = 'headerButton'></div>
				</div>
			</div>
			<div id='headContent2' class ='headSwitcharable'>
				<div id='FirstBlock' class='secondHeaderBlocks'>
                    <form action="#" method="post" >
		                <div class="formCost">
			                <input type="text" id="minCost" class ='left' value="-35" readonly />
                            <div id='tempSText' class = 'sText'></div>
							<input type="text" id="maxCost" class ='right' value="+35" readonly />
			            </div>
			            <div class="sliderCont" id ="tSCont">
							<div id="tempSlider" class="ui-slider ui-slider-horizontal ui-widget ui-widget-content ui-corner-all">
								<a id='leftSliderHeandlerTemp' class="ui-slider-handle ui-state-default ui-corner-all" href="#" style="left: 0%"></a>
								<a id='rightSliderHeandlerTemp' class="ui-slider-handle ui-state-default ui-corner-all" href="#" style="left: 100%"></a> <!---->
							</div>					
			            </div>
		            </form>
                    <form action="#" method="post" >
			            <div class="formCost">
				            <input type="text" id="alcMinCost" class ='left' value="$0" readonly />
                            <div id='AlcoholSText' class = 'sText' ></div>
				            <input type="text" id="alcMaxCost" class ='right' value="$30" readonly />
			            </div>
			            <div class="sliderCont">
					        <div id="alchSlider">
								<a class="ui-slider-handle ui-state-default ui-corner-all" href="#" style="left: 0%"></a>
								<a id='rightSliderHeandlerAlc' class="ui-slider-handle ui-state-default ui-corner-all" href="#" style="left: 100%"></a> <!---->
							</div>
			            </div>
		            </form>					
					
					
				</div>
				
				<div id='SecondBlock' class='secondHeaderBlocks'>
                    <form action="#" method="post" >
			            <div class="formCost">
				            <input type="text" id="TimeMinV" class ='left' value="0 Ч" readonly />
                            <div id='TimeSText' class = 'sText'></div>
				            <input type="text" id="TimeMaxV" class ='right' value="24 Ч" readonly />
			            </div>
			            <div class="sliderCont" id ="timeCont">
					            <div id="timeSlider">
									<a class="ui-slider-handle ui-state-default ui-corner-all" href="#" style="left: 0%"></a>
									<a id='rightSliderHeandlerTime' class="ui-slider-handle ui-state-default ui-corner-all" href="#" style="left: 100%"></a>								
								</div>
			            </div>
		            </form>
					<form action="#" method="post" >
			            <div class="formCost">
				            <input type="text" class ='left' value="" readonly />
                            <div id='Mood' class = 'sText' ></div>
				            <input type="text" class ='right' value="" readonly />
			            </div>
			            <div class="sliderCont">
					            <div id="MoodSlider">
									<a id='MoodHeandler' class="ui-slider-handle ui-state-default ui-corner-all" href="#" style="left: 0%"></a>
								</div>
			            </div>
		            </form>
				</div>
				<div id='ThirdBlock' class='secondHeaderBlocks'>
                    <form action="#" method="post" >
			            <div class="formCost">
				            <input type="text" id="LivMinV" class ='left' value="$0" readonly />
                            <div id='Living' class = 'sText'></div>
				            <input type="text" id="LivMaxV" class ='right' value="$300" readonly />
			            </div>
			            <div class="sliderCont" id ="LivCont">
					            <div id="LivSlider">
									<a class="ui-slider-handle ui-state-default ui-corner-all" href="#" style="left: 0%"></a>
									<a id='rightSliderHeandlerLiv' class="ui-slider-handle ui-state-default ui-corner-all" href="#" style="left: 100%"></a>								
								</div>
			            </div>
		            </form>
                    <form action="#" method="post" >
			            <div class="formCost">
				            <input type="text" class ='left' value="" readonly />
                            <div id='security' class = 'sText' ></div>
				            <input type="text" class ='right' value="" readonly />
			            </div>
			            <div class="sliderCont">
					            <div id="securitySlider">
									<a id='SecurityHeandler' class="ui-slider-handle ui-state-default ui-corner-all" href="#" style="left: 0%"></a>	
								</div>
			            </div>
		            </form>				
				</div>
				<div id='ForthBlock' class='secondHeaderBlocks'>
                    <form action="#" method="post" >
			            <div class="formCost">
				            <input type="text" id="FoodMinV" class ='left' value="$0" readonly />
                            <div id='food' class = 'sText'></div>
				            <input type="text" id="FoodMaxV" class ='right' value="$100" readonly />
			            </div>
			            <div class="sliderCont" id ="foodCont">
					            <div id="foodSlider">
									<a class="ui-slider-handle ui-state-default ui-corner-all" href="#" style="left: 0%"></a>
									<a id='rightSliderHeandlerFood' class="ui-slider-handle ui-state-default ui-corner-all" href="#" style="left: 100%"></a>								
								</div>
			            </div>
		            </form>
                    <form action="#" method="post" >
			            <div class="formCost">
				            <input type="text" class ='left' value="" readonly />
                            <div id='Sex' class = 'sText' ></div>
				            <input type="text" class ='right' value="" readonly />
			            </div>
			            <div class="sliderCont">
					            <div id="SexSlider">
									<a id='FoodHeandler' class="ui-slider-handle ui-state-default ui-corner-all" href="#" style="left: 50%"></a>	
								</div>
			            </div>
		            </form>
				</div>
				<div id='FifthBlock' class='secondHeaderBlocks'>
					<div id='FifthBlockText'></div>
					<div id ='VisaButtonActive'></div>
					<div id = 'LangButtonActive'></div>
				</div>
			</div>
			<div id='headBottom'>
				<div id = 'HeaderButtonUp' class='headerUpSwitcher'></div>
			</div>
			<div id ='calendar'>
				<div id='CalendarIMG'></div>
				<div id='JanBG' class='upperClicableCaendarBG'></div>
				<div id='FebBG' class='upperClicableCaendarBG'></div>
				<div id='MarBG' class='upperClicableCaendarBG'></div>
				<div id='AprBG' class='upperClicableCaendarBG'></div>
				<div id='MayBG' class='upperClicableCaendarBG'></div>
				<div id='JunBG' class='upperClicableCaendarBG'></div>
				<div id='JulBG' class='upperClicableCaendarBG'></div>
				<div id='AugBG' class='upperClicableCaendarBG'></div>
				<div id='SepBG' class='upperClicableCaendarBG'></div>
				<div id='OctBG' class='upperClicableCaendarBG'></div>
				<div id='NovBG' class='upperClicableCaendarBG'></div>
				<div id='DecBG' class='upperClicableCaendarBG'></div>
				<div id='calendarText'></div>
				<div id='JanB' class='upperClicableCaendar'></div>
				<div id='FebB' class='upperClicableCaendar'></div>
				<div id='MarB' class='upperClicableCaendar'></div>
				<div id='AprB' class='upperClicableCaendar'></div>
				<div id='MayB' class='upperClicableCaendar'></div>
				<div id='JunB' class='upperClicableCaendar'></div>
				<div id='JulB' class='upperClicableCaendar'></div>
				<div id='AugB' class='upperClicableCaendar'></div>
				<div id='SepB' class='upperClicableCaendar'></div>
				<div id='OctB' class='upperClicableCaendar'></div>
				<div id='NovB' class='upperClicableCaendar'></div>
				<div id='DecB' class='upperClicableCaendar'></div>
			</div>
		</div>
	</div>
</div>
<div id= 'bottomContainer'>
	<div id = "invisibleBottom"></div>
	<div id='bottom' class='roundBorder'>
	<div id ='bottomButtons'>
		<div id='Tour' class='bottomButton'>
			<div class = 'bottomHeadText'>Туры</div>
		</div>
		<div id='Avia' class='bottomButton'>
			<div class = 'bottomHeadText'>Авиабилеты</div>
		</div>
		<div id='Hotel' class='bottomButton'>
			<div class = 'bottomHeadText'>Отели</div>
		</div>
		<div id='Auto' class='bottomButton'>
			<div class = 'bottomHeadText'>Авто</div>
		</div>
	</div>
	<div id='gradLine'></div>
	<div id ='WhiteBottom'>
		<div id='TourB' class='bottomB'>Туры</div>
		<div id='AviaB' class='bottomB'>Авиа</div>
		<div id='HotelB' class='bottomB'>Отели</div>
		<div id='AutoB' class='bottomB'>Авто</div>
	</div>	
	</div>

	

</div>
<div id='map'></div>
</BODY>
</HTML>