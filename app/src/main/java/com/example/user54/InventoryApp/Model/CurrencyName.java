package com.example.user54.InventoryApp.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CURRENCY_TABLE")
public class CurrencyName {
   @ColumnInfo(name ="CURRENCY_NAME" )
    private String CURRENCY_NAME;
   @NonNull
   @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name = "ID")
   private int id;

    public CurrencyName(String CURRENCY_NAME) {
        this.CURRENCY_NAME = CURRENCY_NAME;

    }

    public String getCURRENCY_NAME() {
        return CURRENCY_NAME;
    }

    public void setCURRENCY_NAME(String CURRENCY_NAME) {
        this.CURRENCY_NAME = CURRENCY_NAME;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
