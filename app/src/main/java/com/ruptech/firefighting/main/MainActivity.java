package com.ruptech.firefighting.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.ruptech.firefighting.App;
import com.ruptech.firefighting.DataType;
import com.ruptech.firefighting.R;
import com.ruptech.firefighting.SettingsActivity;
import com.ruptech.firefighting.utils.PrefUtils;
import com.ruptech.firefighting.view.PagerItem;
import com.ruptech.firefighting.view.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class MainActivity extends ActionBarActivity implements MaterialTabListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_TYPE = "EXTRA_TYPE";
    public static MainActivity instance = null;
    @InjectView(R.id.tabHost)
    MaterialTabHost tabHost;
    @InjectView(R.id.pager)
    ViewPager pager;

    public static void close() {
        if (instance != null) {
            instance.finish();
            instance = null;
        }
    }

    protected List<PagerItem> setupTabs() {
        List<PagerItem> mTabs = new ArrayList<PagerItem>();

        /**
         * Populate our tab list with tabs. Each item contains a title, indicator color and divider
         * color, which are used by {@link com.ruptech.firefighting.view.SlidingTabLayout}.
         */
        mTabs.add(new PagerItem(
                getString(R.string.tab_title_todo)
        ) {
            public Fragment createFragment() {
                return UndoListFragment.newInstance(
                );
            }
        });

        mTabs.add(new PagerItem(
                getString(R.string.tab_title_audit) // Title
        ) {
            public Fragment createFragment() {
                return AuditListFragment.newInstance();
            }
        });
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
        instance = this;

        if (!PrefUtils.existsPushToken()) {
            initWithApiKey();
        }

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        String title = getString(R.string.title_activity_actionbar) + " - " + App.readUser().get真实姓名();
        getSupportActionBar().setTitle(title);

        DataType.init();

        // init view pager
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), setupTabs());
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

    // 以apikey的方式绑定
    private void initWithApiKey() {
        // Push: 无账号初始化，用api key绑定
        String baidu_api_key = App.properties.getProperty("baidu_api_key");
        Log.e(TAG, baidu_api_key);
        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY,
                baidu_api_key);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                gotoSettingActivity();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void gotoSettingActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
