package com.ruptech.firefighting.check;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.ruptech.firefighting.R;
import com.ruptech.firefighting.main.MainActivity;

import java.util.List;
import java.util.Map;


public class ItemListActivity extends ActionBarActivity {

    public static final String EXTRA_ITEMS = "EXTRA_ITEMS";
    public static final String EXTRA_SUM = "EXTRA_SUM";
    String type;
    private List<Map<String, Object>> items;
    private List<Map<String, Object>> sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set content view (which contains a PopupListFragment)
        setContentView(R.layout.activity_plan_detail);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        String title = getString(R.string.title_item_check);
        getSupportActionBar().setTitle(title);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        items = (List<Map<String, Object>>) getIntent().getSerializableExtra(ItemListActivity.EXTRA_ITEMS);
        sum = (List<Map<String, Object>>) getIntent().getSerializableExtra(ItemListActivity.EXTRA_SUM);
        type = getIntent().getStringExtra(MainActivity.EXTRA_TYPE);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.sample_content_fragment, ItemListFragment.newInstance(items, sum, type));
            transaction.commit();
        }
    }
}
