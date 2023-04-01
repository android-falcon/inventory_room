package com.example.user54.InventoryApp;

import static com.example.user54.InventoryApp.controll.RoomVersion;
import static com.example.user54.InventoryApp.controll.dataBaseNo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.service.controls.Control;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.user54.InventoryApp.Model.MainSetting;
import com.example.user54.InventoryApp.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LogIn extends AppCompatActivity {

    EditText userName, password;
    Button logIn,dataB;
    TextView messageLogIn, passStar, userStar,onIntent;
    importJson json1;
    CheckBox design;
    public static TextView intentControl;

  //  InventoryDatabase database;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(controll.isYellow){
            setContentView(R.layout.log_activaty_yellow);
        }else{
            setContentView(R.layout.log_activaty);
        }
       // database=new InventoryDatabase(LogIn.this);
     //   List<MainSetting> main=new ArrayList<>();
//        try {
//            main = database.getAllMainSetting();
//
//            if (main.size() != 0) {
//                dataBaseNo = main.get(0).getDataBaseNo();
//            }
//        }catch (Exception e){
//
//        }

        dataB=findViewById(R.id.data);
        dataB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Log.e("showpassworddailog","showpassworddailog");
                    passwordDialog = new Dialog(MainActivity2.this);
                    passwordDialog.setCancelable(true);
                    passwordDialog.setContentView(R.layout.passworddailog);
                    passwordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                    passwordEt = passwordDialog.findViewById(R.id.passwordd);
                    okBtn = passwordDialog.findViewById(R.id.done);
                    passwordDialog.show();


                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (passwordEt.getText().toString().trim().equals("")) {

                                passwordEt.requestFocus();
                                passwordEt.setError(getString(R.string.required));

                            } else {

                                if (passwordEt.getText().toString().trim().equals("2023000")) {
                                    controll co=new controll();
                                    co.WriteBtn(LogIn.this,RoomVersion+"");

                                    passwordDialog.dismiss();

                                } else {

                                    passwordEt.setError("");

                                }


                            }

                        }
                    });






            }
        });
        String data=readFromFile(LogIn.this);

        if(!TextUtils.isEmpty(data)){
            dataBaseNo = Integer.parseInt(data.replace("\n",""));
        }else {
            dataBaseNo=79;
        }

        design=(CheckBox)findViewById(R.id.design);
        design.setVisibility(View.GONE);
            if(controll.isYellow){
                design.setChecked(true);
            }else{
                design.setChecked(false);
            }
        design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(design.isChecked()){
                    if(controll.isYellow){

                    }else{
                        finish();
                        Intent intent=new Intent(LogIn.this,LogIn.class);
                        startActivity(intent);
                    }
                    controll.isYellow=true;
                }else{

                    if(controll.isYellow){
                        finish();
                        Intent intent=new Intent(LogIn.this,LogIn.class);
                        startActivity(intent);
                    }else{

                    }
                    controll.isYellow=false;
                }
            }
        });

        logIn = (Button) findViewById(R.id.log);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.passwordLog);
        messageLogIn = (TextView) findViewById(R.id.message);
        passStar = (TextView) findViewById(R.id.pasStar);
        userStar = (TextView) findViewById(R.id.userStar);
        onIntent=(TextView) findViewById(R.id.onIntent);

//        json1 = new importJson();
//        json1.execute();


        onIntent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(onIntent.getText().toString().equals("@")&&!onIntent.getText().toString().equals("")){
                    Intent collectIntent = new Intent(LogIn.this, MainActivity2.class);
                    collectIntent.putExtra("USER_NAME", "");
                    finish();
                    startActivity(collectIntent);
                    onIntent.setText("1");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameL, passwordL;
                userNameL = userName.getText().toString();
                passwordL = password.getText().toString();


                if (!userNameL.equals("") || !passwordL.equals("")) {
                    if (!userNameL.equals("")) {
                        userStar.setText("");
                        if (!passwordL.equals("")) {
                            passStar.setText("");
                            if (logInAuthentication(userNameL, passwordL)) {
                                intentControl=onIntent;
//                                importJson sendCloud = new importJson(LogIn.this,"");
//                                sendCloud.startSending("ItemCard");
                                onIntent.setText("@");
                            } else {
                                messageLogIn.setText(getResources().getString(R.string.notcorrectusernamepass));
                            }
                        } else {
                            messageLogIn.setText(getResources().getString(R.string.interPass));
                            passStar.setText("*");
                        }
                    } else {
                        messageLogIn.setText(getResources().getString(R.string.enterUsername));
                        userStar.setText("*");
                    }
                } else {
                    messageLogIn.setText(getResources().getString(R.string.enterPassAndUser));
                    userStar.setText("*");
                    passStar.setText("*");
                }


            }
        });


    }

    boolean logInAuthentication(String userName, String password) {
        boolean Authentication = false;
        if (userName.equals("1") && password.equals("1")) {
            Authentication = true;
        }
//        else  {
//
//
//
//
//        }

        return Authentication;
    }
    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("databaseNo.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


}
