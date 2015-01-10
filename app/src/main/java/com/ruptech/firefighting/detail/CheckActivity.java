package com.ruptech.firefighting.detail;

import android.support.v4.app.Fragment;

import com.ruptech.firefighting.R;
import com.ruptech.firefighting.view.PagerItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckActivity extends MaintainActivity {
    void parseExtras() {
        task = (Map<String, Object>) getIntent().getSerializableExtra(ARG_DATA);
    }

    protected List<PagerItem> setupTabs(final Map<String, Object> task) {
        List<PagerItem> mTabs = new ArrayList<PagerItem>();

        mTabs = new ArrayList<PagerItem>();
        /**
         * Populate our tab list with tabs. Each item contains a title, indicator color and divider
         * color, which are used by {@link com.ruptech.firefighting.view.SlidingTabLayout}.
         */
        mTabs.add(new PagerItem(
                getString(R.string.tab_title_detail_task)
        ) {
            public Fragment createFragment() {
                Map<String, Object> basicTask = (Map<String, Object>) task.get("task");
                return TaskFragment.newInstance(basicTask);
            }
        });

        mTabs.add(new PagerItem(
                getString(R.string.tab_title_detail_worklog) // Title
        ) {
            public Fragment createFragment() {
                List<Map<String, Object>> worklogs = (List<Map<String, Object>>) task.get("worklogs");
                List<Map<String, Object>> workhoursum = (List<Map<String, Object>>) task.get("workhoursum");
                return WorklogListFragment.newInstance(worklogs, workhoursum);
            }
        });

        mTabs.add(new PagerItem(
                getString(R.string.tab_title_detail_plan) // Title
        ) {
            public Fragment createFragment() {
                List<Map<String, Object>> items = (List<Map<String, Object>>) task.get("plans");
                return PlanListFragment.newInstance(items);
            }
        });
        // END_INCLUDE (populate_tabs)
        return mTabs;
    }

}
