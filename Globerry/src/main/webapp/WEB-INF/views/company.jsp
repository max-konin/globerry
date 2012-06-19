<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%> 
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
</head>
<body>


	<table>
		<tr>
		
			<td>${locale.getDisplayCountry()}</td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit"
				value="Кнопка" /></td>
		</tr>
		<tr>
		
			<td>${FIRSTHASH}</td>
		</tr>
		<tr>
		
			<td>${SECONDHASH}</td>
		</tr>
                <tr>
		
			<td>${hash}</td>
		</tr>
		
	</table>


<h3>	${locale.getLanguage()}</h3>
<h3><spring:message code="label.logout" /></h3>


<button onclick="test_slider()">
   Test Slider Values
</button>
<button onclick="test_input()">
   Test Input Values
</button>
<button onclick="test_array()">
   Test Array
</button>
<spring:url value="/some/link"/>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script>
    
    function test_slider() {
        var request = {id : '8', value : {leftValue : 10, rightValue: 20}};
        $.ajax({
            url: '/feature_test',
            dataType: 'json',
            type: 'POST',
            data: JSON.stringify(request),
            contentType: "application/json",
            success: function (response) {
                alert("OK");
            }
        });
    }
    function test_input() {
        var request = {id : '3', value : {value : 3}};
        $.ajax({
            url: '/feature_test',
            dataType: 'json',
            type: 'POST',
            data: JSON.stringify(request),
            contentType: "application/json",
            success: function (response) {
                alert("OK");
            }
        });
    }
    function test_array() {
        var request = [{id : '3', value : {value : 6}}, {id : '8', value : {leftValue : 10, rightValue: 20}}];
        $.ajax({
            url: '/gui_changed',
            dataType: 'json',
            type: 'POST',
            data: JSON.stringify(request),
            contentType: "application/json",
            success: function (response) {
                alert("OK");
            }
        });
    }
</script>
</html>