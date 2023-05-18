package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.ItemInfoExp;
import com.example.user54.InventoryApp.Model.ItemUnit;
import com.example.user54.InventoryApp.Model.MainSetting;

import java.util.List;

@Dao
public interface UserDaoMainSetting {

    @Query("SELECT * FROM MAIN_SETTING_TABLE")
    List<MainSetting> getAll();

    @Query("SELECT * FROM MAIN_SETTING_TABLE ")
    List<MainSetting> loadAllByIds();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  //  @Insert
    void insertAll(List<MainSetting> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(MainSetting... users);

    @Delete
    void delete(MainSetting user);

    @Query("DELETE FROM MAIN_SETTING_TABLE")
    void deleteAll();

}
