<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<% String path = request.getContextPath();  %>
<!--<div id="body">
	<div id="slider">
		<div class="aCity">
			<a id="city"></a>
			<label for="cityName">Название города</label><input type="text" disabled="disabled" value="" id="cityName" />
			<label for="cityArea">Площадь города</label><input type="text" disabled="disabled" value="" id="cityArea" />
			<label for="cityCordinate">Координаты(широта, долгота)</label><input type="text" disabled="disabled" value="," id="cityCordinate" />
			<label for="cityPopulation">Население города</label><input type="text" disabled="disabled" value="" id="cityPopulation" />
		</div>
		<div class="aCompany">
			<a id="company"></a>
			<label for=""></label><input type="text" disabled="disabled" value="" />
		</div>
		<div class="aEvent">
			<a id="event"></a>
			<label for=""></label><input type="text" disabled="disabled" value="" />
		</div>
		<div class="aTour">
			<a id="tour"></a>
			<label for=""></label><input type="text" disabled="disabled" value="" />
		</div>
	</div>
</div>-->
<div id="body">
	<input onclick="dropDB()" type="button" value="Drop Mongo"/>
	<table>
		<tr>
			<td>Шаг долготы</td><td><input id="stepLat" type="text" value="${stepLat}"/></td>
		</tr>
		<tr>
			<td>Шаг широты</td><td><input id="stepLng" type="text" value="${stepLng}"/></td>			
		</tr>
		<tr>
			<td colspan="2"><input onclick="recreateMongo()" type="button" value="Change Step"/></td>
		</tr>
	</table>
</div>

<script type="text/javascript">
 	var dropDB = function dropDB() {
		$.ajax({
			url: "admin/dmongo",
			type: "POST",
			data: { needDrop : true },
			success: function(response) {
				alert("Success");
			}
		});
	},
	recreateMongo = function recreateMongo() {
		$.ajax({
			url: "admin/rMongo",
			type: "POST",
			data: {
					stepLat : document.getElementById("stepLat").value,
					stepLng : document.getElementById("stepLng").value
				},
			success: function(response) {
				alert("Success");
			}
		});
	}
</script>