/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ruptech.firefighting.detail;

import android.support.v4.app.Fragment;

import com.ruptech.firefighting.R;
import com.ruptech.firefighting.view.MainTabsFragment;

import java.util.ArrayList;

/**
 * to display a custom {@link android.support.v4.view.ViewPager} title strip which gives continuous feedback to the user
 * when scrolling.
 */
public class DetailTabsFragment extends MainTabsFragment {


    static final String LOG_TAG = DetailTabsFragment.class.getName();

    protected void setupTabs() {
        mTabs = new ArrayList<PagerItem>();
        /**
         * Populate our tab list with tabs. Each item contains a title, indicator color and divider
         * color, which are used by {@link com.ruptech.firefighting.view.SlidingTabLayout}.
         */
        mTabs.add(new PagerItem(
                getString(R.string.tab_title_detail_task)
        ) {
            public Fragment createFragment() {
                return TaskFragment.newInstance(
                );
            }
        });

        mTabs.add(new PagerItem(
                getString(R.string.tab_title_detail_worklog) // Title
        ) {
            public Fragment createFragment() {
                return WorklogFragment.newInstance();
            }
        });

        mTabs.add(new PagerItem(
                getString(R.string.tab_title_detail_item) // Title
        ) {
            public Fragment createFragment() {
                return ItemFragment.newInstance();
            }
        });
        // END_INCLUDE (populate_tabs)
    }

}