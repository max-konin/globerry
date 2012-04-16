<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html"; charset="utf8">
	<title><spring:message code="label.title" /></title>
	<script type="text/javascript" src="../resources/javascripts/jquerry.js"></script>
	<script type="text/javascript" src="../resources/javascripts/kendoui/source/js/kendo.web.js"></script>
    <link href="../resources/javascripts/kendoui/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="../resources/javascripts/kendoui/styles/kendo.default.min.css" rel="stylesheet" />
    <script language="javascript">
    	function getJsp()
    	{
    		
    	}
    </script>
</head>
<body>

<a href="<c:url value="/logout" />">
	<spring:message code="label.logout" />
</a>

  
<h2><spring:message code="label.title" /></h2>
  <div id="example" class="k-content">
            <div id="clientsDb">

                <div id="grid"></div>

            </div>

            <style scoped>
                #clientsDb {
                    width: 692px;
                    height: 393px;
                    margin: 30px auto;
                    padding: 51px 4px 0 4px;
                  
                }
            </style>
			<script language ="javascript">
	                $(document).ready(function() {
	                	
	                	
	                	/*events_group = $.getJSON("getevents",
                	            function(response) 
                	            {
                        				var events = [];	
                        				/*$.each(response, function(key, val)
   	    										{
   	    											var id = val.id;
   	    											var description = val.description;
   	    											var image = val.image;
   	    											var month = val.month;
   	    											var name = val.name;
   	    											var ru_description = val.ru_description;
   	    											var ru_name = val.ru_name;
   	    											
   	    											events.push({
   	    												Id: id,
   	    												Description: description,
   	    												Image: image,
   	    												Month: month,
   	    												Name: name,
   	    												Ru_description: ru_description,
   	    												Ru_name: ru_name
   	    											});
   	    										});*/
   	    									dataSource = new kendo.data.DataSource({
                        					transport: {
                        						 read:  {
                                                     url: "getevents",
                                                     dataType: "jsonp"
                                                 },
                                                 update: {
                                                     url: "update",
                                                     dataType: "jsonp"
                                                 },
                                                 destroy: {
                                                     url: "delete",
                                                     dataType: "jsonp"
                                                 }, 
                                                 create: {
                                                     url: "Create",
                                                     dataType: "jsonp"
                                                 },
                                                 parameterMap: function(options, operation) {
                                                     if (operation !== "read" && options.models) {
                                                         return {models: kendo.stringify(options.models)};
                                                     }
                                                 } 
                        					},
                        					batch: true,
                                            pageSize: 30,
                        					schema: {
                                                model: {
                                                    id: "id",
                                                    fields: {
                                                    	description: { validation: { required: true } },
                                                        image: { validation: { required: true } },
                                                        month: { type: "number", validation: { required: true, min: 0} },
                                                        name: {validation: { required: true } },
                                                        ru_description: { validation: { required: true } },
                                                        ru_name: {validation: { required: true } }
                                                    }
                                                }
                                            }
                                        });
   	    										//alert("ready");
   	    								/*$.each(response, function(key, val)
   	    										{
   	    											var id = val.id;
   	    											var description = val.description;
   	    											var image = val.image;
   	    											var month = val.month;
   	    											var name = val.name;
   	    											var ru_description = val.ru_description;
   	    											var ru_name = val.ru_name;
   	    											
   	    											events.push({
   	    												Id: id,
   	    												Description: description,
   	    												Image: image,
   	    												Month: month,
   	    												Name: name,
   	    												Ru_description: ru_description,
   	    												Ru_name: ru_name
   	    											});
   	    										});//*/
	   									$("#grid").kendoGrid(
	   						                    {
	   						                        dataSource: dataSource,
	   						                        	/* {
	   						                            data: events,
	   						                          	pageSize: 20
	   						                    	    }*/
	   						                        height: 500,
	   						                        scrollable: true,
	   						                        sortable: true,
	   						                        pageable: true,
	   						                        columns: [ 
	   						                             /*   /*  {
	   						                                field: "id",
	   						                           		} , 
	   						                           		{
	   						                                field: "description"
	   						                            	} , 
	   						                           		{
	   						                                field: "image"
	   						                            	} , 
	   						                            	{
	   						                                field: "month"
	   						                            	} , 
	   						                            	{
	   						                                field: "name"
	   						                           		} , 
	   						                           		{
	   						                                field: "ru_description"
	   						                            	} , */
	   						                            	{
	   							                            field: "image"
	   						                            	} ,
	   						                            	{ command: ["edit", "destroy"], title: "&nbsp;", width: "210px" }
	   						                        ],
	   						                     editable: "inline"
	   						                    });	
	   							 });	
	   										

	                	
	                /*	alert(events_group[3].Month);
	                	
	                    $("#grid").kendoGrid(
	                    		{
	                        dataSource: {
	                            data: $.getJSON("getevents",
	                    	            function(response) 
	                    	            {
	                            				var events = [];	
           	    								$.each(response, function(key, val)
           	    										{
           	    											var id = val.id;
           	    											var description = val.description ? val.description : "ok";
           	    											var image = val.image ? val.image : "ok";
           	    											var month = val.month ? val.month : "ok";
           	    											var name = val.name ? val.name : "ok";
           	    											var ru_description = val.ru_description ? val.ru_description : "ok";
           	    											var ru_name = val.ru_name ? val.ru_name : "ok";
           	    											
           	    											events.push({
           	    												Id: id,
           	    												Description: description,
           	    												Image: image,
           	    												Month: month,
           	    												Name: name,
           	    												Ru_description: ru_description,
           	    												Ru_name: ru_name
           	    											});
           	    											
           	    										
           	    											
           	    										});
           	    								
           	    								return events;
           	         						 }),
	                          	pageSize: 10
	                        },
	                        height: 360,
	                        groupable: true,
	                        scrollable: true,
	                        sortable: true,
	                        pageable: true,
	                        columns: [ 
	                                {
	                                field: "Id"
	                           		} , 
	                           		{
	                                field: "Description"
	                            	} , 
	                           		{
	                                field: "Image"
	                            	} , 
	                            	{
	                                field: "Month"
	                            	} , 
	                            	{
	                                field: "Name"
	                           		} , 
	                           		{
	                                field: "Ru_description"
	                            	} ,
	                            	{
		                            field: "Ru_name"
	                            	}
	                        ]
	                    });
	                });*/
	            </script>
	    </div>

<!-- 
<h3><spring:message code="label.events" /></h3>
<c:if test="${!empty eventList}">
	<table class="data">
		<tr>
			<th><spring:message code="label.name" /></th>
			<th><spring:message code="label.descriprion" /></th>
			<th><spring:message code="label.image" /></th>
			<th><spring:message code="label.month" /></th>
			<th><spring:message code="label.name" /></th>
			<th><spring:message code="label.ru_description" /></th>
			<th><spring:message code="label.ru_name" /></th>
			<th>&nbsp;</th>
		</tr>
		<c:forEach items="${cityList}" var="city">
			<tr>
				<td>${city.name}</td>
				<td>${city.descriprion}</td>
				<td>${city.image}</td>
				<td>${city.month}</td>
				<td>${city.name}</td>
				<td>${city.ru_description}</td>
				<td>${city.ru_name}</td>
				<td><a href="cityadminpage/delete/${city.id}"><spring:message code="label.delete" /></a></td>
			</tr>
		</c:forEach>
	</table>
</c:if> -->

</body>
</html>