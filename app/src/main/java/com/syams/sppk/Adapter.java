package com.syams.sppk;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
//    String[] items;
    ArrayList<String> items;
    ArrayList<String> idItems;

    public Adapter(Context context, ArrayList<String> idItems){
        this.context=context;
//        this.items=items;
        this.idItems = idItems;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.costume_row,parent, false);
        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        ((Item) holder).txt_item.setText(items.get(position));
        ((Item) holder).txt_idItem.setText(idItems.get(position));
    }

    @Override
    public int getItemCount() {
        return idItems.size();
    }

    public class Item extends RecyclerView.ViewHolder {
//        TextView txt_item;
        TextView txt_idItem;
        public Item(View itemView) {
            super(itemView);
//            txt_item = (TextView) itemView.findViewById(R.id.txt_item);
            txt_idItem = (TextView) itemView.findViewById(R.id.txt_idItem);
        }
    }
}
