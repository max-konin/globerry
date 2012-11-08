//Bottom Singleton object
var Bottom = new function() {
	var instance;
	// Приватные методы и свойства
	// ... 
	//Массив городов, для которого генерятся туры, отели, билеты
	var _cities;
	// Информация болкировона карта панелью
	var isLock;
	//нажатая кнопка
	var activeButton;
	var magicConstant = 37;
	var _hotels = [ "InterContinental Hotels Group", "Wyndham Worldwide	", "Marriott International	", "Hilton Hotels Corp.	", "Choice Hotels International",
			"Accor", "Best Western International", "Starwood Hotels & Resorts Worldwide", "Carlson Hospitality Worldwide", "Global Hyatt Corp.",
			"Golden Tulip Hospitality Group", "Swissôtel Hotels & Resorts", "Kempinski", "AZIMUT Hotels", "AMAKS Hotels & Resorts" ];
	//Открытая/закрытая нижняя панель
	var _bottomActive = false;
	//0 - незаполнен, 1 - заполнен рандомными городами,2 - городом из маркера либо городами из кривулины
	var _isFilled = 0;
	
	var FILL = (function() {
	     var private = {
	         'noFill': '0',
	         'randomFill': '1',
	         'markerOrCurveFill': '2'
	     };

	     return {
	        get: function(name) { return private[name]; }
	    };
	})();

	var getNight = function(number) {
		var mod = number % 10;
		if ((number <= 20 && number >= 5) || (mod >= 5 && mod <= 9) || (mod == 0))
			return " ночей";
		if (mod >= 2 && mod <= 4)
			return " ночи";
		return " ночь";

	};
	var isArray = function isArray(input){
	    return typeof(input)=='object'&&(input instanceof Array);
	  };

	var createTour = function(cityName, cityId) {
		var numberOfNights = Math.floor(Math.random() * 9 + 3);
		var hotelNumber = Math.floor(Math.random() * (_hotels.length));
		var image = "resources/img/pegas.png";
		var href = "http://pegast.ru/";
		var tourText = "Тур на ";
		var hotelText = "Отель ";
		var nightText = " на ";
		var nightWord = getNight(numberOfNights);

		return '<div class="Line" id="tour' + cityId + '"><div class="bottom_entity"><table><tr><td width="300px"><div class="bottom_entity_name">' + cityName
				+ ":" + tourText + numberOfNights + nightWord + ", " + hotelText + _hotels[hotelNumber] + '.</div><div class="bottom_entity_description">'
				+ '</div></td><td class="bottom_entity_td"><div class="hotelCost">' + '</div></td>'
				+ '<td><div class="bottom_entity_ref"><a target="_blank" href="' + href + '" ><img src="' + image + '"/></a></div></td>'
				+ '</tr></table></div></div>';

	};
	var createHotel = function(cityName, cityId) {
		var hotelText = "Отель ";
		var nightText = " на ";
		var hotelNumber = Math.floor(Math.random() * (_hotels.length));
		var numberOfNights = Math.floor(Math.random() * 9 + 3);
		var nightWord = getNight(numberOfNights);
		image = "http://www.onetwotrip.com/images/logo_u0341.png";
		href = "http://www.onetwotrip.com/";

		return '<div class="Line" id="avia' + cityId + '"><div class="bottom_entity"><table><tr><td width="300px"><div class="bottom_entity_name">' + hotelText
				+ _hotels[hotelNumber] + nightText + numberOfNights + nightWord + '</div><div class="bottom_entity_description">'
				+ '</div></td><td class="bottom_entity_td"><div class="hotelCost">' + '</div></td>'
				+ '<td><div class="bottom_entity_ref"><a target="_blank" href="' + href + '" ><img src="' + image + '"/></a></div></td>'
				+ '</tr></table></div></div>';
	};
	var createAvia = function(cityName, cityId) {
		image = "resources/img/booking.com.png";
		href = "http://www.booking.com/";
		var aviaText = "Рейс ";
		var flight = Math.floor(Math.random() * 9 + 3);

		return '<div class="Line" id="hotel' + cityId + '"><div class="bottom_entity"><table><tr><td width="300px"><div class="bottom_entity_name">' + cityName
				+ ":" + aviaText + flight + '</div><div class="bottom_entity_description">' + '</div></td><td class="bottom_entity_td"><div class="hotelCost">'
				+ '</div></td>' + '<td><div class="bottom_entity_ref"><a target="_blank" href="' + href + '" ><img src="' + image + '"/></a></div></td>'
				+ '</tr></table></div></div>';
	};
	//очищает боттом
	var emptyBottom = function() {
		$("#" + "tour" + "ScrollBar .viewport .overview").empty();
		$("#" + "avia" + "ScrollBar .viewport .overview").empty();
		$("#" + "hotel" + "ScrollBar .viewport .overview").empty();
	};
	
	
	// Конструктор
	function Bottom() {
		if (!instance) {
			instance = this;

		} else
			return instance;

		// Публичные свойства
		return instance;
	}

	// Публичные методы
	//заполняет боттом случайными сущностями со случайными городами
	Bottom.prototype.fillWithRandomEntitys = function(cities) {
		emptyBottom();
		var numberOfEntitys = Math.floor(Math.random() * 7 + 16);
		for (i = 0; i < numberOfEntitys; i++) {
			cityNumber = Math.floor(Math.random() * (cities.length));
			city = cities[cityNumber];
			$("#" + "tour" + "ScrollBar .viewport .overview").append(createTour(city.ru_name));
			cityNumber = Math.floor(Math.random() * (cities.length));
			city = cities[cityNumber];
			$("#" + "hotel" + "ScrollBar .viewport .overview").append(createHotel(city.ru_name));
			cityNumber = Math.floor(Math.random() * (cities.length));
			city = cities[cityNumber];
			$("#" + "avia" + "ScrollBar .viewport .overview").append(createAvia(city.ru_name));

		}
		$("#" + activeButton.id + "ScrollBar").tinyscrollbar_update();

	};
	var isDragging = function isDragging() {
		return $("#bottom").is(".ui-draggable-dragging");
	}
	Bottom.prototype.getActive = function() {
		if(_bottomActive == false)
			return null;
		return activeButton;
	}

	Bottom.prototype.isLock = function() {
		return isLock;
	}
	
	Bottom.prototype.draggableHandlerStart = function(event, ui) {			
		if(_bottomActive == false) {
			var button = document.getElementById("tour");
			if (activeButton != undefined) {
				activeButton.style.background = 'rgb(37, 46, 64)';
			}
			activeButton = button;
				
				var cities = (new AjaxRequestController).getCitiesArrayCopy();
				instance.fillWithRandomEntitys(cities);

			$("#" + button.id + "B").show(1);
			button.style.background = 'rgb(255, 122, 2)';
			$(button).animate({
				width : 124
			}, 0);
			_bottomActive = true;
		}
	}
	
	Bottom.prototype.draggableHandlerDrag = function(event, ui) {
		var height = $(document).height() - ui.position.top;
		var buttonId = activeButton.id;
		$("#" + buttonId + "B .viewport").css("height", height - magicConstant);
		$("#" + buttonId + "ScrollBar").tinyscrollbar_update();
	}
	
	Bottom.prototype.draggableHandlerStop = function(event, ui) {
		if(!_bottomActive)
			return;
		var buttonId = activeButton.id;
		var jCal = $("#calendar:visible");
		var minTop = Math.max((jCal.length != 0 ? jCal.position().top : 0) + jCal.height(),
								$("#headBottom").position().top + $("#headBottom").height()) + 30;		
		var middleTop = $(document).height() / 2 - 30;			
		var maxTop = $(document).height() - 100;
		if(ui.position.top <  minTop || ui.position.top < middleTop) {
			$("#" + buttonId + "ScrollBar .scrollbar").hide();
			$("#" + buttonId + "B .viewport").animate({ 
				height :  $(document).height() - minTop - magicConstant
			}, "normal", function() {
				$("#" + buttonId + "ScrollBar").tinyscrollbar_update();				
				$("#modalBlack").fadeIn(2000);
				$("#" + buttonId + "ScrollBar .scrollbar").show();
			});
			isLock = true;
			(new AjaxRequestController).setLock(true);
		} else if(ui.position.top > maxTop) {
			instance.close();
			$("#modalBlack").fadeOut(500);
			isLock = false;
			(new AjaxRequestController).setLock(false);
		} else if(ui.position.top > middleTop) {
			$("#" + buttonId + "ScrollBar .scrollbar").hide();
			$("#" + buttonId + "B .viewport").animate({
				height : 170 - magicConstant
			}, "normal", function() {
				$("#modalBlack").fadeOut(500);
				$("#" + buttonId + "ScrollBar").tinyscrollbar_update();
				$("#" + buttonId + "ScrollBar .scrollbar").show();
			});
			(new AjaxRequestController).setLock(false);
			isLock = false;
		}
		$("#bottomContainer").css("top", "");
	}
	
	Bottom.prototype.bottomButtonClick = function(button, callBackFunction) {
		var height = $(document).height() - $("#bottomContainer").position().top - magicConstant;
		if (_bottomActive == false) {
			if (activeButton != undefined) {
				activeButton.style.background = 'rgb(37, 46, 64)';
			}
			activeButton = button;
			var fillWithRandomEntitysFunc = this.fillWithRandomEntitys;
			if(typeof callBackFunction == "undefined")
			callBackFunction = function() {
				$("#" + button.id + "B").show("normal", function() {
					var cities = (new AjaxRequestController).getCitiesArrayCopy();
					fillWithRandomEntitysFunc(cities);
					$("#" + button.id + "B .scrollbar").show();
				});
			}
			$("#" + button.id + "B .scrollbar").hide();			
			$("#" + button.id + "B .viewport").animate({
				height : height < 170 ? 170 - magicConstant : height
 			}, 10, callBackFunction);
			button.style.background = 'rgb(255, 122, 2)';
			$(button).animate({
				width : 124
			}, 0);

			_bottomActive = true;
			activeButton = button;
		} else if (_bottomActive == true) {
			if (activeButton == button) {
				if(isLock)
					return;
				$("#" + activeButton.id + "B").hide("normal", function() {
					button.style.background = 'rgb(37, 46, 64)';
				});
				$(button).animate({
					width : 120
				}, 0);
				_bottomActive = false;
				activeButton = undefined;
			} else {
				button.style.background = 'rgb(255, 122, 2)';
				$(button).animate({
					width : 124
				}, 0);
				$("#" + button.id + "B .viewport").css("height", height);
				$("#" + activeButton.id + "B .viewport").css("height", 133);
				activeButton.style.background = 'rgb(37, 46, 64)';
				$(activeButton).animate({
					width : 120
				}, 0);
				$("#" + button.id + "B").show();
				$("#" + button.id + "ScrollBar").tinyscrollbar_update();				
				$("#" + activeButton.id + "B").hide();
				activeButton = button;
			}
		}
	}
	
	Bottom.prototype.close = function() {
		if (activeButton == undefined)
			return;
		if (!_bottomActive)
			return;
		$(("#" + activeButton.id + "B")).hide("normal");
		$("#" + activeButton.id + "B .viewport").css("height", 133);

		activeButton.style.background = 'rgb(37, 46, 64)';
		$(activeButton).animate({
			width : 120
		}, 1);
		_bottomActive = false;
		activeButton = undefined;
		isLock = false;
	};
	
	Bottom.prototype.updateStaff = function(cities) {
		if(!isArray(cities))
			return;
		if(cities.length == 0)
			return;
		var needUpdate = this._cities != cities;
		if(!needUpdate && _bottomActive)
			return;
		this._cities = cities;

		var height = $(document).height() - $("#bottomContainer").position().top - 67;
		if (typeof activeButton == "undefined")
			activeButton = document.getElementById("tour");
		activeButton.style.background = 'rgb(255, 122, 2)';
		
		updateStaffCallback = function() {
			if(needUpdate)
				emptyBottom();
			_bottomActive = true;
			$(("#" + activeButton.id + "B")).show("normal", function()
					{
			if(needUpdate)
				{
			var numberOfEntitys = Math.floor(Math.random() * 6 + 4);
			for (i = 0; i < numberOfEntitys; i++) {
				cityNumb = Math.floor(Math.random() * (cities.length));
				$("#" + "tour" + "ScrollBar .viewport .overview").append(createTour(cities[cityNumb].ru_name));
				$("#" + "hotel" + "ScrollBar .viewport .overview").append(createHotel(cities[cityNumb].ru_name));
				$("#" + "avia" + "ScrollBar .viewport .overview").append(createAvia(cities[cityNumb].ru_name));

			}
				}
			$("#" + activeButton.id + "ScrollBar").tinyscrollbar_update();
			$("#" + activeButton.id + "B .scrollbar").show();
					});
	};
		$("#" + activeButton.id + "B .scrollbar").hide();
		$("#" + activeButton.id + "B .viewport").animate({
			height : height < 200 ? 133 : height
			}, 10, updateStaffCallback);
		


			
	};
	return Bottom;
}