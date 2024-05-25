package com.example.storiescoffeeshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.storiescoffeeshop.Domain.categories;
import com.example.storiescoffeeshop.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder> {

    ArrayList<categories> items;
    Context context;

    public CategoryAdapter(ArrayList<categories> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public CategoryAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.viewholder holder, int position) {
        holder.titleView.setText(items.get(position).getName());
        int drawableResourceId = context.getResources().getIdentifier(items.get(position).getImagePath(),"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    public class viewholder extends RecyclerView.ViewHolder {
        TextView titleView;
        ImageView pic;
        public viewholder(@NonNull View itemView){
            super(itemView);
            titleView = itemView.findViewById(R.id.categoryNameTxt);
            pic = itemView.findViewById(R.id.imgCategory);
        }
    }
}
