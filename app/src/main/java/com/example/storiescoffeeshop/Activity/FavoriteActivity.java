package com.example.storiescoffeeshop.Activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.storiescoffeeshop.Adapter.FavoriteAdapter;
import com.example.storiescoffeeshop.Domain.Items;
import com.example.storiescoffeeshop.Helper.ManagementFavorite;
import com.example.storiescoffeeshop.R;

import java.util.ArrayList;

public class FavoriteActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FavoriteAdapter favoriteAdapter;
    private ManagementFavorite managementFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set your specific layout for FavoriteActivity
        setContentView(R.layout.activity_favorite);
        bottomNavbar = findViewById(R.id.bottomNavbar);
        if (bottomNavbar != null) {
            bottomNavbar.setItemSelected(R.id.favorite, true);
            setupBottomNavigation();
        } else {
            throw new NullPointerException("BottomNavbar is not found in the layout");
        }

        // Initialize views
        recyclerView = findViewById(R.id.itemListView);
        progressBar = findViewById(R.id.progressBar);
        bottomNavbar = findViewById(R.id.bottomNavbar);

        // Set back button click listener
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> onBackPressed()); // Navigate back

        // Initialize ManagementFavorite
        managementFavorite = new ManagementFavorite(this);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadFavorites();
    }

    private void loadFavorites() {
        // Show progress bar while loading
        progressBar.setVisibility(View.VISIBLE);

        // Retrieve favorite items
        ArrayList<Items> favoriteItems = managementFavorite.getListFavorite();

        // Hide progress bar after loading
        progressBar.setVisibility(View.GONE);

        // Initialize adapter and set it to RecyclerView
        favoriteAdapter = new FavoriteAdapter(favoriteItems, managementFavorite, this);
        recyclerView.setAdapter(favoriteAdapter);
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigateToMainActivity();
    }
}
