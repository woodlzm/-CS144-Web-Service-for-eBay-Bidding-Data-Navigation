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

public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String id = request.getParameter("id");
        String rawData = AuctionSearchClient.getXMLDataForItemId(id);
        if (rawData == null){
            request.setAttribute("invalid", "1");
            request.getRequestDispatcher("./getItemResult.jsp").forward(request, response);
        } else {
            request.setAttribute("invalid", "0");
            String name = getElement(rawData, "Name");
            request.setAttribute("name", name);
            ArrayList<String> tempCat = new ArrayList<String>();
            String temp = getElement(rawData, "Category");
            int idx = 0;
            while (idx != -1){
            	idx = rawData.indexOf("</Category>", idx);
            	tempCat.add(temp);
            	temp = getElement(rawData.substring(idx), "<Category>");
            	idx = rawData.indexOf("<Category>", idx);
            }
            idx = 0;
            if (tempCat.size() == 0)
            	tempCat.add("");
            String[] categories = new String[tempCat.size()];
            for (int i = 0; i < tempCat.size(); ++i){
            	categories[i] = tempCat.get(i);
            }
            request.setAttribute("categories", categories);
            request.setAttribute("concurrently", getElement(rawData, "Concurrently"));
            String buyPrice = getElement(rawData, "Buy_Price");
            request.setAttribute("buyPrice", buyPrice);
            request.setAttribute("firstBid", getElement(rawData, "First_Bid"));
            String numOfBids = getElement(rawData, "Number_of_Bids");
            request.setAttribute("numOfBids", numOfBids);
            if (Integer.parseInt(numOfBids) != 0){
    	        ItemBid[] bids = new ItemBid[Integer.parseInt(numOfBids)];
    	        idx = rawData.indexOf("<Bids>");
    	        for (int i = 0; i < Integer.parseInt(numOfBids); ++i){
    	        	idx = rawData.indexOf("<Bid>", idx);
    	        	bids[i] = new ItemBid();
    	        	bids[i].setBidder(getAttribute(rawData.substring(idx), "UserID"));
    	        	bids[i].getBidder().setRating(getAttribute(rawData.substring(idx), "Rating"));
    	        	bids[i].getBidder().setLocation(getElement(rawData.substring(idx), "Location"));
    	        	bids[i].getBidder().setCountry(getElement(rawData.substring(idx), "Country"));
    	        	bids[i].setTime(getElement(rawData.substring(idx), "Time"));
    	        	bids[i].setAmount(getElement(rawData.substring(idx), "Amount"));
    	        	idx = rawData.indexOf("</Bid>", idx);
    	        }
    	        for (int s = 2; s <= Integer.parseInt(numOfBids); ++s){
    	        	ItemBid cur = bids[s - 1];
    	        	int i = s - 2;
    	        	while (i > -1 && compBid(bids[i], cur)){
    	        		bids[i + 1] = bids[i];
    	        		--i;
    	        	}
    	        	bids[i + 1] = cur;
    	        }
            	request.setAttribute("bids", bids);
            }
            Location loc = new Location(getElement(rawData.substring(idx), "Location"));
            loc.setLatitude(getAttribute(rawData, "Latitude"));
            loc.setLongitude(getAttribute(rawData, "Longitude"));
            request.setAttribute("loc", loc);
            request.setAttribute("country", getElement(rawData.substring(idx), "Country"));
            request.setAttribute("started", getElement(rawData, "Started"));
            request.setAttribute("ends", getElement(rawData, "Ends"));
            idx = rawData.indexOf("Seller");
            Seller seller = new Seller(getAttribute(rawData.substring(idx), "UserID"));
            seller.setRating(getAttribute(rawData.substring(idx), "Rating"));
            request.setAttribute("seller", seller);
            request.setAttribute("des", getElement(rawData, "Description"));
            HttpSession session = request.getSession(true);
            HashMap<String, ItemInfo> items = (HashMap<String, ItemInfo>)session.getAttribute("items");
            if (items == null)
                items = new HashMap<String, ItemInfo>();
            ItemInfo curItem = new ItemInfo(name);
            curItem.setBuyPrice(buyPrice);
            items.put(id, curItem);
            session.setAttribute("items", items);
            /*
            rawData = rawData.replace("<", " | ");
            rawData = rawData.replace(">", " | ");
            PrintWriter out = response.getWriter();
            out.println("<html>\n<body>" + rawData + "\n</body>\n</html>");
            */
            request.getRequestDispatcher("./getItemResult.jsp").forward(request, response);
        }
    }

    private boolean compBid(ItemBid bid1, ItemBid bid2){
    	double val1 = Double.parseDouble(bid1.getAmount().substring(1));
    	double val2 = Double.parseDouble(bid2.getAmount().substring(1));
    	return (val1 < val2);
    }

    private String getAttribute(String raw, String attr){
    	int beginIdx = raw.indexOf(attr);
    	if (beginIdx == -1)
    		return "";
    	beginIdx += attr.length();
    	beginIdx += 2;
    	int endIdx = raw.indexOf("\"", beginIdx);
    	return raw.substring(beginIdx, endIdx);
    }

    private String getElement(String raw, String e){
    	int beginIdx = raw.indexOf(e);
    	if (beginIdx == -1)
    		return "";
    	beginIdx = raw.indexOf(">", beginIdx) + 1;
    	int endIdx = raw.indexOf("<", beginIdx);
    	return raw.substring(beginIdx, endIdx);
    }
}
