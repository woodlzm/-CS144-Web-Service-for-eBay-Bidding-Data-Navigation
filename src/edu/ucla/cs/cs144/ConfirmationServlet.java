package edu.ucla.cs.cs144;

import java.util.*;
import java.text.SimpleDateFormat;
import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConfirmationServlet extends HttpServlet implements Servlet {
	public ConfirmationServlet() {}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		boolean valid = true;
		String id = request.getParameter("id");
		ItemInfo curItem = null;
		HashMap<String, ItemInfo> items = null;
		if (session == null)
			valid = false;
		else {
			items = (HashMap<String, ItemInfo>)session.getAttribute("items");
			if ((items == null) || ((curItem = items.get(id)) == null))
				valid = false;
		}
		Date curTime = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("MMM-dd-yy HH:mm:ss zzz");
		
		if (valid) {
			items.remove(id);
			session.setAttribute("items", items);
			if (request.isSecure()) {
				request.setAttribute("name", (String)curItem.getName());
				request.setAttribute("buyPrice", (String)curItem.getBuyPrice());
				request.setAttribute("date", ft.format(curTime));
				request.getRequestDispatcher("./confirmation.jsp").forward(request, response);
			} else {
				StringBuffer url = new StringBuffer();
				url.append("https://" + request.getServerName() + ":8443");
				url.append(request.getContextPath() + "/confirmation.jsp");
				url.append("?id=" + id);
				url.append("&name=" + (String)curItem.getName());
				url.append("&buyPrice=" + (String)curItem.getBuyPrice());
				url.append("&credit=" + request.getParameter("credit"));
				url.append("&date=" + ft.format(curTime));
				response.sendRedirect(response.encodeRedirectURL(url.toString()));
			}
		} else {
			StringBuffer url = new StringBuffer();
			url.append("http://" + request.getServerName() + ":1448");
			url.append(request.getContextPath());
			url.append("/session_expired.jsp?id=" + id);
			response.sendRedirect(response.encodeRedirectURL(url.toString()));
		}
		
		/*
		PrintWriter out = response.getWriter();
		out.println("Item ID: " + id);
		out.println("Buy Price: " + (String)curItem.getBuyPrice());
		out.println("Date: " + ft.format(curTime));
		out.close();
		*/
	}
}