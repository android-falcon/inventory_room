package com.example.user54.InventoryApp.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "STK")
public class Stk {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "STK_NO")
    private String StkNo;
    @ColumnInfo(name = "STK_NAME")
    private  String StkName;

    public Stk() {

    }

    public Stk(String stkNo, String stkName) {
        StkNo = stkNo;
        StkName = stkName;
    }

    public String getStkNo() {
        return StkNo;
    }

    public void setStkNo(String stkNo) {
        StkNo = stkNo;
    }

    public String getStkName() {
        return StkName;
    }

    public void setStkName(String stkName) {
        StkName = stkName;
    }
}
