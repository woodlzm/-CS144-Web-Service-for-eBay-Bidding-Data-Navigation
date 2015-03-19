<html>
	<head>
		<title>Search Results</title>
		<link rel="stylesheet" type="text/css" href="search.css" />
	</head>
	<body>
		<%@ include file="keywordSearch.html" %>
		<% 
			if (request.isSecure()) {
				response.sendRedirect(response.encodeRedirectURL("http://" + request.getServerName() + ":1448" + request.getContextPath() + "/search?q=" + request.getParameter("q") + "&numResultsToSkip=" + request.getParameter("numResultsToSkip") + "&numResultsToReturn=" + request.getParameter("numResultsToReturn") + ((request.getParameter("page") == null ? "" : ("&page=" + request.getParameter("page"))))));
			}
			Integer n = (Integer)request.getAttribute("length");
			if (n == 0) {
			%>
				<table id="results">
					<tr>
						<td>
							<p>No results found!</p>
						</td>
					</tr>
				</table>
		<%	}
			else {
			%>
			<table class="des">
				<tr>
					<td>
						<b>The results of your previous search are as follows:</b>
					</td>
				</tr>
			</table>
			<table id="results">
				<tr>
					<td class="itemID"><b>ItemID</b></th>
					<td class="name"><b>Name</b></th>
				</tr>
				<%
					Integer curPage = Integer.parseInt((String)request.getAttribute("curPage")) - 1;
					int itemNum = n - curPage * 20;
					if (itemNum > 20)
						itemNum = 20;
					for (Integer i = 0; i < itemNum; ++i) {
						%>
						<tr class="result">
							<%
								String itemId = ((String[])request.getAttribute("ids"))[i + curPage * 20];
							%>
							<td class="itemID"><a href="item?id=<%= itemId %>"><%= itemId %></a></td>
							<td class="name"><%= ((String[])request.getAttribute("names"))[i + curPage * 20]%></td>
						</tr>
						<%
					}
				%>
			</table>
			<%
			}
		%>
		<%@ include file="nav.html" %>
	</body>
</html>