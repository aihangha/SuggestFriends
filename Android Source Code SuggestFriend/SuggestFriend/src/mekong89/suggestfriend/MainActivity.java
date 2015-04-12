package mekong89.suggestfriend;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private Boolean exit = false;
	TabHost tabHost;
	LocalActivityManager mlam;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mlam = new LocalActivityManager(this, true);
		setContentView(R.layout.activity_main);

		// create the TabHost that will contain the Tabs
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		mlam.dispatchCreate(savedInstanceState);
		tabHost.setup(mlam);
		TabSpec tabFeed = tabHost.newTabSpec("Feed");
		TabSpec tabMessages = tabHost.newTabSpec("Messages");
		TabSpec tabFriends = tabHost.newTabSpec("Friends");
		TabSpec tabSetting = tabHost.newTabSpec("Setting");

		tabFeed.setIndicator("", getResources()
				.getDrawable(R.drawable.tab_feed));
		tabFeed.setContent(new Intent(this, TabFeeds.class));

		tabMessages.setIndicator("",
				getResources().getDrawable(R.drawable.tab_messages));
		tabMessages.setContent(new Intent(this, TabMessages.class));

		tabFriends.setIndicator("",
				getResources().getDrawable(R.drawable.tab_friends));
		tabFriends.setContent(new Intent(this, TabFriends.class));

		tabSetting.setIndicator("",
				getResources().getDrawable(R.drawable.tab_setting));
		tabSetting.setContent(new Intent(this, TabSetting.class));

		tabHost.addTab(tabMessages);
		tabHost.addTab(tabFeed);
		tabHost.addTab(tabFriends);
		tabHost.addTab(tabSetting);

		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabID) {
				// TODO Auto-generated method stub
				Log.d("Mekong89", "Switch to " + tabID);
			}
		});

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		new UpdateFriendList().execute();
		new UpdateFollowList().execute();

		SQLiteHandler db = new SQLiteHandler(getApplicationContext());

		// LocationManager manager =
		// (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		// if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
		// //Ask the user to enable GPS
		// AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// builder.setTitle("Location Manager");
		// builder.setMessage("Would you like to enable GPS?");
		// builder.setPositiveButton("Yes", new
		// DialogInterface.OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// //Launch settings, allowing user to make a change
		// Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		// startActivity(i);
		// }
		// });
		// builder.setNegativeButton("No", new DialogInterface.OnClickListener()
		// {
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// //No location service, no Activity
		// finish();
		// }
		// });
		// builder.create().show();
		// }
		
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == TabFeeds.REQUEST_IMAGE_CAPTURE)
        {
			TabFeeds tabFeeds = (TabFeeds) mlam.getActivity("Feed");
            tabFeeds.onActivityResult(requestCode, resultCode, data);
        }
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
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
					String friendString = "";
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
								friendString = friendString + friendMail + ";";
							}

						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					if (friendString.endsWith(";")) {
						friendString = friendString.substring(0,
								friendString.length() - 1);
					}
					LocalStore.saveString(getApplicationContext(),
							Utils.TAG_FRIENDLIST, friendString);
					Log.d("Mekong89", "Friend List:" + friendString);
				} else {
					Log.d("Mekong89",
							"Get Friend List Fail"
									+ json.getString(Utils.TAG_MESSAGE));
					LocalStore.saveString(getApplicationContext(),
							Utils.TAG_FRIENDLIST, "");
					Log.d("Mekong89", "Friend List:" + "");
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
