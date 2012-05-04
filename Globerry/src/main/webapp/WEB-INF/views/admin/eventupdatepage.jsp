<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form:form method="post" action="update" commandName="event">

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
</body>
</html>