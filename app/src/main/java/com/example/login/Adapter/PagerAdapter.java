package com.example.login.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.login.DetailSalesFragment;
import com.example.login.TenderFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                DetailSalesFragment tab1 = new DetailSalesFragment();
                return tab1;
            case 1:
                TenderFragment tab2 = new TenderFragment();
                return tab2;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
