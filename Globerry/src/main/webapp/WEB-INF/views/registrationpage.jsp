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
	<script src="http://documentcloud.github.com/underscore/underscore-min.js"></script>
	<title>Insert title here</title>
</head>
<body>
	<div>
		<div class="header">
			<h1> Globerry - registration </h1>
		</div>
		<div class="body">
			<form:form method="post" name="registration">
				<table>
					<tbody align="right">
						<tr>
							<td><label for="name">Name</label></td>
							<td><input type="text" id="name" name="name" value="${Name}"/></td>
							<td align="left"><p class="error">${errorList.get("name")}</p></td>
						</tr>
						<tr>
							<td><label for="e-mail">Email</label></td>
							<td><input name="e-mail" id="e-mail" type="text" value="${Email}"/></td>
							<td align="left"><p class="error">${errorList.get("email")}</p></td>
						</tr>
						<tr>
							<td><label for="pass">Password</label></td>
							<td><input type="password" id="pass" name="pass"/></td>
							<td align="left"><p class="error">${errorList.get("password")}</p></td>
						</tr>
						<tr>
							<td><label for="cPass">Confirm password</label></td>
							<td><input name="cPass" id="cPass" type="password"/></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="2"><input onclick="submit()" type="submit" value="Registration"></td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</form:form>
		</div>
	</div>
	<div class="footer">
	</div>
	<c:if test="${!empty success}">
		<div style="position: absolute; right: 10px; top: 10px; float: right;">
			<p>---------------------------------------------------------------------------------</p>
			<p>${success}</p>
			<p>---------------------------------------------------------------------------------</p>
		</div>
	</c:if>
	<script type="text/javascript">
	putErrorForName_f = function() {
		$.ajax({
		    url: "<%=path%>/auth/ajax",
	    	dataType : "post",
		    data: { name : $("input[name='name']").val() },
		    type: "post",
		    success: function (data) {
		    	$("input[name='name']").parent().next().children("p.error").html(data);
		    },
			error: function (error) {
				$("input[name='name']").parent().next().children("p.error").html("Loading...");
				setTimeout(putErrorForName(), 1301);
			}
		});
	};
	
	var putErrorForName = _.throttle(putErrorForName_f, 1300);
	
	$(document).ready(
		function() {
			$("form[name='registration']").attr('action', 'registration');
			
			$("input[name='pass']").focusout(function() {
				if($(this).val() == "") {
					$("input[name='pass']").parent().next().children("p.error").html("Please fill password.");
					return false;
				}
				if($("input[name='cPass']").val() != "" && $(this).val() != $("input[name='cPass']").val()) {
					$("input[name='pass']").parent().next().children("p.error").html("Confirming password failed.");
				} else {
					$("input[name='pass']").parent().next().children("p.error").html("");
				}
			});
			
			$("input[name='cPass']").focusout(function() {
				if($("input[name='pass']").val() != $(this).val()) {
					$("input[name='pass']").parent().next().children("p.error").html("Confirming password failed.");
				} else {
					$("input[name='pass']").parent().next().children("p.error").html("");
				}
			});
			
			$("input[name='e-mail']").focusout(function() {
				var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
				if(this.value == "")
					return false;
				if(filter.test(this.value)) {
					$(this).parent().next().children("p.error").html("");
				} else {
					$(this).parent().next().children("p.error").html("Error in email. Please fix it.");
				}
			});
			
			$("input[name='name']").focusout(function(){
				if(this.value != "")
					putErrorForName();
				});
		}
	);
	</script>
</body>
</html>