package mekong89.suggestfriend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
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

	static boolean firstTabClick = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_setting);
		
		email = LocalStore.getString(getApplicationContext(), Utils.TAG_EMAIL);
		
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
		
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
	public void OnTabClick(){
		if(firstTabClick){
			firstTabClick = false;
		} else{
			
		}
	}
	
}
