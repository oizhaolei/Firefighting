/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

/**
 * A sample which shows how to use {@link android.support.v4.widget.SwipeRefreshLayout} within a
 * {@link android.support.v4.app.ListFragment} to add the 'swipe-to-refresh' gesture to a
 * {@link android.widget.ListView}. This is provided through the provided re-usable
 * {@link SwipeRefreshListFragment} class.
 * <p/>
 * <p>To provide an accessible way to trigger the refresh, this app also provides a refresh
 * action item. This item should be displayed in the Action Bar's overflow item.
 * <p/>
 * <p>In this sample app, the refresh updates the ListView with a random set of new items.
 * <p/>
 * <p>This sample also provides the functionality to change the colors displayed in the
 * {@link android.support.v4.widget.SwipeRefreshLayout} through the options menu. This is meant to
 * showcase the use of color rather than being something that should be integrated into apps.
 */
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
        String item = (String) getListAdapter().getItem(position);
        // In single-pane mode, simply start the detail activity
        // for the selected item ID.
        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        detailIntent.putExtra(DetailActivity.ARG_ITEM_ID, id);
        detailIntent.putExtra(DetailActivity.ARG_ITEM_STR, item);
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
        new UncheckTaskBackgroundTask().execute();
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

    /**
     * Dummy {@link android.os.AsyncTask} which simulates a long running task to fetch new cheeses.
     */
    private class UncheckTaskBackgroundTask extends AsyncTask<Void, Void, List<Map<String, Object>>> {

        @Override
        protected List<Map<String, Object>> doInBackground(Void... params) {
            try {
                return App.getHttpServer().getTodoTaskList();
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
