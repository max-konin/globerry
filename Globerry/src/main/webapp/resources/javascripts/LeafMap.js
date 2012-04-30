/*window.onload = function () {
    init();
}
*/
function init() {
	//alert("123");
	//version with normal map;
    var Width = $(document.getElementById("map")).width();
    var Height = $(document.getElementById("map")).height();
    var arrln = 200, i, j;
    if(Width < 1200)
    	arrln = 100;
    var myArray = new Array();
    var p = 2;
    var level = 1.6;
    var begLevel = level;
    var blockWidth = Width / arrln;
    var blockHeight = Height / arrln;
    var polygons = new Array();
    var R = 50;
    
    var JSONContr = new JsonController(); 

    for (i = 0; i < arrln + 1; i++) {
        myArray[i] = new Array();
        for (j = 0; j < arrln + 1; j++) {
            myArray[i][j] = 0;
        }
    }

    var circle_counter;
    var my2Array = new Array();
    var i2, j2;
    var arrln2 = 3;
    if(Width < 1200)
    	arrln2 = 5;
    var littleBlockWidth = blockWidth / arrln2;
    var littleBlockHeight = blockHeight / arrln2;


    var map = new L.Map('map');

    var cloudmadeUrl = 'http://{s}.tile.cloudmade.com/BC9A493B41014CAABB98F0471D759707/997/256/{z}/{x}/{y}.png';
    var cloudmadeAttribution = 'Map data &copy; 2011 OpenStreetMap contributors, Imagery &copy; 2011 CloudMade';
    var cloudmade = new L.TileLayer(cloudmadeUrl, { maxZoom: 18, attribution: cloudmadeAttribution });

    map.setView(new L.LatLng(51.505, -0.09), 13).addLayer(cloudmade); //London 13
	JSONContr.rangeChange();
	
    //debag
    //var cir = [[100, 100, 50], [150, 200, 50], [100, 300, 50]];
    //debag
    var circleOptions = {
        color: 'blue',
        fillColor: 'blue',
        fillOpacity: 0.1,
        stroke: false
    };
    var polygonOptions = {
        //color: 'blue',
        fillColor: 'blue',
        fillOpacity: 0.3,
        //strokeOpacity: 0,
        weight: 100,
        //stroke: false
    };
    bounds = map.getBounds();
    var polygons = new Array();

    //-------------------------------------------------------------------------------------------------------
    function redraw(arrayOfCityes, size) {
    	//alert('123');
        for (i2 = 0; i2 < arrln2 + 1; i2++) {
            my2Array[i2] = new Array();
            for (j2 = 0; j2 < arrln2 + 1; j2++) {
                my2Array[i2][j2] = 0;
            }
        }
        
        for (i = 0; i < arrln + 1; i++) {
            for (j = 0; j < arrln + 1; j++) {
                myArray[i][j] = 0;
            }
        }
        
        //alert(map.getZoom());
        R = 50*map.getZoom()/13;
        if(R < 30) R*=1.03;
        if(R < 20) R*=1.02;
        var circle_counter = 0;
        for (i = 0; i < arrln + 1; i++) {
            for (j = 0; j < arrln + 1; j++) {
                for (circle_counter in arrayOfCityes) {
                	var cityLocation = new L.LatLng(arrayOfCityes[circle_counter].latitude , arrayOfCityes[circle_counter].longitude);
                	var cityPoint = map.latLngToLayerPoint(cityLocation);
                	var myCityX = (map.layerPointToContainerPoint(cityPoint)).x;
                	var myCityY = (map.layerPointToContainerPoint(cityPoint)).y;
                    var some_math_operations = (Math.pow(Math.sqrt((i * blockWidth - myCityX) * (i * blockWidth - myCityX) + (j * blockHeight - myCityY) * (j * blockHeight - myCityY)), p));
                    if (some_math_operations) {
                        myArray[i][j] += (6 * (R*R) / some_math_operations - level);
                    }
                    else
                        myArray[i][j] += level * 10;
                        
                }//for[circles]
            }//for[j]
        }//for[i]


        draw(arrayOfCityes);
    }
    var citiesLayer = new L.LayerGroup();
    function draw(arrayOfCityes) {
    	citiesLayer.clearLayers();
        for (polygonsCounter in polygons) {
            map.removeLayer(polygons[polygonsCounter]);
            polygons[polygonsCounter] = 0;
        }

        var polygonsCounter = 0;
        var currentPolygon = 0;
        var drawingCirclesCounter = 0;
        var drawingCircles = new Array();
        var circleLocation;
  
        for (i = 0; i < arrln; i++) {
            for (j = 0; j < arrln; j++) {
                if (!((myArray[i][j] < 0) && (myArray[i + 1][j] < 0) && (myArray[i][j + 1] < 0) && (myArray[i + 1][j + 1] < 0))) {
                    if (((myArray[i][j] > 0) && (myArray[i + 1][j] > 0) && (myArray[i][j + 1] > 0) && (myArray[i + 1][j + 1] > 0))) {
                        var j0 = j;
                        while (((myArray[i][j + 1] > 0) && (myArray[i + 1][j + 1] > 0) && (myArray[i][j + 2] > 0) && (myArray[i + 1][j + 2] > 0)) && (j < arrln)) j++;
                   
                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth, j0 * blockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + blockWidth, j0 * blockHeight + (j - j0 + 1) * blockHeight)),containerPointToLatLng(new L.Point(i * blockWidth, j0 * blockHeight + (j - j0 + 1) * blockHeight))];
                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth, j0 * blockHeight)),containerPointToLatLng(new L.Point(i * blockWidth + blockWidth, j0 * blockHeight + (j - j0 + 1) * blockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + blockWidth, j0 * blockHeight))];
                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                        
                        j++;
                    } //if(4+)
                    for (i2 = 0; i2 < arrln2 + 1; i2++) {
                        for (j2 = 0; j2 < arrln2 + 1; j2++) {
                                my2Array[i2][j2] = 0;
                         }
                    }//memset(my2Array, 0, ..);

                    for (i2 = 0; i2 < arrln2 + 1; i2++) {
                        for (j2 = 0; j2 < arrln2 + 1; j2++) {
                            for (circle_counter in arrayOfCityes) {                            	
                                //var some_math_operations = (Math.pow(Math.sqrt((i * blockWidth + i2 * littleBlockWidth - ((myCitiesArray[circle_counter].longitude - bounds.getNorthEast().lat)/ (bounds.getSouthWest().lat - bounds.getNorthEast().lat)*$(document.getElementById("map")).width())) * (i * blockWidth + i2 * littleBlockWidth - ((myCitiesArray[circle_counter].longitude - bounds.getNorthEast().lat)/(bounds.getSouthWest().lat - bounds.getNorthEast().lat)*$(document.getElementById("map")).width())) + (j * blockHeight + j2 * littleBlockHeight - ((myCitiesArray[circle_counter].latitude - bounds.getNorthEast().lng)/(bounds.getSouthWest().lng - bounds.getNorthEast().lng)*$(document.getElementById("map")).height())) * (j * blockHeight + j2 * littleBlockHeight - ((myCitiesArray[circle_counter].latitude - bounds.getNorthEast().lng)/(bounds.getSouthWest().lng - bounds.getNorthEast().lng)*$(document.getElementById("map")).height()))), p));
                                    	var cityLocation = new L.LatLng(arrayOfCityes[circle_counter].latitude , arrayOfCityes[circle_counter].longitude);
                                    	var cityPoint = map.latLngToLayerPoint(cityLocation);
                                    	var myCityX = (map.layerPointToContainerPoint(cityPoint)).x;
                                    	var myCityY = (map.layerPointToContainerPoint(cityPoint)).y;
                                        var some_math_operations = (Math.pow(Math.sqrt((i * blockWidth + i2 * littleBlockWidth - myCityX) * (i * blockWidth + i2 * littleBlockWidth - myCityX) + (j * blockHeight + j2 * littleBlockHeight - myCityY) * (j * blockHeight + j2 * littleBlockHeight - myCityY)), p));
                                        if (some_math_operations) {
                                            my2Array[i2][j2] += (6 * (R*R) / some_math_operations - level);
                                        }
                                        else
                                            my2Array[i2][j2] += level * 10;
                            }//for[circles]
                        }//for[j]
                    }//for[i]

                    for (i2 = 0; i2 < arrln2; i2++) {
                        for (j2 = 0; j2 < arrln2; j2++) {                            
                            if ((my2Array[i2][j2] > 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2][j2 + 1] > 0) && (my2Array[i2 + 1][j2 + 1] > 0)) {
                                var j0 = j2;
                                while (((my2Array[i2][j2 + 1] > 0) && (my2Array[i2 + 1][j2 + 1] > 0) && (my2Array[i2][j2 + 2] > 0) && (my2Array[i2 + 1][j2 + 2] > 0)) && (j2 < arrln2)) j2++;
                                polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + i2 * littleBlockWidth, j * blockHeight +j0 * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + i2 * littleBlockWidth + littleBlockWidth, j * blockHeight +j0 * littleBlockHeight + (j2 - j0 + 1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + i2 * littleBlockWidth, j * blockHeight +j0 * littleBlockHeight + (j2 - j0 + 1) * littleBlockHeight))];
                                polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + i2 * littleBlockWidth, j * blockHeight +j0 * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + i2 * littleBlockWidth + littleBlockWidth, j * blockHeight +j0 * littleBlockHeight + (j2 - j0 + 1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + i2 * littleBlockWidth + littleBlockWidth, j * blockHeight +j0 * littleBlockHeight))];
                                polygons[polygonsCounter++] = new L.Polygon(polygonPoints);                         
                                j2++;                                    
                            }//if[4+]
                             if (((my2Array[i2][j2] > 0) && (my2Array[i2][j2 + 1] > 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] < 0)) || ((my2Array[i2][j2] < 0) && (my2Array[i2][j2 + 1] < 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] > 0))) {
                                    if (my2Array[i2][j2] > 0) {
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + i2 * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + i2 * littleBlockWidth, j * blockHeight +(j2 + 1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth, j * blockHeight +(j2 + 1) * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + i2 * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth, j * blockHeight +(j2 + 1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                    } else
                                    {
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2+1) * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2+1) * littleBlockWidth, j * blockHeight +(j2 + 1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth, j * blockHeight +(j2 + 1) * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2+1) * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth, j * blockHeight +(j2 + 1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                    } 
                                } //if[2+|2-] (left & right)
                                if (((my2Array[i2][j2] > 0) && (my2Array[i2][j2 + 1] < 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] < 0)) || ((my2Array[i2][j2] < 0) && (my2Array[i2][j2 + 1] > 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] > 0))) {
                                    if (my2Array[i2][j2] > 0){
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2) * littleBlockWidth, j * blockHeight +(j2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2][j2 + 1])) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2) * littleBlockWidth, j * blockHeight +(j2) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2 + 1) * littleBlockWidth, j * blockHeight +(j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2+1) * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2) * littleBlockWidth, j * blockHeight +(j2) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2 + 1) * littleBlockWidth, j * blockHeight +(j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                    } else
                                    {
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2) * littleBlockWidth, j * blockHeight +(j2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2][j2 + 1])) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2) * littleBlockWidth, j * blockHeight +(j2+1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2 + 1) * littleBlockWidth, j * blockHeight +(j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2+1) * littleBlockWidth, j * blockHeight +(j2+1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2) * littleBlockWidth, j * blockHeight +(j2+1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2 + 1) * littleBlockWidth, j * blockHeight +(j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                    }
                                }//if[2+|2-] (up&down)
                                if (((my2Array[i2][j2] > 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] < 0) && (my2Array[i2][j2 + 1] < 0)) || ((my2Array[i2][j2] < 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] > 0) && (my2Array[i2][j2 + 1] > 0))) {
                                    if (my2Array[i2][j2] > 0){
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2) * littleBlockWidth, j * blockHeight +(j2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2][j2 + 1])) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2) * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                    } else
                                    {
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2) * littleBlockWidth, j * blockHeight +(j2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2][j2 + 1])) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2) * littleBlockWidth, j * blockHeight +(j2 + 1) * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2+1) * littleBlockWidth, j * blockHeight +(j2 + 1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2) * littleBlockWidth, j * blockHeight +(j2 + 1) * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2+1) * littleBlockWidth, j * blockHeight +(j2 + 1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2+1) * littleBlockWidth, j * blockHeight +(j2) * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                    }
                                }//if[3+|1-] upper left
                                if (((my2Array[i2][j2] < 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] < 0) && (my2Array[i2][j2 + 1] < 0)) || ((my2Array[i2][j2] > 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] > 0) && (my2Array[i2][j2 + 1] > 0))) {
                                    if (my2Array[i2 + 1][j2] > 0){
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2+1) * littleBlockWidth, j * blockHeight +(j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2+1) * littleBlockWidth, j * blockHeight +(j2) * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                    } else
                                    {
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth , j * blockHeight +j2 * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2+1) * littleBlockWidth, j * blockHeight +(j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2+1) * littleBlockWidth, j * blockHeight +(j2+1) * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth , j * blockHeight +j2 * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2) * littleBlockWidth, j * blockHeight +(j2 + 1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2+1) * littleBlockWidth, j * blockHeight +(j2+1) * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth , j * blockHeight +j2 * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2) * littleBlockWidth, j * blockHeight +(j2 + 1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2) * littleBlockWidth, j * blockHeight +(j2) * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                    }
                                }//if[1+|3-] (upper right)
                                if (((my2Array[i2][j2] < 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] < 0) && (my2Array[i2][j2 + 1] > 0)) || ((my2Array[i2][j2] > 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] > 0) && (my2Array[i2][j2 + 1] < 0))) {
                                    if (my2Array[i2][j2 + 1] > 0){
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth ,j * blockHeight +(j2 + 1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2) * littleBlockWidth, j * blockHeight +(j2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2][j2 + 1])) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + i2 * littleBlockWidth, j * blockHeight +(j2 + 1) * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                    } else
                                    {
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth ,j * blockHeight +(j2 + 1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2) * littleBlockWidth, j * blockHeight +(j2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2][j2 + 1])) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + i2 * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth ,j * blockHeight +(j2 + 1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2+1) * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + i2 * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth ,j * blockHeight +(j2 + 1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2+1) * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2+1) * littleBlockWidth, j * blockHeight +(j2+1) * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);                                    
                                    }
                                }//if[1+|3-] (down left)
                                if (((my2Array[i2][j2] < 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] > 0) && (my2Array[i2][j2 + 1] < 0)) || ((my2Array[i2][j2] > 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] < 0) && (my2Array[i2][j2 + 1] > 0))) {
                                    if (my2Array[i2 + 1][j2 + 1] > 0){
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth ,j * blockHeight +(j2 + 1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2 + 1) * littleBlockWidth, j * blockHeight +(j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2 + 1) * littleBlockWidth, j * blockHeight +(j2 + 1) * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                    } else
                                    {
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth ,j * blockHeight +(j2 + 1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2 + 1) * littleBlockWidth, j * blockHeight +(j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2 + 1) * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth ,j * blockHeight +(j2 + 1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2) * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2 + 1) * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                        polygonPoints = [containerPointToLatLng(new L.Point(i * blockWidth + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth ,j * blockHeight +(j2 + 1) * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2) * littleBlockWidth, j * blockHeight +j2 * littleBlockHeight)), containerPointToLatLng(new L.Point(i * blockWidth + (i2) * littleBlockWidth, j * blockHeight +(j2+1) * littleBlockHeight))];
                                        polygons[polygonsCounter++] = new L.Polygon(polygonPoints);
                                    }
                                }//if[1+|3-] (down right)

                        }//for[j2]
                    }//for[i2]


                }//if!()
            }//for[j]
        }//for[i]
       
        for (polygonsCounter in polygons){
            polygons[polygonsCounter].setStyle({stroke : false});
            citiesLayer.addLayer(polygons[polygonsCounter]);
        }
        map.addLayer(citiesLayer);
        polygonsCounter = 0;

    }//draw

    JSONContr.cityRequest(1);

    //======================================Map novigation==================
    map.on('viewreset', function() {
        //catches view reset e.g. zooming or any of unexpected resets(!)    	
    	JSONContr.rangeChange();
        //JSONContr.cityRequest(1);
    });
    map.on('moveend', function() {
        //catches move end.
    	JSONContr.rangeChange();
        //JSONContr.cityRequest(1);
    });
   
        map.on('click', function(e) {
        //this function may be useful for debagging
        //alert(e.latlng);
        //var bounds = map.getBounds();
        //map.layerLatLngToPoint(bounds.getSouthWest());
        //alert(bounds.getSouthWest());
    });
     /**/


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
    	    this.rangeChange = function(){    	    	
    	    	$.post("/Globerry/rangechange", 
    	    			{minX : map.getBounds().getSouthWest().lng,
        	    		maxX : map.getBounds().getNorthEast().lng,
        	    		minY : map.getBounds().getSouthWest().lat,
        	    		maxY : map.getBounds().getNorthEast().lat},
        	    	  this.cityRequest(),
        	    	  "json");
    	    };
    	    this.tagChange = function(tagId){
    	    	$.post("/Globerry/tagchange", 
    	    			{id : tagId},
        	    	  null,
        	    	  "json");
    	    };
    	    this.mounthChange = function(monthName){
    	    	//monthName == JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;
    			$.post("/Globerry/monthchange", 
    					{month : monthName},
    		    	  null,
    		    	  "json");
    	    };
    	    this.sliderChange = function(idSlider, leftValueSlider, rightValueSlider){
    	    	$.post("/Globerry/sliderchange", 
    	    			{id : idSlider,
    	    			leftValue : leftValueSlider,
    	    			rightValue : rightValue},
        	    	  null,
        	    	  "json");
    	    };
    	    this.cityRequest = function(input){
    	    	$.getJSON("/Globerry/getcities",
    	            function(data) {
    	    			redraw(data, 0);
    	                // do something with the data
    	                //myDataArray = data;
    	          });
    	    };
    	};
    	function containerPointToLatLng(point){
    		return map.layerPointToLatLng((map.containerPointToLayerPoint(point)));
    	};


}

