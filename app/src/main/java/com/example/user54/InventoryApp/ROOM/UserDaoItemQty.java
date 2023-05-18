package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.ItemQty;
import com.example.user54.InventoryApp.Model.ItemUnit;

import java.util.List;

@Dao
public interface UserDaoItemQty {

    @Query("SELECT * FROM ITEMS_QTY")
    List<ItemQty> getAll();

    @Query("SELECT * FROM ITEMS_QTY ")
    List<ItemQty> loadAllByIds();

//    @Query("SELECT * FROM UserModel WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    UserModel findByName(String first, String last);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  //  @Insert
    void insertAll(List<ItemQty> users);

    @Delete
    void delete(ItemQty user);

    @Query("DELETE FROM ITEMS_QTY")
    void deleteAll();

}
