jQuery(document).ready(function () {

    function mySlider(leftLimit, rightLimit, measure, div, leftInput, rightInput, pos) {
        jQuery("#" + div).slider({
            min: leftLimit,
            max: rightLimit,
            values: [leftLimit, rightLimit],
            range: true,
            stop: function (event, ui) {

                jQuery("input#" + leftInput).val(jQuery("#" + div).slider("values", 0));
                jQuery("input#" + rightInput).val(jQuery("#" + div).slider("values", 1));
                //Important! values of the inputs, will change only after the previous two lines.
            },
            slide: function (event, ui) {

                jQuery("input#" + leftInput).val(jQuery("#" + div).slider("values", 0));
                jQuery("input#" + rightInput).val(jQuery("#" + div).slider("values", 1));
            }
        });
    };

    

    firstSL = new mySlider(-35, 35, 1, "tempSlider", "minCost", "maxCost", "l");
    firstSL = new mySlider(0, 30, 1, "alchSlider", "alcMinCost", "alcMaxCost", "l");
    firstSL = new mySlider(0, 24, 1, "timeSlider", "TimeMinV", "TimeMaxV", "l");
    $("#MoodSlider").slider();
    firstSL = new mySlider(0, 300, 1, "LivSlider", "LivMinV", "LivMaxV", "l");
    $("#securitySlider").slider();
    firstSL = new mySlider(0, 100, 1, "foodSlider", "FoodMinV", "FoodMaxV", "l");
    $("#SexSlider").slider();
    alert('sliders have to be here');
});



