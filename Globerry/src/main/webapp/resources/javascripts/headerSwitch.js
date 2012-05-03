function headerChange()
{
	
    var JSONContr = new JsonController(); 
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

	
	var prevBotBut;
	var bottomActive = false;
	
	$(".headerButton").click(function (event) {
		configureText();
		$(prevList).animate({height:0}, "fast");
		SelectActive = false;
		$("#headContent1").animate({height:0},"slow", function() {
			$("#headContent1").hide();
			$("#headContent2").show();
			$("#headText").show();
			$("#bottomToggle").slideToggle("slow");
			$("#bottom").show();
			$("#headContent2").animate({height:100}, "slow", function (){
				$("#calendar").show();
				$("#calendar").animate({height:26}, "normal");
			});
			$("#HeaderButtonUp").show();
			$("#UpperHeaderBlockWithCircle").show();
			$("#UpperHeaderBlockWithCircle").animate({opacity:1}, "slow");
			$("#HeaderButtonUp").animate({opacity:1}, "slow");			
		});
	});	
	$(".headerUpSwitcher").click(function (event) {
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
				prevList = "#" + this.id + "BG";
				a = $(prevList).css("max-height");
				$(prevList).animate({height:a}, "slow");
				SelectActive = true;
			} else
			{
				if(prevList != "#" + this.id + "BG"){
					$(prevList).animate({height:0}, "slow");
					prevList = "#" + this.id + "BG";
					a = $(prevList).css("max-height");
					$(prevList).animate({height:a}, "slow");
				} else {
					$(prevList).animate({height:0}, "slow");
					SelectActiveNumber = 0;
					SelectActive = false;
				}
			}
	}); 
	prevSelect = new Array();
	prevSelect[1] = "#aloneSelected";
	prevSelect[2] = "#tanSelected";
	prevSelect[3] = "#janSelected";
	TextSelectorWho = 1;
	TextSelectorWhat = 1;
	TextSelectorWhen = 1;
	$(".selectItem").click(function (event) { 
	if(SelectActiveNumber != 0){
			$(prevSelect[SelectActiveNumber]).hide();
			$("#" + this.id + "Selected").show();
			prevSelect[SelectActiveNumber] = "#" + this.id + "Selected";
			$(prevList).animate({height:0}, "slow");
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
	});

		$(".bottomButton").click(function(){
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
	};
	
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
	    			rightValue : rightValue},
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