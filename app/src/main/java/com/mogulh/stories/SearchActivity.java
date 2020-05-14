package com.mogulh.stories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mogulh.stories.Fragment.BlazeFragment;
import com.mogulh.stories.Fragment.PlaceFragment;
import com.mogulh.stories.Fragment.TagsFragment;
import com.mogulh.stories.Fragment.UserFragment;

public class SearchActivity extends AppCompatActivity {

    BottomNavigationView bottom_navigation;
    Fragment selectedfragment = null;
    ImageView back_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        back_btn=findViewById(R.id.back);

        bottom_navigation = findViewById(R.id.bottom_nav);
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new BlazeFragment()).commit();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, Home.class);
                startActivity(intent);
            }
        });


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.nav_blaze:
                            selectedfragment = new BlazeFragment();
                            break;
                        case R.id.nav_users:
                            selectedfragment = new UserFragment();
                            break;
                        case R.id.nav_places:
                            selectedfragment = new PlaceFragment();
                            Toast.makeText(SearchActivity.this,"places", Toast.LENGTH_LONG).show();

                        case R.id.nav_tags:
                            selectedfragment = new TagsFragment();
                            break;

                    }
                    if (selectedfragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedfragment).commit();
                    }

                    return true;
                }
            };
}
