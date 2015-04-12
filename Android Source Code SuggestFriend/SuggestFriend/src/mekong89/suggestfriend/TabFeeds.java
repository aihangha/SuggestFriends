package mekong89.suggestfriend;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class TabFeeds extends Activity implements
		SwipeRefreshLayout.OnRefreshListener {
	static final int REQUEST_IMAGE_CAPTURE = 1;
	String lastupdate = "last30";
	private Boolean exit = false;
	Button btnPost;
	EditText txtContent;
	ListView listFeed;
	PostItemAdapter listAdapter;
	ArrayList<PostItem> feedArray;
	JSONParser jsonParser = new JSONParser();
	private SwipeRefreshLayout mSwipeLayout;
	ImageView imgPost;
	ImageView imgPostImageThumb;
	File capturedImage;
	String picturePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_feed);
		
		capturedImage = new File(Utils.getDataFolder(getApplicationContext())+"/captured.jpg");
		
		feedArray = new ArrayList<PostItem>();
		listFeed = (ListView) findViewById(R.id.listfeed);
		getFeedFromServer();
		listAdapter = new PostItemAdapter(this, feedArray);
		imgPost = (ImageView) findViewById(R.id.imgPostInFeed);
		listFeed.setAdapter(listAdapter);

		listFeed.setScrollingCacheEnabled(false);
		listFeed.setItemsCanFocus(true);
		listFeed.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				// Intent oneFeedIntent = new Intent(TabFeeds.this,
				// OneFeedActivity.class);
				// oneFeedIntent.putExtra("Feed",
				// (Serializable)feedArray.get(position));
				// startActivity(oneFeedIntent);
			}
		});
		final Dialog postDialog = new Dialog(this);

		postDialog.setContentView(R.layout.compose_dialog);
		postDialog.setTitle("New Post");
		Button dialogCancel = (Button) postDialog
				.findViewById(R.id.btnCancelInDialogPost);
		dialogCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				postDialog.dismiss();
			}
		});
		imgPostImageThumb = (ImageView) postDialog
				.findViewById(R.id.imgPostImageThumbInPostDialog);
		Button dialogPickImage = (Button) postDialog
				.findViewById(R.id.btnSelectImageInPostDialog);
		dialogPickImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent takePictureIntent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);			
				if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
					ActionBarActivity parentActivity = (ActionBarActivity) getParent();
					parentActivity.startActivityForResult(takePictureIntent,
							REQUEST_IMAGE_CAPTURE);
				}
			}
		});

		txtContent = (EditText) postDialog.findViewById(R.id.txtContentInDialog);
		Button btnDialogPost = (Button) postDialog
				.findViewById(R.id.btnPostInDialogPost);
		btnDialogPost.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(imgPostImageThumb.getVisibility()==View.GONE){
					if(txtContent.getText().toString().equals("")){
						Toast.makeText(getApplicationContext(), "No Post content", Toast.LENGTH_SHORT).show();
					} else{
						postStatusNoImage(txtContent.getText().toString(), "0");
						postDialog.dismiss();
					}
				} else{
					postStatusWithImage(txtContent.getText().toString(), "0");
					postDialog.dismiss();
				}
			}
		});

		imgPost.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imgPostImageThumb.setVisibility(View.GONE);
				postDialog.show();
			}
		});

		// txtContent = (EditText) findViewById(R.id.txtPostContent);
		// btnPost = (Button) findViewById(R.id.btnPostNew);
		// btnPost.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// String s = txtContent.getText().toString();
		// if (null != s && !s.equals("")) {
		// postStatus(s);
		// txtContent.setText("");
		// }
		// }
		// });

		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_red_light, android.R.color.holo_purple,
				android.R.color.holo_green_dark);

	}

	public void postStatusNoImage(String content, String whocansee){
		new PostStatus().execute(content,whocansee);
	}
	public void postStatusWithImage(String content, String whocansee){
		new PostStatusWithImage().execute(content,whocansee);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("data");
			imgPostImageThumb.setImageBitmap(imageBitmap);
			imgPostImageThumb.setVisibility(View.VISIBLE);

			Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);

	        // CALL THIS METHOD TO GET THE ACTUAL PATH
	        picturePath = getRealPathFromURI(tempUri);

		}
	}
	public Uri getImageUri(Context inContext, Bitmap inImage) {
	    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
	    String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
	    return Uri.parse(path);
	}

	public String getRealPathFromURI(Uri uri) {
	    Cursor cursor = getContentResolver().query(uri, null, null, null, null); 
	    cursor.moveToFirst(); 
	    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
	    return cursor.getString(idx); 
	}

	public void Follow(String email) {
		new Follow().execute(email);
	}

	protected void postStatus(String content) {
		// TODO Auto-generated method stub
		new PostStatus().execute(content, "0");
	}

	private void getFeedFromServer() {
		// TODO Auto-generated method stub
		new FeedNews().execute();
	}

	public void createLike(String feedid) {
		new CreateLike().execute(feedid);
	}

	public void deleteLike(String feedid) {
		new DeleteLike().execute(feedid);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		new FeedNews().execute();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (exit) {
			finish(); // finish activity
		} else {
			Toast.makeText(this, "Press Back again to Exit.",
					Toast.LENGTH_SHORT).show();
			exit = true;
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					exit = false;
				}
			}, 3 * 1000);

		}
		// super.onBackPressed();
	}

	/**
	 * Background Async Task to feed news
	 * */
	class FeedNews extends AsyncTask<String, String, String> {
		/**
		 * Getting product details in background thread
		 * */
		JSONArray jsonFeedArray;

		protected String doInBackground(String... value) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("xemail", "'"
						+ LocalStore.getString(getApplicationContext(),
								Utils.TAG_EMAIL) + "'"));
				params.add(new BasicNameValuePair("lastupdate", lastupdate));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_get_list_by_nick, "GET", params);

				Log.d("Mekong89", json.toString());
				// json success tag
				success = json.getInt(Utils.TAG_SUCCESS);
				if (success == 1) {
					// successfully received feeds
					jsonFeedArray = json.getJSONArray("feeds");
					if (null != jsonFeedArray) {

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
					if (null != jsonFeedArray) {
						for (int i = 0; i < jsonFeedArray.length(); i++) {
							try {
								JSONObject feed = (JSONObject) jsonFeedArray
										.get(i);
								boolean isLiked = false;
								if (feed.getString("liked").equals("1")) {
									isLiked = true;
								}
								PostItem postItem = new PostItem(
										getApplicationContext(),
										Long.parseLong(feed.getString("feedID")),
										feed.getString("feedowner"), feed
												.getString("feedcontent"),
										Integer.parseInt(feed
												.getString("NoLike")),
										Integer.parseInt(feed
												.getString("NoComment")), feed
												.getString("feedtimestamp"),
										isLiked, feed.getString("imagename"));
								for (int j = 0; j < feedArray.size(); j++) {
									PostItem item = feedArray.get(j);
									if (item.getID() == Long.parseLong(feed
											.getString("feedID"))) {
										feedArray.remove(j);
										j--;
									}
								}
								feedArray.add(0, postItem);
								lastupdate = feed.getString("lastupdate");
							} catch (Exception ex) {
								ex.printStackTrace();
							}

						}
						listAdapter.notifyDataSetChanged();
					}
					mSwipeLayout.setRefreshing(false);
				}
			});

			super.onPostExecute(result);
		}
	}

	/**
	 * Background Async Task to Get complete product details
	 * */
	public class PostStatus extends AsyncTask<String, String, String> {
		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... value) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("semail", LocalStore
						.getString(getApplicationContext(), Utils.TAG_EMAIL)));
				params.add(new BasicNameValuePair("scontent", value[0]));
				params.add(new BasicNameValuePair("swhocansee", value[1]));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_post_status, "POST", params);

				Log.d("Mekong89", json.toString());
				// json success tag
				success = json.getInt(Utils.TAG_SUCCESS);
				if (success == 1) {
					// successfully received product details
					Log.d("Mekong89", "" + json.getString(Utils.TAG_MESSAGE));
					getFeedFromServer();
				} else {
					Log.d("Mekong89", "" + json.getString(Utils.TAG_MESSAGE));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return "0";
		}
	}

	public class PostStatusWithImage extends AsyncTask<String, String, String> {
		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... value) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				MultipartEntity entity= new MultipartEntity();
				entity.addPart("semail", new StringBody(LocalStore
						.getString(getApplicationContext(), Utils.TAG_EMAIL)));
				entity.addPart("scontent", new StringBody(value[0]));
				entity.addPart("swhocansee", new StringBody(value[1]));
				File image = new File(picturePath);
				entity.addPart("file", new FileBody(image));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_post_status_have_image, "POST", entity);

				Log.d("Mekong89", json.toString());
				// json success tag
				success = json.getInt(Utils.TAG_SUCCESS);
				if (success == 1) {
					// successfully received product details
					Log.d("Mekong89", "" + json.getString(Utils.TAG_MESSAGE));
					getFeedFromServer();
				} else {
					Log.d("Mekong89", "" + json.getString(Utils.TAG_MESSAGE));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "0";
		}
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
				params.add(new BasicNameValuePair("semail", LocalStore
						.getString(getApplicationContext(), Utils.TAG_EMAIL)));
				params.add(new BasicNameValuePair("sfeedid", value[0]));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_create_like, "POST", params);
				if (null == json) {
					return "0";
				}
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
				params.add(new BasicNameValuePair("semail", LocalStore
						.getString(getApplicationContext(), Utils.TAG_EMAIL)));
				params.add(new BasicNameValuePair("sfeedid", value[0]));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_delete_like, "POST", params);
				if (null == json) {
					return "0";
				}
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

	public class Follow extends AsyncTask<String, String, String> {
		/**
		 * Getting product details in background thread
		 * */
		boolean isRequestSent = false;

		protected String doInBackground(String... value) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("user1", LocalStore
						.getString(getApplicationContext(), Utils.TAG_EMAIL)));
				params.add(new BasicNameValuePair("user2", value[0]));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(Utils.url_follow,
						"POST", params);
				if (null == json) {
					return "0";
				}
				Log.d("Mekong89", json.toString());
				// json success tag
				success = json.getInt(Utils.TAG_SUCCESS);
				if (success == 1) {
					// successfully received product details
					isRequestSent = true;
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
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (isRequestSent) {
						new UpdateFollowList().execute();
					}
				}
			});
		}
	}

	class UpdateFollowList extends AsyncTask<String, String, String> {
		JSONArray jsonFollowArray;
		JSONParser jsonParser = new JSONParser();

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... value) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("user1", LocalStore
						.getString(getApplicationContext(), Utils.TAG_EMAIL)));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_get_follow_list, "GET", params);
				if (null == json) {
					return "-1";
				}

				Log.d("Mekong89", json.toString());
				// json success tag
				success = json.getInt(Utils.TAG_SUCCESS);
				// String WIFIfriends = json.getJSONObject("users").toString();
				// Log.d("Mekong89",WIFIfriends);
				if (success == 1) {
					// successfully received product details
					Log.d("Mekong89",
							"Get Follow List Success! "
									+ json.getString(Utils.TAG_MESSAGE));
					jsonFollowArray = json.getJSONArray("follow");
					String followString = "";
					for (int i = 0; i < jsonFollowArray.length(); i++) {
						try {
							JSONObject result = (JSONObject) jsonFollowArray
									.get(i);

							String toUser = result.getString("user2");
							followString = followString + toUser + ";";

						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}

					followString = followString.substring(0,
							followString.length() - 1);
					LocalStore.saveString(getApplicationContext(),
							Utils.TAG_FOLLOWLIST, followString);
					Log.d("Mekong89", "Follow List" + followString);
				} else {
					Log.d("Mekong89",
							"Get Follow List Fail"
									+ json.getString(Utils.TAG_MESSAGE));
					LocalStore.saveString(getApplicationContext(),
							Utils.TAG_FOLLOWLIST, "");
					Log.d("Mekong89", "Follow List" + "");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return "0";
		}
	}
}
