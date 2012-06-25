<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><spring:message code="label.title" /></title>
</head>
<body>
	<table>
		<tr>
			<td><H1 align="center">${Name}</H1></td>
		</tr>
	</table>
	<a href="add/${tour.id}">Добавить новый тур</a>
	<p>Ваши туры (${TourList.size()}):</p>
	<table class="TourTable" border=1>
		<c:forEach items="${TourList}" var="tour">
			<tr>
				<td>${tour.name}</td>
				<td>${tour.cost}</td>
				<td>${tour.dateStart}</td>
				<td>${tour.dateEnd}</td>
				<td>${tour.description}</td>
				<td><a href="delete/${tour.id}"><spring:message code="label.delete" /></a></td>
				<td><a href="edit/${tour.id}">Edit</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>