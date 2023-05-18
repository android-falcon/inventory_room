package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.ItemUnit;
import com.example.user54.InventoryApp.Model.MainSetting;
import com.example.user54.InventoryApp.Model.TransferItemsInfo;

import java.util.List;

@Dao
public interface UserDaoTrancfer {

    @Query("SELECT * FROM TRANSFER_ITEMS_INFO")
    List<TransferItemsInfo> getAll();

    @Query("select IFNULL(Max(VHF_NO),1)  FROM TRANSFER_ITEMS_INFO")
    int  getMax();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(TransferItemsInfo... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  //  @Insert
    void insertAll(List<TransferItemsInfo> users);

    @Delete
    void delete(TransferItemsInfo user);

    @Query("DELETE FROM TRANSFER_ITEMS_INFO")
    void deleteAll();

    @Query("UPDATE TRANSFER_ITEMS_INFO SET ISEXPORT = '1'")
    void updateExport();

    @Query("UPDATE TRANSFER_ITEMS_INFO SET ITEM_QTY = :newValue where ROW_INDEX = :serial and ITEM_CODE = :code")
    void updateTrance(String newValue,String serial,String code);

    @Query(" SELECT COALESCE(sum(ITEM_QTY),0)  FROM  TRANSFER_ITEMS_INFO  WHERE ITEM_CODE = :ItemCode ")
    String getTotal(String ItemCode);

}
