package com.example.user54.InventoryApp.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TRANSF_VHF_SER")
public class TransferVhfSerial {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "VHF_SERIAL")
    private int VhfSerial;

    public TransferVhfSerial(int vhfSerial) {
        VhfSerial = vhfSerial;
    }
    public TransferVhfSerial() {

    }

    public int getVhfSerial() {
        return VhfSerial;
    }

    public void setVhfSerial(int vhfSerial) {
        VhfSerial = vhfSerial;
    }
}
