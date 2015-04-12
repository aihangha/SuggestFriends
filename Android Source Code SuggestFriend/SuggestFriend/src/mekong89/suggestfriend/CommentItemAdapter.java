package mekong89.suggestfriend;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentItemAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<PostItem> dataList;
	private static LayoutInflater inflater = null;
	PostItem postItemTemp = null;

	public CommentItemAdapter(Activity a, ArrayList<PostItem> d) {
		activity = a;
		dataList = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public PostItem getItem(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public static class ViewHolder {

		public ImageView avatar;
		public TextView nickName;
		public TextView cmtContent;
		public TextView cmtDate;

	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.commentitem, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.avatar = (ImageView) vi.findViewById(R.id.imgcommenttavatar);
			holder.nickName = (TextView) vi.findViewById(R.id.txtnicknameincomment);				
			holder.cmtContent = (TextView) vi.findViewById(R.id.txtcommentcontent);
			holder.cmtDate = (TextView) vi.findViewById(R.id.txtTimeincomment);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else{
			holder=(ViewHolder)vi.getTag();
		}
		

        	/***** Get each Model object from Arraylist ********/
        	postItemTemp = ( PostItem ) dataList.get( position );
        	holder.cmtContent.setText(postItemTemp.getContent());
        	holder.nickName.setText(postItemTemp.getName().toUpperCase());
        	holder.avatar.setImageBitmap(postItemTemp.getBitmapAvatar());
        	holder.cmtDate.setText(Utils.getDateString(postItemTemp.getPostDate()));

		return vi;
	}

}
