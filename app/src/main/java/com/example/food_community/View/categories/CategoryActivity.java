package com.example.food_community.View.categories;

import static android.nfc.cardemulation.CardEmulation.EXTRA_CATEGORY;

import static com.example.food_community.MainActivity.EXTRA_POSITION;

import android.content.Intent;
import android.os.Bundle;

import com.example.food_community.Models.Categories;
import com.example.food_community.View.Adapter.ViewPagerCategoryAdapter;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.food_community.R;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        initActionBar();
        initIntent();
    }

    private void initIntent() {
        Intent intent = getIntent();
        List<Categories.Category> categories =
                (List<Categories.Category>) intent.getSerializableExtra(EXTRA_CATEGORY);
        int position = intent.getIntExtra(EXTRA_POSITION, 0);

        ViewPagerCategoryAdapter adapter = new ViewPagerCategoryAdapter(
                getSupportFragmentManager(),
                categories);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(position, true);
        adapter.notifyDataSetChanged();

    }

    private void initActionBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}