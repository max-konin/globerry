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
	<script type="text/javascript" src="../resources/javascripts/kendoui/js/kendo.web.min.js"></script>
    <link href="../resources/javascripts/kendoui/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="../resources/javascripts/kendoui/styles/kendo.default.min.css" rel="stylesheet" />
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
            <div>
				<ul id="treeView">
					
				</ul>
            </div>

            <style scoped>
                #clientsDb {
                    width: 692px;
                    height: 500px;
                    margin: 30px;
                    padding: 51px 4px 0 4px;
                  
                }
            </style>
			<script language ="javascript">
				
	                $(document).ready(function() {
	                	$.getJSON("getevents",
               	            function(response) 
               	            {
	                			var events = [];		
   								$.each(response, function(key, val)
   								{
									var id = val.id;
									var description = val.description;
									var image = val.image;
									var month = val.month;
									var name = val.name;
									var ru_description = val.ru_description;
									var ru_name = val.ru_name;
									var deleteButton = "<input type=\"button\" value=\"блаблабла\" onclick=\"location.href='delete/" + id +"'\" />";
									
									//alert(month);
									
									events.push({
										Id: id,
										Description: description,
										Image: image,
										Month: month,
										Name: name,
										Ru_description: ru_description,
										Ru_name: ru_name,
										DeleteButton: deleteButton
   									});
									//alert(deleteButton);
   								});
   									
	   							 $("#grid").kendoGrid(
	   		                    		{
	   		                        dataSource:
	   		                        {
	   		                            data:events,
	   		                          	pageSize: 30
	   		                        },
	   		                        height: 500,
	   		                        groupable: true,
	   		                        scrollable: true,
	   		                     	selectable: "multiple",
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
	   		                            	} ,
	   		                            	{
	   		                            		command: ["edit", "destroy"], 
	   		                            		title: " ",
	   		                            		width: "110px" 
	   		                            		//template: "&lt;a class=\"k-button k-button-icontext k-grid-delete\" href=\"#= Id#\"><span class=\"k-icon k-delete\"></span>Delete</a>&rt;"
	   		                            	}
	   		                        		]
	   		                    		});
   		                	$('.k-focusable tr').each(function(key, val)
   		                			{
   		                				var id =$(val).children("td:first").text();
   		                				var buttonsCell = $(val).children("td:last");
   		                				$(buttonsCell).children("a.k-grid-delete")
   		                					.click(function(){
   		                						$.ajax(
   		                								{
   		                									url:"delete/" + id,
   		                									success:function(response){
   		                										$(val).remove();
   		                									}
   		                								});
   		                				}); 
   		                				$(buttonsCell).children("a.k-grid-edit").attr("href", "update/" + id);
   		                			}); 
   		                	var row = $("#grid").find(".k-grid-content").find("tr");
   		                	row.click(function(){
   		                			var id =$(this).find("td:first").text();
           							$.getJSON("getrelations/" + id,function(response) 
       					               	       {
           											$("#treeView").empty();
       												$.each(response, function(key, val)
       				   								{	
       													var globElem = $("<li>" + key + "</li>");
       													var localElem = $("<ul/>");
       													for(var i = 0; i < val.length; i++)
       														{
																$(localElem).append("<li>" + val[i].name + "</li>");
       														}
       													$(globElem).append(localElem);
       													$("#treeView").append(globElem);
       				   								});
       												$("#treeView").kendoTreeView();
       											});
           							
       										
   		                			});
   		                
               	            });
        			});
	                   
	                /*
						dataSource = new kendo.data.DataSource({
					transport: {
						 read:  {
                             url: "getevents",
                             dataType: "json"
                         },
                         update: {
                             url: "edit",
                             dataType: "json"
                         },
                         destroy: {
                             url: "delete",
                             dataType: "jsonp"
                         }, 
                         create: {
                             url: "Create",
                             dataType: "json"
                         },
                         parameterMap: function(options, operation) {
                             if (operation !== "read" && options.models) {
                            	 alert(kendo.stringify(options.models));
                                 return {event: kendo.stringify(options.models)};
                             }
                         } 
					},
					batch: true,
                    pageSize: 30,
					schema: {
                        model: {
                        	title: "event",
                            id: "id",
                            fields: {
                            	description: { type:"string", validation: { required: true } },
                                image: { type:"string", validation: { required: true } },
                                month: { type: "string", validation: { required: true} },
                                name: { type:"string", validation: { required: true } },
                                ru_description: { type:"string", validation: { required: true } },
                                ru_name: { type:"string", validation: { required: true } }
                            }
                        }
                    }
                });
							//alert("ready");*/
		/*	var events[];
			$.each(response, function(key, val)
					{
						var id = val.id;
						var description = val.description;
						var image = val.image;
						var month = val.month;
						var name = val.name;
						var ru_description = val.ru_description;
						var ru_name = val.ru_name;//*/
						
						/*events.push({
							Id: id,
							Description: description,
							Image: image,
							Month: month,
							Name: name,
							Ru_description: ru_description,
							Ru_name: ru_name
							//Func:"<a id = id></a>
						});
						});//*/
			/*	$("#grid").kendoGrid(
	                    {
	                        dataSource: 
	                        	{
	                            data: events,
	                          	pageSize: 20
	                    	    } ,
	                        height: 360,
	                        groupable: true,
	                        scrollable: true,
	                        sortable: true,
	                        pageable: true,
	                        columns: [ 
	                                  {
	                                field: "id",
	                           		} , 
	                           		{
	                                field: "description",
	                            	} , 
	                           		{
	                                field: "image",
	                            	} , 
	                            	{
	                                field: "month",
	                            	} , 
	                            	{
	                                field: "name",
	                           		} , 
	                           		{
	                                field: "ru_description",
	                            	} , 
	                            	{
		                            field: "image",
	                            	} 
	                            	
	                        ],
	                    });	
		 });	//*/
					



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