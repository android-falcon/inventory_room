package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.ItemQR;
import com.example.user54.InventoryApp.Model.ItemUnit;

import java.util.List;

@Dao
public interface UserDaoQR {

    @Query("SELECT * FROM ITEM_QR_CODE_TABLE")
    List<ItemQR> getAll();

    @Query("SELECT  * FROM  ITEM_QR_CODE_TABLE  WHERE  QR_CODE = :Qrcode ")
    List<ItemQR> getAllQr(String Qrcode);

    @Query("SELECT  * FROM  ITEM_QR_CODE_TABLE  WHERE  QR_CODE LIKE  :Qrcode  || '%'")
    List<ItemQR> loadAllByIds(String Qrcode);

    @Query("SELECT  PRICE FROM  ITEM_QR_CODE_TABLE  WHERE  ITEM_CODE = :ITEM_CODE ")
    String  getQrcodePrice(String ITEM_CODE);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
  //  @Insert
    void insertAll(List<ItemQR> users);

    @Delete
    void delete(ItemQR user);

    @Query("DELETE FROM ITEM_QR_CODE_TABLE")
    void deleteAll();

}
