<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html">
	<title><spring:message code="label.title" /></title>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="../resources/javascripts/superfish/js/superfish.js"></script>
</head>
<body>
<jsp:include page="../utils/dropdown_menu/dropdown_menu.jsp"/>
	<script type="text/javascript">
	<!--
		$(document).ready(function() {
			$('.${menu.getName()}').superfish({
				hoverClass : 'sfHover',
				pathClass : 'overideThisToUse',
				delay : 0,
				animation : {
					height : 'show'
				},
				speed : 'normal',
				autoArrows : false,
				dropShadows : false,
				disableHI : false, /* set to true to disable hoverIntent detection */
				onInit : function() {
				},
				onBeforeShow : function() {
				},
				onShow : function() {
				},
				onHide : function() {
				}
			});

			$('.${menu.getName()}').css('display', 'block');
		});
	</script>
</body>