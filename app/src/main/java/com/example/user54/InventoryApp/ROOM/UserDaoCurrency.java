package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.CurrencyName;
import com.example.user54.InventoryApp.Model.ItemQR;

import java.util.List;

@Dao
public interface UserDaoCurrency {

    @Query("SELECT CURRENCY_NAME FROM CURRENCY_TABLE")
    List<String> getAll();

    @Query("SELECT * FROM CURRENCY_TABLE ")
    List<CurrencyName> loadAllByIds();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  //  @Insert
    void insertAll(List<CurrencyName> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
        //  @Insert
    void insertCur(CurrencyName users);


    @Delete
    void delete(CurrencyName user);

    @Query("DELETE FROM CURRENCY_TABLE")
    void deleteAll();

}
