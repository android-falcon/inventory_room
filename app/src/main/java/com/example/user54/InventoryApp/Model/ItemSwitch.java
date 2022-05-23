package com.example.user54.InventoryApp.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ITEM_SWITCH")

public class ItemSwitch {

    // private static final String ITEM_SWITCH = "ITEM_SWITCH";
    //
    //    private static final String ITEM_O_CODE = "ITEM_O_CODE";
    //    private static final String ITEM_N_CODE = "ITEM_N_CODE";
    //    private static final String ITEM_NAME_A = "ITEM_NAME_A";
    //    private static final String ITEM_NAME_E = "ITEM_NAME_E";
    //    private static final String IN_DATE4 = "IN_DATE";


    @ColumnInfo(name = "ITEM_O_CODE")
    private String itemOCode;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ITEM_N_CODE")
    private String itemNCode;
    @ColumnInfo(name = "ITEM_NAME_A")
    private String itemNameA;
    @ColumnInfo(name = "ITEM_NAME_E")
    private String itemNameE;
    @ColumnInfo(name = "IN_DATE")
    private String inDate;

    public ItemSwitch() {

    }

    public ItemSwitch(String itemOCode, String itemNCode, String itemNameA, String itemNameE, String inDate) {
        this.itemOCode = itemOCode;
        this.itemNCode = itemNCode;
        this.itemNameA = itemNameA;
        this.itemNameE = itemNameE;
        this.inDate = inDate;
    }

    public void setItemOCode(String itemOCode) {
        this.itemOCode = itemOCode;
    }

    public void setItemNCode(String itemNCode) {
        this.itemNCode = itemNCode;
    }

    public void setItemNameA(String itemNameA) {
        this.itemNameA = itemNameA;
    }

    public void setItemNameE(String itemNameE) {
        this.itemNameE = itemNameE;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }
    //__________________________________________________________________________________

    public String getItemOCode() {
        return itemOCode;
    }

    public String getItemNCode() {
        return itemNCode;
    }

    public String getItemNameA() {
        return itemNameA;
    }

    public String getItemNameE() {
        return itemNameE;
    }

    public String getInDate() {
        return inDate;
    }
}
