<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<HTML>
<HEAD>

<meta charset="utf-8">

<TITLE>Globerry (new design)</TITLE>
<!---->
<link rel="stylesheet" href="http://code.leafletjs.com/leaflet-0.3.1/leaflet.css" />
<script src="http://code.leafletjs.com/leaflet-0.3.1/leaflet.js"></script>

<link rel="stylesheet" href="resources/styles/main.css" type="text/css" />
<link type="text/css" href="resources/styles/slidersCss.css" rel="stylesheet" media="all" />
<link rel="stylesheet" type="text/css" media="all" href="resources/styles/jquery.selectbox.css" />
<link rel="stylesheet" href="resources/styles/bottom.css" type="text/css" />

<script type="text/javascript" src="resources/javascripts/jquerry.js"></script>
<script type="text/javascript" src="resources/javascripts/raphael.js"></script>
<script type="text/javascript" src="resources/javascripts/LeafMap.js"></script>
<script type="text/javascript" src="resources/javascripts/main.js"></script>
<!---->
<script type="text/javascript" src="resources/javascripts/jquery.ui-slider.js"></script>
<script type="text/javascript" src="resources/javascripts/jquery.main.js"></script>
<script type="text/javascript" src="resources/javascripts/jquery.selectbox-0.6.1.js"></script>



</HEAD>
<BODY height='100%'>
<div id='div_container'>
        <div id ='globerryImg', class='roundBorder'>
            <div id ='GLIMG'>
                <img src="resources/img/Globerry.png" alt="Globerry">
            </div>
			<div id="headText">
				<input type="text" id="HText" value="" readonly />
			</div>
            <div id='upperToggle' class='toggels'>
            <div id ='circlecw'></div>
            <div id ='circlech'></div>
            <div id ='circle'></div>
            </div>
        </div>
        <div id ='head' , class='roundBorder'>
            <div id ='Request'>
                <div id ='who' class='textRequests'><p id ='whoTextDiv' class = 'textInRequests'><span class='number'>01.</span> Кто едет?</p></div>
                <div id ='whoReq' class='reqMenu'>
                    <!--<p class = 'textInRequests'>С друзьями--</p>-->
                    	<select id="WhoS">
							<option value="с друзьями">С друзьями</option>
							<option value="с семьей">С семьей</option>
							<option value="один">Один</option>
							<option value="вдвоем">Вдвоем</option>
							<option value="04"></option><!--<-->
						</select>
                </div>
                <div id ='what' class='textRequests'><p id ='whatTextDiv' class = 'textInRequests'><span class='number'>02.</span> Что делать?</p></div>
                <div id ='whatReq' class='reqMenu'>
                <!--<p class = 'textInRequests'>Загорать ----</p>-->
                       <select id="WhatS">
							<option value="загорать">Загорать</option>
							<option value="кататься">Кататься</option>
							<option value="достопримечательности">Смотреть</option>
							<option value="шоппинг">Шоппинг</option>
							<option value="в круиз">Круиз</option><!--<-->
							<option value="05"></option><!--<-->
						</select>
                </div>
                <div id ='when' class='textRequests'><p id ='whenTextDiv' class = 'textInRequests'><span class='number'>03.</span> Когда?</p></div>
                <div id ='whenReq' class='reqMenu'>
                    <!--<p class = 'textInRequests'>Февраль -----</p>-->
                       <select id="WhenS">
							<option value="январе" id ="janO" class ="OMonth">Январь</option>
							<option value="феврале" id="febO" class ="OMonth">Февраль</option>
							<option value="марте" id="marO" class ="OMonth">Март</option>
							<option value="апреле" id = "aprO" class ="OMonth">Апрель</option>
							<option value="мае" id="mayO" class ="OMonth">Май</option>
                            <option value="июне" id="junO" class ="OMonth">Июнь</option>
							<option value="июле" id="julO" class ="OMonth">Июль</option>
							<option value="августе" id="augO" class ="OMonth">Август</option>
							<option value="сентябре" id="sepO" class ="OMonth">Сентябрь</option>
							<option value="октябре" id="octO" class ="OMonth">Октябрь</option>
							<option value="ноябре" id="novO" class ="OMonth">Ноябрь</option>
							<option value="декабре" id="decO" class ="OMonth">Декабрь</option>
                            <option value="11"></option>
                            <option value="11"></option>
						</select>
                </div>
                <div id ='LetsGo' class='textRequests'><a href="#" id='buttonHeadSwitcher' class="button">Поехали</a></div>
            </div>
        </div>
        <div id='SliderRequest'>
            <div id ='SliderRequestContent'>
                <div id='Sliders'>
                    <div id ='firstSBlock' class = 'blocks'>
                        <form action="#" method="post" >
			                <div class="formCost">
				                <input type="text" id="minCost" class ='left' value="-35" readonly />
                                <div id='tempSText', class = 'sText'>Температура</div>
				                <input type="text" id="maxCost" class ='right' value="+35" readonly />
			                </div>
			                <div class="sliderCont" id ="tSCont">
					                <div id="tempSlider"></div>
			                </div>
		                </form>
                        <form action="#" method="post" >
			                <div class="formCost">
				                <input type="text" id="alcMinCost" class ='left' value="$0" readonly />
                                <div id='AlcoholSText', class = 'sText' >Алкоголь</div>
				                <input type="text" id="alcMaxCost" class ='right' value="$30" readonly />
			                </div>
			                <div class="sliderCont">
					                <div id="alchSlider"></div>
			                </div>
		                </form>
                    </div>
                    <!-- -->
                    <div id ='secondBlock' class = 'blocks'>
                        <form action="#" method="post" >
			                <div class="formCost">
				                <input type="text" id="TimeMinV" class ='left' value="0 Ч" readonly />
                                <div id='TimeSText', class = 'sText'>Время в пути</div>
				                <input type="text" id="TimeMaxV" class ='right' value="24 Ч" readonly />
			                </div>
			                <div class="sliderCont" id ="timeCont">
					                <div id="timeSlider"></div>
			                </div>
		                </form>
                        <form action="#" method="post" >
			                <div class="formCost">
				                <input type="text" class ='left' value="" readonly />
                                <div id='Mood', class = 'sText' >Настроение</div>
				                <input type="text" class ='right' value="" readonly />
			                </div>
			                <div class="sliderCont">
					                <div id="MoodSlider"></div>
			                </div>
		                </form>
                    </div>
                    <div id ='thirdBlock' class = 'blocks'>
                        <form action="#" method="post" >
			                <div class="formCost">
				                <input type="text" id="LivMinV" class ='left' value="$0" readonly />
                                <div id='Living', class = 'sText'>Проживание</div>
				                <input type="text" id="LivMaxV" class ='right' value="$300" readonly />
			                </div>
			                <div class="sliderCont" id ="LivCont">
					                <div id="LivSlider"></div>
			                </div>
		                </form>
                        <form action="#" method="post" >
			                <div class="formCost">
				                <input type="text" class ='left' value="" readonly />
                                <div id='security', class = 'sText' >Безопасность</div>
				                <input type="text" class ='right' value="" readonly />
			                </div>
			                <div class="sliderCont">
					                <div id="securitySlider"></div>
			                </div>
		                </form>
                    </div>
                    <div id ='forthBlock' class = 'blocks'>
                        <form action="#" method="post" >
			                <div class="formCost">
				                <input type="text" id="FoodMinV" class ='left' value="$0" readonly />
                                <div id='food', class = 'sText'>Еда</div>
				                <input type="text" id="FoodMaxV" class ='right' value="$100" readonly />
			                </div>
			                <div class="sliderCont" id ="foodCont">
					                <div id="foodSlider"></div>
			                </div>
		                </form>
                        <form action="#" method="post" >
			                <div class="formCost">
				                <input type="text" class ='left' value="" readonly />
                                <div id='Sex', class = 'sText' >Секс</div>
				                <input type="text" class ='right' value="" readonly />
			                </div>
			                <div class="sliderCont">
					                <div id="SexSlider"></div>
			                </div>
		                </form>
                    </div>
                    <div id ='fifthBlock' class = 'blocks'>
                    <div id='Visa' class='fbText'>Виза</div>
                    <div id ='VisaAc' class='VLBut'></div>
                    <div id='Lang' class='fbText'>Язык</div>
                    <div id ='LangAc' class='VLBut'></div>
                    </div>
                    <!-- -->
                </div>
            </div>
            <div id ='SliderRequestBottom', class='roundBorder'>
                <div id='bottomToggle' class='toggels'>
                    <div id='arrowcw'></div>
                    <div id ='arrow'>
                        <img src="resources/img/arrow.png" alt="slide up">
                    </div>
                </div>
            </div>
            <div id ='monthes'>
                <div id = 'calenrarimg'>
                    <img src="resources/img/calendar.png" alt="Globerry">
                </div>
            </div>
            <!--<div id='monthContainer'>-->
                <div class='calMonth' id ='jan'>Январь</div>
                <div class='calMonth' id ='feb'>Февраль</div>
                <div class='calMonth' id ='mar'>Март</div>
                <div class='calMonth' id ='apr'>Апрель</div>
                <div class='calMonth' id ='may'>Май</div>
                <div class='calMonth' id ='jun'>Июнь</div>
                <div class='calMonth' id ='jul'>Июль</div>
                <div class='calMonth' id ='aug'>Август</div>
                <div class='calMonth' id ='sep'>Сентябрь</div>
                <div class='calMonth' id ='oct'>Октябрь</div>
                <div class='calMonth' id ='nov'>Ноябрь</div>
                <div class='calMonth' id ='dec'>Декабрь</div>
            <!--</div>
            -->
        </div>
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
		</div>
		<div id ='WhiteBottom'></div>
		<div id='TourB' class='bottomB'>Туры</div>
		<div id='AviaB' class='bottomB'>Авиа</div>
		<div id='HotelB' class='bottomB'>Отели</div>
		<div id='AutoB' class='bottomB'>Авто</div>
		
    <div id='map'></div>
</div>
</BODY>
</HTML>