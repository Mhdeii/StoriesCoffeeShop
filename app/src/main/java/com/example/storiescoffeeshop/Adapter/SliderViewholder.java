package com.example.storiescoffeeshop.Adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SliderViewholder extends RecyclerView.Adapter<SliderViewholder.SliderViewholder>{

    @NonNull
    @Override
    public SliderViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull SliderViewholder.SliderViewholder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class SliderViewholder extends RecyclerView.ViewHolder {
        public com.example.storiescoffeeshop.Adapter.SliderViewholder(@NonNullView itemView){
            super(itemView);
        }
    }
}
