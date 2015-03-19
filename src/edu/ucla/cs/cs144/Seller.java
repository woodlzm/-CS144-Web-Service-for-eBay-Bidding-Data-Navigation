package edu.ucla.cs.cs144;

public class Seller{
	private String userId;
	private String rating;

	public Seller(String id){
		this.userId = id;
	}

	public String getUserId(){
		return userId;
	}

	public void setRating(String rating){
		this.rating = rating;
	}

	public String getRating(){
		return rating;
	}
}