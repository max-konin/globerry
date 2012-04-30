jQuery(document).ready(function () {

    function mySlider(leftLimit, rightLimit, measure, div, leftInput, rightInput, pos) {
		var leftMeasure = "";
		var rightMeasure = "";
		if (pos == "l")
			leftMeasure = measure;
		if(pos == "r")
			rightMeasure = measure;
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
    firstSL = new mySlider(0, 24, " ×", "timeSlider", "TimeMinV", "TimeMaxV", "r");
    $("#MoodSlider").slider();
    firstSL = new mySlider(0, 300, "$", "LivSlider", "LivMinV", "LivMaxV", "l");
    $("#securitySlider").slider();
    firstSL = new mySlider(0, 100, "$", "foodSlider", "FoodMinV", "FoodMaxV", "l");
    $("#SexSlider").slider();
    $("#WhoS").selectbox().fadeOut(1000);
    $("#WhatS").selectbox().fadeOut(1000);
    $("#WhenS").selectbox().fadeOut(5000);
});



