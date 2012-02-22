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
<div id='Button_div'>    
<input type="button" id="Button_1" name="press" value="  Соединить  ">
<select id="level_list" onchange = alert("123");)>
    <option value =0.1> 0.1</option>
    <option value =0.2> 0.2</option>
    <option value =0.3> 0.3</option>
    <option value =0.4> 0.4</option>
    <option value =0.5> 0.5</option>
    <option value =0.6> 0.6</option>
    <option value =0.7> 0.7</option>
    <option value =0.8> 0.8</option>
    <option value =0.9> 0.9</option>
    <option value =1> 1</option>
    <option value =1.1> 1.1</option>
    <option value =1.2> 1.2</option>
    <option value =1.3> 1.3</option>
    <option value =1.4> 1.4</option>
    <option value =1.5> 1.5</option>
    <option selected value =1.6> 1.6</option>
    <option value =1.7> 1.7</option>
    <option value =1.8> 1.8</option>
    <option value =1.9> 1.9</option>
    <option value =2.0> 2.0</option>
    <option value =2.1> 2.1</option>
    <option value =2.2> 2.2</option>
    <option value =2.3> 2.3</option>
    <option value =2.4> 2.4</option>
    <option value =2.5> 2.5</option>
    <option value =2.6> 2.6</option>
    <option value =2.7> 2.7</option>
    <option value =2.8> 2.8</option>
    <option value =2.9> 2.9</option>
    <option value =3.0> 3.0</option>
    <option value =3.1> 3.1</option>
    <option value =3.2> 3.2</option>
    <option value =3.3> 3.3</option>

    </select>
</div>

</div>

</BODY>

</HTML>