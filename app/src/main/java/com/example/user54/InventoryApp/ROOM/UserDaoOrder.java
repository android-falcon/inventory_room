package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.ItemUnit;
import com.example.user54.InventoryApp.Model.Order;

import java.util.List;

@Dao
public interface UserDaoOrder {

    @Query("SELECT * FROM ORDERS")
    List<Order> getAll();

    @Query("SELECT * FROM ORDERS ")
    List<Order> loadAllByIds();

//    @Query("SELECT * FROM UserModel WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    UserModel findByName(String first, String last);

//    @Query("SELECT ITEM_O_CODE , SALE_PRICE , CALC_QTY , ITEM_U  FROM  ITEM_UNITS  where ITEM_BARCODE like :ITEM_BARCODE")
//    List<String> findUnite(String ITEM_BARCODE);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  //  @Insert
    void insertAll(List<Order> users);

    @Delete
    void delete(Order user);

    @Query("DELETE FROM ORDERS")
    void deleteAll();

}
