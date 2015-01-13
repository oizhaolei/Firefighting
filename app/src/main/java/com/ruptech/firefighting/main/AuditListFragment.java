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
import com.ruptech.firefighting.DataType;
import com.ruptech.firefighting.R;
import com.ruptech.firefighting.check.CheckActivity;
import com.ruptech.firefighting.maintain.MaintainActivity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class AuditListFragment extends SwipeRefreshListFragment {


    public static final String TAG = AuditListFragment.class.getSimpleName();

    public static AuditListFragment newInstance() {
        return new AuditListFragment();
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
        String taskId = (String) item.get("ID");
        String type = (String) item.get("任务类型");

        new TaskBackgroundTask(taskId, type).execute();
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
        new AuditListTasksBackgroundTask().execute();
    }

    /**
     * When the AsyncTask finishes, it calls onRefreshComplete(), which updates the data in the
     * ListAdapter and turns off the progress bar.
     */
    private void onRefreshComplete(List<Map<String, Object>> result) {

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), result, R.layout.item_task, new String[]{"任务名称", "任务状态", "派单时间"}, new int[]{R.id.item_task_name,
                R.id.item_task_status, R.id.item_task_date});
        setListAdapter(adapter);

        // Stop the refreshing indicator
        setRefreshing(false);
    }

    private void openTask(Map<String, Object> task, String type, Map<String, Object> items) {
        Intent detailIntent = null;
        if (DataType.TYPE_MAINTAIN.equals(type)) {
            detailIntent = new Intent(getActivity(), MaintainActivity.class);
        } else {
            detailIntent = new Intent(getActivity(), CheckActivity.class);
        }
        detailIntent.putExtra(MaintainActivity.ARG_DATA, (Serializable) task);
        startActivity(detailIntent);
    }

    private class AuditListTasksBackgroundTask extends AsyncTask<Void, Void, List<Map<String, Object>>> {

        @Override
        protected List<Map<String, Object>> doInBackground(Void... params) {
            try {
                return App.getHttpServer().getTaskList("audit");
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
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

    private class TaskBackgroundTask extends AsyncTask<Void, Void, Map<String, Object>> {

        private final String taskId;
        private final String type;
        private Map<String, Object> items;

        public TaskBackgroundTask(String taskId, String type) {
            this.taskId = taskId;
            this.type = type;
        }

        @Override
        protected Map<String, Object> doInBackground(Void... params) {
            try {
                items = App.getHttpServer().getItems(taskId, type);

                return App.getHttpServer().getTask(taskId, type);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Map<String, Object> result) {
            super.onPostExecute(result);

            // Tell the Fragment that the refresh has completed
            openTask(result, type, items);
        }
    }
}
