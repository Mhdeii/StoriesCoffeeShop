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
import com.example.storiescoffeeshop.Activity.ItemListActivity;
import com.example.storiescoffeeshop.Domain.categories;
import com.example.storiescoffeeshop.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder> {

    private ArrayList<categories> items;
    private Context context;

    public CategoryAdapter(ArrayList<categories> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_category, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.viewholder holder, int position) {
        categories category = items.get(position);
        holder.titleView.setText(category.getName());

        Glide.with(context)
                .load(category.getImagePath())
                .into(holder.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ItemListActivity.class);
            intent.putExtra("CategoryId", category.getId());
            intent.putExtra("CategoryName", category.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateList(ArrayList<categories> newList) {
        items = newList;
        notifyDataSetChanged();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        TextView titleView;
        ImageView pic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.categoryNameTxt);
            pic = itemView.findViewById(R.id.imgCategory);
        }
    }
}
