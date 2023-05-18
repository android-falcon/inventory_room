package com.example.user54.InventoryApp.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ITEMS_QTY")
public class ItemQty {

    //private static final String ITEMS_QTY = "ITEMS_QTY";
    //
    //    private static final String ITEM_CODE3 = "ITEM_CODE";
    //    private static final String ITEM_NAME3 = "ITEM_NAME";
    //    private static final String QTY3 = "QTY";
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "ITEM_CODE")
    private String itemCode;
    @ColumnInfo(name = "ITEM_NAME")
    private String itemName;
    @ColumnInfo(name = "QTY")
    private String Qty;
    private String f_d;

    public String getF_d() {
        return f_d;
    }

    public void setF_d(String f_d) {
        this.f_d = f_d;
    }

    public ItemQty() {

    }

    public ItemQty(String itemCode, String itemName, String qty) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.Qty = qty;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    //_______________________________________________________________________

    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public String getQty() {
        return Qty;
    }
}
