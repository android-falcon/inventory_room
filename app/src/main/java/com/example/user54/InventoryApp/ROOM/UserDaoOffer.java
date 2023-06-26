package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.OfferTable;

import java.util.List;

@Dao
public interface UserDaoOffer {

    @Query("SELECT * FROM OFFER_TABLE")
    List<OfferTable> getAll();

    @Query("SELECT * FROM OFFER_TABLE WHERE ITEMOCODE LIKE :code   ORDER BY FRMDATE DESC")
    List<OfferTable> getOfferByCode(String code);

//    @Query("SELECT * FROM UserModel WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    UserModel findByName(String first, String last);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  //  @Insert
    void insertAll(List<OfferTable> users);

    @Delete
    void delete(OfferTable user);

    @Query("DELETE FROM OFFER_TABLE")
    void deleteAll();

}
