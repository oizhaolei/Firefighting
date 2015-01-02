package com.ruptech.firefighting.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ruptech.firefighting.App;
import com.ruptech.firefighting.R;
import com.ruptech.firefighting.detail.DetailActivity;

import java.util.List;
import java.util.Map;

public class UncheckFragment extends SwipeRefreshListFragment {


    public static final String TAG = UncheckFragment.class.getSimpleName();

    public static UncheckFragment newInstance() {
        return new UncheckFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");

        // Notify the system to allow an options menu for this fragment.
        setHasOptionsMenu(true);
    }

    // BEGIN_INCLUDE (setup_views)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // BEGIN_INCLUDE
        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                initiateRefresh();
            }
        });
        // END_INCLUDE
        initiateRefresh();
    }


    // BEGIN_INCLUDE (initiate_refresh)

    // END_INCLUDE (setup_views)
    public void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> item = (Map<String, Object>) getListAdapter().getItem(position);
        // In single-pane mode, simply start the detail activity
        // for the selected item ID.
        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        detailIntent.putExtra(DetailActivity.ARG_ITEM_ID, (String) item.get("编号"));
        startActivity(detailIntent);
    }
    // END_INCLUDE (initiate_refresh)

    // BEGIN_INCLUDE (refresh_complete)

    /**
     * By abstracting the refresh process to a single method, the app allows both the
     * SwipeGestureLayout onRefresh() method and the Refresh action item to refresh the content.
     */
    private void initiateRefresh() {

        /**
         * Execute the background task, which uses {@link android.os.AsyncTask} to load the data.
         */
        new UncheckTasksBackgroundTask().execute();
    }

    /**
     * When the AsyncTask finishes, it calls onRefreshComplete(), which updates the data in the
     * ListAdapter and turns off the progress bar.
     */
    private void onRefreshComplete(List<Map<String, Object>> result) {

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), result, R.layout.item_task, new String[]{"标题", "任务状态", "派单时间"}, new int[]{R.id.item_task_name,
                R.id.item_task_status, R.id.item_task_date});
        setListAdapter(adapter);

        // Stop the refreshing indicator
        setRefreshing(false);
    }

    private class UncheckTasksBackgroundTask extends AsyncTask<Void, Void, List<Map<String, Object>>> {

        @Override
        protected List<Map<String, Object>> doInBackground(Void... params) {
            try {
                return App.getHttpServer().getUncheckTaskList();
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Map<String, Object>> result) {
            super.onPostExecute(result);

            // Tell the Fragment that the refresh has completed
            onRefreshComplete(result);
        }

    }

}
