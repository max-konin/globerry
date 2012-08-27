/*******************************************************************************/
//всплывающая панель внизу
// switch the bottom on and off, and changes the state of the buttom.
 var bottomActive = false;
$(document).ready(function() {
	
	$(document).ready(function(){ $("#aviaScrollBar").tinyscrollbar();
								  $("#tourScrollBar").tinyscrollbar();
								  $("#hotelScrollBar").tinyscrollbar();});

    var prevBotBut; //bottom buttoms
   
    $(".bottomButton").click(function(){
        if( bottomActive == false){
            if(prevBotBut != undefined){
                prevBotBut.style.background = 'rgb(37, 46, 64)';
            }
            prevBotBut = this;
            $("#bottom").animate({
                bottom:147
            },100);
            $("#whiteBottom").animate({
                height:147
            },100, function () 
            {
                $(("#" +prevBotBut.id + "B")).show("normal",function(){
                	$.ajax({
                        url: path +  '/get_'+prevBotBut.id+'s',
                        dataType: 'json',
                        type: 'POST',
                        success: function (response) 
                        {
                            var bubbles = {};
                            $("#" +prevBotBut.id + "ScrollBar .viewport .overview").empty(); 
                            //for(var i = 0; i < response.lenght; i++)
                            for(key in response)
                            {
                            	if(key == "copy" || key == "getLast" || key == "remove") continue;
                            	switch(prevBotBut.id)
                                {
                                case "tour":
                                	var bubble = {
                                        name : response[key]['name'],
                                        cityName: response[key]['cityName'],
                                        cost: response[key]['cost'],
                                        description : response[key]['description']};
                                	break
                                case "avia":
                                	var bubble = {
                                        name : response[key]['name'],
                                        cost: response[key]['cost'],
                                        cityName: response[key]['cityName']};
                                	break
                                case "hotel":
                                	var bubble = {
                                        name : response[key]['name'],
                                        cityName: response[key]['cityName'],
                                        cost: response[key]['cost'],
                                        description : response[key]['description']};
                                	break
                                }
                                
                                bubbles[key] = bubble;
                                var image = "";
                                var href = "";
                                switch(prevBotBut.id)
                                {
                                case "tour":
                                	image = "resources/img/pegas.png";
                                	href = "http://pegast.ru/";
                                	$("#" +prevBotBut.id + "ScrollBar .viewport .overview").append('<div class="Line" id="tour' + key
                                    		+ '"><div class="bottom_entity"><table><tr><td width="300px"><div class="bottom_entity_name">' +bubble['name'] + 
                                    		  '</div><div class="bottom_entity_description">' + bubble['description'] + '</div></td><td class="bottom_entity_td"><div class="hotelCost">'+bubble['cost']+'$</div></td>'+
                                    		'<td><div class="bottom_entity_ref"><a target="_blank" href="'+href+'" ><img src="'+image+'"/></a></div></td>'+'</tr></table></div></div>');
                                	break
                                case "avia":
                                	image = "http://www.onetwotrip.com/images/logo_u0341.png";
                                	href = "http://www.onetwotrip.com/";
                                	$("#" +prevBotBut.id + "ScrollBar .viewport .overview").append('<div class="Line" id="avia' + key
                                    		+ '"><div class="bottom_entity"><table><tr><td width="300px"><div class="bottom_entity_name">' +bubble['name'] + 
                                    		  '</div><div class="bottom_entity_description">' + bubble['description'] + '</div></td><td class="bottom_entity_td"><div class="hotelCost">'+bubble['cost']+'$</div></td>'+
                                    		'<td><div class="bottom_entity_ref"><a target="_blank" href="'+href+'" ><img src="'+image+'"/></a></div></td>'+'</tr></table></div></div>');

                                	break
                                case "hotel":
                                	image = "resources/img/booking.com.png";
                                	href = "http://www.booking.com/";
                                	$("#" +prevBotBut.id + "ScrollBar .viewport .overview").append('<div class="Line" id="hotel' + key
                                    		+ '"><div class="bottom_entity"><table><tr><td width="300px"><div class="bottom_entity_name">' +bubble['cityName'] + " - "+bubble['name'] + 
                                    		  '</div><div class="bottom_entity_description">' + bubble['description'] + '</div></td><td class="bottom_entity_td"><div class="hotelCost">'+bubble['cost']+'$</div></td>'+
                                    		'<td><div class="bottom_entity_ref"><a target="_blank" href="'+href+'" ><img src="'+image+'"/></a></div></td>'+'</tr></table></div></div>');
                                	break
                                }
                               
                                }
                            	$("#" +prevBotBut.id + "ScrollBar").tinyscrollbar_update();
                        },
                        error: function(response) {
                            
                        }
                	});
                	}        
                	);
                	
            });			
            this.style.background = 'rgb(255, 122, 2)';
            $(this).animate({
                width:124
            }, 0);
            
            bottomActive = true;
            prevBotBut = this;
        } else
        if(bottomActive == true){
            if(prevBotBut == this){
                $("#bottom").animate({
                    bottom:0
                },100);
                $(("#" +prevBotBut.id + "B")).hide();
                $("#whiteBottom").animate({
                    height:0
                },100);
                this.style.background = 'rgb(37, 46, 64)';
                $(this).animate({
                    width:120
                }, 0);
                bottomActive = false;
            } else {
                this.style.background = 'rgb(255, 122, 2)';
                $(("#" +prevBotBut.id + "B")).hide();
                $(this).animate({
                    width:124
                }, 0);
                prevBotBut.style.background = 'rgb(37, 46, 64)';
                $(prevBotBut).animate({
                    width:120
                }, 0);
                prevBotBut = this;
                $(("#" +prevBotBut.id + "B")).show("normal",function(){
                	
                	$.ajax({
                        url: path +  '/get_'+prevBotBut.id+'s',
                        dataType: 'json',
                        type: 'POST',
                        success: function (response) 
                        {
                            var bubbles = {};
                            $("#" +prevBotBut.id + "ScrollBar .viewport .overview").empty(); 
                            for(key in response)
                            {
                            	if(key == "copy" || key == "getLast" || key == "remove") continue;
                            	switch(prevBotBut.id)
                                {
                                case "tour":
                                	var bubble = {
                                        name : response[key]['name'],
                                        cityName: response[key]['cityName'],
                                        cost: response[key]['cost'],
                                        description : response[key]['description']};
                                	break
                                case "avia":
                                	var bubble = {
                                        name : response[key]['name'],
                                        cost: response[key]['cost'],
                                        cityName: response[key]['cityName']};
                                	break
                                case "hotel":
                                	var bubble = {
                                        name : response[key]['name'],
                                        cityName: response[key]['cityName'],
                                        cost: response[key]['cost'],
                                        description : response[key]['description']};
                                	break
                                }
                                
                                bubbles[key] = bubble;
                                var image = "";
                                var href = "";
                                switch(prevBotBut.id)
                                {
                                case "tour":
                                	image = "resources/img/pegas.png";
                                	href = "http://pegast.ru/";
                                	$("#" +prevBotBut.id + "ScrollBar .viewport .overview").append('<div class="Line" id="tour' + key
                                    		+ '"><div class="bottom_entity"><table><tr><td width="300px"><div class="bottom_entity_name">' +bubble['name'] + 
                                    		  '</div><div class="bottom_entity_description">' + bubble['description'] + '</div></td><td class="bottom_entity_td"><div class="hotelCost">'+bubble['cost']+'$</div></td>'+
                                    		'<td><div class="bottom_entity_ref"><a target="_blank" href="'+href+'" ><img src="'+image+'"/></a></div></td>'+'</tr></table></div></div>');
                                	break
                                case "avia":
                                	image = "http://www.onetwotrip.com/images/logo_u0341.png";
                                	href = "http://www.onetwotrip.com/";
                                	$("#" +prevBotBut.id + "ScrollBar .viewport .overview").append('<div class="Line" id="avia' + key
                                    		+ '"><div class="bottom_entity"><table><tr><td width="300px"><div class="bottom_entity_name">' +bubble['name'] + 
                                    		  '</div><div class="bottom_entity_description">' + bubble['description'] + '</div></td><td class="bottom_entity_td"><div class="hotelCost">'+bubble['cost']+'$</div></td>'+
                                    		'<td><div class="bottom_entity_ref"><a target="_blank" href="'+href+'" ><img src="'+image+'"/></a></div></td>'+'</tr></table></div></div>');

                                	break
                                case "hotel":
                                	image = "resources/img/booking.com.png";
                                	href = "http://www.booking.com/";
                                	$("#" +prevBotBut.id + "ScrollBar .viewport .overview").append('<div class="Line" id="hotel' + key
                                    		+ '"><div class="bottom_entity"><table><tr><td width="300px"><div class="bottom_entity_name">' +bubble['cityName'] + " - "+bubble['name'] + 
                                    		  '</div><div class="bottom_entity_description">' + bubble['description'] + '</div></td><td class="bottom_entity_td"><div class="hotelCost">'+bubble['cost']+'$</div></td>'+
                                    		'<td><div class="bottom_entity_ref"><a target="_blank" href="'+href+'" ><img src="'+image+'"/></a></div></td>'+'</tr></table></div></div>');
                                	break
                                }
                               
                                }
                            	$("#" +prevBotBut.id + "ScrollBar").tinyscrollbar_update();
                        },
                        error: function(response) {
                            
                        }
                	});
                	});
            }
        }
    });
    //нижняя панель (если открыта) скрывается при нажатии по карте
    function closeBottom()
    {}
    
    /*$('#map').mousedown(function(){
    if(bottomActive == true){
                $("#bottom").animate({
                    bottom:0
                },100);
                $(("#" +prevBotBut.id + "B")).hide();
                $("#whiteBottom").animate({
                    height:0
                },100);
                bottomActive = false;
            } 
    });*/
});

//$('#bottomContainer').click(function(){alert('click')})

$(document).ready(function(){
    
    /*******************************************************************************/
    //создаем комбобоксы
    $(".whoSelect").kendoComboBox({});
    $(".whatSelect").kendoComboBox();
    $(".whenSelect").kendoComboBox({
        height: 1000
    });
    /*******************************************************************************/
    /*******************************************************************************/
    //все combobox'ы readonly
    $('.whoWhatWhenDropDownList .k-input').attr('readonly','readonly');
});
$(document).ready(function(){
    $(".changeHeaderByClick").click(function() {
        WhoStr = new Array();
        WhoStr[1] = "Я один еду ";
        WhoStr[2] = "Мы с друзьями едем ";
        WhoStr[3] = "Мы с семьей едем ";
        WhoStr[4] = "Мы вдвоем едем ";

        WhatStr = new Array();
        WhatStr[5] = "загорать ";
        WhatStr[6] = "кататься ";
        WhatStr[7] = "смотреть достопримечательности ";
        WhatStr[8] = "за покупками ";
        WhatStr[9] = "в круиз ";

        WhenStr = new Array();
        WhenStr[0] = "в январе!";
        WhenStr[1] = "в феврале!";
        WhenStr[2] = "в марте!";
        WhenStr[3] = "в апреле!";
        WhenStr[4] = "в мае!";
        WhenStr[5] = "в июне!";
        WhenStr[6] = "в июле!";
        WhenStr[7] = "в августе!";
        WhenStr[8] = "в сентябре!";
        WhenStr[9] = "в октябре!";
        WhenStr[10] = "в ноябре!";
        WhenStr[11] = "в декабре!";
		
        /*получаем значения select'а из комбобокса*/
        var who = $(".whoSelect option:selected").val();
        var what = $(".whatSelect option:selected").val();
        var tmp = $('.activeMonth').attr('value');
        var when = parseInt(tmp)//$(".whenSelect option:selected").val() || tmp;
        
//        var guiid = $('select.whenSelect').attr('guiid');
		
        /*выбранный месяц сделать активным*/
        /*var activeMonthId = $(".whenSelect option:selected").val();
        if ($('.activeMonth').text().length > 0)//месяц в календаре не выбран
        {
            when = $('.activeMonth').text();
        }*/
		
        var resultHeader = WhoStr[who] + WhatStr[what] + WhenStr[when];
        $('#hText').val(resultHeader);
        /*выбранный месяц вписывать в заголовок*/
        $('.activeMonth').val(when);

    });
});
$(document).ready(function(){
    /*******************************************************************************/
    var speed = "normal"
    /*обработка кнопки "Поехали"*/
    $(".headerButton").click(function (event) {
        
        SelectActive = false;
        $("#headContent1").animate({
            height:0
        },speed, function() {
            $("#headContent1").hide();
            $("#headContent2").show();
            $("#headText").show();
            $("#bottomToggle").slideToggle(speed);
            $("#bottom").show();
            //отображаем кнопки внизу
            $("#bottomContainer").show();
            $("#headerButtonUp").show();
            $("#upperHeaderBlockWithCircle").show();
            $("#upperHeaderBlockWithCircle").animate({
                opacity:1
            }, 100);
            $("#headerButtonUp").animate({
                opacity:1
            }, speed);
            $("#headContent2").animate({
                height:100
            }, speed, function (){
                $("#calendar").show();
                $("#calendar").animate({
                    height:26
                }, speed, function(){				
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
        }, speed, function (){
            $("#calendar").hide();
            $("#headerButtonUp").animate({
                opacity:0
            }, speed);
            $("#upperHeaderBlockWithCircle").animate({
                opacity:0
            }, speed);
            $("#headContent2").animate({
                height:0
            },speed, function() {
                //скрываем кнопки внизу
                $("#bottomContainer").hide();
                $("#headerButtonUp").hide();
                $("#upperHeaderBlockWithCircle").hide();
                $("#headContent2").hide();
                $("#headContent1").show();
                $("#headContent1").animate({
                    height:38
                }, speed);
            });
        });
    });

 
});
