<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div id="body">
	<div id="slider">
		<div class="aCityList">
			<a id="cityList"></a>
			<c:forEach items="${cityList}" var="city">
			<label for="cityName"></label><input type="text" disabled="disabled" value="${city.getName()}">
			</c:forEach>
		</div>
		<div class="aCity">
			<a id="city"></a>
			<label for="cityArea"></label><input type="text" disabled="disabled" value="" id="cityArea" />
			<label for="cityName"></label><input type="text" disabled="disabled" value="" id="cityName" />
			<label for="cityId"></label><input type="text" disabled="disabled" value="" id="cityId" />
			<label for="cityCordinate"></label><input type="text" disabled="disabled" value="," id="cityCordinate" />
			<label for="cityPopulation"></label><input type="text" disabled="disabled" value="" id="cityPopulation" />
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
</div>
