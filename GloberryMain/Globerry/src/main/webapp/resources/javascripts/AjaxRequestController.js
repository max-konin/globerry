/* 
 * Autor MaxG
 */
var AjaxRequestController = new function()
{
	var instance;

	// Приватные методы и свойства
	// ...
        var _cities = [];
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
	AjaxRequestController.prototype.sendRequest = function sendRequest(id, data)
	{
		var request = [
		{
			id : id,
			value : data
		} ];
		$('#modal').show();
		console.log(request);
		$.ajax(
		{
			url : path + '/gui_changed_new',
			dataType : 'json',
			type : 'POST',
			data : JSON.stringify(request),
			contentType : "application/json",
			success : function(response)
			{
				_cities = response;
				curves.redrawCurvesNew(_cities);
//				(new Bottom).close();
//				curves.redrawCurves(response);
//				setTimeout(function()
//				{
//					$('#modal').hide();
//				}, 200);
			},
			error : function(response)
			{
				var s = "";
				for (prop in response)
				{
					if (typeof response[prop] != "function")
					{
						s += "obj[" + prop + "] = " + response[prop] + "; ";
					}
				}
				alert(s);
			}
		});
	};
        //Копирует массив, но не копирует элементы
        AjaxRequestController.prototype.getCitiesArrayCopy = function getCitiesArrayCopy()
	{
            return [].concat(_cities);
        };

	return AjaxRequestController;
}