package com.example.storiescoffeeshop.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.storiescoffeeshop.Adapter.CategoryAdapter;
import com.example.storiescoffeeshop.Domain.categories;
import com.example.storiescoffeeshop.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        initCategory();
    }



    private void initCategory() {

        DatabaseReference myRef = database.getReference("categories");
        binding.progressBarCategory.setVisibility(View.VISIBLE);
        ArrayList <categories> list = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        list.add(issue.getValue(categories.class));
                    }
                    if (list.size()>0){
                        binding.categoryView.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
                        binding.categoryView.setAdapter(new CategoryAdapter(list));
                    }
                    binding.progressBarCategory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}