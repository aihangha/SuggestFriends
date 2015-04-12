package mekong89.suggestfriend;

import java.io.Serializable;
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
import android.widget.ListView;
import android.widget.TextView;

public class PostItemAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<PostItem> dataList;
	private static LayoutInflater inflater = null;
	PostItem postItemTemp = null;

	public PostItemAdapter(Activity a, ArrayList<PostItem> d) {
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
		public TextView numOfLike;
		public ImageView likeImage;
		public TextView feedContent;
		public TextView feedDate;
		public Button btnFollow;
		public ImageView imgContent;
		public TextView numOfComment;
		public ImageView imgComment;
		public ImageView imgSocial;

	}

	public static class ImageLikeTagObject {

		public int position;
		public TextView txtLike;

		public ImageLikeTagObject(int position, TextView txtLike) {
			// TODO Auto-generated constructor stub
			this.position = position;
			this.txtLike = txtLike;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.post_item, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.avatar = (ImageView) vi.findViewById(R.id.imgpostavatar);
			holder.nickName = (TextView) vi.findViewById(R.id.txtnickname);
			holder.numOfLike = (TextView) vi.findViewById(R.id.txtnumoflike);
			holder.feedContent = (TextView) vi
					.findViewById(R.id.txtfeedcontent);
			holder.feedDate = (TextView) vi.findViewById(R.id.txtTime);
			holder.likeImage = (ImageView) vi.findViewById(R.id.imglike);
			holder.imgContent = (ImageView) vi
					.findViewById(R.id.imgeContentInFeed);
			holder.btnFollow = (Button) vi.findViewById(R.id.btnFollowInFeed);
			holder.imgComment = (ImageView) vi
					.findViewById(R.id.imgCommentInFeedItem);
			holder.numOfComment = (TextView) vi
					.findViewById(R.id.txtNumOfCommentInFeedItem);
			holder.imgSocial = (ImageView) vi.findViewById(R.id.imgSocialinfeedList);

			// final int pos = position;
			holder.likeImage.setClickable(true);
			holder.likeImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageLikeTagObject tagObject = (ImageLikeTagObject) v
							.getTag();

					PostItem pi = (PostItem) dataList.get(tagObject.position);

					if (pi.getIsLiked()) {
						pi.decreaseLike();
						pi.setIsLiked(false);
						ImageView image = (ImageView) v;
						image.setImageResource(R.drawable.notlike);

						if (activity instanceof TabFeeds) {
							((TabFeeds) activity).deleteLike("" + pi.getID());
						} else{
							((UserHomeActivity) activity).deleteLike("" + pi.getID());
						}
					} else {
						pi.increaseLike();
						pi.setIsLiked(true);
						ImageView image = (ImageView) v;
						image.setImageResource(R.drawable.hearticon);
						if (activity instanceof TabFeeds) {
							((TabFeeds) activity).createLike("" + pi.getID());
						} else{
							((UserHomeActivity) activity).createLike("" + pi.getID());
						}
					}
					tagObject.txtLike.setText("" + pi.getnumOfLike());
				}
			});
			holder.imgComment.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					PostItem pi = (PostItem) dataList.get((Integer) v.getTag());
					Intent oneFeedIntent = new Intent(activity,
							OneFeedActivity.class);
					oneFeedIntent.putExtra("Feed",
							(Serializable) (pi.getPostItemTransfer()));
					activity.startActivity(oneFeedIntent);
				}
			});
			holder.btnFollow.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					PostItem postItem = (PostItem) v.getTag();
					if(activity instanceof TabFeeds){
						TabFeeds tabFeeds = (TabFeeds) activity;
						tabFeeds.Follow(postItem.getEmail());
					}
					v.setVisibility(View.GONE);
				}
			});
			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		if (dataList.size() <= 0) {
			holder.feedContent.setText("No Data");
		} else {
			/***** Get each Model object from Arraylist ********/
			postItemTemp = null;
			postItemTemp = (PostItem) dataList.get(position);
			holder.feedContent.setText(postItemTemp.getContent());
			holder.nickName.setText(postItemTemp.getName().toUpperCase());
			holder.numOfLike.setText("" + postItemTemp.getnumOfLike());
			holder.numOfComment.setText("" + postItemTemp.getNumOfComment());
			holder.avatar.setImageBitmap(postItemTemp.getBitmapAvatar());
			holder.feedDate.setText(Utils.getDateString(postItemTemp
					.getPostDate()));
			if (postItemTemp.getIsLiked()) {
				holder.likeImage.setImageResource(R.drawable.hearticon);
			} else {
				holder.likeImage.setImageResource(R.drawable.notlike);
			}

			if (Utils.isFollow(activity, postItemTemp.getEmail())
					|| Utils.isFriend(activity, postItemTemp.getEmail())
					|| postItemTemp.getEmail().equals(
							LocalStore.getString(activity, Utils.TAG_EMAIL))) {
				holder.btnFollow.setVisibility(View.GONE);
			} else {
				holder.btnFollow.setVisibility(View.VISIBLE);
				holder.btnFollow.setTag(postItemTemp);
			}
			if (Utils.isFriend(activity, postItemTemp.getEmail())
					|| postItemTemp.getEmail().equals(
							LocalStore.getString(activity, Utils.TAG_EMAIL))) {
				holder.imgSocial.setVisibility(View.GONE);
			} else {
				holder.imgSocial.setVisibility(View.VISIBLE);
			}

			if (null != postItemTemp.getImageName()
					&& !postItemTemp.getImageName().isEmpty()) {
				holder.imgContent.setVisibility(View.VISIBLE);
				holder.imgContent
						.setImageBitmap(postItemTemp.getBitmapInPost());
			} else {
				holder.imgContent.setVisibility(View.GONE);
			}
			holder.avatar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (activity instanceof UserHomeActivity)
						return;
					String email = (String) v.getTag();
					Intent userHome = new Intent(activity,
							UserHomeActivity.class);
					userHome.putExtra("Email", email);
					activity.startActivity(userHome);
				}
			});
			
			if(activity instanceof UserHomeActivity){
				holder.btnFollow.setVisibility(View.GONE);
			}

			holder.imgComment.setTag(position);
			holder.likeImage.setTag(new ImageLikeTagObject(position,
					holder.numOfLike));
			holder.avatar.setTag(postItemTemp.getEmail());
		}

		return vi;
	}

}
