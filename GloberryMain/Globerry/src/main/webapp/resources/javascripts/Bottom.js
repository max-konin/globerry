//Bottom Singleton object
var Bottom = new function() {
	var instance;
	// Приватные методы и свойства
	// ... 
	//Имя города, для которого генерятся туры, отели, билеты
	var _cityName;
	//нажатая кнопка
	var activeButton;
	var _hotels = [ "InterContinental Hotels Group", "Wyndham Worldwide	", "Marriott International	", "Hilton Hotels Corp.	", "Choice Hotels International",
			"Accor", "Best Western International", "Starwood Hotels & Resorts Worldwide", "Carlson Hospitality Worldwide", "Global Hyatt Corp.",
			"Golden Tulip Hospitality Group", "Swissôtel Hotels & Resorts", "Kempinski", "AZIMUT Hotels", "AMAKS Hotels & Resorts" ];
	//Открытая/закрытая нижняя панель
	var _bottomActive = false;

	var getNight = function(number) {
		var mod = number % 10;
		if ((number <= 20 && number >= 5) || (mod >= 5 && mod <= 9) || (mod == 0))
			return " ночей";
		if (mod >= 2 && mod <= 4)
			return " ночи";
		return " ночь";

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
	//заполняет боттом случайными сущностями со случайными городами
	var fillWithRandomEntitys = function(cities) {
		emptyBottom();
		var numberOfEntitys = Math.floor(Math.random() * 7 + 5);
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

	}
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
	Bottom.prototype.bottomButtonClick = function(button) {
		if (_bottomActive == false) {
			if (activeButton != undefined) {

				activeButton.style.background = 'rgb(37, 46, 64)';
			}
			activeButton = button;
			$("#bottom").animate({
				bottom : 147
			}, 100);
			$("#whiteBottom").animate({
				height : 147
			}, 100, function() {
				$(("#" + button.id + "B")).show("normal", function() {
					if (_cityName == undefined) {
						var cities = (new AjaxRequestController).getCitiesArrayCopy();
						fillWithRandomEntitys(cities);
					}
				});
			});
			button.style.background = 'rgb(255, 122, 2)';
			$(button).animate({
				width : 124
			}, 0);

			_bottomActive = true;
			activeButton = button;
		} else if (_bottomActive == true) {
			if (activeButton == button) {
				$("#bottom").animate({
					bottom : 0
				}, 100);
				$(("#" + activeButton.id + "B")).hide();
				$("#whiteBottom").animate({
					height : 0
				}, 100);
				button.style.background = 'rgb(37, 46, 64)';
				$(button).animate({
					width : 120
				}, 0);
				_bottomActive = false;
				activeButton = undefined;
			} else {
				button.style.background = 'rgb(255, 122, 2)';
				$(("#" + activeButton.id + "B")).hide();
				$(button).animate({
					width : 124
				}, 0);
				activeButton.style.background = 'rgb(37, 46, 64)';
				$(activeButton).animate({
					width : 120
				}, 0);
				$(("#" + button.id + "B")).show("normal", function() {
					$("#" + button.id + "ScrollBar").tinyscrollbar_update();
				});
				activeButton = button;

			}
		}
	}
	Bottom.prototype.close = function() {
		if (activeButton == undefined)
			return;
		if (!_bottomActive)
			return;
		$("#bottom").animate({
			bottom : 0
		}, 100);
		$(("#" + activeButton.id + "B")).hide();
		$("#whiteBottom").animate({
			height : 0
		}, 100);
		activeButton.style.background = 'rgb(37, 46, 64)';
		$(activeButton).animate({
			width : 120
		}, 0);
		_bottomActive = false;
		activeButton = undefined;
		_cityName = undefined;
	};
	Bottom.prototype.updateStaff = function(cityName) {
		if (cityName == _cityName)
			return;
		_cityName = cityName;
		if (typeof activeButton == "undefined")
			activeButton = document.getElementById("tour");
		$("#" + activeButton.id).css("background", "rgb(255, 122, 2)");
		$("#bottom").animate({
			bottom : 147
		}, 100);
		$("#whiteBottom").animate({
			height : 147
		}, 100, function() {
			emptyBottom();
			_bottomActive = true;
			$(("#" + activeButton.id + "B")).show("normal");

			var numberOfEntitys = Math.floor(Math.random() * 4 + 3);
			for (i = 0; i < numberOfEntitys; i++) {
				$("#" + "tour" + "ScrollBar .viewport .overview").append(createTour(cityName));
				$("#" + "hotel" + "ScrollBar .viewport .overview").append(createHotel(cityName));
				$("#" + "avia" + "ScrollBar .viewport .overview").append(createAvia(cityName));

			}
			$("#" + activeButton.id + "ScrollBar").tinyscrollbar_update();

		});
	};
	return Bottom;
}