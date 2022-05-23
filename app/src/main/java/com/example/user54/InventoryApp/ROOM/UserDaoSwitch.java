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

//    @Query("SELECT * FROM UserModel WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    UserModel findByName(String first, String last);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  //  @Insert
    void insertAll(List<ItemSwitch> users);

    @Delete
    void delete(ItemSwitch user);

    @Query("DELETE FROM ITEM_SWITCH")
    void deleteAll();

}
