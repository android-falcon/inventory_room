package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.user54.InventoryApp.Model.ItemCard;

import java.util.List;

@Dao
public interface UserDaoCard {
    @Query("SELECT * FROM ITEM_CARD")
    public List<ItemCard> getAll();

    @Query("SELECT * FROM ITEM_CARD ")
    List<ItemCard> loadAllByIds();

    @Query("SELECT * FROM ITEM_CARD where IS_NEW ='1' ")
    List<ItemCard> loadNewItem();

//    @Query("SELECT * FROM UserModel WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    UserModel findByName(String first, String last);

   // @Insert
   @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ItemCard> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(ItemCard... users);

    @Query("DELETE FROM ITEM_CARD")
    void deleteAll();

}
