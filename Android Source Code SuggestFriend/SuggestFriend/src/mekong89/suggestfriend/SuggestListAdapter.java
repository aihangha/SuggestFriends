package mekong89.suggestfriend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SuggestListAdapter extends BaseAdapter {

	private Activity activity;
    private ArrayList<SuggestListModel> data;
    private static LayoutInflater inflater=null;
    SuggestListModel tempValues=null;
    int i=0;
	
	public SuggestListAdapter(Activity a,ArrayList<SuggestListModel> d) {
		// TODO Auto-generated constructor stub
		activity = a;
		data = d;
		inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(data.size()<=0)
            return 1;
        return data.size();
	}

	@Override
	public SuggestListModel getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public static class ViewHolder{
        
        public TextView name;
        public TextView meetCount;
        public ImageView image;
 
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
        ViewHolder holder;
        
        if(convertView==null){
            
            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.listitem, null);
             
            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.name = (TextView) vi.findViewById(R.id.txtFriendNameinMessage);
            holder.meetCount=(TextView)vi.findViewById(R.id.txtMeetCount);
            holder.image=(ImageView)vi.findViewById(R.id.imgAvatar);
             
           /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else{ 
            holder=(ViewHolder)vi.getTag();
        }
        if(data.size()<=0)
        {
            holder.name.setText("No Data");
             
        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = ( SuggestListModel ) data.get( position );

            /************  Set Model values in Holder elements ***********/

             holder.name.setText( tempValues.getName() );
             holder.meetCount.setText( "meet " + tempValues.getMeetCount() + "time(s)");
             holder.image.setImageDrawable(Utils.LoadImageFromWebOperations(Utils.avatarAddress + tempValues.getImageLink()));
              
             /******** Set Item Click Listner for LayoutInflater for each row *******/
             //vi.setOnClickListener(new OnClickListener( position ));
        }
        return vi;
	}

	
}
