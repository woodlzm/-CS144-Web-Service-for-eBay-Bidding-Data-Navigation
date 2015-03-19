package edu.ucla.cs.cs144;

public class ItemBid {
	private Bidder bidder;
	private String time;
	private String amount;

	public ItemBid() {}

	public void setBidder(String bidder){
		this.bidder = new Bidder(bidder);
	}

	public Bidder getBidder(){
		return bidder;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}
}
