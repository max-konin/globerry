<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="${direction}" lang="${lang}">
<head>
<title>${title}</title>
<base href="${base}" />
<c:if test="${description}">
<meta name="description" content="${description}" />
</c:if>
<c:if test="${keywords}">
<meta name="keywords" content="${keywords}" />
</c:if>
<c:forEach items="${links}" var="link">
<link href="${link['href']}" rel="${link['rel']}" />
</c:forEach>
<link rel="stylesheet" type="text/css" href="../resources/styles/adminStyleSheet.css" />
<c:forEach items="${styles}" var="style">
<link rel="${style['rel']}" type="text/css" href="${style['href']}" media="${style['media']}" />
</c:forEach>
<script type="text/javascript" src="../resources/javascripts/jquery-1.7.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="../resources/javascripts/ui/themes/ui-lightness/jquery-ui-1.8.16.custom.css" />
<script type="text/javascript" src="../resources/javascripts/ui/external/jquery.bgiframe-2.1.2.js"></script>
<script type="text/javascript" src="../resources/javascripts/tabs.js"></script>
<script type="text/javascript" src="../resources/javascripts/superfish/js/superfish.js"></script>
<c:forEach items = "${scripts}" var = "script">
<script type="text/javascript" src="${script}"></script>
</c:forEach>
<script type="text/javascript">
//-----------------------------------------
// Confirm Actions (delete, uninstall)
//-----------------------------------------
$(document).ready(function(){
    // Confirm Delete
    $('#form').submit(function(){
        if ($(this).attr('action').indexOf('delete',1) != -1) {
            if (!confirm('${text_confirm}')) {
                return false;
            }
        }
    });
    	
    // Confirm Uninstall
    $('a').click(function(){
        if ($(this).attr('href') != null && $(this).attr('href').indexOf('uninstall', 1) != -1) {
            if (!confirm('${text_confirm}')) {
                return false;
            }
        }
    });
});
</script>
</head>
<body>
<div id="container">
<div id="header">
  <div class="div1">
    <div class="div2"><img src="../resources/image/logo.png" title="${heading_title}" onclick="location = '${home}'" /></div>
    <c:if test="${logged}">
    	<div class="div3"><img src="../resources/image/lock.png" alt="" style="position: relative; top: 3px;" />&nbsp;${loggedName}</div>
    </c:if>
  </div>
  <c:if test="${logged}">
  <div id="menu">
    <jsp:include page="../utils/dropdown_menu/dropdown_menu.jsp" />
    <script type="text/javascript"><!--
$(document).ready(function() {
	$('#menu > ul').superfish({
		hoverClass	 : 'sfHover',
		pathClass	 : 'overideThisToUse',
		delay		 : 0,
		animation	 : {height: 'show'},
		speed		 : 'normal',
		autoArrows   : false,
		dropShadows  : false, 
		disableHI	 : false, /* set to true to disable hoverIntent detection */
		onInit		 : function(){},
		onBeforeShow : function(){},
		onShow		 : function(){},
		onHide		 : function(){}
	});
	
	$('#menu > ul').css('display', 'block');
});
    
function getURLVar(urlVarName) {
	var urlHalves = String(document.location).toLowerCase().split('?');
	var urlVarValue = '';
	
	if (urlHalves[1]) {
		var urlVars = urlHalves[1].split('&');

		for (var i = 0; i <= (urlVars.length); i++) {
			if (urlVars[i]) {
				var urlVarPair = urlVars[i].split('=');
				
				if (urlVarPair[0] && urlVarPair[0] == urlVarName.toLowerCase()) {
					urlVarValue = urlVarPair[1];
				}
			}
		}
	}
	
	return urlVarValue;
} 

$(document).ready(function() {
	route = getURLVar('route');
	
	if (!route) {
		$('#dashboard').addClass('selected');
	} else {
		part = route.split('/');
		
		url = part[0];
		
		if (part[1]) {
			url += '/' + part[1];
		}
		
		$('a[href*=\'' + url + '\']').parents('li[id]').addClass('selected');
	}
});
//--></script> 
  </div>
  </c:if>
</div>
<jsp:include page="adminBodyPage.jsp" />
<jsp:include page="adminFooterPage.jsp" />
