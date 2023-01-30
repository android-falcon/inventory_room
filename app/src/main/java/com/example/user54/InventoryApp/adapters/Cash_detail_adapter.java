package com.example.user54.InventoryApp.adapters;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user54.InventoryApp.Model.ItemQty;
import com.example.user54.InventoryApp.R;


import java.util.List;

public class Cash_detail_adapter extends RecyclerView.Adapter<Cash_detail_adapter.ViewHolder> {

    private  List<ItemQty> groupSales_List;
    private  Context context;

    public Cash_detail_adapter(List<ItemQty> groupSales_List, Context context) {
        this.groupSales_List = groupSales_List;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_detail_cash, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.transName.setText(groupSales_List.get(position).getItemName());
        holder.transAmount.setText(groupSales_List.get(position).getF_d());
        holder.trans_barcode.setText(groupSales_List.get(position).getItemCode());
        holder.trans_qty.setText(groupSales_List.get(position).getQty());


    }

    @Override
    public int getItemCount() {
        return groupSales_List.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView transName, transAmount,trans_qty,trans_barcode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            transName = itemView.findViewById(R.id.trans_name);
            transAmount = itemView.findViewById(R.id.trans_amount);
            trans_barcode= itemView.findViewById(R.id.trans_barcode);
            trans_qty= itemView.findViewById(R.id.trans_qty);
            transName.setMovementMethod(new ScrollingMovementMethod());

        }

    }
}
