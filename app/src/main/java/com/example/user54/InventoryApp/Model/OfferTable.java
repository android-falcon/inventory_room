package com.example.user54.InventoryApp.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "OFFER_TABLE")
public class OfferTable {
    //[{"CONO":"290","ITEMOCODE":"40043","ITEMNAMEA":"دجاج مسلخ عمان","DESCNAME":"","F_D":"1.49",
    // "FRMDATE":"01\/01\/2023","TODATE":"02\/01\/2023","OFFERNO":"1851"},{"CONO":"290","ITEMOCODE":"10128","ITEMNAMEA":"فول أخضر","DESCNAME":"","F_D":"1.49","FRMDATE":"29\/01\/2023","TODATE":"30\/01\/2023","OFFERNO":"2208"},{"CONO":"290","ITEMOCODE":"20236","ITEMNAMEA":"مياسي مشكل","DESCNAME":"","F_D":"2.49","FRMDATE":"01\/01\/2023","TODATE":"02\/01\/2023","OFFERNO":"1859"},{"CONO":"290","ITEMOCODE":"10018","ITEMNAMEA":"فاصولياء بلدية",
    // "DESCNAME":"","F_D":"0.99","FRMDATE":"29\/01\/2023","TODATE":"30\/01\/2023","OFFERNO":"2210"},


    @ColumnInfo(name = "ITEMOCODE")
    private String  ITEMOCODE;

    @ColumnInfo(name = "CONO")
    private String  CONO;

    @ColumnInfo(name = "ITEMNAMEA")
    private String  ITEMNAMEA;

    @ColumnInfo(name = "DESCNAME")
    private String  DESCNAME;

    @ColumnInfo(name = "FRMDATE")
    private String  FRMDATE;

    @ColumnInfo(name = "TODATE")
    private String  TODATE;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "OFFERNO")
    private String  OFFERNO;

    @ColumnInfo(name = "F_D")
    private String  F_D;

    public OfferTable() {
    }

    public OfferTable(String CONO, String ITEMOCODE, String ITEMNAMEA, String DESCNAME, String FRMDATE, String TODATE, String OFFERNO, String f_D) {
        this.CONO = CONO;
        this.ITEMOCODE = ITEMOCODE;
        this.ITEMNAMEA = ITEMNAMEA;
        this.DESCNAME = DESCNAME;
        this.FRMDATE = FRMDATE;
        this.TODATE = TODATE;
        this.OFFERNO = OFFERNO;
        this.F_D = f_D;
    }

    public String getCONO() {
        return CONO;
    }

    public void setCONO(String CONO) {
        this.CONO = CONO;
    }

    public String getITEMOCODE() {
        return ITEMOCODE;
    }

    public void setITEMOCODE(String ITEMOCODE) {
        this.ITEMOCODE = ITEMOCODE;
    }

    public String getITEMNAMEA() {
        return ITEMNAMEA;
    }

    public void setITEMNAMEA(String ITEMNAMEA) {
        this.ITEMNAMEA = ITEMNAMEA;
    }

    public String getDESCNAME() {
        return DESCNAME;
    }

    public void setDESCNAME(String DESCNAME) {
        this.DESCNAME = DESCNAME;
    }

    public String getFRMDATE() {
        return FRMDATE;
    }

    public void setFRMDATE(String FRMDATE) {
        this.FRMDATE = FRMDATE;
    }

    public String getTODATE() {
        return TODATE;
    }

    public void setTODATE(String TODATE) {
        this.TODATE = TODATE;
    }

    public String getOFFERNO() {
        return OFFERNO;
    }

    public void setOFFERNO(String OFFERNO) {
        this.OFFERNO = OFFERNO;
    }

    public String getF_D() {
        return F_D;
    }

    public void setF_D(String f_D) {
        F_D = f_D;
    }

}
