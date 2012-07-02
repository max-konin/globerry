/*******************************************************************************/
//всплывающая панель внизу
// switch the bottom on and off, and changes the state of the buttom.
$(document).ready(function() {
    var prevBotBut; //bottom buttoms
    var bottomActive = false;
    $(".bottomButton").click(function(){
        //animation for switching bottoms.
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
            },100, function () {
                $(("#" +prevBotBut.id + "B")).show();
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
                $(("#" +prevBotBut.id + "B")).show();
            }
        }
    });
    $('#map').click(function(){
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
    });
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
        WhoStr[2] = "Мы с семьей едем ";
        WhoStr[3] = "Мы вдвоем едем ";

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
            $("#headerButtonUp").show();
            $("#UpperHeaderBlockWithCircle").show();
            $("#UpperHeaderBlockWithCircle").animate({
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
            $("#UpperHeaderBlockWithCircle").animate({
                opacity:0
            }, speed);
            $("#headContent2").animate({
                height:0
            },speed, function() {
                $("#headerButtonUp").hide();
                $("#UpperHeaderBlockWithCircle").hide();
                $("#headContent2").hide();
                $("#headContent1").show();
                $("#headContent1").animate({
                    height:38
                }, speed);
            });
        });
    });

 
});