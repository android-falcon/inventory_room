package com.example.user54.InventoryApp.ROOM;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.user54.InventoryApp.Model.ItemCard;
import com.example.user54.InventoryApp.Model.ItemSwitch;
import com.example.user54.InventoryApp.Model.ItemUnit;

@Database(entities = {ItemCard.class, ItemSwitch.class, ItemUnit.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDaoCard itemCard();
    public abstract UserDaoSwitch itemSwitch();
    public abstract UserDaoUnit itemUnit();
}

