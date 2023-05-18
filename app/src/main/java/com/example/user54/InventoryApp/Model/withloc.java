package com.example.user54.InventoryApp.Model;

public class withloc {

   private String  ITEM_CODE;
    private String  ITEM_NAME ;
    private String  SALES_PRICE ;
    private double  SumITEM_QTY;
    private double ITEM_QTY;

    public withloc() {
    }

    public String getITEM_CODE() {
        return ITEM_CODE;
    }

    public void setITEM_CODE(String ITEM_CODE) {
        this.ITEM_CODE = ITEM_CODE;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public String getSALES_PRICE() {
        return SALES_PRICE;
    }

    public void setSALES_PRICE(String SALES_PRICE) {
        this.SALES_PRICE = SALES_PRICE;
    }

    public double getSumITEM_QTY() {
        return SumITEM_QTY;
    }

    public void setSumITEM_QTY(double sumITEM_QTY) {
        SumITEM_QTY = sumITEM_QTY;
    }

    public  double getITEM_QTY() {
        return ITEM_QTY;
    }

    public void setITEM_QTY(double ITEM_QTY) {
        this.ITEM_QTY = ITEM_QTY;
    }
}
