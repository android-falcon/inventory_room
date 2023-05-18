package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.ItemSwitch;
import com.example.user54.InventoryApp.Model.ItemUnit;

import java.util.List;

@Dao
public interface UserDaoUnit {

    @Query("SELECT * FROM ITEM_UNITS")
    List<ItemUnit> getAll();

    @Query("SELECT * FROM ITEM_UNITS ")
    List<ItemUnit> loadAllByIds();

    @Query("SELECT IFNULL (MAX(datetime(substr(IN_DATE, 7, 4) || '-' || substr(IN_DATE, 4, 2) || '-' || substr(IN_DATE, 1, 2))),'2020-03-05') FROM ITEM_UNITS")
    String getMaxInDate();


//    @Query("SELECT * FROM UserModel WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    UserModel findByName(String first, String last);
//ITEM_O_CODE , SALE_PRICE , CALC_QTY , ITEM_U
    @Query("SELECT *  FROM  ITEM_UNITS  where ITEM_BARCODE like :ITEM_BARCODE")
    List<ItemUnit> findUnite(String ITEM_BARCODE);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  //  @Insert
    void insertAll(List<ItemUnit> users);

    @Delete
    void delete(ItemUnit user);

    @Query("DELETE FROM ITEM_UNITS")
    void deleteAll();

}
