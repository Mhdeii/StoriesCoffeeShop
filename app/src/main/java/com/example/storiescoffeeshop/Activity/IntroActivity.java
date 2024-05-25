package com.example.storiescoffeeshop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



import com.example.storiescoffeeshop.databinding.ActivityIntroBinding;

public class IntroActivity extends BaseActivity {

    ActivityIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
            }
        });

    }

    /*
    private void setVariable() {
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser()!= null){
                    startActivity(new Intent(IntroActivity.this, HomeActivity.class));
                }
                else {
                    startActivity(new Intent(IntroActivity.this,HomeActivity.class));
                }
            }
        });

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this, HomeActivity.class));
            }
        });
    }


     */
}