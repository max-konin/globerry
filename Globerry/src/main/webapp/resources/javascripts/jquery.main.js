function slidersInitialization() {
	
    var JSONContr = new JsonController(); 

    function mySlider(leftLimit, rightLimit, measure, div, leftInput, rightInput, pos) {
    	var sliderId = 0;
		var leftMeasure = "";
		var rightMeasure = "";
		if (pos == "l")
			leftMeasure = measure;
		if(pos == "r")
			rightMeasure = measure;
		if(div == "tempSlider") sliderId = 1;
		if(div == "alchSlider") sliderId = 2;
		if(div == "timeSlider") sliderId = 3;
		if(div == "LivSlider") sliderId = 4;
		if(div == "foodSlider") sliderId = 5;
		
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



    firstSL = new mySlider(-35, 35, "+", "tempSlider", "minCost", "maxCost", "l");
    firstSL = new mySlider(0, 30, "$", "alchSlider", "alcMinCost", "alcMaxCost", "l");
    firstSL = new mySlider(0, 24, " Ч", "timeSlider", "TimeMinV", "TimeMaxV", "r");
    $("#MoodSlider").slider();
    firstSL = new mySlider(0, 300, "$", "LivSlider", "LivMinV", "LivMaxV", "l");
    $("#securitySlider").slider();
    firstSL = new mySlider(0, 100, "$", "foodSlider", "FoodMinV", "FoodMaxV", "l");
    $("#SexSlider").slider();
    
    //=========================JSON Controller===========================
    function JsonController(){
	    this.buttonClick = function(button){
	    	function objFactory(){
	    		  return {
	    		    constructor : objFactory
	    		  };
	    		};
	    	$.post("/project/selecttag", 
	    		{
	    		id : 1
	    		}
	    	  ,
	    	  jsonController.cityRequest(button),
	    	  "json");
	    	
	    	
	    	//jsonController.cityRequest(button);
	    };
	    this.rangeChange = function(){    	    	
	    	$.post("/project/rangechange", 
	    			{minX : map.getBounds().getSouthWest().lng,
    	    		maxX : map.getBounds().getNorthEast().lng,
    	    		minY : map.getBounds().getSouthWest().lat,
    	    		maxY : map.getBounds().getNorthEast().lat},
    	    	  this.cityRequest(),
    	    	  "json");
	    };
	    this.tagChange = function(tagId){
	    	$.post("/project/tagchange", 
	    			{id : tagId},
    	    	  null,
    	    	  "json");
	    };
	    this.mounthChange = function(monthName){
	    	//monthName == JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;
			$.post("/project/monthchange", 
					{month : monthName},
		    	  null,
		    	  "json");
	    };
	    this.sliderChange = function(idSlider, leftValueSlider, rightValueSlider){
	    	$.post("/project/sliderchange", 
	    			{id : idSlider,
	    			leftValue : leftValueSlider,
	    			rightValue : rightValueSlider},
    	    	  null,
    	    	  "json");
	    };
	    this.cityRequest = function(input){
	    	$.getJSON("/project/getcities",
	            function(data) {
	    			redraw(data, 0);
	                // do something with the data
	                //myDataArray = data;
	          });
	    };
	};
    
}



