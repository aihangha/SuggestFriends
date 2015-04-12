package mekong89.suggestfriend;

public class FriendEntry {
	int id;
	String email;
	float loc_long;
	float loc_lat;
	long timestamp;
	public FriendEntry(int id, String email, float loc_long, float loc_lat, long timestamp ) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.loc_long =loc_long;
		this.loc_lat = loc_lat;
		this.timestamp = timestamp;
		this.email = email;
	}
	public FriendEntry() {
		id=0;
		email="";
		loc_lat=0.0f;
		loc_long=0.0f;
		timestamp = 0;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setLocLong(float loc_long){
		this.loc_long = loc_long;		
	}
	public void setLocLat(float loc_lat){
		this.loc_lat = loc_lat;
	}
	public void setTimeStamp(long timestamp){
		this.timestamp = timestamp;
	}
	public void setID(int id){
		this.id = id;
	}
	
	public String getEmail(){
		return email;
	}
	public float getLocLong(){
		return loc_long;
	}
	
	public float getLocLat(){
		return loc_lat;		
	}
	
	public long getTimeStamp()
	{
		return timestamp;
	}
	
	public int getID(){
		return id;
	}
}
