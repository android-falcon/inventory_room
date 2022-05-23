package com.example.user54.InventoryApp.Model;

public class MainSetting {

    private String IP;
    private String StorNo;
    private String isAssest;
    private String isQr;
    private String onlinePrice;
    private String companyNo;
    private String printerType;
    private String currencyType;
    private int CoName;
    public MainSetting() {
    }

    public MainSetting(String IP, String storNo, String isAssest, String isQr) {
        this.IP = IP;
        this.StorNo = storNo;
        this.isAssest = isAssest;
        this.isQr = isQr;
    }

    public MainSetting(String IP, String storNo, String isAssest, String isQr, String onlinePrice, String companyNo,String printerType,String currencyType,int CoName) {
        this.IP = IP;
        this.StorNo = storNo;
        this.isAssest = isAssest;
        this.isQr = isQr;
        this.onlinePrice = onlinePrice;
        this.companyNo = companyNo;
        this.printerType = printerType;
        this.currencyType=currencyType;
        this.CoName=CoName;
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
}
