<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<li>${node.getName()}
<c:forEach var="node" items="${node.getChildren()}">
	<ul>
		<c:set var="node" value="${node}" scope="request"/>
		<jsp:include page="node.jsp"/>
	</ul>
</c:forEach>
</li>