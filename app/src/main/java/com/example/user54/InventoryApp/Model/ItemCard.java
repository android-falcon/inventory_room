package com.example.user54.InventoryApp.Model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;
@Entity(tableName = "ITEM_CARD")
public class ItemCard {

//    private String itemCode;
//    private String itemName;
//    private String costPrc;
//    private String salePrc;
//    private String AVLQty;
//    private String FDPRC;
//    private String branchId;
//    private String branchName;
//    private String departmentId;
//    private String departmentName;
//    private String itemG;
//    private String itemK;
//    private String itemL;
//    private String itemDiv;
//    private String itemGs;
//    private String orgPrice;
//    private String inDate;
//    private String isExport;
//    private String isNew;
//    private String itemUnit;
//    private boolean check;
//    private String isUnite;
//




    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ITEM_CODE")
    private String itemCode;
    @ColumnInfo(name = "ITEM_NAME")
    private String itemName;
    @ColumnInfo(name = "COST_PRC")
    private String costPrc;
    @ColumnInfo(name = "SALE_PRC")
    private String salePrc;
    @ColumnInfo(name = "AVL_QTY")
    private String AVLQty;
    @ColumnInfo(name = "FD_PRC")
    private String FDPRC;
    @ColumnInfo(name = "BRANCH_ID")
    private String branchId;
    @ColumnInfo(name = "BRANCH_NAME")
    private String branchName;
    @ColumnInfo(name = "DEPARTMENT_ID")
    private String departmentId;
    @ColumnInfo(name = "DEPARTMENT_NAME")
    private String departmentName;
    @ColumnInfo(name = "ITEM_G")
    private String itemG;
    @ColumnInfo(name = "ITEM_K")
    private String itemK;
    @ColumnInfo(name = "ITEM_L")
    private String itemL;
    @ColumnInfo(name = "ITEM_DIV")
    private String itemDiv;
    @ColumnInfo(name = "ITEM_GS")
    private String itemGs;
    @ColumnInfo(name = "ORG_PRICE")
    private String orgPrice;
    @ColumnInfo(name = "IN_DATE")
    private String inDate;
    @ColumnInfo(name = "IS_EXPORT")
    private String isExport;
    @ColumnInfo(name = "IS_NEW")
    private String isNew;

    private String itemUnit;
    @Ignore
    private boolean check;
    private String isUnite;

    @ColumnInfo(name = "ITEM_M")
    private String itemM;

    public String isStartDay;
    public String isName;
    public ItemCard() {

    }

    public ItemCard(String itemCode, String itemName, String costPrc, String salePrc, String AVLQty, String FDPRC, String branchId, String branchName, String departmentId, String departmentName, String itemG, String itemK, String itemL, String itemDiv, String itemGs, String orgPrice, String inDate, String isExport, String isNew, boolean check) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.costPrc = costPrc;
        this.salePrc = salePrc;
        this.AVLQty = AVLQty;
        this.FDPRC = FDPRC;
        this.branchId = branchId;
        this.branchName = branchName;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.itemG = itemG;
        this.itemK = itemK;
        this.itemL = itemL;
        this.itemDiv = itemDiv;
        this.itemGs = itemGs;
        this.orgPrice = orgPrice;
        this.inDate = inDate;
        this.isExport = isExport;
        this.isNew = isNew;
        this.check = check;
    }

    public String getIsUnite() {
        return isUnite;
    }

    public void setIsUnite(String isUnite) {
        this.isUnite = isUnite;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCostPrc() {
        return costPrc;
    }

    public void setCostPrc(String costPrc) {
        this.costPrc = costPrc;
    }

    public String getSalePrc() {
        return salePrc;
    }

    public void setSalePrc(String salePrc) {
        this.salePrc = salePrc;
    }

    public String getAVLQty() {
        return AVLQty;
    }

    public void setAVLQty(String AVLQty) {
        this.AVLQty = AVLQty;
    }

    public String getFDPRC() {
        return FDPRC;
    }

    public void setFDPRC(String FDPRC) {
        this.FDPRC = FDPRC;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getItemG() {
        return itemG;
    }

    public void setItemG(String itemG) {
        this.itemG = itemG;
    }

    public String getItemK() {
        return itemK;
    }

    public void setItemK(String itemK) {
        this.itemK = itemK;
    }

    public String getItemL() {
        return itemL;
    }

    public void setItemL(String itemL) {
        this.itemL = itemL;
    }

    public String getItemDiv() {
        return itemDiv;
    }

    public void setItemDiv(String itemDiv) {
        this.itemDiv = itemDiv;
    }

    public String getItemGs() {
        return itemGs;
    }

    public void setItemGs(String itemGs) {
        this.itemGs = itemGs;
    }

    public String getOrgPrice() {
        return orgPrice;
    }

    public void setOrgPrice(String orgPrice) {
        this.orgPrice = orgPrice;
    }

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getIsExport() {
        return isExport;
    }

    public void setIsExport(String isExport) {
        this.isExport = isExport;
    }

    public String getIsNew() {
        return isNew;
    }

    public String getItemM() {
        return itemM;
    }

    public void setItemM(String itemM) {
        this.itemM = itemM;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public boolean isCheck() {
        return check;
    }

    public String getIsStartDay() {
        return isStartDay;
    }

    public void setIsStartDay(String isStartDay) {
        this.isStartDay = isStartDay;
    }

    public String getIsName() {
        return isName;
    }

    public void setIsName(String isName) {
        this.isName = isName;
    }

    public JSONObject getJSONObject2() { // for server
        JSONObject obj = new JSONObject();
        try {

            obj.put("ItemOCode", itemCode);
            obj.put("ItemNameA", itemName);
            obj.put("ItemG", costPrc);
            obj.put("SalePrice", salePrc);
            obj.put("AVLQTY", AVLQty);
            obj.put("F_D",FDPRC );
            obj.put("ItemK", branchId);
            obj.put("ItemL", branchName);
            obj.put("ITEMDIV", departmentId);
            obj.put("ITEMGS", departmentName);
            obj.put("InDate", itemG);



        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }

}
