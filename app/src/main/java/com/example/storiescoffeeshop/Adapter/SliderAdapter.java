package com.example.storiescoffeeshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.storiescoffeeshop.Domain.SliderItems;
import com.example.storiescoffeeshop.R;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.Sliderviewholder>{
    private List<SliderItems> sliderItems;
    private ViewPager2 viewPager2;
    private Context context;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();
        }
    };

    public SliderAdapter(List<SliderItems> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderAdapter.Sliderviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new Sliderviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_viewholder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapter.Sliderviewholder holder, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(60));

        Glide.with(context)
                .load(sliderItems.get(position).getImage())
                .apply(requestOptions)
                .into(holder.imageView);

        if (position == sliderItems.size()-2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public class Sliderviewholder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public Sliderviewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }
    }
}
