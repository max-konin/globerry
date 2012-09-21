/*******************************************************************************/
//всплывающая панель внизу
// switch the bottom on and off, and changes the state of the buttom.

var _bottomInstance = new Bottom;

function preventSelection(element)
{
	var preventSelection = false;

	function addHandler(element, event, handler)
	{
		if (element.attachEvent)
			element.attachEvent('on' + event, handler);
		else
			if (element.addEventListener)
				element.addEventListener(event, handler, false);
	}
	function removeSelection()
	{
		if (window.getSelection)
		{
			window.getSelection().removeAllRanges();
		}
		else
			if (document.selection && document.selection.clear)
				document.selection.clear();
	}
	function killCtrlA(event)
	{
		var event = event || window.event;
		var sender = event.target || event.srcElement;

		if (sender.tagName.match(/TEXTAREA/i))
			return;

		var key = event.keyCode || event.which;
		if (event.ctrlKey && key == 'A'.charCodeAt(0)) // 'A'.charCodeAt(0) можно заменить на 65
		{
			removeSelection();

			if (event.preventDefault)
				event.preventDefault();
			else
				event.returnValue = false;
		}
	}

	// не даем выделять текст мышкой
	addHandler(element, 'mousemove', function()
	{
		if (preventSelection)
			removeSelection();
	});
	addHandler(element, 'mousedown', function(event)
	{
		var event = event || window.event;
		var sender = event.target || event.srcElement;
		preventSelection = !sender.tagName.match(/TEXTAREA/i);
	});

	// борем dblclick
	// если вешать функцию не на событие dblclick, можно избежать
	// временное выделение текста в некоторых браузерах
	addHandler(element, 'mouseup', function()
	{
		if (preventSelection)
			removeSelection();
		preventSelection = false;
	});

	// борем ctrl+A
	// скорей всего это и не надо, к тому же есть подозрение
	// что в случае все же такой необходимости функцию нужно 
	// вешать один раз и на document, а не на элемент
	addHandler(element, 'keydown', killCtrlA);
	addHandler(element, 'keyup', killCtrlA);
}

//$('#bottomContainer').click(function(){alert('click')})

$(document).ready(function()
{

	/*******************************************************************************/
	//создаем комбобоксы
	$(".whoSelect").kendoComboBox(
	{
		highLightFirst : false
	});
	$(".whatSelect").kendoComboBox(
	{
		highLightFirst : false
	});
	$(".whenSelect").kendoComboBox(
	{
		highLightFirst : false,
		height : 1000
	});
	/*******************************************************************************/
	/*******************************************************************************/
	//все combobox'ы readonly
	$('.whoWhatWhenDropDownList .k-input').attr('readonly', 'readonly');
});
$(document).ready(function()
{
	$(".changeHeaderByClick").click(function()
	{
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
$(document).ready(function()
{
	/*******************************************************************************/
	var speed = "normal"
	/*обработка кнопки "Поехали"*/
	$(".headerButton").click(function(event)
	{

		SelectActive = false;
		$("#headContent1").animate(
		{
			height : 0
		}, speed, function()
		{
			$("#headContent1").hide();
			$("#headContent2").show();
			$("#headText").show();
			$("#bottomToggle").slideToggle(speed);
			$("#bottom").show();
			//отображаем кнопки внизу
			$("#bottomContainer").show();
			$("#headerButtonUp").show();
			$("#upperHeaderBlockWithCircle").show();
			$("#upperHeaderBlockWithCircle").animate(
			{
				opacity : 1
			}, 100);
			$("#headerButtonUp").animate(
			{
				opacity : 1
			}, speed);
			$("#headContent2").animate(
			{
				height : 100
			}, speed, function()
			{
				$("#calendar").show();
				$("#calendar").animate(
				{
					height : 26,
					top : 0
				}, speed, function()
				{
					//here I wanted to fix bug with ctrange appearance of the calendar.
				});
			});
		});
	});
	// as previous function this hides the second header and shows the first, with aimation.
	$(".headerUpSwitcher").click(function(event)
	{
		//animation.
		$("#headText").hide();
		$("#calendar").animate(
		{
			height : 12,
			top : -16
		}, speed, function()
		{
			$("#calendar").hide();
			$("#headerButtonUp").animate(
			{
				opacity : 0
			}, speed);
			$("#upperHeaderBlockWithCircle").animate(
			{
				opacity : 0
			}, speed);
			$("#headContent2").animate(
			{
				height : 0
			}, speed, function()
			{
				//скрываем кнопки внизу
				$("#bottomContainer").hide();
				$("#headerButtonUp").hide();
				$("#upperHeaderBlockWithCircle").hide();
				$("#headContent2").hide();
				$("#headContent1").show();
				$("#headContent1").animate(
				{
					height : 38
				}, speed);
			});
		});
	});

	preventSelection(document);

	/* Подсказки */

});
