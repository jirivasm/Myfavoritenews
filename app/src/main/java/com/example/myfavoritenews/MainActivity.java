package com.example.myfavoritenews;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find the tabs and the viewpager views
        ViewPager viewPager = findViewById(R.id.viewpager);
        TabLayout tabs = findViewById(R.id.tabs);

        //create the fragment adapter
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());

        //set up the tabs and the viewPager
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }
}