package mekong89.suggestfriend;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;

public class Utils {
	public static final String adrr = "http://friendhub.byethost13.com/wecom/alpha/";
	public static final String avatarAddress = adrr+"avatar/";
	public static final String postImages = Utils.adrr + "images/";
	public static final String url_get_wifi_list = Utils.adrr
			+ "wifi/get_list.php";
	public static final String url_get_gps_list = Utils.adrr
			+ "gps/get_list.php";
	public static final String url_get_user = Utils.adrr
			+ "get_user_details.php";
	public static final String url_post_status = Utils.adrr
			+ "feed/create_feed.php";
	public static final String url_post_status_have_image = Utils.adrr
			+ "feed/create_feed_with_image.php";
	public static final String url_delete_status = Utils.adrr
			+ "feed/delete_feed.php";
	public static final String url_increase_like = Utils.adrr
			+ "feed/increase_like.php";
	public static final String url_descrease_like = Utils.adrr
			+ "feed/descrease_like.php";
	public static final String url_delete_like = Utils.adrr
			+ "like/delete_like.php";
	public static final String url_create_like = Utils.adrr
			+ "like/create_like.php";
	public static final String url_get_list_by_nick = Utils.adrr
			+ "feed/get_list_by_nick.php";
	public static final String url_get_friend = Utils.adrr + "get_friend.php";
	public static final String url_get_follow = Utils.adrr + "get_follow.php";
	public static final String url_get_message = Utils.adrr
			+ "message/get_message.php";
	public static final String url_post_message = Utils.adrr
			+ "message/create_message.php";
	public static final String url_update_message = Utils.adrr
			+ "message/update_message.php";
	public static final String url_check_message = Utils.adrr
			+ "message/check_message.php";
	
	public static final String url_create_comment = Utils.adrr
			+ "comment/create_comment.php";
	public static final String url_get_comment = Utils.adrr
			+ "comment/get_comment.php";
	public static final String url_feed_by_user = Utils.adrr
			+ "feed/get_feed_by_user.php";
	
	public static final String url_request_friend = Utils.adrr
			+ "friend/friend_request.php";
	public static final String url_answer_request = Utils.adrr
			+ "friend/answer_request.php";
	public static final String url_check_friend_relate = Utils.adrr
			+ "friend/check_friend_relate.php";
	public static final String url_unfriend = Utils.adrr
			+ "friend/unfriend.php";
	
	public static final String url_unfollow = Utils.adrr
			+ "follow/unfollow.php";
	public static final String url_follow = Utils.adrr
			+ "follow/follow.php";
	public static final String url_get_follow_list = Utils.adrr
			+ "follow/get_follow_list.php";
	public static final String url_get_follow_state = Utils.adrr
			+ "follow/check_follow_state.php";
	

	// JSON Node names
	public static final String TAG_SUCCESS = "success";
	public static final String TAG_MESSAGE = "message";
	public static final String TAG_PASSWORD = "password";
	public static final String TAG_EMAIL = "email";
	public static final String TAG_BIRTHDAY = "birthday";
	public static final String TAG_CELLPHONE = "cellphone";
	public static final String TAG_FACEBOOK = "facebook";
	public static final String TAG_IMG = "image";
	public static final String TAG_USER = "user";
	public static final String TAG_FRIENDLIST = "friendlist";
	public static final String TAG_FOLLOWLIST = "followlist";

	public static String getPhoneNumber(Context context) {
		TelephonyManager tMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tMgr.getLine1Number();
	}

	public static Drawable LoadImageFromWebOperations(String url) {
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			return null;
		}
	}

	public static String getWIFIRouterMacId(Context context) {

		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		return wifiInfo.getBSSID().toUpperCase();
	}

	public static boolean isWIFIConnected(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (mWifi.isConnected()) {
			return true;
		}
		return false;
	}

	// public static boolean hasGPS(Context context){
	// PackageManager pm = context.getPackageManager();
	// return pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
	// }

	public static Location getLocation(Context context) {
		// Get the location manager
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Activity.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String bestProvider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(bestProvider);
		LocationListener loc_listener = new LocationListener() {

			public void onLocationChanged(Location l) {
			}

			public void onProviderEnabled(String p) {
			}

			public void onProviderDisabled(String p) {
			}

			public void onStatusChanged(String p, int status, Bundle extras) {
			}

		};
		locationManager
				.requestLocationUpdates(bestProvider, 0, 0, loc_listener);
		location = locationManager.getLastKnownLocation(bestProvider);
		// Log.d("Mekong89","My Location:"+location.getLatitude()
		// +","+location.getLongitude());
		return location;
	}

	public static String getNameFromEmail(String email) {
		return email.substring(0, email.indexOf("@")).toLowerCase();
	}

	public static Bitmap getBitmapFromURL(Context context, String src,
			String prefix) {
		String filename = prefix + src.substring(src.lastIndexOf("/") + 1);
		File folder = getDataFolder(context);
		File imageFile = new File(folder, filename);
		if (!imageFile.exists()) {
			try {
				//imageFile.createNewFile();
				URL wallpaperURL = new URL(src);
				URLConnection connection = wallpaperURL.openConnection();
				InputStream inputStream = new BufferedInputStream(
						wallpaperURL.openStream(), 10240);
				FileOutputStream outputStream = new FileOutputStream(imageFile,false);

				byte buffer[] = new byte[1024];
				int dataSize;
				int loadedSize = 0;
				while ((dataSize = inputStream.read(buffer)) != -1) {
					loadedSize += dataSize;
					// publishProgress(loadedSize);
					outputStream.write(buffer, 0, dataSize);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		InputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(imageFile);

			BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inSampleSize = 1;
			bitmapOptions.inJustDecodeBounds = false;
			return BitmapFactory.decodeStream(fileInputStream, null,
					bitmapOptions);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static File getDataFolder(Context context) {
		File dataDir = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			dataDir = new File(Environment.getExternalStorageDirectory(),
					"image");
			if (!dataDir.isDirectory()) {
				dataDir.mkdirs();
			}
		}

		if (!dataDir.isDirectory()) {
			dataDir = context.getFilesDir();
		}

		return dataDir;
	}

	@SuppressWarnings("deprecation")
	public static String getDateString(Date postDate) {
		long currentTimeStamp = System.currentTimeMillis();
		long postTimeStamp = postDate.getTime();
		// long second = 1000;
		long minute = 60000;
		long hour = 3600000;
		long day = 86400000;
		long month = day * 30;

		Calendar cal = Calendar.getInstance();
		cal.setTime(postDate);
		if ((currentTimeStamp - postTimeStamp) > month) { // more than 1 month
			return (cal.get(Calendar.DAY_OF_MONTH) + "/" + cal
					.get(Calendar.MONTH));
		} else if ((currentTimeStamp - postTimeStamp) > day) { // more than 1
																// day
			int noDay = (int) ((currentTimeStamp - postTimeStamp) / day);
			if (noDay == 1) {
				return "yesterday";
			} else {
				return "" + noDay + " days ago";
			}
		} else if ((currentTimeStamp - postTimeStamp) > hour) { // more than 1
																// hour
			int noHour = (int) ((currentTimeStamp - postTimeStamp) / hour);
			if (noHour == 1) {
				return "one hour ago";
			} else {
				return "" + noHour + " hours ago";
			}
		} else {
			int noMin = (int) ((currentTimeStamp - postTimeStamp) / minute);
			if (noMin <= 1) {
				return "just now";
			} else {
				return "" + noMin + " minutes ago";
			}
		}
	}

	public static boolean isFriend(Context context, String email) {
		String friendRawString = LocalStore.getString(context,
				Utils.TAG_FRIENDLIST);
		if (friendRawString.isEmpty()) {
			return false;
		} else {
			String[] friends = friendRawString.split(";");
			for (int i = 0; i < friends.length; i++) {
				if (friends[i].equals(email)) {
					return true;
				}
			}
			return false;
		}
	}

	public static boolean isFollow(Context context, String email) {
		String followRawString = LocalStore.getString(context,
				Utils.TAG_FOLLOWLIST);
		if (followRawString.isEmpty()) {
			return false;
		} else {
			String[] follows = followRawString.split(";");
			for (int i = 0; i < follows.length; i++) {
				if (follows[i].equals(email)) {
					return true;
				}
			}
			return false;
		}
	}

	
	
}
