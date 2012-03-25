window.onload = function () {
    //------------------------------------------------
    // Please don't try to change anything, if you're 
    // not sure what you are doing, or are not aware
    // about dependences between objects and variables
    // in this code.
    //------------------------------------------------
    var Div_container = document.getElementById('div_container');
    var Map = document.getElementById('map');
    var Left = document.getElementById('left');
    var Head = document.getElementById('header');
    var Line = document.getElementById('line');
    var Month = document.getElementById('month');
    var BottomSwitcher = document.getElementById('bottom_switcher');
    var BottomAvia = document.getElementById('bottom_avia');
    var BottomAuto = document.getElementById('bottom_auto');
    var BottomHotel = document.getElementById('bottom_hotel');
    var BottomTour = document.getElementById('bottom_tour');

    var gradLineVioletFirstColor = '#67a'; //'#606';
    var gradLineVioletSecondColor = '#67a'; //'#606';
    var gradLineYellowFirstColor = '#f60';
    var gradLineYellowSecondColor = '#fd3'; //'#ff0';

    var bottomZIndexUp = '1';
    var bottomZIndexDown = '0';
    var bottomSwitcherDelay = 300;

    var lineColor = 'gray';
    var bottomColor = 'White';
    var bottomFont = 16;

    var Width = $(document.getElementById("div_container")).width();
    var Height = $(document.getElementById("div_container")).height();

    if (Width < 1500) {
        bottomFont = 14;
    }
    if (Width < 1000) {
        bottomFont = 12;
    }
    var osm = new OpenLayers.Layer.OSM();
    osm.wrapDateLine = false;

    var wms = new OpenLayers.Layer.WMS("OpenLayers WMS", "http://vmap0.tiles.osgeo.org/wms/vmap0", { layers: 'basic', zoom: 1 });

    var map = new OpenLayers.Map({
        projection: 'EPSG:900913',
        numZoomLevels: 6,

        controls: [
    new OpenLayers.Control.TouchNavigation({
        dragPanOptions: {
            enableKinetic: true
        }
    }),
    new OpenLayers.Control.ZoomPanel()
    ],


        div: "map",
        layers: [osm], center: new OpenLayers.LonLat(0, 0), zoom: 1
    });

    if (Width > 800) map.zoomIn();
    if (Width > 1500) map.zoomIn();
    /* 
    var osm = new OpenLayers.Layer.OSM();
    osm.wrapDateLine = false;

    map = new OpenLayers.Map({
    div: 'map',
    projection: 'EPSG:900913',
    numZoomLevels: 18,
    controls: [
    new OpenLayers.Control.TouchNavigation({
    dragPanOptions: {
    enableKinetic: true
    }
    }),
    new OpenLayers.Control.ZoomPanel(),
    toolbar
    ],
    layers: [osm],
    center: new OpenLayers.LonLat(0, 0),
    zoom: 1,
    theme: null
    });
    */


    // map.addLayers([wms, wfs, kml]);

    // map.setCenter(new OpenLayers.LonLat(0, 0), 3);
    /**/

    var HeadPaper = new Raphael(Head, '100%', '100%');
    var HeadPaperWidth = $(document.getElementById("header")).width();
    var HeadPaperHeight = $(document.getElementById("header")).height();
    var headGradRect1 = HeadPaper.rect(0, (HeadPaperHeight * 0.8), HeadPaperWidth, (HeadPaperHeight * 0.13));
    var headGradRect2 = HeadPaper.rect(0, (HeadPaperHeight * 0.9), HeadPaperWidth, (HeadPaperHeight * 0.07));
    HeadPaper.image("resources/img/Globerry.png", 0, HeadPaperHeight * 0.05, 230, HeadPaperHeight * 0.7);
    //<img src="Globerr1.jpg">

    headGradRect1.attr({ fill: [0, gradLineVioletFirstColor, gradLineVioletSecondColor].join("-"), stroke: 'none' });
    headGradRect2.attr({ fill: [0, gradLineYellowFirstColor, gradLineYellowSecondColor].join("-"), stroke: 'none' });

    var bottomSwitcher = new Raphael(BottomSwitcher, '100%', '100%');
    var bottomSwitcherWidth = $(document.getElementById("bottom_switcher")).width();
    var bottomSwitcherHeight = $(document.getElementById("bottom_switcher")).height();
    var tourRectIsActive = new Array;
    var tourrectCounter = 0;
    for (tourrectCounter = 0; tourrectCounter < 4; tourrectCounter++)
        tourRectIsActive[tourrectCounter] = false;
    tourRectIsActive[0] = true;
    /*
    var tourPathNotAnimated = 'M' + (bottomSwitcherWidth * 0.04) + ',' + (bottomSwitcherHeight * 0.85) + 'L' + (bottomSwitcherWidth * 0.06) + ',' + (bottomSwitcherHeight * 0.5) + 'L' + (bottomSwitcherWidth * 0.18) + ',' + (bottomSwitcherHeight * 0.5) + ',' + 'L' + (bottomSwitcherWidth * 0.2) + ',' + (bottomSwitcherHeight * 0.85) + 'z';
    var tourPathAnimated = 'M' + (bottomSwitcherWidth * 0.04) + ',' + (bottomSwitcherHeight * 0.85) + 'L' + (bottomSwitcherWidth * 0.06) + ',' + (bottomSwitcherHeight * 0.5) + 'L' + (bottomSwitcherWidth * 0.18) + ',' + (bottomSwitcherHeight * 0.5) + ',' + 'L' + (bottomSwitcherWidth * 0.2) + ',' + (bottomSwitcherHeight * 0.85) + 'Q' + (bottomSwitcherWidth * 0.11) + ',' + (bottomSwitcherHeight * 1.1) + ',' + (bottomSwitcherWidth * 0.04) + ',' + (bottomSwitcherHeight * 0.85);
    */
    var littleTourPathNotAnimated = 'M' + 45 + ',' + 55 + 'L' + 70 + ',' + 30 + 'L' + 180 + ',' + 30 + ',' + 'L' + 200 + ',' + 55 + 'z';
    var littleTourPathAnimated = 'M' + 45 + ',' + 55 + 'L' + 70 + ',' + 30 + 'L' + 180 + ',' + 30 + ',' + 'L' + 200 + ',' + 55 + 'Q' + 123 + ',' + 72 + ',' + 45 + ',' + 55;
    var largeTourPathNotAnimated = 'M' + 45 + ',' + 55 + 'L' + 70 + ',' + 30 + 'L' + 210 + ',' + 30 + ',' + 'L' + 230 + ',' + 55 + 'z';
    var largeTourPathAnimated = 'M' + 45 + ',' + 55 + 'L' + 70 + ',' + 30 + 'L' + 210 + ',' + 30 + ',' + 'L' + 230 + ',' + 55 + 'Q' + 130 + ',' + 72 + ',' + 45 + ',' + 55;
    var tourPathTranslaion = 162;
    var tourPathTextPosition = 140;
    var tourPathNotAnimated = largeTourPathNotAnimated;
    var tourPathAnimated = largeTourPathAnimated;
    if (Width < 1250) {
        var tourPathNotAnimated = littleTourPathNotAnimated;
        var tourPathAnimated = littleTourPathAnimated;
        tourPathTranslaion = 125;
        tourPathTextPosition = 125;
    }
    whiteLine = bottomSwitcher.rect(0, (bottomSwitcherHeight * 0.85), bottomSwitcherWidth, (bottomSwitcherHeight * 0.15));
    //whiteLine.attr({ fill: [0, '#ffe', '#ffa'].join("-"), stroke: bottomColor });
    whiteLine.attr({ fill: [0, '#fff', '#fee'].join("-"), stroke: 'white', 'stroke-size': 4 });
    //tour-------------------------------------------------------
    var tourRect = bottomSwitcher.path(tourPathAnimated);
    tourRect.attr({ fill: [0, 'yellow', 'orange'].join("-"), stroke: 'none' });
    var tourText = bottomSwitcher.text(tourPathTextPosition, (bottomSwitcherHeight * 0.7), "Туры");
    tourText.attr({ 'font-size': bottomFont });
    var tourRectOver = bottomSwitcher.path(tourPathAnimated);
    tourRectOver.attr({ fill: '#000', stroke: 'none', opacity: 0, cursor: "hand" });

    //Avia-------------------------------------------------------
    var aviaRect = bottomSwitcher.path(tourPathNotAnimated);
    aviaRect.attr({ fill: [0, '#08f', 'blue'].join("-"), stroke: 'none' });
    var aviaText = bottomSwitcher.text(tourPathTextPosition, (bottomSwitcherHeight * 0.7), "Авиабилеты");
    aviaText.attr({ 'font-size': bottomFont });
    aviaRect.translate(tourPathTranslaion, 0);
    aviaText.translate(tourPathTranslaion, 0);
    var aviaRectOver = bottomSwitcher.path(tourPathNotAnimated);
    aviaRectOver.translate(tourPathTranslaion, 0);
    aviaRectOver.attr({ fill: '#000', stroke: 'none', opacity: 0, cursor: "hand" });

    //Hotels-------------------------------------------------------
    var hotelRect = bottomSwitcher.path(tourPathNotAnimated);
    hotelRect.attr({ fill: [0, '#ff0', '#af0'].join("-"), stroke: 'none' });
    var hotelText = bottomSwitcher.text(tourPathTextPosition, (bottomSwitcherHeight * 0.7), "Отели");
    hotelText.attr({ 'font-size': bottomFont });
    hotelRect.translate((tourPathTranslaion * 2), 0);
    hotelText.translate((tourPathTranslaion * 2), 0);
    var hotelRectOver = bottomSwitcher.path(tourPathNotAnimated);
    hotelRectOver.translate((tourPathTranslaion * 2), 0);
    hotelRectOver.attr({ fill: '#000', stroke: 'none', opacity: 0, cursor: "hand" });

    //Auto-------------------------------------------------------
    var autoRect = bottomSwitcher.path(tourPathNotAnimated);
    autoRect.attr({ fill: [0, '#d14', '#f14'].join("-"), stroke: 'none', opacity: 0.8 });
    var autoText = bottomSwitcher.text(tourPathTextPosition, (bottomSwitcherHeight * 0.7), "Авто");
    autoText.attr({ 'font-size': bottomFont });
    autoRect.translate((tourPathTranslaion * 3), 0);
    autoText.translate((tourPathTranslaion * 3), 0);
    var autoRectOver = bottomSwitcher.path(tourPathNotAnimated);
    autoRectOver.translate((tourPathTranslaion * 3), 0);
    autoRectOver.attr({ fill: '#000', stroke: 'none', opacity: 0, cursor: "hand" });
    //tourRect.hover(function () {},function () {});

    // var myBottomSwitcherSet = bottomSwitcher.set();
    //myBottomSwitcherSet.push(aviaRect, aviaText, aviaRectOver, tourRect, tourText, tourRectOver, hotelRect, hotelText, hotelRectOver, autoRect, autoText, autoRectOver);

    tourRectOver.click(function () {
        tourRect.animate({ path: tourPathAnimated, zIndex: 1 }, bottomSwitcherDelay);
        tourRectOver.animate({ path: tourPathAnimated, zIndex: 1 }, bottomSwitcherDelay);
        tourRect.toFront();
        tourText.toFront();
        tourRectOver.toFront();
        aviaRect.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        aviaRectOver.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        hotelRect.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        hotelRectOver.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        autoRect.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        autoRectOver.animate({ path: tourPathNotAnimated, zIndex: 0 }, bottomSwitcherDelay);
        BottomTour.style.zIndex = bottomZIndexUp;
        BottomAuto.style.zIndex = bottomZIndexDown;
        BottomHotel.style.zIndex = bottomZIndexDown;
        BottomAvia.style.zIndex = bottomZIndexDown;
        for (tourrectCounter = 0; tourrectCounter < 4; tourrectCounter++)
            tourRectIsActive[tourrectCounter] = false;
        tourRectIsActive[0] = true;
    });
    aviaRectOver.click(function () {
        aviaRect.animate({ path: tourPathAnimated }, bottomSwitcherDelay);
        aviaRectOver.animate({ path: tourPathAnimated }, bottomSwitcherDelay);
        aviaRect.toFront();
        aviaText.toFront();
        aviaRectOver.toFront();
        tourRect.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        tourRectOver.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        hotelRect.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        hotelRectOver.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        autoRect.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        autoRectOver.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        BottomTour.style.zIndex = bottomZIndexDown;
        BottomAuto.style.zIndex = bottomZIndexDown;
        BottomHotel.style.zIndex = bottomZIndexDown;
        BottomAvia.style.zIndex = bottomZIndexUp;
        for (tourrectCounter = 0; tourrectCounter < 4; tourrectCounter++)
            tourRectIsActive[tourrectCounter] = false;
        tourRectIsActive[1] = true;
    });
    hotelRectOver.click(function () {
        hotelRect.animate({ path: tourPathAnimated }, bottomSwitcherDelay);
        hotelRectOver.animate({ path: tourPathAnimated }, bottomSwitcherDelay);
        hotelRect.toFront();
        hotelText.toFront();
        hotelRectOver.toFront();
        tourRect.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        tourRectOver.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        aviaRect.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        aviaRectOver.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        autoRect.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        autoRectOver.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        BottomTour.style.zIndex = bottomZIndexDown;
        BottomAuto.style.zIndex = bottomZIndexDown;
        BottomHotel.style.zIndex = bottomZIndexUp;
        BottomAvia.style.zIndex = bottomZIndexDown;
        for (tourrectCounter = 0; tourrectCounter < 4; tourrectCounter++)
            tourRectIsActive[tourrectCounter] = false;
        tourRectIsActive[2] = true;
    });
    autoRectOver.click(function () {
        autoRect.animate({ path: tourPathAnimated, zIndex: 1 }, bottomSwitcherDelay);
        autoRectOver.animate({ path: tourPathAnimated, zIndex: 1 }, bottomSwitcherDelay);
        autoRect.toFront();
        autoText.toFront();
        autoRectOver.toFront();
        aviaRect.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        aviaRectOver.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        hotelRect.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        hotelRectOver.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        tourRect.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        tourRectOver.animate({ path: tourPathNotAnimated }, bottomSwitcherDelay);
        BottomTour.style.zIndex = bottomZIndexDown;
        BottomAuto.style.zIndex = bottomZIndexUp;
        BottomHotel.style.zIndex = bottomZIndexDown;
        BottomAvia.style.zIndex = bottomZIndexDown;
        for (tourrectCounter = 0; tourrectCounter < 4; tourrectCounter++)
            tourRectIsActive[tourrectCounter] = false;
        tourRectIsActive[0] = true;
    });

    var gradRect1 = bottomSwitcher.rect(0, 0, bottomSwitcherWidth, (bottomSwitcherHeight * 0.1));
    var gradRect2 = bottomSwitcher.rect(0, (bottomSwitcherHeight * 0.1), bottomSwitcherWidth, (bottomSwitcherHeight * 0.07));

    gradRect1.attr({ fill: [0, gradLineVioletFirstColor, gradLineVioletSecondColor].join("-"), stroke: 'none' });
    gradRect2.attr({ fill: [0, gradLineYellowFirstColor, gradLineYellowSecondColor].join("-"), stroke: 'none' });
    //------------------------------------------------------------
    //==========Monthes===========================================
    var MonthPaper = new Raphael(Month, '100%', '100%');
    var MonthPaperWidth = $(document.getElementById("month")).width();
    var MonthPaperHeight = $(document.getElementById("month")).height();
    var monthInMonthes;
    var orangePath;
    var monthCounter = 0;
    var monthes = new Array();
    var monthNamesText = new Array();
    for (monthCounter = 0; monthCounter < 12; monthCounter++) {
        monthInMonthes = MonthPaper.path('M' + ((MonthPaperWidth / 12) * monthCounter) + ',' + MonthPaperHeight + 'L' + ((MonthPaperWidth / 12) * (monthCounter + 1)) + ',' + MonthPaperHeight + 'L' + ((MonthPaperWidth / 12) * (monthCounter + 1)) + ',' + (MonthPaperHeight * 0.3) + 'L' + ((MonthPaperWidth / 12) * monthCounter) + ',' + (MonthPaperHeight * 0.3)); // (((MonthPaperWidth / 12) * monthCounter), (MonthPaperHeight * 0.3), (MonthPaperWidth / 12), (MonthPaperHeight * 0.7));
        monthes[monthCounter] = monthInMonthes;
        //monthDivideLine = MonthPaper.path('M' + ((MonthPaperWidth / 12) * monthCounter) + ',' + (MonthPaperHeight * 0.3) + 'L' + (MonthPaperWidth / 12) * (monthCounter) + ',' + (MonthPaperHeight));
        monthInMonthes.attr({ fill: 'white', stroke: lineColor, cursor: "hand" });
    }
    monthes[0].attr({ fill: '#f33', stroke: lineColor });
    orangePath = MonthPaper.path('M' + 0 + ',' + (MonthPaperHeight * 0.3) + 'L' + (MonthPaperWidth / 12) + ',' + (MonthPaperHeight * 0.3) + 'L' + (MonthPaperWidth / 12) + ',' + 0 + 'L' + 0 + ',' + 0);
    orangePath.attr({ fill: [90, '#fd3', 'orange'].join("-"), stroke: 'none' });

    monthNamesText[1] = MonthPaper.text((MonthPaperWidth * 0.5 / 12), (MonthPaperHeight * 0.6), "Январь");
    monthNamesText[1].attr({ 'font-size': (bottomFont * 3) / 4, fill: 'white', cursor: "hand" });
    monthNamesText[2] = MonthPaper.text((MonthPaperWidth * 1.5 / 12), (MonthPaperHeight * 0.6), "Февраль");
    monthNamesText[2].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
    monthNamesText[3] = MonthPaper.text((MonthPaperWidth * 2.5 / 12), (MonthPaperHeight * 0.6), "Март");
    monthNamesText[3].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
    monthNamesText[4] = MonthPaper.text((MonthPaperWidth * 3.5 / 12), (MonthPaperHeight * 0.6), "Апрель");
    monthNamesText[4].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
    monthNamesText[5] = MonthPaper.text((MonthPaperWidth * 4.5 / 12), (MonthPaperHeight * 0.6), "Май");
    monthNamesText[5].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
    monthNamesText[6] = MonthPaper.text((MonthPaperWidth * 5.5 / 12), (MonthPaperHeight * 0.6), "Июнь");
    monthNamesText[6].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
    monthNamesText[7] = MonthPaper.text((MonthPaperWidth * 6.5 / 12), (MonthPaperHeight * 0.6), "Июль");
    monthNamesText[7].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
    monthNamesText[8] = MonthPaper.text((MonthPaperWidth * 7.5 / 12), (MonthPaperHeight * 0.6), "Август");
    monthNamesText[8].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
    monthNamesText[9] = MonthPaper.text((MonthPaperWidth * 8.5 / 12), (MonthPaperHeight * 0.6), "Сентябрь");
    monthNamesText[9].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
    monthNamesText[10] = MonthPaper.text((MonthPaperWidth * 9.5 / 12), (MonthPaperHeight * 0.6), "Октябрь");
    monthNamesText[10].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
    monthNamesText[11] = MonthPaper.text((MonthPaperWidth * 10.5 / 12), (MonthPaperHeight * 0.6), "Ноябрь");
    monthNamesText[11].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
    monthNamesText[12] = MonthPaper.text((MonthPaperWidth * 11.5 / 12), (MonthPaperHeight * 0.6), "Декабрь");
    monthNamesText[12].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });


    //monthDivideLine = MonthPaper.path('M' + 0 + ',' + (MonthPaperHeight * 0.3) + 'L' + MonthPaperWidth + ',' + (MonthPaperHeight * 0.3));
    //monthDivideLine.attr({ fill: lineColor, stroke: lineColor });
    var monthClickX;
    Month.onclick = function (e) {
        e = e || window.event;
        monthClickX = e.clientX;
        monthClickX -= $(document.getElementById("left")).width();
        //alert($(document.getElementById("month")).width());
        monthClickX /= ($(document.getElementById("month")).width() / 12);
        monthClickX = Math.round(monthClickX + 0.5);
        orangePath.animate({ opacity: 0 });
        for (monthCounter = 0; monthCounter < 12; monthCounter++) {
            if (monthCounter == (monthClickX - 1)) {
                monthes[monthClickX - 1].animate({ fill: '#f33', path: 'M' + ((MonthPaperWidth / 12) * (monthClickX - 1)) + ',' + MonthPaperHeight + 'L' + ((MonthPaperWidth / 12) * (monthClickX)) + ',' + MonthPaperHeight + 'L' + ((MonthPaperWidth / 12) * (monthClickX)) + ',' + (MonthPaperHeight * 0.3) + 'L' + ((MonthPaperWidth / 12) * (monthClickX - 1)) + ',' + (MonthPaperHeight * 0.3) }, 300);
                monthNamesText[monthClickX].attr({ fill: 'white' });
                orangePath.attr({ path: 'M' + ((MonthPaperWidth / 12) * (monthClickX - 1)) + ',' + (MonthPaperHeight * 0.3) + 'L' + ((MonthPaperWidth / 12) * (monthClickX)) + ',' + (MonthPaperHeight * 0.3) + 'L' + ((MonthPaperWidth / 12) * (monthClickX)) + ',' + 0 + 'L' + ((MonthPaperWidth / 12) * (monthClickX - 1)) + ',' + 0 });
                orangePath.animate({ opacity: 1 }, 300);
            }
            else {
                monthes[monthCounter].animate({ fill: 'white', path: 'M' + ((MonthPaperWidth / 12) * monthCounter) + ',' + MonthPaperHeight + 'L' + ((MonthPaperWidth / 12) * (monthCounter + 1)) + ',' + MonthPaperHeight + 'L' + ((MonthPaperWidth / 12) * (monthCounter + 1)) + ',' + (MonthPaperHeight * 0.3) + 'L' + ((MonthPaperWidth / 12) * monthCounter) + ',' + (MonthPaperHeight * 0.3) }, 300);
                monthNamesText[monthCounter + 1].attr({ fill: gradLineVioletFirstColor });
            }
        }
        // alert(monthClickX);
    };
    /**/
    //------------------------------------------------------------
    //==========buttons===========================================
    /**/
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Sliders and buttons are going below.
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    var SliderPaper = new Raphael(Left, '100%', '100%');
    var SliderPaperWidth = $(document.getElementById("left")).width();
    var SliderPaperHeight = $(document.getElementById("left")).height();

    var rightDevideLine = SliderPaper.path('M' + SliderPaperWidth + ',' + 0 + 'L' + SliderPaperWidth + ',' + SliderPaperHeight);
    rightDevideLine.attr({ fill: lineColor, stroke: lineColor });

    var firstBlockText = SliderPaper.text((SliderPaperWidth * 0.5), (SliderPaperHeight * 0.02), "Кто едет?");
    firstBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
    //if(Width < Height)
    var firstBlockRectWidth = (SliderPaperWidth * 0.12);
    // else
    //var firstBlockRectWidth = (SliderPaperHeight * 0.044);

    //var firstBlockBetweenRectWidth = (SliderPaperWidth * 0.035);
    var firstBlockBetweenRectWidth;
    if (Width < 1500) {
        firstBlockBetweenRectWidth = (SliderPaperWidth * 0.035 * (1 + ((1500 - Width) / Width)));
        //alert("123");
    }
    else {
        firstBlockBetweenRectWidth = (SliderPaperWidth * 0.035);
    }


    var firstBlockRectOffset = (SliderPaperWidth - (firstBlockRectWidth * 4 + firstBlockBetweenRectWidth * 3)) / 2;
    
    var firstDevideLinePosition = (SliderPaperHeight * 0.05) + firstBlockRectWidth * 1.5;
    //var firstDevideLinePosition = ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var firstDevideLine = SliderPaper.path('M' + 0 + ',' + firstDevideLinePosition + 'L' + SliderPaperWidth + ',' + firstDevideLinePosition);
    firstDevideLine.attr({ fill: lineColor, stroke: lineColor });
    // var firstBlockTextYPosition = (firstDevideLinePosition - (SliderPaperHeight * 0.2));
    //if (Width > 1000)
    //  firstBlockTextYPosition = (firstDevideLinePosition - (SliderPaperHeight * 0.034));
    firstBlockTextYPosition = (SliderPaperHeight * 0.04) + firstBlockRectWidth + (SliderPaperHeight * 0.01);


    jsonController = new JsonController();
    
    function ButtonTag(textXPosition,textYPosition,name,
    		buttonXPosition,buttonYPosition,
    		buttonXSize,buttonYSize,
    		image,setTagName){
    	buttonBlock = this;
    	this.tagName = setTagName;
    	    	
    	this.rectText = SliderPaper.text(textXPosition,textYPosition,name);
    	this.rectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
     	   	
    	this.rect = SliderPaper.rect(
    			buttonXPosition,
    			buttonYPosition,
    			buttonXSize,
    			buttonYSize);
    	this.rect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
    	
    	this.rectImage = SliderPaper.image(
    			image,
    			buttonXPosition,buttonYPosition,
    		    buttonXSize,buttonYSize);
    	   
    	this.rectOver = SliderPaper.rect(
    			buttonXPosition,
    			buttonYPosition,
    			buttonXSize,
    			buttonYSize);
    	this.rectOver.attr({ fill: '#000', stroke: 'none', opacity: 0, cursor: "hand" });
    	this.rectOver.parent = this;
    	
    	this.rectOver.click(function () {
    		this.parent.click();
        });
    	this.click = function(){
    		this.parent.click(this);
    	};
    	this.pressed = function(){
    		this.rect.animate({ fill: [270, '#fc0', '#fc6'].join("-"), opacity: 0.7 }, 0);
    	};
    	this.unPressed = function(){
    		this.rect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
    	};
    }
    function ButtonBlock(){
    	this.buttons = new Array();
    	this.add = function(newButton){
    		this.buttons.push(newButton);
    		newButton.parent = this;
    		newButton.idButtonBlock = this.buttons.length;
    	};
    	this.click = function(pressedButton){  		
    		for (button in this.buttons){
    			if (this.buttons[button].idButtonBlock == pressedButton.idButtonBlock){
    				this.buttons[button].pressed();
    			}else{
    				this.buttons[button].unPressed();
    			};
    		};
    		jsonController.buttonClick(pressedButton);
    	};
    };
    
    var selfButton = new ButtonTag(
    	(firstBlockRectOffset + firstBlockRectWidth * 0.5),firstBlockTextYPosition,"Один",
    	(firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth) * 0),(SliderPaperHeight * 0.04),
    	firstBlockRectWidth,firstBlockRectWidth,
    	"resources/img/self.png",0
    	);
    var coupleButton = new ButtonTag(
    		(firstBlockRectOffset + firstBlockBetweenRectWidth + (firstBlockRectWidth) * 1.5),firstBlockTextYPosition,"Вдвоем",
    		(firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth) * 1),(SliderPaperHeight * 0.04),
        	firstBlockRectWidth,firstBlockRectWidth,
        	"resources/img/self.png",1
        	);
    var familyButton = new ButtonTag(
    		(firstBlockRectOffset + firstBlockBetweenRectWidth * 2 + (firstBlockRectWidth) * 2.5),firstBlockTextYPosition,"Семья",
    		(firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth) * 2),(SliderPaperHeight * 0.04),
	    	firstBlockRectWidth,firstBlockRectWidth,
	    	"resources/img/self.png",2
	    	);
    var friendsButton = new ButtonTag(
    		(firstBlockRectOffset + firstBlockBetweenRectWidth * 3 + (firstBlockRectWidth) * 3.5),firstBlockTextYPosition,"Друзья",
    		(firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth) * 3),(SliderPaperHeight * 0.04),
        	firstBlockRectWidth,firstBlockRectWidth,
        	"resources/img/self.png",3
        	);

    var firstButtonsBlock = new ButtonBlock();
    firstButtonsBlock.add(selfButton);
    firstButtonsBlock.add(coupleButton);
    firstButtonsBlock.add(familyButton);
    firstButtonsBlock.add(friendsButton);
    selfButton.click();
    
    var secondDevideLinePosition = firstDevideLinePosition * 2;
    var secondDevideLine = SliderPaper.path('M' + 0 + ',' + secondDevideLinePosition + 'L' + SliderPaperWidth + ',' + secondDevideLinePosition);
    secondDevideLine.attr({ fill: lineColor, stroke: lineColor });
    var secondBlockText = SliderPaper.text((SliderPaperWidth * 0.49), firstDevideLinePosition + (SliderPaperHeight * 0.02), "Что делать?");
    secondBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
    //alert(Height);
    var secondBlockRectWidth = firstBlockRectWidth;
    var secondBlockBetweenRectWidth = firstBlockBetweenRectWidth;
    var secondBlockRectOffset = (SliderPaperWidth - (firstBlockRectWidth * 5 + firstBlockBetweenRectWidth * 4)) / 2;

    var secondBlockTextYPosition = (SliderPaperHeight * 0.05 + firstDevideLinePosition) + secondBlockRectWidth;
    
    var tanButton = new ButtonTag(
    		(secondBlockRectOffset + secondBlockBetweenRectWidth * 0 + (secondBlockRectWidth) * 0 + secondBlockRectWidth * 0.5),secondBlockTextYPosition,"Загарать",
    		(secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 0),(SliderPaperHeight * 0.04 + firstDevideLinePosition),
    		secondBlockRectWidth,secondBlockRectWidth,
        	"resources/img/tan.png",4
        	);
    var skiButton = new ButtonTag(
    		(secondBlockRectOffset + secondBlockBetweenRectWidth * 1 + (secondBlockRectWidth) * 1 + secondBlockRectWidth * 0.5),secondBlockTextYPosition,"Кататься",
    		(secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 1),(SliderPaperHeight * 0.04 + firstDevideLinePosition),
    		secondBlockRectWidth,secondBlockRectWidth,
        	"resources/img/ski.png",5
        	);
    var watchButton = new ButtonTag(
    		(secondBlockRectOffset + secondBlockBetweenRectWidth * 2 + (secondBlockRectWidth) * 2 + secondBlockRectWidth * 0.5),secondBlockTextYPosition,"Смотреть",
    		(secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 2),(SliderPaperHeight * 0.04 + firstDevideLinePosition),
    		secondBlockRectWidth,secondBlockRectWidth,
        	"resources/img/watch.png",6
        	);
    var shoppingButton = new ButtonTag(
    		(secondBlockRectOffset + secondBlockBetweenRectWidth * 3 + (secondBlockRectWidth) * 3 + secondBlockRectWidth * 0.5),secondBlockTextYPosition,"Шоппинг",
    		(secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 3),(SliderPaperHeight * 0.04 + firstDevideLinePosition),
    		secondBlockRectWidth,secondBlockRectWidth,
        	"resources/img/shopping.png",7
        	);
    var cruiseButton = new ButtonTag(
    		(secondBlockRectOffset + secondBlockBetweenRectWidth * 4 + (secondBlockRectWidth) * 4 + secondBlockRectWidth * 0.5),secondBlockTextYPosition,"Круиз",
    		(secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 4),(SliderPaperHeight * 0.04 + firstDevideLinePosition),
    		secondBlockRectWidth,secondBlockRectWidth,
        	"resources/img/cruise.png",8
        	);
    
    var secondButtonsBlock = new ButtonBlock();
    secondButtonsBlock.add(tanButton);
    secondButtonsBlock.add(skiButton);
    secondButtonsBlock.add(watchButton);
    secondButtonsBlock.add(shoppingButton);
    secondButtonsBlock.add(cruiseButton);
    tanButton.click();

    //==============================================================================
    //two handle sliders------------------------------------------------------------
    var thirdDevideLinePosition = firstDevideLinePosition * 2 + (SliderPaperHeight - firstDevideLinePosition * 2) * 0.13; // +0.7 * ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var thirdDevideLine = SliderPaper.path('M' + (SliderPaperWidth * 0.1) + ',' + thirdDevideLinePosition + 'L' + (SliderPaperWidth * 0.9) + ',' + thirdDevideLinePosition);
    thirdDevideLine.attr({ fill: lineColor, stroke: lineColor });
    
    var forthDevideLinePosition = firstDevideLinePosition * 2 + (SliderPaperHeight - firstDevideLinePosition * 2) * 0.26; //firstDevideLinePosition * 2 + 1.35 * ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var forthDevideLine = SliderPaper.path('M' + (SliderPaperWidth * 0.1) + ',' + forthDevideLinePosition + 'L' + (SliderPaperWidth * 0.9) + ',' + forthDevideLinePosition);
    forthDevideLine.attr({ fill: lineColor, stroke: lineColor });
    
    var fifthDevideLinePosition = firstDevideLinePosition * 2 + (SliderPaperHeight - firstDevideLinePosition * 2) * 0.35; //firstDevideLinePosition * 2 + 1.75 * ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var fifthDevideLine = SliderPaper.path('M' + (SliderPaperWidth * 0.1) + ',' + fifthDevideLinePosition + 'L' + (SliderPaperWidth * 0.9) + ',' + fifthDevideLinePosition);
    fifthDevideLine.attr({ fill: lineColor, stroke: lineColor, opacity: 0 });
    
    var sixthDevideLinePosition = firstDevideLinePosition * 2 + (SliderPaperHeight - firstDevideLinePosition * 2) * 0.44; //firstDevideLinePosition * 2 + 2.15 * ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var sixthDevideLine = SliderPaper.path('M' + (SliderPaperWidth * 0.1) + ',' + sixthDevideLinePosition + 'L' + (SliderPaperWidth * 0.9) + ',' + sixthDevideLinePosition);
    sixthDevideLine.attr({ fill: '#f00', stroke: lineColor, opacity: 0 });
    
    var seventhDevideLinePosition = firstDevideLinePosition * 2 + (SliderPaperHeight - firstDevideLinePosition * 2) * 0.53; //firstDevideLinePosition * 2 + 2.55 * ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var seventhDevideLine = SliderPaper.path('M' + (SliderPaperWidth * 0.1) + ',' + seventhDevideLinePosition + 'L' + (SliderPaperWidth * 0.9) + ',' + seventhDevideLinePosition);
    seventhDevideLine.attr({ fill: '#f00', stroke: lineColor, opacity: 0 });
    
    var eightsDevideLinePosition = firstDevideLinePosition * 2 + (SliderPaperHeight - firstDevideLinePosition * 2) * 0.62; //firstDevideLinePosition * 2 + 2.95 * ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var eightsDevideLine = SliderPaper.path('M' + (SliderPaperWidth * 0.1) + ',' + eightsDevideLinePosition + 'L' + (SliderPaperWidth * 0.9) + ',' + eightsDevideLinePosition);
    eightsDevideLine.attr({ fill: '#f00', stroke: lineColor, opacity: 0 });
    
    var ninthDevideLinePosition = firstDevideLinePosition * 2 + (SliderPaperHeight - firstDevideLinePosition * 2) * 0.71; //firstDevideLinePosition * 2 + 3.35 * ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var ninthDevideLine = SliderPaper.path('M' + (SliderPaperWidth * 0.1) + ',' + ninthDevideLinePosition + 'L' + (SliderPaperWidth * 0.9) + ',' + ninthDevideLinePosition);
    ninthDevideLine.attr({ fill: '#f00', stroke: lineColor, opacity: 0 });
    
    var tenthDevideLinePosition = firstDevideLinePosition * 2 + (SliderPaperHeight - firstDevideLinePosition * 2) * 0.8; //firstDevideLinePosition * 2 + 3.75 * ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var tenthDevideLine = SliderPaper.path('M' + (SliderPaperWidth * 0.1) + ',' + tenthDevideLinePosition + 'L' + (SliderPaperWidth * 0.9) + ',' + tenthDevideLinePosition);
    tenthDevideLine.attr({ fill: '#f00', stroke: lineColor, opacity: 0 });
    
    var eleventhDevideLinePosition = firstDevideLinePosition * 2 + (SliderPaperHeight - firstDevideLinePosition * 2) * 0.89; //firstDevideLinePosition * 2 + 4.15 * ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var eleventhDevideLine = SliderPaper.path('M' + (SliderPaperWidth * 0.1) + ',' + eleventhDevideLinePosition + 'L' + (SliderPaperWidth * 0.9) + ',' + eleventhDevideLinePosition);
    eleventhDevideLine.attr({ fill: '#f00', stroke: lineColor, opacity: 0 });
   
    //=====================SLIDER CLASS===============================
    function SliderInt(YPosition,RightHandlerExist,
    		LeftLimit,RightLimit,
    		UnitsOfMeasure,UnitsOfMeasurePrefixPlus,
    		Name){
    	this.sliderYPosition = YPosition;
    	this.sliderLeftLimit = LeftLimit;
    	this.sliderRightLimit = RightLimit;
    	this.sliderUnitsOfMeasure = UnitsOfMeasure;
    	this.sliderUnitsOfMeasurePrefixPlus = UnitsOfMeasurePrefixPlus;
    	this.name = Name;
    	this.sliderRightHandlerExist = RightHandlerExist;
    	
    	this.sliderHeight = firstBlockRectWidth * 0.15;
    	this.sliderHandlerXSize = this.sliderHeight + firstBlockRectWidth * 0.02;
    	this.sliderHandlerYSize = this.sliderHeight + firstBlockRectWidth * 0.02;
    	this.sliderLeftPosition = (SliderPaperWidth * 0.2);
    	
    	this.sliderBackRect = SliderPaper.rect(
    			(this.sliderLeftPosition - this.sliderHandlerYSize),
    			(this.sliderYPosition + firstBlockRectWidth * 0.02),
    			(SliderPaperWidth - (2 * this.sliderLeftPosition) + 3 * this.sliderHandlerYSize),
    			(this.sliderHeight - firstBlockRectWidth * 0.04))
    				.attr({ fill: '#ddd', stroke: "none", 'rx': '20' });
    	//ЧТО ЭТО ТАКОЕ?
    	this.sliderLeftBackCircle = SliderPaper.circle(
    			(this.sliderLeftPosition - this.sliderHandlerYSize),
    			((this.sliderYPosition + firstBlockRectWidth * 0.02) + (this.sliderHeight - firstBlockRectWidth * 0.04) / 2),
    			(this.sliderHeight - firstBlockRectWidth * 0.04) / 2)
    				.attr({ fill: '#ddd', stroke: "none" });
    	this.sliderRightBackCircle = SliderPaper.circle(
    			((this.sliderLeftPosition - this.sliderHandlerYSize) + (SliderPaperWidth - (2 * this.sliderLeftPosition) + 3 * this.sliderHandlerYSize)),
    			((this.sliderYPosition + firstBlockRectWidth * 0.02) + (this.sliderHeight - firstBlockRectWidth * 0.04) / 2),
    			(this.sliderHeight - firstBlockRectWidth * 0.04) / 2)
    				.attr({ fill: '#ddd', stroke: "none" });
    	
    	if(this.sliderRightHandlerExist)
    		this.sliderCenterRect = SliderPaper.rect(
    			(this.sliderLeftPosition + this.sliderHandlerYSize),
    			(this.sliderYPosition + firstBlockRectWidth * 0.02),
    			(SliderPaperWidth - (2 * this.sliderLeftPosition) - this.sliderHandlerYSize),
    			(this.sliderHeight - firstBlockRectWidth * 0.04))
    			.attr({fill: [0, gradLineYellowFirstColor, gradLineYellowSecondColor].join("-"), stroke: "none" });
    	
    	if(this.sliderRightHandlerExist)
    		this.sliderLeftHandler = SliderPaper.rect(
    			this.sliderLeftPosition,
    			(this.sliderYPosition - this.sliderHandlerYSize * 0.1),
    			this.sliderHandlerXSize * 1.1, this.sliderHandlerYSize * 1.2)
    				.attr({ fill: '#fff', stroke: "#ddd", cursor: "hand", 'stroke-linejoin': "round", rx: "5", ry: "5" });
    	else
    		this.sliderLeftHandler = SliderPaper.rect(
    				(SliderPaperWidth * 0.5),
        			(this.sliderYPosition - this.sliderHandlerYSize * 0.1),
        			this.sliderHandlerXSize * 1.1, this.sliderHandlerYSize * 1.2)
        				.attr({ fill: '#fff', stroke: "#ddd", cursor: "hand", 'stroke-linejoin': "round", rx: "5", ry: "5" });
    		
    	if(this.sliderRightHandlerExist)
    		this.sliderRightHandler = SliderPaper.rect(
        		(SliderPaperWidth - this.sliderLeftPosition),
        		(this.sliderYPosition - this.sliderHandlerYSize * 0.1),
        		this.sliderHandlerXSize * 1.1, this.sliderHandlerYSize * 1.2)
        			.attr({ fill: '#fff', stroke: "#ddd", cursor: "hand", rx: "5", ry: "5" });
    	else
    		this.sliderRightHandler = SliderPaper.rect(
            		(SliderPaperWidth - this.sliderLeftPosition),
            		(this.sliderYPosition - this.sliderHandlerYSize * 0.1),
            		this.sliderHandlerXSize * 1.1, this.sliderHandlerYSize * 1.2)
            			.attr({ fill: '#fff', stroke: "#ddd", rx: "5", ry: "5", opacity: 0 });
    		
    	this.sliderLeftHandler.parent = this;
    	if(this.sliderRightHandlerExist)
    	this.sliderRightHandler.parent = this;
    	
    	// start, move, and up are the drag functions
    	this.sliderLeftHandlerStart = function () {
            // storing original coordinates
            this.ox = this.attr("x");
            //  this.attr({ opacity: 1 });
        },
        this.sliderLeftHandlerMove = function (dx, dy) {
            // move will be called with dx and dy
            if ((dx > 0) && (this.attr("x") < ((this.parent.sliderRightHandler.attr("x") - this.parent.sliderHandlerXSize)))) {
                if ((this.ox + dx) < ((this.parent.sliderRightHandler.attr("x") - this.parent.sliderHandlerXSize))) {
                    this.attr({ x: this.ox + dx });
                    this.parent.sliderCenterRect.attr({ x: (this.ox + dx), width: (this.parent.sliderRightHandler.attr("x") - this.parent.sliderCenterRect.attr("x") - this.parent.sliderHandlerXSize / 5) });
                }
                else {
                    this.attr({ x: (this.parent.sliderRightHandler.attr("x") - this.parent.sliderHandlerXSize) });
                    this.parent.sliderCenterRect.attr({ x: (this.parent.sliderRightHandler.attr("x") - this.parent.sliderHandlerXSize), width: (this.parent.sliderRightHandler.attr("x") - this.parent.sliderCenterRect.attr("x") - this.parent.sliderHandlerXSize / 5) });
                }
            }
            if ((dx < 0) && (this.attr("x") > (this.parent.sliderLeftPosition + this.parent.sliderHandlerXSize)))
                if ((this.ox + dx) > (this.attr("x") > (this.parent.sliderLeftPosition + this.parent.sliderHandlerXSize))) {
                    this.attr({ x: this.ox + dx });
                    if(this.sliderRightHandlerExist)
                    	this.parent.sliderCenterRect.attr({ x: (this.ox + dx), width: (this.parent.sliderRightHandler.attr("x") - this.parent.sliderCenterRect.attr("x") - this.parent.sliderHandlerXSize / 5) });
                }
                else {
                    this.attr({ x: (this.attr("x") > (this.parent.sliderLeftPosition - this.parent.sliderHandlerXSize)) });
                    if(this.sliderRightHandlerExist)
                    	this.parent.sliderCenterRect.attr({ x: (this.attr("x") > (this.parent.sliderLeftPosition + this.parent.sliderHandlerXSize)), width: (this.parent.sliderRightHandler.attr("x") - this.parent.sliderCenterRect.attr("x") - this.parent.sliderHandlerXSize / 5) });
                }
            //warp slider if its out of bounds
            if (this.attr("x") < (this.parent.sliderLeftPosition)) {
                this.attr({ x: (this.parent.sliderLeftPosition) });
            }

            this.parent.sliderCenterRect.attr({ x: (this.attr("x")), width: (this.parent.sliderRightHandler.attr("x") - this.parent.sliderCenterRect.attr("x")) });

            this.parent.leftBlockValue = Math.round(
            		this.parent.sliderLeftLimit +
            		(
        				(this.parent.sliderLeftHandler.attr("x") - (this.parent.sliderLeftPosition + this.parent.sliderHandlerYSize))
        				* (this.parent.sliderRightLimit - this.parent.sliderLeftLimit)
            		)
            		/ (
        				(SliderPaperWidth - (2 * this.parent.sliderLeftPosition) - this.parent.sliderHandlerYSize)
            		)
            	);

            this.parent.leftBlockText.attr({ text: this.parent.valueAsString(this.parent.leftBlockValue)});
        },
        this.sliderLeftHandlerUp = function () {
        	jsonController.sliderChange(this.parent);
        };
    	
        this.sliderRightHandlerStart = function () {
            // storing original coordinates
            this.ox = this.attr("x");
            // this.attr({ opacity: 1 });
        },
        this.sliderRightHandlerMove = function (dx, dy) {
            // move will be called with dx and dy
            if ((dx > 0) && (this.attr("x") < (SliderPaperWidth - (this.parent.sliderLeftPosition + this.parent.sliderHandlerXSize))))
                if ((this.ox + dx) < ((SliderPaperWidth - (this.parent.sliderPosition + this.parent.sliderHandlerXSize)))) {
                    this.attr({ x: this.ox + dx });
                }
                else {
                    this.attr({ x: (SliderPaperWidth - (this.parent.sliderLeftPosition)) });
                }

            if ((dx < 0) && (this.attr("x") > (this.parent.sliderLeftHandler.attr("x") + this.parent.sliderHandlerXSize))) {
                if ((this.ox + dx) > (this.parent.sliderLeftHandler.attr("x") + this.parent.sliderHandlerXSize)) {
                    this.attr({ x: this.ox + dx });
                }
                else {
                    this.attr({ x: (this.parent.sliderLeftHandler.attr("x") + this.parent.sliderHandlerXSize) });
                }
            }
            this.parent.sliderCenterRect.attr({ width: (this.parent.sliderRightHandler.attr("x") - this.parent.sliderCenterRect.attr("x")) });
            this.parent.rightBlockValue = Math.round(
            		this.parent.sliderLeftLimit +
            		(
        				(this.parent.sliderRightHandler.attr("x") - (this.parent.sliderLeftPosition + this.parent.sliderHandlerYSize))
        				* (this.parent.sliderRightLimit - this.parent.sliderLeftLimit)
            		)
            		/ (
        				(SliderPaperWidth - (2 * this.parent.sliderLeftPosition) - this.parent.sliderHandlerYSize)
            		)
            	);
            this.parent.rightBlockText.attr({ text: this.parent.valueAsString(this.parent.rightBlockValue)});
        },
        this.sliderRightHandlerUp = function () {
        	jsonController.sliderChange(this.parent);
        };
     // rstart and rmove are the resize functions;
        this.sliderLeftHandler.drag(
        		this.sliderLeftHandlerMove,
        		this.sliderLeftHandlerStart,
        		this.sliderLeftHandlerUp);
        if(this.sliderRightHandlerExist)
        this.sliderRightHandler.drag(
        		this.sliderRightHandlerMove,
        		this.sliderRightHandlerStart,
        		this.sliderRightHandlerUp);
        
        this.valueAsString = function(value){
        	if (value > 0 && this.sliderUnitsOfMeasurePrefixPlus)
                return('+' + value + this.sliderUnitsOfMeasure);
            else
            	return(value + this.sliderUnitsOfMeasure);
        };
        //text
        this.blockText = SliderPaper.text(
        		(SliderPaperWidth * 0.53),
        		this.sliderYPosition - (SliderPaperHeight * 0.04),
        		this.name
        		).attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
        if(this.sliderRightHandlerExist)
        this.leftBlockText = SliderPaper.text(
        		(SliderPaperWidth * 0.2),
        		this.sliderYPosition - (SliderPaperHeight * 0.02),
        		this.valueAsString(this.sliderLeftLimit)
        	).attr({ 'font-size': bottomFont, fill: '#bbb' });
        if(this.sliderRightHandlerExist)
        this.rightBlockText = SliderPaper.text(
        		(SliderPaperWidth * 0.81),
        		this.sliderYPosition - (SliderPaperHeight * 0.02),
        		this.valueAsString(this.sliderRightLimit)
        	).attr({ 'font-size': bottomFont, fill: '#bbb' });
    }
    
    temperatureSlider = new SliderInt(
    		secondDevideLinePosition + (SliderPaperHeight * 0.06),true,
    		-35,35,
    		"C",true,
    		"Температура"
    	);
    timeSlider = new SliderInt(
    		thirdDevideLinePosition + (SliderPaperHeight * 0.065),true,
    		0,24,
    		"Ч",false,
    		"Время перелета"
    	);
    livingSlider = new SliderInt(
    		forthDevideLinePosition + (SliderPaperHeight * 0.06),true,
    		0,3000,
    		"$",false,
    		"Проживание"
    	);
    foodSlider = new SliderInt(
    		fifthDevideLinePosition + (SliderPaperHeight * 0.06),true,
    		0,300,
    		"$",false,
    		"Еда"
    	);
    alcoholSlider = new SliderInt(
    		sixthDevideLinePosition + (SliderPaperHeight * 0.06),true,
    		0,30,
    		"$",false,
    		"Алкоголь"
    	);
    moodSlider = new SliderInt(
    			seventhDevideLinePosition + (SliderPaperHeight * 0.06),false,
        		0,10,
        		"HZ",false,
        		"Настроение"
        	);
    moodSlider = new SliderInt(
    		eightsDevideLinePosition + (SliderPaperHeight * 0.06),false,
    		0,10,
    		"HZ",false,
    		"Количество туристов"
    	);
    moodSlider = new SliderInt(
    		ninthDevideLinePosition + (SliderPaperHeight * 0.06),false,
    		0,10,
    		"HZ",false,
    		"Безопасность"
    	);
    moodSlider = new SliderInt(
    		tenthDevideLinePosition + (SliderPaperHeight * 0.06),false,
    		0,10,
    		"HZ",false,
    		"Секс"
    	);
    //=========================JSON Controller===========================
    function JsonController(){
	    this.buttonClick = function(button){
	    	function objFactory(){
	    		  return {
	    		    constructor : objFactory
	    		  };
	    		};
	    	$.post("/project/selecttag", 
	    		{
	    		id : 1
	    		}
	    	  ,
	    	  jsonController.cityRequest(button),
	    	  "json");
	    	
	    	
	    	//jsonController.cityRequest(button);
	    };
	    this.sliderChange = function(slider){
	    	jsonController.cityRequest(slider);
	    };
	    this.cityRequest = function(input){
	
	    	$.getJSON("/project/getcities",
	            function(data) {
	                // do something with the data
	                alert(data[0].name);
	          });
	    };
	};
    
    //----------------------===========================-----------
    //----------------------===========================-----------
    //----------------------===========================-----------
    //------------------two little bottons in the bottom----------
    var russianButtonActive = true;
    var visaButtonActive = true;
    var russianRect = SliderPaper.rect((firstBlockRectOffset) * 1.3 + 55, eleventhDevideLinePosition + (SliderPaperHeight * 0.02), firstBlockRectWidth / 4, firstBlockRectWidth / 4);
    if (Width > 1000)
        var ninthBlockRussianText = SliderPaper.text((firstBlockRectOffset) * 1.3, eleventhDevideLinePosition + (SliderPaperHeight * 0.027), "Русский язык");
    else
        var ninthBlockRussianText = SliderPaper.text((firstBlockRectOffset) * 1.4, eleventhDevideLinePosition + (SliderPaperHeight * 0.023), "Русский язык");
    ninthBlockRussianText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
    //russianRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
    russianRect.attr({ fill: [270, '#fc0', '#fc6'].join("-"), stroke: '#bbb', cursor: 'hand' });

    var visaRect = SliderPaper.rect((SliderPaperWidth - firstBlockRectOffset), eleventhDevideLinePosition + (SliderPaperHeight * 0.02), firstBlockRectWidth / 4, firstBlockRectWidth / 4);
    if (Width > 1000)
        var ninthBlockVisaText = SliderPaper.text((SliderPaperWidth - firstBlockRectOffset) - 40, eleventhDevideLinePosition + (SliderPaperHeight * 0.027), "Без визы");
    else
        var ninthBlockVisaText = SliderPaper.text((SliderPaperWidth - firstBlockRectOffset) - 30, eleventhDevideLinePosition + (SliderPaperHeight * 0.023), "Без визы");
    ninthBlockVisaText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
    //visaRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
    visaRect.attr({ fill: [270, '#fc0', '#fc6'].join("-"), stroke: '#bbb', cursor: 'hand' });

    russianRect.click(function () {
        if (russianButtonActive == false) {
            this.attr({ fill: [270, '#fc0', '#fc6'].join("-"), stroke: '#bbb' });
            russianButtonActive = true;
        }
        else {
            this.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
            russianButtonActive = false;
        }
    });

    visaRect.click(function () {
        if (russianButtonActive == false) {
            this.attr({ fill: [270, '#fc0', '#fc6'].join("-"), stroke: '#bbb' });
            russianButtonActive = true;
        }
        else {
            this.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
            russianButtonActive = false;
        }
    });

    //var magicKey = true; //magic key for fixing magic bug.

    //==================================================================================================================
    //Rsize
    //==================================================================================================================
    var tourPathTextDelta = 80; //global, due to magic recall of resize function in chrome and opera.
    var resizedTourPathTextDelta = true;
    $(window).resize(function (e) {
        var newWidth = $(document.getElementById("div_container")).width();
        var newHeight = $(document.getElementById("div_container")).height();
        //-------------
        var newHeadHeight = $(Head).height();
        oldSliderPaperWidth = SliderPaperWidth;
        oldSliderPaperHeight = SliderPaperHeight;
        SliderPaperHeight = $(document.getElementById("left")).height();
        SliderPaperWidth = $(document.getElementById("left")).width();
        //-------------
        firstBlockRectOffset *= newWidth / Width;
        secondBlockRectOffset *= newWidth / Width;

        if (newHeight < 700) {
            firstBlockRectWidth = 33;
            secondBlockRectWidth = 33;
        }
        else
            if (Height < 700) {
                firstBlockRectWidth = (SliderPaperWidth * 0.12);
                secondBlockRectWidth = (SliderPaperWidth * 0.12);
            }
            else {
                if (newWidth < 1300) {
                    firstBlockRectWidth = 43;
                }
                else
                    if (Width < 1300) {
                        firstBlockRectWidth *= newWidth / 1300;
                    }
                    else {
                        firstBlockRectWidth *= newWidth / Width;
                    }

                if (newWidth < 1200) {
                    secondBlockRectWidth = 37;
                }
                else
                    if (Width < 1200) {
                        secondBlockRectWidth *= newWidth / 1300;
                    }
                    else {
                        secondBlockRectWidth = firstBlockRectWidth;
                    }
            }
        //-------------
        oldMonthPaperWidth = MonthPaperWidth;
        oldMonthPaperHeight = MonthPaperHeight;
        MonthPaperWidth *= newWidth / Width;
        MonthPaperHeight *= newHeight / Height;
        //-------------
        firstDevideLinePosition *= newHeight / Height;
        secondDevideLinePosition *= newHeight / Height;
        thirdDevideLinePosition *= newHeight / Height;
        forthDevideLinePosition *= newHeight / Height;
        fifthDevideLinePosition *= newHeight / Height;
        sixthDevideLinePosition *= newHeight / Height;
        seventhDevideLinePosition *= newHeight / Height;
        eightsDevideLinePosition *= newHeight / Height;
        ninthDevideLinePosition *= newHeight / Height;
        tenthDevideLinePosition *= newHeight / Height;
        eleventhDevideLinePosition *= newHeight / Height;
        //-------------
        bottomSwitcherHeight *= newHeight / Height;
        //-------------
        if (newWidth < 900)
            newWidth = 900;
        if (newHeight < 600)
            newHeight = 600;

        if (newWidth < 2500)
            bottomFont = 18;
        if (newWidth < 2000)
            bottomFont = 16;
        if (newWidth < 1500)
            bottomFont = 14;
        if (newWidth < 1100)
            bottomFont = 12;



        HeadPaper.setSize(newWidth, newHeight * 0.07);
        HeadPaper.forEach(function (el) { el.scale(1, newHeight / Height, 0, 0); });
        headGradRect1.scale(newWidth / Width, 1, 0, 0);
        headGradRect2.scale(newWidth / Width, 1, 0, 0);

        SliderPaper.setSize(newWidth * 0.31, newHeight * 0.93);
        SliderPaper.forEach(function (el) {
            if (!((el == selfRect) || (el == coupleRect) || (el == friendsRect) || (el == familyRect) ||
            (el == selfRectOver) || (el == coupleRectOver) || (el == friendsRectOver) || (el == familyRectOver) ||
            (el == selfRectImage) || (el == coupleRectImage) || (el == friendsRectImage) || (el == familyRectImage) ||

            (el == tanRect) || (el == skiRect) || (el == watchRect) || (el == shoppingRect) || (el == cruiseRect) ||
            (el == tanRectOver) || (el == skiRectOver) || (el == watchRectOver) || (el == shoppingRectOver) || (el == cruiseRectOver) ||
            (el == tanRectImage) || (el == skiRectImage) || (el == watchRectImage) || (el == shoppingRectImage) || (el == cruiseRectImage)
            ))
                el.scale((newWidth / Width), newHeight / Height, 0, 0);
        });
        //---------------------------------------------------------------------------
        //alert((bottomSwitcherHeight * 0.7));
        if (resizedTourPathTextDelta == true) {
            tourText.remove();
            tourText = bottomSwitcher.text(tourPathTextPosition, (bottomSwitcherHeight * 0.7), "Туры");
            tourText.attr({ 'font-size': bottomFont });
            aviaText.remove();
            aviaText = bottomSwitcher.text((tourPathTextPosition) + tourPathTextDelta * 2, (bottomSwitcherHeight * 0.7), "Авиабилеты");
            aviaText.attr({ 'font-size': bottomFont });
            hotelText.remove();
            hotelText = bottomSwitcher.text((tourPathTextPosition) + tourPathTextDelta * 4, (bottomSwitcherHeight * 0.7), "Отели");
            hotelText.attr({ 'font-size': bottomFont });
            autoText.remove();
            autoText = bottomSwitcher.text((tourPathTextPosition) + tourPathTextDelta * 6, (bottomSwitcherHeight * 0.7), "Авто");
            autoText.attr({ 'font-size': bottomFont });
            resizedTourPathTextDelta = false;
        } else {
            resizedTourPathTextDelta = true;
        }
        if (newWidth < 1250) {
         //   tourText.translate(-15, 0);
            aviaText.translate(-23, 0);
            autoText.translate(-53, 0);
            hotelText.translate(-41, 0);
        }
        else {
            tourText.translate(6, 0);
            aviaText.translate(5, 0);
            autoText.translate(6, 0);
            hotelText.translate(4, 0);
        }
        /**/
        if ((newWidth < 1250) && (Width > 1250)) {
            tourPathNotAnimated = littleTourPathNotAnimated;
            tourPathAnimated = littleTourPathAnimated;
            tourRect.animate({ path: littleTourPathNotAnimated }, 1);
            aviaRect.animate({ path: littleTourPathNotAnimated }, 1);
            autoRect.animate({ path: littleTourPathNotAnimated }, 1);
            hotelRect.animate({ path: littleTourPathNotAnimated }, 1);
            aviaRect.translate(-37, 0);
            autoRect.translate(-111, 0);
            hotelRect.translate(-74, 0);
            tourRectOver.animate({ path: littleTourPathNotAnimated }, 1);
            aviaRectOver.animate({ path: littleTourPathNotAnimated }, 1);
            autoRectOver.animate({ path: littleTourPathNotAnimated }, 1);
            hotelRectOver.animate({ path: littleTourPathNotAnimated }, 1);
            aviaRectOver.translate(-37, 0);
            autoRectOver.translate(-111, 0);
            hotelRectOver.translate(-74, 0);
        }
        else if ((Width < 1250) && (newWidth > 1250)) {
            tourPathNotAnimated = largeTourPathNotAnimated;
            tourPathAnimated = largeTourPathAnimated;
            tourRect.animate({ path: largeTourPathNotAnimated }, 1);
            aviaRect.animate({ path: largeTourPathNotAnimated }, 1);
            autoRect.animate({ path: largeTourPathNotAnimated }, 1);
            hotelRect.animate({ path: largeTourPathNotAnimated }, 1);
            aviaRect.translate(37, 0);
            autoRect.translate(111, 0);
            hotelRect.translate(74, 0);
            tourRectOver.animate({ path: largeTourPathNotAnimated }, 1);
            aviaRectOver.animate({ path: largeTourPathNotAnimated }, 1);
            autoRectOver.animate({ path: largeTourPathNotAnimated }, 1);
            hotelRectOver.animate({ path: largeTourPathNotAnimated }, 1);
            aviaRectOver.translate(37, 0);
            autoRectOver.translate(111, 0);
            hotelRectOver.translate(74, 0);
        }
        tourRectOver.toFront();
        aviaRectOver.toFront();
        hotelRectOver.toFront();
        autoRectOver.toFront();

        if (tourRectIsActive[0] == true) {
            tourRect.animate({ path: tourPathAnimated }, 20);
            tourRectOver.animate({ path: tourPathAnimated }, 20);
            tourRectOver.toFront();
        }
        if (tourRectIsActive[1] == true) {
            aviaRect.animate({ path: tourPathAnimated }, 20);
            aviaRectOver.animate({ path: tourPathAnimated }, 20);
            aviaRectOver.toFront();
        }
        if (tourRectIsActive[2] == true) {
            hotelRect.animate({ path: tourPathAnimated }, 20);
            hotelRectOver.animate({ path: tourPathAnimated }, 20);
            hotelRectOver.toFront();
        }
        if (tourRectIsActive[3] == true) {
            autoRect.animate({ path: tourPathAnimated }, 20);
            autoRectOver.animate({ path: tourPathAnimated }, 20);
            autoRectOver.toFront();
        }

        var newBottomSwitcherWidth = bottomSwitcherWidth * (newWidth / Width);
        bottomSwitcher.setSize(newWidth * 0.7, newHeight * 0.07);

        bottomSwitcher.forEach(function (el) {
            if (!(
        (el == tourRect) || (el == aviaRect) || (el == hotelRect) || (el == autoRect) ||
        (el == tourRectOver) || (el == aviaRectOver) || (el == hotelRectOver) || (el == autoRectOver) ||
        (el == tourText) || (el == aviaText) || (el == hotelText) || (el == autoText)
        )) {
                el.scale(newWidth / Width, newHeight / Height, 0, 0);
            }
            else {
                if (!((el == tourText) || (el == aviaText) || (el == hotelText) || (el == autoText)))
                    el.scale(1, newHeight / Height, 0, 0);
            }
        });
        Height = newHeight;
        Width = newWidth;
        bottomSwitcherWidth = newBottomSwitcherWidth;

        //------------------------
        //Fonts
        //------------------------
        thirdBlockText.remove();
        thirdBlockText = SliderPaper.text((SliderPaperWidth * 0.53), secondDevideLinePosition + (SliderPaperHeight * 0.04), "Температура");
        thirdBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
        firstBlockText.remove();
        firstBlockText = SliderPaper.text((SliderPaperWidth * 0.5), (SliderPaperHeight * 0.02), "Кто едет");
        firstBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
        secondBlockText.remove();
        secondBlockText = SliderPaper.text((SliderPaperWidth * 0.49), firstDevideLinePosition + (SliderPaperHeight * 0.02), "Что делать?");
        secondBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
        forthBlockText.remove();
        forthBlockText = SliderPaper.text((SliderPaperWidth * 0.53), thirdDevideLinePosition + (SliderPaperHeight * 0.02), "Время перелета");
        forthBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
        fifthBlockText.remove();
        fifthBlockText = SliderPaper.text((SliderPaperWidth * 0.53), forthDevideLinePosition + (SliderPaperHeight * 0.02), "Проживание");
        fifthBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
        sixthBlockText.remove();
        sixthBlockText = SliderPaper.text((SliderPaperWidth * 0.53), fifthDevideLinePosition + (SliderPaperHeight * 0.03), "Еда");
        sixthBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
        seventhBlockText.remove();
        seventhBlockText = SliderPaper.text((SliderPaperWidth * 0.53), sixthDevideLinePosition + (SliderPaperHeight * 0.03), "Алкоголь");
        seventhBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
        eightsBlockText.remove();
        eightsBlockText = SliderPaper.text((SliderPaperWidth * 0.53), seventhDevideLinePosition + (SliderPaperHeight * 0.03), "Настроение");
        eightsBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
        ninthBlockText.remove();
        ninthBlockText = SliderPaper.text((SliderPaperWidth * 0.53), eightsDevideLinePosition + (SliderPaperHeight * 0.03), "Количество туристов");
        ninthBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
        tenthBlockText.remove();
        tenthBlockText = SliderPaper.text((SliderPaperWidth * 0.53), ninthDevideLinePosition + (SliderPaperHeight * 0.03), "Безопастность");
        tenthBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
        eleventhBlockText.remove();
        eleventhBlockText = SliderPaper.text((SliderPaperWidth * 0.53), tenthDevideLinePosition + (SliderPaperHeight * 0.03), "Секс");
        eleventhBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });


        var monthCounterForRemovingMonthesInResizeFunc = 0;
        for (monthCounterForRemovingMonthesInResizeFunc in monthNamesText)
            monthNamesText[monthCounterForRemovingMonthesInResizeFunc].remove();
        monthNamesText[1] = MonthPaper.text((MonthPaperWidth * 0.5 / 12), (MonthPaperHeight * 0.6), "Январь");
        monthNamesText[1].attr({ 'font-size': (bottomFont * 3) / 4, fill: 'white', cursor: "hand" });
        monthNamesText[2] = MonthPaper.text((MonthPaperWidth * 1.5 / 12), (MonthPaperHeight * 0.6), "Февраль");
        monthNamesText[2].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
        monthNamesText[3] = MonthPaper.text((MonthPaperWidth * 2.5 / 12), (MonthPaperHeight * 0.6), "Март");
        monthNamesText[3].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
        monthNamesText[4] = MonthPaper.text((MonthPaperWidth * 3.5 / 12), (MonthPaperHeight * 0.6), "Апрель");
        monthNamesText[4].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
        monthNamesText[5] = MonthPaper.text((MonthPaperWidth * 4.5 / 12), (MonthPaperHeight * 0.6), "Май");
        monthNamesText[5].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
        monthNamesText[6] = MonthPaper.text((MonthPaperWidth * 5.5 / 12), (MonthPaperHeight * 0.6), "Июнь");
        monthNamesText[6].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
        monthNamesText[7] = MonthPaper.text((MonthPaperWidth * 6.5 / 12), (MonthPaperHeight * 0.6), "Июль");
        monthNamesText[7].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
        monthNamesText[8] = MonthPaper.text((MonthPaperWidth * 7.5 / 12), (MonthPaperHeight * 0.6), "Август");
        monthNamesText[8].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
        monthNamesText[9] = MonthPaper.text((MonthPaperWidth * 8.5 / 12), (MonthPaperHeight * 0.6), "Сентябрь");
        monthNamesText[9].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
        monthNamesText[10] = MonthPaper.text((MonthPaperWidth * 9.5 / 12), (MonthPaperHeight * 0.6), "Октябрь");
        monthNamesText[10].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
        monthNamesText[11] = MonthPaper.text((MonthPaperWidth * 10.5 / 12), (MonthPaperHeight * 0.6), "Ноябрь");
        monthNamesText[11].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });
        monthNamesText[12] = MonthPaper.text((MonthPaperWidth * 11.5 / 12), (MonthPaperHeight * 0.6), "Декабрь");
        monthNamesText[12].attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor, cursor: "hand" });

        if (monthClickX == undefined)
            monthClickX = 1;
        orangePath.animate({ opacity: 0 });
        for (monthCounter = 0; monthCounter < 12; monthCounter++) {
            if (monthCounter == (monthClickX - 1)) {
                monthes[monthClickX - 1].animate({ fill: '#f33', path: 'M' + ((MonthPaperWidth / 12) * (monthClickX - 1)) + ',' + MonthPaperHeight + 'L' + ((MonthPaperWidth / 12) * (monthClickX)) + ',' + MonthPaperHeight + 'L' + ((MonthPaperWidth / 12) * (monthClickX)) + ',' + (MonthPaperHeight * 0.3) + 'L' + ((MonthPaperWidth / 12) * (monthClickX - 1)) + ',' + (MonthPaperHeight * 0.3) }, 0);
                monthNamesText[monthClickX].attr({ fill: 'white' });
                orangePath.attr({ path: 'M' + ((MonthPaperWidth / 12) * (monthClickX - 1)) + ',' + (MonthPaperHeight * 0.3) + 'L' + ((MonthPaperWidth / 12) * (monthClickX)) + ',' + (MonthPaperHeight * 0.3) + 'L' + ((MonthPaperWidth / 12) * (monthClickX)) + ',' + 0 + 'L' + ((MonthPaperWidth / 12) * (monthClickX - 1)) + ',' + 0 });
                orangePath.animate({ opacity: 1 }, 300)
            }
            else {
                monthes[monthCounter].animate({ fill: 'white', path: 'M' + ((MonthPaperWidth / 12) * monthCounter) + ',' + MonthPaperHeight + 'L' + ((MonthPaperWidth / 12) * (monthCounter + 1)) + ',' + MonthPaperHeight + 'L' + ((MonthPaperWidth / 12) * (monthCounter + 1)) + ',' + (MonthPaperHeight * 0.3) + 'L' + ((MonthPaperWidth / 12) * monthCounter) + ',' + (MonthPaperHeight * 0.3) }, 0);
                monthNamesText[monthCounter + 1].attr({ fill: gradLineVioletFirstColor });
            }
        }

        /*selfRectText.remove();
        coupleRectText.remove();
        familyRectText.remove();
        friendsRectText.remove();
        firstBlockTextYPosition = (SliderPaperHeight * 0.05) + firstBlockRectWidth;
        selfRectText = SliderPaper.text((firstBlockRectOffset + firstBlockRectWidth * 0.5), firstBlockTextYPosition, "????");
        coupleRectText = SliderPaper.text((firstBlockRectOffset + firstBlockBetweenRectWidth + (firstBlockRectWidth) * 1.5), firstBlockTextYPosition, "??????");
        familyRectText = SliderPaper.text((firstBlockRectOffset + firstBlockBetweenRectWidth * 2 + (firstBlockRectWidth) * 2.5), firstBlockTextYPosition, "?????");
        friendsRectText = SliderPaper.text((firstBlockRectOffset + firstBlockBetweenRectWidth * 3 + (firstBlockRectWidth) * 3.5), firstBlockTextYPosition, "??????");

        //selfRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
        coupleRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
        familyRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
        friendsRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });

        tanRectText.remove();
        skiRectText.remove();
        watchRectText.remove();
        shoppingRectText.remove();
        cruiseRectText.remove();
        secondBlockTextYPosition = (SliderPaperHeight * 0.05 + firstDevideLinePosition) + secondBlockRectWidth;
        tanRectText = SliderPaper.text((secondBlockRectOffset + secondBlockRectWidth * 0.5), secondBlockTextYPosition, "????????");
        skiRectText = SliderPaper.text((secondBlockRectOffset + secondBlockBetweenRectWidth + (secondBlockRectWidth) + secondBlockRectWidth * 0.5), secondBlockTextYPosition, "????????");
        watchRectText = SliderPaper.text((secondBlockRectOffset + secondBlockBetweenRectWidth * 2 + (secondBlockRectWidth) * 2 + secondBlockRectWidth * 0.5), secondBlockTextYPosition, "????????");
        shoppingRectText = SliderPaper.text((secondBlockRectOffset + secondBlockBetweenRectWidth * 3 + (secondBlockRectWidth) * 3 + secondBlockRectWidth * 0.5), secondBlockTextYPosition, "???????");
        cruiseRectText = SliderPaper.text((secondBlockRectOffset + secondBlockBetweenRectWidth * 4 + (secondBlockRectWidth) * 4 + secondBlockRectWidth * 0.5), secondBlockTextYPosition, "?????");

        tanRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
        skiRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
        watchRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
        shoppingRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
        cruiseRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
*/

        //FirstBlock       
        /*firstBlockRectOffset = (SliderPaperWidth - (firstBlockRectWidth * 4 + firstBlockBetweenRectWidth * 3)) / 2;
        selfRect.attr({ x: (firstBlockRectOffset), y: (SliderPaperHeight * 0.04), width: firstBlockRectWidth, height: firstBlockRectWidth });
        selfRectOver.attr({ x: (firstBlockRectOffset), y: (SliderPaperHeight * 0.04), width: firstBlockRectWidth, height: firstBlockRectWidth });
        selfRectImage.attr({ x: (firstBlockRectOffset), y: (SliderPaperHeight * 0.04), width: firstBlockRectWidth, height: firstBlockRectWidth }); //= SliderPaper.image("img/self.png", (firstBlockRectOffset), (SliderPaperHeight * 0.04), firstBlockRectWidth, firstBlockRectWidth);
        coupleRect.attr({ x: (firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth)), y: (SliderPaperHeight * 0.04), width: firstBlockRectWidth, height: firstBlockRectWidth }); //= SliderPaper.rect((firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth)), (SliderPaperHeight * 0.04), firstBlockRectWidth, firstBlockRectWidth);
        coupleRectImage.attr({ x: (firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth)), y: (SliderPaperHeight * 0.04), width: firstBlockRectWidth, height: firstBlockRectWidth });
        coupleRectOver.attr({ x: (firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth)), y: (SliderPaperHeight * 0.04), width: firstBlockRectWidth, height: firstBlockRectWidth });
        familyRect.attr({ x: (firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth) * 2), y: (SliderPaperHeight * 0.04), width: firstBlockRectWidth, height: firstBlockRectWidth });
        familyRectImage.attr({ x: (firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth) * 2), y: (SliderPaperHeight * 0.04), width: firstBlockRectWidth, height: firstBlockRectWidth });
        familyRectOver.attr({ x: (firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth) * 2), y: (SliderPaperHeight * 0.04), width: firstBlockRectWidth, height: firstBlockRectWidth });
        friendsRect.attr({ x: (firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth) * 3), y: (SliderPaperHeight * 0.04), width: firstBlockRectWidth, height: firstBlockRectWidth });
        friendsRectImage.attr({ x: (firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth) * 3), y: (SliderPaperHeight * 0.04), width: firstBlockRectWidth, height: firstBlockRectWidth });
        friendsRectOver.attr({ x: (firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth) * 3), y: (SliderPaperHeight * 0.04), width: firstBlockRectWidth, height: firstBlockRectWidth });
*/
        //SecondBlock
      /*  secondBlockRectOffset = (SliderPaperWidth - (secondBlockRectWidth * 5 + secondBlockBetweenRectWidth * 4)) / 2;
        tanRect.attr({ x: (secondBlockRectOffset), y: (SliderPaperHeight * 0.04 + firstDevideLinePosition), width: secondBlockRectWidth, height: secondBlockRectWidth });
        tanRectImage.attr({ x: (secondBlockRectOffset), y: (SliderPaperHeight * 0.04 + firstDevideLinePosition), width: secondBlockRectWidth, height: secondBlockRectWidth });
        tanRectOver.attr({ x: (secondBlockRectOffset), y: (SliderPaperHeight * 0.04 + firstDevideLinePosition), width: secondBlockRectWidth, height: secondBlockRectWidth });
        skiRect.attr({ x: (secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth)), y: (SliderPaperHeight * 0.04 + firstDevideLinePosition), width: secondBlockRectWidth, height: secondBlockRectWidth });
        skiRectImage.attr({ x: (secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth)), y: (SliderPaperHeight * 0.04 + firstDevideLinePosition), width: secondBlockRectWidth, height: secondBlockRectWidth });
        skiRectOver.attr({ x: (secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth)), y: (SliderPaperHeight * 0.04 + firstDevideLinePosition), width: secondBlockRectWidth, height: secondBlockRectWidth });
        watchRect.attr({ x: (secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 2), y: (SliderPaperHeight * 0.04 + firstDevideLinePosition), width: secondBlockRectWidth, height: secondBlockRectWidth });
        watchRectImage.attr({ x: (secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 2), y: (SliderPaperHeight * 0.04 + firstDevideLinePosition), width: secondBlockRectWidth, height: secondBlockRectWidth });
        watchRectOver.attr({ x: (secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 2), y: (SliderPaperHeight * 0.04 + firstDevideLinePosition), width: secondBlockRectWidth, height: secondBlockRectWidth });
        shoppingRect.attr({ x: (secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 3), y: (SliderPaperHeight * 0.04 + firstDevideLinePosition), width: secondBlockRectWidth, height: secondBlockRectWidth });
        shoppingRectImage.attr({ x: (secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 3), y: (SliderPaperHeight * 0.04 + firstDevideLinePosition), width: secondBlockRectWidth, height: secondBlockRectWidth });
        shoppingRectOver.attr({ x: (secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 3), y: (SliderPaperHeight * 0.04 + firstDevideLinePosition), width: secondBlockRectWidth, height: secondBlockRectWidth });
        cruiseRect.attr({ x: (secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 4), y: (SliderPaperHeight * 0.04 + firstDevideLinePosition), width: secondBlockRectWidth, height: secondBlockRectWidth });
        cruiseRectImage.attr({ x: (secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 4), y: (SliderPaperHeight * 0.04 + firstDevideLinePosition), width: secondBlockRectWidth, height: secondBlockRectWidth });
        cruiseRectOver.attr({ x: (secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 4), y: (SliderPaperHeight * 0.04 + firstDevideLinePosition), width: secondBlockRectWidth, height: secondBlockRectWidth });
*/
        //secondBlockTextYPosition = (SliderPaperHeight * 0.05 + firstDevideLinePosition) + secondBlockRectWidth;
        ninthBlockRussianText.remove();
        ninthBlockVisaText.remove();
        if (newWidth > 1350) {
            ninthBlockRussianText = SliderPaper.text(115, SliderPaperHeight - 42, "Русский язык");
        }
        else {
            if (newWidth > 1150)
                ninthBlockRussianText = SliderPaper.text(90, SliderPaperHeight - 42, "Русский язык");
            else
                ninthBlockRussianText = SliderPaper.text(70, SliderPaperHeight - 42, "Русский язык");
        }
        if (newWidth < 975) {
            ninthBlockRussianText.remove();
            ninthBlockRussianText = SliderPaper.text(55, SliderPaperHeight - 42, "Русский язык");
        }

        if (newWidth > 1450) {
            ninthBlockVisaText = SliderPaper.text(320, SliderPaperHeight - 42, "Без визы");
        }
        else {
            if (newWidth > 1250)
                ninthBlockVisaText = SliderPaper.text(250, SliderPaperHeight - 42, "Без визы");
            else
                ninthBlockVisaText = SliderPaper.text(210, SliderPaperHeight - 42, "Без визы");
        }
        if (newWidth < 975) {
            ninthBlockVisaText.remove();
            ninthBlockVisaText = SliderPaper.text(180, SliderPaperHeight - 42, "Без визы");
        }

        /*
        if (newWidth > 1350)
        else
        if (newWidth > 1200)
        ninthBlockRussianText = SliderPaper.text(20, SliderPaperHeight - 42, "������� ����");
        else
        if (newWidth < 1000)
        ninthBlockRussianText = SliderPaper.text(20, SliderPaperHeight - 42, "������� ����");
        */
        ninthBlockRussianText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
        ninthBlockVisaText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });

        SliderPaperHeight = oldSliderPaperWidth
        SliderPaperWidth = SliderPaperHeight
        //MonthPaperWidth = oldMonthPaperWidth
        //MonthPaperHeight = oldMonthPaperHeight

    });
    //  alert("sucsessfull build");s
	mapAndCurves();
}

/**/
