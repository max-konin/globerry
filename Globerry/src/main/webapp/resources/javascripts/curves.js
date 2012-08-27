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
	var canvas = ConnectorCurvesToMap(lmap), stepX = 0.6, stepY = 0.6, points = centers, border = false, circles = false, polygonOptions = {
		color : "red",
		fillColor : "red",
		weight : 1,
		fillOpacity : 0.5,
		opacity : 0
	},
	/*
	 * Функция определяющая расчет потенциала
	 */
	Z = function Z(x, y) {
		var val = [];
		var pointXY = new L.LatLng(y, x);
		for ( var i = 0, l = points.length; i < l; i++) {
			var point = points[i];
			val.push(point.weight / point.distanceTo(pointXY));
		}
		val = val.sort(function(l, r) {
			if (l > r)
				return 1;
			return -1;
		});
		return val.getLast() * 1000000;
	}, drawCurves = function drawCurves() {
		polygonOptions.color = border ? "#ff4d29" : "red";
		polygonOptions.opacity = border ? 0.8 : 0;
		var epsilon = stepX * 17717;

		var returnPoints = [];
		var pointsRemaining = points.copy();
		pointsRemaining = pointsRemaining.sort(function(l, r) {
			if (l.lat > r.lat)
				return 1;
			return -1;
		});
		var center;
		var squarePoints;

		var i = 0;
		var j = 0;

		var buffer = [];
		var buffer2;
		var pointLeft;
		var pointRight;
		var exitCode = 0;
		var c = 0;
		
		var debug = false;
		while (pointsRemaining.length != 0) {
			var somePoint = pointsRemaining.pop();
			var direction = undefined;
			
			
			var start = new L.LatLng(somePoint.lat, somePoint.lng, true);
			start.lat = Math.floor(start.lat / stepY) * stepY;
			start.lng = Math.floor(start.lng / stepX) * stepX;

			center = new L.LatLng(start.lat + 0.1, start.lng);
			squarePoints = [ new L.LatLng(center.lat, center.lng), new L.LatLng(center.lat, center.lng + stepX),
					new L.LatLng(center.lat - stepY, center.lng + stepX), new L.LatLng(center.lat - stepY, center.lng) ];
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
					//if (globalMap.getZoom() >= 6) {
					//	drawMiniCircle(squarePoints[i].lng, squarePoints[i].lat, "black");
//
					//}
					if (Z(squarePoints[i].lng, squarePoints[i].lat) > canvas.zlevel) {
						buffer[count] = i;
						++count;
					} else {
						buffer2 = i;
					}
				}
				if (debug)
					alert("Count : " + count);
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
				returnPoints = [ somePoint ];
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
				canvas.putPolygon(drawCircle(o.lng, o.lat, "black", 2, canvas.radiusCircle));
			});
		}
	}, redrawCurves = function redrawCurves(/* City from Java */centers) {
		var point;
		var pointsToDraw = [];
		for ( var j = 0; j < centers.length; j++) {
			point = new L.LatLng(centers[j].latitude, centers[j].longitude);
			point.weight = centers[j].weight;
			pointsToDraw.push(point);
		}
		points = pointsToDraw;
		drawCurves();
		canvas.removeAll();
	}, debugFunc = function debugFunc(downLeft, topRight) {
		var point = new L.LatLng(topRight.lat, downLeft.lng);
		var optionCircle = {};
		var stepXY = 2;
		while (point.lng <= topRight.lng) {
			while (point.lat >= downLeft.lat) {
				if (canvas.zlevel - Z(point.lng, point.lat) > 0) {
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

	}, setCircles = function setCircles(paint) {
		circles = paint;
	}, setBorder = function setBorder(paint) {
		border = paint;
	};
	drawCurves();
	canvas.removeAll();
	var me = {
		redrawCurves : redrawCurves,
		canvas : canvas,
		stepX : stepX,
		stepY : stepY,
		setCircles : setCircles,
		setBorder : setBorder
	};
	return me;
}

function ConnectorCurvesToMap(/* L.Map */lmap) {
	var polygons = [ [], [] ], zlevelNormalizer = {
		2 : 9,
		3 : 10,
		4 : 13,
		5 : 20,
		6 : 22,
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
		currentZoom : currentZoom
	};

	/*
	 * var circle = new L.Polyline([], {}), option = {}; lmap.on('click',
	 * function(e) { if($("#modal").css("display") != "none") { circle = new
	 * L.Polyline([], {}); return; } lmap.removeLayer(circle); var flag = false;
	 * for(var i = 0; i < polygons.length; i++) if(polygons[i] != null) if(flag ||
	 * polygons[i].pointInPolygon(e.latlng)) flag = true; if(flag) option =
	 * {fillColor: "green"}; else option = {fillColor: "red"}; option.color =
	 * "black"; option.opacity = 1; option.weight = 3; option.fillOpacity = 0.5;
	 * circle = new L.Circle(e.latlng, 50000, option); lmap.addLayer(circle);
	 * });
	 */

	lmap.on('zoomend', function() {
		currentZoom = lmap.getZoom();
		me.zlevel = zlevelNormalizer[currentZoom];
		me.radiusCircle = radiusNormalizer[currentZoom];
		me.currentZoom = currentZoom;
	});

	return me;
}

function drawCircle(x, y, color, weight, radius, fillColor) {
	return /* globalMap.addLayer( */new L.Circle(new L.LatLng(y, x, true), typeof radius == "number" ? radius : 5000, {
		color : typeof color == "string" ? color : "red",
		fillColor : typeof fillColor == "string" ? fillColor : (color ? color : "red"),
		weight : typeof weight == "number" ? weight : 2,
		fillOpacity : 1,
		opacity : 1
	})/* ) */;
}
function drawMiniCircle(x, y, color) {
	globalMap.addLayer(new L.Circle(new L.LatLng(y, x, true), 3000, {
		color : color,
		weight : 1
	}));
}
