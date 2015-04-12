package mekong89.suggestfriend;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHandler extends SQLiteOpenHelper {
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "WECOM";

	// Contacts table name
	private static final String TABLE_FRIENDS = "SuggestFriend";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_LONG = "long";
	private static final String KEY_LAT = "lat";
	private static final String KEY_WHEN = "time";
	
	private static SQLiteHandler instance;

//	public SQLiteHandler(Context context, String name, CursorFactory factory,
//			int version) {
//		super(context, DATABASE_NAME, null, DATABASE_VERSION);
//		// TODO Auto-generated constructor stub
//	}
	
	public SQLiteHandler(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static synchronized SQLiteHandler getHelper(Context context)
    {
        if (instance == null)
            instance = new SQLiteHandler(context);

        return instance;
    }
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FRIENDS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_EMAIL + " TEXT," + KEY_LONG
				+ " TEXT," + KEY_LAT + " TEXT," + KEY_WHEN + " TEXT" +")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);

		// Create tables again
		onCreate(db);
	}

	// Adding new entry
	public void addFriendEntry(String email, float loc_long,
			float loc_lat, String timestamp) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EMAIL, email);
		values.put(KEY_LAT, loc_lat);
		values.put(KEY_LONG, loc_long);
		values.put(KEY_WHEN, timestamp);
		
		Log.d("Mekong89","Add this item to DB: "+email + ":"+loc_long +":"+loc_lat+":"+timestamp);

		// Inserting Row
		db.insert(TABLE_FRIENDS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	public FriendEntry getFriendEntry(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_FRIENDS, new String[] { KEY_ID,
				KEY_EMAIL, KEY_LONG, KEY_LAT, KEY_WHEN }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		// Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
		// cursor.getString(1), cursor.getString(2));
		// return contact
		FriendEntry friendEntry = new FriendEntry(Integer.parseInt(cursor
				.getString(0)), cursor.getString(1), Float.parseFloat(cursor
				.getString(2)), Float.parseFloat(cursor.getString(3)),
				Long.parseLong(cursor.getString(4)));
		if(cursor != null && !cursor.isClosed()){
			cursor.close();
	    }  
		return friendEntry;
	}

	// Getting friends Count
	public int getFriendMeetCount(String email) {
		int result;
		String countQuery = "SELECT * FROM " + TABLE_FRIENDS +" WHERE email="+"\""+email+"\"";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		result = cursor.getCount();
		if(cursor != null && !cursor.isClosed()){
			cursor.close();
	    }   		
		// return count
		return result;
	}
	 
    public List<FriendEntry> getListOfOneFriend(String email) {
        List<FriendEntry> meetList = new ArrayList<FriendEntry>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_FRIENDS +" WHERE email="+"\""+email+"\"";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FriendEntry friend = new FriendEntry();
                friend.setID(Integer.parseInt(cursor.getString(0)));
                Log.d("Mekong89","What I get from cursor is "+cursor.getString(0));
                friend.setEmail(cursor.getString(1));
                Log.d("Mekong89","What I get from cursor is "+cursor.getString(1));
                friend.setLocLong(Float.parseFloat(cursor.getString(2)));
                Log.d("Mekong89","What I get from cursor is "+cursor.getString(2));
                friend.setLocLat(Float.parseFloat(cursor.getString(3)));
                Log.d("Mekong89","What I get from cursor is "+cursor.getString(3));
                friend.setTimeStamp(Long.parseLong(cursor.getString(4)));
                Log.d("Mekong89","What I get from cursor is "+cursor.getString(4));
                // Adding contact to list
                meetList.add(friend);
            } while (cursor.moveToNext());
        }
        if(cursor != null && !cursor.isClosed()){
			cursor.close();
	    }  
        // return contact list
        return meetList;
    }
    public List<FriendEntry> getAllFriends() {
        List<FriendEntry> meetList = new ArrayList<FriendEntry>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_FRIENDS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FriendEntry friend = new FriendEntry();
                friend.setID(Integer.parseInt(cursor.getString(0)));
               
                friend.setEmail(cursor.getString(1));

                friend.setLocLong(Float.parseFloat(cursor.getString(2)));
   
                friend.setLocLat(Float.parseFloat(cursor.getString(3)));
       
                friend.setTimeStamp(Long.parseLong(cursor.getString(4)));
            
                // Adding contact to list
                meetList.add(friend);
            } while (cursor.moveToNext());
        }
        if(cursor != null && !cursor.isClosed()){
			cursor.close();
	    }  
        // return contact list
        return meetList;
    }
	// Deleting all friends
	void deleteAll()
	{
	    SQLiteDatabase db= this.getWritableDatabase();
	    db.delete(TABLE_FRIENDS, null, null);

	}
	
	
}
