$(document).ready(function(){
	$(".headerButton").click(function (event) {
		SelectActive = false;
		$("#headContent1").animate({
			height:0
		},"slow", function() {
			$("#headContent1").hide();
			$("#headContent2").show();
			$("#headText").show();
			$("#bottomToggle").slideToggle("slow");
			$("#bottom").show();
			$("#HeaderButtonUp").show();
			$("#UpperHeaderBlockWithCircle").show();
			$("#UpperHeaderBlockWithCircle").animate({
				opacity:1
			}, "slow");
			$("#HeaderButtonUp").animate({
				opacity:1
			}, "slow");
			$("#headContent2").animate({
				height:100
			}, "slow", function (){
				$("#calendar").show();
				$("#calendar").animate({
					height:26
				}, "slow", function(){				
					//here I wanted to fix bug with ctrange appearance of the calendar.
					});
			});
		});
	});	
	// as previous function this hides the second header and shows the first, with aimation.
	$(".headerUpSwitcher").click(function (event) {
		//animation.
		$("#headText").hide();
		$("#calendar").animate({
			height:0
		}, "normal", function (){
			$("#calendar").hide();
			$("#HeaderButtonUp").animate({
				opacity:0
			}, "slow");
			$("#UpperHeaderBlockWithCircle").animate({
				opacity:0
			}, "slow");
			$("#headContent2").animate({
				height:0
			},"slow", function() {
				$("#HeaderButtonUp").hide();
				$("#UpperHeaderBlockWithCircle").hide();
				$("#headContent2").hide();
				$("#headContent1").show();
				$("#headContent1").animate({
					height:38
				}, "slow");
			});
		});
	});
});
