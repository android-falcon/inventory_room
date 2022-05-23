package com.example.user54.InventoryApp.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "ITEM_UNITS")
public class ItemUnit {

    @ColumnInfo(name = "ITEM_O_CODE")
    private String itemOCode;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ITEM_BARCODE")
    private String itemBarcode ;
    @ColumnInfo(name = "SALE_PRICE")
    private float salePrice;
    @ColumnInfo(name = "ITEM_U")
    private String itemU;
    @ColumnInfo(name = "U_QTY")
    private float UQty;
    @ColumnInfo(name = "U_SERIAL5")
    public int uSerial;
    @ColumnInfo(name="CALC_QTY")
    private float calcQty;
    @ColumnInfo(name = "WHOLE_SALE_PRC")
    private float wholeSalePrc;
    @ColumnInfo(name = "PURCHASE_PRICE")
    private float purchasePrc;
    @ColumnInfo(name = "PCLASS1")
    private float pclAss1;
    @ColumnInfo(name = "PCLASS2")
    private float pclAss2;
    @ColumnInfo(name = "PCLASS3")
    private float pclAss3;
    @ColumnInfo(name = "IN_DATE")
    private String inDate;
    @ColumnInfo(name = "UNIT_NAME")
    private String unitName;

    @ColumnInfo(name = "ORG_SALE_PRICE")
    private  String orgSalePrice ;
    @ColumnInfo(name = "OLD_SALE_PRICE")
    private  String oldSalePrice;
    @ColumnInfo(name = "UPDATE_DATE")
    private  String updateDate ;


    public ItemUnit() {
    }

    public ItemUnit(String itemOCode, String itemBarcode, float salePrice, String itemU, float UQty, int uSerial, float calcQty, float wholeSalePrc, float purchasePrc, float pclAss1, float pclAss2, float pclAss3, String inDate, String unitName, String orgSalePrice, String oldSalePrice, String updateDate) {
        this.itemOCode = itemOCode;
        this.itemBarcode = itemBarcode;
        this.salePrice = salePrice;
        this.itemU = itemU;
        this.UQty = UQty;
        this.uSerial = uSerial;
        this.calcQty = calcQty;
        this.wholeSalePrc = wholeSalePrc;
        this.purchasePrc = purchasePrc;
        this.pclAss1 = pclAss1;
        this.pclAss2 = pclAss2;
        this.pclAss3 = pclAss3;
        this.inDate = inDate;
        this.unitName = unitName;
        this.orgSalePrice = orgSalePrice;
        this.oldSalePrice = oldSalePrice;
        this.updateDate = updateDate;
    }

    public void setItemOCode(String itemOCode) {
        this.itemOCode = itemOCode;
    }

    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public void setItemU(String itemU) {
        this.itemU = itemU;
    }

    public void setUQty(float UQty) {
        this.UQty = UQty;
    }

    public void setUSerial(int uesrIal) {
        this.uSerial = uesrIal;
    }

    public void setCalcQty(float calcQty) {
        this.calcQty = calcQty;
    }

    public void setWholeSalePrc(float wholeSalePrc) {
        this.wholeSalePrc = wholeSalePrc;
    }

    public void setPurchasePrc(float purchasePrc) {
        this.purchasePrc = purchasePrc;
    }

    public void setPclAss1(float pclAss1) {
        this.pclAss1 = pclAss1;
    }

    public void setPclAss2(float pclAss2) {
        this.pclAss2 = pclAss2;
    }

    public void setPclAss3(float pclAss3) {
        this.pclAss3 = pclAss3;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setuSerial(int uSerial) {
        this.uSerial = uSerial;
    }

    public void setOrgSalePrice(String orgSalePrice) {
        this.orgSalePrice = orgSalePrice;
    }

    public void setOldSalePrice(String oldSalePrice) {
        this.oldSalePrice = oldSalePrice;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
    //_________________________________________________________________________

    public String getItemOCode() {
        return itemOCode;
    }

    public String getItemBarcode() {
        return itemBarcode;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public String getItemU() {
        return itemU;
    }

    public float getUQty() {
        return UQty;
    }

    public int getuSerial() {
        return uSerial;
    }

    public float getCalcQty() {
        return calcQty;
    }

    public float getWholeSalePrc() {
        return wholeSalePrc;
    }

    public float getPurchasePrc() {
        return purchasePrc;
    }

    public float getPclAss1() {
        return pclAss1;
    }

    public float getPclAss2() {
        return pclAss2;
    }

    public float getPclAss3() {
        return pclAss3;
    }

    public String getInDate() {
        return inDate;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getOrgSalePrice() {
        return orgSalePrice;
    }

    public String getOldSalePrice() {
        return oldSalePrice;
    }

    public String getUpdateDate() {
        return updateDate;
    }

}
