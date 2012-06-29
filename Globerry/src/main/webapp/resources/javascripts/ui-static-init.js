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
            },"slow");
            $("#whiteBottom").animate({
                height:147
            },"slow", function () {
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
                },"slow");
                $(("#" +prevBotBut.id + "B")).hide();
                $("#WhiteBottom").animate({
                    height:0
                },"slow");
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
});
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
    /*обработка кнопки "Поехали"*/
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
            $("#headerButtonUp").show();
            $("#UpperHeaderBlockWithCircle").show();
            $("#UpperHeaderBlockWithCircle").animate({
                opacity:1
            }, "slow");
            $("#headerButtonUp").animate({
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
            $("#headerButtonUp").animate({
                opacity:0
            }, "slow");
            $("#UpperHeaderBlockWithCircle").animate({
                opacity:0
            }, "slow");
            $("#headContent2").animate({
                height:0
            },"slow", function() {
                $("#headerButtonUp").hide();
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