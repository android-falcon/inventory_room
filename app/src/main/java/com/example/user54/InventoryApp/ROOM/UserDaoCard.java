package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.ItemCard;

import java.util.List;

@Dao
public interface UserDaoCard {
    @Query("SELECT * FROM ITEM_CARD")
    List<ItemCard> getAll();

    @Query("SELECT * FROM ITEM_CARD ")
    List<ItemCard> loadAllByIds();

//    @Query("SELECT * FROM UserModel WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    UserModel findByName(String first, String last);

   // @Insert
   @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ItemCard> users);

    @Delete
    void delete(ItemCard user);

    @Query("DELETE  FROM ITEM_CARD")
    void deleteAll();

}
