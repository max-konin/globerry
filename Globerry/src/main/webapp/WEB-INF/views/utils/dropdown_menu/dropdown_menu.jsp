<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul <c:if test="${!empty menu.getName()}">class="${menu.getName()}"</c:if> <c:if test="${!empty menu.getId()}">id="${menu.getId()}"</c:if>>
	<c:forEach var="node" items="${menu.getRootElement().getChildren()}">
		<c:set var="node" value="${node}" scope="request"/>
		<jsp:include page="node.jsp"/>
	</c:forEach>
</ul>
