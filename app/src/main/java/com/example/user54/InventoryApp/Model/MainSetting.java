package com.example.user54.InventoryApp.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName =  "MAIN_SETTING_TABLE")
public class MainSetting {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "IP_SETTING")
    private String IP;
    @ColumnInfo(name = "STORNO")
    private String StorNo;
    @ColumnInfo(name = "IS_ASSEST")
    private String isAssest;
    @ColumnInfo(name = "IS_QRJARD")
    private String isQr;
    @ColumnInfo(name = "ONLINE_PRICE")
    private String onlinePrice;
    @ColumnInfo(name = "COMPANY_NO")
    private String companyNo;
    @ColumnInfo(name = "PRINTER_TYPE")
    private String printerType;
    @ColumnInfo(name = "CURRENCY_TYPE")
    private String currencyType;
    @ColumnInfo(name = "CO_NAME")
    private int CoName;
    @ColumnInfo(name = "NUMBER_TYPE")
    private String numberType;
    @ColumnInfo(name = "ROTATE")
    private String rotate;
    @ColumnInfo(name = "RESIZE")
    private int reSize;
    @ColumnInfo(name = "SHELF_TAG_WIDTH")
    private int width;
    @ColumnInfo(name = "SHELF_TAG_HEIGHT")
    private int height;
    @ColumnInfo(name = "DATABASE_NO")
    private int dataBaseNo;
    @ColumnInfo(name = "ROOM_DATABASE_NO")
    private int dataBaseNoRoom;
    @ColumnInfo(name = "IS_QTY_MINUS")
    private int isMinus;
    @ColumnInfo(name = "ONlINEshlf")
    private String ONlINEshlf;

    public String getONlINEshlf() {
        return ONlINEshlf;
    }

    public void setONlINEshlf(String ONlINEshlf) {
        this.ONlINEshlf = ONlINEshlf;
    }

    public MainSetting() {
    }

    public MainSetting(String IP, String storNo, String isAssest, String isQr) {
        this.IP = IP;
        this.StorNo = storNo;
        this.isAssest = isAssest;
        this.isQr = isQr;
    }

    public MainSetting(String IP, String storNo, String isAssest, String isQr, String onlinePrice, String companyNo,String printerType,String currencyType,int CoName,String numberType,String rotate,int dataBaseNo,int reSize,int width,int height,int isMinus,String onlinShl) {
        this.IP = IP;
        this.StorNo = storNo;
        this.isAssest = isAssest;
        this.isQr = isQr;
        this.onlinePrice = onlinePrice;
        this.companyNo = companyNo;
        this.printerType = printerType;
        this.currencyType=currencyType;
        this.CoName=CoName;
        this.numberType=numberType;
        this.rotate=rotate;
        this.reSize=reSize;
        this.width=width;
        this.height=height;
        this.dataBaseNo=dataBaseNo;
        this.isMinus=isMinus;
        this.ONlINEshlf= onlinShl;
    }

    public String getPrinterType() {
        return printerType;
    }

    public void setPrinterType(String printerType) {
        this.printerType = printerType;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getStorNo() {
        return StorNo;
    }

    public void setStorNo(String storNo) {
        StorNo = storNo;
    }

    public String getIsAssest() {
        return isAssest;
    }

    public void setIsAssest(String isAssest) {
        this.isAssest = isAssest;
    }

    public String getIsQr() {
        return isQr;
    }

    public void setIsQr(String isQr) {
        this.isQr = isQr;
    }

    public String getOnlinePrice() {
        return onlinePrice;
    }

    public void setOnlinePrice(String onlinePrice) {
        this.onlinePrice = onlinePrice;
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public int getCoName() {
        return CoName;
    }

    public void setCoName(int coName) {
        CoName = coName;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }

    public String getRotate() {
        return rotate;
    }

    public void setRotate(String rotate) {
        this.rotate = rotate;
    }


    public int getDataBaseNo() {
        return dataBaseNo;
    }

    public void setDataBaseNo(int dataBaseNo) {
        this.dataBaseNo = dataBaseNo;
    }

    public int getDataBaseNoRoom() {
        return dataBaseNoRoom;
    }

    public int getReSize() {
        return reSize;
    }

    public void setReSize(int reSize) {
        this.reSize = reSize;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDataBaseNoRoom(int dataBaseNoRoom) {
        this.dataBaseNoRoom = dataBaseNoRoom;
    }

    public int getIsMinus() {
        return isMinus;
    }

    public void setIsMinus(int isMinus) {
        this.isMinus = isMinus;
    }
}
