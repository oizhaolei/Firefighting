package com.ruptech.firefighting.maintain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.ruptech.firefighting.R;
import com.ruptech.firefighting.main.MainActivity;
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

public class MaintainActivity extends ActionBarActivity implements MaterialTabListener {
    public static final String EXTRA_TASK = "EXTRA_TASK";
    private static final String TAG = MaintainActivity.class.getName();
    @InjectView(R.id.tabHost)
    MaterialTabHost tabHost;
    @InjectView(R.id.pager)
    ViewPager pager;
    Map<String, Object> task;
    String type;

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
                return TaskFragment.newInstance(basicTask, type);
            }
        });

        mTabs.add(new PagerItem(
                getString(R.string.tab_title_detail_worklog) // Title
        ) {
            public Fragment createFragment() {
                List<Map<String, Object>> worklogs = (List<Map<String, Object>>) task.get("worklogs");
                List<Map<String, Object>> workhoursum = (List<Map<String, Object>>) task.get("workhoursum");
                return WorklogListFragment.newInstance(worklogs, workhoursum, type);
            }
        });

        mTabs.add(new PagerItem(
                getString(R.string.tab_title_detail_item) // Title
        ) {
            public Fragment createFragment() {
                List<Map<String, Object>> items = (List<Map<String, Object>>) task.get("items");
                List<Map<String, Object>> sum = (List<Map<String, Object>>) task.get("componentsum");
                return ItemListFragment.newInstance(items, sum, type);
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

        parseExtras();

        String title = ((Map<String, Object>) task.get("task")).get("任务名称").toString();
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    void parseExtras() {
        task = (Map<String, Object>) getIntent().getSerializableExtra(EXTRA_TASK);
        type = getIntent().getStringExtra(MainActivity.EXTRA_TYPE);
    }
}
