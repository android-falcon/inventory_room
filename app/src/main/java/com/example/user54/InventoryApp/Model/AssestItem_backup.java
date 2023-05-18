package com.example.user54.InventoryApp.Model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "ASSEST_TABLE_INFO_BACKUP")
public class AssestItem_backup {

//    private static final String ASSEST_TABLE_INFO_BACKUP = "ASSEST_TABLE_INFO_BACKUP";
//
//    private static final String ASSESST_CODE_INFO_BACKUP = "ASSESST_CODE_BACKUP";
//    private static final String ASSESST_NAME_INFO_BACKUP= "ASSESST_NAME_BACKUP";
//    private static final String ASSESST_TYPE_INFO_BACKUP = "ASSESST_TYPE_BACKUP";
//    private static final String ASSESST_NO_INFO_BACKUP = "ASSESST_NO_BACKUP";
//    private static final String ASSESST_MAINMNG_INFO_BACKUP= "ASSESST_MAINMNG_BACKUP";
//    private static final String ASSESST_DEPARTMENT_INFO_BACKUP = "ASSESST_DEPARTMENT_BACKUP";
//    private static final String ASSESST_SECTION_INFO_BACKUP = "ASSESST_SECTION_BACKUP";
//    private static final String ASSESST_AREANAME_INFO_BACKUP = "ASSESST_AREANAME_BACKUP";
//    private static final String ASSESST_QTY_INFO_BACKUP = "ASSESST_QTY_INFO_BACKUP";
//    private static final String ASSESST_DATE_INFO_BACKUP = "ASSESST_DATE_INFO_BACKUP";
//    private static final String ASSESST_ISEXPORT_INFO_BACKUP = "ASSESST_ISEXPORT_INFO_BACKUP";
//    private static final String ASSESST_BARCODE_INFO_BACKUP = "ASSESST_BARCODE_INFO_BACKUP";
//    private static final String ASSESST_SERIAL_INFO_BACKUP = "ASSESST_SERIAL_INFO_BACKUP";


    @ColumnInfo(name = "ASSESST_CODE_BACKUP")
    private String assesstCode;
    @ColumnInfo(name = "ASSESST_NAME_BACKUP")
    private String assesstName;
    @ColumnInfo(name = "ASSESST_NO_BACKUP")
    private String assesstNo;
    @ColumnInfo(name = "ASSESST_TYPE_BACKUP")
    private String assesstType;
//    @ColumnInfo(name = "ASSESST_CODE")
    @Ignore
    private boolean isCheck;
//    @ColumnInfo(name = "ASSESST_CODE")
    @Ignore
    private String count;
//    @ColumnInfo(name = "ASSESST_CODE")
    private String price;
    @ColumnInfo(name = "ASSESST_QTY_INFO_BACKUP")
    private String assesstQty;
    @ColumnInfo(name = "ASSESST_DATE_INFO_BACKUP")
    private String assesstDate;
    @ColumnInfo(name = "ASSESST_MAINMNG_BACKUP")
    private String assesstMangment;
    @ColumnInfo(name = "ASSESST_DEPARTMENT_BACKUP")
    private String assesstDEPARTMENT;
    @ColumnInfo(name = "ASSESST_SECTION_BACKUP")
    private String assesstSECTION;
    @ColumnInfo(name = "ASSESST_AREANAME_BACKUP")
    private String assesstAREANAME;
    @ColumnInfo(name = "ASSESST_BARCODE")
    private String assesstBarcode;
    @ColumnInfo(name = "ASSESST_ISEXPORT_INFO_BACKUP")
    private String isExport;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "ASSESST_SERIAL_INFO_BACKUP")
    private String serial;


    public AssestItem_backup() {
    }

    public AssestItem_backup(String assesstCode, String assesstName, String assesstNo, String assesstType, boolean isCheck, String count, String price, String assesstQty, String assesstDate, String assesstMangment, String assesstDEPARTMENT, String assesstSECTION, String assesstAREANAME, String assesstBarcode, String isExport) {
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
