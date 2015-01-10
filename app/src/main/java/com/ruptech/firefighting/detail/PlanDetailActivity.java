package com.ruptech.firefighting.detail;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.ruptech.firefighting.R;

import java.util.List;
import java.util.Map;


public class PlanDetailActivity extends ActionBarActivity {

    public static final String EXTRA_ITEMS = "EXTRA_ITEMS";
    public static final String EXTRA_SUM = "EXTRA_SUM";
    private List<Map<String, Object>> items;
    private List<Map<String, Object>> sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set content view (which contains a PopupListFragment)
        setContentView(R.layout.activity_plan_detail);
        items = (List<Map<String, Object>>) getIntent().getSerializableExtra(PlanDetailActivity.EXTRA_ITEMS);
        sum = (List<Map<String, Object>>) getIntent().getSerializableExtra(PlanDetailActivity.EXTRA_SUM);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.sample_content_fragment, ItemListFragment.newInstance(items, sum));
            transaction.commit();
        }
    }
}
