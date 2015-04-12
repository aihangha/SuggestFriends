package mekong89.suggestfriend;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OneFeedActivity extends Activity {
	PostItemTransfer currentFeed;
	ImageView imgOwnerAvatar;
	TextView txtPostOwner;
	TextView txtFeedContent;
	ImageView imgFeedImage;
	ImageView likeImage;
	TextView txtNOLike;
	TextView txtNOComment;
	ListView lstComment;
	EditText txtPostContent;
	TextView txtPostTime;
	Button btnPost;
	JSONParser jsonParser = new JSONParser();
	ArrayList<PostItem> commentArray = new ArrayList<PostItem>();
	CommentItemAdapter listAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.onefeed);
		currentFeed = (PostItemTransfer) getIntent().getSerializableExtra("Feed");
		
		
		
		imgOwnerAvatar = (ImageView) findViewById(R.id.imgAvatarInOneFeed);
		txtPostOwner = (TextView) findViewById(R.id.txtUserNameInOneFeed);
		txtFeedContent = (TextView) findViewById(R.id.txtFeedContentInOneFeed);
		imgFeedImage = (ImageView) findViewById(R.id.imgFeedImageInOneFeed);
		likeImage = (ImageView) findViewById(R.id.imgLikeInOneFeed);
		txtNOLike = (TextView) findViewById(R.id.txtNOLikeInOneFeed);
		txtNOComment = (TextView) findViewById(R.id.txtNOCommentInOneFeed);
		lstComment = (ListView) findViewById(R.id.lstCommentOneFeed);
		txtPostContent = (EditText) findViewById(R.id.txtPostContentInOneFeed);
		btnPost= (Button) findViewById(R.id.btnPostInOneFeed);
		txtPostTime = (TextView) findViewById(R.id.txtTimeInOneFeed);
		
		listAdapter = new CommentItemAdapter(this,commentArray);
		lstComment.setAdapter(listAdapter);
		txtPostTime.setText(Utils.getDateString(currentFeed.postDate));
		imgOwnerAvatar.setImageBitmap(Utils.getBitmapFromURL(this, Utils.avatarAddress + currentFeed.getImageLink() , "avatar"));
		txtPostOwner.setText(currentFeed.getName());
		txtFeedContent.setText(currentFeed.content);
		if (null!=currentFeed.imagename && !currentFeed.imagename.isEmpty()) {
			imgFeedImage.setVisibility(View.VISIBLE);
			imgFeedImage.setImageBitmap(Utils.getBitmapFromURL(this, Utils.postImages + currentFeed.imagename , "post"));
		} else {
			imgFeedImage.setVisibility(View.GONE);
		}
		
		if(currentFeed.isLiked){
			likeImage.setImageResource(R.drawable.hearticon);
    	} else{
    		likeImage.setImageResource(R.drawable.notlike);
    	}
		txtNOLike.setText(""+currentFeed.numOfLike);
		txtNOComment.setText(""+currentFeed.numOfComment);
		
		likeImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(currentFeed.isLiked){
					currentFeed.isLiked = false;
					likeImage.setImageResource(R.drawable.notlike);
					currentFeed.numOfLike--;
					new DeleteLike().execute();
				} else{
					currentFeed.isLiked = true;
					likeImage.setImageResource(R.drawable.hearticon);
					currentFeed.numOfLike++;
					new CreateLike().execute();
				}
				txtNOLike.setText(""+currentFeed.numOfLike);
			}
		});
		
		btnPost.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new PostComment().execute(txtPostContent.getText().toString());
				txtPostContent.setText("");
			}
		});
		
		getComments();
	}
	
	public void getComments(){
		new GetComments().execute();
	}
	/**
	 * Background Async Task to Get complete product details
	 * */
	public class CreateLike extends AsyncTask<String, String, String> {
		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... value) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("semail", LocalStore.getString(getApplicationContext(),Utils.TAG_EMAIL)));
				params.add(new BasicNameValuePair("sfeedid", ""+currentFeed.id));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_create_like, "POST", params);
				if(null==json) 
				{return "0";}
				Log.d("Mekong89", json.toString());
				// json success tag
				success = json.getInt(Utils.TAG_SUCCESS);
				if (success == 1) {
					// successfully received product details
					Log.d("Mekong89", "" + json.getString(Utils.TAG_MESSAGE));

				} else {
					Log.d("Mekong89", "" + json.getString(Utils.TAG_MESSAGE));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return "0";
		}
	}

	/**
	 * Background Async Task to Get complete product details
	 * */
	class DeleteLike extends AsyncTask<String, String, String> {
		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... value) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("semail", LocalStore.getString(getApplicationContext(),Utils.TAG_EMAIL)));
				params.add(new BasicNameValuePair("sfeedid", ""+currentFeed.id));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_delete_like, "POST", params);
				if(null==json) 
					{return "0";}
				Log.d("Mekong89", json.toString());
				// json success tag
				success = json.getInt(Utils.TAG_SUCCESS);
				if (success == 1) {
					// successfully received product details
					Log.d("Mekong89", "" + json.getString(Utils.TAG_MESSAGE));

				} else {
					Log.d("Mekong89", "" + json.getString(Utils.TAG_MESSAGE));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return "0";
		}
	}
	
	/**
	 * Background Async Task to feed news
	 * */
	public class GetComments extends AsyncTask<String, String, String> {
		/**
		 * Getting product details in background thread
		 * */
		JSONArray jsonCommentArray;

		protected String doInBackground(String... value) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("feedid","'"
						+ currentFeed.id + "'"));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_get_comment, "GET", params);

				if(null==json){
					return "-1";
				}
				Log.d("Mekong89", json.toString());
				commentArray.clear();
				// json success tag
				success = json.getInt(Utils.TAG_SUCCESS);
				if (success == 1) {
					// successfully received feeds
					jsonCommentArray = json.getJSONArray("comments");
					if (null != jsonCommentArray) {
						for (int i = 0; i < jsonCommentArray.length(); i++) {
							try {
								JSONObject feed = (JSONObject) jsonCommentArray.get(i);
								PostItem postItem = new PostItem(getApplicationContext(),
										Long.parseLong(feed.getString("id")),
										feed.getString("email"), feed
												.getString("content"),
										0,0, feed
												.getString("createdate"),false,null);
								
								commentArray.add(postItem);														
							} catch (Exception ex) {
								ex.printStackTrace();
							}

						}
					}
					Log.d("Mekong89", "" + json.getString(Utils.TAG_MESSAGE));

				} else {
					Log.d("Mekong89", "" + json.getString(Utils.TAG_MESSAGE));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return "0";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			runOnUiThread(new Runnable() {
				public void run() {
					if (null != jsonCommentArray) {
						listAdapter.notifyDataSetChanged();	
						txtNOComment.setText(""+commentArray.size());
					}
				}
			});

			super.onPostExecute(result);
		}
	}

	/**
	 * Background Async Task to Get complete product details
	 * */
	public class PostComment extends AsyncTask<String, String, String> {
		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... value) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("feedid",""+currentFeed.id));
				params.add(new BasicNameValuePair("content", value[0]));
				params.add(new BasicNameValuePair("email", LocalStore.getString(getApplicationContext(),Utils.TAG_EMAIL)));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_create_comment, "POST", params);
				if(null==json){
					return "-1";
				}
				Log.d("Mekong89", json.toString());
				// json success tag
				success = json.getInt(Utils.TAG_SUCCESS);
				if (success == 1) {
					// successfully received product details
					Log.d("Mekong89", "" + json.getString(Utils.TAG_MESSAGE));
					getComments();
				} else {
					Log.d("Mekong89", "" + json.getString(Utils.TAG_MESSAGE));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return "0";
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			runOnUiThread(new Runnable() {
				public void run() {
					
				}
			});

			super.onPostExecute(result);
		}
	}

	
}
