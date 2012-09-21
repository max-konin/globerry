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
	}, drawCurves = function drawCurves() {
		polygonOptions.color = border ? "#ff2b00" : "#ff2b00";
		polygonOptions.opacity = border ? 1.5 : 0;
		canvas.setZoom(globalMap.getZoom());
        if(globalMap.getZoom() > 6)
        {
        	points.forEach(function(o) {
        		canvas.putPolygon(new L.ExMarker(new L.LatLng(o.latitude, o.longitude),o));
            });
        }
        else
        {
		var epsilon = stepX * 17717;

		var returnPoints = [];
		var pointsRemaining = points.copy();
		pointsRemaining = pointsRemaining.sort(function(l, r) {
			if (l.latitude > r.latitude)
				return 1;
			return -1;
		});
                //город
		var center;
                //массив вершин квадрата
		var squarePoints;
                //переменные цикла
		var i = 0;
		var j = 0;
                //Массив куда складываются номера вершин квадрата, которые попали в срез поля
		var buffer = [];
                //точка которая не попала  срез(используется для count == 3)
		var buffer2;
                //точки на квадрате по которым строится кривулина, попадают в массив returnPoints, отсчет право-лево по часовой стрелке
		var pointLeft;
		var pointRight;
                
		var exitCode = 0;
		var c = 0;
		
		var debug = false;
		while (pointsRemaining.length != 0) {
			var somePoint = pointsRemaining.pop();
//                        if(somePoint.name != "Trondheim")
//                            continue;
//                        debug = true;
                        //направление выбора квадрата 0-вверх 1-вправо 2-вниз 3-влево
			var direction = undefined;
//			if(Math.ceil(somePoint.lat) == 36 && Math.ceil(somePoint.lng)==15)
//                            debug = true;
			
			var start = new L.LatLng(somePoint.latitude, somePoint.longitude, true);
			start.lat = Math.floor(start.lat / stepY) * stepY;
			start.lng = Math.floor(start.lng / stepX) * stepX;

			center = new L.LatLng(start.lat, start.lng);
			squarePoints = [ new L.LatLng(center.lat, center.lng), new L.LatLng(center.lat, center.lng + stepX),
					new L.LatLng(center.lat - stepY, center.lng + stepX), new L.LatLng(center.lat - stepY, center.lng) ];
                                    //squarePoints = [ new L.LatLng(center.lat + stepY/2, center.lng - stepX/2), new L.LatLng(center.lat + stepY/2, center.lng + stepX/2),
					//new L.LatLng(center.lat - stepY/2, center.lng + stepX/2), new L.LatLng(center.lat - stepY/2, center.lng - stepX/2) ];
			exitCode = 0;
			while (exitCode == 0) {
				c++;
				if (c > 2000) {
					exitCode = 1;
					c = 0;
					continue;
				}
				var count = 0;
				for (i = 0; i < 4; ++i) {
					if (debug) {
						drawMiniCircle(squarePoints[i].lng, squarePoints[i].lat, "black");
//
					}
					if (Z(squarePoints[i].lng, squarePoints[i].lat) > canvas.zlevel) {
						buffer[count] = i;
						++count;
					} else {
						buffer2 = i;
					}
				}
				if (debug)
					alert("Count : " + count);
                                // в зависимости от того сколько точек попало в срез поля и то какие они определяется какие точки попадут в returnPoints
				switch (count) {
				case 0:
					// alert("Хрень батюшка");
					// drawCircle(center.lng, center.lat, "orange", 2, 30000);
					// alert("Count : " + count + " direction : " + direction);

					exitCode = -1;
					break;
				case 1:
					if (debug)
						alert("CASE 1 buffer[0] : " + buffer[0]);
					if (buffer[0] == 0) {
						pointRight = new L.LatLng(squarePoints[0].lat - stepY / 2, squarePoints[0].lng, true);
						pointLeft = new L.LatLng(squarePoints[0].lat, squarePoints[0].lng + stepX / 2, true);
						direction = 3;
					} else if (buffer[0] == 1) {
						pointRight = new L.LatLng(squarePoints[1].lat, squarePoints[1].lng - stepX / 2, true);
						pointLeft = new L.LatLng(squarePoints[1].lat - stepY / 2, squarePoints[1].lng, true);
						direction = 0;
					} else if (buffer[0] == 2) {
						pointLeft = new L.LatLng(squarePoints[2].lat, squarePoints[2].lng - stepX / 2, true);
						pointRight = new L.LatLng(squarePoints[2].lat + stepY / 2, squarePoints[2].lng, true);
						direction = 1;
					} else if (buffer[0] == 3) {
						pointLeft = new L.LatLng(squarePoints[3].lat + stepY / 2, squarePoints[3].lng, true);
						pointRight = new L.LatLng(squarePoints[3].lat, squarePoints[3].lng + stepX / 2, true);
						direction = 2;
					}
					break;
				case 2:
					if (debug)
						alert("CASE 2 buffer[0] : " + buffer[0] + "      CASE 2 buffer[1] : " + buffer[1]);
					if (buffer[0] == 0 && buffer[1] == 1) {
						pointLeft = new L.LatLng(squarePoints[1].lat - stepY / 2, squarePoints[1].lng, true);
						pointRight = new L.LatLng(squarePoints[0].lat - stepY / 2, squarePoints[0].lng, true);
						direction = 3;
					} else if (buffer[0] == 1 && buffer[1] == 2) {
						pointLeft = new L.LatLng(squarePoints[2].lat, squarePoints[2].lng - stepX / 2, true);
						pointRight = new L.LatLng(squarePoints[1].lat, squarePoints[1].lng - stepX / 2, true);
						direction = 0;
					} else if (buffer[0] == 2 && buffer[1] == 3) {
						pointLeft = new L.LatLng(squarePoints[3].lat + stepY / 2, squarePoints[3].lng, true);
						pointRight = new L.LatLng(squarePoints[2].lat + stepY / 2, squarePoints[2].lng, true);
						direction = 1;
					} else if (buffer[0] == 0 && buffer[1] == 3) {
						pointLeft = new L.LatLng(squarePoints[0].lat, squarePoints[0].lng + stepX / 2, true);
						pointRight = new L.LatLng(squarePoints[3].lat, squarePoints[3].lng + stepX / 2, true);
						direction = 2;
					} else if (buffer[0] == 0 && buffer[1] == 2) {
						if (direction == 0) {
							pointLeft = new L.LatLng(squarePoints[2].lat, squarePoints[2].lng - stepX / 2, true);
							pointRight = new L.LatLng(squarePoints[2].lat + stepY / 2, squarePoints[2].lng, true);
							direction = 1;
						} else if (direction == 2) {
							pointLeft = new L.LatLng(squarePoints[0].lat, squarePoints[0].lng + stepX / 2, true);
							pointRight = new L.LatLng(squarePoints[0].lat - stepY / 2, squarePoints[0].lng, true);
							direction = 3;
						} 
					} else if (buffer[0] == 1 && buffer[1] == 3) {
						if (direction == 3) {
							pointLeft = new L.LatLng(squarePoints[1].lat - stepY / 2, squarePoints[1].lng, true);
							pointRight = new L.LatLng(squarePoints[1].lat, squarePoints[1].lng - stepX / 2, true);
							direction = 0;
						} else if (direction == 1) {
							pointLeft = new L.LatLng(squarePoints[3].lat + stepY / 2, squarePoints[3].lng, true);
							pointRight = new L.LatLng(squarePoints[3].lat, squarePoints[3].lng + stepX / 2, true);
							direction = 2;
						} else if (direction == undefined) {
							pointLeft = undefined;
							pointRight = undefined;
							direction = 2;
						}
					}
					break;
				case 3:
					if (debug)
						alert("CASE 3 buffer2 : " + buffer2);
					if (buffer2 == 0) {
						pointLeft = new L.LatLng(squarePoints[0].lat - stepY / 2, squarePoints[0].lng, true);
						pointRight = new L.LatLng(squarePoints[0].lat, squarePoints[0].lng + stepX / 2, true);
						direction = 0;
					} else if (buffer2 == 1) {
						pointLeft = new L.LatLng(squarePoints[1].lat, squarePoints[1].lng - stepX / 2, true);
						pointRight = new L.LatLng(squarePoints[1].lat - stepY / 2, squarePoints[1].lng, true);
						direction = 1;
					} else if (buffer2 == 2) {
						pointRight = new L.LatLng(squarePoints[2].lat, squarePoints[2].lng - stepX / 2, true);
						pointLeft = new L.LatLng(squarePoints[2].lat + stepY / 2, squarePoints[2].lng, true);
						direction = 2;
					} else if (buffer2 == 3) {
						pointRight = new L.LatLng(squarePoints[3].lat + stepY / 2, squarePoints[3].lng, true);
						pointLeft = new L.LatLng(squarePoints[3].lat, squarePoints[3].lng + stepX / 2, true);
						direction = 3;
					}
					break;
				case 4:
					// alert("Count case 4");
					direction = 0;
					break;
				}
				var distance = 500, tCircle;
				if (returnPoints.length != 0)
					distance = returnPoints[0].distanceTo(pointRight);
				if (count != 4 && count != 0) {
					if (debug)
						alert("count != 4 && count != 0 ");
					if (returnPoints.length == 0 && pointLeft != undefined) {
						returnPoints.push(pointLeft);
						//tCircle = drawCircle(pointLeft.lng, pointLeft.lat);
						//canvas.putPolygon(tCircle);
					} else if (distance < epsilon) {
						exitCode = 1;
						continue;
					}
					if (pointRight != undefined) {
						returnPoints.push(pointRight);
						// if(globalMap.getZoom() == 6)
						//tCircle = drawCircle(pointRight.lng, pointRight.lat);
						//canvas.putPolygon(tCircle);
					}
				}
				if (debug)
					alert("direction : " + direction);
                                //выбор направления движения 
				switch (direction) {
				case 0:
					center.lat += stepY;
					break;
				case 1:
					center.lng += stepX;
					break;
				case 2:
					center.lat -= stepY;
					break;
				case 3:
					center.lng -= stepX;
					break;
				}
				squarePoints = [ new L.LatLng(center.lat, center.lng), new L.LatLng(center.lat, center.lng + stepX),
						new L.LatLng(center.lat - stepY, center.lng + stepX), new L.LatLng(center.lat - stepY, center.lng) ];
			}
			if (returnPoints < 4) {
				somePointLatLng = new L.LatLng(somePoint.latitude, somePoint.longitude, true);
				returnPoints = [ somePointLatLng ];
			}
			var tPolygon = new L.ExPolygon(returnPoints, polygonOptions);
			for ( var k = pointsRemaining.length - 1; k >= 0; k--) {
				if (tPolygon.pointInPolygon(pointsRemaining[k])) {
					pointsRemaining.remove(k);
				}
			}
			canvas.putPolygon(tPolygon);
			returnPoints = [];
		}
		if (circles) {
			points.forEach(function(o) {
				canvas.putPolygon(drawCircle(o.longitude, o.latitude, "black", 2, canvas.radiusCircle));
			});
		}
	}
        }, redrawCurves = function redrawCurves(/* City from Java */cities) {
		points = cities;
		drawCurves();
		canvas.removeAll();
	},
		redrawCurvesNew = function redrawCurvesNew(curves)
		{
			for(var i = 0; i < curves.length; ++i)
			{
				var tPolygon = new L.ExPolygon(curves[i], polygonOptions);
				canvas.putPolygon(tPolygon);
			}
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
		
	    /*children = createElement('path');
	    children.setAttribute('d', 'M -1 4 L 9 4');
	    children.setAttribute('style', 'stroke: #ffffff; stroke-width: 2; opacity: 1');
	    pattern.appendChild(children);*/
		
		svg.appendChild(defs);
		
	    $("div.leaflet-overlay-pane").prepend(svg);
	}, setCircles = function setCircles(paint) {
		circles = paint;
	}, setBorder = function setBorder(paint) {
		border = paint;
	};
	drawCurves();
	canvas.removeAll();
	var me = {
		redrawCurves : redrawCurves,
		redrawCurvesNew : redrawCurvesNew,
		canvas : canvas,
		stepX : stepX,
		stepY : stepY,
		setCircles : setCircles,
		setBorder : setBorder,
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
		7 : 30,
		8 : 34
	}, /** Индекс - это номер зума, значение - zlevel.* */

	radiusNormalizer = {
		2 : 15000,
		3 : 10000,
		4 : 7000,
		5 : 5000,
		6 : 4000,
		7 : 3000,
		8 : 2000
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
		// $('g > path[fill="black"]').remove();
		// $('g > path[fill="red"]').remove();
		// $('g > path[stroke="red"]').remove();
		// $('g > path[stroke="green"]').remove();
		// $('g > path[fill="blue"]').remove();
		// $('g > path[fill="green"]').remove();
		// $('g:not(:has(path))').remove();
		polygons[0] = polygons[1];
		polygons[1] = [];
	},

	me = {
		putPolygon : putPolygon,
		removeAll : removeAll,
		setZLevel : setZLevel,
		setRadius : setRadius,
		zlevel : zlevelNormalizer[currentZoom],
		radiusCircle : radiusNormalizer[currentZoom],
		currentZoom : currentZoom,
		setZoom : setZoom
	};




	return me;
}

function drawCircle(x, y, color, weight, radius, city,fillColor) {
	return /* globalMap.addLayer( */new L.ExCircle(new L.LatLng(y, x, true), typeof radius == "number" ? radius : 5000, {
		color : typeof color == "string" ? color : "red",
		fillColor : typeof fillColor == "string" ? fillColor : (color ? color : "red"),
		weight : typeof weight == "number" ? weight : 20,
		fillOpacity : 0.5,
		opacity : 0.5
	}, city)/* ) */;
}
function drawMiniCircle(x, y, color) {
	globalMap.addLayer(new L.Circle(new L.LatLng(y, x, true), 3000, {
		color : color,
		weight : 1
	}));
}
