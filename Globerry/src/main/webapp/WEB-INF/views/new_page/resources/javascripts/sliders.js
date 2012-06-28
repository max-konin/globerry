/*слайдер температуры*/
$(document).ready(function(){
	$("#temperature-slider").slider({
		min: -35,
		max: 35,
		values: [-35,35],
		range: true,
		stop: function(event, ui) {
			jQuery("input#minCost").val(jQuery("#temperature-slider").slider("values",0));
			jQuery("input#maxCost").val(jQuery("#temperature-slider").slider("values",1));
		},
		slide: function(event, ui){
			jQuery("input#minCost").val(jQuery("#temperature-slider").slider("values",0));
			jQuery("input#maxCost").val(jQuery("#temperature-slider").slider("values",1));
		}
	});
});
/*слайдер время в пути*/
$(document).ready(function(){
	$("#time-slider").slider({
		min: 0,
		max: 24,
		values: [0,24],
		range: true,
		stop: function(event, ui) {
			jQuery("input#TimeMinV").val(jQuery("#time-slider").slider("values",0));
			jQuery("input#TimeMaxV").val(jQuery("#time-slider").slider("values",1));
			
		},
		slide: function(event, ui){
			jQuery("input#TimeMinV").val(jQuery("#time-slider").slider("values",0));
			jQuery("input#TimeMaxV").val(jQuery("#time-slider").slider("values",1));
		}
	});
});

/*слайдер проживание*/
$(document).ready(function(){
	$("#livecost-slider").slider({
		min: 0,
		max: 300,
		values: [0,300],
		range: true,
		stop: function(event, ui) {
			jQuery("input#LivMinV").val(jQuery("#livecost-slider").slider("values",0));
			jQuery("input#LivMaxV").val(jQuery("#livecost-slider").slider("values",1));
			
		},
		slide: function(event, ui){
			jQuery("input#LivMinV").val(jQuery("#livecost-slider").slider("values",0));
			jQuery("input#LivMaxV").val(jQuery("#livecost-slider").slider("values",1));
		}
	});
});
/*слайдер еда*/
$(document).ready(function(){
	$("#food-slider").slider({
		min: 0,
		max: 100,
		values: [0,100],
		range: true,
		stop: function(event, ui) {
			jQuery("input#FoodMinV").val(jQuery("#food-slider").slider("values",0));
			jQuery("input#FoodMaxV").val(jQuery("#food-slider").slider("values",1));
			
		},
		slide: function(event, ui){
			jQuery("input#FoodMinV").val(jQuery("#food-slider").slider("values",0));
			jQuery("input#FoodMaxV").val(jQuery("#food-slider").slider("values",1));
		}
	});
});
/*слайдер алкоголь*/
$(document).ready(function(){
	$("#alc-slider").slider({
		min: 0,
		max: 30,
		values: [0,30],
		range: true,
		stop: function(event, ui) {
			jQuery("input#alcMinCost").val(jQuery("#alc-slider").slider("values",0));
			jQuery("input#alcMaxCost").val(jQuery("#alc-slider").slider("values",1));
			
		},
		slide: function(event, ui){
			jQuery("input#alcMinCost").val(jQuery("#alc-slider").slider("values",0));
			jQuery("input#alcMaxCost").val(jQuery("#alc-slider").slider("values",1));
		}
	});
});

/*слайдер настроение*/
$(document).ready(function(){
	$("#mood-slider").slider({
		min: 0,
		max: 100,
		values: [0,100],
		range: true,
		stop: function(event, ui) {
			jQuery("input#moodMin").val(jQuery("#mood-slider").slider("values",0));
			jQuery("input#moodMax").val(jQuery("#mood-slider").slider("values",1));
			
		},
		slide: function(event, ui){
			jQuery("input#moodMin").val(jQuery("#mood-slider").slider("values",0));
			jQuery("input#moodMax").val(jQuery("#mood-slider").slider("values",1));
		}
	});
});

/*слайдер безопасность*/
$(document).ready(function(){
	$("#security-slider").slider({
		min: 0,
		max: 100,
		values: [0,100],
		range: true,
		stop: function(event, ui) {
			jQuery("input#securityMin").val(jQuery("#security-slider").slider("values",0));
			jQuery("input#securityMax").val(jQuery("#security-slider").slider("values",1));
			
		},
		slide: function(event, ui){
			jQuery("input#securityMin").val(jQuery("#security-slider").slider("values",0));
			jQuery("input#securityMax").val(jQuery("#security-slider").slider("values",1));
		}
	});
});

/*слайдер секс*/
$(document).ready(function(){
	$("#sex-slider").slider({
		min: 0,
		max: 100,
		values: [0,100],
		range: true,
		stop: function(event, ui) {
			jQuery("input#sexMin").val(jQuery("#sex-slider").slider("values",0));
			jQuery("input#sexMax").val(jQuery("#sex-slider").slider("values",1));
			
		},
		slide: function(event, ui){
			jQuery("input#sexMin").val(jQuery("#sex-slider").slider("values",0));
			jQuery("input#sexMax").val(jQuery("#sex-slider").slider("values",1));
		}
	});
});