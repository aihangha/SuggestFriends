package mekong89.suggestfriend;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListSuggestActivity extends Activity {
	ListView listSuggest;
	SuggestListAdapter adapter;
	List<FriendEntry> suggestFriend;
	public ArrayList<SuggestListModel> data = new ArrayList<SuggestListModel>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.suggestlist);
		listSuggest = (ListView) findViewById(R.id.listSuggest);
		getDataFromDB();
		adapter = new SuggestListAdapter(this, data);
		listSuggest.setAdapter(adapter);
		
		listSuggest.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Log.d("Mekong89", "Gonna launch friend: " + data.get(position).getName());
				Intent intent = new Intent(ListSuggestActivity.this,
						UserHomeActivity.class);
				Bundle b = new Bundle();
				b.putString("Email", suggestFriend.get(position).email); // Your
															// id
				intent.putExtras(b); // Put your id to your
										// next Intent
				startActivity(intent);			
			}
		});
	}
	private void getDataFromDB() {
		// TODO Auto-generated method stub
		SQLiteHandler db = SQLiteHandler
				.getHelper(getApplicationContext());
		suggestFriend = db.getAllFriends();
		int i = 0;
		for (FriendEntry friendEntry : suggestFriend) {
			String name = Utils.getNameFromEmail(friendEntry.getEmail());
			SuggestListModel user = new SuggestListModel(name,name+"64.jpg",db.getFriendMeetCount(friendEntry.getEmail()));
			data.add(user);
			i++;
		}
//		final String[] userArray = new HashSet<String>(Arrays
//				.asList(list)).toArray(new String[0]);

//		runOnUiThread(new Runnable() {
//			public void run() {
//				if (userArray.length > 0) {
//					AlertDialog.Builder builder = new AlertDialog.Builder(
//							Friends.this);
//					builder.setTitle("Near Friend Found:");
//
//					ListView modeList = new ListView(Friends.this);
//					ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(
//							Friends.this,
//							android.R.layout.simple_list_item_1,
//							android.R.id.text1, userArray);
//					modeList.setAdapter(modeAdapter);
//
//					builder.setView(modeList);
//					final Dialog dialog = builder.create();
//
//					modeList.setOnItemClickListener(new OnItemClickListener() {
//
//						@Override
//						public void onItemClick(AdapterView<?> arg0,
//								View arg1, int position, long arg3) {
//							// TODO Auto-generated method stub
//							Log.d("Mekong89", "Gonna launch friend: "
//									+ userArray[position]);
//							Intent intent = new Intent(Friends.this,
//									AddFriendActivity.class);
//							Bundle b = new Bundle();
//							b.putString("Email", userArray[position]); // Your
//																		// id
//							intent.putExtras(b); // Put your id to your
//													// next Intent
//							startActivity(intent);
//							dialog.dismiss();
//						}
//					});
//
//					dialog.show();
//				} else {
//					Toast.makeText(Friends.this, "No user found",
//							Toast.LENGTH_LONG).show();
//				}
//			}
//
//		});
	}

}
