package com.example.user54.InventoryApp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.example.user54.InventoryApp.Model.ItemCard;
import com.example.user54.InventoryApp.Port.AlertView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.sewoo.request.android.RequestHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;


// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)

public class bMITP extends Activity {
    private static final String TAG = "BluetoothConnectMenu";
    private static final int REQUEST_ENABLE_BT = 2;
    ArrayAdapter<String> adapter;
    private BluetoothAdapter mBluetoothAdapter;
    private Vector<BluetoothDevice> remoteDevices;
    private BroadcastReceiver searchFinish;
    private BroadcastReceiver searchStart;
    private BroadcastReceiver discoveryResult;
    private BroadcastReceiver disconnectReceiver;
    private Thread hThread;
    private Context context;
    private EditText btAddrBox;
    private Button connectButton;
    private Button searchButton;
    private ListView list;
    private BluetoothPort bluetoothPort;
    private CheckBox chkDisconnect;
    private static final String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "//temp";
    private static final String fileName;
    private String lastConnAddr;
    static String idname;

    String getData;

    List<Item> itemPrint;
    List<Item> allStudents;

    DecimalFormat numberFormat = new DecimalFormat("0.000");


    static {
        fileName = dir + "//BTPrinter";
    }

    public bMITP() {

    }

    private void bluetoothSetup() {
        this.clearBtDevData();
        this.bluetoothPort = BluetoothPort.getInstance();
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (this.mBluetoothAdapter != null) {
            if (!this.mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
                this.startActivityForResult(enableBtIntent, 2);
            }

        }
    }

    private void loadSettingFile() {
//        int rin = false;
        char[] buf = new char[128];

        try {
            FileReader fReader = new FileReader(fileName);
            int rin = fReader.read(buf);
            if (rin > 0) {
                this.lastConnAddr = new String(buf, 0, rin);
                this.btAddrBox.setText(this.lastConnAddr);
            }

            fReader.close();
        } catch (FileNotFoundException var4) {
            Log.i("BluetoothConnectMenu", "Connection history not exists.");
        } catch (IOException var5) {
            Log.e("BluetoothConnectMenu", var5.getMessage(), var5);
        }

    }

    private void saveSettingFile() {
        try {
            File tempDir = new File(dir);
            if (!tempDir.exists()) {
                tempDir.mkdir();
            }

            FileWriter fWriter = new FileWriter(fileName);
            if (this.lastConnAddr != null) {
                fWriter.write(this.lastConnAddr);
            }

            fWriter.close();
        } catch (FileNotFoundException var3) {
            Log.e("BluetoothConnectMenu", var3.getMessage(), var3);
        } catch (IOException var4) {
            Log.e("BluetoothConnectMenu", var4.getMessage(), var4);
        }

    }

    private void clearBtDevData() {
        this.remoteDevices = new Vector();
    }

    private void addPairedDevices() {
        Iterator iter = this.mBluetoothAdapter.getBondedDevices().iterator();

        while (iter.hasNext()) {
            BluetoothDevice pairedDevice = (BluetoothDevice) iter.next();
//            if (this.bluetoothPort.isValidAddress(pairedDevice.getAddress())) {
            this.remoteDevices.add(pairedDevice);
            this.adapter.add(pairedDevice.getName() + "\n[" + pairedDevice.getAddress() + "] [Paired]");
        }
//        }

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.bluetooth_menu_esc);
        this.btAddrBox = (EditText) this.findViewById(R.id.EditTextAddressBT);
        this.connectButton = (Button) this.findViewById(R.id.ButtonConnectBT);
        bMITP.this.connectButton.setEnabled(true);
        this.searchButton = (Button) this.findViewById(R.id.ButtonSearchBT);
        this.list = (ListView) this.findViewById(R.id.BtAddrListView);
        this.chkDisconnect = (CheckBox) this.findViewById(R.id.check_disconnect);
        this.chkDisconnect.setChecked(true);
        this.context = this;



//
        getData = getIntent().getStringExtra("printKey");
//        Bundle bundle = getIntent().getExtras();
//         allStudents = (List<Item>) bundle.get("ExtraData");
//
//         Log.e("all",allStudents.get(0).getBarcode());

        Log.e("printKey", "" + getData);
        this.loadSettingFile();
        this.bluetoothSetup();
        this.connectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!bMITP.this.bluetoothPort.isConnected()) {
                    try {
                        bMITP.this.btConn(bMITP.this.mBluetoothAdapter.getRemoteDevice(bMITP.this.btAddrBox.getText().toString()));
                    } catch (IllegalArgumentException var3) {
                        Log.e("BluetoothConnectMenu", var3.getMessage(), var3);
                        AlertView.showAlert(var3.getMessage(), bMITP.this.context);
                        return;
                    } catch (IOException var4) {
                        Log.e("BluetoothConnectMenu", var4.getMessage(), var4);
                        AlertView.showAlert(var4.getMessage(), bMITP.this.context);
                        return;
                    }
                } else {
                    bMITP.this.btDisconn();
                }

            }
        });
        this.searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!bMITP.this.mBluetoothAdapter.isDiscovering()) {
                    bMITP.this.clearBtDevData();
                    bMITP.this.adapter.clear();
                    bMITP.this.mBluetoothAdapter.startDiscovery();
                } else {
                    bMITP.this.mBluetoothAdapter.cancelDiscovery();
                }

            }
        });
        this.adapter = new ArrayAdapter(bMITP.this, R.layout.cci);

        this.list.setAdapter(this.adapter);
        this.addPairedDevices();
        this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                BluetoothDevice btDev = (BluetoothDevice) bMITP.this.remoteDevices.elementAt(arg2);

                try {
                    if (bMITP.this.mBluetoothAdapter.isDiscovering()) {
                        bMITP.this.mBluetoothAdapter.cancelDiscovery();
                    }

                    bMITP.this.btAddrBox.setText(btDev.getAddress());
                    bMITP.this.btConn(btDev);
                } catch (IOException var8) {
                    AlertView.showAlert(var8.getMessage(), bMITP.this.context);
                }
            }
        });
        this.discoveryResult = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                BluetoothDevice remoteDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (remoteDevice != null) {
                    String key;
                    if (remoteDevice.getBondState() != 12) {
                        key = remoteDevice.getName() + "\n[" + remoteDevice.getAddress() + "]";
                    } else {
                        key = remoteDevice.getName() + "\n[" + remoteDevice.getAddress() + "] [Paired]";
                    }

//                    if (bMITP.this.bluetoothPort.isValidAddress(remoteDevice.getAddress())) {
                    bMITP.this.remoteDevices.add(remoteDevice);
                    bMITP.this.adapter.add(key);
                }
//                }

            }
        };
        this.registerReceiver(this.discoveryResult, new IntentFilter("android.bluetooth.device.action.FOUND"));
        this.searchStart = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                bMITP.this.connectButton.setEnabled(false);
                bMITP.this.btAddrBox.setEnabled(false);
//                BluetoothConnectMenu.this.searchButton.setText(BluetoothConnectMenu.this.getResources().getString(2131034114));

                bMITP.this.searchButton.setText("stop ");
            }
        };
        this.registerReceiver(this.searchStart, new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_STARTED"));
        this.searchFinish = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                bMITP.this.connectButton.setEnabled(true);
                bMITP.this.btAddrBox.setEnabled(true);
//                BluetoothConnectMenu.this.searchButton.setText(BluetoothConnectMenu.this.getResources().getString(2131034113));
                bMITP.this.searchButton.setText("search");

            }
        };
        this.registerReceiver(this.searchFinish, new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED"));
        if (this.chkDisconnect.isChecked()) {
            this.disconnectReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    if (!"android.bluetooth.device.action.ACL_CONNECTED".equals(action) && "android.bluetooth.device.action.ACL_DISCONNECTED".equals(action)) {
                        bMITP.this.DialogReconnectionOption();
                    }

                }
            };
        }

    }

    protected void onDestroy() {
        try {
            if (this.bluetoothPort.isConnected() && this.chkDisconnect.isChecked()) {
                this.unregisterReceiver(this.disconnectReceiver);
            }

            this.saveSettingFile();
            this.bluetoothPort.disconnect();
        } catch (IOException var2) {
            Log.e("BluetoothConnectMenu", var2.getMessage(), var2);
        } catch (InterruptedException var3) {
            Log.e("BluetoothConnectMenu", var3.getMessage(), var3);
        }

        if (this.hThread != null && this.hThread.isAlive()) {
            this.hThread.interrupt();
            this.hThread = null;
        }

        this.unregisterReceiver(this.searchFinish);
        this.unregisterReceiver(this.searchStart);
        this.unregisterReceiver(this.discoveryResult);
        super.onDestroy();
    }

    private void DialogReconnectionOption() {
        String[] items = new String[]{"Bluetooth printer"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("connection ...");
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        }).setPositiveButton("connect", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                try {
                    bMITP.this.btDisconn();
                    bMITP.this.btConn(bMITP.this.mBluetoothAdapter.getRemoteDevice(bMITP.this.btAddrBox.getText().toString()));
                } catch (IllegalArgumentException var4) {
                    Log.e("BluetoothConnectMenu", var4.getMessage(), var4);
                    AlertView.showAlert(var4.getMessage(), bMITP.this.context);
                } catch (IOException var5) {
                    Log.e("BluetoothConnectMenu", var5.getMessage(), var5);
                    AlertView.showAlert(var5.getMessage(), bMITP.this.context);
                }
            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                bMITP.this.btDisconn();
            }
        });
        builder.show();
    }

    private void btConn(BluetoothDevice btDev) throws IOException {
        (new bMITP.connTask()).execute(new BluetoothDevice[]{btDev});
    }

    private void btDisconn() {
        try {
            this.bluetoothPort.disconnect();
            if (this.chkDisconnect.isChecked()) {
                this.unregisterReceiver(this.disconnectReceiver);
            }
        } catch (Exception var2) {
            Log.e("BluetoothConnectMenu", var2.getMessage(), var2);
        }

        if (this.hThread != null && this.hThread.isAlive()) {
            this.hThread.interrupt();
        }

        this.connectButton.setText("Connect");
        this.list.setEnabled(true);
        this.btAddrBox.setEnabled(true);
        this.searchButton.setEnabled(true);
       // Toast toast = Toast.makeText(this.context, "disconnect", Toast.LENGTH_SHORT);
      //  toast.show();
    }

    class connTask extends AsyncTask<BluetoothDevice, Void, Integer> {
        private final ProgressDialog dialog = new ProgressDialog(bMITP.this);

        connTask() {
        }

        protected void onPreExecute() {
            this.dialog.setTitle(" Try Connect ");
            this.dialog.setMessage("Please Wait ....");
            this.dialog.show();
            super.onPreExecute();
        }

        protected Integer doInBackground(BluetoothDevice... params) {
            Integer retVal = null;

            try {
                bMITP.this.bluetoothPort.connect(params[0]);
                bMITP.this.lastConnAddr = params[0].getAddress();
                retVal = 0;
            } catch (IOException var4) {
                Log.e("BluetoothConnectMenu", var4.getMessage());
                retVal = -1;
            }

            return retVal;
        }

        @SuppressLint("WrongThread")
        protected void onPostExecute(Integer result) {
            if (result == 0) {
                RequestHandler rh = new RequestHandler();
                bMITP.this.hThread = new Thread(rh);
                bMITP.this.hThread.start();
                bMITP.this.connectButton.setText("Connect");
                bMITP.this.connectButton.setEnabled(false);
                bMITP.this.list.setEnabled(false);
                bMITP.this.btAddrBox.setEnabled(false);
                bMITP.this.searchButton.setEnabled(false);
                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }

//                Toast toast = Toast.makeText(bMITP.this.context, "Now Printing ", Toast.LENGTH_SHORT);
//                toast.show();


                int count = Integer.parseInt(getData);


                switch (count) {

                    case 0:
                        try {

                            Bitmap bitmap = null;
                            if (Item.itemCardForPrint.getItemG().equals("0")) {
                                ESCPSample23 sample = new ESCPSample23(bMITP.this);
                                bitmap = convertLayoutToImage_shelfTag(Item.itemCardForPrint);
                                Log.e("Count = ", "" + Item.itemCardForPrint.getFDPRC());

                            } else if (Item.itemCardForPrint.getItemG().equals("1")) {
                                ESCPSample23 sample = new ESCPSample23(bMITP.this);
                                bitmap = convertLayoutToImage_shelfTag_Design2(Item.itemCardForPrint);
                                Log.e("Count = ", "" + Item.itemCardForPrint.getFDPRC());
                            } else {
                                ESCPSample23 sample = new ESCPSample23(bMITP.this);
                                bitmap = convertLayoutToImage_shelfTag_Design3(Item.itemCardForPrint);
                                Log.e("Count = ", "" + Item.itemCardForPrint.getFDPRC());
                            }
                            if (bitmap != null) {
                                ESCPSample23 sample = new ESCPSample23(bMITP.this);
                                Log.e("Count mm = ", "" + Item.itemCardForPrint.getCostPrc());
                                sample.imageTest1(Integer.parseInt(Item.itemCardForPrint.getCostPrc()), bitmap);
                                //mBluetoothAdapter.disable();
                          // sample.printMultilingualFontEsc(1);
                            } else {
//                            Toast.makeText(context, "CAN NOT PRINT", Toast.LENGTH_SHORT).show();
                            }

                            break;
                        } catch (Exception e) {

                        }

                        break;
                    case 1:

                        break;

                    case 2:


                        break;

                    case 3:

                        break;

                    case 4:

                        break;
                    case 5:


                        break;
                    case 6:


                        break;
                    case 7:// print last voucher


                        break;

                }


                finish();


                if (bMITP.this.chkDisconnect.isChecked()) {
                    bMITP.this.registerReceiver(bMITP.this.disconnectReceiver, new IntentFilter("android.bluetooth.device.action.ACL_CONNECTED"));
                    bMITP.this.registerReceiver(bMITP.this.disconnectReceiver, new IntentFilter("android.bluetooth.device.action.ACL_DISCONNECTED"));
                }
            } else {
                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }

                AlertView.showAlert("Disconnect Bluetoothُ", "Try Again ,,,.", bMITP.this.context);
            }

            super.onPostExecute(result);
        }
    }

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }


    @SuppressLint("SetTextI18n")
    private Bitmap convertLayoutToImage_shelfTag(ItemCard itemCard) {
        LinearLayout linearView = null;
        final Dialog dialog_Header = new Dialog(bMITP.this);
        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Header.setCancelable(false);
        dialog_Header.setContentView(R.layout.shlf_tag_dialog);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);

        TextView itemName,price,BarcodeText,exp ;

        LinearLayout ExpLiner,priceLiner;

        ExpLiner= (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
        priceLiner= (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);

        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
        price = (TextView) dialog_Header.findViewById(R.id.price);
        BarcodeText=(TextView) dialog_Header.findViewById(R.id.BarcodeText);
        exp=(TextView) dialog_Header.findViewById(R.id.exp);

        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);

        BarcodeText.setText(itemCard.getItemCode());
        itemName.setText(itemCard.getItemName());
        if(itemCard.getSalePrc().equals("**")){
            priceLiner.setVisibility(View.INVISIBLE);
        }else{
            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC())))+"JD");
        }

        if(itemCard.getDepartmentId().equals("**")){
            ExpLiner.setVisibility(View.INVISIBLE);
        }else{
            exp.setText(itemCard.getDepartmentId());
        }


        try {
            Bitmap bitmaps = encodeAsBitmap(itemCard.getItemCode(), BarcodeFormat.CODE_128, 300, 90);
            barcode.setImageBitmap(bitmaps);
        } catch (WriterException e) {
            e.printStackTrace();
        }


        linearView = (LinearLayout) dialog_Header.findViewById(R.id.shelfTagLiner);

        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//        dialog_Header.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();
        Bitmap bit =linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


    }
    private Bitmap convertLayoutToImage_shelfTag_Design2(ItemCard itemCard) {
        LinearLayout linearView = null;
        final Dialog dialog_Header = new Dialog(bMITP.this);
        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Header.setCancelable(false);
        dialog_Header.setContentView(R.layout.shlf_tag_dialog_design2);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);

        TextView itemName,price,BarcodeText,exp ;

        LinearLayout ExpLiner,priceLiner;

        ExpLiner= (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
        priceLiner= (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);

        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
        price = (TextView) dialog_Header.findViewById(R.id.price);
        BarcodeText=(TextView) dialog_Header.findViewById(R.id.BarcodeText);
        exp=(TextView) dialog_Header.findViewById(R.id.exp);

        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);

        BarcodeText.setText(itemCard.getItemCode());
        itemName.setText(itemCard.getItemName());
        if(itemCard.getSalePrc().equals("**")){
            priceLiner.setVisibility(View.INVISIBLE);
        }else{
            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC())))+"JD");
        }

        if(itemCard.getDepartmentId().equals("**")){
            ExpLiner.setVisibility(View.INVISIBLE);
        }else{
            exp.setText(itemCard.getDepartmentId());
        }


        try {
            Bitmap bitmaps = encodeAsBitmap(itemCard.getItemCode(), BarcodeFormat.CODE_128, 300, 90);
            barcode.setImageBitmap(bitmaps);
        } catch (WriterException e) {
            e.printStackTrace();
        }


        linearView = (LinearLayout) dialog_Header.findViewById(R.id.shelfTagLiner);

        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
//        dialog_Header.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();
        Bitmap bit =linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


    }
    private Bitmap convertLayoutToImage_shelfTag_Design3(ItemCard itemCard) {
        LinearLayout linearView = null;
        final Dialog dialog_Header = new Dialog(bMITP.this);
        dialog_Header.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Header.setCancelable(false);
        dialog_Header.setContentView(R.layout.shlf_tag_dialog_design3);
//        CompanyInfo companyInfo = obj.getAllCompanyInfo().get(0);

        TextView itemName,price,itemBar;//,BarcodeText;//,exp ;

        LinearLayout priceLiner;//ExpLiner,

//        ExpLiner= (LinearLayout) dialog_Header.findViewById(R.id.ExpLiner);
        priceLiner= (LinearLayout) dialog_Header.findViewById(R.id.priceLiner);

        itemName = (TextView) dialog_Header.findViewById(R.id.itemName);
        price = (TextView) dialog_Header.findViewById(R.id.price);
        itemBar= (TextView) dialog_Header.findViewById(R.id.itemBar);
//        BarcodeText=(TextView) dialog_Header.findViewById(R.id.BarcodeText);
//        exp=(TextView) dialog_Header.findViewById(R.id.exp);

        ImageView barcode = (ImageView) dialog_Header.findViewById(R.id.barcodeShelf);

//        BarcodeText.setText(itemCard.getItemCode());
        itemName.setText(itemCard.getItemName());
        itemBar.setText(""+itemCard.getItemCode());
        if(itemCard.getSalePrc().equals("**")){
            priceLiner.setVisibility(View.INVISIBLE);
        }else{
            price.setText(convertToEnglish(numberFormat.format(Double.parseDouble(itemCard.getFDPRC())))+"JD");
        }

//        if(itemCard.getDepartmentId().equals("**")){
//            ExpLiner.setVisibility(View.INVISIBLE);
//        }else{
//            exp.setText(itemCard.getDepartmentId());
//        }


        try {
            Bitmap bitmaps = encodeAsBitmap(itemCard.getItemCode(), BarcodeFormat.CODE_128, 300, 40);
            barcode.setImageBitmap(bitmaps);
        } catch (WriterException e) {
            e.printStackTrace();
        }


        linearView = (LinearLayout) dialog_Header.findViewById(R.id.shelfTagLiner);

        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(1, 1, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        Log.e("size of img ", "width=" + linearView.getMeasuredWidth() + "      higth =" + linearView.getHeight());
        dialog_Header.show();
        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();
        Bitmap bit =linearView.getDrawingCache();

        return bit;// creates bitmap and returns the same


    }


    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }


}
