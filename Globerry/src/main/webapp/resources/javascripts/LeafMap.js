/*window.onload = function () {
    init();
}
*/
function init(serverName) {
	/*
	 * To draw the curves I used http://en.wikipedia.org/wiki/Marching_squares algorithm,
	 * also, there are some changes in it, to draw curves faster. 
	 * */
	alert(serverName + "<---INIT()");
	headerChange(serverName); //for switching header.
	slidersInitialization(serverName);
	//alert("123");
	//version with normal map;
    var Width = $(document.getElementById("map")).width();
    var Height = $(document.getElementById("map")).height();
    var arrln = 80, i, j; //arrln 150 for prettier curves.
    var myArray = new Array();
    var p = 2;
    var level = 1.6; //level on which curves are drawing(not map level (read about matching squares if you're interested in this variable)), this level is used by default
    var begLevel = level;
    var blockWidth = Width / arrln;
    var blockHeight = Height / arrln;
    var polygons = new Array(); // due to the fact that each curve consist of large amount of object's htey are all stored in an array, to simplify styling, deleting and searching thow them
    var R = 50;
    
    var JSONContr = new JsonController(serverName);  	//object that is used to control all of the interactions between server and customer.

	//memst array by 0
    for (i = 0; i < arrln + 1; i++) {
        myArray[i] = new Array();
        for (j = 0; j < arrln + 1; j++) {
            myArray[i][j] = 0;
        }
    }

    var circle_counter;
	var popupArray = new Array(); 	//unfortunately, LeafMap.js dowsn't provide it's own way to type something on a map, so I used styled popups( for information about it's style see styles\map.css)
	var popupCounter = 0;
    var my2Array = new Array(); // here goes some modification of the presented algorithm, I created two arrays, one of them(first) draws only squares, and another(my2Array) draws borders. 
								// each time when it's seen that we have to draw a border in first array block(square) we divide it into parts and use the algorithm for this block, by using second array, thus we have
								// less amount of shapes(objects) where they are less needed, and large amount of shapes where they are more useful(on the borders), also the total amount of shapes is decreased by this modification, and the algorithm works faster.
    var i2, j2;	//just reusable counters for second array.
    var arrln2 = 3;

    var littleBlockWidth = blockWidth / arrln2; //for second array
    var littleBlockHeight = blockHeight / arrln2;


    var map = new L.Map('map');

	//previous map had url:
    //var cloudmadeUrl = 'http://{s}.tile.cloudmade.com/BC9A493B41014CAABB98F0471D759707/997/256/{z}/{x}/{y}.png';
    cloudmadeUrl = 'http://grwe.net/osm/{z}/{x}/{y}.png';
    var cloudmadeAttribution = 'Map data &copy; 2011 OpenStreetMap contributors, Imagery &copy; 2011 CloudMade';
    var cloudmade = new L.TileLayer(cloudmadeUrl, { maxZoom: 8, minZoom: 3, attribution: cloudmadeAttribution });

    map.setView(new L.LatLng(51.505, -0.09), 3).addLayer(cloudmade); //London 13
	JSONContr.rangeChange();//we make the server aware that map is created.
	
	//note that unfortunately, changes only in that options won't affect the image, but, also, if u dont change them u wont see any changes at all
	// to see any changes make them here and in the options in the end of draw function(where curves are actually drawn)
    var circleOptions = {
        color: 'blue',
        fillColor: 'blue',
        fillOpacity: 0.1,
        stroke: false
    };
    var polygonOptions = {
        fillColor: 'rgb(255, 132, 2)',
        fillOpacity: 0.3,
        weight: 100,
    };
    bounds = map.getBounds();
    var polygons = new Array();

    //-------------------------------------------------------------------------------------------------------
    function redraw(arrayOfCityes, size) {
    	//alert('redraw');
    	var numberOfCircles = 0;
    	for(circle_counter in arrayOfCityes) {
    		numberOfCircles++;
    	}
		// when there are less then 10 cities it looks pretty with 1.6 level, but it shouldn't with many circles, so we have to test it and make clustering before deciding what should represent when there are more than 10 cities 
    	if(numberOfCircles > 10)
    		level = 1.7;
    	else
    		level = 1.6;
    	popupCounter = 0;
		//memset null
        for (i2 = 0; i2 < arrln2 + 1; i2++) {
            my2Array[i2] = new Array();
            for (j2 = 0; j2 < arrln2 + 1; j2++) {
                my2Array[i2][j2] = 0;
            }
        }
        //memset null
        for (i = 0; i < arrln + 1; i++) {
            for (j = 0; j < arrln + 1; j++) {
                myArray[i][j] = 0;
            }
        }
        
        //city radius.
        R = 100*map.getZoom()/10;
        if(R < 30) R*=1.03;
        if(R < 20) R*=1.02;
        var circle_counter = 0;
        for (i = 0; i < arrln + 1; i++) {
            for (j = 0; j < arrln + 1; j++) {
                for (circle_counter in arrayOfCityes) {
				//changing our coordinates(from server) to coordinates on the screen.
                	var cityLocation = new L.LatLng(arrayOfCityes[circle_counter].latitude , arrayOfCityes[circle_counter].longitude);
                	var cityPoint = map.latLngToLayerPoint(cityLocation);
                	var myCityX = (map.layerPointToContainerPoint(cityPoint)).x;
                	var myCityY = (map.layerPointToContainerPoint(cityPoint)).y;
					// see the algorithm to understand this.
                    var some_math_operations = (Math.pow(Math.sqrt((i * blockWidth - myCityX) * (i * blockWidth - myCityX) + (j * blockHeight - myCityY) * (j * blockHeight - myCityY)), p));
                    if (some_math_operations) {
                    	var cityRadius = R*arrayOfCityes[circle_counter].weight;
                    	if(arrayOfCityes[circle_counter].weight == NaN)
                    		cityRadius = R;
                    	//alert(cityRadius);
                        myArray[i][j] += (6 * (cityRadius*cityRadius) / some_math_operations - level);
                    }
                    else //if, for some mythical reasons we get division by zero of some other unexpected change, we are just saying that that city is very large.
                        myArray[i][j] += level * 10;
                        
                }//for[circles]
            }//for[j]
        }//for[i]


        draw(arrayOfCityes);
    }
	
    var citiesLayer = new L.LayerGroup();
    function draw(arrayOfCityes) {
    	citiesLayer.clearLayers();
		//clearing previous array and memset(without it, js doesn't always works correctly Oo)
        for (polygonsCounter in polygons) {
            map.removeLayer(polygons[polygonsCounter]);
            polygons[polygonsCounter] = 0;
        }
        for(popupCounter in popupArray){
            map.removeLayer(popupArray[popupCounter]);
            popupArray[popupCounter] = 0;
        }
		
		// here goes the part that disposes the cities names.
        for (circle_counter in arrayOfCityes) {
        	var delta = 30;//for fixing bug.
			//changing coordinates from server to our, guess it should be in a individual function.
        	var cityLocation = new L.LatLng(arrayOfCityes[circle_counter].latitude , arrayOfCityes[circle_counter].longitude);
        	var cityPoint = map.latLngToLayerPoint(cityLocation);
        	var myCityX = (map.layerPointToContainerPoint(cityPoint)).x;
        	var myCityY = (map.layerPointToContainerPoint(cityPoint)).y;
			//that "if" fixes magic bug that we've seen mostly in Opera, but, I've caught it in Chrome several times too.
        	if(!((((($("#map").width() - myCityX)) < delta) || (myCityX < delta)) || (myCityY < delta) || (($("#map").height() - myCityY) < delta)))
        	{
	        	popupArray[popupCounter] = new L.Popup();
	        	popupArray[popupCounter].setLatLng(cityLocation);
	        	popupArray[popupCounter].setContent(arrayOfCityes[circle_counter].name);
	        	map.addLayer(popupArray[popupCounter]);
	        	popupCounter++;
        	}
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
                        while (((myArray[i][j + 1] > 0) && (myArray[i + 1][j + 1] > 0) && (myArray[i][j + 2] > 0) && (myArray[i + 1][j + 2] > 0)) && (j < arrln)) 
                        	j++;                   
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

					 //algorithm with some changes.
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
                                        	var cityRadius = R*arrayOfCityes[circle_counter].weight;
                                        	if(arrayOfCityes[circle_counter].weight == NaN)
                                        		cityRadius = R*0.2;
                                        	//var myCityRadiusFor2Array = R*arrayOfCityes[circle_counter].weight;
                                        	//alert(arrayOfCityes[circle_counter].weight);
                                            my2Array[i2][j2] += (6 * (cityRadius*cityRadius) / some_math_operations - level);
                                        }
                                        else
                                            my2Array[i2][j2] += level * 10;
                            }//for[circles]
                        }//for[j]
                    }//for[i]

                    //here goes the change of the algoritm that was said above.
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
       
	   //all polygons addings are here, also if you want to change curves styling make it here and int the top of the document.
        for (polygonsCounter in polygons){
            polygons[polygonsCounter].setStyle({stroke : false, color : 'rgb(255, 132, 2)', fillOpacity : 0.5});
            citiesLayer.addLayer(polygons[polygonsCounter]);
        }
        //******************видимо, это и есть то место, где вырисовывыются города************************
		// Yep, not apparently, for sure all curves are drawn here.(author comment)
        //UNCOMMENT NEXT LINE
        //map.addLayer(citiesLayer);
        polygonsCounter = 0;
    }//draw

    //JSONContr.cityRequest(1);
    //==================================slidersInitialization===================================
    function slidersInitialization(serverName) {
    	
        var JSONContr = new JsonController(serverName); 

		//object that is stwo handeled slider, I used JQuerry slider plagin for it.
        function mySlider(sliderId, leftLimit, rightLimit, measure, div, leftInput, rightInput, pos) {
			//div is div id, pos is the position of measure symbol and can be "l" for left position of "r" fo
			// rigth postion for example +30 has "r" for plus and $150 has "l" fo $  
        	var sliderId = 0;
    		var leftMeasure = "";
    		var rightMeasure = "";
    		if (pos == "l")
    			leftMeasure = measure;
    		if(pos == "r")
    			rightMeasure = measure;
			//should be a parametr of cunstructor too. But as comment downsatairs says due to some strage bug, it's here.
    		if(div == "tempSlider") sliderId = 1;
    		if(div == "alchSlider") sliderId = 2;
    		if(div == "timeSlider") sliderId = 3;
    		if(div == "LivSlider") sliderId = 4;
    		if(div == "foodSlider") sliderId = 5;/*due to some sstrange bag*/
			//actually slider.
            jQuery("#" + div).slider({
                min: leftLimit,
                max: rightLimit,
                values: [leftLimit, rightLimit],
                range: true,
                stop: function (event, ui) {
    			if(measure == "+")
    				if((jQuery("#" + div).slider("values", 0)) > 0)
    					leftMeasure = "+";
    				else
    					leftMeasure = "";
                jQuery("input#" + leftInput).val(leftMeasure + (jQuery("#" + div).slider("values", 0)) + rightMeasure);
    			if(measure == "+")
    				if((jQuery("#" + div).slider("values", 1)) > 0)
    					leftMeasure = "+";
    				else
    					leftMeasure = "";
                jQuery("input#" + rightInput).val(leftMeasure + (jQuery("#" + div).slider("values", 1)) + rightMeasure);
                    //Important! values of the inputs, will change only after the previous two lines.

                JSONContr.sliderChange(sliderId, jQuery("#" + div).slider("values", 0), jQuery("#" + div).slider("values", 1));
                JSONContr.cityRequest();
                },
                slide: function (event, ui) {
    				if(measure == "+")
    					if((jQuery("#" + div).slider("values", 0)) > 0)
    						leftMeasure = "+";
    					else
    						leftMeasure = "";
                    jQuery("input#" + leftInput).val(leftMeasure + (jQuery("#" + div).slider("values", 0)) + rightMeasure);
    				if(measure == "+")
    					if((jQuery("#" + div).slider("values", 1)) > 0)
    						leftMeasure = "+";
    					else
    						leftMeasure = "";
                    jQuery("input#" + rightInput).val(leftMeasure + (jQuery("#" + div).slider("values", 1)) + rightMeasure);
                }
            });
        };
		
		//one handled slider is much easier to understand, so if, u are interested in the way how sliders works, look first here, and read about Jquerry slider plugin on jquerry plugin page.
        function myOneHandledSlider(div, sliderId){
        	jQuery("#" + div).slider({
        		min :1,
        		max : 3,
        		value : 2,
        		stop: function (event, ui) {
        			//alert(sliderId);
                    JSONContr.sliderChange(sliderId, jQuery("#" + div).slider("values", 0), jQuery("#" + div).slider("values", 1));
                    JSONContr.cityRequest();
        		}
        	});
        }
		//initialisation of sliders
        firstSL = new mySlider(1, -35, 35, "+", "tempSlider", "minCost", "maxCost", "l");
        firstSL = new mySlider(2, 0, 30, "$", "alchSlider", "alcMinCost", "alcMaxCost", "l");
        firstSL = new mySlider(3, 0, 24, " Ч", "timeSlider", "TimeMinV", "TimeMaxV", "r");
        firstSL = new myOneHandledSlider("MoodSlider", 6);
        firstSL = new mySlider(4, 0, 300, "$", "LivSlider", "LivMinV", "LivMaxV", "l");
        firstSL = new myOneHandledSlider("securitySlider", 7);
        firstSL = new mySlider(5, 0, 100, "$", "foodSlider", "FoodMinV", "FoodMaxV", "l");
        firstSL = new myOneHandledSlider("SexSlider", 8);
    }
    
    //===========================================headerChange====================================
    // Some overwiew about list's. I didn't find any plugin for list's consisted of images(actually,
    // I've searched only for few hours coz it was faster to write my own image based list, than search
    // more time), so if you want change list's form image based to text based, u may change all the logic of
    // interaction with them, including changing of header text, or,  u may just replace images in my lists with text,
    // coz they work normal.
    function headerChange(serverName)
    {
    	
        var JSONContr = new JsonController(serverName); 
        //string constants.
    	WhoStr = new Array();
    	WhoStr[2] = "Мы с друзьями едем ";
    	WhoStr[3] = "Мы с семьей едем ";
    	WhoStr[1] = "Я один еду ";
    	WhoStr[4] = "Мы вдвоем едем ";
    	
    	WhatStr = new Array();
    	WhatStr[1] = "загорать ";
    	WhatStr[2] = "кататься ";
    	WhatStr[3] = "смотреть достопримечательности ";
    	WhatStr[4] = "за покупками ";
    	WhatStr[5] = "в круиз ";
    	
    	WhenStr = new Array();
    	WhenStr[1] = "в январе!";
    	WhenStr[2] = "в феврале!";
    	WhenStr[3] = "в марте!";
    	WhenStr[4] = "в апреле!";
    	WhenStr[5] = "в мае!";
    	WhenStr[6] = "в июне!";
    	WhenStr[7] = "в июле!";
    	WhenStr[8] = "в августе!";
    	WhenStr[9] = "в сентябре!";
    	WhenStr[10] = "в октябре!";
    	WhenStr[11] = "в ноябре!";
    	WhenStr[12] = "в декабре!";

    	
    	var prevBotBut; //bottom buttoms
    	var bottomActive = false;
    	
    	// function for sliding the second header down and for hiding the first header.
    	$(".headerButton").click(function (event) {
    		configureText();
    		$(prevList).animate({height:0}, "fast"); // switchs list off
    		SelectActive = false;
    		$("#headContent1").animate({height:0},"slow", function() {
    			//animation to represent changes.
    			$("#headContent1").hide();
    			$("#headContent2").show();
    			$("#headText").show();
    			$("#bottomToggle").slideToggle("slow");
    			$("#bottom").show();
    			$("#HeaderButtonUp").show();
    			$("#UpperHeaderBlockWithCircle").show();
    			$("#UpperHeaderBlockWithCircle").animate({opacity:1}, "slow");
    			$("#HeaderButtonUp").animate({opacity:1}, "slow");
    			$("#headContent2").animate({height:100}, "slow", function (){
    				$("#calendar").show();
    				$("#calendar").animate({height:26}, "slow", function(){				
    					//here I wanted to fix bug with ctrange appearance of the calendar.
    				});
    			});
    		});
    	});	
    	// as previous function this hides the second header and shows the first, with aimation.
    	$(".headerUpSwitcher").click(function (event) {
    		//animation.
    		$("#headText").hide();
    		$("#calendar").animate({height:0}, "normal", function (){
    			$("#calendar").hide();
    			$("#HeaderButtonUp").animate({opacity:0}, "slow");
    			$("#UpperHeaderBlockWithCircle").animate({opacity:0}, "slow");
    			$("#headContent2").animate({height:0},"slow", function() {
    				$("#HeaderButtonUp").hide();
    				$("#UpperHeaderBlockWithCircle").hide();
    				$("#headContent2").hide();
    				$("#headContent1").show();
    				$("#headContent1").animate({height:38}, "slow");
    			});
    		});
    	});	
    	
    	prev = "#JanBG";	//first;
    	// changes calendar buttons(div's actually), all stuff here consists of divs.
    	$(".upperClicableCaendar").click(function (event) {
    		if(prev!= ("#" + this.id + "G")){
    			$(prev).animate({opacity:0}, "fast");
    			$("#" + this.id + "G").animate({opacity:1}, "fast");
    			prev = "#" + this.id + "G";
    			$(prevSelect[3]).hide();
    			if(this.id == "JanB"){
    				prevSelect[3] = "#janSelected";	
    			}
    			if(this.id == "FebB"){
    				prevSelect[3] = "#febSelected";	
    			}
    			if(this.id == "MarB"){
    				prevSelect[3] = "#marSelected";	
    			}
    			if(this.id == "AprB"){
    				prevSelect[3] = "#aprSelected";	
    			}
    			if(this.id == "MayB"){
    				prevSelect[3] = "#maySelected";	
    			}
    			if(this.id == "JunB"){
    				prevSelect[3] = "#junSelected";	
    			}
    			if(this.id == "JulB"){
    				prevSelect[3] = "#julSelected";	
    			}
    			if(this.id == "AugB"){
    				prevSelect[3] = "#augSelected";	
    			}
    			if(this.id == "SepB"){
    				prevSelect[3] = "#sepSelected";	
    			}
    			if(this.id == "OctB"){
    				prevSelect[3] = "#octSelected";	
    			}
    			if(this.id == "NovB"){
    				prevSelect[3] = "#novSelected";	
    			}
    			if(this.id == "DecB"){
    				prevSelect[3] = "#decSelected";	
    			}
    			$(prevSelect[3]).show();
    			configureText();
    		}

    	});
    	
    	SelectActive = false;
    	var prevList;
    	var SelectActiveNumber;
    	// List's about them there was written above, also, I think, it's better to rewrite them in object based style.
    	$(".ListButton").click(function (event) {
    	if(this.id == "WhoSelect"){
    		SelectActiveNumber = 1;
    	}
    	if(this.id == "WhatSelect"){
    		SelectActiveNumber = 2;
    	}
    	if(this.id == "WhenSelect"){
    		SelectActiveNumber = 3;
    	}	
    	//alert(this.id);
    		if(SelectActive == false){
    				//animation
    				prevList = "#" + this.id + "BG";
    				a = $(prevList).css("max-height");
    				$(prevList).show();
    				$(prevList).animate({height:a}, "slow");
    				SelectActive = true;
    			} else
    			{
				    //animaion
    				if(prevList != "#" + this.id + "BG"){
    					$(prevList).animate({height:0}, "slow");
    					prevList = "#" + this.id + "BG";
    					a = $(prevList).css("max-height");
    					$(prevList).show();
    					$(prevList).animate({height:a}, "slow");
    				} else {
    					$(prevList).animate({height:0}, "slow", function(){
    						$(prevList).hide();
    					});				
    					SelectActiveNumber = 0;
    					SelectActive = false;
    				}
    			}
    	}); 
    	// inititalisation of selects
    	prevSelect = new Array();
    	prevSelect[1] = "#aloneSelected";
    	prevSelect[2] = "#tanSelected";
    	prevSelect[3] = "#janSelected";
    	TextSelectorWho = 1;
    	TextSelectorWhat = 1;
    	TextSelectorWhen = 1;
    	// animatin on click in the inner space of the list.
    	$(".selectItem").click(function (event) { 
    	if(SelectActiveNumber != 0){
    			$(prevSelect[SelectActiveNumber]).hide();
    			$("#" + this.id + "Selected").show();
    			prevSelect[SelectActiveNumber] = "#" + this.id + "Selected";
    			$(prevList).animate({height:0}, "slow", function(){
    				$(prevList).hide();
    			});
    			SelectActive = false;
    			$(prev).animate({opacity:0}, "fast");
    			if(this.id == "jan"){
    				prev = "#" + "Jan" + "BG";
    			}
    			if(this.id == "feb"){
    				prev = "#" + "Feb" + "BG";
    			}
    			if(this.id == "mar"){
    				prev = "#" + "Mar" + "BG";
    			}
    			if(this.id == "apr"){
    				prev = "#" + "Apr" + "BG";
    			}
    			if(this.id == "may"){
    				prev = "#" + "May" + "BG";
    			}
    			if(this.id == "jun"){
    				prev = "#" + "Jun" + "BG";
    			}
    			if(this.id == "jul"){
    				prev = "#" + "Jul" + "BG";
    			}
    			if(this.id == "aug"){
    				prev = "#" + "Aug" + "BG";
    			}
    			if(this.id == "sep"){
    				prev = "#" + "Sep" + "BG";
    			}
    			if(this.id == "oct"){
    				prev = "#" + "Oct" + "BG";
    			}
    			if(this.id == "nov"){
    				prev = "#" + "Nov" + "BG";
    			}
    			if(this.id == "dec"){
    				prev = "#" + "Dec" + "BG";
    			}
    			$(prev).animate({opacity:1}, "fast");	
    		}
    		configureText();
    		//JSONContr.cityRequest();
    	});
    	// switch the bottom on and off, and changes the state of the buttom.
    	$(".bottomButton").click(function(){
    		//animation for switching bottoms.
    		if( bottomActive == false){
    			if(prevBotBut != undefined){
    				prevBotBut.style.background = 'rgb(37, 46, 64)';
    			}
    			prevBotBut = this;
    			$("#bottom").animate({bottom:147},"slow");
    			$("#WhiteBottom").animate({height:147},"slow", function () { $(("#" +prevBotBut.id + "B")).show(); });			
    			this.style.background = 'rgb(255, 122, 2)';
    			$(this).animate({width:124}, 0);
    			bottomActive = true;
    			prevBotBut = this;
    		} else
    		if(bottomActive == true){
    			if(prevBotBut == this){
    				$("#bottom").animate({bottom:0},"slow");
    				$(("#" +prevBotBut.id + "B")).hide();
    				$("#WhiteBottom").animate({height:0},"slow");
    				this.style.background = 'rgb(37, 46, 64)';
    				$(this).animate({width:120}, 0);
    				bottomActive = false;
    			} else
    			{
    				this.style.background = 'rgb(255, 122, 2)';
    				$(("#" +prevBotBut.id + "B")).hide();
    				$(this).animate({width:124}, 0);
    				prevBotBut.style.background = 'rgb(37, 46, 64)';
    				$(prevBotBut).animate({width:120}, 0);
    				prevBotBut = this;
    				$(("#" +prevBotBut.id + "B")).show();
    			}
    		}
    	});
		
    	//function that configures text that is presented in the upper space if the header(rigth to globerry image)
    	//it's based on the id's of the list blocks, that are currently selected.
    	function configureText(){
    		var prevSelectInFirst = TextSelectorWho;
    		if(prevSelect[1] == "#aloneSelected"){
    			TextSelectorWho = 1;
    		}
    		if(prevSelect[1] == "#WithFriendsSelected"){
    			TextSelectorWho = 2;
    		}
    		if(prevSelect[1] == "#WithFamilySelected"){
    			TextSelectorWho = 3;
    		}
    		if(prevSelect[1] == "#CoupleSelected"){
    			TextSelectorWho = 4;
    		}
    		if(prevSelectInFirst != TextSelectorWho){
    			JSONContr.tagChange(TextSelectorWho);
    		}
    		
    		var prevSelectInSecond = TextSelectorWhat;
    		if(prevSelect[2] == "#tanSelected"){
    			TextSelectorWhat = 1;
    		}
    		if(prevSelect[2] == "#skiSelected"){
    			TextSelectorWhat = 2;
    		}

    		if(prevSelect[2] == "#watchSelected"){
    			TextSelectorWhat = 3;
    		}
    		if(prevSelect[2] == "#shoppingSelected"){
    			TextSelectorWhat = 4;
    		}
    		if(prevSelect[2] == "#cruiseSelected"){
    			TextSelectorWhat = 5;
    		}
    		
    		if(prevSelectInSecond != TextSelectorWhat){
    			JSONContr.tagChange(TextSelectorWhat + 4);
    		}
    		
    		var prevSelectInThird = TextSelectorWhen;
    		var MonthNameInJcontrSelectionAfterTagChange;
        	//monthName == JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;
    		if(prevSelect[3] == "#janSelected"){
    			TextSelectorWhen = 1;
    			MonthNameInJcontrSelectionAfterTagChange = "JANUARY";
    		}
    		if(prevSelect[3] == "#febSelected"){
    			TextSelectorWhen = 2;
    			MonthNameInJcontrSelectionAfterTagChange = "FEBRUARY";
    		}
    		if(prevSelect[3] == "#marSelected"){
    			TextSelectorWhen = 3;
    			MonthNameInJcontrSelectionAfterTagChange = "MARCH";
    		}
    		if(prevSelect[3] == "#aprSelected"){
    			TextSelectorWhen = 4;
    			MonthNameInJcontrSelectionAfterTagChange = "APRIL";
    		}
    		if(prevSelect[3] == "#maySelected"){
    			TextSelectorWhen = 5;
    			MonthNameInJcontrSelectionAfterTagChange = "MAY";
    		}
    		if(prevSelect[3] == "#junSelected"){
    			TextSelectorWhen = 6;
    			MonthNameInJcontrSelectionAfterTagChange = "JUNE";
    		}
    		if(prevSelect[3] == "#julSelected"){
    			TextSelectorWhen = 7;
    			MonthNameInJcontrSelectionAfterTagChange = "JULY";
    		}
    		if(prevSelect[3] == "#augSelected"){
    			TextSelectorWhen = 8;
    			MonthNameInJcontrSelectionAfterTagChange = "AUGUST";
    		}
    		if(prevSelect[3] == "#sepSelected"){
    			TextSelectorWhen = 9;
    			MonthNameInJcontrSelectionAfterTagChange = "SEPTEMBER";
    		}
    		if(prevSelect[3] == "#octSelected"){
    			TextSelectorWhen = 10;
    			MonthNameInJcontrSelectionAfterTagChange = "OCTOBER";
    		}
    		if(prevSelect[3] == "#novSelected"){
    			TextSelectorWhen = 11;
    			MonthNameInJcontrSelectionAfterTagChange = "NOVEMBER";
    		}
    		if(prevSelect[3] == "#decSelected"){
    			TextSelectorWhen = 12;
    			MonthNameInJcontrSelectionAfterTagChange = "DECEMBER";
    		}
    		if(prevSelectInThird != TextSelectorWhen){
    			JSONContr.mounthChange(MonthNameInJcontrSelectionAfterTagChange);
    		}
    		jQuery("input#HText").val( WhoStr[TextSelectorWho] + WhatStr[TextSelectorWhat] + WhenStr[TextSelectorWhen]);
    		JSONContr.cityRequest();
    	};
    	
    	$('#map').click(function(){
    		if((SelectActiveNumber !=0)&&(SelectActive)){
    			$(prevList).animate({height:0}, "slow", function(){
    				$(prevList).hide();
    			});				
    			SelectActiveNumber = 0;
    			SelectActive = false;
    		}	
    	});
    }

    //======================================Map novigation==================
    //fires event's on map changing, and make server aware about them.
    map.on('viewreset', function() {
        //catches view reset e.g. zooming or any of unexpected resets(!)    	
    	JSONContr.rangeChange();
        //JSONContr.cityRequest(1);
    });
    $('#map').mouseup(function(){
    	JSONContr.rangeChange();
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
       //wasnt written by me, so have no idea how it works.
        function JsonController(serverName){
    	    this.buttonClick = function(button){
    	    	function objFactory(){
    	    		  return {
    	    		    constructor : objFactory
    	    		  };
    	    		};
    	    	$.post(serverName + "/selecttag", 
    	    		{
    	    		id : 1
    	    		}
    	    	  ,
    	    	  jsonController.cityRequest(button),
    	    	  "json");
    	    	
    	    	
    	    	//jsonController.cityRequest(button);
    	    };
    	    if(serverName == "undefined") serverName ="";
    	    this.rangeChange = function(){    	    	
    	    	$.post(serverName + "/rangechange", 
    	    			{minX : map.getBounds().getSouthWest().lng,
        	    		maxX : map.getBounds().getNorthEast().lng,
        	    		minY : map.getBounds().getSouthWest().lat,
        	    		maxY : map.getBounds().getNorthEast().lat},
        	    	  this.cityRequest(),
        	    	  "json");
    	    };
    	    this.tagChange = function(tagId){
    	    	$.post(serverName + "/tagchange", 
    	    			{id : tagId},
        	    	  null,
        	    	  "json");
    	    };
    	    this.mounthChange = function(monthName){
    	    	//monthName == JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;
    			$.post(serverName + "/monthchange", 
    					{month : monthName},
    		    	  null,
    		    	  "json");
    	    };
    	    this.sliderChange = function(idSlider, leftValueSlider, rightValueSlider){
    	    	$.post(serverName + "/sliderchange", 
    	    			{id : idSlider,
    	    			leftValue : leftValueSlider,
    	    			rightValue : rightValueSlider},
        	    	  null,
        	    	  "json");
    	    };
    	    this.cityRequest = function(input){
    	    	$.getJSON(serverName + "/getcities",
    	            function(data) {
    	    			redraw(data, 0);
    	                // do something with the data
    	                //myDataArray = data;
    	          });
    	    };
    	};
		
    	//===================================================================
    	
    	// I dunno why leafmap libary doesnt have this function by default, but so it is Oo.
    	function containerPointToLatLng(point){
    		return map.layerPointToLatLng((map.containerPointToLayerPoint(point)));
    	};
}






