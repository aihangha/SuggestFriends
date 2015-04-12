package mekong89.suggestfriend;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageModel {
	private String id;
	private String content;
	private String imageName;
	private Date sendTime;
	private boolean fromMe;
	private String state;
	public MessageModel(String id, String content, String imageName, String timestamp, boolean fromMe, String state) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.content = content;
		this.imageName = imageName;
		this.fromMe = fromMe;
		this.state = state;
		DateFormat formatter ;
        ;
       formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       try {
    	   sendTime = (Date)formatter.parse(timestamp);
       				
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getID(){
		return this.id;
	}
	public void setContent(String content){
		this.content = content;
	}
	public void setImageLink(String url){
		imageName = url;
	}
	public void setSendTime(Date date){
		sendTime = date;
	}
	public String getContent(){
		return content;
	}
	public String getImageLink(){
		return imageName;
	}
	public Date getSendTime(){
		return sendTime;
	}
	
	public void setFromMe(boolean val){
		fromMe=val;
	}
	public boolean getFromMe(){
		return fromMe;
	}
	public String getState(){
		return state;
	}
	public void setState(String newState){
		state=newState;
	}
	
	
}
