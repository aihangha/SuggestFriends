package mekong89.suggestfriend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class TabFriends extends Activity {
	ListView listFriend;
	JSONParser jsonParser = new JSONParser();
	ArrayList<FriendListItem> friendArray = new ArrayList<FriendListItem>();
	FriendListAdapter friendListAdapater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_friends);
		// allow internet access strict
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// start service
		Intent intentService = new Intent(getApplicationContext(),
				CheckOnlineService.class);
		this.startService(intentService);

		listFriend = (ListView) findViewById(R.id.lstFriendInFriendsList);
		listFriend.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> data, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Intent userHome = new Intent(TabFriends.this,
						UserHomeActivity.class);
				userHome.putExtra("Email", friendArray.get(position).email);
				startActivity(userHome);
				// Toast.makeText(Friends.this,
				// friendArray.get(position).toString(),
				// Toast.LENGTH_SHORT).show();
			}
		});

		friendListAdapater = new FriendListAdapter(this, friendArray);
		listFriend.setAdapter(friendListAdapater);

		new GetFriendList().execute();
		new GetFollowList().execute();
	}

	public void AcceptFriendRequest(String email) {
		new AcceptRequestFriend().execute(email);
	}

	// /**
	// * Background Async Task to Get complete product details
	// * */

	class GetFriendList extends AsyncTask<String, String, String> {
		JSONArray jsonFriendArray;

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

				} else {
					Log.d("Mekong89",
							"Get WIFI List Fail"
									+ json.getString(Utils.TAG_MESSAGE));
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
					if (null!=jsonFriendArray) {
						for (int i = 0; i < jsonFriendArray.length(); i++) {
							try {
								JSONObject result = (JSONObject) jsonFriendArray
										.get(i);

								String fromUser = result.getString("user1");
								String toUser = result.getString("user2");
								String isReaded = result.getString("isreaded");
								String isAccept = result.getString("isaccept");
								FriendListItem friendItem;
								if (isAccept.equals("1")) {
									if (toUser.equals(LocalStore.getString(
											getApplicationContext(),
											Utils.TAG_EMAIL))) {
										friendItem = new FriendListItem(
												fromUser, true);
									} else {
										friendItem = new FriendListItem(toUser,
												true);
									}
									friendArray.add(friendItem);
								} else {
									if (toUser.equals(LocalStore.getString(
											getApplicationContext(),
											Utils.TAG_EMAIL))) { // request to
																	// me
										friendItem = new FriendListItem(
												fromUser, false);
										friendArray.add(friendItem);
									}
								}

							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					} else {
						Toast.makeText(TabFriends.this, "You have no friend",
								Toast.LENGTH_LONG).show();
					}
					Collections.sort(friendArray,new Comparator<FriendListItem>() {

						@Override
						public int compare(FriendListItem lhs,
								FriendListItem rhs) {
							// TODO Auto-generated method stub
							if( lhs.isFriend && ! rhs.isFriend ) {
							      return +1;
							   }
							   if( ! lhs.isFriend && rhs.isFriend ) {
							      return -1;
							   }
							   return 0;
						}
					});
					friendListAdapater.notifyDataSetChanged();
				}
			});

			super.onPostExecute(result);
		}
	}

	class GetFollowList extends AsyncTask<String, String, String> {
		String followListRaw = "";

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
						Utils.url_get_follow, "GET", params);
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
					followListRaw = json.getString("follow");
					if (!followListRaw.equals("")) {
						LocalStore.saveString(getApplicationContext(),
								Utils.TAG_FOLLOWLIST, followListRaw);
					}

				} else {
					Log.d("Mekong89",
							"Get Follow List Fail"
									+ json.getString(Utils.TAG_MESSAGE));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return "0";
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
				params.add(new BasicNameValuePair("user1", value[0]));
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
	}
	
}
