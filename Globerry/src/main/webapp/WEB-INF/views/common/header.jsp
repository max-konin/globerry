<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="header">
	<table>
		<tr>
			<td align="left"><h1>${param.title} ${title} ${titleParam} <%= request.getParameter("title") %></h1></td>
			<td><div>
				<jsp:include page="../loginpage.jsp" />
			</div></td>
		</tr>
	</table>
</div>