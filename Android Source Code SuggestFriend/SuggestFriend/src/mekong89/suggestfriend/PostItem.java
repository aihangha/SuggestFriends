package mekong89.suggestfriend;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

@SuppressLint("SimpleDateFormat")
public class PostItem{
	/**
	 * 
	 */
	private long id;
	private String email;
	private Drawable avatar;
	private String content;
	private int numOfLike;
	private int numOfComment;
	private Bitmap bitmapAvatar;
	private Date postDate;
	private boolean isLiked= false;
	private String imagename;
	private Context context;
	
	public PostItem(Context context,long id, String email, String content, int numOfLike,int numOfComment, String timestamp, boolean isLiked,String imagename){
		this.context = context;
		this.id = id;
		this.email = email;
		this.content = content;
		this.numOfLike = numOfLike;
		this.bitmapAvatar = Utils.getBitmapFromURL(context,Utils.avatarAddress + Utils.getNameFromEmail(email)+"64.jpg","avatar");
		this.isLiked = isLiked;
		this.imagename=imagename;
		this.numOfComment = numOfComment;
		DateFormat formatter ;
         ;
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
        	postDate = (Date)formatter.parse(timestamp);
        				
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	public PostItem(String email, String content){
		this.id = 0;
		this.email = email;
		this.avatar = null;
		this.content = content;
		this.numOfLike = 0;
		//Create item on server
	}
	public long getID(){
		return id;
	}
	public String getEmail(){
		return email;		
	}
	public Drawable getAvatar(){
		return avatar;
	}
	public Bitmap getBitmapAvatar(){
		return bitmapAvatar;
	}
	public String getContent(){
		return content;
	}
	public int getnumOfLike(){
		return numOfLike;
	}
	public int getNumOfComment(){
		return numOfComment;
	}
	public void increaseLike(){
		numOfLike++;
		//update server info
		//..................
	}
	public void decreaseLike(){
		numOfLike--;
		//update server info
		//...................
	}
	public String getImageLink(){			// get avatar
		return (getName() + "64.jpg");
	}
	public String getName(){
		return Utils.getNameFromEmail(email);
	}
	public long getTimeInMili(){
		return postDate.getTime();
	}
	public Date getPostDate(){
		return postDate;
	}
	public boolean getIsLiked(){
		return this.isLiked;
	}
	public void setIsLiked(boolean val){
		isLiked = val;
	}
	
	public String getImageName(){
		return this.imagename;
	}
	
	public Bitmap getBitmapInPost(){
		if(null!=imagename && !imagename.isEmpty()){
			return Utils.getBitmapFromURL(context, Utils.postImages + imagename,"post");
		} else{
			return null;
		}
	}
	
	public PostItemTransfer getPostItemTransfer(){
		return new PostItemTransfer(id, email, content, numOfLike, numOfComment, getPostDate(), isLiked, imagename);
	}
	
}
