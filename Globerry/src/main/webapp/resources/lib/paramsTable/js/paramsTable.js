//панель параметров
$(document).ready(function() {
    $(function(){
        $('.panel').tabSlideOut({							//Класс панели
            tabHandle: '.handle',						//Класс кнопки
            pathToTabImage: '../images/button.gif',				//Путь к изображению кнопки (почему-то не работает)
            imageHeight: '122px',						//Высота кнопки
            imageWidth: '40px',                                                 //Ширина кнопки
            tabLocation: 'right',						//Расположение панели top - выдвигается сверху, right - выдвигается справа, bottom - выдвигается снизу, left - выдвигается слева
            speed: 100,								//Скорость анимации
            action: 'click',                                                    //Метод показа click - выдвигается по клику на кнопку, hover - выдвигается при наведении курсора
            topPos: '200px',							//Отступ сверху
            fixedPosition: false						//Позиционирование блока false - position: absolute, true - position: fixed
        });
    });
    $('a.handle').css('background-color','black');
    
    //обработка слайдеров
    $(function() {
        var inputClass = ".cur-amount";
        var sliderDivClass = '.mySliders';
        var sliderClass = '.paramsTableSlider';
        $('.mySliders').each(function(){
            var thisSlider = $(this);
            var min = thisSlider.find('.hiddenInputsDiv > input.min-amount').val();
            var max = thisSlider.find('.hiddenInputsDiv > input.max-amount').val();
            console.log(min);
            $( $(this).find(sliderClass) ).slider({
                range: "min",
                value: 50,
                min: 0,
                max: 100,
                slide: function( event, ui ) {
                    thisSlider.find('input.cur-amount').val( ui.value );
                }
            });
            var sliderValue = $(  $(this).find('div.paramsTableSlider') ).slider( "value" );
            $(this).find('input.cur-amount').val( sliderValue); 
        });
    });  

    function rgb2hex(rgb) {
        if (  rgb.search("rgb") == -1 ) {
            return rgb;
        } else {
            rgb = rgb.match(/^rgba?\((\d+),\s*(\d+),\s*(\d+)(?:,\s*(\d+))?\)$/);
            function hex(x) {
                return ("0" + parseInt(x).toString(16)).slice(-2);
            }
            return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]); 
        }
    }

    //ColorPicker встраиваемый
    var colorSelectorClass = '.colorSelector';
    $(colorSelectorClass).each(function(){
        var thisDiv = $(this).find('div');
        //поулчаем заданный цвет
        var curColor = rgb2hex($(this).find('div').css('backgroundColor'));
        $(this).ColorPicker({
            color:  curColor,
            onShow: function (colpkr) {
                $(colpkr).fadeIn(100);
                return false;
            },
            onHide: function (colpkr) {
                $(colpkr).fadeOut(100);
                return false;
            },
            onChange: function (hsb, hex, rgb) {
                thisDiv.css('backgroundColor', '#' + hex);
            }
        })
    });
});
function createParamTableObject() {
    var events = {
        eventsArray :  {
            onStartColorChanged : [],
            onFinishColorChanged : [],
            onStartOpacityChanged :[],
            onFinishOpacityChanged : [],
            onRadiusChanged : []
        },
        bind : function(evtName, callback) {
            var eventQueue = events[evtName];
            if(!eventQueue)
                return;
            eventQueue.push(callback);
        },
        trigger : function(evtName, params) {
            var eventsQueue = events[evtName];
            if(!eventsQueue)
                return;
            for(var i = 0, l = eventsQueue.length; i < l; i++) {
                eventsQueue[i].apply(window, params);
            }
        }
    }
    function getStartColor() {
        return $('#gradient_start_color').css('background-color');
    }
    function getFinishColor() {
        return $('#gradient_finish_color').css('background-color');
    }
    function getStartOpacity() {
        return parseFloat($('#gradiet_opacity_start').val());
    }
    function getFinishOpacity() {
        return parseFloat($('#gradiet_opacity_finish').val());
    }
    
}