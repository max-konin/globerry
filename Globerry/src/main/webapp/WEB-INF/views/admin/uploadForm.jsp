<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
 
<html>
    <head>
        <META http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <title>Upload Example</title>
        <script language = "javascript">
        function createRequestObject()
       			{
	        	var req;
	        	if(window.XMLHttpRequest)
	        	{
	        	//For Firefox, Safari, Opera
	        		req = new XMLHttpRequest();
	        	}
	        	else if(window.ActiveXObject)
	        	{
	        	//For IE 5+
	        		alert('ie suck');
	        		req = new ActiveXObject( "Microsoft.XMLDOM" );
	        		req.async = "false";
	        	}
	        	else
	        	{
	        	//Error for an old browser
	        		alert('Your browser is not IE 5 or higher, or Firefox or Safari or Opera');
        		}

        	return req;
        	}

        	//Make the XMLHttpRequest Object
        	var http = createRequestObject();

        	function sendRequest(method, url){
	        	if(method == 'get' || method == 'GET')
	        	{
		        	http.open(method,url);
		        	http.onreadystatechange = handleResponse;
		        	http.send(null);
		        }
        	}

        	function handleResponse()
        	{
        		if(http.readyState == 4 && http.status == 200)
        		{
        			//var response = http.responseText;
        			//if(response)
        			{
        				alert("Hello");
        			//document.getElementById("ajax_res").innerHTML = response;
        			}
        	 	}
        	}
        </script>
    </head>
    <body>
        <form:form modelAttribute="uploadItem" method="post" enctype="multipart/form-data">
            <fieldset>
                <legend>Upload Fields</legend>
 
                <p>
                    <form:label for="fileData" path="fileData">File</form:label><br/>
                    <form:input path="fileData" type="file"/>
                </p>
 
                <p>
                    <input type="submit" />
                </p>

 
            </fieldset>
        </form:form>
        <form>
            <p>
              	<input onclick="sendRequest('GET','upload/wikiparse')" type="button" value = "Parse Wiki">
            </p>
        </form>
    </body>
</html>