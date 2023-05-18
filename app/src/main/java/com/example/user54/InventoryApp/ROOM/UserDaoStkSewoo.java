package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.ItemUnit;
import com.example.user54.InventoryApp.Model.SewooSetting;

import java.util.List;

@Dao
public interface UserDaoStkSewoo {

    @Query("SELECT * FROM SEWOO_SETTING")
    List<SewooSetting> getAll();

    @Query("SELECT * FROM SEWOO_SETTING ")
    List<SewooSetting> loadAllByIds();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  //  @Insert
    void insertAll(List<SewooSetting> users);

    @Delete
    void delete(SewooSetting user);

    @Query("DELETE FROM SEWOO_SETTING")
    void deleteAll();

}
