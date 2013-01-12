//Bottom Singleton object
var Bottom = new function() {
	var instance;
	// Приватные методы и свойства
	// ... 
	//Массив городов, для которого генерятся туры, отели, билеты
	var _cities;
	// Информация: болкировона ли карта панелью
	var _isLock;
	//нажатая вкладка
	var activeTab;
	// Some magic
	var magicWork = false;
	//Открытая/закрытая нижняя панель
	var _bottomActive = false;
	
	var bottomTabs =  {
		tour : TourBottom,
		avia : AviaBottom,
		hotel : HotelBottom
	};

	var isArray = function isArray(input){
		return typeof(input)=='object'&&(input instanceof Array);
	};

	// Конструктор
	function Bottom() {
		if (!instance) {
			instance = this;
		}
		
		return instance;
	}

	// Публичные методы
	var isDragging = function isDragging() {
		return $("#bottom").is(".ui-draggable-dragging");
	}

	Bottom.prototype.getActive = function() {
		if(_bottomActive == false)
			return null;
		return activeTab;
	}

	Bottom.prototype.isLock = function() {
		return _isLock;
	}
	
	Bottom.prototype._bottomTabs = bottomTabs;
	
	Bottom.prototype.draggableHandlerStart = function(event, ui) {
		BottomTab.logger("Bottom start dragging.");
		$("#invisibleBottom").css("z-index", 10);
		$(".column").height(0).hide();
		if(_bottomActive == false) {
			instance.bottomButtonClick("tour");
			$(".column").show();
		}
	}
	
	Bottom.prototype.draggableHandlerDrag = function(event, ui) {
		var height = $(document).height() - $("#bottomContainer").position().top;
		$(".column").height(height - 7);
		activeTab.draggableHandlerDrag(event, ui);
	}
	
	Bottom.prototype.draggableHandlerStop = function(event, ui) {
		$("#invisibleBottom").css("z-index", 1);
		if(!_bottomActive)
			return;
		var state = activeTab.draggableHandlerStop(event, ui);
		_isLock = state.isLock;
		(new AjaxRequestController).setLock(_isLock);
		$(".column").height(state.height);
		$(".column").show();
		$("#bottomContainer").css("top", "");
		BottomTab.logger("Bottom stop dragging.");
		if(state.height == 0) {
			instance.close();
		}
	}

	Bottom.prototype.bottomButtonClick = function(buttonId, callBackFunction) {
		BottomTab.logger("Bottom get clicked on " + buttonId + " tab. Executing.");
		var height = $(document).height() - $("#bottomContainer").position().top;
		if(magicWork) {
			BottomTab.logger("Magic at work. Sorry.");
			return;
		}
		magicWork = true;
		BottomTab.logger("Magic is about to happend.");
		if (_bottomActive == false) {
			activeTab = bottomTabs[buttonId];
			_bottomActive = true;
			setTimeout(function() {$(".column").height(163); }, 700);
			$(".column").show();
			bottomTabs[buttonId].Open(height, callBackFunction);
		} else {
			if (activeTab == bottomTabs[buttonId]) {
				if(_isLock)
					return;
				$(".column").hide();
				$(".column").animate({height : 0}, 400);
				activeTab.Close();
				_bottomActive = false;
				activeTab = undefined;
			} else {
				$(".column").height(height - 7);
				$(".column").show();
				activeTab.Close();
				activeTab = bottomTabs[buttonId];
				bottomTabs[buttonId].Open(height, callBackFunction);
			}
		}
		setTimeout(function() { magicWork = false; BottomTab.logger("Magic no more exist."); }, 800);
	}
	
	Bottom.prototype.close = function() {
		BottomTab.logger("Bottom closing.");
		if(activeTab == undefined && !_bottomActive) {
			BottomTab.logger("Bottom already close.")
			return;
		}
		$(".column").hide();
		$(".column").height(0);
		activeTab.RevertOpen();
		_bottomActive = false;
		activeTab = undefined;
		_isLock = false;
		BottomTab.logger("Bottom closed.");
	};
	
	Bottom.prototype.updateStaff = function updateStaff(cities) {
		if(!isArray(cities))
			return;
		if(cities.length == 0)
			return;
		var needUpdate = this._cities != cities;
		if(!needUpdate && _bottomActive)
			return;
		this._cities = cities;

		if (typeof activeTab == "undefined")
			activeTab = bottomTabs["tour"];
		var height = $(document).height() - $("#bottomContainer").position().top;
		var recreateContent = function() {
			$("#" + activeTab._id + "B").slideDown("normal", function() {	
				_bottomActive = true;
				for(var key in bottomTabs) {
					bottomTabs[key].RecreateContent(cities);
				}
				$("#" + activeTab._id + "B .scrollbar").show();
			});
		};
		
		$(".column").height(163);
		if(!activeTab._active)
			activeTab.Open(height, recreateContent);
		else {
			_bottomActive = true;
			for(var key in bottomTabs) {
				bottomTabs[key].RecreateContent(cities);
			}
		}
	};
	
	return Bottom;
}