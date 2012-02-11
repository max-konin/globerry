<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<HTML>
<HEAD>
<META NAME="GENERATOR" Content="Microsoft Visual Studio 8.0">
<TITLE>Globerry 0.0.2</TITLE>

<script type="text/javascript" src="<c:url value="resources/javascripts/raphael.js" />"></script>
<script type="text/javascript" src="resources/javascripts/test_rph.js"></script>

<style type="text/css">  
    
       body, html 
        {
    	    height:100%;
            margin:0px;
            padding:0px;
         }
       #canvas_container   
       {
            width: 100%;
	        height: 99%;
	        position:absolute;
	        /*background-color:White;*/
	        background-color: #ffc;
	        border-color:#333;
	        float: left;           
	   }  
	   #Button_div
	   {
	        position:absolute;
	        /*background-color:White;*/
	        //background-color: #ffc;
	        //border-color:#333;
	        float: left;           
	   	}
        </style>  
</HEAD>
<BODY height='100%'>

<div id='div_container'>
<div id='canvas_container'></div>
<div id='Button_div'><input type="button" id="Button_1" name="press" value="  Соединить  "></div>

</div>

</BODY>

</HTML>
