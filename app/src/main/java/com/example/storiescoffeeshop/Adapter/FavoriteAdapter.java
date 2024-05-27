package com.example.storiescoffeeshop.Adapter;

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
import com.example.storiescoffeeshop.Helper.ManagementFavorite;
import com.example.storiescoffeeshop.R;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private ArrayList<Items> list;
    private ManagementFavorite managementFavorite;
    private Context context;

    public FavoriteAdapter(ArrayList<Items> list, ManagementFavorite managementFavorite, Context context) {
        this.list = list;
        this.managementFavorite = managementFavorite;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_favorite, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        Items item = list.get(position);
        holder.title.setText(item.getTitle());
        holder.price.setText("$" + String.format("%.2f", item.getPrice()));

        Glide.with(holder.itemView.getContext())
                .load(item.getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.pic);

        holder.trashBtn.setOnClickListener(v -> {
            managementFavorite.removeItem(list, position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, list.size());
        });

        // Set click listener to navigate to DetailActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", item);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, price;
        ImageView pic;
        View trashBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            price = itemView.findViewById(R.id.feeEachItem);
            pic = itemView.findViewById(R.id.pic);
            trashBtn = itemView.findViewById(R.id.trashBtn);
        }
    }
}
