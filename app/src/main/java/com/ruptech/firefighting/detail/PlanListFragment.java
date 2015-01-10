package com.ruptech.firefighting.detail;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.ruptech.firefighting.App;
import com.ruptech.firefighting.DataType;
import com.ruptech.firefighting.R;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PlanListFragment extends ListFragment {
    private static final String TAG = PlanListFragment.class.getName();

    @InjectView(R.id.fab)
    FloatingActionButton fab;
    private List<Map<String, Object>> plans;

    public static PlanListFragment newInstance(List<Map<String, Object>> plans) {
        PlanListFragment fragment = new PlanListFragment();
        Bundle args = new Bundle();
        args.putSerializable(MaintainActivity.ARG_DATA, (Serializable) plans);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.fab)
    public void doAdd() {
        Toast.makeText(getActivity(), "Add Plan", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        plans = (List<Map<String, Object>>) getArguments().get(MaintainActivity.ARG_DATA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_plans, null);
        ButterKnife.inject(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), plans, R.layout.item_plan,
                new String[]{"单位名称", "系统名称"},
                new int[]{R.id.item_plan_company, R.id.item_plan_name});
        setListAdapter(adapter);

        fab.attachToListView(getListView());
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> plan = (Map<String, Object>) getListAdapter().getItem(position);
        // In single-pane mode, simply start the detail activity
        // for the selected item ID.

        String planId = plan.get("SId").toString();
        new TaskBackgroundTask(planId, DataType.TYPE_CHECK).execute();

    }

    private void openTask(List<Map<String, Object>> items, List<Map<String, Object>> sum) {

        Intent intent = new Intent(getActivity(), PlanDetailActivity.class);
        intent.putExtra(PlanDetailActivity.EXTRA_ITEMS, (Serializable) items);
        intent.putExtra(PlanDetailActivity.EXTRA_SUM, (Serializable) sum);
        startActivity(intent);
    }

    private class TaskBackgroundTask extends AsyncTask<Void, Void, Map<String, Object>> {

        private final String planId;
        private final String type;

        public TaskBackgroundTask(String taskId, String type) {
            this.planId = taskId;
            this.type = type;
        }

        @Override
        protected Map<String, Object> doInBackground(Void... params) {
            try {
                return App.getHttpServer().getItems(planId, type);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Map<String, Object> result) {
            super.onPostExecute(result);

            List<Map<String, Object>> sum = (List<Map<String, Object>>) result.get("sum");
            List<Map<String, Object>> items = (List<Map<String, Object>>) result.get("detail");
            // Tell the Fragment that the refresh has completed
            openTask(items, sum);
        }
    }

}
