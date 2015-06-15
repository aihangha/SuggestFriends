package mekong89.suggestfriend;

import java.util.ArrayList;
import java.util.List;

import mekong89.suggestfriend.xMessage.MarkMessageReaded;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;

public class CheckOnlineService extends IntentService {
	public CheckOnlineService() {
		super("CheckOnline");
		// TODO Auto-generated constructor stub
	}

	JSONParser jsonParser = new JSONParser();
	Location location;
	

	// @Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		//Log.d("Mekong89", "Background thread started");
		
		if(LocalStore.getString(getApplicationContext(), Utils.TAG_PASSWORD)!=""){
			new CheckMessage().execute();
		}
		
//		if (Utils.isWIFIConnected(getApplicationContext())) {
//			new CreateWifiEntry().execute();
//		} else {
//			// Log.d("Mekong89", "WIFI not connected");
//			// new DeleteWifiEntry().execute();
//		}

		// GPS
		// if(Utils.hasGPS(getApplicationContext())){
		// Log.d("Mekong89","Has GPS");
//		location = Utils.getLocation(getApplicationContext());
//		if (location != null) {
//			new CreateGPSEntry().execute();
//		}
//		new FindGPSFriends().execute("100");
		// }
		scheduleNextUpdate();
		//Log.d("Mekong89", "Background thread done");
	}

	private void scheduleNextUpdate() {
		Intent intent = new Intent(this, this.getClass());
		PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		// The update frequency should often be user configurable. This is not.

		long currentTimeMillis = System.currentTimeMillis();
		long nextUpdateTimeMillis = currentTimeMillis
				+ Configs.BACKGROUND_SERVICE_DELAY_SECONDS
				* DateUtils.SECOND_IN_MILLIS;
		Time nextUpdateTime = new Time();
		nextUpdateTime.set(nextUpdateTimeMillis);

		// if (nextUpdateTime.hour < 8 || nextUpdateTime.hour >= 18)
		// {
		// nextUpdateTime.hour = 8;
		// nextUpdateTime.minute = 0;
		// nextUpdateTime.second = 0;
		// nextUpdateTimeMillis = nextUpdateTime.toMillis(false) +
		// DateUtils.DAY_IN_MILLIS;
		// }
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC, nextUpdateTimeMillis, pendingIntent);
	}

	/**
	 * Background Async Task to Get complete product details
	 * */
	class CreateWifiEntry extends AsyncTask<String, String, String> {

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
				params.add(new BasicNameValuePair("swifi", Utils
						.getWIFIRouterMacId(getApplicationContext())));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_create_wifi_entry(getApplicationContext()), "POST", params);
				// check your log for json response
				// Log.d("Mekong89", json.toString());
				if (json != null) {
					// json success tag
					success = json.getInt(Utils.TAG_SUCCESS);

					if (success == 1) {
						// successfully received product details
						Log.d("Mekong89",
								"Upload WIFI Info Success!"
										+ json.getString(Utils.TAG_MESSAGE));

					} else {
						Log.d("Mekong89",
								"Upload WIFI Info Fail!"
										+ json.getString(Utils.TAG_MESSAGE));
					}
				} else {
					Log.d("Mekong89", "Network Error");
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
	class DeleteWifiEntry extends AsyncTask<String, String, String> {

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... value) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("email", LocalStore
						.getString(getApplicationContext(), Utils.TAG_EMAIL)));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_delete_wifi_entry(getApplicationContext()), "POST", params);

				// json success tag
				if (json != null) {
					success = json.getInt(Utils.TAG_SUCCESS);
					if (success == 1) {
						// successfully received product details
						Log.d("Mekong89",
								"Delete wifi entry success!"
										+ json.getString(Utils.TAG_MESSAGE));

					} else {
						Log.d("Mekong89",
								"Delete Entry Failed! "
										+ json.getString(Utils.TAG_MESSAGE));
					}
				} else {
					Log.d("Mekong89", "Network Error");
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
	class CreateGPSEntry extends AsyncTask<String, String, String> {

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
				params.add(new BasicNameValuePair("slat", String
						.valueOf(location.getLatitude())));
				params.add(new BasicNameValuePair("slong", String
						.valueOf(location.getLongitude())));
				// params.add(new BasicNameValuePair("slat", String
				// .valueOf(10.8)));
				// params.add(new BasicNameValuePair("slong", String
				// .valueOf(106.7)));
				// params.add(new BasicNameValuePair("slat",
				// String.valueOf(Math.round(location.getLatitude()*100000.0)/100000.0)));
				// params.add(new BasicNameValuePair("slong",
				// String.valueOf(Math.round(location.getLongitude()*100000.0)/100000.0)));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_create_gps_entry(getApplicationContext()), "POST", params);
				// check your log for json response
				// Log.d("Mekong89", json.toString());
				if (json != null) {
					// json success tag
					success = json.getInt(Utils.TAG_SUCCESS);
					if (success == 1) {
						// successfully received product details
						Log.d("Mekong89",
								"Upload GPS Info Success!"
										+ json.getString(Utils.TAG_MESSAGE));

					} else {
						Log.d("Mekong89",
								"Upload GPS Info Fail!"
										+ json.getString(Utils.TAG_MESSAGE));
					}
				} else {
					Log.d("Mekong89", "Network Error");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return "0";
		}

	}

	class FindGPSFriends extends AsyncTask<String, String, String> {
		ArrayList<String> userArray = new ArrayList<String>();

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... value) {

			// Check for success tag
			int success;
			try {

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("MAC", Utils
						.getWIFIRouterMacId(getApplicationContext())));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_get_wifi_list(getApplicationContext()), "POST", params);
				if (null == json) {
					Log.d("Mekong89", "Get GPS List Fail! ");
					return "0";
				}
				// json success tag
				success = json.getInt(Utils.TAG_SUCCESS);
				Log.d("Mekong89",
						"Get WIFI List Success! "
								+ json.getString(Utils.TAG_MESSAGE));
				JSONArray arrayUser = json.getJSONArray("users");
				if (null == location) {
					return "0";
				}
				// Building Parameters
				params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("lat", String
						.valueOf(location.getLatitude())));
				params.add(new BasicNameValuePair("long", String
						.valueOf(location.getLongitude())));
				params.add(new BasicNameValuePair("radius", String
						.valueOf(0.00000898 * Integer.parseInt(value[0]))));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				json = jsonParser.makeHttpRequest(Utils.url_get_gps_list(getApplicationContext()),
						"POST", params);
				Log.d("Mekong89", json.toString());
				// json success tag
				success = json.getInt(Utils.TAG_SUCCESS);
				// String WIFIfriends = json.getJSONObject("users").toString();
				// Log.d("Mekong89",WIFIfriends);
				if (success == 1) {
					// successfully received product details
					Log.d("Mekong89",
							"Get GPS List Success! "
									+ json.getString(Utils.TAG_MESSAGE));
					arrayUser = json.getJSONArray("users");

					for (int i = 0; i < arrayUser.length(); i++) {
						if (!arrayUser
								.get(i)
								.toString()
								.equals(LocalStore.getString(
										getApplicationContext(),
										Utils.TAG_EMAIL))) {
							Log.d("Mekong89", arrayUser.get(i).toString());
							// userArray.add(arrayUser.get(i).toString());
							String foundEmail = arrayUser.get(i).toString();

							long now = System.currentTimeMillis();
							SQLiteHandler db = SQLiteHandler
									.getHelper(getApplicationContext());
							Log.d("Mekong89",
									"Meet " + foundEmail + " "
											+ db.getFriendMeetCount(foundEmail)
											+ " time");
							// check exist ---------------------------
							if (db.getFriendMeetCount(foundEmail) == 0) {// Add
																			// friend

								db.addFriendEntry(foundEmail,
										(float) location.getLongitude(),
										(float) location.getLatitude(),
										String.valueOf(now));
							} else {
								List<FriendEntry> meetHistory = db
										.getListOfOneFriend(foundEmail);
								boolean willAdd = false;
								if (meetHistory.size() > 0) {
									double radius = 0.00000898 * Integer
											.parseInt(value[0]);
									for (FriendEntry meetEntry : meetHistory) {
										if (location.getLongitude()
												- meetEntry.getLocLong() > radius
												|| location.getLongitude()
														+ radius < meetEntry
															.getLocLong()
												|| location.getLatitude()
														- meetEntry.getLocLat() > radius
												|| location.getLatitude()
														+ radius < meetEntry
															.getLocLat()) {
											// nothing
											willAdd = false;
										} else {
											willAdd = true;
											break;
										}
									}
								}
								if (willAdd == false) {
									db.addFriendEntry(foundEmail,
											(float) location.getLongitude(),
											(float) location.getLatitude(),
											String.valueOf(now));
								}
							}
						}
					}

				} else {
					Log.d("Mekong89",
							"Get GPS List Fail! "
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
			// runOnUiThread(new Runnable() {
			// public void run() {
			// if(userArray.size()>0){
			// AlertDialog.Builder builder = new
			// AlertDialog.Builder(Friends.this);
			// builder.setTitle("User on this WIFI:");
			//
			// ListView modeList = new ListView(Friends.this);
			// ArrayAdapter<String> modeAdapter = new
			// ArrayAdapter<String>(Friends.this,
			// android.R.layout.simple_list_item_1, android.R.id.text1,
			// userArray);
			// modeList.setAdapter(modeAdapter);
			//
			// builder.setView(modeList);
			// final Dialog dialog = builder.create();
			//
			// modeList.setOnItemClickListener(new OnItemClickListener() {
			//
			// @Override
			// public void onItemClick(AdapterView<?> arg0, View arg1,
			// int position, long arg3) {
			// // TODO Auto-generated method stub
			// Log.d("Mekong89",
			// "Gonna launch friend: "+userArray.get(position));
			// Intent intent = new Intent(Friends.this,
			// AddFriendActivity.class);
			// Bundle b = new Bundle();
			// b.putString("Email", userArray.get(position)); //Your id
			// intent.putExtras(b); //Put your id to your next Intent
			// startActivity(intent);
			// dialog.dismiss();
			// }
			// });
			//
			// dialog.show();
			// } else{
			// Toast.makeText(Friends.this, "No user found",
			// Toast.LENGTH_LONG).show();
			// }
			// }
			// });

			super.onPostExecute(result);
		}
	}

	class CheckMessage extends AsyncTask<String, String, String> {
		JSONArray jsonMessageList;

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
						Utils.url_check_message(getApplicationContext()), "GET", params);
				if (null == json) {
					return "-1";
				}

				// Log.d("Mekong89", json.toString());
				// json success tag
				success = json.getInt(Utils.TAG_SUCCESS);
				if (success == 1) {
					// successfully received product details
					// Log.d("Mekong89","Get Message List Success! +
					// json.getString(Utils.TAG_MESSAGE));
					jsonMessageList = (JSONArray) json.get("entry");
					for (int i = 0; i < jsonMessageList.length(); i++) {
						JSONObject jsonMessage = (JSONObject) jsonMessageList
								.get(i);
						String sender = jsonMessage.getString("sender");
						String content = jsonMessage.getString("content");

						String id = jsonMessage.getString("id");
						new MarkMessageDelivered().execute(id);

						NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
						int icon = R.drawable.icon;
						CharSequence notiText = "WECOM new message";
						long meow = System.currentTimeMillis();

						Notification notification = new Notification(icon,notiText, meow);

						Context context = getApplicationContext();
						CharSequence contentTitle = "Message From " + sender.substring(0, sender.lastIndexOf("@"));
						CharSequence contentText = content;
						Intent messageIntent = new Intent(CheckOnlineService.this,xMessage.class);
						messageIntent.putExtra("friendMail", sender);
						PendingIntent contentIntent = PendingIntent.getActivity(CheckOnlineService.this, 0, messageIntent, PendingIntent.FLAG_UPDATE_CURRENT);
						notification.defaults = Notification.DEFAULT_ALL;
						// Will show lights and make the notification disappear when the presses it
						notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
						notification.setLatestEventInfo(context, contentTitle,contentText, contentIntent);
						notificationManager.notify(0,notification);
					}

				} else {
					// Log.d("Mekong89",
					// "Get Message List Fail"
					// + json.getString(Utils.TAG_MESSAGE));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return "0";
		}

	}

	class MarkMessageDelivered extends AsyncTask<String, String, String> {

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... value) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("messageid", value[0]));
				params.add(new BasicNameValuePair("messagestate", "'delivered'"));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_update_message(getApplicationContext()), "POST", params);
				if (null == json) {
					return "-1";
				}

				Log.d("Mekong89", json.toString());
				// json success tag
				success = json.getInt(Utils.TAG_SUCCESS);
				if (success == 1) {
					// successfully received product details
					Log.d("Mekong89",
							"Post Message Success! "
									+ json.getString(Utils.TAG_MESSAGE));

				} else {
					Log.d("Mekong89",
							"Post Message Fail"
									+ json.getString(Utils.TAG_MESSAGE));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return "0";
		}

	}
}
