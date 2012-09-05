<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
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
        <form:form modelAttribute="uploadItem" method="POST" enctype="multipart/form-data">
            <fieldset>
                <legend>Upload Fields</legend>
 
                <p>
                    <form:label for="fileData" path="fileData">File</form:label><br/>
                    <form:input path="fileData" type="file"/>
                </p>
                <p>
 					<input type="checkbox" name="clean" value="true" checked> Clear database before parsing
 				</p>
                <p>
                    <input type="submit" />
                </p>

 
            </fieldset>
        </form:form>
        <form method="POST">
            <p>
              	<input onclick="sendRequest('POST','upload/wikiparse')" type="button" value = "Parse Wiki">
            </p>
        </form>
        <%
        if(request.getParameter("excelbugs") != null)
        {
        	String[] excelbugs = request.getParameterValues("excelbugs");
        	for(int i = 0; i < excelbugs.length; i++)
        	{
        		out.println("<p>" + excelbugs[i] + "</p>");
        	}
        }
        if(request.getParameter("wikibugs") != null)
        {
        	String[] wikibugs = request.getParameterValues("wikibugs");
        	for(int i = 0; i < wikibugs.length; i++)
        	{
        		out.println("<p>" + wikibugs[i] + "</p>");
        	}
        }
        %>
      <form method="GET">
		<input type="radio" name="download" value="excel"> Excel bugs
		<input type="radio" name="download" value="wiki"> Wikipedia bugs
        <input type="submit" value="Download">

      </form>
        
          	<%@ page import="java.io.*" %>
	        <%
	        if(request.getParameter("download") != null)
	        {
	        	
	        	 String filename = new String();
	        	if(request.getParameter("download").equals("excel"))
		        	filename = "ExcelBugs.txt";
 	        	if(request.getParameter("download").equals("wiki"))  
	        		filename = "WikipediaBugs.txt"; 
	        	 String shortName=	        			
	        	 filename.substring(filename.lastIndexOf("\\")+1,
                         filename.length()); 
		        response.setContentType("application/octet-stream");
		        String disHeader = "Attachment; Filename=\"" + shortName + "\"";
		        response.setHeader("Content-Disposition", disHeader);
		        File fileToDownload = new File(filename);
		
		        InputStream in = null;
		        ServletOutputStream outs = response.getOutputStream();
		
 		        try {
			        in = new BufferedInputStream
			        (new FileInputStream(fileToDownload));
			        int ch;
			        while ((ch = in.read()) != -1) {
			       			 outs.print((char) ch);
		       		 	}
		       		 }
		        finally {
		        if (in != null) in.close(); 
		        } 
		
		        outs.flush();
		        outs.close();
	        }%>

         <p>${excelbugs.first}</p>
      <c:if test="${!empty excelbugs}"><p>hey hey</p></c:if>
      <c:forEach items="${excelbugs}" var="item">
		<p>${item.toString()} </p>
    	</c:forEach>
    	<c:if test="${empty wikibugs}"><p>hey hey</p></c:if>
     <c:forEach items="${wikibugs}" var="item">
		<p>${item} </p>
    </c:forEach> 
        <p>Файлы с ошибками сохраняются скорее всего куда то сюда</p>
        <p>C:\Program Files (x86)\springsource\sts-2.8.1.RELEASE</p>
    </body>
</html>