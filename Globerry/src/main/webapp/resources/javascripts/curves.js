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
	return this[this.length - 1];
};

/*
 * Функция для отрисовки кривулин
 */
function Curves(/*L.LatLng[]*/centers,/*L.Map*/ lmap) {
	var canvas = ConnectorCurvesToMap(lmap),
	stepX = 0.15,
	stepY = 0.7,
	points = centers,
	polygonOptions = {
		color: "orange", 
		smoothFactor : 0.2,
		weight: 1,
		fillOpacity: 0.5
	},
	/* 
		* Функция определяющая расчет потенциала
		*/
	Z = function Z(x, y) {
		var val = 0;
		var pointXY = new L.LatLng(y, x);
		for(var i = 0, l = points.length; i < l; i++) {
			var point = points[i];
			val += point.weight/point.distanceTo(pointXY);
		}
		return val * 1000000;
	},
	/*
		* Производная функции расчета потенциала
		*/
	dZdxy = function dZdxy(x, y) {
		var dx = 0, dy = 0;
		var pointXY = new L.LatLng(y, x);
		for(var i = 0, l = points.length; i < l; i++) {
			var point = points[i];
			var a = point.lng, b = point.lat;
			//Нужно чтобы не вычилять три раза корень из (x-x_i)^2+(y-y_i)^2 при возведении в куб
			var bigSqrt = point.distanceTo(pointXY);
			var znamenatel = bigSqrt * bigSqrt * bigSqrt;
			dx += (a - x) * point.weight * Math.pow(10, 18) / znamenatel;
			dy += (b - y) * point.weight * Math.pow(10, 18) / znamenatel;
		}
		return new L.LatLng(dy, dx);
	},
	drawCurves = function drawCurves() {
		var pointsRemaining = points.copy();
		if(canvas.currentZoom > 6) {
			for(var i = pointsRemaining.length - 1; i >= 0; i--) {
				////////////////////////////////////////////////
				if(typeof(pointsRemaining[i]) == 'undefined')
					continue;
				///////////////////////////////////////////////
				var circle = new L.ExCircle(pointsRemaining[i], canvas.radiusCircle, polygonOptions);
				for(var j = pointsRemaining.length - 1; j >= 0; j--) {
					if(circle.pointInPolygon(pointsRemaining[j])) {
						pointsRemaining.remove(j);					
					}
				}
				canvas.putPolygon(circle);
			}
			return;
		}
		for(var id = 0; pointsRemaining.length > 0; id++) {
			var point = { 
				x: pointsRemaining[0].lng,
				y: pointsRemaining[0].lat
			}
			point.x += 0.01;
			var pointsToDraw = calculatePoints( point, canvas.zlevel, Z, undefined/*drawCircle*/, stepX, stepY);
			if(pointsToDraw == null) {
				drawCircle(pointsRemaining[0].lng, pointsRemaining[0].lat);
				pointsRemaining.remove(0);
				continue;
			}
			var p = pointsToDraw;
			var polygon = [];
			for(var t = 0; t < 1000 ; t++) {
				/////////////////////////
				if(p == null || p.x == null || p.y == null) {
					polygon = [];
					break;
				}
				/////////////////////////
				var polygonPoint = new L.LatLng(p.y, p.x, true);
				polygon.push(polygonPoint);
				p = p.next;
				if(p == pointsToDraw) {
					polygonPoint = new L.LatLng(p.y, p.x, true);
					polygon.push(polygonPoint);
					break;
				}
			}
			var tPolygon;
			if(polygon.length <= 4) {
				tPolygon = new L.ExCircle(pointsRemaining[0], canvas.radiusCircle, polygonOptions);
			} else {
				tPolygon = new L.ExPolygon(polygon, polygonOptions, dZdxy);
			}
			for(var j = pointsRemaining.length - 1; j >= 0; j--) {
				if(tPolygon.pointInPolygon(pointsRemaining[j])) {
					pointsRemaining.remove(j);					
				}
			}
			canvas.putPolygon(tPolygon);
		}
	},
	redrawCurves = function redrawCurves(/*City from Java*/centers) {
		canvas.removeAll();		
		var point;
		var pointsToDraw = [];
		for(var j = 0; j < centers.length; j++) {
			point = new L.LatLng(centers[j].latitude , centers[j].longitude);
			point.weight = centers[j].weight;
			pointsToDraw.push(point);
		}
		points = pointsToDraw;
		drawCurves();
		//var topRight = new L.LatLng(80, 170);
		//var downLeft = new L.LatLng(-90, -180);
		//debugFunc(downLeft, topRight);
	},
	debugFunc = function debugFunc(downLeft, topRight) {
		var point = new L.LatLng(topRight.lat, downLeft.lng);
		var optionCircle = {};
		var stepXY = 2;
		while(point.lng <= topRight.lng) {
			while(point.lat >= downLeft.lat) {				
				if(canvas.zlevel - Z(point.lng, point.lat) > 0) {
					optionCircle.color = "red";
				} else {
					optionCircle.color = "green";
				}
				globalMap.addLayer(new L.Circle(new L.LatLng(point.lat, point.lng), 5000, optionCircle));
				point.lat -= stepXY;
			}
			point.lat = topRight.lat;
			point.lng += stepXY;
		}
	}
	drawCurves();
	//var topRight = new L.LatLng(80, 170);
	//var downLeft = new L.LatLng(-90, -180);
	//debugFunc(downLeft, topRight);
	var me = {
		redrawCurves : redrawCurves,
		canvas: canvas,
		stepX: stepX,
		stepY: stepY
	};
	return me;
}

function ConnectorCurvesToMap(/*L.Map*/ lmap) {	
	var polygons = [],
	zlevelNormalizer = {
		2 : 130,
		3 : 200,
		4 : 240,
		5 : 290,
		6 : 350,
		7 : 400,
		8 : 460
	},/** Индекс - это номер зума, значение - zlevel.**/
	
	radiusNormalizer = {
		2 : 150000,
		3 : 140000,
		4 : 120000,
		5 : 90000,
		6 : 70000,
		7 : 40000,
		8 : 20000
	},/** Индекс - это номер зума, значение - radius.**/
		
	currentZoom = lmap.getZoom(),
	
	setZLevel = function setZLevel(zlevel) {
		zlevelNormalizer[currentZoom] = zlevel;
		me.zlevel = zlevel;
	},
		
	setRadius = function setRadius(radius) {
		radiusNormalizer[currentZoom] = radius;
		me.radiusCircle = radius;
	},
	
	putPolygon = function putPolygon(/*L.ExPolygon*/polygon) {
		polygons.push(polygon);
		lmap.addLayer(polygon);
	},
	
	removeAll = function removeAll() {
		for(var i = 0; i < polygons.length; i++) {
			lmap.removeLayer(polygons[i]);
		}
		$('g > path[fill="red"]').remove();
		$('g > path[stroke="red"]').remove();
		$('g > path[stroke="green"]').remove();
		$('g:not(:has(path))').remove();
		polygons = [];
	},
	
	me = {
		putPolygon: putPolygon,
		removeAll: removeAll,
		setZLevel: setZLevel,
		setRadius: setRadius,
		zlevel: zlevelNormalizer[currentZoom],
		radiusCircle: radiusNormalizer[currentZoom],
		currentZoom: currentZoom
	};
    
	/*var circle = new L.Polyline([], {}), option;
	lmap.on('mousemove', function(e) {
		lmap.removeLayer(circle);
		var flag = false;
		for(var i = 0; i < polygons.length; i++)
			if(polygons[i] != null)
				if(flag || polygons[i].pointInPolygon(e.latlng))
					flag = true;
		if(flag)
			option = {
				fillColor: "green"
			};
		else
			option = {
				fillColor: "red"
			};
		option.color = "black";
		option.opacity = 1;
		option.weight = 3;
		option.fillOpacity = 0.5;
		circle = new L.Circle(e.latlng, 50000, option);
		lmap.addLayer(circle);
	});*/
	lmap.on('zoomend', function() {
		currentZoom = lmap.getZoom();
		me.zlevel = zlevelNormalizer[currentZoom];
		me.radiusCircle = radiusNormalizer[currentZoom];
		me.currentZoom = currentZoom;
	});
	
	return me;
}

function drawCircle(x, y) {
	globalMap.addLayer(new L.Circle(new L.LatLng(y, x, true), 30000, {
		fillColor: "red", 
		weight: 2
	}));
}
