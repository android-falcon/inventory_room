package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.ActiveTable;
import com.example.user54.InventoryApp.Model.ItemQR;

import java.util.List;

@Dao
public interface UserDaoActivity {

    @Query("SELECT * FROM ACTIVATE_TABLE")
    List<ActiveTable> getAll();

    @Query("SELECT * FROM ACTIVATE_TABLE ")
    List<ActiveTable> loadAllByIds();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  //  @Insert
    void insertAll(List<ActiveTable> users);

    @Delete
    void delete(ActiveTable user);

    @Query("DELETE FROM ACTIVATE_TABLE")
    void deleteAll();

}
