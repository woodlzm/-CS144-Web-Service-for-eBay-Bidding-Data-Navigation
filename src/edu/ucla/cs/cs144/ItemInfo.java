 package edu.ucla.cs.cs144;

 public class ItemInfo {
 	private String name;
 	private String buyPrice;

 	public ItemInfo(String name){
 		this.name = name;
 	}

 	public String getName() {
 		return name;
 	}

 	public void setBuyPrice(String buyPrice) {
 		this.buyPrice = buyPrice;
 	}

 	public String getBuyPrice() {
 		return buyPrice;
 	}
 }