<%@ page import="edu.ucla.cs.cs144.*" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<html>
	<head>
		<title>Payment Info Page</title>
		<link rel="stylesheet" type="text/css" href="search.css">
	</head>
	<body>
		<% 
			String id = request.getParameter("id");
			if (!request.isSecure()){
				StringBuffer url = new StringBuffer();
				url.append("https://" + request.getServerName() + ":8443");
				url.append(request.getContextPath());
				url.append("/payment.jsp");
				url.append("?id=" + id);
				response.sendRedirect(response.encodeRedirectURL(url.toString()));
			} else {
				HttpSession curSession = request.getSession(false);
				ItemInfo item = ((HashMap<String, ItemInfo>)curSession.getAttribute("items")).get(id);
		%>
		<form id="creditInfo" method="POST" action="confirmation">
			<table id="paymentInfo">
				<tr>
					<td>Item ID: </td>
					<td><%= id %></td>
				</tr>
				<tr>
					<td>Name: </td>
					<td><%= item.getName() %></td>
				</tr>
				<tr>
					<td>Buy Price: </td>
					<td><%= item.getBuyPrice() %></td>
				</tr>
				<tr>
					<td>Credit Card: </td>
					<td><input class="text" type="text" name="credit"></td>
				</tr>
			</table>
			<input type="hidden" name="id" value=<%= id %> >
			<input class="button" type="submit" value="Submit">
		</form>
		<%
			}
		%>
</html>