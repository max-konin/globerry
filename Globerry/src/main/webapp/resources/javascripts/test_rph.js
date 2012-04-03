function mapAndCurves() {
    var Canvas_container = document.getElementById('canvas_container');
    var My_Button1 = document.getElementById('Button_1');
    //var Map = document.getElementById('map');
    //alert("test_rph.js");
    var paper = new Raphael(Canvas_container, '100%', '100%');
    novigate();
    var startMove = false;
    var startDraw = false;
    var vopacity = 0.5;
    var color = 'orange';
    var circleColor = '#9cf';
    var line;
    var circle;
    var circles = new Array();
    var rect;
    var triangle;
    var ln;
    var startx, starty;
    var arrln = 40, i, j;
    var myArray = new Array();
    var Width = $(document.getElementById("canvas_container")).width();
    var Height = $(document.getElementById("canvas_container")).height();
    var p = 2;
    var level = 1.6;
    var line_s = '';
    var begLevel = level;
    var k = 0;
    var size = 0;
    /*
    if (Width < 1500) arrln = 40;
    if (Width < 1000) arrln = 30;
    if (Width < 700) arrln = 25;
    */
    var blockWidth = Width / arrln;
    var blockHeight = Height / arrln;

    var circle_counter;
    var my2Array = new Array();
    var i2, j2;
    var arrln2 = 5;
    var littleBlockWidth = blockWidth / arrln2;
    var littleBlockHeight = blockHeight / arrln2;
	
	var JSONContr = new JsonController();


    for (i = 0; i < arrln + 1; i++) {
        myArray[i] = new Array();
        for (j = 0; j < arrln + 1; j++) {
            myArray[i][j] = 0;
        }
    }
    //------------------------------------------------------------
    Canvas_container.onclick = function (e) {
        e = e || window.event;

        if (!startDraw) {
            startDraw = true;
            startx = e.clientX - $(document.getElementById("div_container")).width() * 0.3; 
            starty = e.clientY - $(document.getElementById("div_container")).height() * 0.07;
            circle = paper.circle(startx, starty, 0);
            circle.attr({ fill: circleColor, opacity: vopacity, stroke: 'none' });
        }
        else {
            startDraw = false;
            circle = undefined;
            circles[size] = new Array();
            circles[size][1] = startx;
            circles[size][2] = starty;
            circles[size][3] = ln;
            size++;
        }
        //}
        //------------------------------------------------------------
        Canvas_container.onmousemove = function (e) {
            if (startMove) {
                Map.onmousemove(e);
            }
            else {
                e = e || window.event;
                if (5 == k) {
                    line = undefined;
                }

                if (startDraw) {    //� ��� ����!
                    ln = Math.sqrt((e.clientX - startx - $(document.getElementById("div_container")).width() * 0.3 ) * (e.clientX - startx - $(document.getElementById("div_container")).width() * 0.3) + (e.clientY - starty - $(document.getElementById("div_container")).height() * 0.07) * (e.clientY - starty - $(document.getElementById("div_container")).height() * 0.07));
                    circle.animate({ r: ln }, 10);
                }
            }
        }
        //------------------------------------------------------------
		/*
        document.getElementById('Button_1').onclick = function () {
            if (!startMove) {
			redraw (circles, size);
            } //move
			
        }
		*/
        //------------------------------------------------------------
		/*
        document.getElementById('level_list').onchange = function () {
            begLevel = document.getElementById('level_list').value;
            level = begLevel * Math.pow(0.9, size);
        }
		*/
        //------------------------------------------------------------
        document.getElementById('moveOrDraw').onchange = function () {
            if (0 == document.getElementById('moveOrDraw').value) {
                startMove = false;
                document.getElementById('canvas_container').style.zIndex = "2";
                document.getElementById('moveMapAndCurves').style.zIndex = "1";
            } //if
            else {
                startMove = true;
                document.getElementById('canvas_container').style.zIndex = "1";
                document.getElementById('moveMapAndCurves').style.zIndex = "2";
            } //else
        }

    } //draw

   function novigate(){
		var deltaX, delataY;
		var Width = $(document.getElementById("moveMapAndCurves")).width();
		var Height = $(document.getElementById("moveMapAndCurves")).height();
		//alert("test_rph_e.js");
		bounds = new OpenLayers.Bounds(0, 0, 360, 180);
		//alert(bounds);
		map = new OpenLayers.Map('map', { numZoomLevels: 6 });
		layer = new OpenLayers.Layer.WMS("OpenLayers WMS",
					"http://vmap0.tiles.osgeo.org/wms/vmap0", { layers: 'basic' });
		map.addLayer(layer);
		map.zoomIn();
		map.zoomIn();
		map.zoomIn();
		var lon = 0;
		var lat = 0;
		var lonlat = new OpenLayers.LonLat(lon, lat);
		map.zoomIn();
		bounds = map.getExtent();
		//alert(bounds);
		//alert(bounds.right);
		//alert(bounds.left);
		//alert(bounds.bottom);
		//alert(bounds.top);
		//alert( map.getPixelFromLonLat(lonlat));
		var pixel = new OpenLayers.Pixel(840, 468);
		pixel = map.getPixelFromLonLat(lonlat);

		var Canvas_container = document.getElementById('moveMapAndCurves');
		var nowX, nowY, R = Raphael(Canvas_container, '100%', '100%'),
		moveContainer = R.rect(0, 0, Width, Height).attr({
			fill: "hsb(.8, 1, 1)",
			stroke: "none",
			opacity: 0,
		}),
		start = function () {
			// storing original coordinates
			this.ox = this.attr("x");
			this.oy = this.attr("y");
			this.attr({ opacity: 0, cursor: "move" });
		},
		move = function (dx, dy) {
			deltaX = dx;
			deltaY = dy;
			pixel = new OpenLayers.Pixel(($(document.getElementById("canvas_container")).width()*0.5 - dx), ($(document.getElementById("canvas_container")).height()*0.5 - dy));
			lonlat = map.getLonLatFromPixel(pixel);
			map.panTo(lonlat);
            /*
			  $('#canvas_container').animate({
				left: ($(document.getElementById("canvas_container")).width() * 0.3 / 0.7 + dx),
				top: ($(document.getElementById("canvas_container")).height() * 0.07/0.58 + dy)
			  }, 1, function() {
			  });
              */
		},
		up = function () {
			// restoring state
            /*
			$('#canvas_container').animate({
				left: ($(document.getElementById("canvas_container")).width() * 0.3 / 0.7),
				top: ($(document.getElementById("canvas_container")).height() * 0.07/0.58)
			  }, 1, function() {
			  });
              */
			bounds = map.getExtent();
			//alert(bounds);
			//var ololo123;
			JSONContr.cityRequest(1);
			//for(ololo123 in circles)
			//	alert(circles[ololo123]);
			//redraw(circles, 1);
			this.attr({ opacity: 0, cursor: "auto"  });
		};
		// rstart and rmove are the resize functions;
		moveContainer.drag(move, start, up);
		document.getElementById('moveMapAndCurves').ondblclick = function () {
		map.zoomIn();
		};
	}
	
	
    map.events.register("zoomend", map, function(e) { 
        //alert('ok');
		//redraw(circles, size);
        JSONContr.cityRequest(1);
    }); 
	/**/

/*
map.events.register("move", map, function(e) { 
    return true;
});
*/
		//------------------------------------------------------------
		function redraw (arrayOfCityes, number){		
			//latitude
			//longitude
			//commented version has non-constant radius
			/*
		       circle_counter;
                my2Array = new Array();
                i2, j2;
                arrln2 = 5;
                littleBlockWidth = blockWidth / arrln2;
                littleBlockHeight = blockHeight / arrln2;

                level = begLevel * Math.pow(0.9, size);

                for (i = 0; i < arrln + 1; i++) {
                    for (j = 0; j < arrln + 1; j++) {
                        myArray[i][j] = 0;
                    }
                }
                for (i = 0; i < arrln + 1; i++) {
                    for (j = 0; j < arrln + 1; j++) {
                        for (circle_counter in arrayOfCityes) {
                            var some_math_operations = (Math.pow(Math.sqrt((i * blockWidth - arrayOfCityes[circle_counter][1]) * (i * blockWidth - arrayOfCityes[circle_counter][1]) + (j * blockHeight - arrayOfCityes[circle_counter][2]) * (j * blockHeight - arrayOfCityes[circle_counter][2])), p))
                            if (some_math_operations) {
                                myArray[i][j] += (6 * (arrayOfCityes[circle_counter][3] * arrayOfCityes[circle_counter][3]) / some_math_operations - level);
                            }
                            else
                                myArray[i][j] += level * 10;
                        }
                    }
                }
                for (i2 = 0; i2 < arrln2 + 1; i2++) {
                    my2Array[i2] = new Array();
                    for (j2 = 0; j2 < arrln2 + 1; j2++) {
                        my2Array[i2][j2] = 0;
                    }
                }
				*/
				//alert("123");
			size = number;
			
			circles = arrayOfCityes;
				//alert(arrayOfCityes[0].latitude+ "," + arrayOfCityes[0].longitude);
				circle_counter;
                my2Array = new Array();
                i2, j2;
                arrln2 = 5;
                littleBlockWidth = blockWidth / arrln2;
                littleBlockHeight = blockHeight / arrln2;

                level = begLevel * Math.pow(0.9, size);

                for (i = 0; i < arrln + 1; i++) {
                    for (j = 0; j < arrln + 1; j++) {
                        myArray[i][j] = 0;
                    }
                }
                for (i = 0; i < arrln + 1; i++) {
                    for (j = 0; j < arrln + 1; j++) {
                        for (circle_counter = 0;  circle_counter< size; circle_counter++) {
                        	bounds = map.getExtent();
		                    //alert(bounds);
		                    //alert(bounds.right);
		                    //alert(bounds.left);
		                    //alert(bounds.bottom);
		                    //alert(bounds.top);
                            //alert( map.getPixelFromLonLat(lonlat));
                            //((myCitiesArray[circle_counter].longitude - bounds.left)/(bounds.right - bounds.left)*$(document.getElementById("canvas_container")).width())

                            var some_math_operations = (Math.pow(Math.sqrt((i * blockWidth - ((arrayOfCityes[circle_counter].longitude-bounds.left)/(bounds.right -bounds.left))*$(document.getElementById("canvas_container")).width()) * (i * blockWidth - ((arrayOfCityes[circle_counter].longitude-bounds.left)/(bounds.right -bounds.left))*$(document.getElementById("canvas_container")).width()) + (j * blockHeight - ((arrayOfCityes[circle_counter].latitude - bounds.top)/(bounds.bottom - bounds.top)*$(document.getElementById("canvas_container")).height())) * (j * blockHeight - ((arrayOfCityes[circle_counter].latitude - bounds.top)/(bounds.bottom - bounds.top)*$(document.getElementById("canvas_container")).height()))), p));
                            if (some_math_operations) {
                                myArray[i][j] += (6 * (2500) / some_math_operations - level);
                            }
                            else
                                myArray[i][j] += level * 10;
                        }
                    }
                }
                for (i2 = 0; i2 < arrln2 + 1; i2++) {
                    my2Array[i2] = new Array();
                    for (j2 = 0; j2 < arrln2 + 1; j2++) {
                        my2Array[i2][j2] = 0;
                    }
                }
                draw(arrayOfCityes);	
		/* */
		}
		//------------------------------------------------------------
        function draw(myCitiesArray) {
        	//alert(circles[0].name);
            paper.clear();
            /*
            for (i in circles) {
                circle = paper.circle(circles[i][1], circles[i][2], circles[i][3]);
                circle.attr({ fill: circleColor, opacity: vopacity, stroke: 'none' });
            }
            */
            for (i = 0; i < arrln; i++) {
                for (j = 0; j < arrln; j++) {
                    if (!((myArray[i][j] < 0) && (myArray[i + 1][j] < 0) && (myArray[i][j + 1] < 0) && (myArray[i + 1][j + 1] < 0))) {
                        if (((myArray[i][j] > 0) && (myArray[i + 1][j] > 0) && (myArray[i][j + 1] > 0) && (myArray[i + 1][j + 1] > 0))) {
                            var j0 = j;
                            while (((myArray[i][j + 1] > 0) && (myArray[i + 1][j + 1] > 0) && (myArray[i][j + 2] > 0) && (myArray[i + 1][j + 2] > 0)) && (j < arrln)) j++;
                            rect = paper.rect(i * blockWidth, j0 * blockHeight, blockWidth, (j - j0 + 1) * blockHeight);
                            rect.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                            j++;
                        }
                        for (i2 = 0; i2 < arrln2 + 1; i2++) {
                            for (j2 = 0; j2 < arrln2 + 1; j2++) {
                                my2Array[i2][j2] = 0;
                            }
                        }
                        for (i2 = 0; i2 < arrln2 + 1; i2++) {
                            for (j2 = 0; j2 < arrln2 + 1; j2++) {
                                for (circle_counter in myCitiesArray) {
                                    var some_math_operations = (Math.pow(Math.sqrt((i * blockWidth + i2 * littleBlockWidth - ((myCitiesArray[circle_counter].longitude - bounds.left)/(bounds.right - bounds.left)*$(document.getElementById("canvas_container")).width())) * (i * blockWidth + i2 * littleBlockWidth - ((myCitiesArray[circle_counter].longitude - bounds.left)/(bounds.right - bounds.left)*$(document.getElementById("canvas_container")).width())) + (j * blockHeight + j2 * littleBlockHeight - ((myCitiesArray[circle_counter].latitude - bounds.top)/(bounds.bottom - bounds.top)*$(document.getElementById("canvas_container")).height())) * (j * blockHeight + j2 * littleBlockHeight - ((myCitiesArray[circle_counter].latitude - bounds.top)/(bounds.bottom - bounds.top)*$(document.getElementById("canvas_container")).height()))), p));
                                    if (some_math_operations) {
                                        my2Array[i2][j2] += (6 * (2500) / some_math_operations - level);
                                    }
                                    else
                                        my2Array[i2][j2] += level * 10;
                                }
                            }
                        }
                        for (i2 = 0; i2 < arrln2; i2++) {
                            for (j2 = 0; j2 < arrln2; j2++) {
                                if ((my2Array[i2][j2] > 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2][j2 + 1] > 0) && (my2Array[i2 + 1][j2 + 1] > 0)) {
                                    var j0 = j2;
                                    while (((my2Array[i2][j2 + 1] > 0) && (my2Array[i2 + 1][j2 + 1] > 0) && (my2Array[i2][j2 + 2] > 0) && (my2Array[i2 + 1][j2 + 2] > 0)) && (j2 < arrln2)) j2++;
                                    rect = paper.rect(i * blockWidth + i2 * littleBlockWidth, j0 * littleBlockHeight + j * blockHeight, littleBlockWidth, (j2 - j0 + 1) * littleBlockHeight);
                                    rect.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                                    j2++;
                                }
                                if (((my2Array[i2][j2] > 0) && (my2Array[i2][j2 + 1] > 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] < 0)) || ((my2Array[i2][j2] < 0) && (my2Array[i2][j2 + 1] < 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] > 0))) {
                                    line = undefined;
                                    if (my2Array[i2][j2] > 0)
                                        line = 'M' + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth + ',' + j2 * littleBlockHeight;
                                    else
                                        line = 'M' + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth + ',' + j2 * littleBlockHeight;
                                    triangle = paper.path(line);
                                    triangle.translate(i * blockWidth, j * blockHeight);
                                    triangle.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                                }
                                else
                                    if (((my2Array[i2][j2] > 0) && (my2Array[i2][j2 + 1] < 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] < 0)) || ((my2Array[i2][j2] < 0) && (my2Array[i2][j2 + 1] > 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] > 0))) {
                                        line = undefined;
                                        if (my2Array[i2][j2] > 0)
                                            line = 'M' + (i2) * littleBlockWidth + ',' + (j2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2][j2 + 1])) * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight;
                                        else
                                            line = 'M' + (i2) * littleBlockWidth + ',' + (j2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2][j2 + 1])) * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight;
                                        triangle = paper.path(line);
                                        triangle.translate(i * blockWidth, j * blockHeight);
                                        triangle.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                                    }
                                    else
                                        if (((my2Array[i2][j2] > 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] < 0) && (my2Array[i2][j2 + 1] < 0)) || ((my2Array[i2][j2] < 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] > 0) && (my2Array[i2][j2 + 1] > 0))) {
                                            line = undefined;
                                            if (my2Array[i2][j2] > 0)
                                                line = 'M' + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2) * littleBlockWidth + ',' + (j2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2][j2 + 1])) * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + j2 * littleBlockHeight + 'z';
                                            else
                                                line = 'M' + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2) * littleBlockWidth + ',' + (j2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2][j2 + 1])) * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'z';
                                            triangle = paper.path(line);
                                            triangle.translate(i * blockWidth, j * blockHeight);
                                            triangle.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                                        }
                                        else
                                            if (((my2Array[i2][j2] < 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] < 0) && (my2Array[i2][j2 + 1] < 0)) || ((my2Array[i2][j2] > 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] > 0) && (my2Array[i2][j2 + 1] > 0))) {
                                                line = undefined;
                                                if (my2Array[i2 + 1][j2] > 0)
                                                    line = 'M' + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'z';
                                                else
                                                    line = 'M' + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + j2 * littleBlockHeight + 'z';
                                                triangle = paper.path(line);
                                                triangle.translate(i * blockWidth, j * blockHeight);
                                                triangle.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                                            }
                                            else
                                                if (((my2Array[i2][j2] < 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] < 0) && (my2Array[i2][j2 + 1] > 0)) || ((my2Array[i2][j2] > 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] > 0) && (my2Array[i2][j2 + 1] < 0))) {
                                                    line = undefined;
                                                    if (my2Array[i2][j2 + 1] > 0)
                                                        line = 'M' + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2) * littleBlockWidth + ',' + (j2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2][j2 + 1])) * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'z';
                                                    else
                                                        line = 'M' + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2) * littleBlockWidth + ',' + (j2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2][j2 + 1])) * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'z';
                                                    triangle = paper.path(line);
                                                    triangle.translate(i * blockWidth, j * blockHeight);
                                                    triangle.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                                                }
                                                else
                                                    if (((my2Array[i2][j2] < 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] > 0) && (my2Array[i2][j2 + 1] < 0)) || ((my2Array[i2][j2] > 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] < 0) && (my2Array[i2][j2 + 1] > 0))) {
                                                        line = undefined;
                                                        if (my2Array[i2 + 1][j2 + 1] > 0)
                                                            line = 'M' + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'z';
                                                        else
                                                            line = 'M' + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'z';
                                                        triangle = paper.path(line);
                                                        triangle.translate(i * blockWidth, j * blockHeight);
                                                        triangle.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                                                    }
                                                    else
                                                        if (((my2Array[i2][j2] > 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] > 0) && (my2Array[i2][j2 + 1] < 0)) || ((my2Array[i2][j2] < 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] < 0) && (my2Array[i2][j2 + 1] > 0))) {
                                                        }

                            }
                        }
                    }
                }
            }
        }//draw
        //=========================JSON Controller===========================
        function JsonController(){
    	    this.buttonClick = function(button){
    	    	function objFactory(){
    	    		  return {
    	    		    constructor : objFactory
    	    		  };
    	    		};
    	    	$.post("/Globerry/selecttag", 
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
    	    	//myDataArray = new Array();
    	    	//alert("!!!");
    	    	$.getJSON("/Globerry/getcities",
    	            function(data) {
    	    			redraw(data, 2);
    	                // do something with the data
    	                //myDataArray = data;
    	    			
    	          });
    	    	//(myDataArray[0].name);
    	    	//return myDataArray;
    	    };
    	    this.rangeChange = function(input){
    	    	
    	    };
    	};
}

