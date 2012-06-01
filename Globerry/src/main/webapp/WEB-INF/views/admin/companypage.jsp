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
	                	$.getJSON("getcompanies",
               	            function(response) 
               	            {
	                			var companies = [];		
   								$.each(response, function(key, val)
   								{
									var id = val.id;
									var access = val.access;
									var description = val.description;
									var email = val.email;
									var login = val.login;
									var name = val.name;
									var password = val.password;
									
									//alert(month);
									
									companies.push({
										Id: id,
										Name: name,
										Access: access,
										Description: description,
										Email: email,
										Login: login,
										Password: password		
   									});
									//alert(deleteButton);
   								});
   									
	   							 $("#grid").kendoGrid(
	   		                    		{
	   		                        dataSource:
	   		                        {
	   		                            data:companies,
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
	   		                                field: "Name"
	   		                            	} , 
	   		                           		{
	   		                                field: "Access"
	   		                            	} , 
	   		                            	{
	   		                                field: "Description"
	   		                            	} , 
	   		                            	{
	   		                                field: "Email"
	   		                           		} , 
	   		                           		{
	   		                                field: "Login"
	   		                            	} ,
	   		                            	{
	   			                            	field: "Password"
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
	                </script>
	        	   </div>

</body>
</html>