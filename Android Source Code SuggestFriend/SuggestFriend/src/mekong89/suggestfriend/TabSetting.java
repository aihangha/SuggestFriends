package mekong89.suggestfriend;

import java.util.ArrayList;
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
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TabSetting extends Activity {
	
	private Boolean exit = false;
	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();
	String email ="";
	TextView txtEmail;
	TextView txtBirthday;
	TextView txtCellPhone;
	TextView txtFacebook;
	ImageView imgAvater;
	Button btnLogOut;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_info);
		
		email = LocalStore.getString(getApplicationContext(), Utils.TAG_EMAIL);
		txtEmail = (TextView) findViewById(R.id.txtFriendEmail);
		txtEmail.setText(email);
		
		btnLogOut = (Button) findViewById(R.id.btnLogOut);
		btnLogOut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				LocalStore.saveString(getApplicationContext(), Utils.TAG_PASSWORD, "");
				startActivity(new Intent(getApplicationContext(), Login.class));
				TabSetting.this.finish();
			}
		});
		
		// allow internet access strict
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		
		new GetProductDetails().execute();
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
		//super.onBackPressed();
	}
	/**
	 * Background Async Task to Get complete product details
	 * */
	class GetProductDetails extends AsyncTask<String, String, String> {

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
						params.add(new BasicNameValuePair(Utils.TAG_EMAIL, "'"+email+"'"));
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
							txtBirthday = (TextView) findViewById(R.id.txtFriendBirthday);
							txtCellPhone = (TextView) findViewById(R.id.txtFriendCellPhone);
							txtFacebook= (TextView) findViewById(R.id.txtFriendFacebook);
							imgAvater = (ImageView) findViewById(R.id.imgBackgroundInHome);

							// display product data in EditText
							txtBirthday.setText(user.getString(Utils.TAG_BIRTHDAY));
							txtCellPhone.setText(user.getString(Utils.TAG_CELLPHONE));
							txtFacebook.setText(user.getString(Utils.TAG_FACEBOOK));
							imgAvater.setImageDrawable(Utils.LoadImageFromWebOperations(Utils.avatarAddress + user.getString(Utils.TAG_IMG)));							
						}else{
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
}
