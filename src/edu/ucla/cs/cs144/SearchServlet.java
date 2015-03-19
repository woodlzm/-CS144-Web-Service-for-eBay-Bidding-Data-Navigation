package edu.ucla.cs.cs144;

import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet implements Servlet {
       
    public SearchServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String q = request.getParameter("q");
        String skipStr = request.getParameter("numResultsToSkip");
        if (skipStr.equals(""))
        	skipStr = "0";
        int skip = Integer.parseInt(skipStr);
        String retStr = request.getParameter("numResultsToReturn");
        if (retStr.equals(""))
        	retStr = "20";
        int ret = Integer.parseInt(retStr);
        SearchResult[] results = AuctionSearchClient.basicSearch(q, skip, ret);
        int n = results.length;
        String[] ids = new String[n];
        String[] names = new String[n];
        /*PrintWriter out = response.getWriter();
        String docType =
	      "<!doctype html public \"-//w3c//dtd html 4.0 " +
	      "transitional//en\">\n";
        response.setContentType("text/html");
        out.println(docType +
                "<html>\n" + "<body>\n");*/
        for (int i = 0; i < n; ++i){
        	ids[i] = results[i].getItemId();
        	names[i] = results[i].getName();
        	//out.println(ids[i] + ' ' + names[i] + "<br/>");
        }
        //out.println("</body></html>");
        request.setAttribute("ids", ids);
        request.setAttribute("names", names);
        int resLen = results.length;
        request.setAttribute("length", resLen);
        int pageNum = resLen / 20;
        if (results.length % 20 != 0)
        	++pageNum;
        request.setAttribute("pageNum", pageNum);
        String curPage;
        if (resLen == 0)
            curPage = "0";
        else if (request.getParameter("page") == null)
        	curPage = "1";
        else
        	curPage = request.getParameter("page");
        request.setAttribute("curPage", curPage);
        request.getRequestDispatcher("./keywordSearchResult.jsp").forward(request, response);
    }
}
