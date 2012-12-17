/* 
 * Представление гладкого полигона
 */
var clickedPoligon = undefined;
L.ExPolygon = L.Polygon.extend({
	
	initialize: function (latlngs, options, cityList) {
		L.Polygon.prototype.initialize.call(this, latlngs, options);
		this._cityList = cityList;
	},
		
	onAdd : function (map){
		var __onAdd = L.Polygon.prototype.onAdd.call(this, map);
		hintText = '';
		this._cityList.forEach(function(city)
		{
			hintText += city.ru_name + "<br/>"; 
		});
		$(this._path).attr('hint', hintText);
		$(this._path).Hint({trigger : "mouseover"}, -1, -1);
		var cityList = this._cityList;
                var _thisPoligon = this;
		$(this._path).bind("click",function(event){ 
                            var _bottom = new Bottom;
                              _bottom.updateStaff(cityList);
                            $(_thisPoligon._path).attr('fill', 'url(#linesOnClick)');
                            if(clickedPoligon != undefined && clickedPoligon != _thisPoligon)
                                   $(clickedPoligon._path).attr('fill', 'url(#lines)');
                            clickedPoligon = _thisPoligon;
                            event.stopPropagation();
                                                          });
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
		var x0 = point.longitude, y0 = point.latitude, x1, x2, y1, y2, angle = 0;
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
	
	getPathString: function () {
                if(this != clickedPoligon)
                    $(this._path).attr('fill', 'url(#lines)');
                else
                     $(this._path).attr('fill', 'url(#linesOnClick)');
		var t = 2;
		if(this._latlngs.length < 8) {
			t = 1;
		}
		var toPoints = function toPoints(/*L.LatLng[]*/latlngs) {
			var ret = [];
			for(var i = 0, l = latlngs.length; i < l; i += t) {
				ret.push(globalMap.latLngToLayerPoint(latlngs[i]));
			}
			return ret;
		}
		var points = toPoints(this._latlngs), path, a3, a2, a1, a0, b3, b2, b1, b0, 
			p0 = new Object(), p1 = new Object(), p2 = new Object(), p3 = new Object();
		if(points.length < 4) {
			var r = this._cityList[0].weight * curves.canvas.radiusNormalizer[globalMap.getZoom()];
			if(!points[0]) {
				//alert(this._cityList.length);
				
				points[0] = globalMap.latLngToLayerPoint(new L.LatLng(this._cityList[0].latitude, this._cityList[0].longitude));
			}
			return "M" + points[0].x + "," + (points[0].y + r) +
					"A" + r + "," + r + ",0,1,1," +
					(points[0].x - 0.1) + "," + (points[0].y + r) + " z";
		}
		a3 = (-points.getLast().x + 3 * points[0].x - 3 * points[1].x + points[2].x) / 6;
		a2 = (points.getLast().x - 2 * points[0].x + points[1].x) / 2;
		a1 = (-points.getLast().x + points[1].x) / 2;
		a0 = (points.getLast().x + 4 * points[0].x + points[1].x) / 6;
		b3 = (-points.getLast().y + 3 * points[0].y - 3 * points[1].y + points[2].y) / 6;
		b2 = (points.getLast().y - 2 * points[0].y + points[1].y) / 2;
		b1 = (-points.getLast().y + points[1].y) / 2;
		b0 = (points.getLast().y + 4 * points[0].y + points[1].y) / 6;
		p0.x = a0;
		p0.y = b0;
		p3.x = a3 + a2 + a1 + a0;
		p3.y = b3 + b2 + b1 + b0;
		p1.x = (a1 + 3 * a0)/3;
		p1.y = (b1 + 3 * b0)/3;
		p2.x = (2 * a1 + a2 + 3 * a0)/3;
		p2.y = (2 * b1 + b2 + 3 * b0)/3;
		path = "M " + p0.x + " " + p0.y + " ";
		path += "C " + p1.x + " " + p1.y + " " + p2.x + " " + p2.y + " " + p3.x + " " + p3.y + " ";
		for(var i = 1, l = points.length - 2; i < l; i++) {
			a3 = (-points[i - 1].x + 3 * points[i].x - 3 * points[i + 1].x + points[i + 2].x) / 6;
			a2 = (points[i - 1].x - 2 * points[i].x + points[i + 1].x) / 2;
			a1 = (-points[i - 1].x + points[i + 1].x) / 2;
			a0 = (points[i - 1].x + 4 * points[i].x + points[i + 1].x) / 6;
			b3 = (-points[i - 1].y + 3 * points[i].y - 3 * points[i + 1].y + points[i + 2].y) / 6;
			b2 = (points[i - 1].y - 2 * points[i].y + points[i + 1].y) / 2;
			b1 = (-points[i - 1].y + points[i + 1].y) / 2;
			b0 = (points[i - 1].y + 4*points[i].y + points[i + 1].y) / 6;
			p0.x = a0;
			p0.y = b0;
			p3.x = a3 + a2 + a1 + a0;
			p3.y = b3 + b2 + b1 + b0;
			p1.x = (a1 + 3 * a0)/3;
			p1.y = (b1 + 3 * b0)/3;
			p2.x = (2 * a1 + a2 + 3 * a0)/3;
			p2.y = (2 * b1 + b2 + 3 * b0)/3;
			path += "C " + p1.x + " " + p1.y + " " + p2.x + " " + p2.y + " " + p3.x + " " + p3.y + " ";
		}
		// i + 2 == points.length
		i = points.length - 2;
		a3 = (-points[i - 1].x + 3 * points[i].x - 3 * points[i + 1].x + points[0].x) / 6;
		a2 = (points[i - 1].x - 2 * points[i].x + points[i + 1].x) / 2;
		a1 = (-points[i - 1].x + points[i + 1].x) / 2;
		a0 = (points[i - 1].x + 4 * points[i].x + points[i + 1].x) / 6;
		b3 = (-points[i - 1].y + 3 * points[i].y - 3 * points[i + 1].y + points[0].y) / 6;
		b2 = (points[i - 1].y - 2 * points[i].y + points[i + 1].y) / 2;
		b1 = (-points[i - 1].y + points[i + 1].y) / 2;
		b0 = (points[i - 1].y + 4 * points[i].y + points[i + 1].y) / 6;
		p0.x = a0;
		p0.y = b0;
		p3.x = a3 + a2 + a1 + a0;
		p3.y = b3 + b2 + b1 + b0;
		p1.x = (a1 + 3 * a0)/3;
		p1.y = (b1 + 3 * b0)/3;
		p2.x = (2 * a1 + a2 + 3 * a0)/3;
		p2.y = (2 * b1 + b2 + 3 * b0)/3;
		path += "C " + p1.x + " " + p1.y + " " + p2.x + " " + p2.y + " " + p3.x + " " + p3.y + " ";
		// i + 1 == points.length
		i =  points.length - 1;
		a3 = (-points[i - 1].x + 3 * points[i].x - 3 * points[0].x + points[1].x) / 6;
		a2 = (points[i - 1].x - 2 * points[i].x + points[0].x) / 2;
		a1 = (-points[i - 1].x + points[0].x) / 2;
		a0 = (points[i - 1].x + 4 * points[i].x + points[0].x) / 6;
		b3 = (-points[i - 1].y + 3 * points[i].y - 3 * points[0].y + points[1].y) / 6;
		b2 = (points[i - 1].y - 2 * points[i].y + points[0].y) / 2;
		b1 = (-points[i - 1].y + points[0].y) / 2;
		b0 = (points[i - 1].y + 4 * points[i].y + points[0].y) / 6;
		p0.x = a0;
		p0.y = b0;
		p3.x = a3 + a2 + a1 + a0;
		p3.y = b3 + b2 + b1 + b0;
		p1.x = (a1 + 3 * a0)/3;
		p1.y = (b1 + 3 * b0)/3;
		p2.x = (2 * a1 + a2 + 3 * a0)/3;
		p2.y = (2 * b1 + b2 + 3 * b0)/3;
		path += "C " + p1.x + " " + p1.y + " " + p2.x + " " + p2.y + " " + p3.x + " " + p3.y + " ";
		path += "z";
		return path;
	}

});

L.ExCircle = L.Circle.extend({
	
	getPathString: function () {
		$(this._path).attr('fill', 'url(#lines)');
		return L.Circle.prototype.getPathString.call(this);
	},
		
	initialize: function (center, radius, options, city) {
		L.Circle.prototype.initialize.call(this, center, radius, options);
                if(typeof(city) != undefined)
                {
                    this._city = city; 
                    
                    //
                }
	},
	
	pointInPolygon : function (/*L.LatLng*/point) {
		var length = point.distanceTo(this._latlng);
		if(length <= 2 * this._mRadius)
			return true;
		return false;
	},
	
	onAdd : function (map){
		var __onAdd = L.Circle.prototype.onAdd.call(this, map);
		$(this._path).attr('hint', this._city.ru_name);
		$(this._path).Hint({trigger : "mouseover"}, -1, -1);
		var cityName = this._city.name;
		$(this._path).bind("click",function(event){ 
                if($("#headContent2").is(":visible"))
                {
                    var _bottom = new Bottom;
                    _bottom.updateStaff(cityName);
                    event.stopPropagation();
                }
                });
		return __onAdd;
	}
	
});

L.ExMarker = L.Marker.extend({
	
	initialize: function (center, city) {
		L.Marker.prototype.initialize.call(this, center);
                if(typeof(city) != undefined)
                {
                    this._city = city; 
                    
                    //
                }
	},
	onAdd : function (map){
		var __onAdd = L.Marker.prototype.onAdd.call(this, map);
		$(this._icon).attr('hint', this._city.ru_name);
		$(this._icon).Hint({trigger : "mouseover"}, -1, -1);
		var city = [this._city];
		$(this._icon).bind("click",function(event){ 
                
                    var _bottom = new Bottom;
                    _bottom.updateStaff(city);
                    event.stopPropagation();

                });
		return __onAdd;
	}
	
});
