package mekong89.suggestfriend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.interpolator;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class TabMessages extends Activity {
	private Boolean exit = false;
	ListView listFriend;
	JSONParser jsonParser = new JSONParser();
	ArrayList<String> chatArrayList = new ArrayList<String>();
	ChatListAdapter chatListAdapater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_messages);
		// allow internet access strict
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// start service
		Intent intentService = new Intent(getApplicationContext(),
				CheckOnlineService.class);
		this.startService(intentService);

		listFriend = (ListView) findViewById(R.id.lstFriendInMessagesList);
		listFriend.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> data, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Intent messageActivity = new Intent(TabMessages.this,
						xMessage.class);
				messageActivity.putExtra("friendMail", chatArrayList
						.get(position));
				startActivity(messageActivity);

				// Toast.makeText(Friends.this,
				// friendArray.get(position).toString(),
				// Toast.LENGTH_SHORT).show();
			}
		});
		addItems();
		chatListAdapater = new ChatListAdapter(this, chatArrayList);
		listFriend.setAdapter(chatListAdapater);


		
		//new GetFriendList().execute();
		//new GetFollowList().execute();

		// Friends.this.startService(new
		// Intent(getApplicationContext(),CheckOnlineService.class));

		// btnTestWifi = (Button) findViewById(R.id.btnTest);
		// btnTestWifi.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Friends.this.startService(new Intent(getApplicationContext(),
		// CheckOnlineService.class));
		// }
		// });
		//
		// btnFindWIFIFriend = (Button) findViewById(R.id.btnFindWifiFriend);
		// btnFindWIFIFriend.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// new FindWIFIFriends().execute();
		// }
		// });
		//
		// btnFindNearFriend100 = (Button)
		// findViewById(R.id.btnFindGPSFriend100);
		// btnFindNearFriend100.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// location = Utils.getLocation(getApplicationContext());
		// new FindGPSFriends().execute("100");
		// }
		// });
		// btnFindNearFriend500 = (Button)
		// findViewById(R.id.btnFindGPSFriend500);
		// btnFindNearFriend500.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// location = Utils.getLocation(getApplicationContext());
		// new FindGPSFriends().execute("500");
		// }
		// });
		// btnFindNearFriend10km = (Button)
		// findViewById(R.id.btnFindGPSFriend10km);
		// btnFindNearFriend10km.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// location = Utils.getLocation(getApplicationContext());
		// new FindGPSFriends().execute("10000");
		// }
		// });
		//
		// btnClearDB = (Button) findViewById(R.id.btnClearDB);
		// btnClearDB.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// SQLiteHandler db = SQLiteHandler
		// .getHelper(getApplicationContext());
		// db.deleteAll();
		// }
		// });
		//
		// btnFindFriend = (Button) findViewById(R.id.btnFindFriend);
		// btnFindFriend.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// startActivity(new Intent(Friends.this,
		// ListSuggestActivity.class));
		// }
		// });
		// txtContent = (EditText) findViewById(R.id.txtfeedcontent);
		// btnPost = (Button) findViewById(R.id.btnPost);
		// btnPost.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// postStatus(txtContent.getText().toString());
		// }
		// });
		//
		// location = Utils.getLocation(getApplicationContext());
		// if(location==null){
		// btnFindNearFriend100.setEnabled(false);
		// btnFindNearFriend500.setEnabled(false);
		// btnFindNearFriend10km.setEnabled(false);
		// }
	}

	private void addItems() {
		// TODO Auto-generated method stub
		String lastChatListRaw = LocalStore.getString(getApplicationContext(), Utils.TAG_CHATLIST);
		if(lastChatListRaw.isEmpty()) return;
		String[] chatArray = lastChatListRaw.split(";");
		List<String> chatList = Arrays.asList(chatArray);
		chatArrayList.clear();
		chatArrayList.addAll(chatList);
	}

	public void OnTabClick() {
		// TODO Auto-generated method stub
		addItems();		
		chatListAdapater.notifyDataSetChanged();
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
						Utils.url_get_friend(getApplicationContext()), "GET", params);
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
									
								} 

							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					} else {
						
					}
				
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
						Utils.url_get_follow(getApplicationContext()), "GET", params);
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
						LocalStore.saveString(getApplicationContext(), Utils.TAG_FOLLOWLIST, followListRaw);
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

	// class FindWIFIFriends extends AsyncTask<String, String, String> {
	// ArrayList<String> userArray = new ArrayList<String>();
	//
	// /**
	// * Getting product details in background thread
	// * */
	// protected String doInBackground(String... value) {
	//
	// // Check for success tag
	// int success;
	// try {
	// // Building Parameters
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	// params.add(new BasicNameValuePair("MAC", Utils
	// .getWIFIRouterMacId(getApplicationContext())));
	// // getting user details by making HTTP request
	// // Note that product details url will use GET request
	// JSONObject json = jsonParser.makeHttpRequest(
	// Utils.url_get_wifi_list, "POST", params);
	// Log.d("Mekong89", json.toString());
	// // json success tag
	// success = json.getInt(Utils.TAG_SUCCESS);
	// // String WIFIfriends = json.getJSONObject("users").toString();
	// // Log.d("Mekong89",WIFIfriends);
	// if (success == 1) {
	// // successfully received product details
	// Log.d("Mekong89",
	// "Get WIFI List Success! "
	// + json.getString(Utils.TAG_MESSAGE));
	// JSONArray arrayUser = json.getJSONArray("users");
	//
	// for (int i = 0; i < arrayUser.length(); i++) {
	// if (!arrayUser
	// .get(i)
	// .toString()
	// .equals(LocalStore.getString(
	// getApplicationContext(),
	// Utils.TAG_EMAIL))) {
	// Log.d("Mekong89", arrayUser.get(i).toString());
	// userArray.add(arrayUser.get(i).toString());
	// }
	//
	// }
	//
	// } else {
	// Log.d("Mekong89",
	// "Get WIFI List Fail"
	// + json.getString(Utils.TAG_MESSAGE));
	// }
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	//
	// return "0";
	// }
	//
	// @Override
	// protected void onPostExecute(String result) {
	// // TODO Auto-generated method stub
	// runOnUiThread(new Runnable() {
	// public void run() {
	// if (userArray.size() > 0) {
	// AlertDialog.Builder builder = new AlertDialog.Builder(
	// Friends.this);
	// builder.setTitle("User on this WIFI:");
	//
	// ListView modeList = new ListView(Friends.this);
	// ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(
	// Friends.this,
	// android.R.layout.simple_list_item_1,
	// android.R.id.text1, userArray);
	// modeList.setAdapter(modeAdapter);
	//
	// builder.setView(modeList);
	// final Dialog dialog = builder.create();
	//
	// modeList.setOnItemClickListener(new OnItemClickListener() {
	//
	// @Override
	// public void onItemClick(AdapterView<?> arg0,
	// View arg1, int position, long arg3) {
	// // TODO Auto-generated method stub
	// Log.d("Mekong89", "Gonna launch friend: "
	// + userArray.get(position));
	// Intent intent = new Intent(Friends.this,
	// AddFriendActivity.class);
	// Bundle b = new Bundle();
	// b.putString("Email", userArray.get(position)); // Your
	// // id
	// intent.putExtras(b); // Put your id to your next
	// // Intent
	// startActivity(intent);
	// dialog.dismiss();
	// }
	// });
	//
	// dialog.show();
	// } else {
	// Toast.makeText(Friends.this, "No user found",
	// Toast.LENGTH_LONG).show();
	// }
	// }
	// });
	//
	// super.onPostExecute(result);
	// }
	// }
	//

	// class FindGPSFriends extends AsyncTask<String, String, String> {
	// ArrayList<String> userArray = new ArrayList<String>();
	//
	// /**
	// * Getting product details in background thread
	// * */
	// protected String doInBackground(String... value) {
	//
	// // Check for success tag
	// int success;
	// try {
	// // Building Parameters
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	// params.add(new BasicNameValuePair("lat", String
	// .valueOf(location.getLatitude())));
	// params.add(new BasicNameValuePair("long", String
	// .valueOf(location.getLongitude())));
	// params.add(new BasicNameValuePair("radius", String
	// .valueOf(0.00000898 * Integer.parseInt(value[0]))));
	// // getting user details by making HTTP request
	// // Note that product details url will use GET request
	// JSONObject json = jsonParser.makeHttpRequest(
	// Utils.url_get_gps_list, "POST", params);
	// Log.d("Mekong89", json.toString());
	// // json success tag
	// success = json.getInt(Utils.TAG_SUCCESS);
	// // String WIFIfriends = json.getJSONObject("users").toString();
	// // Log.d("Mekong89",WIFIfriends);
	// if (success == 1) {
	// // successfully received product details
	// Log.d("Mekong89",
	// "Get GPS List Success! "
	// + json.getString(Utils.TAG_MESSAGE));
	// JSONArray arrayUser = json.getJSONArray("users");
	//
	// for (int i = 0; i < arrayUser.length(); i++) {
	// if (!arrayUser
	// .get(i)
	// .toString()
	// .equals(LocalStore.getString(
	// getApplicationContext(),
	// Utils.TAG_EMAIL))) {
	// Log.d("Mekong89", arrayUser.get(i).toString());
	// userArray.add(arrayUser.get(i).toString());
	// }
	//
	// }
	//
	// } else {
	// Log.d("Mekong89",
	// "Get GPS List Fail! "
	// + json.getString(Utils.TAG_MESSAGE));
	// }
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	//
	// return "0";
	// }
	//
	// @Override
	// protected void onPostExecute(String result) {
	// // TODO Auto-generated method stub
	// runOnUiThread(new Runnable() {
	// public void run() {
	// if (userArray.size() > 0) {
	// AlertDialog.Builder builder = new AlertDialog.Builder(
	// Friends.this);
	// builder.setTitle("User on this WIFI:");
	//
	// ListView modeList = new ListView(Friends.this);
	// ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(
	// Friends.this,
	// android.R.layout.simple_list_item_1,
	// android.R.id.text1, userArray);
	// modeList.setAdapter(modeAdapter);
	//
	// builder.setView(modeList);
	// final Dialog dialog = builder.create();
	//
	// modeList.setOnItemClickListener(new OnItemClickListener() {
	//
	// @Override
	// public void onItemClick(AdapterView<?> arg0,
	// View arg1, int position, long arg3) {
	// // TODO Auto-generated method stub
	// Log.d("Mekong89", "Gonna launch friend: "
	// + userArray.get(position));
	// Intent intent = new Intent(Friends.this,
	// AddFriendActivity.class);
	// Bundle b = new Bundle();
	// b.putString("Email", userArray.get(position)); // Your
	// // id
	// intent.putExtras(b); // Put your id to your next
	// // Intent
	// startActivity(intent);
	// dialog.dismiss();
	// }
	// });
	//
	// dialog.show();
	// } else {
	// Toast.makeText(Friends.this, "No user found",
	// Toast.LENGTH_LONG).show();
	// }
	// }
	// });
	//
	// super.onPostExecute(result);
	// }
	// }
	//
	// /**
	// * Background Async Task to Get complete product details
	// * */
	// class PostStatus extends AsyncTask<String, String, String> {
	// /**
	// * Getting product details in background thread
	// * */
	// protected String doInBackground(String... value) {
	//
	// // Check for success tag
	// int success;
	// try {
	// // Building Parameters
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	// params.add(new BasicNameValuePair("semail", "mekong89@gmail.com"));
	// params.add(new BasicNameValuePair("scontent",
	// value[0]));
	// params.add(new BasicNameValuePair("swhocansee", value[1]));
	// // getting user details by making HTTP request
	// // Note that product details url will use GET request
	// JSONObject json = jsonParser.makeHttpRequest(
	// Utils.url_post_status, "POST", params);
	//
	// Log.d("Mekong89", json.toString());
	// // json success tag
	// success = json.getInt(Utils.TAG_SUCCESS);
	// if (success == 1) {
	// // successfully received product details
	// Log.d("Mekong89", "" + json.getString(Utils.TAG_MESSAGE));
	//
	// } else {
	// Log.d("Mekong89", "" + json.getString(Utils.TAG_MESSAGE));
	// }
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// return "0";
	// }
	// }
}
