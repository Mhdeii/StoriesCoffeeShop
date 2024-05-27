package com.example.storiescoffeeshop.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.storiescoffeeshop.Adapter.CategoryAdapter;
import com.example.storiescoffeeshop.Adapter.SliderAdapter;
import com.example.storiescoffeeshop.Domain.SliderItems;
import com.example.storiescoffeeshop.Domain.categories;
import com.example.storiescoffeeshop.R;
import com.example.storiescoffeeshop.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private BroadcastReceiver profilePictureDeletedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            resetProfilePicture();
        }
    };

    ActivityMainBinding binding;
    private CategoryAdapter categoryAdapter;
    private ArrayList<categories> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavbar = findViewById(R.id.bottomNavbar);
        if (bottomNavbar != null) {
            bottomNavbar.setItemSelected(R.id.home, true);
            setupBottomNavigation();
        }

        initCategory();
        initBanner();
        loadProfilePicture();

        // Register the broadcast receiver with the appropriate flag
        IntentFilter filter = new IntentFilter("com.example.storiescoffeeshop.PROFILE_PICTURE_DELETED");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(profilePictureDeletedReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
        }

        // Initialize search bar
        EditText searchBar = findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCategories(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the broadcast receiver
        unregisterReceiver(profilePictureDeletedReceiver);
    }

    private void loadProfilePicture() {
        SharedPreferences sharedPreferences = getSharedPreferences("ProfilePrefs", MODE_PRIVATE);
        String imagePath = sharedPreferences.getString("profile_image_path", null);

        if (imagePath != null) {
            File imgFile = new File(imagePath);
            if (imgFile.exists()) {
                Glide.with(this)
                        .load(imgFile)
                        .apply(RequestOptions.circleCropTransform()) // Make the image circular
                        .into(binding.profileImageViewMain);
            }
        }
    }

    public void resetProfilePicture() {
        binding.profileImageViewMain.setImageResource(R.drawable.profile); // default profile image resource
    }

    private void initBanner() {
        DatabaseReference myRef = database.getReference("Banners");
        binding.progressBarBanner.setVisibility(View.VISIBLE);
        ArrayList<SliderItems> items = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        items.add(issue.getValue(SliderItems.class));
                    }

                    banners(items);
                    binding.progressBarBanner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void banners(ArrayList<SliderItems> items) {
        binding.viewpager2.setAdapter(new SliderAdapter(items, binding.viewpager2));
        binding.viewpager2.setClipChildren(false);
        binding.viewpager2.setClipToPadding(false);
        binding.viewpager2.setOffscreenPageLimit(3);
        binding.viewpager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

        binding.viewpager2.setPageTransformer(compositePageTransformer);
    }

    private void initCategory() {
        DatabaseReference myRef = database.getReference("categories");
        binding.progressBarCategory.setVisibility(View.VISIBLE);
        categoryList = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        categoryList.add(issue.getValue(categories.class));
                    }
                    if (categoryList.size() > 0) {
                        binding.categoryView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                        categoryAdapter = new CategoryAdapter(categoryList);
                        binding.categoryView.setAdapter(categoryAdapter);
                    }
                    binding.progressBarCategory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filterCategories(String query) {
        ArrayList<categories> filteredList = new ArrayList<>();
        for (categories category : categoryList) {
            if (category.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(category);
            }
        }

        categoryAdapter.updateList(filteredList);
    }
}
