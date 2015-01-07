package com.ruptech.firefighting.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final List<PagerItem> pagerItems;

    public ViewPagerAdapter(FragmentManager fm, List<PagerItem> pagerItems) {
        super(fm);
        this.pagerItems = pagerItems;
    }

    public Fragment getItem(int num) {
        return pagerItems.get(num).createFragment();
    }

    @Override
    public int getCount() {
        return pagerItems.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pagerItems.get(position).getTitle();
    }

}