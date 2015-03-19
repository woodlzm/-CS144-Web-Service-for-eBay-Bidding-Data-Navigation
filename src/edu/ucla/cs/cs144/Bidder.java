package edu.ucla.cs.cs144;

public class Bidder {
	private String userId;
	private String rating;
	private String location;
	private String country;

	public Bidder(String userId){
		this.userId = userId;
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

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}
}
