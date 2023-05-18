package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.ItemInfo;
import com.example.user54.InventoryApp.Model.ItemInfoBackUp;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDaoItemInfoBackUp {
    @Query("SELECT * FROM ITEMS_INFO_BACKUP")
    public List<ItemInfoBackUp> getAll();

//    @Query("SELECT  * FROM ITEMS_INFO_BACKUP  where ITEM_LOCATION like :loc")
//    List<ItemInfo> loadAllByLoc(String loc);


//    @Query("SELECT ITEM_CODE, ITEM_NAME , SALES_PRICE , SUM(ITEM_QTY) ITEM_QTY FROM  ITEMS_INFO_BACKUP  GROUP BY   ITEM_CODE")
//    List<ItemInfo> getWithOutLoc();
////
//    @Query("SELECT ITEM_CODE, ITEM_NAME , SALES_PRICE , SUM(ITEM_QTY) ITEM_QTY FROM  ITEMS_INFO where ITEM_LOCATION like :loc GROUP BY   ITEM_CODE ")
//    List<ItemInfo> getWithLoc(String loc);





    @Query("SELECT  ITEM_LOCATION FROM ITEMS_INFO_BACKUP  Group by ITEM_LOCATION ")
    List<String> getAllItemLocation();

   // @Insert
   @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ItemInfoBackUp> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(ItemInfo... users);

    @Query("DELETE FROM ITEMS_INFO_BACKUP")
    void deleteAll();

    @Query("DELETE FROM ITEMS_INFO_BACKUP  where ITEM_CODE = :itemCode and SERIAL_NO = :SERIALNo")
    void deleteByItemCdeNo(String itemCode, String SERIALNo);

    @Query(" DELETE FROM ITEMS_INFO_BACKUP where  ITEM_LOCATION= :loc")
    void deleteByLoc(String loc);

    @Query("UPDATE ITEMS_INFO_BACKUP SET IS_EXPORT = '1' ")
    void updateExport();
    @Query("UPDATE ITEMS_INFO_BACKUP SET IS_DELETE = '1' ")
    void updateIsDelete();

    @Query("UPDATE ITEMS_INFO_BACKUP SET IS_DELETE = '1' where SERIAL_NO like :SERIAL_NO and ITEM_CODE like :ITEM_CODE")
    void updateIsDeleteWhere(String SERIAL_NO,String ITEM_CODE);
}
