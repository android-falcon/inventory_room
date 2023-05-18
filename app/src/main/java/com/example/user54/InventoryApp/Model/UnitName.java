package com.example.user54.InventoryApp.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ITEM_UNIT")

public class UnitName {
    @ColumnInfo(name ="UNITE_NAME" )
    public String itemUnitN;


    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    public UnitName(String itemUnitN, int id) {
        this.itemUnitN = itemUnitN;
        this.id = id;
    }

    public UnitName(String itemUnitN) {
        this.itemUnitN = itemUnitN;
    }

    public UnitName() {
    }

    public String getItemUnitN() {
        return itemUnitN;
    }

    public void setItemUnitN(String itemUnitN) {
        this.itemUnitN = itemUnitN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
