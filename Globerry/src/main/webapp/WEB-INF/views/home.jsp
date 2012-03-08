<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<HTML>
<HEAD>
<META NAME="GENERATOR" Content="Microsoft Visual Studio 8.0">
<TITLE>Globerry 0.0.2</TITLE>

<script type="text/javascript" src="javascripts/raphael.js"></script>
<script type="text/javascript" src="javascripts/jquerry.js"></script>
<script type="text/javascript" src="javascripts/test_rph_d.js"></script>
<script type="text/javascript" src="http://openlayers.org/api/OpenLayers.js"></script>

<style type="text/css">  
    
       body, html 
        {
    	    height:100%;
            margin:0px;
            padding:0px;
         }
        #div_container
	   {
	   	width:100%;
	   	height : 100%; 
	   	position : relative;
	   	/*background-color:#333;*/
	   	}
        #header
	   {
	   	width:100%;
	   	height : 7%; 
	   	position : absolute;
	   	}
	   .bottom
	   {
	   	bottom :0px;
	   	left:30%;
	   	width:70%;
	   	height : 27.1%; 
	   	position : absolute;
	   	float:left;
	   	/*background-color:#000;*/
	   	
	   	background: #ffffff; /* Для старых браузров */
        background: -moz-linear-gradient(left, #ffffff, #f0f0f0); /* Firefox 3.6+  fefcea */
        background: -webkit-linear-gradient(left, #ffffff, #f0f0f0);
        background: -o-linear-gradient(left, #ffffff, #f0f0f0); /* Opera 11.10+ */
        background: -ms-linear-gradient(left, #ffffff, #f0f0f0); /* IE10 */
        background: linear-gradient(left, #ffffff, #f0f0f0); /* CSS3 */ 

	   	}
	   #month
	   {
	   	bottom :34%;
	   	left:30%;/**/
	   	width:70%;
	   	height : 2%; 
	   	position : absolute;
	   	float:left;
	   	/*background-color:#def;*/
	   	}

	   #bottom_switcher
	   {
	   	bottom :27%;
	   	left:30%;/**/
	   	width:70%;
	   	height : 7%; 
	   	position : absolute;
	   	float:left;
	   	background-color:#def;
	   	}

	   #line
	   {
	   	bottom :27%;
	   	left:30%;/**/
	   	width:70%;
	   	height : 4%; 
	   	position : absolute;
	   	float:left;
	   	background-color:#def;
	   	}

        #left
	   {
	   	top: 7%;
	   	width:30%;
	   	height : 92%; 
	   	position : absolute;
	   	/*background-color:#9cf;*/
	   	background: #ffffff; /* Для старых браузров */
        background: -moz-linear-gradient(left, #ffffff, #f0f0f0); /* Firefox 3.6+  fefcea */
        background: -webkit-linear-gradient(left, #ffffff, #f0f0f0);
        background: -o-linear-gradient(left, #ffffff, #f0f0f0); /* Opera 11.10+ */
        background: -ms-linear-gradient(left, #ffffff, #f0f0f0); /* IE10 */
        background: linear-gradient(left, #ffffff, #f0f0f0); /* CSS3 */ 

	   	}
        #map
	   {
	   	top: 7%;
	   	left:30%;
	   	width:70%;
	   	height : 58%; 
	   	position : absolute;
	   	background-color:#eee;
	   	/*background-color:#333;*/
	   	}
</style> 

<style>
   .gradient {
	   	background: #ffffff; /* Для старых браузров */
        background: -moz-linear-gradient(left, #ffffff, #f0f0f0); /* Firefox 3.6+  fefcea */
        background: -webkit-linear-gradient(left, #ffffff, #f0f0f0);
        background: -o-linear-gradient(left, #ffffff, #f0f0f0); /* Opera 11.10+ */
        background: -ms-linear-gradient(left, #ffffff, #f0f0f0); /* IE10 */
        background: linear-gradient(left, #ffffff, #f0f0f0); /* CSS3 */ 

   }
  
</style>


</HEAD>
<BODY height='100%', class="gradient">
<div id='div_container'>

<div id='header'></div>
<div id='left'>
<div id='div1'></div>
</div>
<div id='map'></div>
<div id='line'></div>
<div id ='month'></div>
<div id='bottom_switcher'></div>
<div id='bottom_auto' , class ='bottom'><p>auto</p></div>
<div id='bottom_avia' , class ='bottom'><p>avia</p></div>
<div id='bottom_hotel' , class ='bottom'><p>hotel</p></div>
<div id='bottom_tour' , class ='bottom'><p>tour</p></div>

</div>
</BODY>

</HTML>
