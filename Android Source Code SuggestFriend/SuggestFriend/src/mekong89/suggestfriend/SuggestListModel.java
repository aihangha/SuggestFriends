package mekong89.suggestfriend;

public class SuggestListModel {
	private String name;
	private String imageLink;
	private int meetCount;
	public SuggestListModel(String name, String url, int meetCount) {
		// TODO Auto-generated constructor stub
		this.name = name;
		imageLink = url;
		this.meetCount = meetCount;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setImageLink(String url){
		imageLink = url;
	}
	public void setMeetCount(int number){
		meetCount = number;
	}
	public String getName(){
		return name;
	}
	public String getImageLink(){
		return imageLink;
	}
	public int getMeetCount(){
		return meetCount;
	}
	
}
