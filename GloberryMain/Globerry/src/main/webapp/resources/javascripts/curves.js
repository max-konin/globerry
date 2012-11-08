/*
 * Создаем функцию для простого копирования массива и удаления
 */
Array.prototype.copy = function() {
	return [].concat(this);
};
Array.prototype.remove = function(number) {
	return this.splice(number, 1);
};
Array.prototype.getLast = function() {
	if (this.length == 0)
		return null;
	return this[this.length - 1];
};

/*
 * Функция для отрисовки кривулин
 */
function Curves(/* L.LatLng[] */centers,/* L.Map */lmap) {
	var canvas = ConnectorCurvesToMap(lmap), stepX = 0.6, stepY = 0.6, points = centers, border = true, circles = false, polygonOptions = {
		color : "#ff4d29",
		fillColor :"#ff4d29",
		weight : 1,
		fillOpacity : 0.5,
		opacity : 1
	},
	/*
	 * Функция определяющая расчет потенциала
	 */
	Z = function Z(x, y) {
		var val = [];
		var pointXY = new L.LatLng(y, x);
		for ( var i = 0, l = points.length; i < l; i++) {
			var point = points[i];
			buf = new L.LatLng(point.latitude, point.longitude)
			val.push(point.weight / buf.distanceTo(pointXY));
		}
		val = val.sort(function(l, r) {
			if (l > r)
				return 1;
			return -1;
		});
		return val.getLast() * 1000000;
	}, 
		redrawCurvesNew = function redrawCurvesNew(curvesEntity)
		{
			for(var i = 0; i < curvesEntity.length; ++i)
			{
				var tPolygon = new L.ExPolygon(curvesEntity[i].points, polygonOptions,curvesEntity[i].cityList);
				canvas.putPolygon(tPolygon);	
			}
			canvas.removeAll();
	    },
	    redrawMarkers = function redrawMarkers(cities)
	    {
	    	cities.forEach(function(o) {
        		canvas.putPolygon(new L.ExMarker(new L.LatLng(o.latitude, o.longitude),o));
            });
	    	canvas.removeAll();
	    },
	    appendDefs = function appendDefs() {
	    var NS = "http://www.w3.org/2000/svg";
	    function createElement(name) {
	        return document.createElementNS(NS,name);
	    }
	    
		var svg = createElement('svg');
	    var defs = createElement('defs');
	    var pattern = createElement('pattern');
	    defs.appendChild(pattern);
	    pattern.setAttribute('id', 'lines');
	            
	    pattern.setAttribute('patternUnits', 'userSpaceOnUse');
	    pattern.setAttribute('x', '0');
	    pattern.setAttribute('y', '0');
		//Размер паттерна
	    pattern.setAttribute('width', '8');
	    pattern.setAttribute('height', '8');
	    
		//Содержимое паттерна(svg-объекты)
		//Закраска всего паттерна цветом заливки
	    var children = createElement('rect');
	    children.setAttribute('width', '8');
	    children.setAttribute('height', '8');
		children.setAttribute('fill', '#ff4d29');
	    pattern.appendChild(children);
	    
		//Прямые линии
	    children = createElement('path');
	    children.setAttribute('d', 'M -1 5 L 5 -1');
	    children.setAttribute('style', 'stroke: #ffffff; stroke-width: 2; opacity: 1');
	    pattern.appendChild(children);
	    children = createElement('path');
	    children.setAttribute('d', 'M 9 3 L 3 9');
	    children.setAttribute('style', 'stroke: #ffffff; stroke-width: 2; opacity: 1');
	    pattern.appendChild(children);
		
		svg.appendChild(defs);
		
	    $("div.leaflet-overlay-pane").prepend(svg);
	};
	canvas.removeAll();
	var me = {
		redrawMarkers : redrawMarkers,
		redrawCurvesNew : redrawCurvesNew,
		canvas : canvas,
		appendDefs : appendDefs
	};
	return me;
}

function ConnectorCurvesToMap(/* L.Map */lmap) {
	var polygons = [ [], [] ], zlevelNormalizer = {
		2 : 14,
		3 : 18,
		4 : 22,
		5 : 25,
		6 : 33,
		7 : 37,
		8 : 40
	}, /** Индекс - это номер зума, значение - zlevel.* */

	radiusNormalizer = {
		2 : 75,
		3 : 50,
		4 : 35,
		5 : 25,
		6 : 20,
		7 : 15,
		8 : 10
	}, /** Индекс - это номер зума, значение - radius.* */

	currentZoom = lmap.getZoom(),

	setZLevel = function setZLevel(zlevel) {
		zlevelNormalizer[currentZoom] = zlevel;
		me.zlevel = zlevel;
	},

	setZoom = function setZoom(zoom) {
		currentZoom = zoom;
		me.zlevel = zlevelNormalizer[currentZoom];
		me.currentZoom = zoom;
	},
	
	setRadius = function setRadius(radius) {
		radiusNormalizer[currentZoom] = radius;
		me.radiusCircle = radius;
	},

	putPolygon = function putPolygon(/* L.ExPolygon */polygon) {
		polygons[1].push(polygon);
		lmap.addLayer(polygon);
	},

	removeAll = function removeAll() {
		for ( var i = 0; i < polygons[0].length; i++) {
			lmap.removeLayer(polygons[0][i]);
		}
		polygons[0] = polygons[1];
		polygons[1] = [];
	},

	me = {
		putPolygon : putPolygon,
		removeAll : removeAll,
		setZLevel : setZLevel,
		setRadius : setRadius,
		zlevel : zlevelNormalizer[currentZoom],
		radiusNormalizer : radiusNormalizer,
		currentZoom : currentZoom,
		setZoom : setZoom
	};




	return me;
}

function drawCircle(x, y, color, weight, radius, city,fillColor) {
	return new L.ExCircle(new L.LatLng(y, x, true), typeof radius == "number" ? radius : 5000, {
		color : typeof color == "string" ? color : "red",
		fillColor :  fillColor, //== "string" ? fillColor : (color ? color : "red"),
		weight : typeof weight == "number" ? weight : 20,
		fillOpacity : 0.5,
		opacity : 0.5
	}, city);
}

