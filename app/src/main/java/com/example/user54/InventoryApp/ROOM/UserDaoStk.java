package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.SewooSetting;
import com.example.user54.InventoryApp.Model.Stk;

import java.util.List;

@Dao
public interface UserDaoStk {

    @Query("SELECT * FROM STK")
    List<Stk> getAll();

    @Query("SELECT  STK_NAME FROM   STK  where STK_NO like :STORNo ")
    String getStkName(String STORNo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  //  @Insert
    void insertAll(List<Stk> users);

    @Delete
    void delete(Stk user);

    @Query("DELETE FROM STK")
    void deleteAll();

}
