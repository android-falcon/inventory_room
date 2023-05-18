package com.example.user54.InventoryApp.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.user54.InventoryApp.Model.TransferItemsInfo;
import com.example.user54.InventoryApp.Model.TransferVhfSerial;

import java.util.List;

@Dao
public interface UserDaoTrancferSerial {

    @Query("SELECT VHF_SERIAL FROM TRANSF_VHF_SER LIM")
    int getAll();

    @Query("SELECT * FROM TRANSF_VHF_SER ")
    List<TransferVhfSerial> loadAllByIds();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(TransferVhfSerial... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  //  @Insert
    void insertAll(List<TransferVhfSerial> users);

    @Delete
    void delete(TransferVhfSerial user);

    @Query("DELETE FROM TRANSF_VHF_SER")
    void deleteAll();

    @Query("UPDATE TRANSF_VHF_SER SET VHF_SERIAL = :VHF_SERIAL")
    void updateSerial(String VHF_SERIAL);
}
