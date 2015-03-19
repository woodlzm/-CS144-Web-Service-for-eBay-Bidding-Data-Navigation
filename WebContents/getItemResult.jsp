<!DOCTYPE html>
<%@ page import="edu.ucla.cs.cs144.*" %>
<html>
	<head>
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
		<title>Retrieved Item Info</title>
		<script type="text/javascript" 
		    src="http://maps.google.com/maps/api/js?sensor=false"> 
		</script> 
		<script type="text/javascript">
			function getMapViaLatLng(flag){
				if (flag == 1){
					var s = document.getElementById("itemLatlng").innerHTML;
					var lat = s.slice(1, s.indexOf(","));
					var lng = s.slice(s.indexOf(",") + 1, s.indexOf(")"));
					if (lat < -90 || lat > 90 || lng < -180 || lng > 180)
						flag = 0;
					else {
						var latlng = new google.maps.LatLng(lat,lng); 
					    var myOptions = {  
					    	zoom: 14,
			  		        center: latlng, 
					        mapTypeId: google.maps.MapTypeId.ROADMAP 
					    }; 
					    var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
					    var marker = new google.maps.Marker({
					    	map: map,
					    	position: latlng,
					    	visible: true
					    });
					}
				}
				if (flag==0) {
					var s = document.getElementById("itemLoc").innerHTML;
					var myConstraints = {country: document.getElementById("itemCountry").innerHTML};
					var myRequest = {
						address: s,
						componentRestrictions: myConstraints
					};
					var geocoder = new google.maps.Geocoder();
					geocoder.geocode(myRequest, function(results, status){
							if (status == google.maps.GeocoderStatus.OK) {
								var latlng;
								if (results.partial_match)
									latlng = results[1].geometry.location;
								else
									latlng = results[0].geometry.location;
								var myOptions = {
									zoom: 14,
									center: latlng,
									mapTypeId: google.maps.MapTypeId.ROADMAP
								};
								var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
								var marker = new google.maps.Marker({
									map: map,
									position: latlng,
									visible: true
								});
							}
							else 
								document.getElementById("map_canvas").innerHTML = "The location for this item is currently unavailable.";
						}
					);
				}
			}
		</script>
	</head>
	<body onload="getMapViaLatLng(hasLatlng)">
		<%@ include file="getItem.html" %>
		<%
			if (request.isSecure()) {
				response.sendRedirect(response.encodeRedirectURL("http://" + request.getServerName() + ":1448" + request.getContextPath() + "/item?id=" + request.getParameter("id")));
			}
			if (((String)request.getAttribute("invalid")).equals("1")) {
		%>
		<b>Requested item not found!</b>
		<%
			} else {
		%>
		<form id="buyNow" method="GET" action="buy_now">
			<table>
			<tr>
				<td class="info">
				<table>
					<tr>
						<td>Item ID:</td>
						<td><%= request.getParameter("id") %></td>
					</tr>
					<tr>
						<td>Name:</td>
						<td><%= request.getAttribute("name") %><td>
					</tr>
					<tr>
						<td>Categories:</td>
						<td>
						<%
							String[] categories = (String[])request.getAttribute("categories");
							for (int i = 0; i < categories.length - 1; ++i) {
						%>
							<%= categories[i] + ", " %>
						<%
							}
						%>
						<%= categories[categories.length - 1] %>
						<td>
					</tr>
					<tr>
						<td>Concurrently:</td>
						<td>
							<% 
								String concur = (String)request.getAttribute("concurrently");
								if (concur.equals("")) {
							%>
							N/A
							<%
								} else {
							%>
							<%= concur %>
							<%
								}
							%>
						</td>
					</tr>
					<tr>
						<td>Buy Price:</td>
						<td>
							<% 
								String buyPrice = (String)request.getAttribute("buyPrice");
								if (buyPrice.equals("")) {
							%>
							N/A
							<%
								} else {
							%>
							<%= buyPrice %>
						</td>
					</tr>
					<tr>
						<td />
						<td>
							<input type="hidden" name="id" value=<%= request.getParameter("id") %> >
							<input type="submit" class="button" value="Buy Now">
							<%
								}
							%>
						</td>
					</tr>
					<tr>
						<td>First Bid:</td>
						<td>
							<% 
								String firstBid = (String)request.getAttribute("firstBid");
								if (firstBid.equals("")) {
							%>
							N/A
							<%
								} else {
							%>
							<%= firstBid %>
							<%
								}
							%>
						</td>
					<tr>
					<tr>
						<td>Number of Bids:</td>
						<td>
							<% 
								String numOfBids = (String)request.getAttribute("numOfBids");
							%>
							<%= numOfBids %>
						</td>
					<tr>
					<tr>
						<%
							if (!numOfBids.equals("0")) {
						%>
							<td>Bids:</td>
							<td>
								<%
									ItemBid[] bids = (ItemBid[])request.getAttribute("bids");
									for (int i = 0; i < Integer.parseInt((String)request.getAttribute("numOfBids")); ++i){
								%>
									<table>
										<tr>
											<td>User: </td>
											<td>
											<%
												String bidInfo = bids[i].getBidder().getUserId() + " (Rating: " + bids[i].getBidder().getRating();
												if (bids[i].getBidder().getLocation() != "")
													bidInfo = bidInfo + ", Location: " + bids[i].getBidder().getLocation();
												if (bids[i].getBidder().getCountry() != "")
													bidInfo = bidInfo + ", Country: " + bids[i].getBidder().getCountry();
												bidInfo += ")";
											%>
											<%= bidInfo %>
											</td>
										</tr>
										<tr>
											<td>Time:</td>
											<td><%= bids[i].getTime() %>
										</tr>
										<tr>
											<td>Amount:</td>
											<td><%= bids[i].getAmount() %>
										</tr>
									</table>
								<%
									}
								%>
							</td>
						<%
							}
						%>
					</tr>
					<tr>
						<td>Location:</td>
						<%
							Location loc = (Location)request.getAttribute("loc");
						%>
						<td><span id="itemLoc"><%= loc.getLocation() %></span>
							<script>hasLatlng = 0;</script>
							<%
								if (loc.getLatitude() != ""){
							%>
								<script>hasLatlng = 1;</script>
								<span id="itemLatlng"><%= "(" + loc.getLatitude() + "," + loc.getLongitude() + ")" %></span>
							<%
								}
							%>
						</td>
					</tr> 
					<tr>
						<td>Country:</td>
						<td><span id="itemCountry"><%= request.getAttribute("country") %></span></td>
					</tr>
					<tr>
						<td>Seller:</td>
						<td><%= ((Seller)request.getAttribute("seller")).getUserId() + " (Rating: " + ((Seller)request.getAttribute("seller")).getRating() + ")" %></td>
					</tr>
					<tr>
						<td>Description:</td>
						<td><%= request.getAttribute("des") %></td>
					</tr>
				</table>
				</td>
				<td>
					<div id="map_canvas" style="width: 300px; height: 300px; position: absolute; right: 50px; top: 200px;"></div>
				</td>
			</tr>
			</table>
		</form>
		<%
			}
		%>
	</body>
</html>