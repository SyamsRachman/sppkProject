package com.syams.sppk;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<String> items;
    ArrayList<String> idItems;


    public Adapter(Context context, ArrayList<String> idItems, ArrayList<String> items){
        this.context=context;
        this.items=items;
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((Item) holder).txt_item.setText(items.get(position));
        ((Item) holder).txt_idItem.setText(idItems.get(position));

        ((Item) holder).ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,idItems.get(position),Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context,DataTanamanActivity.class);
                i.putExtra("idTanaman",idItems.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return idItems.size();
    }

    public class Item extends RecyclerView.ViewHolder {
        TextView txt_item;
        TextView txt_idItem;
        LinearLayout ll_item;
        public Item(View itemView) {
            super(itemView);
            txt_item = (TextView) itemView.findViewById(R.id.txt_item);
            txt_idItem = (TextView) itemView.findViewById(R.id.txt_idItem);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }
}
