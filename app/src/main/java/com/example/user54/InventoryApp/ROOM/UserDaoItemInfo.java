package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.ItemCard;
import com.example.user54.InventoryApp.Model.ItemInfo;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDaoItemInfo {
    @Query("SELECT * FROM ITEMS_INFO")
    public List<ItemInfo> getAll();

    @Query("SELECT  * FROM ITEMS_INFO  where ITEM_LOCATION like :loc")
    List<ItemInfo> loadAllByLoc(String loc);


    @Query("SELECT * , SUM(ITEM_QTY) ITEM_QTY FROM  ITEMS_INFO  GROUP BY   ITEM_CODE")
    List<ItemInfo> getWithOutLoc();
//
    @Query("SELECT *, SUM(ITEM_QTY) ITEM_QTY FROM  ITEMS_INFO where ITEM_LOCATION like :loc GROUP BY   ITEM_CODE ")
    List<ItemInfo> getWithLoc(String loc);





    @Query("SELECT  ITEM_LOCATION FROM ITEMS_INFO  Group by ITEM_LOCATION ")
    List<String> getAllItemLocation();

   // @Insert
   @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ItemInfo> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(ItemInfo... users);

    @Query("DELETE FROM ITEMS_INFO")
    void deleteAll();

    @Query("DELETE FROM ITEMS_INFO  where ITEM_CODE = :itemCode and SERIAL_NO = :SERIALNo")
    void deleteByItemCdeNo(String itemCode, String SERIALNo);

    @Query(" DELETE FROM ITEMS_INFO where  ITEM_LOCATION= :loc")
    void deleteByLoc(String loc);

    @Query("UPDATE ITEMS_INFO SET IS_EXPORT = '1' ")
    void updateExport();

    @Query("UPDATE ITEMS_INFO SET ITEM_QTY = :newValue where SERIAL_NO = :serial and ITEM_CODE = :code")
    void updateTrance(String newValue,String serial,String code);



    @Query(" SELECT COALESCE(sum(ITEM_QTY),0)  FROM  ITEMS_INFO  WHERE ITEM_CODE = :ItemCode ")
    String getTotal(String ItemCode);

}
