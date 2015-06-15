package mekong89.suggestfriend;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	EditText txtEmail;
	EditText txtPass;
	Button btnLogin;
	Button btnRegister;
	JSONParser jsonParser = new JSONParser();
	String storedPass;
	String storedEmail;
	String checkEmail;
	String checkPass;
	EditText txtServer;
	boolean flagConnectFail = false;

	// private static final String url_get_user = Utils.adrr
	// + "get_user_details.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		// allow internet access strict
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		//txtServer = (EditText) findViewById(R.id.txtServerIPInLogin);
//		String storedServer = LocalStore.getString(getApplicationContext(),
//				Utils.TAG_SERVERIP).trim();
//		if (storedServer.equals("")) {
//			storedServer = Configs.defaultIP;
//		}
//		txtServer.setText(storedServer);

		// Get UI object
		txtEmail = (EditText) findViewById(R.id.txtFriendEmail);
		txtPass = (EditText) findViewById(R.id.txtLoginPass);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnRegister = (Button) findViewById(R.id.btnRegistry);

		// check saved account
		storedEmail = LocalStore.getString(getApplicationContext(),
				Utils.TAG_EMAIL);
		storedPass = LocalStore.getString(getApplicationContext(),
				Utils.TAG_PASSWORD);
		if (!storedEmail.equals("") && !storedPass.equals("")) {
			// login auto by password
			checkEmail = storedEmail;
			checkPass = storedPass;
			try {
//				LocalStore.saveString(getApplicationContext(),
//						Utils.TAG_SERVERIP, txtServer.getText().toString()
//								.trim());
				String result = new GetProductDetails().execute().get();
				if (result.equals("1")) {

					startActivity(new Intent(getApplicationContext(),
							MainActivity.class));
				} else {
					if (!flagConnectFail) {
						Toast.makeText(getApplicationContext(),
								"Email or password are not valid",
								Toast.LENGTH_SHORT).show();

					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			txtEmail.setText(storedEmail);
		}

		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				LocalStore.saveString(getApplicationContext(),
//						Utils.TAG_SERVERIP, txtServer.getText().toString()
//								.trim());
				checkEmail = txtEmail.getText().toString();
				checkPass = txtPass.getText().toString();
				try {
					String result = new GetProductDetails().execute().get();
					if (result.equals("1")) {

						startActivity(new Intent(getApplicationContext(),
								MainActivity.class));
					} else {
						if (!flagConnectFail) {
							Toast.makeText(getApplicationContext(),
									"Email or password are not valid",
									Toast.LENGTH_SHORT).show();

						}
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Background Async Task to Get complete product details
	 * */
	class GetProductDetails extends AsyncTask<String, String, String> {
		int success;

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... value) {

			// Check for success tag

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair(Utils.TAG_EMAIL, "'"
						+ checkEmail + "'"));
				// getting user details by making HTTP request
				// Note that product details url will use GET request
				String url =  Utils.url_get_user(getApplicationContext()); 
				JSONObject json = jsonParser.makeHttpRequest(url
						, "GET",
						params);
				// check your log for json response
				if (null == json) {
					flagConnectFail = true;
					return "-1";
				}
				Log.d("Mekong89", json.toString());

				// json success tag
				success = json.getInt(Utils.TAG_SUCCESS);
				if (success == 1) {
					// successfully received product details
					JSONArray userObj = json.getJSONArray(Utils.TAG_USER); // JSON
																			// Array

					// get first product object from JSON Array
					JSONObject user = userObj.getJSONObject(0);

					// product with this pid found
					if (user.getString(Utils.TAG_PASSWORD).equals(checkPass)) {
						LocalStore.saveString(getApplicationContext(),
								Utils.TAG_EMAIL, checkEmail);
						LocalStore.saveString(getApplicationContext(),
								Utils.TAG_PASSWORD, checkPass);
						return "1";
					}
					// Edit Text

				} else {
					// user with email not found
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return "0";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (flagConnectFail) {
				Toast.makeText(getApplicationContext(),
						"Connect To Server Fail", Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(result);
		}

	}
}
