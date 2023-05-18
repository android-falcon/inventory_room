package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.ItemInfoExpRec;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDaoInfoExpRec {
    @Query("SELECT * FROM ITEM_INFO_EXP_REC")
    public List<ItemInfoExpRec> getAll();

    @Query("SELECT * FROM ITEM_INFO_EXP_REC ")
    List<ItemInfoExpRec> loadAllByIds();

//    @Query("SELECT * FROM ITEM_CARD where IS_NEW ='1' ")
//    List<ItemCard> loadNewItem();
//
//    @Query("SELECT  * FROM  ITEM_CARD  where ITEM_CODE LIKE :itemCode LIMIT 1")
//     ItemCard findItemCardByCode(String itemCode);
//
//    @Query("SELECT  * FROM  ITEM_CARD where IS_EXPORT = '0' and  IS_NEW = '1' and (IN_DATE  is NULL or  IN_DATE ='') ")
//    List<ItemCard> getAllNotExportedItem();

   // @Insert
   @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ItemInfoExpRec> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(ItemInfoExpRec... users);

    @Query("DELETE FROM ITEM_INFO_EXP_REC")
    void deleteAll();

    @Query("UPDATE ITEM_INFO_EXP_REC SET ITEM_QTY = :newValue where SERIAL_NO = :serial and ITEM_CODE = :code")
    void updateTrance(String newValue,String serial,String code);

    @Query(" SELECT COALESCE(sum(ITEM_QTY),0)  FROM  ITEM_INFO_EXP_REC  WHERE ITEM_CODE = :ItemCode ")
    String getTotal(String ItemCode);


//    @Query("DELETE FROM ITEM_CARD where ITEM_CODE like :itemCode and ITEM_NAME like :ItemName")
//    void deleteByItemCdeName(String itemCode, String ItemName);

//    @Query("UPDATE ITEM_CARD SET IS_EXPORT = '1' ")
//    void updateExport();

}
