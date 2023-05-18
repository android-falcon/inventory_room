package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.Order;
import com.example.user54.InventoryApp.Model.Password;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDaoPassword {

    @Query("SELECT * FROM PASSWORD")
    List<Password> getAll();

    @Query("SELECT * FROM PASSWORD ")
    List<Password> loadAllByIds();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  //  @Insert
    void insertAll(List<Password> users);

    @Delete
    void delete(Password user);

    @Query("DELETE FROM PASSWORD")
    void deleteAll();

    @Query("UPDATE PASSWORD SET PASSWORD = :newValue where PASSWORD like :oldPass")
    void updatePassword(String newValue,String oldPass);

}
