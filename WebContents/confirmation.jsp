<html>
	<head>
		<title>Payment Confirmation</title>
		<link rel="stylesheet" type="text/css" href="" />
	</head>
	<body>
		<%
			String name = (String)request.getParameter("name");
			if (name == null)
				name = (String)request.getAttribute("name");
			String buyPrice = (String)request.getParameter("buyPrice");
			if (buyPrice == null)
				buyPrice = (String)request.getAttribute("buyPrice");
			String date = (String)request.getParameter("date");
			if (date == null)
				date = (String)request.getAttribute("date");
		%>
		<table>
			<tr>
				<td>Item ID: </td>
				<td><%= request.getParameter("id") %></td>
			</tr>
			<tr>
				<td>Name: </td>
				<td><%= name %></td>
			</tr>
			<tr>
				<td>Buy Price: </td>
				<td><%= buyPrice %></td>
			</tr>
			<tr>
				<td>Credit Card: </td>
				<td><%= request.getParameter("credit") %></td>
			</tr>
			<tr>
				<td>Date: </td>
				<td><%= date %></td>
			</tr>
		</table>
		<%
			String url = "http://" + request.getServerName() + ":1448" + request.getContextPath() + "/keywordSearch.html";
		%>
		<div>
			<a href="<%= response.encodeRedirectURL(url) %>" >&gt;&gt;Return to item searching page&lt;&lt;</a>
		</div>
	</body>
</html>