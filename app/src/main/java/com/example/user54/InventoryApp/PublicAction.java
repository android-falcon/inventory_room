package com.example.user54.InventoryApp;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.print.Print;

import HPRTAndroidSDK.PublicFunction;

public class PublicAction
{	
	private Context context=null;	
	public PublicAction()
	{
		
	}
	public PublicAction(Context con)
	{
		context = con;
	}
	
	public void BeforePrintAction()
	{
		try
		{
			PublicFunction PFun=new PublicFunction(context);
			if(PFun.ReadSharedPreferencesData("Cut").equals("1") )
				Print.CutPaper(Print.HPRT_PARTIAL_CUT,PrinterProperty.CutSpacing);
			if(PFun.ReadSharedPreferencesData("Cashdrawer").equals("1"))
				Print.OpenCashdrawer(0);
			if(PFun.ReadSharedPreferencesData("Buzzer").equals("1") )
				Print.BeepBuzzer((byte)1,(byte)10,(byte)0);
		}
		catch(Exception e)
		{
			Log.e("HPRTSDKSample", (new StringBuilder("PublicAction --> BeforePrintAction ")).append(e.getMessage()).toString());
		}
	}
	
	public void AfterPrintAction()
	{
		try
		{
			PublicFunction PFun=new PublicFunction(context);
						
    		if(PFun.ReadSharedPreferencesData("Cashdrawer").equals("2") )
    			Print.OpenCashdrawer(0);
    		if(PFun.ReadSharedPreferencesData("Buzzer").equals("2") )
    			Print.BeepBuzzer((byte)1,(byte)10,(byte)10);
    		
    		int iFeed=Integer.valueOf(PFun.ReadSharedPreferencesData("Feeds"));
    		ArrayAdapter arrFeeds;
    		arrFeeds = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item);					
    		arrFeeds=ArrayAdapter.createFromResource(context, R.array.feeds_list, android.R.layout.simple_spinner_item);
    		arrFeeds.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    		iFeed=(Integer.valueOf(arrFeeds.getItem(iFeed).toString().replace("mm", "")));
    		Print.PrintAndFeed(iFeed*8);
    		if(PFun.ReadSharedPreferencesData("Cut").equals("2"))
    			Print.CutPaper(Print.HPRT_PARTIAL_CUT,PrinterProperty.CutSpacing);
			else
				Print.PrintAndFeed(PrinterProperty.TearSpacing);
		}
		catch(Exception e)
		{
			Log.e("HPRTSDKSample", (new StringBuilder("PublicAction --> AfterPrintAction ")).append(e.getMessage()).toString());
		}
	}
	
	public String LanguageEncode()
	{
		try
		{
			PublicFunction PFun=new PublicFunction(context);
			String sLanguage=PFun.ReadSharedPreferencesData("Codepage").split(",")[1].toString();
			String sLEncode="gb2312";
			int intLanguageNum=0;
			
			sLEncode=PFun.getLanguageEncode(sLanguage);		
			intLanguageNum= PFun.getCodePageIndex(sLanguage);

			Print.LanguageEncode=sLEncode;
			Print.SetCharacterSet((byte)intLanguageNum);

			return sLEncode;
		}
		catch(Exception e)
		{			
			Log.e("HPRTSDKSample", (new StringBuilder("PublicAction --> AfterPrintAction ")).append(e.getMessage()).toString());
			return "";
		}
	}
}