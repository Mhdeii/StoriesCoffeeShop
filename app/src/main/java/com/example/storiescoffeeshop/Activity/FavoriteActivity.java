package com.example.storiescoffeeshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.storiescoffeeshop.R;

public class FavoriteActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set your specific layout for FavoriteActivity
        setContentView(R.layout.activity_favorite);

        // Initialize bottomNavbar before setting item selected
        bottomNavbar = findViewById(R.id.bottomNavbar);
        if (bottomNavbar != null) {
            bottomNavbar.setItemSelected(R.id.favorite, true);
            setupBottomNavigation();
        } else {
            throw new NullPointerException("BottomNavbar is not found in the layout");
        }
    }
}