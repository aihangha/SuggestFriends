package mekong89.suggestfriend;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
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
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class xMessage extends Activity {
	ListView lstMessage;
	TextView txtFriendName;
	EditText txtSend;

	Button btnSend;
	String friendMail;
	MessageListAdapter messageAdapter;
	private Handler mHandler;
	String lastUpdateTime="last50";
	ArrayList<MessageModel> arrayMessage = new ArrayList<MessageModel>();
	JSONParser jsonParser = new JSONParser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_message);
		Intent intent = getIntent();
		friendMail = intent.getStringExtra("friendMail");
		
		
		
			
		

		txtFriendName = (TextView) findViewById(R.id.txtFriendNameinMessage);
		txtFriendName.setText(Utils.getNameFromEmail(friendMail));
		txtSend = (EditText) findViewById(R.id.txtSendInMessage);
		btnSend = (Button) findViewById(R.id.btnSendInMessage);
		lstMessage = (ListView) findViewById(R.id.lstChat);
		messageAdapter = new MessageListAdapter(this, arrayMessage,Utils.getBitmapFromURL(this, Utils.avatarAddress(getApplicationContext()) + Utils.getNameFromEmail(friendMail) + "64.jpg", "avatar"));
		lstMessage.setAdapter(messageAdapter);

		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new PostMessage().execute(txtSend.getText().toString());
				txtSend.setText("");
			}
		});
		mHandler = new Handler();
		
		
		// bring friend to top of chat list
		String lastChatListRaw = LocalStore.getString(getApplicationContext(), Utils.TAG_CHATLIST);
		String[] chatArray = lastChatListRaw.split(";");
		List<String> chatList = new LinkedList<String>(Arrays.asList(chatArray));
		
		int findIndex=chatList.indexOf(friendMail);		
		if(findIndex>-1){		//available chat
				chatList.remove(findIndex);
			lastChatListRaw="";
			for(int i=0;i<chatList.size();i++){
				lastChatListRaw+=chatList.get(i)+";";
			}
			if(lastChatListRaw.endsWith(";")){
				lastChatListRaw = lastChatListRaw.substring(0,lastChatListRaw.length()-1);
			}
		} 
		LocalStore.saveString(getApplicationContext(), Utils.TAG_CHATLIST, friendMail+";"+lastChatListRaw);				
	}

	protected void onResume() {
		startRepeatingTask();
		super.onResume();
	};

	protected void onPause() {
		stopRepeatingTask();
		super.onPause();
	};

	Runnable mStatusChecker = new Runnable() {
		@Override
		public void run() {
			new GetMessageList().execute();
			mHandler.postDelayed(mStatusChecker,
					Configs.BACKGROUND_CHAT_MESSAGE_REPEAT_MILISECONDS);
		}
	};

	void startRepeatingTask() {
		mStatusChecker.run();
	}

	void stopRepeatingTask() {
		mHandler.removeCallbacks(mStatusChecker);
	}

	// /**
	// * Background Async Task to Get complete product details
	// * */

	class GetMessageList extends AsyncTask<String, String, String> {
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
				params.add(new BasicNameValuePair("sender", "'"
						+ LocalStore.getString(getApplicationContext(),
								Utils.TAG_EMAIL) + "'"));
				params.add(new BasicNameValuePair("receiver", "'" + friendMail
						+ "'"));					
				params.add(new BasicNameValuePair("lastUpdate", lastUpdateTime));
				Log.d("Mekong89", "get message after " + lastUpdateTime + "----------------------");
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_get_message(getApplicationContext()), "GET", params);
				if (null == json) {
					return "-1";
				}

				//Log.d("Mekong89", json.toString());
				// json success tag
				success = json.getInt(Utils.TAG_SUCCESS);
				if (success == 1) {
					// successfully received product details
					//Log.d("Mekong89","Get Message List Success!  + json.getString(Utils.TAG_MESSAGE));
					jsonMessageList = (JSONArray) json.get("entry");

				} else {
//					Log.d("Mekong89",
//							"Get Message List Fail"
//									+ json.getString(Utils.TAG_MESSAGE));
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

					if (null != jsonMessageList) {
						for (int i = 0; i < jsonMessageList.length(); i++) {
							try {
								JSONObject jsonMessage = (JSONObject) jsonMessageList
										.get(i);
								String id = jsonMessage.getString("id");
								String content = Utils.convertUTF8(jsonMessage
										.getString("content"));
								String sender = jsonMessage.getString("sender");
								String imageLink = sender.substring(0,
										sender.lastIndexOf("@"))
										+ "64.jpg";
								String timestamp = jsonMessage
										.getString("timestamp");
								lastUpdateTime = jsonMessage
										.getString("lastupdate");
								String state = jsonMessage
										.getString("msgstate");
								if (sender.equals(friendMail)) {
									MessageModel messageModel = new MessageModel(
											id, content, imageLink, timestamp,
											false,state);
									if(!state.equals("readed")){
										new MarkMessageReaded().execute(id);										
									}
									
									boolean flagExist = false;
									for(int j=0;j<arrayMessage.size();j++ ){
										MessageModel message = arrayMessage.get(j);
										if(message.getID().equals(id)){											
											flagExist = true;
											break;
										}
									}
									if(!flagExist){
										arrayMessage.add(messageModel);
									}
									
								} else {
									MessageModel messageModel = new MessageModel(
											id, content, imageLink, timestamp,
											true,state);
										boolean flagExist = false;
										for(int j=0;j<arrayMessage.size();j++ ){
											MessageModel message = arrayMessage.get(j);
											if(message.getID().equals(id)){
												message.setState(state);
												flagExist = true;
												break;
											}
										}
										if(!flagExist){
											arrayMessage.add(messageModel);
										}

								}
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
						messageAdapter.notifyDataSetChanged();
						lstMessage.setSelection(messageAdapter.getCount() - 1);
					}
					
				}
			});

			super.onPostExecute(result);
		}
	}

	class MarkMessageReaded extends AsyncTask<String, String, String> {

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... value) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();				
				params.add(new BasicNameValuePair("messageid",value[0]));
				params.add(new BasicNameValuePair("messagestate","'readed'"));
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
	
	class PostMessage extends AsyncTask<String, String, String> {

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... value) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				String content="";
				try {
					content = URLEncoder.encode(value[0], "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("sender",LocalStore.getString(getApplicationContext(),
								Utils.TAG_EMAIL) ));
				params.add(new BasicNameValuePair("receiver", friendMail));
				params.add(new BasicNameValuePair("content",content));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				JSONObject json = jsonParser.makeHttpRequest(
						Utils.url_post_message(getApplicationContext()), "POST", params);
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
