package edu.ucla.cs.cs144;

public class ItemData {
	private String itemId;
	private String name;
	private String[] categories;

	public ItemData(String id) {
		this.itemId = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addCategory(String cat) {
		int n = categories.length;
		categories[n] = new String(cat);
	}

	public int getCatNum(){
		return categories.length;
	}

	public String getCategory(int idx){
		return categories[idx];
	}
}