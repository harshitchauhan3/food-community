package com.example.food_community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_community.Models.Meals;
import com.example.food_community.Utils.Utils;
import com.example.food_community.View.Adapter.FavRecyclerAdapter;
import com.example.food_community.View.categories.CategoryView;
import com.example.food_community.View.detail.DetailActivity;
import com.example.food_community.databinding.ActivityFavoriteBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class favorite extends AppCompatActivity implements CategoryView {

    private ActivityFavoriteBinding binding;

    @BindView(R.id.favRecyclerView)
    RecyclerView favRecyclerView;

    @BindView(R.id.favProgressBar)
    ProgressBar favProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar=findViewById(R.id.fav_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.favorite);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.favorite);

        binding.bottomNavigationView.setOnItemSelectedListener(item-> {
            switch (item.getItemId()){
                case R.id.home:{
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.profile:{
                    Intent intent = new Intent(getApplicationContext(), profile.class);
                    startActivity(intent);
                    break;
                }

            }
            return true;
        });
        ButterKnife.bind(this); // Initialize ButterKnife

        // Set the layout manager for the RecyclerView
        favRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        FavRecyclerAdapter adapter = new FavRecyclerAdapter(this, new ArrayList<>());
        favRecyclerView.setAdapter(adapter);
        getFav();
    }
    public void getFav(){
        showLoading();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(firebaseAuth.getUid()));

        databaseRef.child("Favorites").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Meals.Meal> userMeals = new ArrayList<>();
                for (DataSnapshot mealSnapshot : dataSnapshot.getChildren()) {
                    Meals.Meal meal = mealSnapshot.getValue(Meals.Meal.class);
                    userMeals.add(meal);
                }
                FavRecyclerAdapter adapter = (FavRecyclerAdapter) favRecyclerView.getAdapter();
                hideLoading();
                if (adapter != null) {
                    setMeals(userMeals);
                } else {
                    // This might happen if the activity is destroyed before the data is fetched.
                    Toast.makeText(favorite.this, "Activity destroyed before data was fetched", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(favorite.this, "Some error", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void setMeals(List<Meals.Meal> meals) {
        FavRecyclerAdapter adapter =
                new FavRecyclerAdapter(this, meals);
        favRecyclerView.setClipToPadding(false);
        favRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new FavRecyclerAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TextView favMealName = view.findViewById(R.id.favMealName);
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("detail", favMealName.getText().toString());
                startActivity(intent);
            }
        });
    }
    @Override
    public void showLoading() {
        favProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        favProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(getApplicationContext(), "Error ", message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.favorite);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}