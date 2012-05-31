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
	<script type="text/javascript" src="http://documentcloud.github.com/underscore/underscore-min.js"></script>
	<script type="text/javascript" src="../resources/javascripts/jquery.wresize.js"></script>
	<script type="text/javascript" src="../resources/javascripts/underscore.js"></script>
	<link rel="stylesheet" href="../resources/styles/registration.css" type="text/css" />
	<title>Insert title here</title>
</head>
<body>
	<div id="registration">
		<jsp:include page="common/header.jsp" />
		<div id="body">
			<img alt="Globerry - лучший способ провести отпуск!" src="../resources/img/funPic.png">
			<c:if test="${empty success}">
			<form:form method="post" name="registration">
				<table>
					<tbody align="right">
						<tr>
							<td><label for="name">Name</label></td>
							<td><input type="text" id="name" name="name" value="${Name}"/></td>
							<td align="left"><p class="error">${errorList.get("name")}</p></td>
						</tr>
						<tr>
							<td><label for="email">Email</label></td>
							<td><input name="email" id="email" type="text" value="${Email}"/></td>
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
			</c:if>		
			<c:if test="${!empty success}">
				<div>
					<p>${success}</p>
				</div>
			</c:if>
		</div>
		<div id="footer">
		</div>
	</div>
	<script type="text/javascript">
	putErrorForVariable_f = function(variable) {
		var hasError = null;
		$.ajax({
		    url: "<%=path%>/auth/ajax",
	    	dataType : "post",
		    data: variable + "=" + $("input[name='" + variable + "']").val(),
		    type: "post",
		    success: function (data) {
		    	$("input[name='" + variable + "']").parent().next().children("p.error").html(data);
		    	hasError = false;
		    },
			error: function (error) {
				$("input[name='" + variable + "']").parent().next().children("p.error").html("Loading...");
				console.log("error");
				hasError = true;
				setTimeout(putErrorForVariable(variable), 1301);
			}
		});
		return hasError;
	};
	
	resizeWindow = function() {
		$("div.registration").css({ width : ($(window).width() < 1000) ? 1000 : $(window).width(),
			height : ($(window).height() < 640) ? 640 : $(window).height() });
		$("body").find("img").css({ width : $(window).width() - 730 });
	};
	
	var _putErrorForVariable = _.throttle(putErrorForVariable_f, 1300);
	
	$(document).ready(
		function() {
			$("div.registration").css({ width : ($(window).width() < 1000) ? 1000 : $(window).width(),
				height : ($(window).height() < 640) ? 640 : $(window).height() });
			$("body").find("img").css({ width : $(window).width() - 730 });
			
			$(window).wresize(resizeWindow);
			
			$("form[name='registration']").attr('action', 'registration');
			
			$("input[name='pass']").focusout(function() {
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
			
			$("input[name='email']").focusout(function() {
				var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
				if(this.value == "")
					return false;
				if(filter.test(this.value)) {
					$(this).parent().next().children("p.error").html("");
					_putErrorForVariable("email");
				} else {
					$(this).parent().next().children("p.error").html("Error in email. Please fix it.");
					return false;
				}
			});
			
			$("input[name='name']").focusout(function(){
				if(this.value != "")
					_putErrorForVariable("name");
				});
		}
	);
	</script>
</body>
</html>