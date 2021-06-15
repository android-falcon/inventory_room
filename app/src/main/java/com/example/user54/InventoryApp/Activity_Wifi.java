package com.example.user54.InventoryApp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.print.Print;


public class Activity_Wifi extends Activity
{	
	private Context thisCon=null;
	private Print HPRTPrinter=new Print();
	private EditText edtIP=null;
	private EditText edtPort=null;
	private TextView txtTips=null;
	
	private String PrinterName="";
	private ProgressBar pro_bar;
	private Handler handler;

	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);	   
		this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_wifi);			
		
		thisCon=this.getApplicationContext();
		edtIP = (EditText) findViewById(R.id.txtIPAddress);
		edtPort = (EditText) findViewById(R.id.txtWifiPort);	
		txtTips = (TextView) findViewById(R.id.txtTips);
		pro_bar = (ProgressBar) findViewById(R.id.pro_bar);

		Intent intent = getIntent();
		PrinterName=intent.getStringExtra("PN");
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				pro_bar.setVisibility(View.GONE);
				Intent intent = new Intent();
				intent.putExtra("is_connected", "OK");
				setResult(Print.ACTIVITY_CONNECT_WIFI, intent);
				finish();
			}
		};
	}

	public void onClickConnect(View view) 
	{
    	if (!checkClick.isClickEvent()) return;
    	
    	try
    	{
	    	if(HPRTPrinter!=null)
			{					
	    		HPRTPrinter.PortClose();
			}
			
			final String strIP=edtIP.getText().toString().trim();
			final String strPort=edtPort.getText().toString().trim();
			if(strIP.length()==0)
			{
				Toast.makeText(thisCon, R.string.activity_wifi_noIP, Toast.LENGTH_SHORT).show();
            	return;
			}
			pro_bar.setVisibility(View.VISIBLE);
			new Thread(){
				@Override
				public void run() {
					super.run();
					try {
						if(HPRTPrinter.PortOpen(thisCon,"WiFi,"+strIP+","+strPort)!=0)
						{
							HPRTPrinter=null;
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									pro_bar.setVisibility(View.GONE);
									txtTips.setText(thisCon.getString(R.string.activity_main_connecterr));
								}
							});
						}
						else
						{
							Message message = new Message();
							handler.sendMessage(message);
						}
					} catch (Exception e) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								pro_bar.setVisibility(View.GONE);
								txtTips.setText(thisCon.getString(R.string.activity_main_connecterr));
							}
						});
					}
				}
			}.start();

    	}
		catch (Exception e) 
		{			
			Log.d("HPRTSDKSample", (new StringBuilder("Activity_Wifi --> onClickConnect ")).append(e.getMessage()).toString());
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					pro_bar.setVisibility(View.GONE);
					txtTips.setText(thisCon.getString(R.string.activity_main_connecterr));
				}
			});
		}
    }
	
	public void onClickCancel(View view) 
	{
    	if (!checkClick.isClickEvent()) return;
    	
    	try
    	{
	    	if(HPRTPrinter!=null)
			{					
	    		HPRTPrinter.PortClose();
			}
			
			this.finish();
    	}
		catch (Exception e) 
		{			
			Log.d("HPRTSDKSample", (new StringBuilder("Activity_Wifi --> onClickCancel ")).append(e.getMessage()).toString());
		}
    }
}
