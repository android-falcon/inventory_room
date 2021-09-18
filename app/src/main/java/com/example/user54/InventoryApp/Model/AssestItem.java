package com.example.user54.InventoryApp.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class AssestItem {

    private String assesstCode;
    private String assesstName;
    private String assesstNo;
    private String assesstType;
    private boolean isCheck;
    private String count;
    private String price;
    private String assesstQty;
    private String assesstDate;
    private String assesstMangment;
    private String assesstDEPARTMENT;
    private String assesstSECTION;
    private String assesstAREANAME;
    private String assesstBarcode;
    private String isExport;
    private String serial;


    public AssestItem() {
    }

    public AssestItem(String assesstCode, String assesstName, String assesstNo, String assesstType, boolean isCheck, String count, String price, String assesstQty, String assesstDate, String assesstMangment, String assesstDEPARTMENT, String assesstSECTION, String assesstAREANAME, String assesstBarcode, String isExport) {
        this.assesstCode = assesstCode;
        this.assesstName = assesstName;
        this.assesstNo = assesstNo;
        this.assesstType = assesstType;
        this.isCheck = isCheck;
        this.count = count;
        this.price = price;
        this.assesstQty = assesstQty;
        this.assesstDate = assesstDate;
        this.assesstMangment = assesstMangment;
        this.assesstDEPARTMENT = assesstDEPARTMENT;
        this.assesstSECTION = assesstSECTION;
        this.assesstAREANAME = assesstAREANAME;
        this.assesstBarcode = assesstBarcode;
        this.isExport = isExport;
    }

    public String getAssesstCode() {
        return assesstCode;
    }

    public void setAssesstCode(String assesstCode) {
        this.assesstCode = assesstCode;
    }

    public String getAssesstName() {
        return assesstName;
    }

    public void setAssesstName(String assesstName) {
        this.assesstName = assesstName;
    }

    public String getAssesstNo() {
        return assesstNo;
    }

    public void setAssesstNo(String assesstNo) {
        this.assesstNo = assesstNo;
    }

    public String getAssesstType() {
        return assesstType;
    }

    public void setAssesstType(String assesstType) {
        this.assesstType = assesstType;
    }

    public boolean getCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public String getAssesstQty() {
        return assesstQty;
    }

    public void setAssesstQty(String assesstQty) {
        this.assesstQty = assesstQty;
    }

    public String getAssesstDate() {
        return assesstDate;
    }

    public void setAssesstDate(String assesstDate) {
        this.assesstDate = assesstDate;
    }

    public String getAssesstMangment() {
        return assesstMangment;
    }

    public void setAssesstMangment(String assesstMangment) {
        this.assesstMangment = assesstMangment;
    }

    public String getAssesstDEPARTMENT() {
        return assesstDEPARTMENT;
    }

    public void setAssesstDEPARTMENT(String assesstDEPARTMENT) {
        this.assesstDEPARTMENT = assesstDEPARTMENT;
    }

    public String getAssesstSECTION() {
        return assesstSECTION;
    }

    public void setAssesstSECTION(String assesstSECTION) {
        this.assesstSECTION = assesstSECTION;
    }

    public String getAssesstAREANAME() {
        return assesstAREANAME;
    }

    public void setAssesstAREANAME(String assesstAREANAME) {
        this.assesstAREANAME = assesstAREANAME;
    }

    public String getAssesstBarcode() {
        return assesstBarcode;
    }

    public void setAssesstBarcode(String assesstBarcode) {
        this.assesstBarcode = assesstBarcode;
    }

    public String getIsExport() {
        return isExport;
    }

    public void setIsExport(String isExport) {
        this.isExport = isExport;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public JSONObject getJSONObjectAssets() { // for server
        JSONObject obj = new JSONObject();

//        {"JRD":[{"TYPE":"AAA","TYPENO":"123","CODE":"1","NAME":"TEST 1","MAINMNG":" MNG1","DEPARTMENT":"DEPT1","SECTION":"SECT1","AREANAME":"LOC1","TRDATE":"13/07/2020","QTY":"1"},{"TYPE":"BBB","TYPENO":"124","CODE":"2","NAME":"TEST 2","MAINMNG":" MNG2","DEPARTMENT":"DEPT2","SECTION":"SECT2","AREANAME":"LOC2","TRDATE":"13/07/2020","QTY":"2"}]}



        try {
            obj.put("TYPE", assesstType);
            obj.put("TYPENO", assesstNo);
            obj.put("CODE", assesstCode);
            obj.put("NAME", assesstName);
            obj.put("MAINMNG", assesstMangment);
            obj.put("DEPARTMENT",assesstDEPARTMENT );
            obj.put("SECTION", assesstSECTION);
            obj.put("AREANAME", assesstAREANAME);
            obj.put("TRDATE", assesstDate);
            obj.put("QTY", assesstQty);
            obj.put("ASSETBARCODE", assesstBarcode);


        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }
}
