package com.example.user54.InventoryApp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.user54.InventoryApp.Model.ItemCard;

import java.util.List;


public class ListAdapterSearch extends BaseAdapter {
    CheckBox checkPriceed;
    private Context context;
    List<ItemCard> itemsList;
    int companyNo;

    public ListAdapterSearch(Context context, List<ItemCard> itemsList,int companyNo) {
        this.context = context;
        this.itemsList = itemsList;
        this.companyNo=companyNo;
    }

    public ListAdapterSearch() {

    }

    public void setItemsList(List<ItemCard> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView itemCode, itemName,size,color;//, price
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.item_row_search, null);

        holder.itemCode = (TextView) view.findViewById(R.id.itemCode);
        holder.itemName = (TextView) view.findViewById(R.id.itemName);
//        holder.price = (TextView) view.findViewById(R.id.price);

        holder.itemCode.setText("" + itemsList.get(i).getItemCode());
        holder.itemName.setText("" + itemsList.get(i).getItemName());
//        holder.price.setText("" + itemsList.get(i).getSalePrc());

        holder.size=view.findViewById(R.id.size);
        holder.color=view.findViewById(R.id.color);

         if(companyNo==1){
            holder.size.setVisibility(View.VISIBLE);
            holder.color.setVisibility(View.VISIBLE);
            holder.size.setText(""+itemsList.get(1).getItemM());
            holder.color.setText(""+itemsList.get(1).getItemK());

        }else {
            holder.size.setVisibility(View.GONE);
            holder.color.setVisibility(View.GONE);
        }

        return view;
    }




}

