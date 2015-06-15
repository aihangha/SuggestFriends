package mekong89.suggestfriend;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
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
	public static String adrr(Context ctx) {
		//String currentIP = LocalStore.getString(ctx, Utils.TAG_SERVERIP);
		//if(currentIP.trim().equals("")){
		//	return "http://" + Configs.defaultIP + "//wecom/alpha/";
		//}
		//return "http://"+currentIP+"//wecom/alpha/";
		return "http://wecom.byethost5.com/alpha/";
	}

	public static String avatarAddress(Context ctx){ return adrr(ctx)+"avatar/";}
	public static String postImages(Context ctx){ return adrr(ctx)+"images/";}
	public static String url_get_wifi_list(Context ctx){ return adrr(ctx)+"wifi/get_list.php";}	
	public static String url_get_gps_list(Context ctx){ return adrr(ctx)+"gps/get_list.php";}
	public static String url_get_user(Context ctx){ return adrr(ctx)+"user/get_user_details.php";}
	public static String url_post_status(Context ctx){ return adrr(ctx)+"feed/create_feed.php";}
	public static String url_post_status_have_image(Context ctx){ return adrr(ctx)+"feed/create_feed_with_image.php";}
	public static String url_delete_status(Context ctx){ return adrr(ctx)+"feed/delete_feed.php";}
	public static String url_increase_like(Context ctx){ return adrr(ctx)+"feed/increase_like.php";}
	public static String url_descrease_like(Context ctx){ return adrr(ctx)+"feed/descrease_like.php";}
	public static String url_delete_like(Context ctx){ return adrr(ctx)+"like/delete_like.php";}
	public static String url_create_like(Context ctx){ return adrr(ctx)+"like/create_like.php";}
	public static String url_get_list_by_nick(Context ctx){ return adrr(ctx)+"feed/get_list_by_nick.php";}
	public static String url_get_friend(Context ctx){ return adrr(ctx)+"user/get_friend.php";}
	public static String url_get_follow(Context ctx){ return adrr(ctx)+"user/get_follow.php";}
	public static String url_get_message(Context ctx){ return adrr(ctx)+"message/get_message.php";}
	public static String url_post_message(Context ctx){ return adrr(ctx)+"message/create_message.php";}
	public static String url_update_message(Context ctx){ return adrr(ctx)+"message/update_message.php";}
	public static String url_check_message(Context ctx){ return adrr(ctx)+"message/check_message.php";}
	public static String url_create_comment(Context ctx){ return adrr(ctx)+"comment/create_comment.php";}
	public static String url_get_comment(Context ctx){ return adrr(ctx)+"comment/get_comment.php";}
	public static String url_feed_by_user(Context ctx){ return adrr(ctx)+"feed/get_feed_by_user.php";}
	public static String url_request_friend(Context ctx){ return adrr(ctx)+"friend/friend_request.php";}
	public static String url_answer_request(Context ctx){ return adrr(ctx)+"friend/answer_request.php";}
	public static String url_check_friend_relate(Context ctx){ return adrr(ctx)+"friend/check_friend_relate.php";}
	public static String url_unfriend(Context ctx){ return adrr(ctx)+"friend/unfriend.php";}
	public static String url_unfollow(Context ctx){ return adrr(ctx)+"follow/unfollow.php";}
	public static String url_follow(Context ctx){ return adrr(ctx)+"follow/follow.php";}
	public static String url_get_follow_list(Context ctx){ return adrr(ctx)+"follow/get_follow_list.php";}
	public static String url_get_follow_state(Context ctx){ return adrr(ctx)+"follow/check_follow_state.php";}
	public static String url_create_wifi_entry(Context ctx){ return adrr(ctx)+"wifi/create_entry.php";}
	public static String url_create_gps_entry(Context ctx){ return adrr(ctx)+"gps/create_entry.php";}
	public static String url_delete_wifi_entry(Context ctx){ return adrr(ctx)+"wifi/delete_entry.php";}

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
	public static final String TAG_CHATLIST = "chatlist";
	public static final String TAG_SERVERIP = "serverip";

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
				// imageFile.createNewFile();
				URL wallpaperURL = new URL(src);
				URLConnection connection = wallpaperURL.openConnection();
				InputStream inputStream = new BufferedInputStream(
						wallpaperURL.openStream(), 10240);
				FileOutputStream outputStream = new FileOutputStream(imageFile,
						false);

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

	public static String convertUTF8(String s) {
		try {
			final String result = URLDecoder.decode(s, "UTF-8"); // new
																	// String(s.getBytes(),
																	// "UTF-8");
			return result;
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static Bitmap decodeFile(File f, int requiredSize) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			//
			// //The new size we want to scale to
			// final int REQUIRED_SIZE=70;

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			while (o.outWidth / scale / 2 >= requiredSize
					&& o.outHeight / scale / 2 >= requiredSize)
				scale *= 2;

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

}
