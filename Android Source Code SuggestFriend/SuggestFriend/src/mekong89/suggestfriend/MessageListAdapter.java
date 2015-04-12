package mekong89.suggestfriend;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MessageListAdapter extends BaseAdapter {

	private Activity activity;
    private ArrayList<MessageModel> data;
    private static LayoutInflater inflater=null;
    MessageModel tempValues=null;
    int i=0;
	Bitmap avatar;
	public MessageListAdapter(Activity a,ArrayList<MessageModel> d, Bitmap avatar) {
		// TODO Auto-generated constructor stub
		activity = a;
		data = d;
		inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.avatar = avatar;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(data.size()<=0)
            return 1;
        return data.size();
	}

	@Override
	public MessageModel getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public static class ViewHolder{
        
        public TextView content;
        public ImageView image;
        public TextView postdate;
        public LinearLayout linearLayout;
        public ImageView imgState;
        
 
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
        ViewHolder holder;
        
        if(convertView==null){
            
            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.messageitem, null);
             
            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.content = (TextView) vi.findViewById(R.id.txtMessageInMessageList);
            holder.image=(ImageView)vi.findViewById(R.id.imgAvatarInMessageList);
            holder.postdate = (TextView) vi.findViewById(R.id.lblTimeInMessageList);
            holder.imgState = (ImageView)vi.findViewById(R.id.imgStateInMessageList);
            holder.linearLayout = (LinearLayout) vi.findViewById(R.id.layoutMessageContentInMessageList);
             
           /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else{ 
            holder=(ViewHolder)vi.getTag();
        }
        if(data.size()<=0)
        {
            holder.content.setText("No Message");
             
        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempValues = ( MessageModel ) data.get( position );           

            /************  Set Model values in Holder elements ***********/
             LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.linearLayout.getLayoutParams();
             if(tempValues.getFromMe()){
            	 holder.image.setVisibility(View.GONE);
            	 layoutParams.setMargins(40, 0, 15, 8);
            	 holder.linearLayout.setBackgroundColor(Color.rgb(216, 248, 252));
            	 holder.imgState.setVisibility(View.VISIBLE);
            	 if(tempValues.getState().equals("uploaded")){
            		 holder.imgState.setImageResource(R.drawable.wait);
            	 } else if(tempValues.getState().equals("delivered")){
            		 holder.imgState.setImageResource(R.drawable.delivered);
            	 } else{
            		 holder.imgState.setImageResource(R.drawable.readed);
            	 }
            	 
             } else{
            	 holder.image.setImageBitmap(avatar);
            	 layoutParams.setMargins(5, 0, 50, 8);
            	 holder.linearLayout.setBackgroundColor(Color.rgb(255, 255, 255));
            	 holder.image.setVisibility(View.VISIBLE);
            	 holder.imgState.setVisibility(View.GONE);
            	 
            	 holder.image.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(activity instanceof xMessage){
							xMessage xMessage = (mekong89.suggestfriend.xMessage) activity;
							Intent userHome = new Intent(activity,
									UserHomeActivity.class);
							userHome.putExtra("Email", xMessage.friendMail);
							activity.startActivity(userHome);
						}
					}
				});
             }
             holder.linearLayout.setLayoutParams(layoutParams);            
             holder.content.setText( tempValues.getContent() );                         
             holder.postdate.setText(Utils.getDateString(tempValues.getSendTime()));
             /******** Set Item Click Listner for LayoutInflater for each row *******/
             //vi.setOnClickListener(new OnClickListener( position ));
        }
        return vi;
	}

	
}
