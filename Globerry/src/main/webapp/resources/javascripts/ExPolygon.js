/* 
 * Представление гладкого полигона
 */
L.ExPolygon = L.Polygon.extend({
	
	initialize: function (latlngs, options, gradF) {
		L.Polygon.prototype.initialize.call(this, latlngs, options);
		this._gradF = gradF;
	},
		
	pointInPolygon : function (/*L.LatLng*/point) {
		var rate = function (x, y) {
			return Math.sqrt(x*x + y*y);
		}
		var sign = function (exp) {
			if(exp > 0)
				return 1;
			return -1;
		}
		var polygon = this._latlngs;
		var influence = 0.5;
		var x0 = point.lng, y0 = point.lat, x1, x2, y1, y2, angle = 0;
		for(var i = 0; i < polygon.length; i++) {
			x1 = polygon[i].lng;
			y1 = polygon[i].lat;
			if(i + 1 == polygon.length) {
				x2 = polygon[0].lng;
				y2 = polygon[0].lat;
			} else {
				x2 = polygon[i + 1].lng;
				y2 = polygon[i + 1].lat;
			}
			var cos = ((x1 - x0)*(x2 - x0) + (y1 - y0)*(y2 - y0))/(rate(x1 - x0, y1 - y0)*rate(x2 - x0, y2 - y0));
			if(cos > 1) {
				cos = 1;
			}
			angle += sign((x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0)) * Math.acos(cos) * 180 / Math.PI;
		}
		angle = Math.abs(angle);
		if(Math.abs(angle - 360) < influence * 180 / Math.PI)
			return true;
		return false;
	},
	
	Temprary_getPathString: function () {
		function Ray(p1/*Начало*/, p2/*Вектор направления*/, flag /*Отсчитывать вектор напавления от нуля - true или от p1 - false*/) {
			var start = new L.Point(p1.x, p1.y), direction;
			if(flag) {
				var length = p2.distanceTo(new L.Point(0,0));
				direction = new L.Point(p2.x/length, p2.y/length);
			} else {
				var distance = p2.distanceTo(p1);
				direction = new L.Point((p2.x - p1.x)/distance, (p2.y - p1.y)/distance);
			}
			function getPoint(t) {
				return new L.Point(start.x + t*direction.x, start.y + t*direction.y);
			}
			function cross(ray) {
				var znamenatel = ray.direction.y*direction.x - ray.direction.x*direction.y;
				if (znamenatel == 0) {
					return null;
				}
				var chislitel = ray.direction.y*(ray.start.x - start.x) 
				+ ray.direction.x*(start.y - ray.start.y);
				return chislitel/znamenatel;
			}
			return {
				start : start,
				direction : direction,
				getPoint : getPoint,
				cross: cross
			};

		}
		var rays = [];
		for(var i = 0; i < this._latlngs.length ; i++) {
			var point = globalMap.latLngToLayerPoint(this._latlngs[i]);
			var gradF = this._gradF(this._latlngs[i].lng, this._latlngs[i].lat);
			var temp = globalMap.latLngToLayerPoint(gradF);
			var ray = Ray(point, new L.Point(-temp.y, temp.x) , true);
			rays.push(ray);
		}
		var ray1 = rays[0], ray2, c1, c2, factor = 0.3, prevT = null, factor2 = 0.6;
		var path = "M " + ray1.start.x + " " + ray1.start.y + " ";
		var t1, t2;
		rays.push(rays[0]);
		for(var j = 1, l = rays.length; j < l; j++) {
			ray2 = rays[j];
			t1 = ray1.cross(ray2);
			t2 = ray2.cross(ray1);
			distance = ray1.start.distanceTo(ray2.start);
			if(prevT == null) {
				if(t1 > distance){
					t1 = distance/2;
					t2 = distance/2;
				}
				if(t1 >= 0 && t2 <= 0) {
					c1 = ray1.getPoint(t1);
					c2 = c1;
					prevT = -t2;
				}
			}
			if(t1 >= 0 && t2 <= 0.01) {
				c1 = ray1.getPoint(prevT);
				if(t1 > distance) {
					t2 = -distance;
				}
				if(t1 > distance * 2) {
					c1 = ray1.getPoint(distance);
				}
				c2 = ray2.getPoint(t2 * factor2);
				prevT = -t2 * factor2;
			} else if(t1 >= 0 && t2 >= 0.01) {
				c1 = ray1.getPoint(prevT);
				c2 = ray2.getPoint(-factor);
				prevT = factor;
			} else if(t1 < 0 && t2 < 0) {
				c1 = ray1.getPoint(prevT);
				c2 = ray2.getPoint(-factor/2);
				prevT = factor/2;
			} else if(t1 < 0 && t2 >0) {
				c1 = ray1.getPoint(prevT);
				c2 = ray2.getPoint(-factor);
				prevT = factor;
			}
			
			//c1 = ray1.getPoint(t1 / 2);
			//c2 = ray2.getPoint(t2 / 4);
			
			path += "C " + c1.x + " " + c1.y + " "
			+ c2.x + " " + c2.y + " "
			+ ray2.start.x + " " + ray2.start.y + " "; 
			//path += "Q " + c1.x + " " + c1.y + " " + ray2.start.x + " " + ray2.start.y + " "; 
			ray1 = ray2;
		}
                
		path += 'z';
		return path;
	}

});

L.ExCircle = L.Circle.extend({
	
	initialize: function (center, radius, options) {
		L.Circle.prototype.initialize.call(this, center, radius, options);
	},
	
	pointInPolygon : function (/*L.LatLng*/point) {
		var length = point.distanceTo(this._latlng);
		if(length <= 2 * this._mRadius)
			return true;
		return false;
	}
	
});