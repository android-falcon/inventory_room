package com.example.user54.InventoryApp.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ITEM_QR_CODE_TABLE")
public class ItemQR {
    @ColumnInfo(name = "STR_NO")
    private String storeNo;
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "ITEM_CODE")
    private String itemCode;
    @ColumnInfo(name = "ITEM_NAME")
    private String itemNmae;
    @ColumnInfo(name = "PRICE")
    private String salesPrice;
    @ColumnInfo(name = "QR_CODE")
    private String qrCode;
    @ColumnInfo(name = "LOT_NUMBER")
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
