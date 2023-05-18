package com.example.user54.InventoryApp.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ACTIVATE_TABLE")
public class ActiveTable {
   @ColumnInfo(name ="ACTIVATE" )
    private String Active;
   @NonNull
   @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name = "ID")
   private int id;

    public ActiveTable() {
    }

    public ActiveTable(String active, int id) {
        Active = active;
        this.id = id;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
