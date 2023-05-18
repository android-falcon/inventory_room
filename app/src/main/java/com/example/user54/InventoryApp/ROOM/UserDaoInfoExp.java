package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.ItemCard;
import com.example.user54.InventoryApp.Model.ItemInfoExp;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDaoInfoExp {
    @Query("SELECT * FROM ITEM_INFO_EXP")
    public List<ItemInfoExp> getAll();

    @Query("SELECT * FROM ITEM_INFO_EXP ")
    List<ItemInfoExp> loadAllByIds();

//    @Query("SELECT * FROM ITEM_CARD where IS_NEW ='1' ")
//    List<ItemCard> loadNewItem();
//
//    @Query("SELECT  * FROM  ITEM_CARD  where ITEM_CODE LIKE :itemCode LIMIT 1")
//     ItemCard findItemCardByCode(String itemCode);
//
//    @Query("SELECT  * FROM  ITEM_CARD where IS_EXPORT = '0' and  IS_NEW = '1' and (IN_DATE  is NULL or  IN_DATE ='') ")
//    List<ItemCard> getAllNotExportedItem();


    @Query("UPDATE ITEM_INFO_EXP SET ITEM_QTY = :newValue where SERIAL_NO = :serial and ITEM_CODE = :code")
    void updateTrance(String newValue,String serial,String code);

    @Query(" SELECT COALESCE(sum(ITEM_QTY),0)  FROM  ITEM_INFO_EXP  WHERE ITEM_CODE = :ItemCode ")
    String getTotal(String ItemCode);


    // @Insert
   @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ItemInfoExp> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(ItemInfoExp... users);

    @Query("DELETE FROM ITEM_INFO_EXP")
    void deleteAll();

//    @Query("DELETE FROM ITEM_CARD where ITEM_CODE like :itemCode and ITEM_NAME like :ItemName")
//    void deleteByItemCdeName(String itemCode, String ItemName);

//    @Query("UPDATE ITEM_CARD SET IS_EXPORT = '1' ")
//    void updateExport();

}
