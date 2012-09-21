<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin update page:city</title>
</head>
<body>
<form:form method="post" action="updateCity" commandName="city">

	<table>
		<tr style="display: none">
			<td ><form:label path="id">

			</form:label></td> 
			<td><form:input path="id"/></td>
		
		</tr>
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
	<c:if test="${!empty eventList}">
		<table class="data">
			<thead>
				  <tr>
  						 <td> Event </td>
				  </tr>
			</thead>
			<tr>
				<th><spring:message code="label.id" /></th>
				<th><spring:message code="label.name" /></th>
			</tr>
			<c:forEach items="${eventList}" var="events">
				<tr>
					<td>${events.id}</td>
					<td>${events.name}</td>
					<td><a href="delete/${events.id}"><spring:message code="label.delete" /></a></td>
				</tr>
			</c:forEach>
	
		</table>
	</c:if>
	<c:if test="${!empty dmpList}">
		<table class="data">
			<thead>
				  <tr>
  						 <td> Depending Month Property </td>
				  </tr>
			</thead>
			<tr>
				<th><spring:message code="label.id" /></th>
				<th><spring:message code="label.name" /></th>
				<th><spring:message code="label.name" /></th>
				<th><spring:message code="label.property" /></th>
			</tr>
			<c:forEach items="${dmpList}" var="dmp">
				<tr>
					<td>${dmp.id}</td>
					<td>${dmp.month}</td>
					<td>${dmp.value}</td>
					<td>${dmp.propertyType.name} </td> 
					<td><a href="delete/${dmp.id}"><spring:message code="label.delete" /></a></td>
				</tr>
			</c:forEach>
	
		</table>
	</c:if>
	<c:if test="${!empty propertyList}">

		<table class="data">
			<thead>
				  <tr>
  						 <td> Property </td>
				  </tr>
			</thead>
			<tr>
				<th><spring:message code="label.id" /></th>
				<th><spring:message code="label.name" /></th>
			</tr>
			<c:forEach items="${propertyList}" var="property">
				<tr>
					<td>${property.id}</td>
					<td>${property.value}</td>
					<td>${property.propertyType.name}</td>
					<td><a href="delete/${property.id}"><spring:message code="label.delete" /></a></td>
				</tr>
			</c:forEach>
	
		</table>
	</c:if>
		<c:if test="${!empty tagList}">
		<table class="data">
			<thead>
				  <tr>
  						 <td> Tag </td>
				  </tr>
			</thead>
			<tr>
				<th><spring:message code="label.id" /></th>
				<th><spring:message code="label.name" /></th>
			</tr>
			<c:forEach items="${tagList}" var="tag">
				<tr>
					<td>${tag.id}</td>
					<td>${tag.name}</td>
					<td><a href="delete/${tag.id}"><spring:message code="label.delete" /></a></td>
				</tr>
			</c:forEach>
	
		</table>
	</c:if>
</form:form>
</body>
</html>