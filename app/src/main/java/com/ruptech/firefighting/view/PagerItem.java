package com.ruptech.firefighting.view;

import android.support.v4.app.Fragment;


public abstract class PagerItem {
    private final CharSequence mTitle;

    public PagerItem(CharSequence title) {
        mTitle = title;
    }

    /**
     * @return A new {@link android.support.v4.app.Fragment} to be displayed by a {@link android.support.v4.view.ViewPager}
     */
    public abstract Fragment createFragment();

    /**
     * @return the title which represents this tab. In this sample this is used directly by
     * {@link android.support.v4.view.PagerAdapter#getPageTitle(int)}
     */
    public CharSequence getTitle() {
        return mTitle;
    }
}
