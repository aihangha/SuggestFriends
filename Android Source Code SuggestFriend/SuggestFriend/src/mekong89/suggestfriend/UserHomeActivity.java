package mekong89.suggestfriend;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UserHomeActivity extends Activity {

	ImageView imgAvatar;
	TextView txtUsername;
	Button btnAddFriend;
	Button btnFollow;
	Button btnChat;
	ListView listUserFeed;
	ArrayList<PostItem> feedArray = new ArrayList<PostItem>();
	PostItemAdapter feedItemAdapter;
	// TextView txtBirthday;
	// TextView txtCellPhone;
	// TextView txtFacebook;
	// TextView txtEmail;
	// TextView txtLog;
	// ListView meetList;
	// List<FriendEntry> logList;

	String email = "";
	JSONParser jsonParser = new JSONParser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userhome);

		Bundle b = getIntent().getExtras();
		email = b.getString("Email");

		// allow internet access strict
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		txtUsername = (TextView) findViewById(R.id.txtUserNameInHome);
		imgAvatar = (ImageView) findViewById(R.id.imgAvatarInHome);

		txtUsername.setText(Utils.getNameFromEmail(email));
		imgAvatar.setImageBitmap(Utils.getBitmapFromURL(
				getApplicationContext(),
				Utils.avatarAddress + Utils.getNameFromEmail(email) + "64.jpg",
				"avatar"));

		btnAddFriend = (Button) findViewById(R.id.btnAddFriendInHome);
		btnChat = (Button) findViewById(R.id.btnChatInHome);
		btnFollow = (Button) findViewById(R.id.btnFollowInHome);

		listUserFeed = (ListView) findViewById(R.id.lstFeedInHome);
		feedItemAdapter = new PostItemAdapter(this, feedArray);
		listUserFeed.setAdapter(feedItemAdapter);

		if (email.endsWith(LocalStore.getString(getApplicationContext(),
				Utils.TAG_EMAIL))) {
			btnAddFriend.setVisibility(View.GONE);
			btnFollow.setVisibility(View.GONE);
			btnChat.setVisibility(View.GONE);
		}
		// if(Utils.isFriend(getApplicationContext(), email)){
		// btnAddFriend.setVisibility(View.GONE);
		// btnFollow.setVisibility(View.GONE);
		// }
		// if(Utils.isFollow(getApplicationContext(), email)){
		// btnFollow.setVisibility(View.GONE);
		// }

		btnChat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent messageActivity = new Intent(UserHomeActivity.this,
						xMessage.class);
				messageActivity.putExtra("friendMail", email);
				startActivity(messageActivity);
			}
		});

		btnAddFriend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (btnAddFriend.getText().toString().equals("Add Friend")) {
					new RequestFriend().execute();
				} else if (btnAddFriend.getText().toString().equals("Accept Friend")) {
					new AcceptRequestFriend().execute();
				} else if (btnAddFriend.getText().toString().equals("Unfriend")){
					new UnFriend().execute();
				}
			}
		});

		btnFollow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(btnFollow.getText().toString().equals("Follow")){
					new Follow().execute();
				} else{
					new UnFollow().execute();
				}
			}
		});
		
		// txtLog = (TextView) findViewById(R.id.txtLog);
		// meetList = (ListView) findViewById(R.id.lstLog);
		// SQLiteHandler db = SQLiteHandler.getHelper(getApplicationContext());
		// logList = db.getListOfOneFriend(friendEmail);
		// ArrayList<String> logData = new ArrayList<String>();
		//
		// for (FriendEntry log : logList) {
		// // logContent = logContent +
		// //
		// "Location:"+log.getLocLat()+"-"+log.getLocLong()+"   Time:"+getDate(log.getTimeStamp())+"\n";
		// // stringAdapter.add(getDate(log.getTimeStamp()));
		// String locationName = "";
		// locationName = getLoccationName(log.getLocLat(),log.getLocLong());
		// if(null == locationName || locationName.equals("")){
		// logData.add(getDate(log.getTimeStamp()));
		// } else{
		// logData.add(getDate(log.getTimeStamp()) + " near " + locationName);
		// }
		// }
		// // txtLog.setText(logContent);
		// ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(
		// UserHomeActivity.this, android.R.layout.simple_list_item_1,
		// android.R.id.text1, logData);
		// meetList.setAdapter(stringAdapter);
		// meetList.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1,
		// int position, long arg3) {
		// // TODO Auto-generated method stub
		// startActivity(new Intent(android.content.Intent.ACTION_VIEW,
		// Uri.parse("geo:" + logList.get(position).getLocLat()
		// + "," + logList.get(position).getLocLong()
		// + "?q=" + logList.get(position).getLocLat()
		// + "," + logList.get(position).getLocLong())));
		// }
		// });
		new CheckFriendRelate().execute();
		new FeedNews().execute();
		new GetFollowState().execute();
		// new GetFriendDetails().execute();
	}

	public void createLike(String feedid) {
		new CreateLike().execute(feedid);
	}

	public void deleteLike(String feedid) {
		new DeleteLike().execute(feedid);
	}

	private String getLoccationName(float loclat, float loclong) {
		String _Location = "";
		Geocoder geocoder = new Geocoder(getApplicationContext(),
				Locale.getDefault());
		try {
			List<Address> listAddresses = geocoder.getFromLocation(loclat,
					loclong, 1);
			if (null != listAddresses && listAddresses.size() > 0) {
				// _Location = listAddresses.get(0).getAddressLine(0);
				_Location = listAddresses.get(0).getFeatureName() + " "
						+ listAddresses.get(0).getSubAdminArea();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return _Location;
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("SimpleDateFormat")
	private String getDate(long timeStamp) {

		try {
			DateFormat sdf = new SimpleDateFormat("hh:mm a");
			Date storedDate = (new Date(timeStamp));
			Date todayDate = (new Date(System.currentTimeMillis()));
			if (storedDate.getDay() == todayDate.getDay()) {
				return sdf.format(storedDate) + " today";
			} else {
				return sdf.format(storedDate) + " yesterday";
			}
		} catch (Exception ex) {
			return "xx";
		}
	}

	/**
	 * Background Async Task to Get complete product details
	 * */
	class GetFriendDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... params) {

			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					// Check for success tag
					int success;
					try {
						// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair(Utils.TAG_EMAIL, "'"
								+ email + "'"));
						// getting user details by making HTTP request
						// Note that product details url will use GET request
						JSONObject json = jsonParser.makeHttpRequest(
								Utils.url_get_user, "GET", params);
						// check your log for json response
						Log.d("Single User Details", json.toString());

						// json success tag
						success = json.getInt(Utils.TAG_SUCCESS);
						if (success == 1) {
							// successfully received product details
							JSONArray userObj = json
									.getJSONArray(Utils.TAG_USER); // JSON Array

							// get first product object from JSON Array
							JSONObject user = userObj.getJSONObject(0);

							// product with this pid found
							// Edit Text

						} else {
							// user with email not found
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once got all details
		}
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
				params.add(new BasicNameValuePair("email", "'" + email + "'"));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_feed_by_user, "GET", params);
				feedArray.clear();
				if (null == json) {
					return "-1";
				}
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
							} catch (Exception ex) {
								ex.printStackTrace();
							}

						}
						feedItemAdapter.notifyDataSetChanged();
					}
				}
			});

			super.onPostExecute(result);
		}
	}

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

	public class RequestFriend extends AsyncTask<String, String, String> {
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
				params.add(new BasicNameValuePair("user2", email));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_request_friend, "POST", params);
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
						btnAddFriend.setText("Request Sent");
						btnAddFriend.setEnabled(false);
					}
				}
			});
		}
	}

	class CheckFriendRelate extends AsyncTask<String, String, String> {
		/**
		 * Getting product details in background thread
		 * */
		JSONArray jsonFriendArray;
		int success;

		protected String doInBackground(String... value) {

			// Check for success tag

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("user1", LocalStore
						.getString(getApplicationContext(), Utils.TAG_EMAIL)));
				params.add(new BasicNameValuePair("user2", email));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_check_friend_relate, "GET", params);
				if (null == json) {
					return "-1";
				}
				Log.d("Mekong89", json.toString());
				// json success tag
				success = json.getInt(Utils.TAG_SUCCESS);
				if (success == 1) {
					// successfully received feeds
					jsonFriendArray = json.getJSONArray("friend");
					if (null != jsonFriendArray) {

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
					if (success == 1) {

						try {
							JSONObject result = (JSONObject) jsonFriendArray
									.get(0);

							String fromUser = result.getString("user1");
							String toUser = result.getString("user2");
							String isReaded = result.getString("isreaded");
							String isAccept = result.getString("isaccept");

							if (isAccept.equals("1")) {
								btnAddFriend.setEnabled(true);
								btnAddFriend.setText("Unfriend");
								btnFollow.setVisibility(View.GONE);
							} else {
								if (toUser.equals(email)) { // request from me
									btnAddFriend.setEnabled(false);
									btnAddFriend.setText("Request Sent");
								} else { // request from user
									btnAddFriend.setEnabled(true);
									btnAddFriend
											.setText("Accept Friend");
								}
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}

					} else {
						btnAddFriend.setEnabled(true);
						btnAddFriend.setText("Add Friend");
					}

				}
			});

			super.onPostExecute(result);
		}
	}
	public class AcceptRequestFriend extends AsyncTask<String, String, String> {
		/**
		 * Getting product details in background thread
		 * */
		boolean isAcceptSent = false;

		protected String doInBackground(String... value) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("user2", LocalStore
						.getString(getApplicationContext(), Utils.TAG_EMAIL)));
				params.add(new BasicNameValuePair("user1", email));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_answer_request, "POST", params);
				if (null == json) {
					return "0";
				}
				Log.d("Mekong89", json.toString());
				// json success tag
				success = json.getInt(Utils.TAG_SUCCESS);
				if (success == 1) {
					// successfully received product details
					isAcceptSent = true;
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
					if (isAcceptSent) {
						btnAddFriend.setText("Unfriend");
						btnAddFriend.setEnabled(true);
						btnFollow.setVisibility(View.GONE);
						new UpdateFriendList().execute();
					}
				}
			});
		}
	}
	
	public class UnFriend extends AsyncTask<String, String, String> {
		/**
		 * Getting product details in background thread
		 * */
		boolean isUnFriendSent = false;

		protected String doInBackground(String... value) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("user2", LocalStore
						.getString(getApplicationContext(), Utils.TAG_EMAIL)));
				params.add(new BasicNameValuePair("user1", email));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_unfriend, "POST", params);
				if (null == json) {
					return "0";
				}
				Log.d("Mekong89", json.toString());
				// json success tag
				success = json.getInt(Utils.TAG_SUCCESS);
				if (success == 1) {
					// successfully received product details
					isUnFriendSent = true;
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
					if (isUnFriendSent) {
						btnAddFriend.setText("Add Friend");
						btnAddFriend.setEnabled(true);
					}
				}
			});
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
				params.add(new BasicNameValuePair("user2", email));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_follow, "POST", params);
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
						btnFollow.setText("Unfollow");
						new UpdateFollowList().execute();
					}
				}
			});
		}
	}
	
	public class UnFollow extends AsyncTask<String, String, String> {
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
				params.add(new BasicNameValuePair("user2", email));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_unfollow, "POST", params);
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
						btnFollow.setText("Follow");
						new UpdateFollowList().execute();
					}
				}
			});
		}
	}
	
	public class GetFollowState extends AsyncTask<String, String, String> {
		/**
		 * Getting product details in background thread
		 * */
		boolean isFollow = false;

		protected String doInBackground(String... value) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("user1", LocalStore
						.getString(getApplicationContext(), Utils.TAG_EMAIL)));
				params.add(new BasicNameValuePair("user2", email));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_get_follow_state, "GET", params);
				if (null == json) {
					return "0";
				}
				Log.d("Mekong89", json.toString());
				// json success tag
				success = json.getInt(Utils.TAG_SUCCESS);
				if (success == 1) {
					// successfully received product details
					isFollow = true;
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
					if (isFollow) {
						btnFollow.setText("Unfollow");
					} else{
						btnFollow.setText("Follow");
					}
				}
			});
		}
	}
	
	class UpdateFriendList extends AsyncTask<String, String, String> {
		JSONArray jsonFriendArray;
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
				params.add(new BasicNameValuePair("email", "'"
						+ LocalStore.getString(getApplicationContext(),
								Utils.TAG_EMAIL) + "'"));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_get_friend, "GET", params);
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
							"Get Friend List Success! "
									+ json.getString(Utils.TAG_MESSAGE));
					jsonFriendArray = json.getJSONArray("friend");
					String friendString="";
					for (int i = 0; i < jsonFriendArray.length(); i++) {
						try {
							JSONObject result = (JSONObject) jsonFriendArray
									.get(i);

							String fromUser = result.getString("user1");
							String toUser = result.getString("user2");
							String isAccept = result.getString("isaccept");
							FriendListItem friendItem;
							String friendMail = "";
							if (isAccept.equals("1")) {
								if (toUser.equals(LocalStore.getString(
										getApplicationContext(),
										Utils.TAG_EMAIL))) {
									friendMail = fromUser;
								} else {
									friendMail = toUser;
								}	
								friendString = friendString + friendMail +";";
							} 

						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					
					friendString = friendString.substring(0,friendString.length()-1);
					LocalStore.saveString(getApplicationContext(), Utils.TAG_FRIENDLIST, friendString);
					Log.d("Mekong89","Friend List:"+friendString);
				} else {
					Log.d("Mekong89",
							"Get Friend List Fail"
									+ json.getString(Utils.TAG_MESSAGE));
					LocalStore.saveString(getApplicationContext(), Utils.TAG_FRIENDLIST, "");
					Log.d("Mekong89","Friend List:"+"");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return "0";
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
				params.add(new BasicNameValuePair("user1", 
						LocalStore.getString(getApplicationContext(),
								Utils.TAG_EMAIL) ));
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
					String followString="";
					for (int i = 0; i < jsonFollowArray.length(); i++) {
						try {
							JSONObject result = (JSONObject) jsonFollowArray
									.get(i);

							String toUser = result.getString("user2");
							followString = followString + toUser +";";


						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					
					followString = followString.substring(0,followString.length()-1);
					LocalStore.saveString(getApplicationContext(), Utils.TAG_FOLLOWLIST, followString);
					Log.d("Mekong89","Follow List"+followString);
				} else {
					Log.d("Mekong89",
							"Get Follow List Fail"
									+ json.getString(Utils.TAG_MESSAGE));
					LocalStore.saveString(getApplicationContext(), Utils.TAG_FOLLOWLIST, "");
					Log.d("Mekong89","Follow List"+"");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return "0";
		}
	}
}
