/**
 Эта функция добавляет тэг <defs> к тегу <svg>, чтобы активизировать возможность создания градиента. Leaflet не умеет
 делать этого по умолчанию.
 **/
function appendSVGGradientData(zoomLevel) {
    var NS = "http://www.w3.org/2000/svg";
    function createElement(name) {
        return document.createElementNS(NS,name);
    }
    
    
    var defs = createElement('defs');
    var gradient = createElement('radialGradient');
    defs.appendChild(gradient);
    gradient.setAttribute('id', 'grad1');
            
    gradient.setAttribute('cx', '50%');
    gradient.setAttribute('cy', '50%');
    gradient.setAttribute('r', '50%');
    gradient.setAttribute('fx', '50%');
    gradient.setAttribute('fy', '50%');
            
    var stop = createElement('stop');
    stop.setAttribute('offset', '0%');
    stop.setAttribute('style', 'stop-color:'+bubbleParams[zoomLevel].start_color+';stop-opacity:' + bubbleParams[zoomLevel].start_opacity);
    gradient.appendChild(stop);
            
    stop = createElement('stop');
    stop.setAttribute('offset', '100%');
    stop.setAttribute('style', 'stop-color:'+bubbleParams[zoomLevel].finish_color+';stop-opacity:' + bubbleParams[zoomLevel].finish_opacity);
    gradient.appendChild(stop);
    
    var svg = document.getElementsByTagName('svg')[0];
    if(svg)
        svg.appendChild(defs);
    
    
    
}
//Параметры кружочков на каждом уровне
var bubbleParams = {
    2 : {
        start_opacity : 0.8,
        finish_opacity : 0,
        start_color : '#ffa500',
        finish_color : '#ffa500'
    },
    3 : {
        start_opacity : 0.8,
        finish_opacity : 0,
        start_color : '#ffa500',
        finish_color : '#ffa500'
    },
    4 : {
        start_opacity : 0.8,
        finish_opacity : 0,
        start_color : '#ffa500',
        finish_color : '#ffa500'
    },
    5 : {
        start_opacity : 0.8,
        finish_opacity : 0,
        start_color : '#ffa500',
        finish_color : '#ffa500'
    },
    6 : {
        start_opacity : 0.8,
        finish_opacity : 0,
        start_color : '#ffa500',
        finish_color : '#ffa500'
    },
    7 : {
        start_opacity : 0.8,
        finish_opacity : 0,
        start_color : '#ffa500',
        finish_color : '#ffa500'
    },
    8 : {
        start_opacity : 0.8,
        finish_opacity : 0,
        start_color : '#ffa500',
        finish_color : '#ffa500'
    }
}
function BubblesInit(bubbleCanvas, initialBubbles) {
    var bubbles = {};
    var canvas = bubbleCanvas;
    $(initialBubbles).each(function(index, value) {
        var bubble = {
            name : value['name'],
            lng : value['longitude'],
            lat : value['latitude'],
            weight : value['weight']
        };
        bubbles[value.id] = bubble;
    });
    function update(newBubbles) {
        for(var key in bubbles) {
            canvas.removeBubble(key);
            delete bubbles[key];
        }
        for(key in newBubbles) {
            var bubble = {
                name : newBubbles[key]['name'],
                lng : newBubbles[key]['longitude'],
                lat : newBubbles[key]['latitude'],
                weight : newBubbles[key]['weight']
            };
            bubbles[key] = bubble;
        }
        draw();
    }
    function draw() {
        for(var id in bubbles) {
            canvas.putBubble(id, bubbles[id]);
        }
    }
    var me = {
        update : update,
        draw : draw,
        setZoom : bubbleCanvas.setZoom,
        getRadius : bubbleCanvas.getRadius
    }
    return me;
}

function BubbleFieldProvider(/*Это объект L.Map*/lmap) {
    
    var map = lmap;
    var mapObjects = [];
    var zoomNormalizer = {
        2 : 150000,
        3 : 200000,
        4 : 200000,
        5 : 100000,
        6 : 100000,
        7 : 40000,
        8 : 20000
    };/** Индекс - это номер зума, значение - множитель, на которой домножаются размеры.**/
    var currentZoom = map.getZoom();
    function putBubble(bubbleId, bubble) {
        var radius = bubble.weight * zoomNormalizer[currentZoom];
        var circle = new L.Circle(new L.LatLng(bubble.lat, bubble.lng), radius, circleOptions);
        circle.radius = radius;
        mapObjects[bubbleId] = circle;
        map.addLayer(circle);
    }
    
    function removeBubble(bubbleId) {
        map.removeLayer(mapObjects[bubbleId]);
    }
    
    function changeBubble(bubbleId, bubble) {
        var radius = bubble.weight * zoomNormalizer[currentZoom];
        var circle = mapObjects[bubbleId];
        if(!circle)
            return;
        circle.setRadius(radius);
        circle.radius = radius;
    }
    function setZoom(value) {
        if(value < 100)
            return;
        currentZoom = map.getZoom();
        var prevValue = zoomNormalizer[currentZoom];
        if(!prevValue) return;
        zoomNormalizer[currentZoom] = value;
        for(var key in mapObjects) {
           var circle = mapObjects[key];
           var r = value;
           circle.setRadius(r);
           circle.radius = r;
       }
    }
    function getRadius() {
        return zoomNormalizer[map.getZoom()];
    }
    var me = {
        putBubble : putBubble,
        removeBubble : removeBubble,
        changeBubble : changeBubble,
        setZoom : setZoom,
        getRadius : getRadius
    }
    
    /**Подписываемся на события карты**/
    map.on('zoomend', function(e) {
       var prevZoom = currentZoom;
       currentZoom = map.getZoom();
       for(var key in mapObjects) {
           var circle = mapObjects[key];
           var r = circle.radius*zoomNormalizer[currentZoom]/zoomNormalizer[prevZoom];
           circle.setRadius(r);
           circle.radius = r;
       }
    });
    
    return me;
    
}