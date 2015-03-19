package edu.ucla.cs.cs144;

public class Location{
	private String location;
	private String latitude;
	private String longitude;

	public Location(String loc){
		this.location = loc;
	}

	public String getLocation(){
		return location;
	}

	public void setLatitude(String lat){
		this.latitude = lat;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setLongitude(String lon){
		this.longitude = lon;
	}

	public String getLongitude(){
		return longitude;
	}
}