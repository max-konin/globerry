$(document).ready(function() {
                
    $(function(){
        $('.panel').tabSlideOut({							//Класс панели
            tabHandle: '.handle',						//Класс кнопки
            pathToTabImage: '../images/button.gif',				//Путь к изображению кнопки (почему-то не работает)
            imageHeight: '122px',						//Высота кнопки
            imageWidth: '40px',						//Ширина кнопки
            tabLocation: 'right',						//Расположение панели top - выдвигается сверху, right - выдвигается справа, bottom - выдвигается снизу, left - выдвигается слева
            speed: 100,								//Скорость анимации
            action: 'click',						//Метод показа click - выдвигается по клику на кнопку, hover - выдвигается при наведении курсора
            topPos: '200px',							//Отступ сверху
            fixedPosition: false						//Позиционирование блока false - position: absolute, true - position: fixed
        });
    });
    //картинка не берется
    $('a.handle').css('background-color','black')
    
    $(function() {
        var sliderId = "#"+"slider-range-min";
        var inputId = "#"+"cur-amount";
        $( sliderId ).slider({
            range: "min",
            value: 37,
            min: 1,
            max: 700,
            slide: function( event, ui ) {
                $( inputId ).val( ui.value );
            }
        });
        $( inputId).val( $( sliderId ).slider( "value" ) );
    });  

    //ColorPicker встраиваемый
    var colorSelectorId = '.colorSelector';
    $(colorSelectorId).each(function(){
        var thisDiv = $(this).find('div');
        $(this).ColorPicker({
        color: '#0000ff',
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