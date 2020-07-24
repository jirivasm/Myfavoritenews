package com.example.myfavoritenews;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class FragmentAdapter extends FragmentPagerAdapter {


    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            getPageTitle(position);
            return new GeneralNews();
        } else if (position == 1) {
            getPageTitle(position);
            return new SportsNews();
        } else if (position == 2) {
            getPageTitle(position);
            return new CovidNews();
        } else {
            getPageTitle(position);
            return new PoliticNews();
        }
    }


    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Latest";
            case 1:
                return "Sports";
            case 2:
                return "Covid-19";
            case 3:
                return "Politics";
        }
        return null;
    }

}

