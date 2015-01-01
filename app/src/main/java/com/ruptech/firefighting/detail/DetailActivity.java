package com.ruptech.firefighting.detail;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;

import com.ruptech.firefighting.App;
import com.ruptech.firefighting.R;

import java.util.Map;


/**
 */
public class DetailActivity extends ActionBarActivity {

    public static final String ARG_ITEM_ID = "ARG_ITEM_ID";
    public static final String ARG_ITEM = "ARG_ITEM";
    private static final String TAG = DetailActivity.class.getName();
    private String taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        taskId = getIntent().getStringExtra(ARG_ITEM_ID);

        initiateRefresh();

        Log.v(TAG, "onCreate");
    }

    private void initiateRefresh() {
        new TaskBackgroundTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    private void onRefreshComplete(Map<String, Object> task) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DetailTabsFragment fragment = new DetailTabsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEM, (java.io.Serializable) task);
        fragment.setArguments(args);
        transaction.replace(R.id.activity_detail_content_fragment, fragment);
        transaction.commit();
    }

    private class TaskBackgroundTask extends AsyncTask<Void, Void, Map<String, Object>> {

        @Override
        protected Map<String, Object> doInBackground(Void... params) {
            try {
                return App.getHttpServer().getTask(taskId);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Map<String, Object> result) {
            super.onPostExecute(result);

            // Tell the Fragment that the refresh has completed
            onRefreshComplete(result);
        }

    }


}
