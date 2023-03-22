package com.example.user54.InventoryApp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.service.controls.Control;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.user54.InventoryApp.Model.AssestItem;
import com.example.user54.InventoryApp.Model.ItemCard;
import com.example.user54.InventoryApp.Model.ItemInfo;
import com.example.user54.InventoryApp.Model.ItemQR;
import com.example.user54.InventoryApp.Model.ItemQty;
import com.example.user54.InventoryApp.Model.ItemSwitch;
import com.example.user54.InventoryApp.Model.ItemUnit;
import com.example.user54.InventoryApp.Model.MainSetting;
import com.example.user54.InventoryApp.Model.OnlineItems;
import com.example.user54.InventoryApp.Model.Stk;
import com.example.user54.InventoryApp.Model.UnitName;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.user54.InventoryApp.CollectingData.textItemNameUpdate;
import static com.example.user54.InventoryApp.CollectingData.textViewUpdate;
import static com.example.user54.InventoryApp.Item.listItemUnitQty;
import static com.example.user54.InventoryApp.Item.texetrespone;
import static com.example.user54.InventoryApp.Item.textItemName;
import static com.example.user54.InventoryApp.Item.textQty;
import static com.example.user54.InventoryApp.Item.textView;
import static com.example.user54.InventoryApp.Item.textViewFd;

public class importJson {

    private Context context;
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialogSave;
    private JSONObject obj;
    InventoryDatabase dbHandler;
    String itemCode;
    String JsonResponseSave;
    String JsonResponseAssetsSave;
    String JsonResponseSaveUnite;
    String JsonResponseSaveQRCode;
    String JsonResponseSaveSwitch;
    public static ArrayList<OnlineItems> onlineItems = new ArrayList<>();
    SweetAlertDialog pd = null,pdRepla4;
    controll co;
          String isAssetsIn,ip,QrUse,onlinePrice,CompanyNo;
          String fromDate,ToDate;
          int coName=0;


    public importJson(Context context,String itemCodes,int is,String fromDate,String toDate) {//, JSONObject obj
//        this.obj = obj;
        this.context = context;
        co=new controll();

        controll co=new controll();
        int data= Integer.parseInt(co.readFromFile(context));
        dbHandler = new InventoryDatabase(context,data);
        this.itemCode=itemCodes;
        this.fromDate=fromDate;
        this.ToDate=toDate;
        Log.e("DateFromTo","tt = "+fromDate+"  "+toDate);
        if(is!=0) {
            try {

                pd = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
                pd.setTitleText(context.getResources().getString(R.string.importData));
                pd.setCancelable(false);
                pd.show();
            }catch (Exception e){

            }
        }
//        progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);

    }

    public void startSending(String flag) {
//        Log.e("check",flag);
        final List<MainSetting> mainSettings = dbHandler.getAllMainSetting();
        if (mainSettings.size() != 0) {
            this.ip = mainSettings.get(0).getIP();
            this.isAssetsIn = mainSettings.get(0).getIsAssest();
            this.QrUse = mainSettings.get(0).getIsQr();
            this.onlinePrice=mainSettings.get(0).getOnlinePrice();
            this.CompanyNo=mainSettings.get(0).getCompanyNo();
            this.coName=mainSettings.get(0).getCoName();
        }

        if (flag.equals("ItemCard"))
            new SyncItemCard().execute();

        if (flag.equals("ItemSwitch"))
            new SyncItemSwitch().execute();

        if (flag.equals("ItemPrice"))
            new SyncItemPrice().execute();
        if (flag.equals("GetStory"))
            new SyncGetStor().execute();

        if (flag.equals("GetAssest")){

            new SyncGetAssest().execute();}

        if (flag.equals("itemUnite")){

            new SyncItemUnite().execute();}

        if (flag.equals("gETiTEM")){
            new SyncGetItem().execute();
        }

        if (flag.equals("SyncItemQR")){
            new SyncItemQR().execute();
        }

        if (flag.equals("ItemCost"))
            new SyncItemCoastPrice().execute();


    }

    private class SyncItemCard extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {

//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();
//            pd = ProgressDialog.show(context, "title", "loading", true);
            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pd.setTitleText(context.getResources().getString(R.string.importitemcard));


            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {///GetModifer?compno=736&compyear=2019
            try {
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip= mainSettings.get(0).getIP();
//                }

//
                String link = "http://"+ip + "/GetJRDITEMS";
                // ITEM_CARD
                String max=dbHandler.getMaxInDate("ITEM_CARD");

                String maxInDate="";
                if(max.equals("-1")) {
                    maxInDate="05/03/1991";
                }else{
                    maxInDate=max.substring(0,10);
                    String date[]=maxInDate.split("-");
                    maxInDate=date[2]+"/"+date[1]+"/"+date[0];
                    Log.e("split ",""+maxInDate);
                }
//               String data = "MAXDATE=" + URLEncoder.encode(maxInDate, "UTF-8") + "&" +
//                             "CONO="+URLEncoder.encode(CompanyNo, "UTF-8");

                String data = "FROM_DATE=" + URLEncoder.encode(fromDate, "UTF-8") + "&" +
                        "TO_DATE=" + URLEncoder.encode(ToDate, "UTF-8")   + "&" +
                             "CONO="+URLEncoder.encode(CompanyNo, "UTF-8");

////

                URL url = new URL(link);
                Log.e("urlStringCard = ",""+url.toString()+"   "+data);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");



                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

               // Log.e("tag", "ItemOCode -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

//            try {
//            if(coName==1) {
//                JsonResponse = co.readFromAssest(context);
//            }else{
//                Log.e("jsonResponse1", "****co name not 0");
//            }
//            }catch (Exception e){
//                Log.e("jsonResponse", "****Error");
//
//            }
            if (JsonResponse != null && JsonResponse.contains("ItemOCode")) {
                Log.e("tag_ItemOCode", "****Success");
//                progressDialog.dismiss();

//                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                        .setTitleText(context.getResources().getString(R.string.ops))
//                        .setContentText("in save 242")
//                        .show();

                JsonResponseSave=JsonResponse;
                 new SaveItemCard().execute();
//                try {
//
//                    JSONArray parentArray = new JSONArray(JsonResponse);
//
//
//                    List<ItemCard> itemCard=new ArrayList<>();
//
//                    for (int i = 0; i < parentArray.length(); i++) {
//                        JSONObject finalObject = parentArray.getJSONObject(i);
//// "ItemOCode": "0829274512282",
////    "ItemNameA": "MEOW MIX CUPS PATE WHITE FISH & SALMON 78G*12",
////    "ItemNameE": "MEOW MIX CUPS PATE WHITE FISH & SALMON 78G*12",
////    "ItemG": "Grocery-(بقالة)",
////    "TAXPERC": "16",
////    "SalePrice": "0",
////    "LLCPrice": "0.73",
////    "AVLQTY": "0",
////    "F_D": "1",
////    "ItemK": "مستلزمات الحيوانات",
////    "ItemL": "",
////    "ITEMDIV": "",
////    "ITEMGS": ""
//
//
//
//                        ItemCard obj = new ItemCard();
//                        obj.setItemCode(finalObject.getString("ItemOCode"));
//                        obj.setItemName(finalObject.getString("ItemNameA"));
////                        obj.setit(finalObject.getString("ItemNameE"));
//                        obj.setItemG(finalObject.getString("ItemG"));
////                        obj.set(finalObject.getString("TAXPERC"));
//                        obj.setSalePrc(finalObject.getString("SalePrice"));
////
//
////                        obj.set(finalObject.getString("LLCPrice"));
//                        obj.setAVLQty(finalObject.getString("AVLQTY"));
//                        obj.setFDPRC(finalObject.getString("F_D"));
//
//                        obj.setItemK(finalObject.getString("ItemK"));
//                        obj.setItemL(finalObject.getString("ItemL"));
//                        obj.setItemDiv(finalObject.getString("ITEMDIV"));
//
//                        obj.setItemGs(finalObject.getString("ITEMGS"));
//
//
//                        itemCard.add(obj);
//
//                    }
//
////
//                    dbHandler.deleteAllItem("ITEM_CARD");
//                    for (int i = 0; i < itemCard.size(); i++) {
//                        dbHandler.addItemcardTable(itemCard.get(i));
//                    }
//
//Log.e("tag_itemCard", "****saveSuccess");
////                    intentControl.setText("@");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

            }else if (JsonResponse != null && JsonResponse.contains("No Data Found.")){
                new SyncItemSwitch().execute();
            }
            else {
                Log.e("tag_itemCard", "****Failed to export data");
//                Toast.makeText(context, "Failed to Get data", Toast.LENGTH_SHORT).show();
                if(pd!=null) {
                    pd.dismiss();
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(context.getResources().getString(R.string.ops))
                            .setContentText(context.getResources().getString(R.string.fildtoimportitemswitch))
                            .show();
                }


            }

        }
    }
    private class SyncItemCard333 extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
       String ITEMCODE;

        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        public SyncItemCard333(String ITEMCODE) {
            this.ITEMCODE = ITEMCODE;
        }

        @Override
        protected void onPreExecute() {




            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {///GetModifer?compno=736&compyear=2019
            try {
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip= mainSettings.get(0).getIP();
//                }

//
                String link = "http://"+ip + "/GetDirectItems";
                // ITEM_CARD





                String data = "ITEMCODE=" + URLEncoder.encode(ITEMCODE.toString().trim()+"", "UTF-8") + "&" +
                        "CONO="+URLEncoder.encode(CompanyNo, "UTF-8");

////

                URL url = new URL(link);
                Log.e("urlStringCard = ",""+url.toString()+"   "+data);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");



                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                // Log.e("tag", "ItemOCode -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                pdRepla4.dismiss();
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        pdRepla4.dismiss();
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String array) {
            super.onPostExecute(array);


            pdRepla4.dismiss();
            if (array != null && array.contains("ItemOCode")) {

Log.e("ItemOCode==","ItemOCode");
                if (array.length() != 0) {
                    try {
                        Log.e("here====","here");
                        JSONArray requestArray = null;
                        requestArray = new JSONArray(array);

                        JSONObject jsonObject1 = null;
                        for (int i = 0; i < requestArray.length(); i++) {
                            Log.e("here2====","here");
                            jsonObject1 = requestArray.getJSONObject(i);
                            OnlineItems item = new OnlineItems();
                            item.setItemOCode(jsonObject1.getString("ItemOCode"));
                            item.setItemNameA(jsonObject1.getString("ItemNameA"));
                            item.setSalePrice(jsonObject1.getString("SalePrice"));
                            item.setF_D(jsonObject1.getString("F_D"));
                            item.setITEMU(jsonObject1.getString("ITEMU"));
                            item.setInDate(jsonObject1.getString("InDate"));
                            Log.e("item====","item"+item.getItemOCode());
                            onlineItems.add(item);
                            texetrespone.setText("ItemOCode");

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            }else if (JsonResponse != null && JsonResponse.contains("No Data Found.")){
                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();


            }

        }
    }
    private class SyncItemSwitch extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pd.setTitleText(context.getResources().getString(R.string.importitemswitch));

        }

        @Override
        protected String doInBackground(String... params) {
            try {


//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip=mainSettings.get(0).getIP();
//                }
                String link = "http://"+ip + "/GetJRDITEMSWICH";

                // ITEM_CARD
                String max=dbHandler.getMaxInDate("ITEM_SWITCH");
                String maxInDate="";
                if(max.equals("-1")) {
                    maxInDate="05/03/2020";
                }else{
                    maxInDate=max.substring(0,10);
                    String date[]=maxInDate.split("-");
                    maxInDate=date[2]+"/"+date[1]+"/"+date[0];
                    Log.e("splitSwitch ",""+maxInDate);
                }
//                String data = "MAXDATE=" + URLEncoder.encode(maxInDate, "UTF-8") + "&" +
//                        "CONO="+URLEncoder.encode(CompanyNo, "UTF-8");

                String data = "FROM_DATE=" + URLEncoder.encode(fromDate, "UTF-8") + "&" +
                        "TO_DATE=" + URLEncoder.encode(ToDate, "UTF-8")  + "&" +
                        "CONO="+URLEncoder.encode(CompanyNo, "UTF-8");
////
                URL url = new URL(link);


                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();
                Log.e("url____",""+link+data);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_itemSwitch -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

//            try {
//                if(coName==1) {
//                    JsonResponse = co.readFromSwitchAssest(context);
//                }else{
//                    Log.e("jsonResponse1", "****co name not 0");
//                }
//            }catch (Exception e){
//                Log.e("jsonResponse", "****Error");
//
//            }

            if (JsonResponse != null && JsonResponse.contains("ItemOCode")) {
                Log.e("TAG_itemSwitch", "****Success");

                JsonResponseSaveSwitch=JsonResponse;
                new SaveItemSwitch().execute();

            }
            else if (JsonResponse != null && JsonResponse.contains("No Data Found.")){
               if(QrUse.equals("1")) {
                   new SyncItemQR().execute();
               }else{
                   new SyncItemUnite().execute();
               }
//                new SyncItemUnite().execute();

            }else {
                Log.e("TAG_itemSwitch", "****Failed to export data");
//                progressDialog.dismiss();
                if(pd!=null) {
                    pd.dismiss();
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(context.getResources().getString(R.string.ops))
                            .setContentText(context.getResources().getString(R.string.faildtoimport))
                            .show();
                }
            }

        }
    }

    private class SyncItemQR extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pd.setTitleText(context.getResources().getString(R.string.importItemQR));

        }

        @Override
        protected String doInBackground(String... params) {
            try {


//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip=mainSettings.get(0).getIP();
//                }
                String link = "http://"+ip + "/GetEXPIRYVIEW";

                // ITEM_CARD
                String max=dbHandler.getMaxInDate("ITEM_SWITCH");
                String maxInDate="";
                if(max.equals("-1")) {
                    maxInDate="05/03/2020";
                }else{
                    maxInDate=max.substring(0,10);
                    String date[]=maxInDate.split("-");
                    maxInDate=date[2]+"/"+date[1]+"/"+date[0];
                    Log.e("splitSwitch ",""+maxInDate);
                }
//                String data = "MAXDATE=" + URLEncoder.encode(maxInDate, "UTF-8");
////
                URL url = new URL(link);


                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

//                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                wr.writeBytes(data);
//                wr.flush();
//                wr.close();
//                Log.e("url____",""+link+data);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();


                    while ((JsonResponse = bufferedReader.readLine()) != null) {
                        try {
                        stringBuffer.append(JsonResponse + "\n");
                    }catch (Exception ex){
                        Log.e("QRJsonResponse","Long JsonResponse "+ex.toString());
                    }
                    }


                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_itemQR -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

            if (JsonResponse != null && JsonResponse.contains("STRNO")) {
                Log.e("TAG_item_qr", "****Success");

                JsonResponseSaveQRCode=JsonResponse;
                new SaveItemQr().execute();

            }
            else if (JsonResponse != null && JsonResponse.contains("No Data Found.")){
                new SyncItemUnite().execute();
            }else {
                Log.e("TAG_itemSwitch", "****Failed to export data");
//                progressDialog.dismiss();
                if(pd!=null) {
                    pd.dismiss();
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(context.getResources().getString(R.string.ops))
                            .setContentText(context.getResources().getString(R.string.falidedimportItemQR))
                            .show();
                }
            }

        }
    }


    private class SyncItemUnite extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pd.setTitleText("import item Unit");

        }

        @Override
        protected String doInBackground(String... params) {
            try {


//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip=mainSettings.get(0).getIP();
//                }
                String link = "http://"+ip + "/GetJRDITEMUNIT";

                // ITEM_CARD
                String max=dbHandler.getMaxInDate("ITEM_UNITS");
                String maxInDate="";
                if(max.equals("-1")) {
                    maxInDate="05/03/2020";
                }else{
                    maxInDate=max.substring(0,10);
                    String date[]=maxInDate.split("-");
                    maxInDate=date[2]+"/"+date[1]+"/"+date[0];
                    Log.e("splitSwitch ",""+maxInDate);
                }
//                String data = "MAXDATE=" + URLEncoder.encode(maxInDate, "UTF-8") + "&" +
//                        "CONO="+URLEncoder.encode(CompanyNo, "UTF-8");

                String data = "FROM_DATE=" + URLEncoder.encode(fromDate, "UTF-8")+ "&" +
                        "TO_DATE=" + URLEncoder.encode(ToDate, "UTF-8")  + "&" +
                        "CONO="+URLEncoder.encode(CompanyNo, "UTF-8");
////
                URL url = new URL(link);


                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();
                Log.e("url____",""+link+data);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_itemSwitch -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

//            try {
//                if(coName==1) {
//                    JsonResponse = co.readFromUnitAssest(context);
//                }else{
//                    Log.e("jsonResponse1", "****co name not 0");
//                }
//            }catch (Exception e){
//                Log.e("jsonResponse", "****Error");
//
//            }

            if (JsonResponse != null && JsonResponse.contains("ITEMOCODE")) {
                Log.e("TAG_itemUnite", "****Success");

                JsonResponseSaveUnite=JsonResponse;
                new SaveItemUnite().execute();

            }
            else if (JsonResponse != null && JsonResponse.contains("No Data Found.")){
                new SyncGetStor().execute();
            }else {
                Log.e("TAG_itemSwitch", "****Failed to export data");
//                progressDialog.dismiss();
                if(pd!=null) {
                    pd.dismiss();
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(context.getResources().getString(R.string.ops))
                            .setContentText("Failed to import Item Unit")
                            .show();
                }
            }

        }
    }

    private class SyncItemPrice extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        SweetAlertDialog pdItem=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
             pdItem = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            pdItem.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pdItem.setTitleText(context.getResources().getString(R.string.itemprice));
            pdItem.setCancelable(false);
            pdItem.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
                String ip="";
                if(mainSettings.size()!=0) {
                    ip= mainSettings.get(0).getIP();
                }
                String link = "http://"+ip + "/GetJRDITEMPRICE";
//                String link = controll.URL + "GetJRDITEMPRICE";
//
                String data = "ITEMCODE=" + URLEncoder.encode(itemCode, "UTF-8")  + "&" +
                        "CONO="+URLEncoder.encode(CompanyNo, "UTF-8");

//
                URL url = new URL(link);
                Log.e("TAG_itemPrice", "link -->" +link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_itemPrice -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

            if (JsonResponse != null && JsonResponse.contains("F_D")) {
                Log.e("TAG_itemPrice", "****Success");

                try {

                    JSONArray parentArray = new JSONArray(JsonResponse);

                    Log.e("TAG_itemPrice", " "+parentArray.toString());
                    Log.e("TAG_itemPriceR", " "+JsonResponse);
                    for (int i = 0; i < parentArray.length(); i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        controll.F_D= finalObject.getString("F_D");
                        controll.Item_name= finalObject.getString("ITEMNAMEA");
                        controll.qty_name= finalObject.getString("QTY");
//                        textView.setText(controll.F_D);
                        if(textQty!=null)  textQty.setText(controll.qty_name);
                        if(textViewFd!=null) textViewFd.setText(controll.F_D);
                        if(textItemName!=null)textItemName.setText(controll.Item_name);
//                                Log.e("TAG_itemPrice", "****getSuccess"+controll.F_D+"name= "+ controll.Item_name);

                    }
                  new   SyncItemPrice_Unit().execute();
                    if(pdItem !=null) {

                        pdItem.dismissWithAnimation();
//                        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
//                                .setTitleText(context.getResources().getString(R.string.save_SUCCESS))
//                                .setContentText(context.getResources().getString(R.string.importSuc))
//                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    } else if(JsonResponse != null && JsonResponse.contains("No Parameter Found.")){
                Log.e("TAG_itemPrice", "****No Parameter Found.");

                if(pdItem !=null) {
                    pdItem.dismissWithAnimation();
                }

                textView.setText("*");



            } else {
                Log.e("TAG_itemPrice", "****Failed to export data");

                if(pdItem !=null) {
                    pdItem.dismissWithAnimation();
                }

                textView.setText("-1");
            }
        }
    }
    private class SyncItemPrice_Unit extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        SweetAlertDialog pdItem=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdItem = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            pdItem.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pdItem.setTitleText(context.getResources().getString(R.string.qty));
            pdItem.setCancelable(false);
            pdItem.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
                String ip="";
                if(mainSettings.size()!=0) {
                    ip= mainSettings.get(0).getIP();
                }
                String link = "http://"+ip + "/GetUnitDTL";
//                String link = controll.URL + "GetJRDITEMPRICE";
//
                String data = "ITEMCODE=" + URLEncoder.encode(itemCode, "UTF-8")  + "&" +
                        "CONO="+URLEncoder.encode(CompanyNo, "UTF-8");

//
                URL url = new URL(link);
                Log.e("TAG_itemPrice", "link -->" +link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_itemPrice -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

            if (JsonResponse != null && JsonResponse.contains("F_D")) {
                Log.e("TAG_itemPrice", "****Success");

                try {
                    listItemUnitQty.clear();

                    JSONArray parentArray = new JSONArray(JsonResponse);
                    for (int i = 0; i < parentArray.length(); i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        ItemQty item=new ItemQty();

                        item.setF_d( finalObject.getString("F_D"));
                        item.setItemName(finalObject.getString("ITEMNAMEA"));
                        item.setQty(finalObject.getString("QTY"));
                        item.setItemCode(finalObject.getString("ITEMBARCODE"));
                     listItemUnitQty.add(item);
                        Log.e("TAG_itemPrice", "****getSuccess name= "+listItemUnitQty.size());

                    }
                    textView.setText(controll.F_D);
                    Log.e("aya", "****textView name ");
if(textViewFd!=null)                    textViewFd.setText(controll.F_D);
                    if(pdItem !=null) {
                        pdItem.dismissWithAnimation();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if(JsonResponse != null && JsonResponse.contains("No Parameter Found.")){
                Log.e("TAG_itemPrice22", "****No Parameter Found.");
                listItemUnitQty.clear();
                Log.e("TAG_itemPrice23", "****No Parameter Found.");
                textView.setText(controll.F_D);
                Log.e("TAG_itemPrice24", "****No Parameter Found.");
                if(pdItem !=null) {
                    pdItem.dismissWithAnimation();
                }
//                textView.setText("*");
            } else {
                Log.e("TAG_itemPrice", "****Failed to export data");

                if(pdItem !=null) {
                    pdItem.dismissWithAnimation();
                }

//                textView.setText("-1");
            }
        }
    }

    private class SyncItemCoastPrice extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        SweetAlertDialog pdItem=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

            pdItem = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            pdItem.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pdItem.setTitleText(context.getResources().getString(R.string.itemCost));
            pdItem.setCancelable(false);
            pdItem.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
                String ip="";
                if(mainSettings.size()!=0) {
                    ip= mainSettings.get(0).getIP();
                }
                String link = "http://"+ip + "/GetJRDItemCostPrice";
//                String link = controll.URL + "GetJRDITEMPRICE";
//
                String data = "ITEMCODE=" + URLEncoder.encode(itemCode, "UTF-8")  + "&" +
                        "CONO="+URLEncoder.encode(CompanyNo, "UTF-8");

//
                URL url = new URL(link);
                Log.e("TAG_itemPrice", "link -->" +link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_itemPrice -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

            if (JsonResponse != null && JsonResponse.contains("F_D")) {
                Log.e("TAG_itemPrice", "****Success");

                try {

                    JSONArray parentArray = new JSONArray(JsonResponse);

                    Log.e("TAG_itemPrice", " "+parentArray.toString());
                    Log.e("TAG_itemPriceR", " "+JsonResponse);
                    for (int i = 0; i < parentArray.length(); i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);


                        controll.F_D= finalObject.getString("F_D");
                        controll.Item_name= finalObject.getString("ITEMNAMEA");
                        textView.setText(controll.F_D);
                        textItemName.setText(controll.Item_name);
                        Log.e("TAG_itemCOST", "****getSuccess"+controll.F_D+"name= "+ controll.Item_name);

                    }

                    if(pdItem !=null) {
                        pdItem.dismissWithAnimation();
//                        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
//                                .setTitleText(context.getResources().getString(R.string.save_SUCCESS))
//                                .setContentText(context.getResources().getString(R.string.importSuc))
//                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if(JsonResponse != null && JsonResponse.contains("No Parameter Found.")){
                Log.e("TAG_itemPrice", "****No Parameter Found.");

                if(pdItem !=null) {
                    pdItem.dismissWithAnimation();
                }

                textView.setText("*");



            } else {
                Log.e("TAG_itemPrice", "****Failed to export data");

                if(pdItem !=null) {
                    pdItem.dismissWithAnimation();
                }

                textView.setText("-1");
            }
//            progressDialog.dismiss();
        }
    }

    private class SyncGetItem extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        SweetAlertDialog pdItem=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

            pdItem = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            pdItem.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pdItem.setTitleText("Item  ");
            pdItem.setCancelable(false);
            pdItem.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
                String ip="",STORE="";
                if(mainSettings.size()!=0) {
                    ip= mainSettings.get(0).getIP();
                    STORE=mainSettings.get(0).getStorNo();
                }
                String link = "http://"+ip + "/GetItemInfo";
//                String link = controll.URL + "GetJRDITEMPRICE";
//
                String data = "ITEMCODE=" + URLEncoder.encode(itemCode, "UTF-8")+"&"
                        +"STORENO=" + URLEncoder.encode(STORE, "UTF-8")  + "&" +
                        "CONO="+URLEncoder.encode(CompanyNo, "UTF-8");

//
                URL url = new URL(link);
                Log.e("TAG_itemPrice", "link -->" +link+"     -->"+data);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_itemPrice -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

            if (JsonResponse != null && JsonResponse.contains("ItemNameA")) {
                Log.e("TAG_itemPrice", "****Success");

                try {

                    JSONArray parentArray = new JSONArray(JsonResponse);

                    Log.e("TAG_itemPrice", " "+parentArray.toString());
                    Log.e("TAG_itemPriceR", " "+JsonResponse);
                    for (int i = 0; i < parentArray.length(); i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);


                        String itemName= finalObject.getString("ItemNameA");
                        String qty= finalObject.getString("REALQTY");
                        textViewUpdate.setText(qty);
                        textItemNameUpdate.setText(itemName);
                        Log.e("TAG_itemPrice", "****getSuccess"+qty+"name= "+ itemName);

                    }

                    if(pdItem !=null) {
                        pdItem.dismissWithAnimation();
//                        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
//                                .setTitleText(context.getResources().getString(R.string.save_SUCCESS))
//                                .setContentText(context.getResources().getString(R.string.importSuc))
//                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if(JsonResponse != null && JsonResponse.contains("No Parameter Found.")){
                Log.e("TAG_itemPrice", "****No Parameter Found.");

                if(pdItem !=null) {
                    pdItem.dismissWithAnimation();
                }

                textViewUpdate.setText("*");



            } else {
                Log.e("TAG_itemPrice", "****Failed to export data");

                if(pdItem !=null) {
                    pdItem.dismissWithAnimation();
                }

                textViewUpdate.setText("-1");
            }
//            progressDialog.dismiss();
        }
    }

    private class SyncGetStor extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pd.setTitleText(context.getResources().getString(R.string.importstor));

        }

        @Override
        protected String doInBackground(String... params) {
            try {

//
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip=mainSettings.get(0).getIP();
//                }
                String link = "http://"+ip + "/GetSore";

                //
                String data = "CONO=" + URLEncoder.encode(CompanyNo, "UTF-8") ;//+ "&" +
                      //  "compyear=" + URLEncoder.encode("2019", "UTF-8") ;
////
                URL url = new URL(link);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_GetStor -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);


//            try {
//                if(coName==1) {
//                    JsonResponse = co.readFromStoreAssest(context);
//                }else{
//                    Log.e("jsonResponse1", "****co name not 0");
//                }
//            }catch (Exception e){
//                Log.e("jsonResponse", "****Error");
//
//            }

            if (JsonResponse != null && JsonResponse.contains("STORENO")) {
                Log.e("TAG_GetStor", "****Success");

                pd.getProgressHelper().setBarColor(Color.parseColor("#1F6381"));
                pd.setTitleText(context.getResources().getString(R.string.storesave));
                try {

                    JSONArray parentArray = new JSONArray(JsonResponse);


                    List<Stk> stks=new ArrayList<>();

                    for (int i = 0; i < parentArray.length(); i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);

                        Stk obj = new Stk();

                        obj.setStkNo(finalObject.getString("STORENO"));
                        obj.setStkName(finalObject.getString("STORENAME"));


                        stks.add(obj);

                    }

//
                    dbHandler.deleteAllItem("STK");
                    for (int i = 0; i < stks.size(); i++) {
                        dbHandler.addStory(stks.get(i));
                    }

                    Log.e("TAG_GetStor", "****SaveSuccess");
                    pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pd.setTitleText(context.getResources().getString(R.string.storeSave));


//                       if(!isAssetsIn.equals("1")) {
//                           if(pd!=null) {
//                           pd.dismiss();
//
//                           new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
//                                   .setTitleText(context.getResources().getString(R.string.save_SUCCESS))
//                                   .setContentText(context.getResources().getString(R.string.importSuc))
//                                   .show();
//                       }
//
//                    }else{
//                        pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                        pd.setTitleText(context.getResources().getString(R.string.storeSave));
//                        new SyncGetAssest().execute();
//                    }

                    new SyncGetItemUnitsU().execute();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("TAG_GetStor", "****Failed to export data");
//                if(!isAssetsIn.equals("1")) {
                if (pd != null) {
                    pd.dismiss();
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(context.getResources().getString(R.string.ops))
                            .setContentText(context.getResources().getString(R.string.faildstore))
                            .show();
                    new SyncGetItemUnitsU().execute();
                }
//            }else{
//                    pd.dismiss();
//                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                            .setTitleText(context.getResources().getString(R.string.ops))
//                            .setContentText(context.getResources().getString(R.string.faildstore))
//                            .show();
//                    new SyncGetAssest().execute();
//                }
            }
//            progressDialog.dismiss();

        }
    }

    private class SyncGetItemUnitsU extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pd.setTitleText(context.getResources().getString(R.string.importstor));

        }

        @Override
        protected String doInBackground(String... params) {
            try {

//
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip=mainSettings.get(0).getIP();
//                }
                String link = "http://"+ip + "/GetItemUnit";

                //
                String data = "CONO=" + URLEncoder.encode(CompanyNo, "UTF-8") ;//+ "&" +
                //  "compyear=" + URLEncoder.encode("2019", "UTF-8") ;
////
                URL url = new URL(link);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_GetStor -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

            if (JsonResponse != null && JsonResponse.contains("ITEMU")) {
                Log.e("TAG_GetStor", "****Success");

                pd.getProgressHelper().setBarColor(Color.parseColor("#1F6381"));
                pd.setTitleText(context.getResources().getString(R.string.unitSave));
                try {

                    JSONArray parentArray = new JSONArray(JsonResponse);


                    List<UnitName> ITEM=new ArrayList<>();

                    for (int i = 0; i < parentArray.length(); i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);

                        UnitName obj = new UnitName();

                        obj.setItemUnitN(finalObject.getString("ITEMU"));



                        ITEM.add(obj);

                    }

//
                    dbHandler.deleteAllItem("ITEM_UNIT");
                    for (int i = 0; i < ITEM.size(); i++) {
                        dbHandler.addItemU(ITEM.get(i));
                    }

                    Log.e("TAG_GetStor", "****SaveSuccess");
                    pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pd.setTitleText(context.getResources().getString(R.string.storeSave));


                    if(!isAssetsIn.equals("1")) {
                        if(pd!=null) {
                            pd.dismiss();

                            new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText(context.getResources().getString(R.string.save_SUCCESS))
                                    .setContentText(context.getResources().getString(R.string.importSuc))
                                    .show();
                        }

                    }else{
                        pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pd.setTitleText(context.getResources().getString(R.string.unitSave));
                        new SyncGetAssest().execute();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("TAG_GetStor", "****Failed to export data");
                if (!JsonResponse.contains("<title>Title of the document</title>")) {
                    if (!isAssetsIn.equals("1")) {
                        if (pd != null) {
                            pd.dismiss();
                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(context.getResources().getString(R.string.ops))
                                    .setContentText(context.getResources().getString(R.string.faildUn))
                                    .show();
                        }
                    } else {
                        pd.dismiss();
                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(context.getResources().getString(R.string.ops))
                                .setContentText(context.getResources().getString(R.string.faildUn))
                                .show();
                        new SyncGetAssest().execute();
                    }
                }else{
                    Toast.makeText(context, "no Parameter", Toast.LENGTH_SHORT).show();
                    if (!isAssetsIn.equals("1")) {
                        if (pd != null) {
                            pd.dismiss();
                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(context.getResources().getString(R.string.ops))
                                    .setContentText(context.getResources().getString(R.string.faildU))
                                    .show();
                        }
                    } else {
                        pd.dismiss();
                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(context.getResources().getString(R.string.ops))
                                .setContentText(context.getResources().getString(R.string.faildU))
                                .show();
                        new SyncGetAssest().execute();
                    }
                }
                }

//            progressDialog.dismiss();

        }
    }


    private class SyncGetAssest extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {

//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();
//            pd = ProgressDialog.show(context, "title", "loading", true);
            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pd.setTitleText(context.getResources().getString(R.string.importassets));


            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {///GetModifer?compno=736&compyear=2019
            try {
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip= mainSettings.get(0).getIP();
//                }

//
                String link = "http://"+ip + "/GETASSETS";


                URL url = new URL(link);
                Log.e("urlStringGETASSEST = ",""+url.toString());

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");


//
//                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                wr.writeBytes(data);
//                wr.flush();
//                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "GETASSEST -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

           // JsonResponse="[{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10166\",\"NAME\":\"LAPTOP HP NOTEBOOK 450\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10167\",\"NAME\":\"LAPTOP HP NOTEBOOK 450\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10168\",\"NAME\":\"LAPTOP HP NOTEBOOK 450\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10169\",\"NAME\":\"2TB HDD DELL SERVER\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10170\",\"NAME\":\"LENOVO THINK CENTER E73I3 PC\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10171\",\"NAME\":\"PRINTER HP LASERJET P1102W\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10172\",\"NAME\":\"LAPTOP DELL LATITUDE  E7240\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10173\",\"NAME\":\"RACK SERVER HP PROLIANT DL 380 GEN 8\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10174\",\"NAME\":\"RACK SERVER HP PROLIANT DL380 GEN 9  64G SVR\\/GO\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10175\",\"NAME\":\"RACK SERVER HP PROLIANT DL380 GEN 9  128G SVR\\/GO\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10176\",\"NAME\":\"DESKTOP HP PRO DESK 400 G3 MT\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10177\",\"NAME\":\"DESKTOP HP PRO DESK 400 G3 MT\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10178\",\"NAME\":\"DESKTOP HP PRO DESK 400 G3 MT\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10179\",\"NAME\":\"LAPTOP HP NOTEBOOK 450\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10180\",\"NAME\":\"LAPTOP HP NOTEBOOK 450\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10181\",\"NAME\":\"LAPTOP HP NOTEBOOK 450\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10182\",\"NAME\":\"LAPTOP HP NOTEBOOK 450\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10183\",\"NAME\":\"CISCO SG 300-28PP 28 PORT GIGABIT POE MANAGED SWIT\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10184\",\"NAME\":\"CISCO SG 200-26 26 PORT GIGABIT SMART SWITCH\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10185\",\"NAME\":\"CISCO SG 200-26 26 PORT GIGABIT SMART SWITCH\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10186\",\"NAME\":\"DUAL BAND 321 ACCESS POINT\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10187\",\"NAME\":\"EXTERNAL STOREGE 8T POWER\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10188\",\"NAME\":\"RACK SERVER HP PRO LIANT DL360 GEN9   16G DDR4 - 2\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10189\",\"NAME\":\"MINI-SERVER HP PROLIANT ML310E GEN8 V2\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10190\",\"NAME\":\"MINI SERVER HP PROLIANT ML310E GEN8 V2\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10191\",\"NAME\":\"MINI SERVER HP PROLIANT ML310E GEN8 V2\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10192\",\"NAME\":\"RACK SERVER CABINET\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10193\",\"NAME\":\"RACK SERVER CABINET TOTEN\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10194\",\"NAME\":\"FIVE CPMPUTERS IN SALES DEP. AND 3 IN DATA DEP.\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10195\",\"NAME\":\"ACCESS POINT WIRLESS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10196\",\"NAME\":\"APS SMART UP3 SRT 60001VA 230V\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10197\",\"NAME\":\"HP PRODESK 400 G4\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10198\",\"NAME\":\"HP LAPTOP 15-DA0093NE\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10199\",\"NAME\":\"LENOVO SERVER ST50 E2124G 3 YAERS S#SJ300LZKF\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10200\",\"NAME\":\"UMNIAH -  FIREALL\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10201\",\"NAME\":\"HP PRODESK 400 G5 Q3\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10202\",\"NAME\":\"KINGSTON DDR4 4GB PC 2400 Q3\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10203\",\"NAME\":\"TP LINK 722 USB WIRELESS 150 M Q3\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10204\",\"NAME\":\"HP SERVER DL380 GEN 10 SILVER 4110\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10205\",\"NAME\":\"SYNOLOGY RACK STATION 4 BAY RS818+ SERIES\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10206\",\"NAME\":\"4TB WD RED 64MB CACHE SATA Q4\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10207\",\"NAME\":\"SYNOLOGY DS918+ 4BAY PLUS SERIES\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10208\",\"NAME\":\"8TB WD RED  64MB CACHE SATA Q2\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10209\",\"NAME\":\"CABINER 60*60*45 6U FRONT DOOR STEEL\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10210\",\"NAME\":\"SYNOLOG DS918+ + M.2 240G  Q2 + 4TM 64MB  Q 4\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10211\",\"NAME\":\"ACER ASPIRE E5 576G CORE 15 BLACK\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10212\",\"NAME\":\"HP PRODESK 400 G5 CORE I7 8GEEN\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10213\",\"NAME\":\"HP LAPTOP 15-DA0093NE\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10214\",\"NAME\":\"HP PRODESK 400 G5 CORE I7 8GEN\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10215\",\"NAME\":\"HP PRODESK 400 G5 CORE I7 8GEN\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10216\",\"NAME\":\"WD 2TB ELEMENTS PORATBALE 2.5 Q2\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10217\",\"NAME\":\"KINGSTON HYPER X 8GB 2400 MHZ\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10218\",\"NAME\":\"DELL 20\\\" COLOR LED MONITORS E2016 H\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10219\",\"NAME\":\"HP PRODESK 400 G5 CORE I7 8GEN\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10220\",\"NAME\":\"HP ML110 GEN 10 XEON 3104 (P03684-425)\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10221\",\"NAME\":\"HP ELITEDSK 800 G4 CORE I5 S# 4CE9111KT2\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10222\",\"NAME\":\"HP PRODESK 400 G5 CORE I7\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10223\",\"NAME\":\"HP MONITOR 18.5\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10224\",\"NAME\":\"ACER ASPIRE A315-53G 8GEN CORE I7\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10225\",\"NAME\":\"LENOVO V530S 8GEN I7 Q 2\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10226\",\"NAME\":\"DELL 20\\\" COLOR LED MONITOR\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10227\",\"NAME\":\"SYNOLOG DS918+ 4BAY PLUS SERIES\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10228\",\"NAME\":\"10TB ULTRASTARS DC H510 256MB CACHE QTY 4\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10229\",\"NAME\":\"LENOVO IDEAPAD 330 I7 Q2\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10230\",\"NAME\":\"SERVER DMS NEW (HP DL380 GEN 10 XEON 4208 (8CORE)+\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10231\",\"NAME\":\"MACBOOK PRO 13INCH\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10232\",\"NAME\":\"HP LAPTOP 255 GZ RYZEN 5\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10233\",\"NAME\":\"AST C10 CALL CENTER +\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10234\",\"NAME\":\"LENOVO IDEAPAD 5 INTEAL CORE I5 Q2\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10235\",\"NAME\":\"LENOVO IDEAPAD 5 INTEAL CORE I5 Q4\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10236\",\"NAME\":\"LENOVO IDEAPAD 5 INTEAL CORE I5 Q5\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10237\",\"NAME\":\"DELL VOSTRO 3888  CORE I7 Q2 10GEN\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10238\",\"NAME\":\"DELL VOSTROS 3500 11 GEN CORE I5Q1\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10239\",\"NAME\":\"DELL VOSTROS 3500 11 GEN CORE I7 Q1 HDD\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10240\",\"NAME\":\"DELL OPIOLEX 7090(2021)11 GEN  CORE I7 Q3\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10241\",\"NAME\":\"HP V27I FULL HD IPS MONITOR\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10242\",\"NAME\":\"ASUS EXPERT BOOK CORE I5 Q1\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10243\",\"NAME\":\"ASUS EXPERT BOOK B1 B1500 CORE I5\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10244\",\"NAME\":\"LENOVO 24\\\" L24I-30 MONITOR\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10138\",\"NAME\":\"DESKTOP HP PRO 3500 MICROTOWER PC\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10139\",\"NAME\":\"DESKTOP HP PRO 3500 MICROTOWER PC\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10140\",\"NAME\":\"DESKTOP HP PRO 3500 MICROTOWER PC\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10141\",\"NAME\":\"4G RAM DELL P.E R710 SERVER\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10142\",\"NAME\":\"MONITOR HP\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10143\",\"NAME\":\"DESKTOP HP PRO DESK 400 G3 MT\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10144\",\"NAME\":\"MONITOR HP\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10145\",\"NAME\":\"SYNOLOGY RS 812 + 4TB SATA RED HDD\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10146\",\"NAME\":\"HP PRO 490 I7, 18.5 LED\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10147\",\"NAME\":\"HP PRO 490 I7, 18.5 LED\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10148\",\"NAME\":\"RACK SERVER HP PRO LIANT DL360 GEN9\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10149\",\"NAME\":\"RACK SERVER HP PRO LIANT DL360 GEN9\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10150\",\"NAME\":\"RACK SERVER HP PRO LIANT DL360 GEN9\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10151\",\"NAME\":\"LAPTOP LENOVO 80G0\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10152\",\"NAME\":\"LAPTOP LENOVO 80G0\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10153\",\"NAME\":\"LAPTOP LENOVO 80G0\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10154\",\"NAME\":\"LENOVO THINKPAD YOGA\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10155\",\"NAME\":\"DESKTOP HP PRO DESK 400 G3 MT\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10156\",\"NAME\":\"HP PRO 490 I7, 18.5 LED\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10157\",\"NAME\":\"HP PRO 490 I7, 16G RAM, 1TB\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10158\",\"NAME\":\"APC 6KVA UPS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10159\",\"NAME\":\"LAPTOP HP NOTEBOOK 450\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10160\",\"NAME\":\"LAPTOP HP NOTEBOOK 450\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10161\",\"NAME\":\"LAPTOP HP NOTEBOOK 450\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10162\",\"NAME\":\"LAPTOP HP NOTEBOOK 450\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10163\",\"NAME\":\"LAPTOP HP NOTEBOOK 450\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10164\",\"NAME\":\"LAPTOP HP NOTEBOOK 450\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"COMPUTER EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10165\",\"NAME\":\"LAPTOP HP NOTEBOOK 450\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10065\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10066\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10067\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10068\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10069\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10070\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10071\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10072\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10073\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10074\",\"NAME\":\"JWICO\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10075\",\"NAME\":\"JWICO\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10076\",\"NAME\":\"JWICO\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10077\",\"NAME\":\"JWICO\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10078\",\"NAME\":\"JWICO\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10079\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10080\",\"NAME\":\"JWICO\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10081\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10082\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10083\",\"NAME\":\"غاز على الكهرباء\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10084\",\"NAME\":\"كولر ماء\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10085\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10086\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10087\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10088\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10089\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10090\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10091\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10092\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10093\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10094\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10095\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10096\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10097\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10098\",\"NAME\":\"HISHAM F. SALAMEH & PARTNERS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10099\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10100\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10101\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10102\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10103\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10104\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10105\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10106\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10107\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10108\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10109\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10110\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10111\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10112\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10113\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10114\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10115\",\"NAME\":\"SAFE BOX 50K.G\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10116\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10117\",\"NAME\":\"CORNER SOFA #-SEAT BORRED\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10118\",\"NAME\":\"COFFEE TABLE . WHITE STAINED OAK CFFECT\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10119\",\"NAME\":\"طاولة اجتماعت خشب اسباني عدد 6\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10120\",\"NAME\":\"وحدة ادراج خشب اسباني متحرك ثلاثية عدد 6\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10121\",\"NAME\":\"قاطع خشب عدد 5\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10001\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10002\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10003\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10004\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10005\",\"NAME\":\"ABDO ABU MAYALEH\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10006\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10007\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10008\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10009\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10010\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10011\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10012\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10013\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10014\",\"NAME\":\"JWICO\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10015\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10016\",\"NAME\":\"TARGET GROUP FOR OFFICE FURNITURE\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10017\",\"NAME\":\"TARGET GROUP FOR OFFICE FURNITURE\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10018\",\"NAME\":\"TARGET GROUP FOR OFFICE FURNITURE\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10019\",\"NAME\":\"TARGET GROUP FOR OFFICE FURNITURE\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10021\",\"NAME\":\"JWICO\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10020\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10022\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10023\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10024\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10025\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10026\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10027\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10028\",\"NAME\":\"ABDO ABU MAYALEH\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10029\",\"NAME\":\"ABDO ABU MAYALEH\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10030\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10031\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10032\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10033\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10034\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10035\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10036\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10037\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10038\",\"NAME\":\"JWICO\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10039\",\"NAME\":\"JWICO\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10040\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10041\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10042\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10043\",\"NAME\":\"الموبليات الحزين\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10044\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10045\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10046\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10047\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10048\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10049\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10050\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10051\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10052\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10053\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10054\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10055\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10056\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10057\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10058\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10059\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10060\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10061\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10062\",\"NAME\":\"SMART OFFICE FURNITURE AND PROJECTS\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10063\",\"NAME\":\"JWICO\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"FURNTURE\",\"TYPENO\":\"\",\"CODE\":\"10064\",\"NAME\":\"T\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"MACHINERY AND EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10122\",\"NAME\":\"EACHSL V0521ST0048 SAMSUNG LED 48\\\" SMART TV 3D\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"MACHINERY AND EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10137\",\"NAME\":\"MOBAIL IPHONE 13 STARLIGHT 128 GB\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"MACHINERY AND EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10124\",\"NAME\":\"PART OF TELEPHONE SYSTEM (FULL INVOICE DIVIDED IBL\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"MACHINERY AND EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10125\",\"NAME\":\"MOBAIL SAMSUNG GALAXY A50 BLACK\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"MACHINERY AND EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10126\",\"NAME\":\"IN DOOR CAMERA 5MP DS-2CE16HOT Q10\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"MACHINERY AND EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10127\",\"NAME\":\"DVR 16 CHANNEL 5MP FULL FRAME HQ\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"MACHINERY AND EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10128\",\"NAME\":\"LED TV GG-43X SMART\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"MACHINERY AND EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10129\",\"NAME\":\"CISCO SWITCH SG350-28 NON POE 1 GB\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"MACHINERY AND EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10130\",\"NAME\":\"CISCO SWITCH SG350-10 NON POE 1 GB\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"MACHINERY AND EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10131\",\"NAME\":\"PATCH PANEL 24 PORT MOULAR\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"MACHINERY AND EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10132\",\"NAME\":\"LED TV GG-32X SMART\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"MACHINERY AND EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10133\",\"NAME\":\"IFACE302 ZKT ECO\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"MACHINERY AND EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10134\",\"NAME\":\"KMMFC BIZHUB C257I\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"MACHINERY AND EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10135\",\"NAME\":\"SAMSUNG 58 INCH SMART 4K TV\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"MACHINERY AND EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10136\",\"NAME\":\"MOBAIL SAMSUNG GALAXY A23  WHITE\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"MACHINERY AND EQUIPMENTS\",\"TYPENO\":\"\",\"CODE\":\"10123\",\"NAME\":\"PHOTOCOPIER KONICA MINOLTA BIZHUB C224E WITH EXTRA\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"SOFTWARE\",\"TYPENO\":\"\",\"CODE\":\"10245\",\"NAME\":\"SOFTWARE \\/ WINDOWS SERVER + LICENSE\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"SOFTWARE\",\"TYPENO\":\"\",\"CODE\":\"10246\",\"NAME\":\"CYBEROAM CR 200 ING UTM\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"SOFTWARE\",\"TYPENO\":\"\",\"CODE\":\"10250\",\"NAME\":\"AST C10 CALL CENTER + ASTTECS SOFTWARE INSTALLATIO\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"SOFTWARE\",\"TYPENO\":\"\",\"CODE\":\"10248\",\"NAME\":\"COMPUTER FINANCIAL & MANAGERIAL SOFTWARE (SYSTEM)\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"SOFTWARE\",\"TYPENO\":\"\",\"CODE\":\"10249\",\"NAME\":\"AST C10 CALL CENTER + ASTTECS SOFTWARE INSTALLATIO\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"},{\"TYPE\":\"SOFTWARE\",\"TYPENO\":\"\",\"CODE\":\"10247\",\"NAME\":\"CYBEROAM CR 10 ING UTM\",\"MAINMNG\":\"\",\"DEPARTMENT\":\"\",\"SECTION\":\"\",\"AREANAME\":\"\",\"ASSETBARCODE\":\"\"}]";
            if (JsonResponse != null && JsonResponse.contains("TYPENO")) {
                Log.e("Assets", "****Success");
                JsonResponseAssetsSave=JsonResponse;
                new SaveItemAssets().execute();


            }else if (JsonResponse != null && JsonResponse.contains("No Data Found.")){
                Log.e("ASSETS", "****No Data Found.");
            }
            else {
                Log.e("ASSETS", "****Failed to export data");
//                Toast.makeText(context, "Failed to Get data", Toast.LENGTH_SHORT).show();
                if(pd!=null) {
                    pd.dismiss();
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(context.getResources().getString(R.string.ops))
                            .setContentText(context.getResources().getString(R.string.fildtoimportitemAssets))
                            .show();
                }


            }

        }
    }


    private class SaveItemCard extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialogSave = new ProgressDialog(context,R.style.MyTheme);
//            progressDialogSave.setCancelable(false);
//            progressDialogSave.setMessage("Loading Save in DataBase...");
//            progressDialogSave.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialogSave.setProgress(0);
//            progressDialogSave.show();

            pd.getProgressHelper().setBarColor(Color.parseColor("#1F6381"));
            pd.setTitleText(context.getResources().getString(R.string.savingindb));

        }

        @Override
        protected String doInBackground(String... params) {///GetModifer?compno=736&compyear=2019

            try {
                Log.e("tag_itemCard", "****inDataBaseSave");
                JSONArray parentArray = new JSONArray(JsonResponseSave);


                List<ItemCard> itemCard=new ArrayList<>();
              //  List<ItemCard> itemCard2=dbHandler.getAllItemCard();
//                boolean stopBollen=true;
//if(itemCard2.size()==0){
//    dbHandler.deleteAllItem("ITEM_CARD");
//    stopBollen=false;
//}

                String q="INSERT INTO ITEM_CARD VALUES";
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    ItemCard obj = new ItemCard();
                    try{
                        obj.setItemCode(finalObject.getString("ItemOCode"));
                    }catch (Exception e){
                        obj.setItemCode("");

                    }
                    try {
                        obj.setItemName(finalObject.getString("ItemNameA"));
                    }catch (Exception e){
                        obj.setItemName("");

                    }
//                        obj.setit(finalObject.getString("ItemNameE"));
                    try {
                        obj.setItemG(finalObject.getString("ItemG"));
                    }catch (Exception e){
                        obj.setItemG("");

                    }
//                        obj.set(finalObject.getString("TAXPERC"));

                    try {
                        obj.setSalePrc(finalObject.getString("SalePrice"));
                    }catch (Exception e){
                        obj.setSalePrc("");

                    }
//

//                        obj.set(finalObject.getString("LLCPrice"));
                    try {
                        obj.setAVLQty(finalObject.getString("AVLQTY"));
                    }catch (Exception e){
                        obj.setAVLQty("");

                    }
                    try {
                        obj.setFDPRC(finalObject.getString("F_D"));
                    }catch (Exception e){
                        obj.setFDPRC("");

                    }

                    try {
                        obj.setItemK(finalObject.getString("ItemK"));
                    }catch (Exception e){
                        obj.setItemK("");

                    }

                    try {
                        obj.setItemL(finalObject.getString("ItemL"));
                    }catch (Exception e){

                        obj.setItemL("");

                    }

                    try {
                        obj.setItemDiv(finalObject.getString("ITEMDIV"));
                    }catch (Exception e){
                        obj.setItemDiv("");

                    }

                    try {
                        obj.setItemGs(finalObject.getString("ITEMGS"));
                    }catch (Exception e){
                        obj.setItemGs("");

                    }

                    try {
                        obj.setInDate(finalObject.getString("InDate"));
                    }catch (Exception e){
                        obj.setInDate("");

                    }

                    try {
                        obj.setItemM(finalObject.getString("ItemM"));
                    }catch (Exception e){
                        obj.setItemM("");

                    }
                    try {
                       // dbHandler.deleteItemCardByItemCode(finalObject.getString("ItemOCode"));
                    }catch (Exception e){

                    }
//                    dbHandler.deleteItemCardSwitchByItemCode(finalObject.getString("ItemOCode"));
//                    dbHandler.deleteItemCardUnitByItemCode(finalObject.getString("ItemOCode"));
//                    dbHandler.deleteItemCardQRByItemCode(finalObject.getString("ItemOCode"));

                    itemCard.add(obj);
//                    if(stopBollen){

//                    }

                 //   dbHandler.addItemcardTable(itemCard.get(i));

//                    Log.e("rrrrrrrr", "s_"+i);
//

//                        q += "('" +
//                                itemCard.get(i).getItemCode()
//                                + "','" + itemCard.get(i).getItemName()
//                                + "','" + itemCard.get(i).getCostPrc()
//                                + "','" + itemCard.get(i).getSalePrc()
//                                + "','" + itemCard.get(i).getAVLQty()
//                                + "','" + itemCard.get(i).getFDPRC()
//                                + "','" + itemCard.get(i).getBranchId()
//                                + "','" + itemCard.get(i).getBranchName()
//                                + "','" + itemCard.get(i).getDepartmentId()
//                                + "','" + itemCard.get(i).getDepartmentName()
//                                + "','" + itemCard.get(i).getItemG()
//                                + "','" + itemCard.get(i).getItemK()
//                                + "','" + itemCard.get(i).getItemL()
//                                + "','" + itemCard.get(i).getItemDiv()
//                                + "','" + itemCard.get(i).getItemGs()
//                                + "','" + itemCard.get(i).getOrgPrice()
//                                + "','" + itemCard.get(i).getInDate()
//                                + "','" + itemCard.get(i).getIsExport()
//                                + "','" + itemCard.get(i).getIsNew()
//                                + "'),";

//


//                               q += "('" +
//                                finalObject.getString("ItemOCode")
//                                + "','" +finalObject.getString("ItemNameA")
//                                + "','" + ""
//                                + "','" + finalObject.getString("SalePrice")
//                                + "','" + finalObject.getString("AVLQTY")
//                                + "','" + finalObject.getString("F_D")
//                                + "','" + ""
//                                + "','" +""
//                                + "','" + ""
//                                + "','" + ""
//                                + "','" + finalObject.getString("ItemG")
//                                + "','" + finalObject.getString("ItemK")
//                                + "','" + finalObject.getString("ItemL")
//                                + "','" + finalObject.getString("ITEMDIV")
//                                + "','" + finalObject.getString("ITEMGS")
//                                + "','" + ""
//                                + "','" + finalObject.getString("InDate")
//                                + "','" + ""
//                                + "','" + ""
//                                + "'),";



                }
                MainActivity2  contextM=(MainActivity2)context;
                contextM.dd(itemCard);


                Log.e("rrrrrrrr", "finish");
//                dbHandler.addItemcardTableTester(itemCard);
              //  Log.e("itemSave", ""+q);

//                dbHandler.addItemcardTableTestAll_2(q);

//
//                dbHandler.deleteAllItem("ITEM_CARD");
//
//                for (int i = 0; i < itemCard.size(); i++) {
//                    dbHandler.deleteItemCardByItemCode(itemCard.get(i).getItemCode());
//                    dbHandler.addItemcardTable(itemCard.get(i));
//
//                }
//                dbHandler.addItemcardTableTest(itemCard);

                Log.e("tag_itemCard", "****saveSuccess");


            } catch (JSONException e) {
                e.printStackTrace();

                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(context.getResources().getString(R.string.ops))
                        .setContentText("error 1600"+e.getMessage().toString())
                        .show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);
            Toast.makeText(context, "Save Item Card Success", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Start Import Item Switch", Toast.LENGTH_SHORT).show();
            pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pd.setTitleText(context.getResources().getString(R.string.saveitemcard));

            new SyncItemSwitch().execute();

//            progressDialog.dismiss();
        }
    }



    private class SaveItemSwitch extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialogSave = new ProgressDialog(context,R.style.MyTheme);
//            progressDialogSave.setCancelable(false);
//            progressDialogSave.setMessage("Loading Save in DataBase...");
//            progressDialogSave.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialogSave.setProgress(0);
//            progressDialogSave.show();
            pd.getProgressHelper().setBarColor(Color.parseColor("#1F6381"));
            pd.setTitleText(context.getResources().getString(R.string.savingitemswitch));

        }

        @Override
        protected String doInBackground(String... params) {///GetModifer?compno=736&compyear=2019


                Log.e("TAG_itemSwitch", "****Success");

                try {

                    JSONArray parentArray = new JSONArray(JsonResponseSaveSwitch);


                    List<ItemSwitch> itemCard=new ArrayList<>();

                    for (int i = 0; i < parentArray.length(); i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);

                        ItemSwitch obj = new ItemSwitch();
//                        obj.se(finalObject.getString("CoNo"));
                        obj.setItemOCode(finalObject.getString("ItemOCode"));
                        obj.setItemNCode(finalObject.getString("ItemNCode"));
                        obj.setItemNameA(finalObject.getString("ItemNameA"));
                        obj.setItemNameE(finalObject.getString("ItemNameE"));
                        obj.setInDate(finalObject.getString("INDATE"));

                        itemCard.add(obj);
                      //  dbHandler.deleteItemSwitchByItemCode(itemCard.get(i).getItemOCode(),itemCard.get(i).getItemNCode());

                    }
//                    dbHandler.deleteAllItem("ITEM_SWITCH");
                    //dbHandler.addItemSwitchTester(itemCard);5

                    MainActivity2  contextM=(MainActivity2)context;
                    contextM.saveSwitch(itemCard);

//
//                    dbHandler.deleteAllItem("ITEM_SWITCH");
//                    for (int i = 0; i < itemCard.size(); i++) {
//                        dbHandler.deleteItemSwitchByItemCode(itemCard.get(i).getItemOCode(),itemCard.get(i).getItemNCode());
//                        dbHandler.addItemSwitch(itemCard.get(i));
//                    }

                    Log.e("TAG_itemSwitch", "****SaveSuccess");



                } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

            pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pd.setTitleText(context.getResources().getString(R.string.saveitemswitch));

            if(QrUse.equals("1")) {
                new SyncItemQR().execute();
            }else {
            new SyncItemUnite().execute();
            }
        }
    }

    private class SaveItemQr extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.getProgressHelper().setBarColor(Color.parseColor("#1F6381"));
            pd.setTitleText(context.getResources().getString(R.string.saveItemQr));
        }

        @Override
        protected String doInBackground(String... params) {


            Log.e("TAG_itemQR", "****Success");

            try {

                JSONArray parentArray = new JSONArray(JsonResponseSaveQRCode);


                List<ItemQR> itemQRS=new ArrayList<>();

                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    ItemQR obj = new ItemQR();

                    obj.setStoreNo(finalObject.getString("STRNO"));
                    obj.setItemCode(finalObject.getString("ITEMCODE"));
                    obj.setItemNmae(finalObject.getString("ITEMNAME"));
                    obj.setSalesPrice(finalObject.getString("PRICE"));
                    obj.setQrCode(finalObject.getString("QRCODE"));
                    obj.setLotNo(finalObject.getString("LOTNUMBER"));

                    itemQRS.add(obj);
                    dbHandler.deleteItemQRByItemCode(itemQRS.get(i).getItemCode(),itemQRS.get(i).getQrCode());

                }

                dbHandler.addItemQRList(itemQRS);
                Log.e("TAG_itemQR", "****SaveSuccess");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

            pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pd.setTitleText(context.getResources().getString(R.string.saveitemQR));
            new SyncItemUnite().execute();
        }
    }


    private class SaveItemUnite extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialogSave = new ProgressDialog(context,R.style.MyTheme);
//            progressDialogSave.setCancelable(false);
//            progressDialogSave.setMessage("Loading Save in DataBase...");
//            progressDialogSave.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialogSave.setProgress(0);
//            progressDialogSave.show();
            pd.getProgressHelper().setBarColor(Color.parseColor("#1F6381"));
            pd.setTitleText("Save Item Unite");

        }

        @Override
        protected String doInBackground(String... params) {///GetModifer?compno=736&compyear=2019


            Log.e("TAG_itemSwitch", "****Success");

            try {

                JSONArray parentArray = new JSONArray(JsonResponseSaveUnite);


                List<ItemUnit> itemCard=new ArrayList<>();

                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);




//                    {"ITEMOCODE":"1002839","ITEMBARCODE":"4800528456282","SALEPRICE":"1.89","ITEMU":"حبة","UQTY":"1","USERIAL":"1","CALCQTY":"1","WHOLESALEPRC":"0","PURCHASEPRICE":"0","PCLASS1":"0","PCLASS2":"0","PCLASS3":"0","INDATE":"12\/28\/2019 10:30:45 AM","UNIT_NAME":"شعيرية بيهون الخاصة هوبي 454 غم","ORG_SALEPRICE":"","OLD_SALE_PRICE":"","UPDATE_DATE":"12\/28\/2019 10:30:45 AM"}
//
//[{"ITEMOCODE":"6251001212648","ITEMBARCODE":"6251001212648","SALEPRICE":"2","ITEMU":"حبة","UQTY":"1","USERIAL":"1","CALCQTY":"1","WHOLESALEPRC":"2","PURCHASEPRICE":"0","PCLASS1":"","PCLASS2":"","PCLASS3":"","INDATE":"12\/08\/2020 4:44:53 PM","UNIT_NAME":"AAA","ORG_SALEPRICE":"0","OLD_SALE_PRICE":"0","UPDATE_DATE":""}]


                    ItemUnit obj = new ItemUnit();

                    obj.setItemOCode(finalObject.getString("ITEMOCODE"));
                    obj.setItemBarcode(finalObject.getString("ITEMBARCODE"));

                    try {
                        obj.setSalePrice(Float.parseFloat(finalObject.getString("SALEPRICE")));


                    }catch (Exception ex){
                        obj.setSalePrice(0);
                        Log.e("setSalePrice",""+ex.toString());
                    }

                    obj.setItemU(finalObject.getString("ITEMU"));

                    try {
                        obj.setUQty(Float.parseFloat(finalObject.getString("UQTY")));

                    }catch (Exception ex){
                        obj.setUQty(0);
                        Log.e("setUQty",""+ex.toString());
                    }

                    try {
                        obj.setUSerial(Integer.parseInt(finalObject.getString("USERIAL")));

                    }catch (Exception ex){
                        obj.setUSerial(0);
                        Log.e("setUSerial",""+ex.toString());
                    }


                    try {
                        obj.setCalcQty(Float.parseFloat(finalObject.getString("CALCQTY")));

                    }catch (Exception ex){
                        obj.setCalcQty(0);
                        Log.e("setCalcQty",""+ex.toString());
                    }

                    try {
                        obj.setWholeSalePrc(Float.parseFloat(finalObject.getString("WHOLESALEPRC")));

                    }catch (Exception ex){
                        obj.setWholeSalePrc(0);
                        Log.e("setWholeSalePrc",""+ex.toString());
                    }

                    try {
                        obj.setPurchasePrc(Float.parseFloat(finalObject.getString("PURCHASEPRICE")));

                    }catch (Exception ex){
                        obj.setPurchasePrc(0);
                        Log.e("setPurchasePrc",""+ex.toString());
                    }


                    try {
                        obj.setPclAss1(Float.parseFloat(finalObject.getString("PCLASS1")));

                    }catch (Exception ex){
                        obj.setPclAss1(0);
                        Log.e("setPclAss1",""+ex.toString());
                    }


                    try {
                        obj.setPclAss2(Float.parseFloat(finalObject.getString("PCLASS2")));

                    }catch (Exception ex){
                        obj.setPclAss2(0);
                        Log.e("setPclAss2",""+ex.toString());
                    }
                    try {
                        obj.setPclAss3(Float.parseFloat(finalObject.getString("PCLASS3")));
                    }catch (Exception ex){
                        obj.setPclAss3(0);
                        Log.e("setPclAss3",""+ex.toString());
                    }

                    obj.setInDate(finalObject.getString("INDATE"));
                    obj.setUnitName(finalObject.getString("UNIT_NAME"));
                    obj.setOrgSalePrice(finalObject.getString("ORG_SALEPRICE"));

                    obj.setOldSalePrice(finalObject.getString("OLD_SALE_PRICE"));
                    obj.setUpdateDate(finalObject.getString("UPDATE_DATE"));


                    itemCard.add(obj);
                  //  dbHandler.deleteItemUniteByItemCode(itemCard.get(i).getItemOCode(),itemCard.get(i).getItemBarcode());

                }
//                    dbHandler.deleteAllItem("ITEM_SWITCH");
                //    dbHandler.addItemUniteTable(itemCard);
                MainActivity2  contextM=(MainActivity2)context;
                contextM.saveUnit(itemCard);
//
//                    dbHandler.deleteAllItem("ITEM_SWITCH");
//                    for (int i = 0; i < itemCard.size(); i++) {
//                        dbHandler.deleteItemSwitchByItemCode(itemCard.get(i).getItemOCode(),itemCard.get(i).getItemNCode());
//                        dbHandler.addItemSwitch(itemCard.get(i));
//                    }

                Log.e("TAG_itemSwitch", "****SaveSuccess");



            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);
//            Toast.makeText(context, "Save Item Card Success", Toast.LENGTH_SHORT).show();
//            Toast.makeText(context, "Start Import Item Switch", Toast.LENGTH_SHORT).show();
//
            pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pd.setTitleText("Save Item Unite In DataBase");
            new SyncGetStor().execute();
//            progressDialog.dismiss();
        }
    }


    private class SaveItemAssets extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialogSave = new ProgressDialog(context,R.style.MyTheme);
//            progressDialogSave.setCancelable(false);
//            progressDialogSave.setMessage("Loading Save in DataBase...");
//            progressDialogSave.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialogSave.setProgress(0);
//            progressDialogSave.show();

            pd.getProgressHelper().setBarColor(Color.parseColor("#1F6381"));
            pd.setTitleText(context.getResources().getString(R.string.AssetsSave));

        }

        @Override
        protected String doInBackground(String... params) {///GetModifer?compno=736&compyear=2019

            try {
                Log.e("Assets", "****inDataBaseSave");
                JSONArray parentArray = new JSONArray(JsonResponseAssetsSave);


                List<AssestItem> assestItems=new ArrayList<>();
//                boolean stopBollen=true;
//if(itemCard2.size()==0){
//    dbHandler.deleteAllItem("ITEM_CARD");
//    stopBollen=false;
//}
                    dbHandler.deleteAllItem("ASSEST_TABLE");


                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

//                    ظظ"MAINMNG":"ادارة","DEPARTMENT":"دائرة","SECTION":"القسم","AREANAME":"الموقع"
                    AssestItem obj = new AssestItem();
                    obj.setAssesstType(finalObject.getString("TYPE"));
                    obj.setAssesstNo(finalObject.getString("TYPENO"));
                    obj.setAssesstCode(finalObject.getString("CODE"));
                    obj.setAssesstName(finalObject.getString("NAME"));
                    obj.setAssesstAREANAME(finalObject.getString("AREANAME"));
                    obj.setAssesstSECTION(finalObject.getString("SECTION"));
                    obj.setAssesstDEPARTMENT(finalObject.getString("DEPARTMENT"));
                    obj.setAssesstMangment(finalObject.getString("MAINMNG"));
                    obj.setAssesstBarcode(finalObject.getString("ASSETBARCODE"));

//
                    assestItems.add(obj);
//                    if(stopBollen){
//                    dbHandler.deleteItemCardByItemCode(assestItems.get(i).getItemCode());
//                    }

//                    dbHandler.addItemcardTable(itemCard.get(i));



                }

                dbHandler.addAssetsItemList(assestItems);

//
//                dbHandler.deleteAllItem("ITEM_CARD");
//
//                for (int i = 0; i < itemCard.size(); i++) {
//                    dbHandler.deleteItemCardByItemCode(itemCard.get(i).getItemCode());
//                    dbHandler.addItemcardTable(itemCard.get(i));
//
//                }
//                dbHandler.addItemcardTableTest(itemCard);

                Log.e("tag_itemCard", "****saveSuccess");


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);
//            Toast.makeText(context, "Save Item Assets Success", Toast.LENGTH_SHORT).show();
//            Toast.makeText(context, "Start Import Item Switch", Toast.LENGTH_SHORT).show();
            if(pd!=null) {
                pd.dismiss();
                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(context.getResources().getString(R.string.save_SUCCESS))
                        .setContentText(context.getResources().getString(R.string.assestSave))
                        .show();
            }


//            progressDialog.dismiss();
        }
    }

    public void getItems(String itemcode) {
        Log.e("getItems", "" + "getItems  "+itemcode);
        pdRepla4 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdRepla4.getProgressHelper().setBarColor(Color.parseColor("#7A7A7A"));
        pdRepla4.setTitleText("Get Item data");
        pdRepla4.setCancelable(false);
        pdRepla4.show();
        onlineItems.clear();
        final List<MainSetting> mainSettings = dbHandler.getAllMainSetting();
        if (mainSettings.size() != 0) {
            this.ip = mainSettings.get(0).getIP();
            this.isAssetsIn = mainSettings.get(0).getIsAssest();
            this.QrUse = mainSettings.get(0).getIsQr();
            this.onlinePrice=mainSettings.get(0).getOnlinePrice();
            this.CompanyNo=mainSettings.get(0).getCompanyNo();
            this.coName=mainSettings.get(0).getCoName();
        }
        new SyncItemCard333(itemcode).execute();
       // new JSONTaskGetDirectItems(itemcode).execute();
    }
    //
    private class JSONTaskGetDirectItems extends AsyncTask<String, String, String> {

       String ITEMCODE ;
        String   link;
        public JSONTaskGetDirectItems(String ITEMCODE) {
            Log.e("JSONTaskGetDirectItems", "" + "JSONTaskGetDirectItems  ");
            this.ITEMCODE = ITEMCODE;
            Log.e("JSONTaskGetDirectItems2", "" + "JSONTaskGetDirectItems  ");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                Log.e("ip===", "" + ip);
                if (!ip.equals("")) {

                    http://10.0.0.22:8085/GetJRDITEMS?FROM_DATE=01%2F12%2F2021&TO_DATE=27%2F06%2F2022&CONO=290
                    Log.e("link===", "" + link);
                    link = "http://" + ip.trim() + "/GetDirectItems?CONO="+ CompanyNo.trim()+"&ITEMCODE="+ITEMCODE;

                    Log.e("link===", "" + link);
                }
            } catch (Exception e) {
                Log.e("getAllSto", e.getMessage());
                pdRepla4.dismiss();
            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();


                //JSONArray parentObject = new JSONArray(finalJson);

                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        pdRepla4.dismiss();
                        Toast.makeText(context, context.getString(R.string.ipConnectionFailed), Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
                pdRepla4.dismiss();
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        try {

                            Toast.makeText(context, "The target server failed to respond", Toast.LENGTH_SHORT).show();
                        } catch (WindowManager.BadTokenException e) {
                            //use a log message
                        }
                    }
                });
//                progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(String array) {
            super.onPostExecute(array);
            pdRepla4.dismiss();
            JSONObject jsonObject1 = null;
            if (array != null) {
                Log.e("array====",array+"");

                if (array.contains("ItemOCode")) {

                    if (array.length() != 0) {
                        try {
                            Log.e("here====","here");
                            JSONArray requestArray = null;
                            requestArray = new JSONArray(array);


                            for (int i = 0; i < requestArray.length(); i++) {
                                Log.e("here2====","here");
                                jsonObject1 = requestArray.getJSONObject(i);
                                OnlineItems item = new OnlineItems();
                                item.setItemOCode(jsonObject1.getString("ItemOCode"));
                                item.setItemNameA(jsonObject1.getString("ItemNameA"));
                                item.setSalePrice(jsonObject1.getString("SalePrice"));
                                item.setF_D(jsonObject1.getString("F_D"));
                                Log.e("item====","item"+item.getItemOCode());
                                onlineItems.add(item);
                                texetrespone.setText("ItemOCode");

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }



                }

            } else {




            }
        }


    }



}

//
//
////
//
//
//    public boolean isInternetAvailable() {
//        try {
//            final InetAddress address = InetAddress.getByName("www.google.com");
//            return !address.equals("");
//        } catch (UnknownHostException e) {
//            // Log error
//        }
//        return false;
//    }
//
//}






