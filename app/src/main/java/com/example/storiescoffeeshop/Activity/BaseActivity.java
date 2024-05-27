package com.example.storiescoffeeshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.google.firebase.database.FirebaseDatabase;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.example.storiescoffeeshop.R;

public class BaseActivity extends AppCompatActivity {
    FirebaseDatabase database;
    protected ChipNavigationBar bottomNavbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Initialize the navigation bar only if it exists in the layout
        bottomNavbar = findViewById(R.id.bottomNavbar);

        if (bottomNavbar != null) {
            setupBottomNavigation();
        }
    }

    protected void setupBottomNavigation() {
        if (bottomNavbar != null) {
            bottomNavbar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
                @Override
                public void onItemSelected(int id) {
                    if (id == R.id.home) {
                        if (!(BaseActivity.this instanceof MainActivity)) {
                            Intent homeIntent = new Intent(BaseActivity.this, MainActivity.class);
                            startActivity(homeIntent);
                            overridePendingTransition(0, 0);
                        }
                    } else if (id == R.id.favorite) {
                        if (!(BaseActivity.this instanceof FavoriteActivity)) {
                            Intent favoriteIntent = new Intent(BaseActivity.this, FavoriteActivity.class);
                            startActivity(favoriteIntent);
                            overridePendingTransition(0, 0);
                        }
                    } else if (id == R.id.cart) {
                        if (!(BaseActivity.this instanceof CartActivity)) {
                            Intent cartIntent = new Intent(BaseActivity.this, CartActivity.class);
                            startActivity(cartIntent);
                            overridePendingTransition(0, 0);
                        }
                    } else if (id == R.id.profile) {
                        if (!(BaseActivity.this instanceof ProfileActivity)) {
                            Intent profileIntent = new Intent(BaseActivity.this, ProfileActivity.class);
                            startActivity(profileIntent);
                            overridePendingTransition(0, 0);
                        }
                    }
                }
            });
        }
    }
}
