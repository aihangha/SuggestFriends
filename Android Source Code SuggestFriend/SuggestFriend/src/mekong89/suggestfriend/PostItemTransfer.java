package mekong89.suggestfriend;

import java.io.Serializable;
import java.util.Date;

public class PostItemTransfer implements Serializable {
	/**
	 * 
	 */
	public long id;
	public String email;
	public String content;
	public int numOfLike;
	public int numOfComment;
	public Date postDate;
	public boolean isLiked= false;
	public String imagename;
	
	public PostItemTransfer(long id, String email, String content, int numOfLike,int numOfComment, Date postDate, boolean isLiked,String imagename){
		this.id = id;
		this.email = email;
		this.content = content;
		this.numOfLike = numOfLike;
		//this.bitmapAvatar = Utils.getBitmapFromURL(context,Utils.avatarAddress + Utils.getNameFromEmail(email)+"64.jpg","avatar");
		this.isLiked = isLiked;
		this.imagename=imagename;
		this.numOfComment = numOfComment;
		this.postDate = postDate;        
	}
	
	public String getImageLink(){			// get avatar
		return (getName() + "64.jpg");
	}
	public String getName(){
		return Utils.getNameFromEmail(email);
	}
}
