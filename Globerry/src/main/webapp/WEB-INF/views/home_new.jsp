<%-- 
    Document   : home_new_design
    Created on : 27.06.2012, 20:10:49
    Author     : Ed
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <meta charset="utf-8">
    <TITLE>Globerry (v<spring:message code="buildNumber"/>)</TITLE>

    <link rel="stylesheet" href="resources/styles/globerry.css" type="text/css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>

    <link rel="stylesheet" href="resources/lib/leaflet-v0.3.1/dist/leaflet.css" />
    <!--<script src="resources/javascripts/CloudMade-Leaflet-538dfb4/debug/leaflet-include.js"></script>-->
    <script src="resources/lib/leaflet-v0.3.1/debug/leaflet-include.js"></script>

    <script src="resources/javascripts/bubbles.js"></script>
    <script type="text/javascript" src="resources/javascripts/ui-static-init.js"></script>
    <!--In the header of your page, paste the following for Kendo UI Web styles-->
    <link href="resources/javascripts/kendoui/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
    <link href="resources/javascripts/kendoui/styles/globerry.min.css" rel="stylesheet" type="text/css" />
    <!--Then paste the following for Kendo UI Web scripts-->
    <script src="resources/javascripts/kendoui/js/kendo.web.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="resources/javascripts/jquery.ui-slider.js"></script>
    
    
    <!--   стили для таблицы параметров     -->
<!--        <link type="text/css" href="resources/lib/colorpicker/lib/jquery-ui-1.8.21/css/ui-lightness/jquery-ui-1.8.21.custom.css" rel="stylesheet" />-->
    <link rel="stylesheet" media="screen" type="text/css" href="resources/lib/colorpicker/css/layout.css" />
    <link rel="stylesheet" media="screen" type="text/css" href="resources/lib/colorpicker/css/colorpicker.css" />
    <link rel="stylesheet" media="screen" type="text/css" href="resources/lib/paramsTable/css/paramsTableStyle.css" />
    <!--   /стили для таблицы параметров     -->
    <!--   скрипты для  таблицы параметров   -->
<!--        <script type="text/javascript" src="resources/lib/colorpicker/js/jquery.js"></script>-->
    <script type="text/javascript" src="resources/lib/colorpicker/js/colorpicker.js"></script>
    <script type="text/javascript" src="resources/lib/colorpicker/js/eye.js"></script>
    <script type="text/javascript" src="resources/lib/colorpicker/js/utils.js"></script>
    <script type="text/javascript" src="resources/lib/colorpicker/js/layout.js"></script>
    <script type="text/javascript" src="resources/lib/colorpicker/lib/jquery.tabSlideOut.v1.3.js"></script>
    <script type="text/javascript" src="resources/lib/paramsTable/js/paramsTable.js"></script>
    <!--   /скрипты для  таблицы параметров   -->
</head>
<body>
    <div id = 'top'>
        <div id = 'invisibleHeadBlock'></div>
        <div id='head'>
            <div id='header'>
                <div id='headTop'>
                    <div id="globerryTitle">GLOBERRY</div>
                    <div id='headText'><input type="text" id="hText" value="" readonly=""></div>
                </div>
                <div id='headContent1' class ='headSwitcharable changeHeaderByClick'>
                    <div id ='whoText' class = 'requestText'>
                        <span id="menuNumber">01.</span>
                        <spring:message code="label.who"/>
                    </div>
                    <div id='whoDropDownList' class='whoWhatWhenDropDownList'>
                        <select id='' class='whoSelect gui_element'  style="width:120px" guiId="${who.getId()}">
                            <c:forEach items="${who.getOptionAvaliable()}" var="value">
                                <option value="${value}">
                                    <spring:message code="label.who${value}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id ='whatText' class = 'requestText'>
                        <span id="menuNumber">02.</span>
                        <spring:message code="label.what"/>
                    </div>
                    <div id='whatDropDownList' class='whoWhatWhenDropDownList' >
                        <select id='' class='whatSelect gui_element' style="width:100px" guiId="${what.getId()}">
                            <c:forEach items="${what.getOptionAvaliable()}" var="value">
                                <option value="${value}">
                                    <spring:message code="label.what${value}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id ='whenText' class = 'requestText'><span id="menuNumber">03.</span><spring:message code="label.when"/></div>
                    <div id='whenDropDownList' class='whoWhatWhenDropDownList'>
                        <select id='whenSelect' class='whenSelect gui_element' style="width:100px" guiId="${when.getId()}">
                            <c:forEach items="${when.getOptionAvaliable()}" var="value">
                                <option value="${value}">
                                    <spring:message code="label.m${value}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id ='headerButton'>
                        <div id ='headerButtonText' class = 'headerButton'><spring:message code="label.go"/></div>
                        <div id ='headerButtonTBackGround' class = 'headerButton'></div>
                    </div>
                </div>
                <div id='headContent2' class ='headSwitcharable'>
                    <div id="firstBlock" class="secondHeaderBlocks">
                        <div id='temperatureBlock' class='sliderBlocks gui_element' guiId="${temperature.getId()}"
                             >
                            <div class="formCost">
                                <div class="bucks">
                                    <input type="text" id="minCost" class="left" 
                                           value="<fmt:formatNumber value="${temperature.getMinValue()}"
                                              minFractionDigits="0" maxFractionDigits="0"/>" readonly />
                                </div>
                                <div id='temperatureText' class='blocksText' >
                                    <spring:message code="label.temperature"/>
                                </div>
                                <div class="bucks rightBucks">
                                    <input type="text" id="maxCost" class="right"
                                           value="<fmt:formatNumber value="${temperature.getMaxValue()}"
                                              minFractionDigits="0" maxFractionDigits="0"/>" readonly />
                                </div>
                            </div>
                            <div id='temperatureSlider' class='slider-range'></div>
                        </div>
                        <div id='alcBlock' class='sliderBlocks bottomLineInBlocks gui_element'
                             style='width:200px;' guiId="${alcohol.getId()}">
                            <div class="formCost">
                                <div class="bucks">$
                                    <input type="text" id="alcMinCost" class ='left bucksInputPosition' 
                                           value="<fmt:formatNumber value="${alcohol.getMinValue()}" 
                                                             minFractionDigits="0" maxFractionDigits="0"/>" readonly />
                                </div>
                                <div id='alcText' class='blocksText'><spring:message code="label.alcohol"/></div>
                                <div class="bucks rightBucks">$
                                    <input type="text" id="alcMaxCost" class ='right bucksInputPosition' 
                                           value="<fmt:formatNumber value="${alcohol.getMaxValue()}"  
                                                             minFractionDigits="0" maxFractionDigits="0"/>" readonly />
                                </div>
                            </div>
                            <div id='alcSlider' class='slider-range'></div>
                        </div>
                    </div>
                    <div id="secondBlock" class="secondHeaderBlocks">
                        <div id='timeInPathBlock' class='sliderBlocks gui_element'
                             style='width:150px;' guiId="${travelTime.getId()}">
                            <div class="formCost">
                                <div class="bucks">
                                    <input type="text" id="timeMinV" class ='left'
                                           value="<fmt:formatNumber value="${travelTime.getMinValue()}"
                                                             minFractionDigits="0" maxFractionDigits="0"/>" readonly />
                                    <spring:message code="label.hour"/>
                                </div>
                                <div id='timeInPathText' class='blocksText' >
                                    <spring:message code="label.travel_time"/>
                                </div>
                                <div class="bucks rightBucks">
                                    <input type="text" id="timeMaxV" class ='right' 
                                           value="<fmt:formatNumber value="${travelTime.getMaxValue()}" 
                                                             minFractionDigits="0" maxFractionDigits="0"/>" readonly />
                                    <spring:message code="label.hour"/>
                                </div>
                            </div>
                            <div id='timeSlider' class='slider-range'></div>
                        </div>
                        <div id='moodBlock' class='sliderBlocks bottomLineInBlocks gui_element' style='width:150px;'
                             guiId="${mood.getId()}">
                            <div class="formCost">
                                <div class="bucks">
                                    <input type="text" id="moodMin" class ='left'
                                           value="<fmt:formatNumber value="${mood.getMinValue()}" minFractionDigits="0"
                                                             maxFractionDigits="0"/>" readonly />
                                </div>
                                <div id='moodText' class='blocksText' ><spring:message code="label.mood"/></div>
                                <div class="bucks rightBucks">
                                    <input type="text" id="moodMax" class ='right'
                                           value="<fmt:formatNumber value="${mood.getMaxValue()}" minFractionDigits="0"
                                                             maxFractionDigits="0"/>" readonly />
                                </div>
                            </div>
                            <div id='moodSlider' class='slider-range'></div>
                        </div>
                    </div>
                    <div id="thirdBlock" class="secondHeaderBlocks">
                        <div id='liveCostBlock' class='sliderBlocks gui_element' style='width:150px;'
                             guiId="${livingCost.getId()}">
                            <div class="formCost">
                                <div class="bucks">$
                                    <input type="text" id="livMinV" class ='left bucksInputPosition'
                                           value="<fmt:formatNumber value="${livingCost.getMinValue()}"
                                                             minFractionDigits="0" maxFractionDigits="0"/>" readonly />
                                </div>
                                <div id='liveText' class='blocksText '><spring:message code="label.living_cost"/></div>
                                <div class="bucks rightBucks">$
                                    <input type="text" id="LivMaxV" class ='right bucksInputPosition'
                                           value="<fmt:formatNumber value="${livingCost.getMaxValue()}"
                                                             minFractionDigits="0" maxFractionDigits="0"/>" readonly />
                                </div>
                            </div>
                            <div id='livecostSlider' class='slider-range'></div>
                        </div>
                        <div id='securityBlock' class='sliderBlocks bottomLineInBlocks gui_element' style='width:150px;'
                             guiId="${security.getId()}">
                            <div class="formCost">
                                <div class="bucks">
                                    <input type="text" id="securityMin" class ='left bucksInputPosition'
                                           value="<fmt:formatNumber value="${security.getMinValue()}"
                                                             minFractionDigits="0" maxFractionDigits="0"/>" readonly />
                                </div>
                                <div id='securityText' class='blocksText' ><spring:message code="label.security"/></div>
                                <div class="bucks rightBucks">
                                    <input type="text" id="securityMax" class ='right bucksInputPosition'
                                           value="<fmt:formatNumber value="${security.getMaxValue()}"
                                                             minFractionDigits="0" maxFractionDigits="0"/>" readonly />
                                </div>
                            </div>
                            <div id='securitySlider' class='slider-range'></div>
                        </div>
                    </div>
                    <div id="forthBlock" class="secondHeaderBlocks">
                        <div id='foodBlock' class='sliderBlocks gui_element' style='width:150px;'
                             guiId="${cost.getId()}">
                            <div class="formCost">
                                <div class="bucks">$
                                    <input type="text" id="foodMinV" class ='left bucksInputPosition'
                                           value="<fmt:formatNumber value="${cost.getMinValue()}" minFractionDigits="0"
                                                             maxFractionDigits="0"/>" readonly />
                                </div>
                                <div id='foodText' class='blocksText' ><spring:message code="label.food"/></div>
                                <div class="bucks rightBucks">$
                                    <input type="text" id="foodMaxV" class ='right bucksInputPosition'
                                           value="<fmt:formatNumber value="${cost.getMaxValue()}" minFractionDigits="0"
                                                             maxFractionDigits="0"/>" readonly/>
                                </div>
                            </div>
                            <div id='foodSlider' class='slider-range'></div>
                        </div>
                        <div id='sexBlock' class='sliderBlocks bottomLineInBlocks gui_element' style='width:150px;'
                             guiId="${sex.getId()}">
                            <div class="formCost">
                                <div class="bucks">
                                    <input type="text" id="sexMin" class ='left bucksInputPosition'
                                           value="<fmt:formatNumber value="${sex.getMinValue()}" minFractionDigits="0"
                                                             maxFractionDigits="0"/>" readonly />
                                </div>
                                <div id='sexText' class='blocksText' ><spring:message code="label.sex"/></div>
                                <div class="bucks rightBucks">
                                    <input type="text" id="sexMax" class ='right bucksInputPosition'
                                           value="<fmt:formatNumber value="${sex.getMaxValue()}" minFractionDigits="0"
                                                             maxFractionDigits="0"/>" readonly />
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
                        <div id="visaText" class="blocksText">Виза
                            <div id="visaButtonActive">
                                <div class="check" id="visaCheckbox">
                                    <input name="visaCheck" id="visaCheckInput" value="0" type="checkbox" />
                                </div>
                            </div>
                        </div>
                        <br />
                        <div id="languageText" class="blocksText">Язык
                            <div id="langButtonActive">
                                <div class="check" id="langCheckbox">
                                    <input name="langCheck" id="langCheckInput" value="0" type="checkbox" />
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <div id='headBottom'>
                    <div id = 'headerButtonUp' class='headerUpSwitcher'></div>
                </div>
                <div id="calendar" style="display: none; height: 26px; ">
                    <div id="calendarIMG"></div>
                    <div id='allMonths' class='months changeHeaderByClick gui_element' guiId="${when.getId()}">
                        
                        <c:forEach items="${when.getOptionAvaliable()}" var="value">
                                <div value="${value}" id="month${value}" class="upperClickableCaendarBG month">
                                    <spring:message code="label.m${value}"/>
                                </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="bottomContainer">
        <div id="invisibleBottom"></div>
        <div id="bottom" class="roundBorder" style="display: block; ">
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
                <div id="auto" class="bottomButton">
                    <div class="bottomHeadText">Авто</div>
                </div>
            </div>
            <div id="gradLine"></div>
            <div id="whiteBottom">
                <div id="tourB" class="bottomB">Туры</div>
                <div id="aviaB" class="bottomB">Авиа</div>
                <div id="hotelB" class="bottomB">Отели</div>
                <div id="autoB" class="bottomB">Авто</div>
            </div>	
        </div>
    </div>
    <div id='map'></div>
     <jsp:include page="paramsTable.jsp" />
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
<script type="text/javascript">
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
    $(document).ready(function() {
        //Устанавливаем январь активным в выдвигающимся меню.
        $('#allMonths>div:first').addClass('activeMonth');
        /***************************************************/
        //выбор месяца
        
        $(".month").click(function() {
            if($(this).hasClass('activeMonth'))
                return;
            $('.activeMonth').removeClass('activeMonth');
            $(this).addClass('activeMonth');
            var value = $(this).attr('value');
            var comboBox = $('#whenSelect').data("kendoComboBox");
//            comboBox.select(parseInt(value) - 1);
            comboBox.select(parseInt(value));
            sendRequest($(this).parent().attr('guiId'), {value : parseInt(value)});
        });
				
        $('select.gui_element').change(function() {
            var value = $(this).select().val();
            sendRequest($(this).attr('guiId'), {value : parseInt(value)});
            var guiid = $(this).attr('guiId');
            //если изменено значение "кто едет"
            if (guiid == 1){ }
            //если изменено значение "что делать"
            if (guiid == 2){ }
            //если изменено значение "когда"
            if (guiid == 3){
                $('.activeMonth').removeClass('activeMonth');
                $('#month' + value).addClass('activeMonth');
            }
            
        });
        
        

        //обработка чекбоксов виза и язык
        $(".check").click(function() {
            if ($('#'+this.id).hasClass('checkOn'))
            {
                check_box_id = $('#'+this.id);
                $('#'+this.id).removeClass('checkOn');
                $('#'+this.id+'>'+'input').val(0);
            }
            else
            {
                $('#'+this.id).addClass('checkOn');
                $('#'+this.id+'>'+'input').val(1);
            }
        });

        /*******************************************************************************/
        //слайдеры
        $(".slider-range").each(function(){
            var minVal = parseInt($(this).parent().find('input:first').val()); 
            var maxVal = parseInt($(this).parent().find('input:last').val());  
            var params = {
                range: true,
                min: minVal,
                max: maxVal,
                values: [ minVal, maxVal ],
                step : 1,
                stop : function(event, ui) {
                        sendRequest($(this).parent().attr('guiId') ,{leftValue : ui.values[0],rightValue : ui.values[1]})
                    },
                slide : function(event, ui) {
                    $(this).parent().find('input:first').val(ui.values[0]);
                    $(this).parent().find('input:last').val(ui.values[1]);
                }
            };
            $(this).slider(params);
        });
    });
    
    $(document).ready(function(){ 
        
        cloudmadeUrl = 'http://grwe.net/osm/{z}/{x}/{y}.png';
        var cloudmadeAttribution = 'Map data &copy; 2011 OpenStreetMap contributors, Imagery &copy; 2011 CloudMade';
        var southWest = new L.LatLng(-75, -180); 
        var northEast = new L.LatLng(75, 180);
        var restrictBounds = new L.LatLngBounds(southWest, northEast);
        var map = new L.Map('map', {maxBounds : restrictBounds});
        //var map = new L.Map('map');
        var cloudmade = new L.TileLayer(cloudmadeUrl, {
            maxZoom: 7, 
            minZoom: 2, 
            attribution: cloudmadeAttribution
            
        });
        map.setView(new L.LatLng(51.505, -0.09), 2).addLayer(cloudmade);
            
        var canvas = BubbleFieldProvider(map);
        bubbles = BubblesInit(canvas, initCities);
            
            
        bubbles.draw();
        //Запиливаем тэг defs к svg, чтобы была возможность рисовать круги с градиентом.
        appendSVGGradientData();
        document.params = createParamTableObject();
        console.log(document.params.getStartOpacity());
        
        
            
    });
    function saveParams(){
        savedParams = {
            //key:value,
            startColor: document.params.getStartColor(),
            finishColor: document.params.getFinishColor(),
            startOpacity: document.params.getStartOpacity(),
            finishOpacity: document.params.getFinishOpacity(),
            
        }
        return savedParams;
    }
    
    function sendRequest(id, data) {
        
        var request = [{id : id, value : data}];
        
        console.log(request);
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
                
            }
        });
    }

</script>
</html>
