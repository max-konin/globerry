<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<% 
	String path = request.getContextPath().toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.3/jquery.min.js"></script>
<title>Insert title here</title>
</head>
<body>

<h1> Globerry - registration </h1>

<form:form method="post" action="registration" name="registration">
<table>
	<tbody align="right">
		<tr>
			<td>Name</td>
			<td><input type="text" name="name" value="${Name}"/></td>
			<td align="left"><p class="error">${errorList.get("name")}</p></td>
		</tr>
		<tr>
			<td>Email</td>
			<td><input name="e-mail" type="text" value="${Email}"/></td>
			<td align="left"><p class="error">${errorList.get("email")}</p></td>
		</tr>
		<tr>
			<td>Password</td>
			<td><input type="password" name="pass"/></td>
			<td align="left"><p class="error">${errorList.get("password")}</p></td>
		</tr>
		<tr>
			<td>Confirm password</td>
			<td><input name="cPass" type="password"/></td>
			<td></td>
		</tr>
		<tr>
			<td colspan="2"><input onclick="submit()" type="button" value="Registration"></td>
			<td></td>
		</tr>
	</tbody>
</table>
</form:form>
<c:if test="${!empty success}">
	<div style="position: absolute; right: 10px; top: 10px; float: right;">
		<p>-------------------------------------------------------------------</p>
		<p>${success}</p>
		<p>-------------------------------------------------------------------</p>
	</div>
</c:if>
<script type="text/javascript">
submit = function() {
	if(!hasError)
		$("form[name='registration']").submit();
	else
		return false;
};

$(document).ready(
	function() {
		var hasError = false;
		
		$("input[type='password']").focusout(function() {
			
		});
		
		$("input[name='e-mail']").focusout(function() {
			var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
			if(!filter.test(this.value) && this.value != "" && !hasError) {
				$(this).parent().next().children("p.error").html("Error in email. Please fix it.");
				hasError = true;
			} else {
				$(this).parent().next().children("p.error").html("");
				hasError = false;
			}
		});
		
		$("input[name='name']").focusout(function(){
			if(this.value != "")
				$.ajax({
				    url: "<%=path%>/auth/ajax",
			    	dataType : "post",
				    data: { name : this.value },
				    type: "post",
				    success: function (data) {
				        $("input[name='name']").parent().next().children("p.error").html(data);
				        if(data != "" && !hasError)
				        	hasError = false;
				        else
				        	hasError = true;
				        alert("All ok.");
				    },
					error: function (error) {
						$("input[name='name']").parent().next().children("p.error").html(error);
						alert("Error");
					}
				});
			});
	});
</script>
</body>
</html>