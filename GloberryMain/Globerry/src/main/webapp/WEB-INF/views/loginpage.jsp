<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form:form action="../j_spring_security_check" method="post" >
	<label for="j_username">Username</label><input id="j_username" name="j_username" type="text" />
	<label for="j_password">Password</label><input id="j_password" name="j_password" type="password" />
	<input  type="submit" value="Login"/>
</form:form>

<div id="login-error">${error}</div>