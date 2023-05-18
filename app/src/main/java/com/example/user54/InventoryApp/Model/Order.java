package com.example.user54.InventoryApp.Model;
// private static final String ORDERS = "ORDERS";
//
//    private static final String STK_NO6 = "STK_NO";
//    private static final String LOAD_DATE6 = "LOAD_DATE";
//    private static final String LOAD_QTY6 = "LOAD_QTY";
//    private static final String NET_QTY6 = "NET_QTY";
//    private static final String ITEM_BARCODE6 = "ITEM_BARCODE";
//    private static final String ITEM_CODE6 = "ITEM_CODE";

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ORDERS")
public class Order  {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int id ;
    @ColumnInfo(name = "STK_NO")
    private int stockNo;
    @ColumnInfo(name = "LOAD_DATE")
    private String loadDate;
    @ColumnInfo(name = "LOAD_QTY")
    private float loadQty;
    @ColumnInfo(name = "NET_QTY")
    private float netQty ;
    @ColumnInfo(name = "ITEM_BARCODE")
    private String itemBarcode ;
    @ColumnInfo(name = "ITEM_CODE")
    private String itemCode ;

    public Order() {
    }

    public Order(int stockNo, String loadDate, float loadQty,
                 float netQty, String itemBarcode, String itemCode) {
        this.stockNo = stockNo;
        this.loadDate = loadDate;
        this.loadQty = loadQty;
        this.netQty = netQty;
        this.itemBarcode = itemBarcode;
        this.itemCode = itemCode;
    }

    public void setStockNo(int stockNo) {
        this.stockNo = stockNo;
    }

    public void setLoadDate(String loadDate) {
        this.loadDate = loadDate;
    }

    public void setLoadQty(float loadQty) {
        this.loadQty = loadQty;
    }

    public void setNetQty(float netQty) {
        this.netQty = netQty;
    }

    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    //_______________________________________________________________________
    public int getStockNo() {
        return stockNo;
    }

    public String getLoadDate() {
        return loadDate;
    }

    public float getLoadQty() {
        return loadQty;
    }

    public float getNetQty() {
        return netQty;
    }

    public String getItemBarcode() {
        return itemBarcode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
