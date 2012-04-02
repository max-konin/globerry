<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html"; charset="utf8">
	<title><spring:message code="label.title" /></title>
</head>
<body>

<a href="<c:url value="/logout" />">
	<spring:message code="label.logout" />
</a>
  
<h2><spring:message code="label.title" /></h2>

<h3><spring:message code="label.cities" /></h3>
<c:if test="${!empty cityList}">
	<table class="data">
		<tr>
			<th><spring:message code="label.name" /></th>
			<th><spring:message code="label.email" /></th>
			<th><spring:message code="label.login" /></th>
			<th><spring:message code="label.password" /></th>
			<th>&nbsp;</th>
		</tr>
		<c:forEach items="${cityList}" var="city">
			<tr>
				<td>${city.name}</td>
				<td>${city.area}</td>
				<td>${city.population}</td>
				<td>${city.ru_name}</td>
				<td>${city.longitude}</td>
				<td>${city.latitude}</td>
				<td><a href="cityadminpage/delete/${city.id}"><spring:message code="label.delete" /></a></td>
			</tr>
		</c:forEach>
	</table>
</c:if>

</body>
</html>