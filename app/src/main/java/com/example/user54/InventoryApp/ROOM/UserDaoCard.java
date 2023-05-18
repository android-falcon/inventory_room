package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

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

    @Query("SELECT  * FROM  ITEM_CARD  where ITEM_CODE LIKE :itemCode LIMIT 1")
    ItemCard findItemCardByCode(String itemCode);

    @Query("SELECT  * FROM  ITEM_CARD where IS_EXPORT = '0' and  IS_NEW = '1' and (IN_DATE  is NULL or  IN_DATE ='') ")
    List<ItemCard> getAllNotExportedItem();

    // @Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ItemCard> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(ItemCard... users);

    @Query("DELETE FROM ITEM_CARD")
    void deleteAll();

    @Query("DELETE FROM ITEM_CARD where ITEM_CODE like :itemCode and ITEM_NAME like :ItemName")
    void deleteByItemCdeName(String itemCode, String ItemName);

    @Query("UPDATE ITEM_CARD SET IS_EXPORT = '1' ")
    void updateExport();

    @Query("SELECT IFNULL (MAX(datetime(substr(IN_DATE, 7, 4) || '-' || substr(IN_DATE, 4, 2) || '-' || substr(IN_DATE, 1, 2))),'2020-03-05') FROM ITEM_CARD")
    String getMaxInDate();





}
