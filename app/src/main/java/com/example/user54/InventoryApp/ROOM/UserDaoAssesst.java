package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.AssestItem;
import com.example.user54.InventoryApp.Model.ItemCard;

import java.util.List;

@Dao
public interface UserDaoAssesst {
    @Query("SELECT * FROM ASSEST_TABLE")
    public List<AssestItem> getAllAssest();

    @Query("SELECT * FROM ASSEST_TABLE ")
    List<AssestItem> loadAllAssest();

//    @Query("SELECT * FROM ASSEST_TABLE where IS_NEW ='1' ")
//    List<ItemCard> loadNewItem();

//    @Query("SELECT * FROM UserModel WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    UserModel findByName(String first, String last);

//    String selectQuery = "SELECT *  FROM " + ASSEST_TABLE +" where ASSESST_CODE = '"+ASSESST_BARCODE+"'";
    @Query("SELECT * FROM ASSEST_TABLE WHERE ASSESST_CODE LIKE :ASSESST_BARCODE ")
    List<AssestItem> getItemAssetsByBarcode(String ASSESST_BARCODE);

//    String selectQuery = "SELECT  DISTINCT ASSESST_MAINMNG FROM " + ASSEST_TABLE;
@Query("SELECT DISTINCT ASSESST_MAINMNG FROM ASSEST_TABLE ")
List<String> getItemAssetsManager();

    @Query("SELECT DISTINCT ASSESST_DEPARTMENT FROM ASSEST_TABLE ")
    List<String> getItemAssetsDep();

    @Query("SELECT DISTINCT ASSESST_SECTION FROM ASSEST_TABLE ")
    List<String> getItemAssetsSection();

    @Query("SELECT DISTINCT ASSESST_AREANAME FROM ASSEST_TABLE ")
    List<String> getItemAssetsAreaName();
    // @Insert
   @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllAssassest(List<AssestItem> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAssest(AssestItem... users);

    @Query("DELETE FROM ASSEST_TABLE")
    void deleteAllAssest();

}
