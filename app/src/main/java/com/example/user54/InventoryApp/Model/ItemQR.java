package com.example.user54.InventoryApp.Model;

public class ItemQR {
    private String storeNo;
    private String itemCode;
    private String itemNmae;
    private String salesPrice;
    private String qrCode;
    private String lotNo;

    public ItemQR() {

    }

    public ItemQR(String storeNo, String itemCode, String itemNmae, String salesPrice, String qrCode, String lotNo) {
        this.storeNo = storeNo;
        this.itemCode = itemCode;
        this.itemNmae = itemNmae;
        this.salesPrice = salesPrice;
        this.qrCode = qrCode;
        this.lotNo = lotNo;
    }


    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemNmae() {
        return itemNmae;
    }

    public void setItemNmae(String itemNmae) {
        this.itemNmae = itemNmae;
    }

    public String getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(String salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }
}
