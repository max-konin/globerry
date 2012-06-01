<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form:form method="post" action="updateEvent" commandName="event">

	<table>
		<tr style="display: none">
			<td ><form:label path="id">

			</form:label></td> 
			<td><form:input path="id"/></td>
		
		</tr>
		<tr>
			<td><form:label path="name">
				<spring:message code="label.event_name" />
			</form:label></td>
			<td><form:input path="name" /></td>
		</tr>
		<tr>
			<td><form:label path="ru_name">
				<spring:message code="label.event_ru_name" />
			</form:label></td>
			<td><form:input path="ru_name" /></td>
		</tr>
		<tr>
			<td><form:label path="description">
				<spring:message code="label.event_description" />
			</form:label></td>
			<td><form:input path="description" /></td>
		</tr>
		<tr>
			<td><form:label path="ru_description">
				<spring:message code="label.event_ru_description" />
			</form:label></td>
			<td><form:input path="ru_description" /></td>
		</tr>
		<tr>
			<td><form:label path="image">
				<spring:message code="label.event_image" />
			</form:label></td>
			<td><form:input path="image" /></td>
		</tr>
		<tr>
			<td><form:label path="month">
				<spring:message code="label.event_month" />
			</form:label></td>

			<td> <form:select path="month" size="1">
  					<form:options items="${months}"/>
				</form:select>
			</td>
		

		</tr>
		
	</table>
 	<c:if test="${!empty cityList}">
		<table class="data">
			<tr>
				<th><spring:message code="label.id" /></th>
				<th><spring:message code="label.name" /></th>
			</tr>
			<c:forEach items="${cityList}" var="cities">
				<tr>
					<td>${cities.id}</td>
					<td>${cities.name}</td>
					<td><a href="delete/${cities.id}"><spring:message code="label.delete" /></a></td>
				</tr>
			</c:forEach>
	
		</table>
	</c:if> 
	<input type="submit"
				value="<spring:message code="label.addevent"/>" />
</form:form>
	<form:form method="get" action="join/city" commandName="event">
		<form:select path="id">
			<form:option value="0" label="Select" />
			<form:options items="${allCities}" itemValue="id" itemLabel="name" />
		</form:select>
		<input type="submit"
				value="<spring:message code="label.addevent"/>" />
</form:form>
<%-- 			<td> <form:select path="city" size="10">
					<c:if test="${!empty allCities}">
							<c:forEach items="${allCities}" var="allcities">
									<form:option value="${allcities.name}"/>
  							</c:forEach>
  					</c:if>
				</form:select>
			<c:forEach items="${allCities}" var="allcities">
									<form:option value="${allcities.name}"/>
  							</c:forEach> --%>

</body>
</html>