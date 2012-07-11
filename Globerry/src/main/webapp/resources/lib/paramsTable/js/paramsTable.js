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
    
    //скрываем/показываем панель настроек
    $('a.handle').css('background-color','black');
    $('#paramsTable').css('right','0px');
    $('#paramsTable').css('display','none');
    var isTableVisible = false;
    $('#buttonParamsTable').click(function(){
        if (isTableVisible){
            $('#paramsTable').css('display','none');
            $('#paramsTable').css('right','0px')
            isTableVisible = false;
        }
        else{
            $('#paramsTable').css('display','block');
            $('#paramsTable').css('right','0px')
            isTableVisible = true;
        }
    })
    
    function changeInputValue(thisSlider){
        var min = parseFloat(thisSlider.find('.inputsDiv > input.min-amount').val());
        var max = parseFloat(thisSlider.find('.inputsDiv > input.max-amount').val());
        thisSlider.find('div.inputsDiv > input.cur-amount').change(function() {
            var tmpValue = thisSlider.find('input.cur-amount').val();
            //ограничения максимального и минимального вводимых значений
            if (tmpValue >= min && tmpValue <= max){
                curValue = tmpValue;
            }
            console.log(curValue);
            $( thisSlider.find('.paramsTableSlider') ).slider({
                value: curValue
            })
        });
    };
    
    
    //обработка слайдеров
    $(function() {
        var inputClass = ".cur-amount";
        var sliderDivClass = '.mySliders';
        var sliderClass = '.paramsTableSlider';
        
        //        $('input#gradient_opacity_start').change(function() {
        //            console.log('Handler for .change() called.');
        //        });
        
        $('.mySliders').each(function(){
            var thisSlider = $(this);
            var curValue = thisSlider.find('input.cur-amount').val();
            console.log(curValue);
            
            var min = parseFloat(thisSlider.find('.inputsDiv > input.min-amount').val());
            var max = parseFloat(thisSlider.find('.inputsDiv > input.max-amount').val());
            changeInputValue(thisSlider);
            //            $(this).find('div.inputsDiv > input.cur-amount').change(function() {
            //                var tmpValue = thisSlider.find('input.cur-amount').val();
            //                //ограничения максимального и минимального вводимых значений
            //                if (tmpValue >= min && tmpValue <= max){
            //                    curValue = tmpValue;
            //                }
            //                console.log(curValue);
            //                $( thisSlider.find('.paramsTableSlider') ).slider({
            //                    value: curValue
            //                })
            //            });
            $( $(this).find(sliderClass) ).slider({
                range: "min",
                value: 50,
                min: min,
                max: max,
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
                //                console.log(colpkr);
                curColor = rgb2hex($(this).find('div').css('backgroundColor'));
                $(this).ColorPickerSetColor(curColor);
                thisDiv.css('backgroundColor', curColor);
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
    
    //передвижение ползунка слайдера
    function setSliderValue(thisSlider, val){
        var min = parseFloat(thisSlider.find('.inputsDiv > input.min-amount').val());
        var max = parseFloat(thisSlider.find('.inputsDiv > input.max-amount').val());
        console.log('min='+min);
        console.log('val='+val);
        console.log('max='+max);
        
        if (val < min){
            val = min;
        }
        if (val > max){
            val = max;
        }
        $( thisSlider.find('.paramsTableSlider') ).slider({
            value: val
        })
        return val;
    };
    
    //get
    function getZoomLevel(){
        return $('#zoom_level').text();
    }
    function getRadius(){
        //
    }
    function getStartColor() {
        return $('#gradient_start_color > div').css('background-color');
    }
    function getFinishColor() {
        return $('#gradient_finish_color > div').css('background-color');
    }
    function getStartOpacity() {
        return parseFloat($('#gradient_opacity_start').val());
    }
    function getFinishOpacity() {
        return parseFloat($('#gradient_opacity_finish').val());
    }
    //set
    function setStartColor(color) {
        return $('#gradient_start_color > div').css('background-color',color);
    }
    function setFinishColor(color) {
        return $('#gradient_finish_color > div').css('background-color',color);
    }
    function setStartOpacity(opacityVal) {
        opacityVal = parseInt(opacityVal);
        var thisSlider = $('#gradient_opacity_start').parent().parent();
        var val = setSliderValue(thisSlider,opacityVal);
        return $('#gradient_opacity_start').val(val);
    }
    function setFinishOpacity(opacityVal) {
        opacityVal = parseInt(opacityVal);
        var thisSlider = $('#gradient_opacity_finish').parent().parent();
        var val = setSliderValue(thisSlider,opacityVal);
        return parseFloat($('#gradient_opacity_finish').val(val));
    }
    var me = {
        getStartColor : getStartColor,
        getFinishColor : getFinishColor,
        getStartOpacity : getStartOpacity,
        getFinishOpacity : getFinishOpacity,
        
        setStartColor : setStartColor,
        setFinishColor : setFinishColor,
        setStartOpacity : setStartOpacity,
        setFinishOpacity : setFinishOpacity
    }
    return me;
}