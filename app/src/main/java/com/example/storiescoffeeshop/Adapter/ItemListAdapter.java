package com.example.storiescoffeeshop.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.storiescoffeeshop.Activity.DetailActivity;
import com.example.storiescoffeeshop.Domain.Items;
import com.example.storiescoffeeshop.R;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.viewholder> {
    ArrayList<Items> items;
    Context context;
    public ItemListAdapter(ArrayList<Items> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemListAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();

        return new viewholder((LayoutInflater.from(context).inflate(R.layout.viewholder_list_item,parent,false)));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListAdapter.viewholder holder, int position) {
    holder.titleTxt.setText(items.get(position).getTitle());
    holder.priceTxt.setText("$" + items.get(position).getPrice());
    holder.rateTxt.setText(""+items.get(position).getStar());

        Glide.with(context)
                .load(items.get(position).getImagePath())
                .transform(new CenterCrop(),new RoundedCorners(50))
                .into(holder.pic);

        holder.itemView.setOnClickListener(v -> {
         Intent intent=new Intent(context, DetailActivity.class);
         intent.putExtra("object",items.get(position));
         context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView titleTxt,priceTxt,rateTxt;
        ImageView pic;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            titleTxt=itemView.findViewById(R.id.titleTxt);
            priceTxt=itemView.findViewById(R.id.priceTxt);
            rateTxt=itemView.findViewById(R.id.ratingTxt);
            pic=itemView.findViewById(R.id.img);
        }
    }
}
