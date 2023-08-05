package com.example.food_community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.food_community.databinding.ActivityProfileBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class profile extends AppCompatActivity {

    Button buttonLogout;
    Toolbar toolbar;
    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar=findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        binding.bottomNavigationView.setOnItemSelectedListener(item-> {
            switch (item.getItemId()){
                case R.id.favorite:{
                    Intent intent = new Intent(getApplicationContext(), favorite.class);
                    startActivity(intent);
                    break;
                }
                case R.id.home:{
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    break;
                }

            }
            return true;
        });
        String userId="email unavailable";
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            userId=firebaseAuth.getCurrentUser().getEmail();
        }
        binding.textId2.setText(userId);

        buttonLogout=findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform logout
                FirebaseAuth.getInstance().signOut();

                // Redirect to the login activity
                Intent intent = new Intent(profile.this, login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.profile);
    }

}