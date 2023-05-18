package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.AssestItem_backup;
import com.example.user54.InventoryApp.Model.AssestItem_info;

import java.util.List;

@Dao
public interface UserDaoAssesst_infoBackup {
    @Query("SELECT * FROM ASSEST_TABLE_INFO_BACKUP")
    public List<AssestItem_backup> getAllAssestInfoBackUp();

    @Query("SELECT * FROM ASSEST_TABLE_INFO_BACKUP ")
    List<AssestItem_backup> loadAllAssestInfoBackUp();

//    @Query("SELECT * FROM ASSEST_TABLE where IS_NEW ='1' ")
//    List<ItemCard> loadNewItem();

//    @Query("SELECT * FROM UserModel WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    UserModel findByName(String first, String last);

   // @Insert
   @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllAssassestInfoBackUp(List<AssestItem_backup> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAssestInfoBackUp(AssestItem_backup... users);

    @Query("DELETE FROM ASSEST_TABLE_INFO_BACKUP")
    void deleteAllAssestInfoBackUp();

}
