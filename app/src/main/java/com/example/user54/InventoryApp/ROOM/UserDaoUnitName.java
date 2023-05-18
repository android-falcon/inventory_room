package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.ItemQR;
import com.example.user54.InventoryApp.Model.UnitName;

import java.util.List;

@Dao
public interface UserDaoUnitName {

    @Query("SELECT * FROM ITEM_UNIT")
    List<UnitName> getAll();

    @Query("SELECT * FROM ITEM_UNIT ")
    List<UnitName> loadAllByIds();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  //  @Insert
    void insertAll(List<UnitName> users);

    @Delete
    void delete(UnitName user);

    @Query("DELETE FROM ITEM_UNIT")
    void deleteAll();

}
