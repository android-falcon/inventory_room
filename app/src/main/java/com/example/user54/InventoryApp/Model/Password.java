package com.example.user54.InventoryApp.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PASSWORD")
public class Password {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")

    private int id ;
    @ColumnInfo(name = "PASSWORD")
    private String passwords;

    public Password() {

    }

    public Password(String passwords) {
        this.passwords = passwords;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
