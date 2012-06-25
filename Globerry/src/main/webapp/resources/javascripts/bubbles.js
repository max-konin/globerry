var initialBubbles = [{"id":2,"name":"Paris","ru_name":null,"area":0.0,"population":0,"longitude":2.19,"latitude":48.56,"isValid":null,"message":null,"weight":0.8470437},
    {"id":3,"name":"Praha","ru_name":null,"area":0.0,"population":0,"longitude":14.4,"latitude":50.8,"isValid":null,"message":null,"weight":0.88343906},
    {"id":5,"name":"Amsterdam","ru_name":null,"area":0.0,"population":0,"longitude":4.9,"latitude":52.35,"isValid":null,"message":null,"weight":0.80380994},
    {"id":11,"name":"Warszawa","ru_name":null,"area":0.0,"population":0,"longitude":21.05,"latitude":52.2,"isValid":null,"message":null,"weight":0.92557275},
    {"id":13,"name":"Kopengagen","ru_name":null,"area":0.0,"population":0,"longitude":12.58,"latitude":55.65,"isValid":null,"message":null,"weight":0.8627029}]
/**
 * Стиль заливки кругов.
 **/
var circleOptions = {
    color: 'orange',
    fill : 'url(#grad1)'
}
/**
 Эта функция добавляет тэг <defs> к тегу <svg>, чтобы активизировать возможность создания градиента. Leaflet не умеет
 делать этого по умолчанию.
 **/
function appendSVGGradientData() {
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
    stop.setAttribute('style', 'stop-color:orange;stop-opacity:1');
    gradient.appendChild(stop);
            
    stop = createElement('stop');
    stop.setAttribute('offset', '95%');
    stop.setAttribute('style', 'stop-color:orange;stop-opacity:0');
    gradient.appendChild(stop);
    stop = createElement('stop');
    stop.setAttribute('offset', '100%');
    stop.setAttribute('style', 'stop-color:orange;stop-opacity:0.5');
    gradient.appendChild(stop);
    
    var svg = document.getElementsByTagName('svg')[0];
    svg.appendChild(defs);
    
    
    
}
function BubblesInit(bubbleCanvas) {
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
    function update() {
        
    }
    function draw() {
        for(var id in bubbles) {
            canvas.putBubble(id, bubbles[id]);
        }
    }
    var me = {
        update : update,
        draw : draw
    }
    return me;
}

function BubbleFieldProvider(/*Это объект L.Map*/lmap) {
    
    var map = lmap;
    var mapObjects = [];
    var zoomNormalizer = {
        3 : 200000,
        4 : 750000,
        5 : 1250000,
        6 : 17500000,
        7 : 1
    };/** Индекс - это номер зума, значение - множитель, на которой домножаются размеры.**/
    var currentZoom = 3;
    function putBubble(bubbleId, bubble) {
        var radius = bubble.weight * zoomNormalizer[currentZoom];
        var circle = new L.Circle(new L.LatLng(bubble.lat, bubble.lng), radius, circleOptions)
        circle.radius = radius;
        mapObjects[bubbleId] = circle;
        map.addLayer(circle);
    }
    
    function removeBubble(bubbleId) {
        map.removeLayer(mapObjects[bubbleId]);
    }
    
    function changeBubble(bubbleId) {
        
    }
    var me = {
        putBubble : putBubble,
        removeBubble : removeBubble,
        changeBubble : changeBubble
    }
    
    /**Подписываемся на события карты**/
    map.on('zoomend', function(e) {
       var prevZoom = currentZoom;
       currentZoom = map.getZoom();
       
       for(var key in mapObjects) {
           var circle = mapObjects[key];
           var z = currentZoom/prevZoom;
           //circle.setRadius(circle.getRadius() * z); 
           circle.setRadius(circle.radius * z);
       }
    });
    
    return me;
    
}