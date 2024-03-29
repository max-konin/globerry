var BottomTab = {
	// tabs identificator
	_id: "",
	// HTMLElement - tabs button
	_button: null,
	// do some magic for fit in html
	_magicConstant: 0,
	// indicator of activity for tab
	_active: false,
	// revert state
	RevertOpen: function() {alert("Need implemetation!")},
	// open tab
	Open: function(height, callbackFunction) {alert("Need implemetation!")},
	// close tab
	Close: function(callbackFunction) {alert("Need implementation!")},
	// recreate content by cities
	RecreateContent: function(cities) {alert("Need implementation!")},
	//Handlers for ui-draggable
	DraggableHandlerDrag: function(event, ui) {alert("Need implementation!")},
	DraggableHandlerStop: function(event, ui) {alert("Need implementation!")}
};
Object.defineProperties(BottomTab, {
	// relic of the past
	_tempHotels: {value : [ "InterContinental Hotels Group", "Wyndham Worldwide	", "Marriott International	", "Hilton Hotels Corp.	", "Choice Hotels International",
						"Accor", "Best Western International", "Starwood Hotels & Resorts Worldwide", "Carlson Hospitality Worldwide", "Global Hyatt Corp.",
						"Golden Tulip Hospitality Group", "Swissôtel Hotels & Resorts", "Kempinski", "AZIMUT Hotels", "AMAKS Hotels & Resorts" ],
					writable: false,
					configurable: true},
	// help function
	getNight: {value : function(number) {
		var mod = number % 10;
		if ((number <= 20 && number >= 5) || (mod >= 5 && mod <= 9) || (mod == 0))
			return " ночей";
		if (mod >= 2 && mod <= 4)
			return " ночи";
		return " ночь";
	}, writable: false, configurable : true},
	// logger
	logger: {value: function(string) {
		var d = new Date();
		console.log("[" + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds() + "." + d.getMilliseconds() + "] " + string);
	}, writable: false, configurable: true}
})

var TourBottom = Object.create(BottomTab);

TourBottom._id = "tour";
TourBottom._magicConstant = 37;
$(document).ready(function() { 
	TourBottom._button = document.getElementById(TourBottom._id);
});

TourBottom.RevertOpen = function() {
	$(document).stop(true);
	this.logger("TourBottom reverting open.");
	$("#" + this._id + "B .viewport").css("height", 170 - TourBottom._magicConstant);
	this._button.style.background = 'rgb(37, 46, 64)';
	this._button.style.width = "120px";
	$("#" + this._id + "B").slideUp(200, function() {TourBottom._active = false;BottomTab.logger("TourBottom close now.");});
}

TourBottom.RecreateContent = function(cities) {
	BottomTab.logger("TourBottom recreating content.");
	$("#" + this._id + "ScrollBar .viewport .overview").empty();
	var numberOfEntitys = Math.floor(Math.random() * 7 + 16);
	for(var i = 0; i < numberOfEntitys; i++) {
		var numberOfNights = Math.floor(Math.random() * 9 + 3);
		var hotelNumber = Math.floor(Math.random() * (this._tempHotels.length));
		var image = "resources/img/pegas.png";
		var href = "http://pegast.ru/";
		var tourText = "Тур на ";
		var hotelText = "Отель ";
		var nightText = " на ";
		var nightWord = this.getNight(numberOfNights);
		var city = cities[Math.floor(Math.random() * cities.length)];


		$("#" + this._id + "ScrollBar .viewport .overview").append(
			'<div class="Line" id="tour'
			+ city.id 
			+ '"><div class="bottom_entity"><table><tr><td width="300px"><div class="bottom_entity_name">' 
			+ city.ru_name
			+ ":" 
			+ tourText 
			+ numberOfNights 
			+ nightWord 
			+ ", " 
			+ hotelText 
			+ BottomTab._tempHotels[hotelNumber]
			+ '.</div><div class="bottom_entity_description">'
			+ '</div></td><td class="bottom_entity_td"><div class="hotelCost">' 
			+ '</div></td>'
			+ '</tr></table></div></div>');
	}
	$("#" + this._id + "ScrollBar").tinyscrollbar_update();
	BottomTab.logger("TourBottom recreate content.");
}
	
TourBottom.Open = function(height, callBackFunction) {
	if(this._active)
		return;
	BottomTab.logger("TourBottom start opening.");
	if(typeof callBackFunction == 'undefined')
		callBackFunction = function() {
			$("#" + TourBottom._id + "B").slideDown(200, function() {
				$("#" + TourBottom._id + "ScrollBar").tinyscrollbar_update();
				$("#" + TourBottom._id + "B .scrollbar").show();
				TourBottom._active = true;
				BottomTab.logger("TourBottom open now.");
			});
		}
	$("#" + this._id + "B .scrollbar").hide();
	$("#" + this._id + "B .viewport").height(height <= 170 ? 170 - TourBottom._magicConstant : height - TourBottom._magicConstant);
	callBackFunction();
	this._button.style.background = 'rgb(255, 122, 2)';
	this._button.style.width = "124px";
}
	
TourBottom.Close = function(callBackFunction) {	
	if(!this._active)
		return;
	this.logger("TourBottom start closing.");
	$("#" + this._id + "B .viewport").css("height", 170 - TourBottom._magicConstant);
	this._button.style.background = 'rgb(37, 46, 64)';
	this._button.style.width = "120px";
	$("#" + this._id + "B").slideUp(200, function() {TourBottom._active = false;BottomTab.logger("TourBottom close now.");});
}
	
TourBottom.draggableHandlerDrag = function(event, ui) {
	var height = $(document).height() - ui.position.top;
	$("#" + this._id + "ScrollBar .scrollbar").hide()
	
	$("#" + this._id + "B .viewport").css("height", height - TourBottom._magicConstant);
}
	
TourBottom.draggableHandlerStop = function(event, ui) {
	var ret = {isLock: false, height: 0};
	var jCal = $("#calendar:visible");
	var minTop = Math.max((jCal.length != 0 ? jCal.position().top : 0) + jCal.height(),
						$("#headBottom").position().top + $("#headBottom").height()) + 30;		
	var middleTop = $(document).height() / 2 - 30;			
	var maxTop = $(document).height() - 100;
	if(ui.position.top <  minTop || ui.position.top < middleTop) {
		$("#" + this._id + "ScrollBar .scrollbar").hide();
		$("#" + this._id + "B .viewport").animate({ 
				height :  $(document).height() - minTop - TourBottom._magicConstant
		}, "normal", function() {
			$("#modalBlack").fadeIn(2000);
			$("#" + TourBottom._id + "ScrollBar").tinyscrollbar_update();
			$("#" + TourBottom._id + "ScrollBar .scrollbar").show();
		});	
		ret.isLock = true;
		ret.height = $(document).height() - minTop - TourBottom._magicConstant;
	} else if(ui.position.top > maxTop) {
		this.Close();
		$("#modalBlack").fadeOut(500);
		ret.height = 0;
	} else if(ui.position.top > middleTop) {
		$("#" + this._id + "ScrollBar .scrollbar").hide();
		$("#" + this._id + "B .viewport").animate({
			height : 170 - TourBottom._magicConstant
		}, "normal", function() {
			$("#modalBlack").fadeOut(500);
			$("#" + TourBottom._id + "ScrollBar").tinyscrollbar_update();
			$("#" + TourBottom._id + "ScrollBar .scrollbar").show();
		});
		ret.height = 170 - TourBottom._magicConstant;
	}
	BottomTab.logger("TourBottom stop dragging.");
	return ret;
}

var AviaBottom = Object.create(BottomTab);

AviaBottom._id = "avia";
AviaBottom._magicConstant = 37;
$(document).ready(function() { 
	AviaBottom._button = document.getElementById(AviaBottom._id);
});

AviaBottom.RevertOpen = function() {
	$(document).stop(true);
	this.logger("AviaBottom reverting open.");
	$("#" + this._id + "B .viewport").css("height", 170 - AviaBottom._magicConstant);
	this._button.style.background = 'rgb(37, 46, 64)';
	this._button.style.width = "120px";
	$("#" + this._id + "B").slideUp(200, function() {AviaBottom._active = false;BottomTab.logger("AviaBottom close now.");});
}

AviaBottom.RecreateContent = function(cities) {
	BottomTab.logger("AviaBottom recreating content.");
	$("#" + this._id + "ScrollBar .viewport .overview").empty();
	var numberOfEntitys = Math.floor(Math.random() * 7 + 16);
	for(var i = 0; i < numberOfEntitys; i++) {
		var image = "resources/img/booking.com.png";
		var href = "http://www.booking.com/";
		var aviaText = "Рейс ";
		var flight = Math.floor(Math.random() * 9 + 3);
		var city = cities[Math.floor(Math.random() * (cities.length))];
		$("#" + this._id + "ScrollBar .viewport .overview").append(
			'<div class="Line" id="hotel' 
			+ city.id 
			+ '"><div class="bottom_entity"><table><tr><td width="300px"><div class="bottom_entity_name">'
			+ city.ru_name
			+ ":" 
			+ aviaText 
			+ flight 
			+ '</div><div class="bottom_entity_description">' 
			+ '</div></td><td class="bottom_entity_td"><div class="hotelCost">'
			+ '</tr></table></div></div>');
	}
	$("#" + this._id + "ScrollBar").tinyscrollbar_update();
	BottomTab.logger("AviaBottom recreate content.");
}
	
AviaBottom.Open = function(height, callBackFunction) {
	if(this._active)
		return;
	BottomTab.logger("AviaBottom start opening.");
	if(typeof callBackFunction == 'undefined')
		callBackFunction = function() {
			$("#" + AviaBottom._id + "B").slideDown(200, function() {
				$("#" + AviaBottom._id + "ScrollBar").tinyscrollbar_update();
				$("#" + AviaBottom._id + "B .scrollbar").show();
				AviaBottom._active = true;
				BottomTab.logger("AviaBottom open now.");
			});
		}
	$("#" + this._id + "B .scrollbar").hide();
	$("#" + this._id + "B .viewport").animate({
		height : height <= 170 ? 170 - AviaBottom._magicConstant : height - AviaBottom._magicConstant
	}, null, callBackFunction);
	this._button.style.background = 'rgb(255, 122, 2)';
	this._button.style.width = "124px";
}
	
AviaBottom.Close = function(callBackFunction) {
	if(!this._active)
		return;
	BottomTab.logger("AviaBottom start closing.");
	$("#" + this._id + "B .viewport").css("height", 170 - AviaBottom._magicConstant);
	this._button.style.background = 'rgb(37, 46, 64)';
	this._button.style.width = "120px";
	$("#" + this._id + "B").slideUp(200, function() {AviaBottom._active = false;BottomTab.logger("AviaBottom close now.");});
}
	
AviaBottom.draggableHandlerDrag = function(event, ui) {
	var height = $(document).height() - ui.position.top;
	$("#" + this._id + "ScrollBar .scrollbar").hide();
	$("#" + this._id + "B .viewport").css("height", height - AviaBottom._magicConstant);
}
	
AviaBottom.draggableHandlerStop = function(event, ui) {
	var ret = {isLock: false, height: 0};
	var jCal = $("#calendar:visible");
	var minTop = Math.max((jCal.length != 0 ? jCal.position().top : 0) + jCal.height(),
						$("#headBottom").position().top + $("#headBottom").height()) + 30;		
	var middleTop = $(document).height() / 2 - 30;			
	var maxTop = $(document).height() - 100;
	if(ui.position.top <  minTop || ui.position.top < middleTop) {
		$("#" + this._id + "ScrollBar .scrollbar").hide();
		$("#" + this._id + "B .viewport").animate({
			height : $(document).height() - minTop - AviaBottom._magicConstant
		}, "normal", function() {
			$("#modalBlack").fadeIn(2000);
			$("#" + AviaBottom._id + "ScrollBar").tinyscrollbar_update();
			$("#" + AviaBottom._id + "ScrollBar .scrollbar").show();
		});
		ret.isLock = true;
		ret.height = $(document).height() - minTop - AviaBottom._magicConstant;
	} else if(ui.position.top > maxTop) {
		this.Close();
		$("#modalBlack").fadeOut(500);
		ret.height = 0;
	} else if(ui.position.top > middleTop) {
		$("#" + this._id + "ScrollBar .scrollbar").hide();
		$("#" + this._id + "B .viewport").animate({
			height : 170 - AviaBottom._magicConstant
		}, "normal", function() {
			$("#modalBlack").fadeOut(500);
			$("#" + AviaBottom._id + "ScrollBar").tinyscrollbar_update();
			$("#" + AviaBottom._id + "ScrollBar .scrollbar").show();
		});
		ret.height = 170 - AviaBottom._magicConstant;
	}
	BottomTab.logger("AviaBottom stop dragging.");
	return ret;
}

var HotelBottom = Object.create(BottomTab);

HotelBottom._id = "hotel";
HotelBottom._magicConstant = 7;
$(document).ready(function() { 
	HotelBottom._button = document.getElementById(HotelBottom._id);
});
	
HotelBottom.RevertOpen = function() {
	$(document).stop(true);
	this.logger("HotelBottom reverting open.");
	$("#" + this._id + "B .viewport").css("height", 170 - HotelBottom._magicConstant);
	this._button.style.background = 'rgb(37, 46, 64)';
	this._button.style.width = "120px";
	$("#" + this._id + "B").slideUp(200, function() {HotelBottom._active = false;BottomTab.logger("HotelBottom close now.");});
}
	
HotelBottom.RecreateContent = function(cities) {
	BottomTab.logger("HotelBottom recreating content.");
	var cityStr = "";
	for(var i = 0, j = cities.length; i < j; ++i) {
		cityStr += cities[i].name + ", ";
	}
	cityStr = cityStr.substring(0, cityStr.length - 2);
	if(this._active)
		$("#bookingFrame").attr("src",  "http://www.booking.com/searchresults.html?aid=355103&ss=" + cityStr + "&si=ci");
	else
		$("#bookingFrame").attr("cityBuffer", cityStr);
	BottomTab.logger("HotelBottom recreate content.");
}
	
HotelBottom.Open = function(height, callBackFunction) {
	if(this._active)
		return;
	BottomTab.logger("HotelBottom start opening.");
	if(typeof callBackFunction == 'undefined')
		callBackFunction = function() {
			$("#" + HotelBottom._id + "B").slideDown(200, function() {
				HotelBottom._active = true;
				BottomTab.logger("HotelBottom open now.");
			});
		}
	$("#" + this._id + "B").animate({
		height : height <= 170 ? 170 - HotelBottom._magicConstant : height - HotelBottom._magicConstant
	}, null, callBackFunction);
	this._button.style.background = 'rgb(255, 122, 2)';
	this._button.style.width = "124px";
	var cityStr = $("#bookingFrame").attr("cityBuffer");
	if(typeof cityStr == 'string')
		$("#bookingFrame").attr("src",  "http://www.booking.com/searchresults.html?aid=355103&ss=" + cityStr + "&si=ci");
	$("#bookingFrame").attr("cityBuffer", null);
}
	
HotelBottom.Close = function(callBackFunction) {
	if(!this._active)
		return;
	BottomTab.logger("HotelBottom start closing");
	$("#" + this._id + "B").css("height", 170 - HotelBottom._magicConstant);
	this._button.style.background = 'rgb(37, 46, 64)';
	this._button.style.width = "120px";
	$("#" + this._id + "B").slideUp(200, function() {HotelBottom._active = false;BottomTab.logger("HotelBottom close now.");});
}
	
HotelBottom.draggableHandlerDrag = function(event, ui) {
	var height = $(document).height() - ui.position.top;

	$("#" + this._id + "B").css("height", height - HotelBottom._magicConstant);
}
	
HotelBottom.draggableHandlerStop = function(event, ui) {
	var ret = {isLock : false, height : 0};
	var jCal = $("#calendar:visible");
	var minTop = Math.max((jCal.length != 0 ? jCal.position().top : 0) + jCal.height(),
							$("#headBottom").position().top + $("#headBottom").height()) + 30;
	var middleTop = $(document).height() / 2 - 30;			
	var maxTop = $(document).height() - 100;
	if(ui.position.top <  minTop || ui.position.top < middleTop) {
		$("#" + this._id + "B").animate({
			height : $(document).height() - minTop - HotelBottom._magicConstant
		}, 200, function() {
			$("#modalBlack").fadeIn(2000);
		});
		ret.isLock = true;
		ret.height = $(document).height() - minTop - HotelBottom._magicConstant;
	} else if(ui.position.top > maxTop) {
		this.Close();
		$("#modalBlack").fadeOut(500);
		ret.height = 0;
	} else if(ui.position.top > middleTop) {
		$("#" + this._id + "B").animate({
			height : 170 - HotelBottom._magicConstant
		}, 200, function() {
			$("#modalBlack").fadeOut(500);
		});
		ret.height = 170 - HotelBottom._magicConstant;
	}
	BottomTab.logger("HotelBottom stop dragging.");
	return ret;
}

$(document).ajaxComplete(function(event, request, settings) {
	var cities = (new AjaxRequestController).getCitiesArrayCopy();
	var bTabs = (new Bottom)._bottomTabs;
	for(var key in bTabs) {		
		bTabs[key].RecreateContent(cities);
	}
});