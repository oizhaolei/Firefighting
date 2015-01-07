package com.ruptech.firefighting.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.ruptech.firefighting.R;
import com.ruptech.firefighting.view.PagerItem;
import com.ruptech.firefighting.view.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class DetailActivity extends ActionBarActivity implements MaterialTabListener {
    public static final String ARG_ITEM = "ARG_ITEM";
    private static final String TAG = DetailActivity.class.getName();
    @InjectView(R.id.tabHost)
    MaterialTabHost tabHost;
    @InjectView(R.id.pager)
    ViewPager pager;
    private Map<String, Object> task;

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
                return WorklogFragment.newInstance(worklogs);
            }
        });

        mTabs.add(new PagerItem(
                getString(R.string.tab_title_detail_item) // Title
        ) {
            public Fragment createFragment() {
                List<Map<String, Object>> items = (List<Map<String, Object>>) task.get("items");
                return ItemFragment.newInstance(items);
            }
        });
        // END_INCLUDE (populate_tabs)
        return mTabs;
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        task = (Map<String, Object>) getIntent().getSerializableExtra(ARG_ITEM);

        // init view pager
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), setupTabs(task));
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);

            }
        });
        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this)
            );

        }
    }
}
