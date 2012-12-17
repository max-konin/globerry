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
	var magicConstant = 37;
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

		} else
			return instance;

		// Публичные свойства
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
	
	Bottom.prototype.draggableHandlerStart = function(event, ui) {
		$("#invisibleBottom").css("z-index", 10);
		if(_bottomActive == false) {
			bottomTabs["tour"].Open();
			activeTab = bottomTabs["tour"];
			_bottomActive = true;
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
		var isLock = activeTab.draggableHandlerStop(event, ui);
		_isLock = isLock;
		(new AjaxRequestController()).setLock(isLock);
		$("#bottomContainer").css("top", "");
	}

	Bottom.prototype.bottomButtonClick = function(buttonId, callBackFunction) {
		var height = $(document).height() - $("#bottomContainer").position().top;
		if (_bottomActive == false) {
			bottomTabs[buttonId].Open(height, callBackFunction);
			_bottomActive = true;
			activeTab = bottomTabs[buttonId];
		} else {
			if (activeTab == bottomTabs[buttonId]) {
				if(_isLock)
					return;				
				activeTab.Close();
				_bottomActive = false;
				activeTab = undefined;
			} else {
				activeTab.Close();
				bottomTabs[buttonId].Open(height, callBackFunction);
				activeTab = bottomTabs[buttonId];
			}
		}
	}
	
	Bottom.prototype.close = function() {
		if (activeTab == undefined)
			return;
		if (!_bottomActive)
			return;
		activeTab.Close();
		_bottomActive = false;
		activeTab = undefined;
		_isLock = false;
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

		if (typeof activeTab == "undefined")
			activeTab = bottomTabs["tour"];
		var height = $(document).height() - $("#bottomContainer").position().top;
		
		var updateStaffCallback = function() {
			_bottomActive = true;
			$(("#" + activeTab._id + "B")).show(null, function()
			{
				if(needUpdate)
				{
					for(var key in bottomTabs) {
						bottomTabs[key].RecreateContent(cities);
					}
				}
			});
		};
		if(activeTab._active)
			updateStaffCallback();
		else
			activeTab.Open(height, updateStaffCallback);
	};
	
	return Bottom;
}