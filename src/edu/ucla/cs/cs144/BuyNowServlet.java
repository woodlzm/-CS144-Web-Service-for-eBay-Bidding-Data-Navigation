package edu.ucla.cs.cs144;

import java.util.*;
import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BuyNowServlet extends HttpServlet implements Servlet {
	public BuyNowServlet() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
	{
		HttpSession session = request.getSession(false);
		boolean valid = true;
		String id = request.getParameter("id");
		if (session == null)
			valid = false;
		else {
			HashMap<String, ItemInfo> items = (HashMap<String, ItemInfo>)session.getAttribute("items");
			ItemInfo curItem;
			if ((items == null) || ((curItem = items.get(id)) == null))
				valid = false;
		}
		if (valid) {
			if (request.isSecure())
				request.getRequestDispatcher("./payment.jsp").forward(request, response);
			else {
				StringBuffer url = new StringBuffer();
				url.append("https://" + request.getServerName() + ":8443");
				url.append(request.getContextPath());
				url.append("/payment.jsp");
				url.append("?id=" + id);
				response.sendRedirect(response.encodeRedirectURL(url.toString()));
			}
		} else {
			StringBuffer url = new StringBuffer();
			url.append("http://" + request.getServerName() + ":1448");
			url.append(request.getContextPath());
			url.append("/session_expired.jsp?id=" + id);
			response.sendRedirect(response.encodeRedirectURL(url.toString()));
		}
	}
}