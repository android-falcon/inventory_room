package com.example.user54.InventoryApp;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MacAdressAuth extends AppCompatActivity {
    TextView MacAddress;
//    Button ActivateButton;
    InventoryDatabase inventoryDatabase;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inventoryDatabase = new InventoryDatabase(MacAdressAuth.this);
//        String ac=inventoryDatabase.getActivate();
//        if(!ac.equals("")){
//            if(ac.equals("11A")){
//
//                Intent logIntent=new Intent(MacAdressAuth.this,LogIn.class);
//                startActivity(logIntent);
//                finish();
//            }
//        }else{
//            setContentView(R.layout.mac_adreass_activity_yellow);
//        }
//c4:9f:4c:96:d5:dd
        //48:3f:e9:86:ce:90
//0:10:20:98:eb:9b
        //14:9f:e8:1:49:2f
        if(true){
        if (getMacAddr().equals("48:3f:e9:86:ce:90") ||
                getMacAddr().equals("0:10:20:98:ed:3b") ||
                getMacAddr().equals("0:10:20:98:ec:29") ||
                getMacAddr().equals("0:10:20:98:ec:67") ||
                getMacAddr().equals("0:10:20:98:ec:ed") ||
                getMacAddr().equals("0:10:20:98:eb:9b") ||
                getMacAddr().equals("c4:9f:4c:96:d5:dd")) {//        if(getMacAddr().toUpperCase().equals("0:10:20:98:eb:96".toUpperCase())){

            Intent logIntent = new Intent(MacAdressAuth.this, LogIn.class);
            startActivity(logIntent);
            finish();
        } else {
            setContentView(R.layout.mac_adreass_activity_yellow);
            Toast.makeText(this, "Not Activate", Toast.LENGTH_SHORT).show();
            MacAddress = findViewById(R.id.MacAddress);
//        ActivateButton=findViewById(R.id.ActivateButton);

            MacAddress.setTextIsSelectable(true);
            MacAddress.setText("" + getMacAddr());
        }

    }else {

        Intent logIntent=new Intent(MacAdressAuth.this,LogIn.class);
        startActivity(logIntent);
        finish();

        }

//        ActivateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(getMacAddr().toUpperCase().equals("C4:9F:4C:96:D5:DD")){
//                    finish();
//                    Intent logIntent=new Intent(MacAdressAuth.this,LogIn.class);
//                    startActivity(logIntent);
//
//                    inventoryDatabase.addActive("11A");
//
//                }else{
////                    Toast.makeText(MacAdressAuth.this, "", Toast.LENGTH_SHORT).show();
//                    SweetAlertDialog sd2 = new SweetAlertDialog(MacAdressAuth.this,SweetAlertDialog.ERROR_TYPE);
//                    sd2.setCancelable(true);
//                    sd2.setCanceledOnTouchOutside(true);
//                    sd2.setContentText("Mac Address Not Activate");
//                    sd2.hideConfirmButton();
//                    sd2.show();
//                }
//            }
//        });
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {

            Log.e("ExceptionMac",""+ex.toString());

        }
        return "02:00:00:00:00:00";
    }

    public String getMacAddress() {
        WifiManager wimanager = (WifiManager) MacAdressAuth.this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String macAddress = wimanager.getConnectionInfo().getMacAddress();
        if (macAddress == null) {
            macAddress = "Device don't have mac address or wi-fi is disabled";
        }
        return macAddress;
    }

}
