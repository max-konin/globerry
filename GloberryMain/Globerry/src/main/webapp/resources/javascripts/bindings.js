/* 
 * Bindings of globerry home page
 */
$(document).ready(function() {
	var ajaxRequestController = new AjaxRequestController;

	$(".slider-range").each(function(){
		var signTemper = false;
		var signLiving = false;
		var signFeel = false;
		var signSecurity = false;
		var signSex = false;
		if($(this).attr('id') == "temperatureSlider") signTemper = true; else signTemper = false;
		if($(this).attr('id') == "livecostSlider") signLiving = true; else signLiving = false;
		if($(this).attr('id') == "moodSlider") signFeel = true; else signFeel = false;
		if($(this).attr('id') == "securitySlider") signSecurity = true; else signSecurity = false;
		if($(this).attr('id') == "sexSlider") signSex = true; else signSex = false;
		var minVal = parseInt($(this).parent().find('div.left:first').html());
		var maxVal = parseInt($(this).parent().find('div.right:last').html());
		(signFeel || signSecurity || signSex) ? maxVal = 120 : maxVal = maxVal;
		var params = {
			range: true,
			min: minVal,
			max: maxVal,
			values: [ minVal, maxVal ],
			step : signLiving ? 10 : 1,
			stop : function(event, ui) {
				ui.values[0] = (signFeel || signSecurity || signSex) ? Math.round(ui.values[0] / 40) : ui.values[0];
				ui.values[1] = (signFeel || signSecurity || signSex) ? Math.round(ui.values[1] / 40) : ui.values[1];
				ajaxRequestController.sendRequest($(this).parent().attr('guiId') , {
					leftValue : ui.values[0],
					rightValue : ui.values[1]
				})
			},
			slide : signTemper ? function(event, ui) {
				$(this).parent().find('div.left:first').html((ui.values[0] > 0 ? "+" : "") + ui.values[0]);
				$(this).parent().find('div.right:last').html((ui.values[1] > 0 ? "+" : "") + ui.values[1]);
			} : function(event, ui) {
				$(this).parent().find('div.left:first').html(ui.values[0]);
				$(this).parent().find('div.right:last').html(ui.values[1]);
			}
		};
		$(this).slider(params);
	});

	/***************************************************/
	//выбор месяца

	$(".month").click(function() {
	
	 
		if($(this).hasClass('activeMonth'))
			return;
		$('.activeMonth').removeClass('activeMonth');
		$(this).addClass('activeMonth');
		var value = $(this).attr('value');
		var comboBox = $('#whenSelect').data("kendoComboBox");
		//            comboBox.select(parseInt(value) - 1);
		comboBox.select(parseInt(value));
		ajaxRequestController.sendRequest($(this).parent().attr('guiId'), {
			value : parseInt(value)
			});
	});
	
	$('select.gui_element').change(function() {

	
		var value = $(this).select().val();
		ajaxRequestController.sendRequest($(this).attr('guiId'), {
			value : parseInt(value)
			});
		var guiid = $(this).attr('guiId');
		//если изменено значение "кто едет"
		if (guiid == 1){ }
		//если изменено значение "что делать"
		if (guiid == 2){ }
		//если изменено значение "когда"
		if (guiid == 3){
			$('.activeMonth').removeClass('activeMonth');
			$('#month' + value).addClass('activeMonth');
		}

	});



	//обработка чекбоксов виза и язык
	$(".check").click(function() {

		if ($('#'+this.id).hasClass('checkOn'))
		{
			check_box_id = $('#'+this.id);
			$('#'+this.id).removeClass('checkOn');
			$('#'+this.id+'>'+'input').val(0);
		}
		else
		{
			$('#'+this.id).addClass('checkOn');
			$('#'+this.id+'>'+'input').val(1);	
		}

		ajaxRequestController.sendRequest($(this).attr('guiId'), {
			value : parseInt($('#'+this.id+'>'+'input').val())
			});
	});


	$("#aviaScrollBar").tinyscrollbar();
	$("#tourScrollBar").tinyscrollbar();

	$(".bottomButton").click(function()
	{
		(new Bottom).bottomButtonClick(this.id);
	});

	$("#bottomContainer").draggable({
		axis : "y",
		scroll : false,
		handle : "#gradLine, .bottomButton, #whiteBottom, #invisibleBottom",
		cursorAt: { top : 2 },
		delay : 0,
		start : (new Bottom).draggableHandlerStart,
		drag : (new Bottom).draggableHandlerDrag,
		stop : (new Bottom).draggableHandlerStop
	});
        $("#tourScrollBar").touchScroll();
	var resize;
	
	$(window).resize(function() {
		clearTimeout(resize);
		resize = setTimeout((new Bottom).draggableHandlerStop, 100, null, {position:{top:$("#bottomContainer").position().top}});
	});

});
window.onload = function () {

    var iframes = document.getElementsByTagName('iframe');

   

        var iframe = iframes[0],

            d = document.createElement('div');

        d.style.width = iframe.offsetWidth + 'px';

        d.style.height = iframe.offsetHeight + 'px';

        d.style.top = iframe.offsetTop + 'px';

        d.style.left = iframe.offsetLeft + 'px';

        d.style.position = 'absolute';

        d.style.opacity = '0';

        d.style.filter = 'alpha(opacity=0)';

        d.style.background = 'black';

        iframe.offsetParent;

    

};