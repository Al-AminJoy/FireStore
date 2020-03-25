package com.trustedoffer.firestore;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PlansPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public PlansPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        return TestFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}