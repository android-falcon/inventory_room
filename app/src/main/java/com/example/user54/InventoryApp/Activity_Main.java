package com.example.user54.InventoryApp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.print.Print;

import java.util.HashMap;
import java.util.Iterator;

import HPRTAndroidSDK.IPort;
import HPRTAndroidSDK.PublicFunction;

public class Activity_Main extends AppCompatActivity
{
	private Context thisCon=null;
	private BluetoothAdapter mBluetoothAdapter;
	private PublicFunction PFun=null;
	private PublicAction PAct=null;
	
	private Button btnWIFI=null;
	private Button btnBT=null;
	private Button btnUSB=null;
	private Button btnSerial=null;
	
	private Spinner spnPrinterList=null;
	private TextView txtTips=null;
	private Button btnOpenCashDrawer=null;
	private Button btnSampleReceipt=null;	
	private Button btn1DBarcodes=null;
	private Button btnQRCode=null;
	private Button btnPDF417=null;
	private Button btnCut=null;
	private Button btnPageMode=null;
	private Button btnImageManage=null;
	private Button btnGetRemainingPower=null;
	
	private EditText edtTimes=null;
	
	private ArrayAdapter arrPrinterList; 
	private String ConnectType="";
	private String PrinterName="";
	private String PortParam="";
	
	private UsbManager mUsbManager=null;	
	private UsbDevice device=null;
	private static final String ACTION_USB_PERMISSION = "com.PRINTSDKSample";
	private PendingIntent mPermissionIntent=null;
	private static IPort Printer=null;
	private static String[] PERMISSIONS_STORAGE = {
			"android.permission.READ_EXTERNAL_STORAGE",
			"android.permission.WRITE_EXTERNAL_STORAGE" };
	private static String[] wifi_PERMISSIONS={
			"android.permission.CHANGE_WIFI_STATE",
			"android.permission.ACCESS_WIFI_STATE"
	};
	String baudrate="";

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_printer);
		
		try
		{
			thisCon=this.getApplicationContext();
			
			btnWIFI = (Button) findViewById(R.id.btnWIFI);
			btnUSB = (Button) findViewById(R.id.btnUSB);
			btnBT = (Button) findViewById(R.id.btnBT);
			btnSerial = (Button) findViewById(R.id.btnSerial);
			
			//edtTimes = (EditText) findViewById(R.id.edtTimes);
			
			spnPrinterList = (Spinner) findViewById(R.id.spn_printer_list);	
			txtTips = (TextView) findViewById(R.id.txtTips);
			btnSampleReceipt = (Button) findViewById(R.id.btnSampleReceipt);
			btnOpenCashDrawer = (Button) findViewById(R.id.btnOpenCashDrawer);
			btn1DBarcodes = (Button) findViewById(R.id.btn1DBarcodes);
			btnQRCode = (Button) findViewById(R.id.btnQRCode);
			btnPDF417 = (Button) findViewById(R.id.btnPDF417);
			btnCut = (Button) findViewById(R.id.btnCut);
			btnPageMode = (Button) findViewById(R.id.btnPageMode);
			btnImageManage = (Button) findViewById(R.id.btnImageManage);
			btnGetRemainingPower = (Button) findViewById(R.id.btnGetRemainingPower);
					
			mPermissionIntent = PendingIntent.getBroadcast(thisCon, 0, new Intent(ACTION_USB_PERMISSION), 0);
	        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
			thisCon.registerReceiver(mUsbReceiver, filter);
			
			PFun=new PublicFunction(thisCon);
			PAct=new PublicAction(thisCon);
			InitSetting();
			InitCombox();
			this.spnPrinterList.setOnItemSelectedListener(new OnItemSelectedPrinter());
			//Enable Bluetooth
			EnableBluetooth();
			handler = new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					if (msg.what==1) {
						Toast.makeText(thisCon, "succeed", Toast.LENGTH_LONG).show();
						dialog.cancel();
					}else {
						Toast.makeText(thisCon, "failure", Toast.LENGTH_LONG).show();
						dialog.cancel();
					}
				}
			};
		}
		catch (Exception e) 
		{			
			Log.e("HPRTSDKSample", (new StringBuilder("Activity_Main --> onCreate ")).append(e.getMessage()).toString());
		}
	}
	
	private void InitSetting()
	{
		String SettingValue="";
		SettingValue=PFun.ReadSharedPreferencesData("Codepage");
		if(SettingValue.equals(""))		
			PFun.WriteSharedPreferencesData("Codepage", "0,PC437(USA:Standard Europe)");			
		
		SettingValue=PFun.ReadSharedPreferencesData("Cut");
		if(SettingValue.equals(""))		
			PFun.WriteSharedPreferencesData("Cut", "0");	//
			
		SettingValue=PFun.ReadSharedPreferencesData("Cashdrawer");
		if(SettingValue.equals(""))			
			PFun.WriteSharedPreferencesData("Cashdrawer", "0");
					
		SettingValue=PFun.ReadSharedPreferencesData("Buzzer");
		if(SettingValue.equals(""))			
			PFun.WriteSharedPreferencesData("Buzzer", "0");
					
		SettingValue=PFun.ReadSharedPreferencesData("Feeds");
		if(SettingValue.equals(""))			
			PFun.WriteSharedPreferencesData("Feeds", "0");				
	}
	
	//add printer list
	private void InitCombox()
	{
		try
		{
			arrPrinterList = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
			String strSDKType=thisCon.getString(com.example.user54.InventoryApp.R.string.sdk_type);
			if(strSDKType.equals("all"))
				arrPrinterList=ArrayAdapter.createFromResource(this, R.array.printer_list_all, android.R.layout.simple_spinner_item);
			if(strSDKType.equals("mkt"))
				arrPrinterList=ArrayAdapter.createFromResource(this, R.array.printer_list_mkt, android.R.layout.simple_spinner_item);
			if(strSDKType.equals("mprint"))
				arrPrinterList=ArrayAdapter.createFromResource(this, R.array.printer_list_mprint, android.R.layout.simple_spinner_item);
			if(strSDKType.equals("sycrown"))
				arrPrinterList=ArrayAdapter.createFromResource(this, R.array.printer_list_sycrown, android.R.layout.simple_spinner_item);
			if(strSDKType.equals("mgpos"))
				arrPrinterList=ArrayAdapter.createFromResource(this, R.array.printer_list_mgpos, android.R.layout.simple_spinner_item);
			if(strSDKType.equals("ds"))
				arrPrinterList=ArrayAdapter.createFromResource(this, R.array.printer_list_ds, android.R.layout.simple_spinner_item);
			if(strSDKType.equals("cst"))
				arrPrinterList=ArrayAdapter.createFromResource(this, R.array.printer_list_cst, android.R.layout.simple_spinner_item);
			if(strSDKType.equals("other"))
				arrPrinterList=ArrayAdapter.createFromResource(this, R.array.printer_list_other, android.R.layout.simple_spinner_item);
			arrPrinterList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			PrinterName=arrPrinterList.getItem(0).toString();
			spnPrinterList.setAdapter(arrPrinterList);
		}
		catch (Exception e) 
		{			
			Log.e("HPRTSDKSample", (new StringBuilder("Activity_Main --> InitCombox ")).append(e.getMessage()).toString());
		}
	}
	
	private class OnItemSelectedPrinter implements OnItemSelectedListener
	{				
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
		{
			PrinterName=arrPrinterList.getItem(arg2).toString();
//			CapturePrinterFunction();
//			GetPrinterProperty();
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) 
		{
			// TODO Auto-generated method stub			
		}
	}
	
	//EnableBluetooth
	private boolean EnableBluetooth()
    {
        boolean bRet = false;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter != null)
        {
            if(mBluetoothAdapter.isEnabled())
                return true;
            mBluetoothAdapter.enable();
            try 
    		{
    			Thread.sleep(500);
    		} 
    		catch (InterruptedException e) 
    		{			
    			e.printStackTrace();
    		}
            if(!mBluetoothAdapter.isEnabled())
            {
                bRet = true;
                Log.d("PRTLIB", "BTO_EnableBluetooth --> Open OK");
            }
        } 
        else
        {
        	Log.d("HPRTSDKSample", (new StringBuilder("Activity_Main --> EnableBluetooth ").append("Bluetooth Adapter is null.")).toString());
        }
        return bRet;
    }
	
	//call back by scan bluetooth printer
	@Override  
  	protected void onActivityResult(int requestCode, int resultCode, final Intent data)  
  	{  
  		try
  		{  		
  			String strIsConnected;
	  		switch(resultCode)
	  		{
	  			case Print.ACTIVITY_CONNECT_BT:
	  				strIsConnected=data.getExtras().getString("is_connected");
	  	        	if (strIsConnected.equals("NO")) {
	  	        		txtTips.setText(thisCon.getString(R.string.activity_main_scan_error));	  	        		
  	                	return;
	  	        	}
	  	        	else {
	  						txtTips.setText(thisCon.getString(R.string.activity_main_connected));
	  					return;
	  	        	}		  	        	
	  			case Print.ACTIVITY_CONNECT_WIFI:
	  				strIsConnected=data.getExtras().getString("is_connected");
	  	        	if (strIsConnected.equals("NO")) {
	  	        		txtTips.setText(thisCon.getString(R.string.activity_main_scan_error));	  	        		
  	                	return;
	  	        	}
	  	        	else {
						txtTips.setText(thisCon.getString(R.string.activity_main_connected));
	  					return;
	  	        	}		  	        	
	  			case Print.ACTIVITY_IMAGE_FILE:
				dialog = new ProgressDialog(Activity_Main.this);
				dialog.setMessage("Printing.....");
				dialog.setProgress(100);
				dialog.show();
	  				new Thread(){
	  					public void run() {
	  						PAct.BeforePrintAction();
	  						String strImageFile=data.getExtras().getString("FilePath");
	  						Bitmap bmp= BitmapFactory.decodeFile(strImageFile);
	  						int printImage=0;
							try {
								printImage= Print.PrintBitmap(bmp,  (byte)0, (byte)0,200);
								if (printImage>0) {
									handler.sendEmptyMessage(1);
								}else {
									handler.sendEmptyMessage(0);
								}
							} catch (Exception e){
								handler.sendEmptyMessage(0);
							}
	  						PAct.AfterPrintAction();
	  					};
	  				}.start();
	  				return;
	  			case Print.ACTIVITY_PRNFILE:
	  		    	PAct.LanguageEncode();
	  		    	PAct.BeforePrintAction();
	  				String strPRNFile=data.getExtras().getString("FilePath");
	  				System.out.println(strPRNFile);
					Print.PrintBinaryFile(strPRNFile);
	  				PAct.AfterPrintAction();
	  				return;
  			}
  		}
  		catch(Exception e)
  		{
  			Log.e("HPRTSDKSample", (new StringBuilder("Activity_Main --> onActivityResult ")).append(e.getMessage()).toString());
  		}
        super.onActivityResult(requestCode, resultCode, data);  
  	} 
	@SuppressLint("NewApi")
	public void onClickConnect(View view) 
	{		
    	//if (!checkClick.isClickEvent()) return;
    	
    	try
    	{
	    	if(view.getId()==R.id.btnBT)
	    	{
//                if (Build.VERSION.SDK_INT >= 23) {
//                    //校验是否已具有模糊定位权限
//                    if (ContextCompat.checkSelfPermission(Activity_Main.this,
//                            android.Manifest.permission.ACCESS_COARSE_LOCATION)
//                            != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(Activity_Main.this,
//                                new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
//                                100);
//                    } else {
//                        //具有权限
//                        ConnectType="Bluetooth";
//                        Intent serverIntent = new Intent(thisCon,Activity_DeviceList.class);
//                        startActivityForResult(serverIntent, Print.ACTIVITY_CONNECT_BT);
//                        return;
//                    }
//                } else
                {
                    //系统不高于6.0直接执行
                    ConnectType="Bluetooth";
                    Intent serverIntent = new Intent(thisCon,Activity_DeviceList.class);
                    startActivityForResult(serverIntent, Print.ACTIVITY_CONNECT_BT);
                }
				return;
	    	}
	    	else if(view.getId()==R.id.btnWIFI)
	    	{
				Utility.checkBlueboothPermission(Activity_Main.this, android.Manifest.permission.ACCESS_WIFI_STATE, wifi_PERMISSIONS, new Utility.Callback() {
					@Override
					public void permit() {
						ConnectType="WiFi";
						Intent serverIntent = new Intent(thisCon,Activity_Wifi.class);
						serverIntent.putExtra("PN", PrinterName);
						startActivityForResult(serverIntent, Print.ACTIVITY_CONNECT_WIFI);
						return;
					}

					@Override
					public void pass() {
						ConnectType="WiFi";
						Intent serverIntent = new Intent(thisCon,Activity_Wifi.class);
						serverIntent.putExtra("PN", PrinterName);
						startActivityForResult(serverIntent, Print.ACTIVITY_CONNECT_WIFI);
						return;
					}
				});

	    	}
	    	else if(view.getId()==R.id.btnUSB)
	    	{
	    		ConnectType="USB";							
				//USB not need call "iniPort"
				mUsbManager = (UsbManager) thisCon.getSystemService(Context.USB_SERVICE);				
		  		HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();  		
		  		Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
		  		
		  		boolean HavePrinter=false;		  
		  		while(deviceIterator.hasNext())
		  		{
		  		    device = deviceIterator.next();
		  		    int count = device.getInterfaceCount();
		  		    for (int i = 0; i < count; i++) 
		  	        {
		  		    	UsbInterface intf = device.getInterface(i); 
		  	            if (intf.getInterfaceClass() == 7)
		  	            {
		  	            	HavePrinter=true;
		  	            	mUsbManager.requestPermission(device, mPermissionIntent);		  	            	
		  	            }
		  	        }
		  		}
		  		if(!HavePrinter)
		  			txtTips.setText(thisCon.getString(R.string.activity_main_connect_usb_printer));	
	    	}
	    	else if(view.getId()==R.id.btnSerial){
	    		//ConantSerial(Activity_Main.this);
	    	}
    	}
		catch (Exception e) 
		{			
			Log.e("HPRTSDKSample", (new StringBuilder("Activity_Main --> onClickConnect "+ConnectType)).append(e.getMessage()).toString());
		}
    }
		   			
	private BroadcastReceiver mUsbReceiver = new BroadcastReceiver() 
	{
	    public void onReceive(Context context, Intent intent) 
	    {
	    	try
	    	{
		        String action = intent.getAction();	       
		        if (ACTION_USB_PERMISSION.equals(action))
		        {
			        synchronized (this) 
			        {		        	
			            device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
				        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false))
				        {			 
				        	if(Print.PortOpen(thisCon,device)!=0)
							{					
								txtTips.setText(thisCon.getString(R.string.activity_main_connecterr));
			                	return;
							}
				        	else
				        		txtTips.setText(thisCon.getString(R.string.activity_main_connected));
				        		
				        }		
				        else
				        {			        	
				        	return;
				        }
			        }
			    }
		        if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action))
		        {
		            device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
		            if (device != null) 
		            {
						Print.PortClose();
		            }
		        }	    
	    	} 
	    	catch (Exception e) 
	    	{
	    		Log.e("HPRTSDKSample", (new StringBuilder("Activity_Main --> mUsbReceiver ")).append(e.getMessage()).toString());
	    	}
		}
	};
	private Handler handler;
	private ProgressDialog dialog;
	
	public void onClickClose(View view) 
	{
    	if (!checkClick.isClickEvent()) return;
    	
    	try
    	{

			Print.PortClose();

			this.txtTips.setText(R.string.activity_main_tips);
			return;	
    	}
		catch (Exception e) 
		{			
			Log.e("HPRTSDKSample", (new StringBuilder("Activity_Main --> onClickClose ")).append(e.getMessage()).toString());
		}
    }
	
	public void onClickbtnSetting(View view) 
	{
    	if (!checkClick.isClickEvent()) return;
    	
    	try
    	{
    		startActivity(new Intent(Activity_Main.this, Activity_Setting.class));
    	}
		catch (Exception e) 
		{			
			Log.e("HPRTSDKSample", (new StringBuilder("Activity_Main --> onClickClose ")).append(e.getMessage()).toString());
		}
    }
	
	public void onClickDo(View view) 
	{
		if (!checkClick.isClickEvent()) return;
		
		if(!Print.IsOpened())
		{
			Toast.makeText(thisCon, thisCon.getText(R.string.activity_main_tips), Toast.LENGTH_SHORT).show();				
			return;
		}
		    	    	
    	if(view.getId()==R.id.btnGetStatus)
    	{
//    		Intent myIntent = new Intent(this, Activity_Status.class);
//        	myIntent.putExtra("StatusMode", PrinterProperty.StatusMode);
//        	startActivityFromChild(this, myIntent, 0);
    	}
    	else if(view.getId()==R.id.btnOpenCashDrawer)
    	{
//    		Intent myIntent = new Intent(this, Activity_Cashdrawer.class);
//        	startActivityFromChild(this, myIntent, 0);
    	}
    	else if(view.getId()==R.id.btnSampleReceipt)
    	{
    		PrintSampleReceipt();
    	}
    	else if(view.getId()==R.id.btn1DBarcodes)
    	{
//    		Intent myIntent = new Intent(this, Activity_1DBarcodes.class);
//        	startActivityFromChild(this, myIntent, 0);
    	}
    	else if(view.getId()==R.id.btnCut)
    	{
//    		Intent myIntent = new Intent(this, Activity_Cut.class);
//        	startActivityFromChild(this, myIntent, 0);
    	}
    	else if(view.getId()==R.id.btnTextFormat)
    	{
//    		Intent myIntent = new Intent(this, Activity_TextFormat.class);
//        	startActivityFromChild(this, myIntent, 0);
    	}
    	else if(view.getId()==R.id.btnPrintImageFile)
    	{
//			Utility.checkBlueboothPermission(com.printsdksample.Activity_Main.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSIONS_STORAGE, new Utility.Callback() {
//				@Override
//				public void permit() {
//					Intent myIntent = new Intent(com.printsdksample.Activity_Main.this, Activity_PRNFile.class);
//					myIntent.putExtra("Folder", android.os.Environment.getExternalStorageDirectory().getAbsolutePath());
//					myIntent.putExtra("FileFilter", "jpg,gif,png,bmp,pdf,");
//					startActivityForResult(myIntent, Print.ACTIVITY_IMAGE_FILE);
//				}
//
//				@Override
//				public void pass() {
//					Intent myIntent = new Intent(com.printsdksample.Activity_Main.this, Activity_PRNFile.class);
//					myIntent.putExtra("Folder", android.os.Environment.getExternalStorageDirectory().getAbsolutePath());
//					myIntent.putExtra("FileFilter", "jpg,gif,png,bmp,pdf,");
//					startActivityForResult(myIntent, Print.ACTIVITY_IMAGE_FILE);
//				}
//			});

		}
    	else if(view.getId()==R.id.btnPrintPRNFile)
    	{
//    		Intent myIntent = new Intent(this, Activity_PRNFile.class);
//        	myIntent.putExtra("Folder", android.os.Environment.getExternalStorageDirectory().getAbsolutePath());
//        	myIntent.putExtra("FileFilter", "prn,");
//        	startActivityForResult(myIntent, Print.ACTIVITY_PRNFILE);
    	}
    	else if(view.getId()==R.id.btnPageMode)
    	{
//    		String[] sArrXY;
//        	sArrXY=PrinterProperty.PagemodeArea.split(",");
//        	Intent myIntent = new Intent(this, Activity_PageMode.class);
//        	myIntent.putExtra("PageModeW",sArrXY[0]);
//        	myIntent.putExtra("PageModeH",sArrXY[1]);
//        	startActivityFromChild(this, myIntent, 0);
    	}
    	else if(view.getId()==R.id.btnQRCode)
    	{
//    		Intent myIntent = new Intent(this, Activity_QRCode.class);
//        	startActivityFromChild(this, myIntent, 0);
    	}    	
    	else if(view.getId()==R.id.btnPDF417)
    	{
//    		Intent myIntent = new Intent(this, Activity_PDF417.class);
//    		startActivityFromChild(this, myIntent, 0);
    	}    	
    	else if(view.getId()==R.id.btnImageManage)
    	{
			Utility.checkBlueboothPermission(Activity_Main.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSIONS_STORAGE, new Utility.Callback() {
				@Override
				public void permit() {
//					Intent myIntent = new Intent(Activity_Main.this, Activity_Image_Manage.class);
//					startActivityFromChild(Activity_Main.this, myIntent, 0);
				}

				public void pass() {
//					Intent myIntent = new Intent(com.printsdksample.Activity_Main.this, Activity_Image_Manage.class);
//					startActivityFromChild(com.printsdksample.Activity_Main.this, myIntent, 0);
				}
			});
		}else if(view.getId()==R.id.btnPrintTestPage) {
			PrintTestPage();
    	}
    }
	
	public void PrintTestPage() 
	{    	
    	try
    	{    		    		
    		PAct.LanguageEncode();
    		PAct.BeforePrintAction();  
    		
    		String strPrintText="Print SDK Sample!";
			Print.PrintText(thisCon.getString(R.string.activity_main_originalsize) + strPrintText+"\n",0,0,0);
			Print.PrintText(thisCon.getString(R.string.activity_main_heightsize) + strPrintText+"\n",0,16,0);
			Print.PrintText(thisCon.getString(R.string.activity_main_widthsize) + strPrintText+"\n",0,32,0);
			Print.PrintText(thisCon.getString(R.string.activity_main_heightwidthsize) + strPrintText+"\n",0,48,0);
			Print.PrintText(thisCon.getString(R.string.activity_main_bold) + strPrintText+"\n",0,2,0);
			Print.PrintText(thisCon.getString(R.string.activity_main_underline) + strPrintText+"\n",0,4,0);
			Print.PrintText(thisCon.getString(R.string.activity_main_minifront) + strPrintText+"\n",0,1,0);

			//"UPC-A,UPC-E,EAN8,EAN13,CODE39,ITF,CODEBAR,CODE128,CODE93,QRCODE"
				Barcode_BC_UPCA();
				Barcode_BC_UPCE();
				Barcode_BC_EAN8();
				Barcode_BC_EAN13();
				Barcode_BC_CODEBAR();
				Barcode_BC_ITF();
			PAct.AfterPrintAction();
    	}
		catch (Exception e) 
		{
			Log.e("HPRTSDKSample", (new StringBuilder("Activity_Main --> onClickWIFI ")).append(e.getMessage()).toString());
		}
    }

	private int Barcode_BC_UPCA() throws Exception
	{
		Print.PrintText("BC_UPCA:\n");
		return Print.PrintBarCode(Print.BC_UPCA,
		 		 "075678164125");
	}
	
	private int Barcode_BC_UPCE() throws Exception
	{
		Print.PrintText("BC_UPCE:\n");
		return Print.PrintBarCode(Print.BC_UPCE,
	 		 "01227000009");//04252614 
	}
	
	private int Barcode_BC_EAN8() throws Exception
	{
		Print.PrintText("BC_EAN8:\n");
		return Print.PrintBarCode(Print.BC_EAN8,
	 		 "04210009");		
	}
	
	private int Barcode_BC_EAN13() throws Exception
	{
		Print.PrintText("BC_EAN13:\n");
		return Print.PrintBarCode(Print.BC_EAN13,
		 		 "6901028075831");		
	}
	
	private int Barcode_BC_CODE93() throws Exception
	{
		Print.PrintText("BC_CODE93:\n");
		return Print.PrintBarCode(Print.BC_CODE93,
	 		 "TEST93");		
	}
	
	private int Barcode_BC_CODE39() throws Exception
	{
		Print.PrintText("BC_CODE39:\n");
		return Print.PrintBarCode(Print.BC_CODE39,
	 		 "123456789");		
	}
	
	private int Barcode_BC_CODEBAR() throws Exception
	{
		Print.PrintText("BC_CODEBAR:\n");
		return Print.PrintBarCode(Print.BC_CODEBAR,
	 		 "A40156B");		
	}
	
	private int Barcode_BC_ITF() throws Exception
	{
		Print.PrintText("BC_ITF:\n");
		return Print.PrintBarCode(Print.BC_ITF,
			"123456789012");		
	}
	
	private int Barcode_BC_CODE128() throws Exception
	{
		Print.PrintText("BC_CODE128:\n");
		return Print.PrintBarCode(Print.BC_CODE128,
	 		 "{BS/N:{C\014\042\070\116{A3");	// decimal 1234 = octonary 1442		
	}

	private void CapturePrinterFunction()
	{
		try
		{
			int[] propType=new int[1];
			byte[] Value=new byte[500];
			int[] DataLen=new int[1];
			String strValue="";
			boolean isCheck=false;
			
			int iRtn= Print.CapturePrinterFunction(Print.HPRT_MODEL_PROPERTY_KEY_BEEP, propType, Value,DataLen);
			if(iRtn!=0)
				return;			
			PrinterProperty.Buzzer=(Value[0]==0?false:true);
			
			iRtn= Print.CapturePrinterFunction(Print.HPRT_MODEL_PROPERTY_KEY_CUT, propType, Value,DataLen);
			if(iRtn!=0)
				return;			
			PrinterProperty.Cut=(Value[0]==0?false:true);
			btnCut.setVisibility((PrinterProperty.Cut?View.VISIBLE:View.GONE));
			
			iRtn= Print.CapturePrinterFunction(Print.HPRT_MODEL_PROPERTY_KEY_DRAWER, propType, Value,DataLen);
			if(iRtn!=0)
				return;		
			PrinterProperty.Cashdrawer=(Value[0]==0?false:true);
			btnOpenCashDrawer.setVisibility((PrinterProperty.Cashdrawer?View.VISIBLE:View.GONE));
			
			iRtn= Print.CapturePrinterFunction(Print.HPRT_MODEL_PROPERTY_KEY_BARCODE, propType, Value,DataLen);
			if(iRtn!=0)
				return;						
			PrinterProperty.Barcode=new String(Value);
			isCheck=PrinterProperty.Barcode.replace("QRCODE", "").replace("PDF417", "").replace(",,", ",").replace(",,", ",").length()>0;
			btn1DBarcodes.setVisibility((isCheck?View.VISIBLE:View.GONE));								
			isCheck = PrinterProperty.Barcode.contains("QRCODE");
			btnQRCode.setVisibility((isCheck?View.VISIBLE:View.GONE));
			btnPDF417.setVisibility((PrinterProperty.Barcode.indexOf("PDF417") != -1?View.VISIBLE:View.GONE));
			
			iRtn= Print.CapturePrinterFunction(Print.HPRT_MODEL_PROPERTY_KEY_PAGEMODE, propType, Value,DataLen);
			if(iRtn!=0)
				return;		
			PrinterProperty.Pagemode=(Value[0]==0?false:true);
			if (PrinterName.equals("MLP2")) {
				btnPageMode.setVisibility(View.VISIBLE);
			}else {
				btnPageMode.setVisibility((PrinterProperty.Pagemode?View.VISIBLE:View.GONE));
			}
			
			iRtn= Print.CapturePrinterFunction(Print.HPRT_MODEL_PROPERTY_KEY_GET_REMAINING_POWER, propType, Value,DataLen);
			if(iRtn!=0)
				return;	
			PrinterProperty.GetRemainingPower=(Value[0]==0?false:true);
			btnGetRemainingPower.setVisibility((PrinterProperty.GetRemainingPower?View.VISIBLE:View.GONE));
			
			iRtn= Print.CapturePrinterFunction(Print.HPRT_MODEL_PROPERTY_CONNECT_TYPE, propType, Value,DataLen);
			if(iRtn!=0)
				return;	
			PrinterProperty.ConnectType=(Value[1]<<8)+Value[0];
			btnWIFI.setVisibility(((PrinterProperty.ConnectType&1)==0?View.GONE:View.VISIBLE));
			btnUSB.setVisibility(((PrinterProperty.ConnectType&16)==0?View.GONE:View.VISIBLE));
			btnBT.setVisibility(((PrinterProperty.ConnectType&32)==0?View.GONE:View.VISIBLE));
			
			iRtn= Print.CapturePrinterFunction(Print.HPRT_MODEL_PROPERTY_KEY_PRINT_RECEIPT, propType, Value,DataLen);
			if(iRtn!=0)
				return;			
			PrinterProperty.SampleReceipt=(Value[0]==0?false:true);
			btnSampleReceipt.setVisibility((PrinterProperty.SampleReceipt?View.VISIBLE:View.GONE));							
		}
		catch(Exception e)
		{
			Log.e("HPRTSDKSample", (new StringBuilder("Activity_Main --> CapturePrinterFunction ")).append(e.getMessage()).toString());
		}
	}
	
	private void GetPrinterProperty()
	{
		try
		{
			int[] propType=new int[1];
			byte[] Value=new byte[500];
			int[] DataLen=new int[1];
			String strValue="";			
			int iRtn=0;
			
			iRtn= Print.CapturePrinterFunction(Print.HPRT_MODEL_PROPERTY_KEY_STATUS_MODEL, propType, Value,DataLen);
			if(iRtn!=0)
				return;			
			PrinterProperty.StatusMode=Value[0];
			
			if(PrinterProperty.Cut)
			{
				iRtn= Print.CapturePrinterFunction(Print.HPRT_MODEL_PROPERTY_KEY_CUT_SPACING, propType, Value,DataLen);
				if(iRtn!=0)
					return;			
				PrinterProperty.CutSpacing=Value[0];				
			}
			else
			{
				iRtn= Print.CapturePrinterFunction(Print.HPRT_MODEL_PROPERTY_KEY_TEAR_SPACING, propType, Value,DataLen);
				if(iRtn!=0)
					return;		
				PrinterProperty.TearSpacing=Value[0];				
			}	
			
			if(PrinterProperty.Pagemode)
			{
				iRtn= Print.CapturePrinterFunction(Print.HPRT_MODEL_PROPERTY_KEY_PAGEMODE_AREA, propType, Value,DataLen);
				if(iRtn!=0)
					return;			
				PrinterProperty.PagemodeArea=new String(Value).trim();				
			}
			Value=new byte[500];
			iRtn= Print.CapturePrinterFunction(Print.HPRT_MODEL_PROPERTY_KEY_WIDTH, propType, Value,DataLen);
			if(iRtn!=0)
				return;			
			PrinterProperty.PrintableWidth=(int)(Value[0] & 0xFF | ((Value[1] & 0xFF) <<8));
		}
		catch(Exception e)
		{
			Log.e("HPRTSDKSample", (new StringBuilder("Activity_Main --> CapturePrinterFunction ")).append(e.getMessage()).toString());
		}
	}
	
	private void PrintSampleReceipt()
	{
		try
		{
			byte[] data=new byte[]{0x1b,0x40};
			Print.WriteData(data);
			PAct.LanguageEncode();
		    PAct.BeforePrintAction();
			String[] ReceiptLines = getResources().getStringArray(R.array.activity_main_sample_2inch_receipt);
			for(int i=0;i<ReceiptLines.length;i++)
				Print.PrintText(ReceiptLines[i]);
			PAct.AfterPrintAction();
		}
		catch(Exception e)
		{
			Log.e("HPRTSDKSample", (new StringBuilder("Activity_Main --> PrintSampleReceipt ")).append(e.getMessage()).toString());
		}
	}
//	private void ConantSerial(Context context){
//		AlertDialog.Builder builder=new AlertDialog.Builder(context);
//		View inflate = LayoutInflater.from(context).inflate(R.layout.item_serial_dialog, null);
//		final Spinner sp_serial_baudrate = (Spinner) inflate.findViewById(R.id.sp_serial_baudrate);
//		final String[] sList=new String[]{"9600","14400","19200","115200"};
//		arrPrinterList=new ArrayAdapter<String>(com.printsdksample.Activity_Main.this,android.R.layout.simple_spinner_item,sList);
//		sp_serial_baudrate.setAdapter(arrPrinterList);
//		sp_serial_baudrate.setOnItemSelectedListener(new OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//				baudrate=sList[position];
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> parent) {
//
//			}
//		});
//		final EditText ed_serial_port = (EditText) inflate.findViewById(R.id.ed_serial_port);
//		builder.setPositiveButton(getResources().getString(R.string.activity_wifi_btnconnect), new AlertDialog.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				String port = ed_serial_port.getText().toString();
//				if ("".equals(baudrate)) {
//					Toast.makeText(getApplicationContext(), getString(R.string.serial_dialog_nobaudrate),Toast.LENGTH_LONG).show();
//					return;
//				}
//				if ("".equals(port)) {
//					Toast.makeText(getApplicationContext(), getString(R.string.serial_dialog_noport), Toast.LENGTH_LONG).show();
//					return;
//				}
//				try {
//					int portOpen = Print.PortOpen(thisCon,"Serial,"+port+","+baudrate);
//					if (portOpen==-1) {
//						txtTips.setText(thisCon.getString(R.string.activity_main_connecterr));
//					}else {
//						txtTips.setText(thisCon.getString(R.string.activity_main_connected));
//					}
//				} catch (Exception e) {
//					txtTips.setText(thisCon.getString(R.string.activity_main_connecterr));
//				}
//			}
//		});
//		builder.setNegativeButton(getString(R.string.activity_wifi_btncancel), null);
//		builder.setView(inflate);
//		builder.show();
//	}
}
