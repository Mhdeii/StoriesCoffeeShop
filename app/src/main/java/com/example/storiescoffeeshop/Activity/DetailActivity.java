package com.example.storiescoffeeshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.storiescoffeeshop.Domain.Items;
import com.example.storiescoffeeshop.Helper.ManagementFavorite;
import com.example.storiescoffeeshop.Helper.ManagmentCart;

import com.example.storiescoffeeshop.databinding.ActivityDetailBinding;

public class DetailActivity extends BaseActivity {
    ActivityDetailBinding binding;
    private Items object;
    private int num = 1;
    private ManagmentCart managmentCart;
    private ManagementFavorite managmentFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        managmentCart = new ManagmentCart(this);
        managmentFavorite = new ManagementFavorite(this);

        binding.backBtn.setOnClickListener(v -> finish());

        Glide.with(this)
                .load(object.getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(60))
                .into(binding.itemPic);

        binding.priceTxt.setText("$" + object.getPrice());
        binding.titleTxt.setText(object.getTitle());
        binding.descriptionTxt.setText(object.getDescription());
        binding.ratingTxt.setText(object.getStar() + " Rating");
        binding.ratingBar.setRating((float) object.getStar());
        binding.totalTxt.setText(num * object.getPrice() + "$");

        binding.plusBtn.setOnClickListener(v -> {
            num = num + 1;
            binding.numTxt.setText(num + "");
            binding.totalTxt.setText("$" + (num * object.getPrice()));
        });

        binding.minusBtn.setOnClickListener(v -> {
            if (num > 1) {
                num = num - 1;
                binding.numTxt.setText(num + "");
                binding.totalTxt.setText("$" + (num * object.getPrice()));
            }
        });

        binding.addToBtn.setOnClickListener(v -> {
            object.setNumberInCart(num);
            managmentCart.insertItem(object);
        });

        binding.favBtn.setOnClickListener(v -> {
            managmentFavorite.insertItem(object);
        });
    }

    private void getIntentExtra() {
        object = (Items) getIntent().getSerializableExtra("object");
    }
}
