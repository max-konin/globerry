<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<% 
	String appendAddress = "";
	if (request.getAttribute("Operation") == "Add")
		appendAddress = "../appendAddTour";
	else if (request.getAttribute("Operation") == "Edit")
		appendAddress = "../appendEditTour";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><spring:message code="label.title" /></title>
</head>
<body>
	<c:if test='${Operation == "Add"}'>
		<H1>Добавление тура</H1>
	</c:if>
	<c:if test='${Operation == "Edit"}'>
		<H1>Редактирование тура</H1>
	</c:if>
	<form:form action="<%=appendAddress %>"	method="post">
		
		<table>
			<tr>
				<td><label for="tourName">Название тура: </label></td>
				<td><input id="tourName" name="tourName" type="text"  
					<c:if test='${Operation == "Edit"}'>
						Value=${Name }
					</c:if> />
				</td>
			</tr>
			<tr>
				<td><label for="tourCost">Цена тура: </label></td>
				<td><input id="tourCost" name="tourCost" type="text"
					<c:if test='${Operation == "Edit"}'>
							Value=${Cost}
						</c:if> />
				</td>
			</tr>
			<tr>
				<td><label for="tourStartDate">Дата начала тура (ГГГГ-ММ-ДД): </label></td>
				<td><input id="tourStartDate" name="tourStartDate" type="text"
					<c:if test='${Operation == "Edit"}'>
						Value=${StartDate }
					</c:if> />
				</td>
			</tr>
			<tr>
				<td><label for="tourEndDate">Дата окончания тура (ГГГГ-ММ-ДД): </label></td>
				<td><input id="tourEndDate" name="tourEndDate" type="text"
					<c:if test='${Operation == "Edit"}'>
						Value=${EndDate }
					</c:if> />
				</td>
			</tr>
			<tr>
				<td><label for="tourDesription">Описание тура: </label></td>
				<td><input id="tourDesription" name="tourDesription" type="text"
					<c:if test='${Operation == "Edit"}'>
						Value=${Description }
					</c:if> />
				</td>
			</tr>
		</table>
		<c:if test='${Operation == "Add"}'>
			<input  type="submit" value="Добавить тур"/>
		</c:if>
		<c:if test='${Operation == "Edit"}'>
			<input id="tourID" name="tourID" type="hidden" value="${Tourid}" >
			<input  type="submit" value="Применить"/>
		</c:if>
		
	</form:form>
</body>
</html>