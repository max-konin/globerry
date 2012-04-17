window.onload = function () {
   /* $("div").click(function (event) {
        event = event || window.event;
        var t = event.target || event.srcElement
        alert(t.id)
    }); */
    var headerflag = 0;
	var prevBotBut;
	var bottomActive = false;
	
    var JSONContr = new JsonController(); 
	
    var prev = document.getElementById('jan');
    $("#SliderRequest").hide();
    $("#upperToggle").hide();
    $("#bottomToggle").hide();

    $("#buttonHeadSwitcher").click(function (event) {
        if ((headerflag % 2) == 0) {
            $("#head").slideToggle("slow", function () {
                $("#SliderRequest").slideToggle("slow");
                $("#upperToggle").slideToggle("slow");
                $("#bottomToggle").slideToggle("slow");
				$("#bottom").show();
				//alert($(document.getElementById("WhenS")).val);
				//alert($('select.#WhenS option:selected').option);
				//alert($("#WhenS").val());
				//jQuery("input#HText").val("Едем " + $("#WhoS").val() + " " + $("#WhatS").val() + " в " + $("#WhenS").val() + "!");
				jQuery("input#HText").val("Едем " + $("#WhoS").val() + " " + $("#WhatS").val() + " в " + $("#WhenS").val() + "!");
				//prev.style.background = 'rgb(37, 46, 64)';
				//$()
				//alert($("#WhenS").options[$("#WhenS").val()].id);
            });
		}
        else {
				$("#SliderRequest").slideToggle("slow", function () { $("#head").slideToggle("slow") });
				jQuery("input#HText").val("");
				//$("#bottom").hide();
				//$("#WhiteBottom").animate({height:0},"slow");
			}

        headerflag++;

    });
    $("#bottomToggle").click(function (event) {
        if ((headerflag % 2) == 0) {
				$("#head").slideToggle("slow", function () { $("#SliderRequest").slideToggle("slow") });
				$("#bottom").show();
			}
        else {
			jQuery("input#HText").val("");
            $("#bottomToggle").slideToggle("slow");
            $("#upperToggle").slideToggle("slow");
            $("#SliderRequest").slideToggle("slow", function () { $("#head").slideToggle("slow") });
			$("#bottom").animate({bottom:0},"slow");
			if(bottomActive == true) {
				//alert(prevBotBut.id);
				$(("#" +prevBotBut.id + "B")).hide();
				//$(prevBotBut).style.background = 'rgb(37, 46, 64)';				
			}
			$("#WhiteBottom").animate({height:0},"slow");
			$("#bottom").hide();
			bottomActive = false;
        }
        headerflag++;
    });
    

    $(".calMonth").click(function (event) {
        prev.style.background = 'rgb(37, 46, 64)';
        prev = this;
        this.style.background = 'rgb(236, 72, 7)';
		//alert($("#" + this.id+"O").val());
		jQuery("select#WhenS").val($("#" + this.id+"O").val()).attr("selected", "selected");
		//$("select#WhenS option:selected").val($("#WhenS").val());//.attr("selected", "selected");
		//$('select#WhenS').val($("#WhenS").val()).change();
		jQuery("input#HText").val("Едем " + $("#WhoS").val() + " " + $("#WhatS").val() + " в " + $("#WhenS").val() + "!");
		monthName = "";
		if(this.id == "jan") {
			monthName = "JANUARY";
		}
		if(this.id == "febO"){
			monthName = "FEBRUARY";
		}
		if(this.id == "mar"){
			monthName = "MARCH";
		}
		if(this.id == "apr"){
			monthName = "APRIL";
		}
		if(this.id == "may"){
			monthName = "MAY";
		}
		if(this.id == "jun"){
			monthName = "JUNE";
		}
		if(this.id == "jul"){
			monthName = "JULY";
		}
		if(this.id == "aug"){
			monthName = "AUGUST";
		}
		if(this.id == "sep"){
			monthName = "SEPTEMBER";
		}				
		if(this.id == "oct"){
			monthName = "OCTOBER";
		}
		if(this.id == "nov"){
			monthName = "NOVEMBER";
		}
		if(this.id == "dec"){
			monthName = "DECEMBER";
		}
		JSONContr.mounthChange(monthName);
    });
	
	$("#WhenS").change(function () {
		$("select option:selected").each(function () {
				str="";
				monthNameInWhenS="";
				if(this.id == "janO") {
					str="jan";
					monthNameInWhenS = "JANUARY";
				}
				if(this.id == "febO"){
					str="feb";
					monthNameInWhenS = "FEBRUARY";
				}
				if(this.id == "marO"){
					str="mar";
					monthNameInWhenS = "MARCH";
				}
				if(this.id == "aprO"){
					str="apr";
					monthNameInWhenS = "APRIL";
				}
				if(this.id == "mayO"){
					str="may";
					monthNameInWhenS = "MAY";
				}
				if(this.id == "junO"){
					str="jun";
					monthNameInWhenS = "JUNE";
				}
				if(this.id == "julO"){
					str="jul";
					monthNameInWhenS = "JULY";
				}
				if(this.id == "augO"){
					str="aug";
					monthNameInWhenS = "AUGUST";
				}
				if(this.id == "sepO"){
					str="sep";
					monthNameInWhenS = "SEPTEMBER";
				}				
				if(this.id == "octO"){
					str="oct";
					monthNameInWhenS = "OCTOBER";
				}
				if(this.id == "novO"){
					str="nov";
					monthNameInWhenS = "NOVEMBER";
				}
				if(this.id == "decO"){
					str="dec";
					monthNameInWhenS = "DECEMBER";
				}
				if(str != ""){
					JSONContr.mounthChange(monthNameInWhenS);
					prev.style.background = 'rgb(37, 46, 64)';
					prev = document.getElementById(str);
					prev.style.background = 'rgb(236, 72, 7)';					
				}
              });
	});
	$("#WhoS").change(function () {
		$("select option:selected").each(function () {
				whoNameInWhoS = -1;
				if(this.id == "friendsO") whoNameInWhoS = 1;
				if(this.id == "familyO") whoNameInWhoS = 2;
				if(this.id == "aloneO") whoNameInWhoS = 3;
				if(this.id == "coupleO") whoNameInWhoS = 4;
				if(whoNameInWhoS != -1)
					JSONContr.tagChange(whoNameInWhoS);
		});
	});
	
	$("#WhatS").change(function () {
		$("select option:selected").each(function () {
				whatNameInWhatS = -1;
				if(this.id == "tanO") whatNameInWhatS = 5;
				if(this.id == "skiO") whatNameInWhatS = 6;
				if(this.id == "watchO") whatNameInWhatS = 7;
				if(this.id == "shopO") whatNameInWhatS = 8;
				if(this.id == "cruiseO") whatNameInWhatS = 9;
				if(whatNameInWhatS != -1)
					JSONContr.tagChange(whatNameInWhatS);
		});
	});
	
	$(".bottomButton").click(function(){
		if( bottomActive == false){
			if(prevBotBut != undefined){
				prevBotBut.style.background = 'rgb(37, 46, 64)';
			}
			prevBotBut = this;
			$("#bottom").animate({bottom:300},"slow");
			$("#WhiteBottom").animate({height:300},"slow", function () { $(("#" +prevBotBut.id + "B")).show(); });			
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
	});/**/
	$(".bottomButton").click(function(){
		//alert(this.id);
	});
	$(window).resize(function (e) { 
		/*alert("123");*/
	});
	
    
    init();
    
    
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
	function containerPointToLatLng(point){
		return map.layerPointToLatLng((map.containerPointToLayerPoint(point)));
	};

    

}
