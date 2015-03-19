package edu.ucla.cs.cs144;

import java.net.URL;
import java.io.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProxyServlet extends HttpServlet implements Servlet {
       
    public ProxyServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String keyword = request.getParameter("q");
        URL query = new URL("http://google.com/complete/search?output=toolbar&q=" + keyword.replace(" ", "+"));
        HttpURLConnection conn = (HttpURLConnection)query.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type:", "text/xml");
        if (conn.getResponseCode() < 299){
        	BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        	String tempInput;
        	StringBuffer res = new StringBuffer();
        	while ((tempInput = in.readLine()) != null)
        		res.append(tempInput);
        	in.close();
        	response.setContentType("text/xml");
        	PrintWriter out = response.getWriter();
        	out.println(res.toString());
        	out.close();
        }
    }
}
