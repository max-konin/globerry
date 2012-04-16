window.onload = function () {
   /* $("div").click(function (event) {
        event = event || window.event;
        var t = event.target || event.srcElement
        alert(t.id)
    }); */
    var headerflag = 0;
	var prevBotBut;
	var bottomActive = false;
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
				//$("bottomButton").style.background = 'rgb(37, 46, 64)';
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
		selectWhensInMonthes = document.getElementById('WhenS');
		//selectWhensInMonthes.options[$("#WhenS").val()].selected=true;
		for(i in selectWhensInMonthes.options){
			//alert("i: "+i + " op[i]: " + selectWhensInMonthes.options[i]);
		}
    });
	
	$("#WhenS").change(function () {
		$("select option:selected").each(function () {
				str="";
				if(this.id == "janO") str="jan";
				if(this.id == "febO") str="feb";
				if(this.id == "marO") str="mar";
				if(this.id == "aprO") str="apr";
				if(this.id == "mayO") str="may";
				if(this.id == "junO") str="jun";
				if(this.id == "julO") str="jul";
				if(this.id == "sepO") str="sep";
				if(this.id == "octO") str="oct";
				if(this.id == "novO") str="nov";
				if(this.id == "decO") str="dec";
				if(str != ""){
					prev.style.background = 'rgb(37, 46, 64)';
					prev = document.getElementById(str);
					prev.style.background = 'rgb(236, 72, 7)';
				}
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

}
