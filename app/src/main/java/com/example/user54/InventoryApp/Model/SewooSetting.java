package com.example.user54.InventoryApp.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SEWOO_SETTING")
public class SewooSetting {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "COMPORT")
    private String ComPort;
    @ColumnInfo(name = "BARCODE_X")
    private int barcodeX;
    @ColumnInfo(name = "BARCODE_Y")
    private int barcodeY;
    @ColumnInfo(name = "ITEM_X")
    private int itemX;
    @ColumnInfo(name = "ITEM_Y")
    private int itemY;
    @ColumnInfo(name = "ITEM_FONT_TYPE")
    private int itemFontType;
    @ColumnInfo(name = "ITEM_FONT_SIZE")
    private int itemFontSize;
    @ColumnInfo(name = "PRC_X")
    private int prcX;
    @ColumnInfo(name = "PRC_Y")
    private int prcY;
    @ColumnInfo(name = "PRC_FONT_TYPE")
    private int prcFontType;
    @ColumnInfo(name = "PRC_FONT_SIZE")
    private int prcFontSize;
    @ColumnInfo(name = "BARCODE_TYPE")
    private int barcodeType;
    @ColumnInfo(name = "BARCODE_BN")
    private int barcodeBn;
    @ColumnInfo(name = "COMP_NAME")
    private String CompName;
    @ColumnInfo(name = "COMP_NM_X")
    private String CompNMX;
    @ColumnInfo(name = "COMP_NM_Y")
    private String CompNMY;
    @ColumnInfo(name = "DEV_ID")
    private String devID;


    public SewooSetting() {
    }

    public SewooSetting(String comPort, int barcodeX, int barcodeY, int itemX, int itemY, int itemFontType,
                        int itemFontSize, int prcX, int prcY, int prcFontType, int prcFontSize,
                        int barcodeType, int barcodeBn, String compName, String compNMX, String compNMY, String devID) {
        this.ComPort = comPort;
        this.barcodeX = barcodeX;
        this.barcodeY = barcodeY;
        this.itemX = itemX;
        this.itemY = itemY;
        this.itemFontType = itemFontType;
        this.itemFontSize = itemFontSize;
        this.prcX = prcX;
        this.prcY = prcY;
        this.prcFontType = prcFontType;
        this.prcFontSize = prcFontSize;
        this.barcodeType = barcodeType;
        this.barcodeBn = barcodeBn;
        this.CompName = compName;
        this.CompNMX = compNMX;
        this.CompNMY = compNMY;
        this.devID = devID;
    }


    public void setComPort(String comPort) {
        ComPort = comPort;
    }

    public void setBarcodeX(int barcodeX) {
        this.barcodeX = barcodeX;
    }

    public void setBarcodeY(int barcodeY) {
        this.barcodeY = barcodeY;
    }

    public void setItemX(int itemX) {
        this.itemX = itemX;
    }

    public void setItemY(int itemY) {
        this.itemY = itemY;
    }

    public void setItemFontType(int itemFontType) {
        this.itemFontType = itemFontType;
    }

    public void setItemFontSize(int itemFontSize) {
        this.itemFontSize = itemFontSize;
    }

    public void setPrcX(int prcX) {
        this.prcX = prcX;
    }

    public void setPrcY(int prcY) {
        this.prcY = prcY;
    }

    public void setPrcFontType(int prcFontType) {
        this.prcFontType = prcFontType;
    }

    public void setPrcFontSize(int prcFontSize) {
        this.prcFontSize = prcFontSize;
    }

    public void setBarcodeType(int barcodeType) {
        this.barcodeType = barcodeType;
    }

    public void setBarcodeBn(int barcodeBn) {
        this.barcodeBn = barcodeBn;
    }

    public void setCompName(String compName) {
        CompName = compName;
    }

    public void setCompNMX(String compNMX) {
        CompNMX = compNMX;
    }

    public void setCompNMY(String compNMY) {
        CompNMY = compNMY;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    //________________________________________________________________________________________________________
    public String getComPort() {
        return ComPort;
    }

    public int getBarcodeX() {
        return barcodeX;
    }

    public int getBarcodeY() {
        return barcodeY;
    }

    public int getItemX() {
        return itemX;
    }

    public int getItemY() {
        return itemY;
    }

    public int getItemFontType() {
        return itemFontType;
    }

    public int getItemFontSize() {
        return itemFontSize;
    }

    public int getPrcX() {
        return prcX;
    }

    public int getPrcY() {
        return prcY;
    }

    public int getPrcFontType() {
        return prcFontType;
    }

    public int getPrcFontSize() {
        return prcFontSize;
    }

    public int getBarcodeType() {
        return barcodeType;
    }

    public int getBarcodeBn() {
        return barcodeBn;
    }

    public String getCompName() {
        return CompName;
    }

    public String getCompNMX() {
        return CompNMX;
    }

    public String getCompNMY() {
        return CompNMY;
    }

    public String getDevID() {
        return devID;
    }
}
