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
    HeadPaper.image("img/Globerry.png", 0, HeadPaperHeight * 0.05, 230, HeadPaperHeight * 0.7);
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
        monthes[monthCounter] = monthInMonthes
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
                orangePath.animate({ opacity: 1 }, 300)
            }
            else {
                monthes[monthCounter].animate({ fill: 'white', path: 'M' + ((MonthPaperWidth / 12) * monthCounter) + ',' + MonthPaperHeight + 'L' + ((MonthPaperWidth / 12) * (monthCounter + 1)) + ',' + MonthPaperHeight + 'L' + ((MonthPaperWidth / 12) * (monthCounter + 1)) + ',' + (MonthPaperHeight * 0.3) + 'L' + ((MonthPaperWidth / 12) * monthCounter) + ',' + (MonthPaperHeight * 0.3) }, 300);
                monthNamesText[monthCounter + 1].attr({ fill: gradLineVioletFirstColor });
            }
        }
        // alert(monthClickX);
    }
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
    if (Width < 1500) {
        var firstBlockBetweenRectWidth = (SliderPaperWidth * 0.035 * (1 + ((1500 - Width) / Width)));
        //alert("123");
    }
    else {
        var firstBlockBetweenRectWidth = (SliderPaperWidth * 0.035);
    }


    var firstBlockRectOffset = (SliderPaperWidth - (firstBlockRectWidth * 4 + firstBlockBetweenRectWidth * 3)) / 2;
    var selfRect = SliderPaper.rect((firstBlockRectOffset), (SliderPaperHeight * 0.04), firstBlockRectWidth, firstBlockRectWidth);
    var selfRectImage = SliderPaper.image("img/self.png", (firstBlockRectOffset), (SliderPaperHeight * 0.04), firstBlockRectWidth, firstBlockRectWidth);
    var selfRectOver = SliderPaper.rect((firstBlockRectOffset), (SliderPaperHeight * 0.04), firstBlockRectWidth, firstBlockRectWidth);
    var coupleRect = SliderPaper.rect((firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth)), (SliderPaperHeight * 0.04), firstBlockRectWidth, firstBlockRectWidth);
    var coupleRectImage = SliderPaper.image("img/couple.png", (firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth)), (SliderPaperHeight * 0.04), firstBlockRectWidth, firstBlockRectWidth);
    var coupleRectOver = SliderPaper.rect((firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth)), (SliderPaperHeight * 0.04), firstBlockRectWidth, firstBlockRectWidth);
    var familyRect = SliderPaper.rect((firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth) * 2), (SliderPaperHeight * 0.04), firstBlockRectWidth, firstBlockRectWidth);
    var familyRectImage = SliderPaper.image("img/family.png", (firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth) * 2), (SliderPaperHeight * 0.04), firstBlockRectWidth, firstBlockRectWidth);
    var familyRectOver = SliderPaper.rect((firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth) * 2), (SliderPaperHeight * 0.04), firstBlockRectWidth, firstBlockRectWidth);
    var friendsRect = SliderPaper.rect((firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth) * 3), (SliderPaperHeight * 0.04), firstBlockRectWidth, firstBlockRectWidth);
    var friendsRectImage = SliderPaper.image("img/friends.png", (firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth) * 3), (SliderPaperHeight * 0.04), firstBlockRectWidth, firstBlockRectWidth);
    var friendsRectOver = SliderPaper.rect((firstBlockRectOffset + (firstBlockRectWidth + firstBlockBetweenRectWidth) * 3), (SliderPaperHeight * 0.04), firstBlockRectWidth, firstBlockRectWidth);

    var firstDevideLinePosition = (SliderPaperHeight * 0.05) + firstBlockRectWidth * 1.5;
    //var firstDevideLinePosition = ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var firstDevideLine = SliderPaper.path('M' + 0 + ',' + firstDevideLinePosition + 'L' + SliderPaperWidth + ',' + firstDevideLinePosition);
    firstDevideLine.attr({ fill: lineColor, stroke: lineColor });
    // var firstBlockTextYPosition = (firstDevideLinePosition - (SliderPaperHeight * 0.2));
    //if (Width > 1000)
    //  firstBlockTextYPosition = (firstDevideLinePosition - (SliderPaperHeight * 0.034));
    firstBlockTextYPosition = (SliderPaperHeight * 0.04) + firstBlockRectWidth + (SliderPaperHeight * 0.01);

    var selfRectText = SliderPaper.text((firstBlockRectOffset + firstBlockRectWidth * 0.5), firstBlockTextYPosition, "Один");
    var coupleRectText = SliderPaper.text((firstBlockRectOffset + firstBlockBetweenRectWidth + (firstBlockRectWidth) * 1.5), firstBlockTextYPosition, "Вдвоем");
    var familyRectText = SliderPaper.text((firstBlockRectOffset + firstBlockBetweenRectWidth * 2 + (firstBlockRectWidth) * 2.5), firstBlockTextYPosition, "Семья");
    var friendsRectText = SliderPaper.text((firstBlockRectOffset + firstBlockBetweenRectWidth * 3 + (firstBlockRectWidth) * 3.5), firstBlockTextYPosition, "Друзья");

    selfRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
    coupleRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
    familyRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
    friendsRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });

    selfRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
    coupleRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
    familyRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
    friendsRect.attr({ fill: [270, '#fc0', '#fc6'].join("-"), stroke: 'none' });

    selfRectOver.attr({ fill: '#000', stroke: 'none', opacity: 0, cursor: "hand" });
    coupleRectOver.attr({ fill: '#000', stroke: 'none', opacity: 0, cursor: "hand" });
    familyRectOver.attr({ fill: '#000', stroke: 'none', opacity: 0, cursor: "hand" });
    friendsRectOver.attr({ fill: '#000', stroke: 'none', opacity: 0, cursor: "hand" });

    selfRectOver.click(function () {
        selfRect.animate({ fill: [270, '#fc0', '#fc6'].join("-"), opacity: 0.7 }, 0);
        coupleRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        familyRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        friendsRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });

    });
    coupleRectOver.click(function () {
        coupleRect.animate({ fill: [270, '#fc0', '#fc6'].join("-"), opacity: 0.7 }, 0);
        selfRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        familyRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        friendsRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });

    });
    familyRectOver.click(function () {
        familyRect.animate({ fill: [270, '#fc0', '#fc6'].join("-"), opacity: 0.7 }, 0);
        selfRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        coupleRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        friendsRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
    });
    friendsRectOver.click(function () {
        friendsRect.animate({ fill: [270, '#fc0', '#fc6'].join("-"), opacity: 0.7 }, 0);
        selfRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        coupleRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        familyRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
    });


    var secondDevideLinePosition = firstDevideLinePosition * 2;
    var secondDevideLine = SliderPaper.path('M' + 0 + ',' + secondDevideLinePosition + 'L' + SliderPaperWidth + ',' + secondDevideLinePosition);
    secondDevideLine.attr({ fill: lineColor, stroke: lineColor });
    var secondBlockText = SliderPaper.text((SliderPaperWidth * 0.49), firstDevideLinePosition + (SliderPaperHeight * 0.02), "Что делать?");
    secondBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
    //alert(Height);
    var secondBlockRectWidth = firstBlockRectWidth;
    var secondBlockBetweenRectWidth = firstBlockBetweenRectWidth;
    var secondBlockRectOffset = (SliderPaperWidth - (firstBlockRectWidth * 5 + firstBlockBetweenRectWidth * 4)) / 2;

    var tanRect = SliderPaper.rect((secondBlockRectOffset), (SliderPaperHeight * 0.04 + firstDevideLinePosition), secondBlockRectWidth, secondBlockRectWidth);
    var tanRectImage = SliderPaper.image("img/tan.png", (secondBlockRectOffset), (SliderPaperHeight * 0.04 + firstDevideLinePosition), secondBlockRectWidth, firstBlockRectWidth);
    var tanRectOver = SliderPaper.rect((secondBlockRectOffset), (SliderPaperHeight * 0.04 + firstDevideLinePosition), secondBlockRectWidth, secondBlockRectWidth);
    var skiRect = SliderPaper.rect((secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth)), (SliderPaperHeight * 0.04 + firstDevideLinePosition), secondBlockRectWidth, secondBlockRectWidth);
    var skiRectImage = SliderPaper.image("img/ski.png", (secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth)), (SliderPaperHeight * 0.04 + firstDevideLinePosition), secondBlockRectWidth, secondBlockRectWidth);
    var skiRectOver = SliderPaper.rect((secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth)), (SliderPaperHeight * 0.04 + firstDevideLinePosition), secondBlockRectWidth, secondBlockRectWidth);
    var watchRect = SliderPaper.rect((secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 2), (SliderPaperHeight * 0.04 + firstDevideLinePosition), secondBlockRectWidth, secondBlockRectWidth);
    var watchRectImage = SliderPaper.image("img/watch.png", (secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 2), (SliderPaperHeight * 0.04 + firstDevideLinePosition), secondBlockRectWidth, secondBlockRectWidth);
    var watchRectOver = SliderPaper.rect((secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 2), (SliderPaperHeight * 0.04 + firstDevideLinePosition), secondBlockRectWidth, secondBlockRectWidth);
    var shoppingRect = SliderPaper.rect((secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 3), (SliderPaperHeight * 0.04 + firstDevideLinePosition), secondBlockRectWidth, secondBlockRectWidth);
    var shoppingRectImage = SliderPaper.image("img/shopping.png", (secondBlockRectOffset + (firstBlockRectWidth + secondBlockBetweenRectWidth) * 3), (SliderPaperHeight * 0.04 + firstDevideLinePosition), secondBlockRectWidth, secondBlockRectWidth);
    var shoppingRectOver = SliderPaper.rect((secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 3), (SliderPaperHeight * 0.04 + firstDevideLinePosition), secondBlockRectWidth, secondBlockRectWidth);
    var cruiseRect = SliderPaper.rect((secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 4), (SliderPaperHeight * 0.04 + firstDevideLinePosition), secondBlockRectWidth, secondBlockRectWidth);
    var cruiseRectImage = SliderPaper.image("img/cruise.png", (secondBlockRectOffset + (firstBlockRectWidth + secondBlockBetweenRectWidth) * 4), (SliderPaperHeight * 0.04 + firstDevideLinePosition), secondBlockRectWidth, secondBlockRectWidth);
    var cruiseRectOver = SliderPaper.rect((secondBlockRectOffset + (secondBlockRectWidth + secondBlockBetweenRectWidth) * 4), (SliderPaperHeight * 0.04 + firstDevideLinePosition), secondBlockRectWidth, secondBlockRectWidth);

    var secondBlockTextYPosition = (SliderPaperHeight * 0.05 + firstDevideLinePosition) + secondBlockRectWidth;
    //if (Width > 1000)
    //  secondBlockTextYPosition = (secondDevideLinePosition - (SliderPaperHeight * 0.035));

    var tanRectText = SliderPaper.text((secondBlockRectOffset + secondBlockRectWidth * 0.5), secondBlockTextYPosition, "Загарать");
    var skiRectText = SliderPaper.text((secondBlockRectOffset + secondBlockBetweenRectWidth + (secondBlockRectWidth) + secondBlockRectWidth * 0.5), secondBlockTextYPosition, "Кататься");
    var watchRectText = SliderPaper.text((secondBlockRectOffset + secondBlockBetweenRectWidth * 2 + (secondBlockRectWidth) * 2 + secondBlockRectWidth * 0.5), secondBlockTextYPosition, "Смотреть");
    var shoppingRectText = SliderPaper.text((secondBlockRectOffset + secondBlockBetweenRectWidth * 3 + (secondBlockRectWidth) * 3 + secondBlockRectWidth * 0.5), secondBlockTextYPosition, "Шоппинг");
    var cruiseRectText = SliderPaper.text((secondBlockRectOffset + secondBlockBetweenRectWidth * 4 + (secondBlockRectWidth) * 4 + secondBlockRectWidth * 0.5), secondBlockTextYPosition, "Круиз");

    tanRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
    skiRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
    watchRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
    shoppingRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
    cruiseRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });

    tanRect.attr({ fill: [270, '#fc0', '#fc6'].join("-"), stroke: 'none' });
    skiRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
    watchRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
    shoppingRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
    cruiseRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });

    tanRectOver.attr({ fill: '#000', stroke: 'none', opacity: 0, cursor: "hand" });
    skiRectOver.attr({ fill: '#000', stroke: 'none', opacity: 0, cursor: "hand" });
    watchRectOver.attr({ fill: '#000', stroke: 'none', opacity: 0, cursor: "hand" });
    shoppingRectOver.attr({ fill: '#000', stroke: 'none', opacity: 0, cursor: "hand" });
    cruiseRectOver.attr({ fill: '#000', stroke: 'none', opacity: 0, cursor: "hand" });


    tanRectOver.click(function () {
        tanRect.animate({ fill: [270, '#fc0', '#fc6'].join("-"), opacity: 0.7 }, 0);
        skiRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        watchRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        shoppingRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        cruiseRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
    });
    skiRectOver.click(function () {
        skiRect.animate({ fill: [270, '#fc0', '#fc6'].join("-"), opacity: 0.7 }, 0);
        tanRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        watchRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        shoppingRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        cruiseRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
    });
    watchRectOver.click(function () {
        watchRect.animate({ fill: [270, '#fc0', '#fc6'].join("-"), opacity: 0.7 }, 0);
        tanRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        skiRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        shoppingRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        cruiseRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });

    });
    shoppingRectOver.click(function () {
        shoppingRect.animate({ fill: [270, '#fc0', '#fc6'].join("-"), opacity: 0.7 }, 0);
        tanRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        skiRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        watchRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        cruiseRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
    });
    cruiseRectOver.click(function () {
        cruiseRect.animate({ fill: [270, '#fc0', '#fc6'].join("-"), opacity: 0.7 }, 0);
        tanRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        skiRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        watchRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
        shoppingRect.attr({ fill: [270, '#ccc', '#999'].join("-"), stroke: 'none' });
    });

    //==============================================================================
    //two handle sliders------------------------------------------------------------
    var thirdDevideLinePosition = firstDevideLinePosition * 2 + (SliderPaperHeight - firstDevideLinePosition * 2) * 0.13; // +0.7 * ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var thirdDevideLine = SliderPaper.path('M' + (SliderPaperWidth * 0.1) + ',' + thirdDevideLinePosition + 'L' + (SliderPaperWidth * 0.9) + ',' + thirdDevideLinePosition);
    thirdDevideLine.attr({ fill: lineColor, stroke: lineColor });
    var thirdBlockText = SliderPaper.text((SliderPaperWidth * 0.53), secondDevideLinePosition + (SliderPaperHeight * 0.04), "Температура");
    thirdBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
    var leftTemperatureBlockText = SliderPaper.text((SliderPaperWidth * 0.2), secondDevideLinePosition + (SliderPaperHeight * 0.03), "-35C").attr({ 'font-size': bottomFont, fill: '#bbb' });
    var rightTemperatureBlockText = SliderPaper.text((SliderPaperWidth * 0.81), secondDevideLinePosition + (SliderPaperHeight * 0.03), "+35C").attr({ 'font-size': bottomFont, fill: '#bbb' });

    var forthDevideLinePosition = firstDevideLinePosition * 2 + (SliderPaperHeight - firstDevideLinePosition * 2) * 0.26; //firstDevideLinePosition * 2 + 1.35 * ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var forthDevideLine = SliderPaper.path('M' + (SliderPaperWidth * 0.1) + ',' + forthDevideLinePosition + 'L' + (SliderPaperWidth * 0.9) + ',' + forthDevideLinePosition);
    forthDevideLine.attr({ fill: lineColor, stroke: lineColor });
    var forthBlockText = SliderPaper.text((SliderPaperWidth * 0.53), thirdDevideLinePosition + (SliderPaperHeight * 0.02), "Время перелета");
    forthBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
    var leftTimeBlockText = SliderPaper.text((SliderPaperWidth * 0.2), thirdDevideLinePosition + (SliderPaperHeight * 0.05), "0Ч").attr({ 'font-size': bottomFont, fill: '#bbb' });
    var rightTimeBlockText = SliderPaper.text((SliderPaperWidth * 0.81), thirdDevideLinePosition + (SliderPaperHeight * 0.05), "24Ч").attr({ 'font-size': bottomFont, fill: '#bbb' });

    var fifthDevideLinePosition = firstDevideLinePosition * 2 + (SliderPaperHeight - firstDevideLinePosition * 2) * 0.35; //firstDevideLinePosition * 2 + 1.75 * ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var fifthDevideLine = SliderPaper.path('M' + (SliderPaperWidth * 0.1) + ',' + fifthDevideLinePosition + 'L' + (SliderPaperWidth * 0.9) + ',' + fifthDevideLinePosition);
    fifthDevideLine.attr({ fill: lineColor, stroke: lineColor, opacity: 0 });
    var fifthBlockText = SliderPaper.text((SliderPaperWidth * 0.53), forthDevideLinePosition + (SliderPaperHeight * 0.02), "Проживание");
    fifthBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
    var leftlivingBlockText = SliderPaper.text((SliderPaperWidth * 0.2), forthDevideLinePosition + (SliderPaperHeight * 0.03), "0$").attr({ 'font-size': bottomFont, fill: '#bbb' });
    var rightlivingBlockText = SliderPaper.text((SliderPaperWidth * 0.81), forthDevideLinePosition + (SliderPaperHeight * 0.03), "3000$").attr({ 'font-size': bottomFont, fill: '#bbb' });

    var sixthDevideLinePosition = firstDevideLinePosition * 2 + (SliderPaperHeight - firstDevideLinePosition * 2) * 0.44; //firstDevideLinePosition * 2 + 2.15 * ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var sixthDevideLine = SliderPaper.path('M' + (SliderPaperWidth * 0.1) + ',' + sixthDevideLinePosition + 'L' + (SliderPaperWidth * 0.9) + ',' + sixthDevideLinePosition);
    sixthDevideLine.attr({ fill: '#f00', stroke: lineColor, opacity: 0 });
    var sixthBlockText = SliderPaper.text((SliderPaperWidth * 0.53), fifthDevideLinePosition + (SliderPaperHeight * 0.03), "Еда");
    sixthBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
    var leftfoodBlockText = SliderPaper.text((SliderPaperWidth * 0.2), fifthDevideLinePosition + (SliderPaperHeight * 0.04), "0$").attr({ 'font-size': bottomFont, fill: '#bbb' });
    var rightfoodBlockText = SliderPaper.text((SliderPaperWidth * 0.81), fifthDevideLinePosition + (SliderPaperHeight * 0.04), "300$").attr({ 'font-size': bottomFont, fill: '#bbb' });

    var seventhDevideLinePosition = firstDevideLinePosition * 2 + (SliderPaperHeight - firstDevideLinePosition * 2) * 0.53; //firstDevideLinePosition * 2 + 2.55 * ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var seventhDevideLine = SliderPaper.path('M' + (SliderPaperWidth * 0.1) + ',' + seventhDevideLinePosition + 'L' + (SliderPaperWidth * 0.9) + ',' + seventhDevideLinePosition);
    seventhDevideLine.attr({ fill: '#f00', stroke: lineColor, opacity: 0 });
    var seventhBlockText = SliderPaper.text((SliderPaperWidth * 0.53), sixthDevideLinePosition + (SliderPaperHeight * 0.03), "Алкоголь");
    seventhBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
    var leftalcoholBlockText = SliderPaper.text((SliderPaperWidth * 0.2), sixthDevideLinePosition + (SliderPaperHeight * 0.04), "0$").attr({ 'font-size': bottomFont, fill: '#bbb' });
    var rightalcoholBlockText = SliderPaper.text((SliderPaperWidth * 0.81), sixthDevideLinePosition + (SliderPaperHeight * 0.04), "30$").attr({ 'font-size': bottomFont, fill: '#bbb' });

    var eightsDevideLinePosition = firstDevideLinePosition * 2 + (SliderPaperHeight - firstDevideLinePosition * 2) * 0.62; //firstDevideLinePosition * 2 + 2.95 * ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var eightsDevideLine = SliderPaper.path('M' + (SliderPaperWidth * 0.1) + ',' + eightsDevideLinePosition + 'L' + (SliderPaperWidth * 0.9) + ',' + eightsDevideLinePosition);
    eightsDevideLine.attr({ fill: '#f00', stroke: lineColor, opacity: 0 });
    var eightsBlockText = SliderPaper.text((SliderPaperWidth * 0.53), seventhDevideLinePosition + (SliderPaperHeight * 0.03), "Настроение");
    eightsBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
    //var leftmoodBlockText = SliderPaper.text((SliderPaperWidth * 0.2), seventhDevideLinePosition + (SliderPaperHeight * 0.04), "0$").attr({ 'font-size': bottomFont, fill: '#bbb' });
    //var rightmoodBlockText = SliderPaper.text((SliderPaperWidth * 0.81), seventhDevideLinePosition + (SliderPaperHeight * 0.04), "30$").attr({ 'font-size': bottomFont, fill: '#bbb' });

    var ninthDevideLinePosition = firstDevideLinePosition * 2 + (SliderPaperHeight - firstDevideLinePosition * 2) * 0.71; //firstDevideLinePosition * 2 + 3.35 * ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var ninthDevideLine = SliderPaper.path('M' + (SliderPaperWidth * 0.1) + ',' + ninthDevideLinePosition + 'L' + (SliderPaperWidth * 0.9) + ',' + ninthDevideLinePosition);
    ninthDevideLine.attr({ fill: '#f00', stroke: lineColor, opacity: 0 });
    var ninthBlockText = SliderPaper.text((SliderPaperWidth * 0.53), eightsDevideLinePosition + (SliderPaperHeight * 0.03), "Количество туристов");
    ninthBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
    //var leftmoodBlockText = SliderPaper.text((SliderPaperWidth * 0.2), eightsDevideLinePosition + (SliderPaperHeight * 0.04), "0$").attr({ 'font-size': bottomFont, fill: '#bbb' });
    //var rightmoodBlockText = SliderPaper.text((SliderPaperWidth * 0.81), eightsDevideLinePosition + (SliderPaperHeight * 0.04), "30$").attr({ 'font-size': bottomFont, fill: '#bbb' });

    var tenthDevideLinePosition = firstDevideLinePosition * 2 + (SliderPaperHeight - firstDevideLinePosition * 2) * 0.8; //firstDevideLinePosition * 2 + 3.75 * ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var tenthDevideLine = SliderPaper.path('M' + (SliderPaperWidth * 0.1) + ',' + tenthDevideLinePosition + 'L' + (SliderPaperWidth * 0.9) + ',' + tenthDevideLinePosition);
    tenthDevideLine.attr({ fill: '#f00', stroke: lineColor, opacity: 0 });
    var tenthBlockText = SliderPaper.text((SliderPaperWidth * 0.53), ninthDevideLinePosition + (SliderPaperHeight * 0.03), "Безопасность");
    tenthBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
    //var leftmoodBlockText = SliderPaper.text((SliderPaperWidth * 0.2), ninthDevideLinePosition + (SliderPaperHeight * 0.04), "0$").attr({ 'font-size': bottomFont, fill: '#bbb' });
    //var rightmoodBlockText = SliderPaper.text((SliderPaperWidth * 0.81), ninthDevideLinePosition + (SliderPaperHeight * 0.04), "30$").attr({ 'font-size': bottomFont, fill: '#bbb' });

    var eleventhDevideLinePosition = firstDevideLinePosition * 2 + (SliderPaperHeight - firstDevideLinePosition * 2) * 0.89; //firstDevideLinePosition * 2 + 4.15 * ((SliderPaperHeight * 0.05) + (SliderPaperHeight * 0.105));
    var eleventhDevideLine = SliderPaper.path('M' + (SliderPaperWidth * 0.1) + ',' + eleventhDevideLinePosition + 'L' + (SliderPaperWidth * 0.9) + ',' + eleventhDevideLinePosition);
    eleventhDevideLine.attr({ fill: '#f00', stroke: lineColor, opacity: 0 });
    var eleventhBlockText = SliderPaper.text((SliderPaperWidth * 0.53), tenthDevideLinePosition + (SliderPaperHeight * 0.03), "Секс");
    eleventhBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
    //var leftmoodBlockText = SliderPaper.text((SliderPaperWidth * 0.2), tenthDevideLinePosition + (SliderPaperHeight * 0.04), "0$").attr({ 'font-size': bottomFont, fill: '#bbb' });
    //var rightmoodBlockText = SliderPaper.text((SliderPaperWidth * 0.81), tenthDevideLinePosition + (SliderPaperHeight * 0.04), "30$").attr({ 'font-size': bottomFont, fill: '#bbb' });


    ///---------------------------------first slider-------------------
    //var nowX, nowY, R = Raphael(BottomAvia, 500, 500),
    var previousX;
    var temperatureSliderYPosition = secondDevideLinePosition + (SliderPaperHeight * 0.060);
    var temperatureSliderHeight = firstBlockRectWidth * 0.15;
    var temperatureSliderHandlerXSize = temperatureSliderHeight + firstBlockRectWidth * 0.02;
    var temperatureSliderHandlerYSize = temperatureSliderHeight + firstBlockRectWidth * 0.02;
    var temperatureSliderLeftPosition = (SliderPaperWidth * 0.2);
    var temperatureSliderBackRect = SliderPaper.rect((temperatureSliderLeftPosition - temperatureSliderHandlerYSize), (temperatureSliderYPosition + firstBlockRectWidth * 0.02), (SliderPaperWidth - (2 * temperatureSliderLeftPosition) + 3 * temperatureSliderHandlerYSize), (temperatureSliderHeight - firstBlockRectWidth * 0.04)).attr({ fill: '#ddd', stroke: "none", 'rx': '20' });
    var temperatureSliderLeftBackCircle = SliderPaper.circle((temperatureSliderLeftPosition - temperatureSliderHandlerYSize), ((temperatureSliderYPosition + firstBlockRectWidth * 0.02) + (temperatureSliderHeight - firstBlockRectWidth * 0.04) / 2), (temperatureSliderHeight - firstBlockRectWidth * 0.04) / 2).attr({ fill: '#ddd', stroke: "none" });
    var temperatureSliderRightBackCircle = SliderPaper.circle(((temperatureSliderLeftPosition - temperatureSliderHandlerYSize) + (SliderPaperWidth - (2 * temperatureSliderLeftPosition) + 3 * temperatureSliderHandlerYSize)), ((temperatureSliderYPosition + firstBlockRectWidth * 0.02) + (temperatureSliderHeight - firstBlockRectWidth * 0.04) / 2), (temperatureSliderHeight - firstBlockRectWidth * 0.04) / 2).attr({ fill: '#ddd', stroke: "none" });

    var temperatureSliderCenterRect = SliderPaper.rect((temperatureSliderLeftPosition + temperatureSliderHandlerYSize), (temperatureSliderYPosition + firstBlockRectWidth * 0.02), (SliderPaperWidth - (2 * temperatureSliderLeftPosition) - temperatureSliderHandlerYSize), (temperatureSliderHeight - firstBlockRectWidth * 0.04)).attr({ fill: [0, gradLineYellowFirstColor, gradLineYellowSecondColor].join("-"), stroke: "none" });

    //var temperatureSliderGradCenterBottomLies = SliderPaper.path("M" + temperatureSliderLeftPosition + ',' + (temperatureSliderYPosition + temperatureSliderHeight) + 'L' + temperatureSliderLeftPosition + ',' + (temperatureSliderYPosition + temperatureSliderHeight + temperatureSliderHandlerYSize));

    temperatureSliderLeftHandler = SliderPaper.rect(temperatureSliderLeftPosition, (temperatureSliderYPosition - temperatureSliderHandlerYSize * 0.1), temperatureSliderHandlerXSize * 1.1, temperatureSliderHandlerYSize * 1.2).attr({ fill: '#fff', stroke: "#ddd", cursor: "hand", 'stroke-linejoin': "round", rx: "5", ry: "5" }),
    temperatureSliderRightHandler = SliderPaper.rect((SliderPaperWidth - temperatureSliderLeftPosition), (temperatureSliderYPosition - temperatureSliderHandlerYSize * 0.1), temperatureSliderHandlerXSize * 1.1, temperatureSliderHandlerYSize * 1.2).attr({ fill: '#fff', stroke: "#ddd", cursor: "hand", rx: "5", ry: "5" });
    //var temperatureSliderLeftTopText = SliderPaper.text((SliderPaperWidth * 0.49), secondDevideLinePosition + (SliderPaperHeight * 0.04), "Температура");
    //var temperatureSliderLeftPathString = 'M'+ temperatureSliderLeftPosition +',' + ()
    //var temperatureSliderLeftPath = SliderPaper.path(temperatureSliderLeftPath);

    //j = SliderPaper.rect(0, 0, 100, 100),
    // start, move, and up are the drag functions
    temperatureSliderLeftHandlerStart = function () {
        // storing original coordinates
        this.ox = this.attr("x");
        //  this.attr({ opacity: 1 });
    },
    temperatureSliderLeftHandlerMove = function (dx, dy) {
        // move will be called with dx and dy
        if ((dx > 0) && (this.attr("x") < ((temperatureSliderRightHandler.attr("x") - temperatureSliderHandlerXSize)))) {
            if ((this.ox + dx) < ((temperatureSliderRightHandler.attr("x") - temperatureSliderHandlerXSize))) {
                this.attr({ x: this.ox + dx });
                temperatureSliderCenterRect.attr({ x: (this.ox + dx), width: (temperatureSliderRightHandler.attr("x") - temperatureSliderCenterRect.attr("x") - temperatureSliderHandlerXSize / 5) });
            }
            else {
                this.attr({ x: (temperatureSliderRightHandler.attr("x") - temperatureSliderHandlerXSize) });
                temperatureSliderCenterRect.attr({ x: (temperatureSliderRightHandler.attr("x") - temperatureSliderHandlerXSize), width: (temperatureSliderRightHandler.attr("x") - temperatureSliderCenterRect.attr("x") - temperatureSliderHandlerXSize / 5) });
            }
        }
        if ((dx < 0) && (this.attr("x") > (temperatureSliderLeftPosition + temperatureSliderHandlerXSize)))
            if ((this.ox + dx) > (this.attr("x") > (temperatureSliderLeftPosition + temperatureSliderHandlerXSize))) {
                this.attr({ x: this.ox + dx });
                temperatureSliderCenterRect.attr({ x: (this.ox + dx), width: (temperatureSliderRightHandler.attr("x") - temperatureSliderCenterRect.attr("x") - temperatureSliderHandlerXSize / 5) });
            }
            else {
                this.attr({ x: (this.attr("x") > (temperatureSliderLeftPosition - temperatureSliderHandlerXSize)) });
                temperatureSliderCenterRect.attr({ x: (this.attr("x") > (temperatureSliderLeftPosition + temperatureSliderHandlerXSize)), width: (temperatureSliderRightHandler.attr("x") - temperatureSliderCenterRect.attr("x") - temperatureSliderHandlerXSize / 5) });
            }
        //warp slider if its out of bounds
        if (this.attr("x") < (temperatureSliderLeftPosition)) {
            this.attr({ x: (temperatureSliderLeftPosition) });
        }


        temperatureSliderCenterRect.attr({ x: (this.attr("x")), width: (temperatureSliderRightHandler.attr("x") - temperatureSliderCenterRect.attr("x")) });

        //---
        var leftTemperatureBlockTemperature = Math.round(((temperatureSliderLeftHandler.attr("x") - (temperatureSliderLeftPosition + temperatureSliderHandlerYSize) - (SliderPaperWidth - (2 * temperatureSliderLeftPosition) - temperatureSliderHandlerYSize) / 2) * 35) / ((SliderPaperWidth - (2 * temperatureSliderLeftPosition) - temperatureSliderHandlerYSize) / 2));
        if (leftTemperatureBlockTemperature > 0)
            leftTemperatureBlockText.attr({ text: '+' + leftTemperatureBlockTemperature + 'C' });
        else
            leftTemperatureBlockText.attr({ text: leftTemperatureBlockTemperature + 'C' });

        //---
    },
    temperatureSliderLeftHandlerUp = function () {
        // restoring state
        //temperatureSliderCenterRect.attr({ fill: '#999' });
        // this.attr({ opacity: .5 });
    };
    //----------------------------------------------
    temperatureSliderRightHandlerStart = function () {
        // storing original coordinates
        this.ox = this.attr("x");
        // this.attr({ opacity: 1 });
    },
    temperatureSliderRightHandlerMove = function (dx, dy) {
        // move will be called with dx and dy
        //alert(temperatureSliderCenterRect.attr("x"));
        if ((dx > 0) && (this.attr("x") < (SliderPaperWidth - (temperatureSliderLeftPosition + temperatureSliderHandlerXSize))))
            if ((this.ox + dx) < ((SliderPaperWidth - (temperatureSliderLeftPosition + temperatureSliderHandlerXSize)))) {
                this.attr({ x: this.ox + dx });
            }
            else {
                this.attr({ x: (SliderPaperWidth - (temperatureSliderLeftPosition)) });
            }

        if ((dx < 0) && (this.attr("x") > (temperatureSliderLeftHandler.attr("x") + temperatureSliderHandlerXSize))) {
            if ((this.ox + dx) > (temperatureSliderLeftHandler.attr("x") + temperatureSliderHandlerXSize)) {
                this.attr({ x: this.ox + dx });
            }
            else {
                this.attr({ x: (temperatureSliderLeftHandler.attr("x") + temperatureSliderHandlerXSize) });
            }
        }
        temperatureSliderCenterRect.attr({ width: (temperatureSliderRightHandler.attr("x") - temperatureSliderCenterRect.attr("x")) });
        var rightTemperatureBlockTemperature = Math.round(((temperatureSliderRightHandler.attr("x") - (temperatureSliderLeftPosition + temperatureSliderHandlerYSize) - (SliderPaperWidth - (2 * temperatureSliderLeftPosition) - temperatureSliderHandlerYSize) / 2) * 35) / ((SliderPaperWidth - (2 * temperatureSliderLeftPosition) - temperatureSliderHandlerYSize) / 2));
        if (rightTemperatureBlockTemperature > 0)
            rightTemperatureBlockText.attr({ text: '+' + rightTemperatureBlockTemperature + 'C' });
        else
            rightTemperatureBlockText.attr({ text: rightTemperatureBlockTemperature + 'C' });
    },
    temperatureSliderRightHandlerUp = function () {
        // restoring state
        // this.attr({ opacity: .5 });
        // temperatureSliderCenterRect.attr({fill : '#999' });
    };

    // rstart and rmove are the resize functions;
    temperatureSliderLeftHandler.drag(temperatureSliderLeftHandlerMove, temperatureSliderLeftHandlerStart, temperatureSliderLeftHandlerUp);
    temperatureSliderRightHandler.drag(temperatureSliderRightHandlerMove, temperatureSliderRightHandlerStart, temperatureSliderRightHandlerUp);

    ///----------------------second Slider -------------------------------------

    var timeSliderYPosition = thirdDevideLinePosition + (SliderPaperHeight * 0.065);
    var timeSliderHeight = firstBlockRectWidth * 0.15;
    var timeSliderHandlerXSize = timeSliderHeight + firstBlockRectWidth * 0.02;
    var timeSliderHandlerYSize = timeSliderHeight + firstBlockRectWidth * 0.02;
    var timeSliderLeftPosition = (SliderPaperWidth * 0.2);
    var timeSliderBackRect = SliderPaper.rect((timeSliderLeftPosition - timeSliderHandlerYSize), (timeSliderYPosition + firstBlockRectWidth * 0.02), (SliderPaperWidth - (2 * timeSliderLeftPosition) + 3 * timeSliderHandlerYSize), (timeSliderHeight - firstBlockRectWidth * 0.04)).attr({ fill: '#ddd', stroke: "none", 'rx': '20' });
    var timeSliderLeftBackCircle = SliderPaper.circle((timeSliderLeftPosition - timeSliderHandlerYSize), ((timeSliderYPosition + firstBlockRectWidth * 0.02) + (timeSliderHeight - firstBlockRectWidth * 0.04) / 2), (timeSliderHeight - firstBlockRectWidth * 0.04) / 2).attr({ fill: '#ddd', stroke: "none" });
    var timeSliderRightBackCircle = SliderPaper.circle(((timeSliderLeftPosition - timeSliderHandlerYSize) + (SliderPaperWidth - (2 * timeSliderLeftPosition) + 3 * timeSliderHandlerYSize)), ((timeSliderYPosition + firstBlockRectWidth * 0.02) + (timeSliderHeight - firstBlockRectWidth * 0.04) / 2), (timeSliderHeight - firstBlockRectWidth * 0.04) / 2).attr({ fill: '#ddd', stroke: "none" });

    var timeSliderCenterRect = SliderPaper.rect((timeSliderLeftPosition + timeSliderHandlerYSize), (timeSliderYPosition + firstBlockRectWidth * 0.02), (SliderPaperWidth - (2 * timeSliderLeftPosition) - timeSliderHandlerYSize), (timeSliderHeight - firstBlockRectWidth * 0.04)).attr({ fill: [0, gradLineYellowFirstColor, gradLineYellowSecondColor].join("-"), stroke: "none" });

    timeSliderLeftHandler = SliderPaper.rect(timeSliderLeftPosition, (timeSliderYPosition - timeSliderHandlerYSize * 0.1), timeSliderHandlerXSize * 1.1, timeSliderHandlerYSize * 1.2).attr({ fill: '#fff', stroke: "#ddd", cursor: "hand", 'stroke-linejoin': "round", rx: "5", ry: "5" }),
    timeSliderRightHandler = SliderPaper.rect((SliderPaperWidth - timeSliderLeftPosition), (timeSliderYPosition - timeSliderHandlerYSize * 0.1), timeSliderHandlerXSize * 1.1, timeSliderHandlerYSize * 1.2).attr({ fill: '#fff', stroke: "#ddd", cursor: "hand", rx: "5", ry: "5" });
    //var timeSliderLeftTopText = SliderPaper.text((SliderPaperWidth * 0.49), secondDevideLinePosition + (SliderPaperHeight * 0.04), "Температура");
    //var timeSliderLeftPathString = 'M'+ timeSliderLeftPosition +',' + ()
    //var timeSliderLeftPath = SliderPaper.path(timeSliderLeftPath);

    //j = SliderPaper.rect(0, 0, 100, 100),
    // start, move, and up are the drag functions
    timeSliderLeftHandlerStart = function () {
        // storing original coordinates
        this.ox = this.attr("x");
        //  this.attr({ opacity: 1 });
    },
    timeSliderLeftHandlerMove = function (dx, dy) {
        // move will be called with dx and dy
        if ((dx > 0) && (this.attr("x") < ((timeSliderRightHandler.attr("x") - timeSliderHandlerXSize)))) {
            if ((this.ox + dx) < ((timeSliderRightHandler.attr("x") - timeSliderHandlerXSize))) {
                this.attr({ x: this.ox + dx });
                timeSliderCenterRect.attr({ x: (this.ox + dx), width: (timeSliderRightHandler.attr("x") - timeSliderCenterRect.attr("x") - timeSliderHandlerXSize / 5) });
            }
            else {
                this.attr({ x: (timeSliderRightHandler.attr("x") - timeSliderHandlerXSize) });
                timeSliderCenterRect.atrr({ x: (timeSliderRightHandler.attr("x") - timeSliderHandlerXSize), width: (timeSliderRightHandler.attr("x") - timeSliderCenterRect.attr("x") - timeSliderHandlerXSize / 5) });
            }
        }
        if ((dx < 0) && (this.attr("x") > (timeSliderLeftPosition + timeSliderHandlerXSize)))
            if ((this.ox + dx) > (this.attr("x") > (timeSliderLeftPosition + timeSliderHandlerXSize))) {
                this.attr({ x: this.ox + dx });
                timeSliderCenterRect.attr({ x: (this.ox + dx), width: (timeSliderRightHandler.attr("x") - timeSliderCenterRect.attr("x") - timeSliderHandlerXSize / 5) });
            }
            else {
                this.attr({ x: (this.attr("x") > (timeSliderLeftPosition + timeSliderHandlerXSize)) });
                timeSliderCenterRect.attr({ x: (this.attr("x") > (timeSliderLeftPosition + timeSliderHandlerXSize)), width: (timeSliderRightHandler.attr("x") - timeSliderCenterRect.attr("x") - timeSliderHandlerXSize / 5) });
            }
        //warp slider if its out of bounds
        if (this.attr("x") < (timeSliderLeftPosition)) {
            this.attr({ x: (timeSliderLeftPosition) });
        }


        timeSliderCenterRect.attr({ x: (this.attr("x")), width: (timeSliderRightHandler.attr("x") - timeSliderCenterRect.attr("x")) });
        var leftTimeBlockTime = Math.round(((timeSliderLeftHandler.attr("x") - (timeSliderLeftPosition + timeSliderHandlerYSize)) * 24) / ((SliderPaperWidth - (2 * timeSliderLeftPosition) - timeSliderHandlerYSize)));
        if (leftTimeBlockTime > 0)
            leftTimeBlockText.attr({ text: leftTimeBlockTime + 'Ч' });
        else
            leftTimeBlockText.attr({ text: '0Ч' });

    },
    timeSliderLeftHandlerUp = function () {
        // restoring state
        //timeSliderCenterRect.attr({ fill: '#999' });
        // this.attr({ opacity: .5 });
    };
    //----------------------------------------------
    timeSliderRightHandlerStart = function () {
        // storing original coordinates
        this.ox = this.attr("x");
        // this.attr({ opacity: 1 });
    },
    timeSliderRightHandlerMove = function (dx, dy) {
        // move will be called with dx and dy
        //alert(timeSliderCenterRect.attr("x"));
        if ((dx > 0) && (this.attr("x") < (SliderPaperWidth - (timeSliderLeftPosition + timeSliderHandlerXSize))))
            if ((this.ox + dx) < ((SliderPaperWidth - (timeSliderLeftPosition + timeSliderHandlerXSize)))) {
                this.attr({ x: this.ox + dx });
            }
            else {
                this.attr({ x: (SliderPaperWidth - (timeSliderLeftPosition)) });
            }

        if ((dx < 0) && (this.attr("x") > (timeSliderLeftHandler.attr("x") + timeSliderHandlerXSize))) {
            if ((this.ox + dx) > (timeSliderLeftHandler.attr("x") + timeSliderHandlerXSize)) {
                this.attr({ x: this.ox + dx });
            }
            else {
                this.attr({ x: (timeSliderLeftHandler.attr("x") + timeSliderHandlerXSize) });
            }
        }
        timeSliderCenterRect.attr({ width: (timeSliderRightHandler.attr("x") - timeSliderCenterRect.attr("x")) });
        var rightTimeBlockTime = Math.round(((timeSliderRightHandler.attr("x") - (timeSliderLeftPosition + timeSliderHandlerYSize)) * 24) / ((SliderPaperWidth - (2 * timeSliderLeftPosition) - timeSliderHandlerYSize)));
        if (rightTimeBlockTime > 0) {
            if (rightTimeBlockTime > 24)
                rightTimeBlockText.attr({ text: '24Ч' });
            else
                rightTimeBlockText.attr({ text: rightTimeBlockTime + 'Ч' });
        }
        else
            rightTimeBlockText.attr({ text: '0Ч' });

    },
    timeSliderRightHandlerUp = function () {
        // restoring state
        // this.attr({ opacity: .5 });
        // timeSliderCenterRect.attr({fill : '#999' });
    };

    // rstart and rmove are the resize functions;
    timeSliderLeftHandler.drag(timeSliderLeftHandlerMove, timeSliderLeftHandlerStart, timeSliderLeftHandlerUp);
    timeSliderRightHandler.drag(timeSliderRightHandlerMove, timeSliderRightHandlerStart, timeSliderRightHandlerUp);


    //==================================================================================
    ///----------------------third (Living) Slider -------------------------------------
    //==================================================================================

    var livingSliderYPosition = forthDevideLinePosition + (SliderPaperHeight * 0.06);
    var livingSliderHeight = firstBlockRectWidth * 0.15;
    var livingSliderHandlerXSize = livingSliderHeight + firstBlockRectWidth * 0.02;
    var livingSliderHandlerYSize = livingSliderHeight + firstBlockRectWidth * 0.02;
    var livingSliderLeftPosition = (SliderPaperWidth * 0.2);
    var livingSliderBackRect = SliderPaper.rect((livingSliderLeftPosition - livingSliderHandlerYSize), (livingSliderYPosition + firstBlockRectWidth * 0.02), (SliderPaperWidth - (2 * livingSliderLeftPosition) + 3 * livingSliderHandlerYSize), (livingSliderHeight - firstBlockRectWidth * 0.04)).attr({ fill: '#ddd', stroke: "none", 'rx': '20' });
    var livingSliderLeftBackCircle = SliderPaper.circle((livingSliderLeftPosition - livingSliderHandlerYSize), ((livingSliderYPosition + firstBlockRectWidth * 0.02) + (livingSliderHeight - firstBlockRectWidth * 0.04) / 2), (livingSliderHeight - firstBlockRectWidth * 0.04) / 2).attr({ fill: '#ddd', stroke: "none" });
    var livingSliderRightBackCircle = SliderPaper.circle(((livingSliderLeftPosition - livingSliderHandlerYSize) + (SliderPaperWidth - (2 * livingSliderLeftPosition) + 3 * livingSliderHandlerYSize)), ((livingSliderYPosition + firstBlockRectWidth * 0.02) + (livingSliderHeight - firstBlockRectWidth * 0.04) / 2), (livingSliderHeight - firstBlockRectWidth * 0.04) / 2).attr({ fill: '#ddd', stroke: "none" });

    var livingSliderCenterRect = SliderPaper.rect((livingSliderLeftPosition + livingSliderHandlerYSize), (livingSliderYPosition + firstBlockRectWidth * 0.02), (SliderPaperWidth - (2 * livingSliderLeftPosition) - livingSliderHandlerYSize), (livingSliderHeight - firstBlockRectWidth * 0.04)).attr({ fill: [0, gradLineYellowFirstColor, gradLineYellowSecondColor].join("-"), stroke: "none" });

    livingSliderLeftHandler = SliderPaper.rect(livingSliderLeftPosition, (livingSliderYPosition - livingSliderHandlerYSize * 0.1), livingSliderHandlerXSize * 1.1, livingSliderHandlerYSize * 1.2).attr({ fill: '#fff', stroke: "#ddd", cursor: "hand", 'stroke-linejoin': "round", rx: "5", ry: "5" }),
    livingSliderRightHandler = SliderPaper.rect((SliderPaperWidth - livingSliderLeftPosition), (livingSliderYPosition - livingSliderHandlerYSize * 0.1), livingSliderHandlerXSize * 1.1, livingSliderHandlerYSize * 1.2).attr({ fill: '#fff', stroke: "#ddd", cursor: "hand", rx: "5", ry: "5" });
    //var livingSliderLeftTopText = SliderPaper.text((SliderPaperWidth * 0.49), secondDevideLinePosition + (SliderPaperHeight * 0.04), "Температура");
    //var livingSliderLeftPathString = 'M'+ livingSliderLeftPosition +',' + ()
    //var livingSliderLeftPath = SliderPaper.path(livingSliderLeftPath);

    //j = SliderPaper.rect(0, 0, 100, 100),
    // start, move, and up are the drag functions
    livingSliderLeftHandlerStart = function () {
        // storing original coordinates
        this.ox = this.attr("x");
        //  this.attr({ opacity: 1 });
    },
    livingSliderLeftHandlerMove = function (dx, dy) {
        // move will be called with dx and dy
        if ((dx > 0) && (this.attr("x") < ((livingSliderRightHandler.attr("x") - livingSliderHandlerXSize)))) {
            if ((this.ox + dx) < ((livingSliderRightHandler.attr("x") - livingSliderHandlerXSize))) {
                this.attr({ x: this.ox + dx });
                livingSliderCenterRect.attr({ x: (this.ox + dx), width: (livingSliderRightHandler.attr("x") - livingSliderCenterRect.attr("x") - livingSliderHandlerXSize / 5) });
            }
            else {
                this.attr({ x: (livingSliderRightHandler.attr("x") - livingSliderHandlerXSize) });
                livingSliderCenterRect.atrr({ x: (livingSliderRightHandler.attr("x") - livingSliderHandlerXSize), width: (livingSliderRightHandler.attr("x") - livingSliderCenterRect.attr("x") - livingSliderHandlerXSize / 5) });
            }
        }
        if ((dx < 0) && (this.attr("x") > (livingSliderLeftPosition + livingSliderHandlerXSize)))
            if ((this.ox + dx) > (this.attr("x") > (livingSliderLeftPosition + livingSliderHandlerXSize))) {
                this.attr({ x: this.ox + dx });
                livingSliderCenterRect.attr({ x: (this.ox + dx), width: (livingSliderRightHandler.attr("x") - livingSliderCenterRect.attr("x") - livingSliderHandlerXSize / 5) });
            }
            else {
                this.attr({ x: (this.attr("x") > (livingSliderLeftPosition + livingSliderHandlerXSize)) });
                livingSliderCenterRect.attr({ x: (this.attr("x") > (livingSliderLeftPosition + livingSliderHandlerXSize)), width: (livingSliderRightHandler.attr("x") - livingSliderCenterRect.attr("x") - livingSliderHandlerXSize / 5) });
            }
        //warp
        if (this.attr("x") < (livingSliderLeftPosition)) {
            this.attr({ x: (livingSliderLeftPosition) });
        }

        livingSliderCenterRect.attr({ x: (this.attr("x")), width: (livingSliderRightHandler.attr("x") - livingSliderCenterRect.attr("x")) });
        var leftlivingBlockliving = Math.round(((livingSliderLeftHandler.attr("x") - (livingSliderLeftPosition + livingSliderHandlerYSize)) * 3000) / ((SliderPaperWidth - (2 * livingSliderLeftPosition) - livingSliderHandlerYSize)));
        if (leftlivingBlockliving > 0)
            leftlivingBlockText.attr({ text: leftlivingBlockliving + '$' });
        else
            leftlivingBlockText.attr({ text: '0$' });

    },
    livingSliderLeftHandlerUp = function () {
        // restoring state
        //livingSliderCenterRect.attr({ fill: '#999' });
        // this.attr({ opacity: .5 });
    };
    //----------------------------------------------
    livingSliderRightHandlerStart = function () {
        // storing original coordinates
        this.ox = this.attr("x");
        // this.attr({ opacity: 1 });
    },
    livingSliderRightHandlerMove = function (dx, dy) {
        // move will be called with dx and dy
        //alert(livingSliderCenterRect.attr("x"));
        if ((dx > 0) && (this.attr("x") < (SliderPaperWidth - (livingSliderLeftPosition + livingSliderHandlerXSize))))
            if ((this.ox + dx) < ((SliderPaperWidth - (livingSliderLeftPosition + livingSliderHandlerXSize)))) {
                this.attr({ x: this.ox + dx });
            }
            else {
                this.attr({ x: (SliderPaperWidth - (livingSliderLeftPosition)) });
            }

        if ((dx < 0) && (this.attr("x") > (livingSliderLeftHandler.attr("x") + livingSliderHandlerXSize))) {
            if ((this.ox + dx) > (livingSliderLeftHandler.attr("x") + livingSliderHandlerXSize)) {
                this.attr({ x: this.ox + dx });
            }
            else {
                this.attr({ x: (livingSliderLeftHandler.attr("x") + livingSliderHandlerXSize) });
            }
        }
        livingSliderCenterRect.attr({ width: (livingSliderRightHandler.attr("x") - livingSliderCenterRect.attr("x")) });
        var rightlivingBlockliving = Math.round(((livingSliderRightHandler.attr("x") - (livingSliderLeftPosition + livingSliderHandlerYSize))) / ((SliderPaperWidth - (2 * livingSliderLeftPosition) - livingSliderHandlerYSize)) * 3000);
        if (rightlivingBlockliving > 0) {
            if (rightlivingBlockliving > 3000)
                rightlivingBlockText.attr({ text: '3000$' });
            else
                rightlivingBlockText.attr({ text: rightlivingBlockliving + '$' });
        }
        else
            rightlivingBlockText.attr({ text: '0$' });

    },
    livingSliderRightHandlerUp = function () {
        // restoring state
        // this.attr({ opacity: .5 });
        // livingSliderCenterRect.attr({fill : '#999' });
    };

    // rstart and rmove are the resize functions;
    livingSliderLeftHandler.drag(livingSliderLeftHandlerMove, livingSliderLeftHandlerStart, livingSliderLeftHandlerUp);
    livingSliderRightHandler.drag(livingSliderRightHandlerMove, livingSliderRightHandlerStart, livingSliderRightHandlerUp);

    //----------------------===========================-----------
    ///----------------------forth (food) Slider -------------------------------------

    var foodSliderYPosition = fifthDevideLinePosition + (SliderPaperHeight * 0.06);
    var foodSliderHeight = firstBlockRectWidth * 0.15;
    var foodSliderHandlerXSize = foodSliderHeight + firstBlockRectWidth * 0.02;
    var foodSliderHandlerYSize = foodSliderHeight + firstBlockRectWidth * 0.02;
    var foodSliderLeftPosition = (SliderPaperWidth * 0.2);
    var foodSliderBackRect = SliderPaper.rect((foodSliderLeftPosition - foodSliderHandlerYSize), (foodSliderYPosition + firstBlockRectWidth * 0.02), (SliderPaperWidth - (2 * foodSliderLeftPosition) + 3 * foodSliderHandlerYSize), (foodSliderHeight - firstBlockRectWidth * 0.04)).attr({ fill: '#ddd', stroke: "none", 'rx': '20' });
    var foodSliderLeftBackCircle = SliderPaper.circle((foodSliderLeftPosition - foodSliderHandlerYSize), ((foodSliderYPosition + firstBlockRectWidth * 0.02) + (foodSliderHeight - firstBlockRectWidth * 0.04) / 2), (foodSliderHeight - firstBlockRectWidth * 0.04) / 2).attr({ fill: '#ddd', stroke: "none" });
    var foodSliderRightBackCircle = SliderPaper.circle(((foodSliderLeftPosition - foodSliderHandlerYSize) + (SliderPaperWidth - (2 * foodSliderLeftPosition) + 3 * foodSliderHandlerYSize)), ((foodSliderYPosition + firstBlockRectWidth * 0.02) + (foodSliderHeight - firstBlockRectWidth * 0.04) / 2), (foodSliderHeight - firstBlockRectWidth * 0.04) / 2).attr({ fill: '#ddd', stroke: "none" });

    var foodSliderCenterRect = SliderPaper.rect((foodSliderLeftPosition + foodSliderHandlerYSize), (foodSliderYPosition + firstBlockRectWidth * 0.02), (SliderPaperWidth - (2 * foodSliderLeftPosition) - foodSliderHandlerYSize), (foodSliderHeight - firstBlockRectWidth * 0.04)).attr({ fill: [0, gradLineYellowFirstColor, gradLineYellowSecondColor].join("-"), stroke: "none" });

    foodSliderLeftHandler = SliderPaper.rect(foodSliderLeftPosition, (foodSliderYPosition - foodSliderHandlerYSize * 0.1), foodSliderHandlerXSize * 1.1, foodSliderHandlerYSize * 1.2).attr({ fill: '#fff', stroke: "#ddd", cursor: "hand", 'stroke-linejoin': "round", rx: "5", ry: "5" }),
    foodSliderRightHandler = SliderPaper.rect((SliderPaperWidth - foodSliderLeftPosition), (foodSliderYPosition - foodSliderHandlerYSize * 0.1), foodSliderHandlerXSize * 1.1, foodSliderHandlerYSize * 1.2).attr({ fill: '#fff', stroke: "#ddd", cursor: "hand", rx: "5", ry: "5" });
    //var foodSliderLeftTopText = SliderPaper.text((SliderPaperWidth * 0.49), secondDevideLinePosition + (SliderPaperHeight * 0.04), "Температура");
    //var foodSliderLeftPathString = 'M'+ foodSliderLeftPosition +',' + ()
    //var foodSliderLeftPath = SliderPaper.path(foodSliderLeftPath);

    //j = SliderPaper.rect(0, 0, 100, 100),
    // start, move, and up are the drag functions
    foodSliderLeftHandlerStart = function () {
        // storing original coordinates
        this.ox = this.attr("x");
        //  this.attr({ opacity: 1 });
    },
    foodSliderLeftHandlerMove = function (dx, dy) {
        // move will be called with dx and dy
        if ((dx > 0) && (this.attr("x") < ((foodSliderRightHandler.attr("x") - foodSliderHandlerXSize)))) {
            if ((this.ox + dx) < ((foodSliderRightHandler.attr("x") - foodSliderHandlerXSize))) {
                this.attr({ x: this.ox + dx });
                foodSliderCenterRect.attr({ x: (this.ox + dx), width: (foodSliderRightHandler.attr("x") - foodSliderCenterRect.attr("x") - foodSliderHandlerXSize / 5) });
            }
            else {
                this.attr({ x: (foodSliderRightHandler.attr("x") - foodSliderHandlerXSize) });
                foodSliderCenterRect.atrr({ x: (foodSliderRightHandler.attr("x") - foodSliderHandlerXSize), width: (foodSliderRightHandler.attr("x") - foodSliderCenterRect.attr("x") - foodSliderHandlerXSize / 5) });
            }
        }
        if ((dx < 0) && (this.attr("x") > (foodSliderLeftPosition + foodSliderHandlerXSize)))
            if ((this.ox + dx) > (this.attr("x") > (foodSliderLeftPosition + foodSliderHandlerXSize))) {
                this.attr({ x: this.ox + dx });
                foodSliderCenterRect.attr({ x: (this.ox + dx), width: (foodSliderRightHandler.attr("x") - foodSliderCenterRect.attr("x") - foodSliderHandlerXSize / 5) });
            }
            else {
                this.attr({ x: (this.attr("x") > (foodSliderLeftPosition + foodSliderHandlerXSize)) });
                foodSliderCenterRect.attr({ x: (this.attr("x") > (foodSliderLeftPosition + foodSliderHandlerXSize)), width: (foodSliderRightHandler.attr("x") - foodSliderCenterRect.attr("x") - foodSliderHandlerXSize / 5) });
            }
        //warp
        if (this.attr("x") < (foodSliderLeftPosition)) {
            this.attr({ x: (foodSliderLeftPosition) });
        }

        foodSliderCenterRect.attr({ x: (this.attr("x")), width: (foodSliderRightHandler.attr("x") - foodSliderCenterRect.attr("x")) });
        var leftfoodBlockfood = Math.round(((foodSliderLeftHandler.attr("x") - (foodSliderLeftPosition + foodSliderHandlerYSize)) * 300) / ((SliderPaperWidth - (2 * foodSliderLeftPosition) - foodSliderHandlerYSize)));
        if (leftfoodBlockfood > 0)
            leftfoodBlockText.attr({ text: leftfoodBlockfood + '$' });
        else
            leftfoodBlockText.attr({ text: '0$' });

    },
    foodSliderLeftHandlerUp = function () {
        // restoring state
        //foodSliderCenterRect.attr({ fill: '#999' });
        // this.attr({ opacity: .5 });
    };
    //----------------------------------------------
    foodSliderRightHandlerStart = function () {
        // storing original coordinates
        this.ox = this.attr("x");
        // this.attr({ opacity: 1 });
    },
    foodSliderRightHandlerMove = function (dx, dy) {
        // move will be called with dx and dy
        //alert(foodSliderCenterRect.attr("x"));
        if ((dx > 0) && (this.attr("x") < (SliderPaperWidth - (foodSliderLeftPosition + foodSliderHandlerXSize))))
            if ((this.ox + dx) < ((SliderPaperWidth - (foodSliderLeftPosition + foodSliderHandlerXSize)))) {
                this.attr({ x: this.ox + dx });
            }
            else {
                this.attr({ x: (SliderPaperWidth - (foodSliderLeftPosition)) });
            }

        if ((dx < 0) && (this.attr("x") > (foodSliderLeftHandler.attr("x") + foodSliderHandlerXSize))) {
            if ((this.ox + dx) > (foodSliderLeftHandler.attr("x") + foodSliderHandlerXSize)) {
                this.attr({ x: this.ox + dx });
            }
            else {
                this.attr({ x: (foodSliderLeftHandler.attr("x") + foodSliderHandlerXSize) });
            }
        }
        foodSliderCenterRect.attr({ width: (foodSliderRightHandler.attr("x") - foodSliderCenterRect.attr("x")) });
        var rightfoodBlockfood = Math.round(((foodSliderRightHandler.attr("x") - (foodSliderLeftPosition + foodSliderHandlerYSize))) / ((SliderPaperWidth - (2 * foodSliderLeftPosition) - foodSliderHandlerYSize)) * 300);
        if (rightfoodBlockfood > 0) {
            if (rightfoodBlockfood > 3000)
                rightfoodBlockText.attr({ text: '300$' });
            else
                rightfoodBlockText.attr({ text: rightfoodBlockfood + '$' });
        }
        else
            rightfoodBlockText.attr({ text: '0$' });

    },
    foodSliderRightHandlerUp = function () {
        // restoring state
        // this.attr({ opacity: .5 });
        // foodSliderCenterRect.attr({fill : '#999' });
    };

    // rstart and rmove are the resize functions;
    foodSliderLeftHandler.drag(foodSliderLeftHandlerMove, foodSliderLeftHandlerStart, foodSliderLeftHandlerUp);
    foodSliderRightHandler.drag(foodSliderRightHandlerMove, foodSliderRightHandlerStart, foodSliderRightHandlerUp);

    //----------------------===========================-----------
    ///----------------------fifth (alcohol) Slider -------------------------------------

    var alcoholSliderYPosition = sixthDevideLinePosition + (SliderPaperHeight * 0.06);
    var alcoholSliderHeight = firstBlockRectWidth * 0.15;
    var alcoholSliderHandlerXSize = alcoholSliderHeight + firstBlockRectWidth * 0.02;
    var alcoholSliderHandlerYSize = alcoholSliderHeight + firstBlockRectWidth * 0.02;
    var alcoholSliderLeftPosition = (SliderPaperWidth * 0.2);
    var alcoholSliderBackRect = SliderPaper.rect((alcoholSliderLeftPosition - alcoholSliderHandlerYSize), (alcoholSliderYPosition + firstBlockRectWidth * 0.02), (SliderPaperWidth - (2 * alcoholSliderLeftPosition) + 3 * alcoholSliderHandlerYSize), (alcoholSliderHeight - firstBlockRectWidth * 0.04)).attr({ fill: '#ddd', stroke: "none", 'rx': '20' });
    var alcoholSliderLeftBackCircle = SliderPaper.circle((alcoholSliderLeftPosition - alcoholSliderHandlerYSize), ((alcoholSliderYPosition + firstBlockRectWidth * 0.02) + (alcoholSliderHeight - firstBlockRectWidth * 0.04) / 2), (alcoholSliderHeight - firstBlockRectWidth * 0.04) / 2).attr({ fill: '#ddd', stroke: "none" });
    var alcoholSliderRightBackCircle = SliderPaper.circle(((alcoholSliderLeftPosition - alcoholSliderHandlerYSize) + (SliderPaperWidth - (2 * alcoholSliderLeftPosition) + 3 * alcoholSliderHandlerYSize)), ((alcoholSliderYPosition + firstBlockRectWidth * 0.02) + (alcoholSliderHeight - firstBlockRectWidth * 0.04) / 2), (alcoholSliderHeight - firstBlockRectWidth * 0.04) / 2).attr({ fill: '#ddd', stroke: "none" });

    var alcoholSliderCenterRect = SliderPaper.rect((alcoholSliderLeftPosition + alcoholSliderHandlerYSize), (alcoholSliderYPosition + firstBlockRectWidth * 0.02), (SliderPaperWidth - (2 * alcoholSliderLeftPosition) - alcoholSliderHandlerYSize), (alcoholSliderHeight - firstBlockRectWidth * 0.04)).attr({ fill: [0, gradLineYellowFirstColor, gradLineYellowSecondColor].join("-"), stroke: "none" });

    alcoholSliderLeftHandler = SliderPaper.rect(alcoholSliderLeftPosition, (alcoholSliderYPosition - alcoholSliderHandlerYSize * 0.1), alcoholSliderHandlerXSize * 1.1, alcoholSliderHandlerYSize * 1.2).attr({ fill: '#fff', stroke: "#ddd", cursor: "hand", 'stroke-linejoin': "round", rx: "5", ry: "5" }),
    alcoholSliderRightHandler = SliderPaper.rect((SliderPaperWidth - alcoholSliderLeftPosition), (alcoholSliderYPosition - alcoholSliderHandlerYSize * 0.1), alcoholSliderHandlerXSize * 1.1, alcoholSliderHandlerYSize * 1.2).attr({ fill: '#fff', stroke: "#ddd", cursor: "hand", rx: "5", ry: "5" });
    //var alcoholSliderLeftTopText = SliderPaper.text((SliderPaperWidth * 0.49), secondDevideLinePosition + (SliderPaperHeight * 0.04), "Температура");
    //var alcoholSliderLeftPathString = 'M'+ alcoholSliderLeftPosition +',' + ()
    //var alcoholSliderLeftPath = SliderPaper.path(alcoholSliderLeftPath);

    //j = SliderPaper.rect(0, 0, 100, 100),
    // start, move, and up are the drag functions
    alcoholSliderLeftHandlerStart = function () {
        // storing original coordinates
        this.ox = this.attr("x");
        //  this.attr({ opacity: 1 });
    },
    alcoholSliderLeftHandlerMove = function (dx, dy) {
        // move will be called with dx and dy
        if ((dx > 0) && (this.attr("x") < ((alcoholSliderRightHandler.attr("x") - alcoholSliderHandlerXSize)))) {
            if ((this.ox + dx) < ((alcoholSliderRightHandler.attr("x") - alcoholSliderHandlerXSize))) {
                this.attr({ x: this.ox + dx });
                alcoholSliderCenterRect.attr({ x: (this.ox + dx), width: (alcoholSliderRightHandler.attr("x") - alcoholSliderCenterRect.attr("x") - alcoholSliderHandlerXSize / 5) });
            }
            else {
                this.attr({ x: (alcoholSliderRightHandler.attr("x") - alcoholSliderHandlerXSize) });
                alcoholSliderCenterRect.atrr({ x: (alcoholSliderRightHandler.attr("x") - alcoholSliderHandlerXSize), width: (alcoholSliderRightHandler.attr("x") - alcoholSliderCenterRect.attr("x") - alcoholSliderHandlerXSize / 5) });
            }
        }
        if ((dx < 0) && (this.attr("x") > (alcoholSliderLeftPosition + alcoholSliderHandlerXSize)))
            if ((this.ox + dx) > (this.attr("x") > (alcoholSliderLeftPosition + alcoholSliderHandlerXSize))) {
                this.attr({ x: this.ox + dx });
                alcoholSliderCenterRect.attr({ x: (this.ox + dx), width: (alcoholSliderRightHandler.attr("x") - alcoholSliderCenterRect.attr("x") - alcoholSliderHandlerXSize / 5) });
            }
            else {
                this.attr({ x: (this.attr("x") > (alcoholSliderLeftPosition + alcoholSliderHandlerXSize)) });
                alcoholSliderCenterRect.attr({ x: (this.attr("x") > (alcoholSliderLeftPosition + alcoholSliderHandlerXSize)), width: (alcoholSliderRightHandler.attr("x") - alcoholSliderCenterRect.attr("x") - alcoholSliderHandlerXSize / 5) });
            }
        //warp
        if (this.attr("x") < (alcoholSliderLeftPosition)) {
            this.attr({ x: (alcoholSliderLeftPosition) });
        }

        alcoholSliderCenterRect.attr({ x: (this.attr("x")), width: (alcoholSliderRightHandler.attr("x") - alcoholSliderCenterRect.attr("x")) });
        var leftalcoholBlockalcohol = Math.round(((alcoholSliderLeftHandler.attr("x") - (alcoholSliderLeftPosition + alcoholSliderHandlerYSize)) * 30) / ((SliderPaperWidth - (2 * alcoholSliderLeftPosition) - alcoholSliderHandlerYSize)));
        if (leftalcoholBlockalcohol > 0)
            leftalcoholBlockText.attr({ text: leftalcoholBlockalcohol + '$' });
        else
            leftalcoholBlockText.attr({ text: '0$' });

    },
    alcoholSliderLeftHandlerUp = function () {
        // restoring state
        //alcoholSliderCenterRect.attr({ fill: '#999' });
        // this.attr({ opacity: .5 });
    };
    //----------------------------------------------
    alcoholSliderRightHandlerStart = function () {
        // storing original coordinates
        this.ox = this.attr("x");
        // this.attr({ opacity: 1 });
    },
    alcoholSliderRightHandlerMove = function (dx, dy) {
        // move will be called with dx and dy
        //alert(alcoholSliderCenterRect.attr("x"));
        if ((dx > 0) && (this.attr("x") < (SliderPaperWidth - (alcoholSliderLeftPosition + alcoholSliderHandlerXSize))))
            if ((this.ox + dx) < ((SliderPaperWidth - (alcoholSliderLeftPosition + alcoholSliderHandlerXSize)))) {
                this.attr({ x: this.ox + dx });
            }
            else {
                this.attr({ x: (SliderPaperWidth - (alcoholSliderLeftPosition)) });
            }

        if ((dx < 0) && (this.attr("x") > (alcoholSliderLeftHandler.attr("x") + alcoholSliderHandlerXSize))) {
            if ((this.ox + dx) > (alcoholSliderLeftHandler.attr("x") + alcoholSliderHandlerXSize)) {
                this.attr({ x: this.ox + dx });
            }
            else {
                this.attr({ x: (alcoholSliderLeftHandler.attr("x") + alcoholSliderHandlerXSize) });
            }
        }
        alcoholSliderCenterRect.attr({ width: (alcoholSliderRightHandler.attr("x") - alcoholSliderCenterRect.attr("x")) });
        var rightalcoholBlockalcohol = Math.round(((alcoholSliderRightHandler.attr("x") - (alcoholSliderLeftPosition + alcoholSliderHandlerYSize))) / ((SliderPaperWidth - (2 * alcoholSliderLeftPosition) - alcoholSliderHandlerYSize)) * 30);
        if (rightalcoholBlockalcohol > 0) {
            if (rightalcoholBlockalcohol > 3000)
                rightalcoholBlockText.attr({ text: '30$' });
            else
                rightalcoholBlockText.attr({ text: rightalcoholBlockalcohol + '$' });
        }
        else
            rightalcoholBlockText.attr({ text: '0$' });

    },
    alcoholSliderRightHandlerUp = function () {
        // restoring state
        // this.attr({ opacity: .5 });
        // alcoholSliderCenterRect.attr({fill : '#999' });
    };

    // rstart and rmove are the resize functions;
    alcoholSliderLeftHandler.drag(alcoholSliderLeftHandlerMove, alcoholSliderLeftHandlerStart, alcoholSliderLeftHandlerUp);
    alcoholSliderRightHandler.drag(alcoholSliderRightHandlerMove, alcoholSliderRightHandlerStart, alcoholSliderRightHandlerUp);

    //----------------------===========================-----------
    ///----------------------sixth (mood) Slider -------------------------------------

    var moodSliderYPosition = seventhDevideLinePosition + (SliderPaperHeight * 0.06);
    var moodSliderHeight = firstBlockRectWidth * 0.15;
    var moodSliderHandlerXSize = moodSliderHeight + firstBlockRectWidth * 0.02;
    var moodSliderHandlerYSize = moodSliderHeight + firstBlockRectWidth * 0.02;
    var moodSliderLeftPosition = (SliderPaperWidth * 0.2);
    var moodSliderBackRect = SliderPaper.rect((moodSliderLeftPosition - moodSliderHandlerYSize), (moodSliderYPosition + firstBlockRectWidth * 0.02), (SliderPaperWidth - (2 * moodSliderLeftPosition) + 3 * moodSliderHandlerYSize), (moodSliderHeight - firstBlockRectWidth * 0.04)).attr({ fill: '#ddd', stroke: "none", 'rx': '20' });
    var moodSliderLeftBackCircle = SliderPaper.circle((moodSliderLeftPosition - moodSliderHandlerYSize), ((moodSliderYPosition + firstBlockRectWidth * 0.02) + (moodSliderHeight - firstBlockRectWidth * 0.04) / 2), (moodSliderHeight - firstBlockRectWidth * 0.04) / 2).attr({ fill: '#ddd', stroke: "none" });
    var moodSliderRightBackCircle = SliderPaper.circle(((moodSliderLeftPosition - moodSliderHandlerYSize) + (SliderPaperWidth - (2 * moodSliderLeftPosition) + 3 * moodSliderHandlerYSize)), ((moodSliderYPosition + firstBlockRectWidth * 0.02) + (moodSliderHeight - firstBlockRectWidth * 0.04) / 2), (moodSliderHeight - firstBlockRectWidth * 0.04) / 2).attr({ fill: '#ddd', stroke: "none" });

    // var moodSliderCenterRect = SliderPaper.rect((moodSliderLeftPosition + moodSliderHandlerYSize), (moodSliderYPosition + firstBlockRectWidth * 0.02), (SliderPaperWidth - (2 * moodSliderLeftPosition) - moodSliderHandlerYSize), (moodSliderHeight - firstBlockRectWidth * 0.04)).attr({ fill: [0, gradLineYellowFirstColor, gradLineYellowSecondColor].join("-"), stroke: "none" });

    moodSliderLeftHandler = SliderPaper.rect(moodSliderLeftPosition + ((moodSliderLeftPosition - moodSliderHandlerYSize) + (SliderPaperWidth - (2 * moodSliderLeftPosition) - 5 * moodSliderHandlerYSize)) / 2, (moodSliderYPosition - moodSliderHandlerYSize * 0.1), moodSliderHandlerXSize * 1.1, moodSliderHandlerYSize * 1.2).attr({ fill: '#fff', stroke: "#ddd", cursor: "hand", 'stroke-linejoin': "round", rx: "5", ry: "5" }),
    moodSliderRightHandler = SliderPaper.rect((SliderPaperWidth - moodSliderLeftPosition) + moodSliderHandlerYSize, (moodSliderYPosition - moodSliderHandlerYSize * 0.1), moodSliderHandlerXSize * 1.1, moodSliderHandlerYSize * 1.2).attr({ fill: '#fff', stroke: "#ddd", rx: "5", ry: "5", opacity: 0 });
    //var moodSliderLeftTopText = SliderPaper.text((SliderPaperWidth * 0.49), secondDevideLinePosition + (SliderPaperHeight * 0.04), "Температура");
    //var moodSliderLeftPathString = 'M'+ moodSliderLeftPosition +',' + ()
    //var moodSliderLeftPath = SliderPaper.path(moodSliderLeftPath);

    //j = SliderPaper.rect(0, 0, 100, 100),
    // start, move, and up are the drag functions
    moodSliderLeftHandlerStart = function () {
        // storing original coordinates
        this.ox = this.attr("x");
        //  this.attr({ opacity: 1 });
    },
    moodSliderLeftHandlerMove = function (dx, dy) {
        // move will be called with dx and dy
        if ((dx > 0) && (this.attr("x") < ((moodSliderRightHandler.attr("x") - moodSliderHandlerXSize)))) {
            if ((this.ox + dx) < ((moodSliderRightHandler.attr("x") - moodSliderHandlerXSize))) {
                this.attr({ x: this.ox + dx });
                //   moodSliderCenterRect.attr({ x: (this.ox + dx), width: (moodSliderRightHandler.attr("x") - moodSliderCenterRect.attr("x") - moodSliderHandlerXSize / 5) });
            }
            else {
                this.attr({ x: (moodSliderRightHandler.attr("x") - moodSliderHandlerXSize) });
                //     moodSliderCenterRect.atrr({ x: (moodSliderRightHandler.attr("x") - moodSliderHandlerXSize), width: (moodSliderRightHandler.attr("x") - moodSliderCenterRect.attr("x") - moodSliderHandlerXSize / 5) });
            }
        }
        if ((dx < 0) && (this.attr("x") > (moodSliderLeftPosition + moodSliderHandlerXSize)))
            if ((this.ox + dx) > (this.attr("x") > (moodSliderLeftPosition + moodSliderHandlerXSize))) {
                this.attr({ x: this.ox + dx });
                //     moodSliderCenterRect.attr({ x: (this.ox + dx), width: (moodSliderRightHandler.attr("x") - moodSliderCenterRect.attr("x") - moodSliderHandlerXSize / 5) });
            }
            else {
                this.attr({ x: (this.attr("x") > (moodSliderLeftPosition + moodSliderHandlerXSize)) });
                moodSliderCenterRect.attr({ x: (this.attr("x") > (moodSliderLeftPosition + moodSliderHandlerXSize)), width: (moodSliderRightHandler.attr("x") - moodSliderCenterRect.attr("x") - moodSliderHandlerXSize / 5) });
            }
        //warp
        if (this.attr("x") < (moodSliderLeftPosition)) {
            this.attr({ x: (moodSliderLeftPosition) });
        }

        //   moodSliderCenterRect.attr({ x: (this.attr("x")), width: (moodSliderRightHandler.attr("x") - moodSliderCenterRect.attr("x")) });
        /*
        var leftmoodBlockmood = Math.round(((moodSliderLeftHandler.attr("x") - (moodSliderLeftPosition + moodSliderHandlerYSize)) * 30) / ((SliderPaperWidth - (2 * moodSliderLeftPosition) - moodSliderHandlerYSize)));
        if (leftmoodBlockmood > 0)
        leftmoodBlockText.attr({ text: leftmoodBlockmood + '$' });
        else
        leftmoodBlockText.attr({ text: '0$' });
        */

    },
    moodSliderLeftHandlerUp = function () {
        // restoring state
        //moodSliderCenterRect.attr({ fill: '#999' });
        // this.attr({ opacity: .5 });
    };
    //----------------------------------------------

    // rstart and rmove are the resize functions;
    moodSliderLeftHandler.drag(moodSliderLeftHandlerMove, moodSliderLeftHandlerStart, moodSliderLeftHandlerUp);

    //----------------------===========================-----------

    ///----------------------seventh (tourists) Slider -------------------------------------

    var touristsSliderYPosition = eightsDevideLinePosition + (SliderPaperHeight * 0.06);
    var touristsSliderHeight = firstBlockRectWidth * 0.15;
    var touristsSliderHandlerXSize = touristsSliderHeight + firstBlockRectWidth * 0.02;
    var touristsSliderHandlerYSize = touristsSliderHeight + firstBlockRectWidth * 0.02;
    var touristsSliderLeftPosition = (SliderPaperWidth * 0.2);
    var touristsSliderBackRect = SliderPaper.rect((touristsSliderLeftPosition - touristsSliderHandlerYSize), (touristsSliderYPosition + firstBlockRectWidth * 0.02), (SliderPaperWidth - (2 * touristsSliderLeftPosition) + 3 * touristsSliderHandlerYSize), (touristsSliderHeight - firstBlockRectWidth * 0.04)).attr({ fill: '#ddd', stroke: "none", 'rx': '20' });
    var touristsSliderLeftBackCircle = SliderPaper.circle((touristsSliderLeftPosition - touristsSliderHandlerYSize), ((touristsSliderYPosition + firstBlockRectWidth * 0.02) + (touristsSliderHeight - firstBlockRectWidth * 0.04) / 2), (touristsSliderHeight - firstBlockRectWidth * 0.04) / 2).attr({ fill: '#ddd', stroke: "none" });
    var touristsSliderRightBackCircle = SliderPaper.circle(((touristsSliderLeftPosition - touristsSliderHandlerYSize) + (SliderPaperWidth - (2 * touristsSliderLeftPosition) + 3 * touristsSliderHandlerYSize)), ((touristsSliderYPosition + firstBlockRectWidth * 0.02) + (touristsSliderHeight - firstBlockRectWidth * 0.04) / 2), (touristsSliderHeight - firstBlockRectWidth * 0.04) / 2).attr({ fill: '#ddd', stroke: "none" });

    // var touristsSliderCenterRect = SliderPaper.rect((touristsSliderLeftPosition + touristsSliderHandlerYSize), (touristsSliderYPosition + firstBlockRectWidth * 0.02), (SliderPaperWidth - (2 * touristsSliderLeftPosition) - touristsSliderHandlerYSize), (touristsSliderHeight - firstBlockRectWidth * 0.04)).attr({ fill: [0, gradLineYellowFirstColor, gradLineYellowSecondColor].join("-"), stroke: "none" });

    touristsSliderLeftHandler = SliderPaper.rect(touristsSliderLeftPosition + ((touristsSliderLeftPosition - touristsSliderHandlerYSize) + (SliderPaperWidth - (2 * touristsSliderLeftPosition) - 5 * touristsSliderHandlerYSize)) / 2, (touristsSliderYPosition - touristsSliderHandlerYSize * 0.1), touristsSliderHandlerXSize * 1.1, touristsSliderHandlerYSize * 1.2).attr({ fill: '#fff', stroke: "#ddd", cursor: "hand", 'stroke-linejoin': "round", rx: "5", ry: "5" }),
    touristsSliderRightHandler = SliderPaper.rect((SliderPaperWidth - touristsSliderLeftPosition) + touristsSliderHandlerYSize, (touristsSliderYPosition - touristsSliderHandlerYSize * 0.1), touristsSliderHandlerXSize * 1.1, touristsSliderHandlerYSize * 1.2).attr({ fill: '#fff', stroke: "#ddd", rx: "5", ry: "5", opacity: 0 });
    //var touristsSliderLeftTopText = SliderPaper.text((SliderPaperWidth * 0.49), secondDevideLinePosition + (SliderPaperHeight * 0.04), "Температура");
    //var touristsSliderLeftPathString = 'M'+ touristsSliderLeftPosition +',' + ()
    //var touristsSliderLeftPath = SliderPaper.path(touristsSliderLeftPath);

    //j = SliderPaper.rect(0, 0, 100, 100),
    // start, move, and up are the drag functions
    touristsSliderLeftHandlerStart = function () {
        // storing original coordinates
        this.ox = this.attr("x");
        //  this.attr({ opacity: 1 });
    },
    touristsSliderLeftHandlerMove = function (dx, dy) {
        // move will be called with dx and dy
        if ((dx > 0) && (this.attr("x") < ((touristsSliderRightHandler.attr("x") - touristsSliderHandlerXSize)))) {
            if ((this.ox + dx) < ((touristsSliderRightHandler.attr("x") - touristsSliderHandlerXSize))) {
                this.attr({ x: this.ox + dx });
                //   touristsSliderCenterRect.attr({ x: (this.ox + dx), width: (touristsSliderRightHandler.attr("x") - touristsSliderCenterRect.attr("x") - touristsSliderHandlerXSize / 5) });
            }
            else {
                this.attr({ x: (touristsSliderRightHandler.attr("x") - touristsSliderHandlerXSize) });
                //     touristsSliderCenterRect.atrr({ x: (touristsSliderRightHandler.attr("x") - touristsSliderHandlerXSize), width: (touristsSliderRightHandler.attr("x") - touristsSliderCenterRect.attr("x") - touristsSliderHandlerXSize / 5) });
            }
        }
        if ((dx < 0) && (this.attr("x") > (touristsSliderLeftPosition + touristsSliderHandlerXSize)))
            if ((this.ox + dx) > (this.attr("x") > (touristsSliderLeftPosition + touristsSliderHandlerXSize))) {
                this.attr({ x: this.ox + dx });
                //     touristsSliderCenterRect.attr({ x: (this.ox + dx), width: (touristsSliderRightHandler.attr("x") - touristsSliderCenterRect.attr("x") - touristsSliderHandlerXSize / 5) });
            }
            else {
                this.attr({ x: (this.attr("x") > (touristsSliderLeftPosition + touristsSliderHandlerXSize)) });
                touristsSliderCenterRect.attr({ x: (this.attr("x") > (touristsSliderLeftPosition + touristsSliderHandlerXSize)), width: (touristsSliderRightHandler.attr("x") - touristsSliderCenterRect.attr("x") - touristsSliderHandlerXSize / 5) });
            }
        //warp
        if (this.attr("x") < (touristsSliderLeftPosition)) {
            this.attr({ x: (touristsSliderLeftPosition) });
        }

        //   touristsSliderCenterRect.attr({ x: (this.attr("x")), width: (touristsSliderRightHandler.attr("x") - touristsSliderCenterRect.attr("x")) });
        /*
        var lefttouristsBlocktourists = Math.round(((touristsSliderLeftHandler.attr("x") - (touristsSliderLeftPosition + touristsSliderHandlerYSize)) * 30) / ((SliderPaperWidth - (2 * touristsSliderLeftPosition) - touristsSliderHandlerYSize)));
        if (lefttouristsBlocktourists > 0)
        lefttouristsBlockText.attr({ text: lefttouristsBlocktourists + '$' });
        else
        lefttouristsBlockText.attr({ text: '0$' });
        */

    },
    touristsSliderLeftHandlerUp = function () {
        // restoring state
        //touristsSliderCenterRect.attr({ fill: '#999' });
        // this.attr({ opacity: .5 });
    };
    //----------------------------------------------

    // rstart and rmove are the resize functions;
    touristsSliderLeftHandler.drag(touristsSliderLeftHandlerMove, touristsSliderLeftHandlerStart, touristsSliderLeftHandlerUp);

    //----------------------===========================-----------
    ///----------------------eights (security) Slider -------------------------------------

    var securitySliderYPosition = ninthDevideLinePosition + (SliderPaperHeight * 0.06);
    var securitySliderHeight = firstBlockRectWidth * 0.15;
    var securitySliderHandlerXSize = securitySliderHeight + firstBlockRectWidth * 0.02;
    var securitySliderHandlerYSize = securitySliderHeight + firstBlockRectWidth * 0.02;
    var securitySliderLeftPosition = (SliderPaperWidth * 0.2);
    var securitySliderBackRect = SliderPaper.rect((securitySliderLeftPosition - securitySliderHandlerYSize), (securitySliderYPosition + firstBlockRectWidth * 0.02), (SliderPaperWidth - (2 * securitySliderLeftPosition) + 3 * securitySliderHandlerYSize), (securitySliderHeight - firstBlockRectWidth * 0.04)).attr({ fill: '#ddd', stroke: "none", 'rx': '20' });
    var securitySliderLeftBackCircle = SliderPaper.circle((securitySliderLeftPosition - securitySliderHandlerYSize), ((securitySliderYPosition + firstBlockRectWidth * 0.02) + (securitySliderHeight - firstBlockRectWidth * 0.04) / 2), (securitySliderHeight - firstBlockRectWidth * 0.04) / 2).attr({ fill: '#ddd', stroke: "none" });
    var securitySliderRightBackCircle = SliderPaper.circle(((securitySliderLeftPosition - securitySliderHandlerYSize) + (SliderPaperWidth - (2 * securitySliderLeftPosition) + 3 * securitySliderHandlerYSize)), ((securitySliderYPosition + firstBlockRectWidth * 0.02) + (securitySliderHeight - firstBlockRectWidth * 0.04) / 2), (securitySliderHeight - firstBlockRectWidth * 0.04) / 2).attr({ fill: '#ddd', stroke: "none" });

    // var securitySliderCenterRect = SliderPaper.rect((securitySliderLeftPosition + securitySliderHandlerYSize), (securitySliderYPosition + firstBlockRectWidth * 0.02), (SliderPaperWidth - (2 * securitySliderLeftPosition) - securitySliderHandlerYSize), (securitySliderHeight - firstBlockRectWidth * 0.04)).attr({ fill: [0, gradLineYellowFirstColor, gradLineYellowSecondColor].join("-"), stroke: "none" });

    securitySliderLeftHandler = SliderPaper.rect(securitySliderLeftPosition + ((securitySliderLeftPosition - securitySliderHandlerYSize) + (SliderPaperWidth - (2 * securitySliderLeftPosition) - 5 * securitySliderHandlerYSize)) / 2, (securitySliderYPosition - securitySliderHandlerYSize * 0.1), securitySliderHandlerXSize * 1.1, securitySliderHandlerYSize * 1.2).attr({ fill: '#fff', stroke: "#ddd", cursor: "hand", 'stroke-linejoin': "round", rx: "5", ry: "5" }),
    securitySliderRightHandler = SliderPaper.rect((SliderPaperWidth - securitySliderLeftPosition) + securitySliderHandlerYSize, (securitySliderYPosition - securitySliderHandlerYSize * 0.1), securitySliderHandlerXSize * 1.1, securitySliderHandlerYSize * 1.2).attr({ fill: '#fff', stroke: "#ddd", rx: "5", ry: "5", opacity: 0 });
    //var securitySliderLeftTopText = SliderPaper.text((SliderPaperWidth * 0.49), secondDevideLinePosition + (SliderPaperHeight * 0.04), "Температура");
    //var securitySliderLeftPathString = 'M'+ securitySliderLeftPosition +',' + ()
    //var securitySliderLeftPath = SliderPaper.path(securitySliderLeftPath);

    //j = SliderPaper.rect(0, 0, 100, 100),
    // start, move, and up are the drag functions
    securitySliderLeftHandlerStart = function () {
        // storing original coordinates
        this.ox = this.attr("x");
        //  this.attr({ opacity: 1 });
    },
    securitySliderLeftHandlerMove = function (dx, dy) {
        // move will be called with dx and dy
        if ((dx > 0) && (this.attr("x") < ((securitySliderRightHandler.attr("x") - securitySliderHandlerXSize)))) {
            if ((this.ox + dx) < ((securitySliderRightHandler.attr("x") - securitySliderHandlerXSize))) {
                this.attr({ x: this.ox + dx });
                //   securitySliderCenterRect.attr({ x: (this.ox + dx), width: (securitySliderRightHandler.attr("x") - securitySliderCenterRect.attr("x") - securitySliderHandlerXSize / 5) });
            }
            else {
                this.attr({ x: (securitySliderRightHandler.attr("x") - securitySliderHandlerXSize) });
                //     securitySliderCenterRect.atrr({ x: (securitySliderRightHandler.attr("x") - securitySliderHandlerXSize), width: (securitySliderRightHandler.attr("x") - securitySliderCenterRect.attr("x") - securitySliderHandlerXSize / 5) });
            }
        }
        if ((dx < 0) && (this.attr("x") > (securitySliderLeftPosition + securitySliderHandlerXSize)))
            if ((this.ox + dx) > (this.attr("x") > (securitySliderLeftPosition + securitySliderHandlerXSize))) {
                this.attr({ x: this.ox + dx });
                //     securitySliderCenterRect.attr({ x: (this.ox + dx), width: (securitySliderRightHandler.attr("x") - securitySliderCenterRect.attr("x") - securitySliderHandlerXSize / 5) });
            }
            else {
                this.attr({ x: (this.attr("x") > (securitySliderLeftPosition + securitySliderHandlerXSize)) });
                securitySliderCenterRect.attr({ x: (this.attr("x") > (securitySliderLeftPosition + securitySliderHandlerXSize)), width: (securitySliderRightHandler.attr("x") - securitySliderCenterRect.attr("x") - securitySliderHandlerXSize / 5) });
            }
        //warp
        if (this.attr("x") < (securitySliderLeftPosition)) {
            this.attr({ x: (securitySliderLeftPosition) });
        }

        //   securitySliderCenterRect.attr({ x: (this.attr("x")), width: (securitySliderRightHandler.attr("x") - securitySliderCenterRect.attr("x")) });
        /*
        var leftsecurityBlocksecurity = Math.round(((securitySliderLeftHandler.attr("x") - (securitySliderLeftPosition + securitySliderHandlerYSize)) * 30) / ((SliderPaperWidth - (2 * securitySliderLeftPosition) - securitySliderHandlerYSize)));
        if (leftsecurityBlocksecurity > 0)
        leftsecurityBlockText.attr({ text: leftsecurityBlocksecurity + '$' });
        else
        leftsecurityBlockText.attr({ text: '0$' });
        */

    },
    securitySliderLeftHandlerUp = function () {
        // restoring state
        //securitySliderCenterRect.attr({ fill: '#999' });
        // this.attr({ opacity: .5 });
    };
    //----------------------------------------------

    // rstart and rmove are the resize functions;
    securitySliderLeftHandler.drag(securitySliderLeftHandlerMove, securitySliderLeftHandlerStart, securitySliderLeftHandlerUp);

    //----------------------===========================-----------
    //----------------------===========================-----------
    ///----------------------ninths (sex) Slider -------------------------------------

    var sexSliderYPosition = tenthDevideLinePosition + (SliderPaperHeight * 0.06);
    var sexSliderHeight = firstBlockRectWidth * 0.15;
    var sexSliderHandlerXSize = sexSliderHeight + firstBlockRectWidth * 0.02;
    var sexSliderHandlerYSize = sexSliderHeight + firstBlockRectWidth * 0.02;
    var sexSliderLeftPosition = (SliderPaperWidth * 0.2);
    var sexSliderBackRect = SliderPaper.rect((sexSliderLeftPosition - sexSliderHandlerYSize), (sexSliderYPosition + firstBlockRectWidth * 0.02), (SliderPaperWidth - (2 * sexSliderLeftPosition) + 3 * sexSliderHandlerYSize), (sexSliderHeight - firstBlockRectWidth * 0.04)).attr({ fill: '#ddd', stroke: "none", 'rx': '20' });
    var sexSliderLeftBackCircle = SliderPaper.circle((sexSliderLeftPosition - sexSliderHandlerYSize), ((sexSliderYPosition + firstBlockRectWidth * 0.02) + (sexSliderHeight - firstBlockRectWidth * 0.04) / 2), (sexSliderHeight - firstBlockRectWidth * 0.04) / 2).attr({ fill: '#ddd', stroke: "none" });
    var sexSliderRightBackCircle = SliderPaper.circle(((sexSliderLeftPosition - sexSliderHandlerYSize) + (SliderPaperWidth - (2 * sexSliderLeftPosition) + 3 * sexSliderHandlerYSize)), ((sexSliderYPosition + firstBlockRectWidth * 0.02) + (sexSliderHeight - firstBlockRectWidth * 0.04) / 2), (sexSliderHeight - firstBlockRectWidth * 0.04) / 2).attr({ fill: '#ddd', stroke: "none" });

    // var sexSliderCenterRect = SliderPaper.rect((sexSliderLeftPosition + sexSliderHandlerYSize), (sexSliderYPosition + firstBlockRectWidth * 0.02), (SliderPaperWidth - (2 * sexSliderLeftPosition) - sexSliderHandlerYSize), (sexSliderHeight - firstBlockRectWidth * 0.04)).attr({ fill: [0, gradLineYellowFirstColor, gradLineYellowSecondColor].join("-"), stroke: "none" });

    sexSliderLeftHandler = SliderPaper.rect(sexSliderLeftPosition + ((sexSliderLeftPosition - sexSliderHandlerYSize) + (SliderPaperWidth - (2 * sexSliderLeftPosition) - 5 * sexSliderHandlerYSize)) / 2, (sexSliderYPosition - sexSliderHandlerYSize * 0.1), sexSliderHandlerXSize * 1.1, sexSliderHandlerYSize * 1.2).attr({ fill: '#fff', stroke: "#ddd", cursor: "hand", 'stroke-linejoin': "round", rx: "5", ry: "5" }),
    sexSliderRightHandler = SliderPaper.rect((SliderPaperWidth - sexSliderLeftPosition) + sexSliderHandlerYSize, (sexSliderYPosition - sexSliderHandlerYSize * 0.1), sexSliderHandlerXSize * 1.1, sexSliderHandlerYSize * 1.2).attr({ fill: '#fff', stroke: "#ddd", rx: "5", ry: "5", opacity: 0 });
    //var sexSliderLeftTopText = SliderPaper.text((SliderPaperWidth * 0.49), secondDevideLinePosition + (SliderPaperHeight * 0.04), "Температура");
    //var sexSliderLeftPathString = 'M'+ sexSliderLeftPosition +',' + ()
    //var sexSliderLeftPath = SliderPaper.path(sexSliderLeftPath);

    //j = SliderPaper.rect(0, 0, 100, 100),
    // start, move, and up are the drag functions
    sexSliderLeftHandlerStart = function () {
        // storing original coordinates
        this.ox = this.attr("x");
        //  this.attr({ opacity: 1 });
    },
    sexSliderLeftHandlerMove = function (dx, dy) {
        // move will be called with dx and dy
        if ((dx > 0) && (this.attr("x") < ((sexSliderRightHandler.attr("x") - sexSliderHandlerXSize)))) {
            if ((this.ox + dx) < ((sexSliderRightHandler.attr("x") - sexSliderHandlerXSize))) {
                this.attr({ x: this.ox + dx });
                //   sexSliderCenterRect.attr({ x: (this.ox + dx), width: (sexSliderRightHandler.attr("x") - sexSliderCenterRect.attr("x") - sexSliderHandlerXSize / 5) });
            }
            else {
                this.attr({ x: (sexSliderRightHandler.attr("x") - sexSliderHandlerXSize) });
                //     sexSliderCenterRect.atrr({ x: (sexSliderRightHandler.attr("x") - sexSliderHandlerXSize), width: (sexSliderRightHandler.attr("x") - sexSliderCenterRect.attr("x") - sexSliderHandlerXSize / 5) });
            }
        }
        if ((dx < 0) && (this.attr("x") > (sexSliderLeftPosition + sexSliderHandlerXSize)))
            if ((this.ox + dx) > (this.attr("x") > (sexSliderLeftPosition + sexSliderHandlerXSize))) {
                this.attr({ x: this.ox + dx });
                //     sexSliderCenterRect.attr({ x: (this.ox + dx), width: (sexSliderRightHandler.attr("x") - sexSliderCenterRect.attr("x") - sexSliderHandlerXSize / 5) });
            }
            else {
                this.attr({ x: (this.attr("x") > (sexSliderLeftPosition + sexSliderHandlerXSize)) });
                sexSliderCenterRect.attr({ x: (this.attr("x") > (sexSliderLeftPosition + sexSliderHandlerXSize)), width: (sexSliderRightHandler.attr("x") - sexSliderCenterRect.attr("x") - sexSliderHandlerXSize / 5) });
            }
        //warp
        if (this.attr("x") < (sexSliderLeftPosition)) {
            this.attr({ x: (sexSliderLeftPosition) });
        }
        //   sexSliderCenterRect.attr({ x: (this.attr("x")), width: (sexSliderRightHandler.attr("x") - sexSliderCenterRect.attr("x")) });
        /*
        var leftsexBlocksex = Math.round(((sexSliderLeftHandler.attr("x") - (sexSliderLeftPosition + sexSliderHandlerYSize)) * 30) / ((SliderPaperWidth - (2 * sexSliderLeftPosition) - sexSliderHandlerYSize)));
        if (leftsexBlocksex > 0)
        leftsexBlockText.attr({ text: leftsexBlocksex + '$' });
        else
        leftsexBlockText.attr({ text: '0$' });
        */

    },
    sexSliderLeftHandlerUp = function () {
        // restoring state
        //sexSliderCenterRect.attr({ fill: '#999' });
        // this.attr({ opacity: .5 });
    };
    //----------------------------------------------

    // rstart and rmove are the resize functions;
    sexSliderLeftHandler.drag(sexSliderLeftHandlerMove, sexSliderLeftHandlerStart, sexSliderLeftHandlerUp);

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
        var ninthBlockVisaText = SliderPaper.text((SliderPaperWidth - firstBlockRectOffset) - 40, eleventhDevideLinePosition + (SliderPaperHeight * 0.027), "Без Визы");
    else
        var ninthBlockVisaText = SliderPaper.text((SliderPaperWidth - firstBlockRectOffset) - 30, eleventhDevideLinePosition + (SliderPaperHeight * 0.023), "Без Визы");
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
        if (newWidth < 900)
            newWidth = 900;
        if (newHeight < 600)
            newHeight = 600;

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
            tourText.translate(-15, 0);
            aviaText.translate(-52, 0);
            autoText.translate(-126, 0);
            hotelText.translate(-89, 0);
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
            tourText.translate(15, 0);
            aviaText.translate(52, 0);
            autoText.translate(126, 0);
            hotelText.translate(89, 0);
        }
        if (tourRectIsActive[0] == true) {
            tourRect.animate({ path: tourPathAnimated }, 20);
            tourRectOver.animate({ path: tourPathAnimated }, 20);
        }
        if (tourRectIsActive[1] == true) {
            aviaRect.animate({ path: tourPathAnimated }, 20);
            aviaRectOver.animate({ path: tourPathAnimated }, 20);
        }
        if (tourRectIsActive[2] == true) {
            hotelRect.animate({ path: tourPathAnimated }, 20);
            hotelRectOver.animate({ path: tourPathAnimated }, 20);
        }
        if (tourRectIsActive[3] == true) {
            autoRect.animate({ path: tourPathAnimated }, 20);
            autoRectOver.animate({ path: tourPathAnimated }, 20);
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
                el.scale(1, newHeight / Height, 0, 0);
            }
        });

        Height = newHeight;
        Width = newWidth;
        bottomSwitcherWidth = newBottomSwitcherWidth;

        //------------------------
        //Fonts
        //------------------------
        if (newWidth < 2500)
            bottomFont = 18;
        if (newWidth < 2000)
            bottomFont = 16;
        if (newWidth < 1500)
            bottomFont = 14;
        if (newWidth < 1100)
            bottomFont = 12;

        thirdBlockText.remove();
        thirdBlockText = SliderPaper.text((SliderPaperWidth * 0.53), secondDevideLinePosition + (SliderPaperHeight * 0.04), "Температура");
        thirdBlockText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
        firstBlockText.remove();
        firstBlockText = SliderPaper.text((SliderPaperWidth * 0.5), (SliderPaperHeight * 0.02), "Кто едет?");
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
        tenthBlockText = SliderPaper.text((SliderPaperWidth * 0.53), ninthDevideLinePosition + (SliderPaperHeight * 0.03), "Безопасность");
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

        selfRectText.remove();
        coupleRectText.remove();
        familyRectText.remove();
        friendsRectText.remove();
        firstBlockTextYPosition = (SliderPaperHeight * 0.05) + firstBlockRectWidth;
        selfRectText = SliderPaper.text((firstBlockRectOffset + firstBlockRectWidth * 0.5), firstBlockTextYPosition, "Один");
        coupleRectText = SliderPaper.text((firstBlockRectOffset + firstBlockBetweenRectWidth + (firstBlockRectWidth) * 1.5), firstBlockTextYPosition, "Вдвоем");
        familyRectText = SliderPaper.text((firstBlockRectOffset + firstBlockBetweenRectWidth * 2 + (firstBlockRectWidth) * 2.5), firstBlockTextYPosition, "Семья");
        friendsRectText = SliderPaper.text((firstBlockRectOffset + firstBlockBetweenRectWidth * 3 + (firstBlockRectWidth) * 3.5), firstBlockTextYPosition, "Друзья");

        selfRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
        coupleRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
        familyRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
        friendsRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });

        tanRectText.remove();
        skiRectText.remove();
        watchRectText.remove();
        shoppingRectText.remove();
        cruiseRectText.remove();
        secondBlockTextYPosition = (SliderPaperHeight * 0.05 + firstDevideLinePosition) + secondBlockRectWidth;
        tanRectText = SliderPaper.text((secondBlockRectOffset + secondBlockRectWidth * 0.5), secondBlockTextYPosition, "Загарать");
        skiRectText = SliderPaper.text((secondBlockRectOffset + secondBlockBetweenRectWidth + (secondBlockRectWidth) + secondBlockRectWidth * 0.5), secondBlockTextYPosition, "Кататься");
        watchRectText = SliderPaper.text((secondBlockRectOffset + secondBlockBetweenRectWidth * 2 + (secondBlockRectWidth) * 2 + secondBlockRectWidth * 0.5), secondBlockTextYPosition, "Смотреть");
        shoppingRectText = SliderPaper.text((secondBlockRectOffset + secondBlockBetweenRectWidth * 3 + (secondBlockRectWidth) * 3 + secondBlockRectWidth * 0.5), secondBlockTextYPosition, "Шоппинг");
        cruiseRectText = SliderPaper.text((secondBlockRectOffset + secondBlockBetweenRectWidth * 4 + (secondBlockRectWidth) * 4 + secondBlockRectWidth * 0.5), secondBlockTextYPosition, "Круиз");

        tanRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
        skiRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
        watchRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
        shoppingRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
        cruiseRectText.attr({ 'font-size': (bottomFont * 3) / 4, fill: gradLineVioletFirstColor });
        //FirstBlock       
        firstBlockRectOffset = (SliderPaperWidth - (firstBlockRectWidth * 4 + firstBlockBetweenRectWidth * 3)) / 2;
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

        //SecondBlock
        secondBlockRectOffset = (SliderPaperWidth - (secondBlockRectWidth * 5 + secondBlockBetweenRectWidth * 4)) / 2;
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

        //secondBlockTextYPosition = (SliderPaperHeight * 0.05 + firstDevideLinePosition) + secondBlockRectWidth;
        /*
        ninthBlockRussianText.remove();
        if (newWidth > 1350)
        ninthBlockRussianText = SliderPaper.text((firstBlockRectOffset) * 1.3, eleventhDevideLinePosition + (SliderPaperHeight * 0.028), "Русский язык");
        else
        if (newWidth > 1200)
        ninthBlockRussianText = SliderPaper.text((firstBlockRectOffset) * 1.2, eleventhDevideLinePosition + (SliderPaperHeight * 0.023), "Русский язык");
        else
        if (newWidth < 1000)
        ninthBlockRussianText = SliderPaper.text((firstBlockRectOffset) * 1.1, eleventhDevideLinePosition + (SliderPaperHeight * 0.023), "Русский язык");
        ninthBlockRussianText.attr({ 'font-size': bottomFont, fill: gradLineVioletFirstColor });
        */
        SliderPaperHeight = oldSliderPaperWidth
        SliderPaperWidth = SliderPaperHeight
        //MonthPaperWidth = oldMonthPaperWidth
        //MonthPaperHeight = oldMonthPaperHeight

    });
    //  alert("sucsessfull build");
}

/**/
