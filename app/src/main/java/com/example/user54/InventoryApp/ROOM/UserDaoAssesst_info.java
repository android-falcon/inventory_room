package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.AssestItem;
import com.example.user54.InventoryApp.Model.AssestItem_info;

import java.util.List;

@Dao
public interface UserDaoAssesst_info {
    @Query("SELECT * FROM ASSEST_TABLE_INFO")
    public List<AssestItem_info> getAllAssest();

    @Query("SELECT * FROM ASSEST_TABLE_INFO ")
    List<AssestItem_info> loadAllAssest();

//    @Query("SELECT * FROM ASSEST_TABLE where IS_NEW ='1' ")
//    List<ItemCard> loadNewItem();

//    @Query("SELECT * FROM UserModel WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    UserModel findByName(String first, String last);

   // @Insert
   @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllAssassest(List<AssestItem_info> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAssest(AssestItem_info... users);

    @Query("DELETE FROM ASSEST_TABLE_INFO")
    void deleteAllAssest();

    @Query("UPDATE ASSEST_TABLE_INFO SET ASSESST_ISEXPORT_INFO = 1 ")
    int updateExportTable();

    @Query("UPDATE ASSEST_TABLE_INFO SET ASSESST_QTY_INFO = :newValue Where ASSESST_SERIAL_INFO = :Serial and ASSESST_CODE = :ASSESST_CODE")
    int updateQtyTable(String newValue,String Serial,String ASSESST_CODE);

    //DELETE FROM "+ASSEST_TABLE_INFO+" where ASSESST_CODE = '"+ItemCode +"' and ASSESST_SERIAL_INFO = '"+SERIALNo+"'"
    @Query("DELETE FROM ASSEST_TABLE_INFO  where ASSESST_CODE = :ItemCode  and ASSESST_SERIAL_INFO = :SERIALNo")
    int deleteAssestInfoBySerial(String ItemCode,String SERIALNo);
}
