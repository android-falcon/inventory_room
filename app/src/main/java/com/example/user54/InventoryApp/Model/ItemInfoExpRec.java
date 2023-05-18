package com.example.user54.InventoryApp.Model;


// private static final String ITEM_INFO_EXP = "ITEM_INFO_EXP";

//  private static final String ITEM_INFO_EXP_REC = "ITEM_INFO_EXP_REC";
//
//    private static final String ITEM_CODE2 = "ITEM_CODE";
//    private static final String ITEM_NAME2 = "ITEM_NAME";
//    private static final String ITEM_QTY2 = "ITEM_QTY";
//    private static final String EXP_DATE2 = "EXP_DATE";
//    private static final String BATCH_NO2 = "BATCH_NO";
//    private static final String ROW_INDEX2 = "ROW_INDEX";
//    private static final String AVL_QTY2 = "AVL_QTY";
//    private static final String COST_PRC2 = "COST_PRC";
//    private static final String SALE_PRC2 = "SALE_PRC";
//    private static final String RECEIPT_NO2 = "RECEIPT_NO";
//    private static final String SERIAL_NO2 = "SERIAL_NO";


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ITEM_INFO_EXP_REC")

public class ItemInfoExpRec {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "ITEM_CODE")
    private String itemCode ;

    @ColumnInfo(name = "ITEM_NAME")
    private String itemName ;

    @ColumnInfo(name = "ITEM_QTY")
    private float itemQty;
    @ColumnInfo(name = "EXP_DATE")
    private String expDate;

    @ColumnInfo(name = "BATCH_NO")
    private String batchNo;

    @ColumnInfo(name = "ROW_INDEX")
    private float rowIndex;

    @ColumnInfo(name = "AVL_QTY")
    private String AVLQTY;

    @ColumnInfo(name = "COST_PRC")
    private String costPrc;

    @ColumnInfo(name = "SALE_PRC")
    private String salePrc;

    @ColumnInfo(name = "RECEIPT_NO")
    private String receiptNo;

    @ColumnInfo(name = "SERIAL_NO")
    private int serialNo;


    public ItemInfoExpRec() {

    }

    public ItemInfoExpRec(String itemCode, String itemName, float itemQty, String expDate
                        , String batchNo, float rowIndex, String AVLQTY,
                          String costPrc, String salePrc, String receiptNo, int serialNo) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.expDate = expDate;
        this.batchNo = batchNo;
        this.rowIndex = rowIndex;
        this.AVLQTY = AVLQTY;
        this.costPrc = costPrc;
        this.salePrc = salePrc;
        this.receiptNo = receiptNo;
        this.serialNo = serialNo;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemQty(float itemQty) {
        this.itemQty = itemQty;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public void setRowIndex(float rowIndex) {
        this.rowIndex = rowIndex;
    }

    public void setAVLQTY(String AVLQTY) {
        this.AVLQTY = AVLQTY;
    }

    public void setCostPrc(String costPrc) {
        this.costPrc = costPrc;
    }

    public void setSalePrc(String salePrc) {
        this.salePrc = salePrc;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public float getItemQty() {
        return itemQty;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public float getRowIndex() {
        return rowIndex;
    }

    public String getAVLQTY() {
        return AVLQTY;
    }

    public String getCostPrc() {
        return costPrc;
    }

    public String getSalePrc() {
        return salePrc;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public int getSerialNo() {
        return serialNo;
    }
}
