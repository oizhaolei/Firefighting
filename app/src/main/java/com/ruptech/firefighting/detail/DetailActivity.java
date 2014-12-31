package com.ruptech.firefighting.detail;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;

import com.ruptech.firefighting.R;


/**
 */
public class DetailActivity extends ActionBarActivity {

    public static final String ARG_ITEM_ID = "ARG_ITEM_ID";
    public static final String ARG_ITEM_STR = "ARG_ITEM_STR";
    private static final String TAG = DetailActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Log.v(TAG, "onCreate");
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            DetailTabsFragment fragment = new DetailTabsFragment();
            transaction.replace(R.id.activity_detail_content_fragment, fragment);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

}
