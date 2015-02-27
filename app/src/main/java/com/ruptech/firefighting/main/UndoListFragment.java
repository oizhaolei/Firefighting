package com.ruptech.firefighting.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ruptech.firefighting.App;
import com.ruptech.firefighting.DataType;
import com.ruptech.firefighting.R;
import com.ruptech.firefighting.check.CheckActivity;
import com.ruptech.firefighting.maintain.MaintainActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UndoListFragment extends SwipeRefreshListFragment {

    public static final String TAG = UndoListFragment.class.getSimpleName();

    public static UndoListFragment newInstance() {
        return new UndoListFragment();
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
        List<Map<String, Object>> workers = (ArrayList<Map<String, Object>>)item.get("wokers");

        new TaskBackgroundTask(taskId, type, true).execute();
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
        new UndoListBackgroundTask().execute();
    }

    /**
     * When the AsyncTask finishes, it calls onRefreshComplete(), which updates the data in the
     * ListAdapter and turns off the progress bar.
     */
    private void onRefreshComplete(List<Map<String, Object>> result) {

        if(null != result) {
            ArrayAdapter adapter = new TaskListArrayAdapter(getActivity());
            adapter.addAll(result);
            setListAdapter(adapter);

            // Stop the refreshing indicator
            setRefreshing(false);
        } else {
            Toast.makeText(getActivity(), getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            setRefreshing(false);
        }
    }

    private void openTask(Map<String, Object> task, String type, boolean editable) {
        if(null == task) {
            Toast.makeText(getActivity(), getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            return;
        } else {
            Intent detailIntent;
            if (DataType.TYPE_MAINTAIN.equals(type)) {
                detailIntent = new Intent(getActivity(), MaintainActivity.class);
                detailIntent.putExtra(MaintainActivity.EXTRA_TASK, (Serializable) task);
            } else if(DataType.TYPE_CHECK.equals(type)) {
                detailIntent = new Intent(getActivity(), CheckActivity.class);
                detailIntent.putExtra(CheckActivity.EXTRA_TASK, (Serializable) task);
            } else {
                detailIntent = new Intent(getActivity(), MaintainActivity.class);
            }
            detailIntent.putExtra(MainActivity.EXTRA_TYPE, type);
            detailIntent.putExtra(MainActivity.EXTRA_EDITABLE, editable);
            startActivity(detailIntent);
        }
    }

    private class UndoListBackgroundTask extends AsyncTask<Void, Void, List<Map<String, Object>>> {

        private ProgressDialog progressDialog;

        @Override
        protected List<Map<String, Object>> doInBackground(Void... params) {
            try {
                return App.getHttpServer().getTaskList(DataType.TAB_UNDO);
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

            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), getActivity().getString(R.string.progress_title), getActivity().getString(R.string.progress_message), true, false);
        }

    }

    private class TaskBackgroundTask extends AsyncTask<Void, Void, Map<String, Object>> {

        private final String taskId;
        private final String type;
        private final boolean editable;
        private ProgressDialog progressDialog;

        public TaskBackgroundTask(String taskId, String type, boolean editable) {
            this.taskId = taskId;
            this.type = type;
            this.editable = editable;
        }

        @Override
        protected Map<String, Object> doInBackground(Void... params) {
            try {
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
            openTask(result, type, editable);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), getActivity().getString(R.string.progress_title), getActivity().getString(R.string.progress_message), true, false);
        }


    }
}
