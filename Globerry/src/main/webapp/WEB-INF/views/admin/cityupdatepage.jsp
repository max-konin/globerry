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
<form:form method="post" action="update" commandName="city">

	<table>
		<tr>
			<td><form:label path="name">
				<spring:message code="label.name" />
			</form:label></td>
			<td><form:input path="name" /></td>
		</tr>
		<tr>
			<td><form:label path="area">
				<spring:message code="label.area" />
			</form:label></td>
			<td><form:input path="area" /></td>
		</tr>
		<tr>
			<td><form:label path="population">
				<spring:message code="label.population" />
			</form:label></td>
			<td><form:input path="population" /></td>
		</tr>
		<tr>
			<td><form:label path="ru_name">
				<spring:message code="label.ru_name" />
			</form:label></td>
			<td><form:input path="ru_name" /></td>
		</tr>
		<tr>
			<td><form:label path="longitude">
				<spring:message code="label.longitude" />
			</form:label></td>
			<td><form:input path="longitude" /></td>
		</tr>
		<tr>
			<td><form:label path="latitude">
				<spring:message code="label.latitude" />
			</form:label></td>
			<td><form:input path="latitude" /></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit"
				value="<spring:message code="label.addcontact"/>" /></td>
		</tr>
	</table>
</form:form>
</body>
</html>