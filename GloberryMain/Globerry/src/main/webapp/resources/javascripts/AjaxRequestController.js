/* 
 * Autor MaxG
 */
var AjaxRequestController = new function()
{
	var instance;

	// Приватные методы и свойства
	// ...
	var _isLock = false;
	var buffer = [];
	var _cities = [];
	var zoomCash = [];
	// Конструктор
	function AjaxRequestController()
	{
		if (!instance)
			instance = this;
		else
			return instance;
		_cities = cities;
		// Публичные свойства
	}

	// Публичные методы
	AjaxRequestController.prototype.setLock = function(isLock)
	{
		_isLock = isLock;
		if (!_isLock)
		{
			(new AjaxRequestController).sendRequest();
		}
	}

	AjaxRequestController.prototype.getLock = function()
	{
		return _isLock;
	}

	AjaxRequestController.prototype.sendRequest = function sendRequest(id, data)
	{
		if (_isLock)
		{
			buffer.push(
			{
			    id : id,
			    value : data
			});
			return;
		}
		var request;
		var cashIsNeed;
		var currentZoom = globalMap.getZoom()
		if (typeof id == "undefined" && typeof value == "undefined")
			if (buffer.length == 0)
				return;
			else
				request = buffer;
		else
		{
			request = [
			{
			    id : id,
			    value : data
			} ];
			if (id == globalMap.guiId)
			{
				if (zoomCash[currentZoom] != undefined)
				{
					if (currentZoom <= 6)
						curves.redrawCurvesNew(zoomCash[currentZoom]);
					else
						curves.redrawMarkers(zoomCash[currentZoom]);
					return;
				}
			} else
			{
				zoomCash = [];
				request.push({id : globalMap.guiId, 
							  value : { value : globalMap.getZoom() }});
			}
		}

		$('#modal').show();
		console.log(request);
		if (globalMap.getZoom() <= 6)
			$
			        .ajax(
			        {
			            url : path + '/gui_changed_new',
			            dataType : 'json',
			            type : 'POST',
			            data : JSON.stringify(request),
			            contentType : "application/json",
			            success : function(response)
			            {
				            if (response == null)
				            {
					            curvesEntity = undefined;
					            zoomCash[currentZoom] = undefined
					            _cities = undefined;
					            return;
				            }
				            curvesEntity = response;
				            zoomCash[currentZoom] = curvesEntity;

				            curves.redrawCurvesNew(curvesEntity);

				            for ( var i = 0; i < curvesEntity.length; ++i)
					            _cities = _cities
					                    .concat(curvesEntity[i].cityList);
				            setTimeout(function()
				            {
					            $('#modal').hide();
				            }, 200);
			            },
			            error : function(response)
			            {
				            var s = "";
				            for (prop in response)
				            {
					            if (typeof response[prop] != "function")
					            {
						            s += "obj[" + prop + "] = "
						                    + response[prop] + "; ";
					            }
				            }
				            alert(s);
			            }
			        });
		else
		{
			$
			        .ajax(
			        {
			            url : path + '/gui_changed',
			            dataType : 'json',
			            type : 'POST',
			            data : JSON.stringify(request),
			            contentType : "application/json",
			            success : function(response)
			            {
				            if (response == null)
				            {
					            curvesEntity = undefined;
					            zoomCash[currentZoom] = undefined
					            _cities = undefined;
					            return;
				            }
				            _cities = response;
				            zoomCash[currentZoom] = _cities;
				            curves.redrawMarkers(response);
				            setTimeout(function()
				            {
					            $('#modal').hide();
				            }, 200);
			            },
			            error : function(response)
			            {
				            var s = "";
				            for (prop in response)
				            {
					            if (typeof response[prop] != "function")
					            {
						            s += "obj[" + prop + "] = "
						                    + response[prop] + "; ";
					            }
				            }
				            alert(s);
			            }
			        });

		}
		buffer = [];
	};
	//Копирует массив, но не копирует элементы
	AjaxRequestController.prototype.getCitiesArrayCopy = function getCitiesArrayCopy()
	{
		return [].concat(_cities);
	};

	return AjaxRequestController;
}