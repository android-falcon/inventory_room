package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.ItemSwitch;

import java.util.List;

@Dao
public interface UserDaoSwitch {
    @Query("SELECT * FROM ITEM_SWITCH")
    List<ItemSwitch> getAll();

    @Query("SELECT * FROM ITEM_SWITCH ")
    List<ItemSwitch> loadAllByIds();

    @Query("SELECT ITEM_O_CODE FROM  ITEM_SWITCH where ITEM_N_CODE = :itemNcode ")
    String findByCodeN(String itemNcode);

    @Query("SELECT IFNULL (MAX(datetime(substr(IN_DATE, 7, 4) || '-' || substr(IN_DATE, 4, 2) || '-' || substr(IN_DATE, 1, 2))),'2020-03-05') FROM ITEM_SWITCH")
    String getMaxInDate();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  //  @Insert
    void insertAll(List<ItemSwitch> users);

    @Delete
    void delete(ItemSwitch user);

    @Query("DELETE FROM ITEM_SWITCH")
    void deleteAll();

}
