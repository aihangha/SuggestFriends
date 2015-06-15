package mekong89.suggestfriend;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatListAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<String> data;
	private static LayoutInflater inflater = null;
	String tempValues = null;
	int i = 0;

	public ChatListAdapter(Activity a, ArrayList<String> d) {
		// TODO Auto-generated constructor stub
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (data.size() <= 0)
			return 1;
		return data.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public static class ViewHolder {

		public TextView name;
		public ImageView image;
		public Button btnAccept;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
		ViewHolder holder;

		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.frienditem, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.name = (TextView) vi.findViewById(R.id.txtNameInFriendList);
			holder.image = (ImageView) vi
					.findViewById(R.id.imgAvatarInFriendList);
			holder.btnAccept = (Button) vi
					.findViewById(R.id.btnAddFriendInFriendList);
			holder.btnAccept.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					FriendListItem friendListItem = (FriendListItem) v.getTag();					
					if (activity instanceof TabFriends) {
						friendListItem.isFriend = true;
						v.setVisibility(View.GONE);
						TabFriends tabFriend = (TabFriends) activity;
						tabFriend.AcceptFriendRequest(friendListItem.email);
					}
				}
			});
			
			if(activity instanceof TabFriends){
				holder.image.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String friendMail = (String) v.getTag();
						Intent userHome = new Intent(activity,
								UserHomeActivity.class);
						userHome.putExtra("Email", friendMail);
						activity.startActivity(userHome);
					}
				});
			}

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}
		if (data.size() <= 0) {
			return null;

		} else {
			/***** Get each Model object from Arraylist ********/
			tempValues = (String) data.get(position);			
			/************ Set Model values in Holder elements ***********/
			String name = tempValues.substring(0,
					tempValues.lastIndexOf("@"));
			holder.name.setText(name);
			holder.image.setImageBitmap(Utils.getBitmapFromURL(activity,
					Utils.avatarAddress(activity) + name + "64.jpg", "avatar"));
			holder.image.setTag(tempValues);
			holder.btnAccept.setVisibility(View.GONE);

			/******** Set Item Click Listner for LayoutInflater for each row *******/
			// vi.setOnClickListener(new OnClickListener( position ));
		}
		return vi;
	}

}
