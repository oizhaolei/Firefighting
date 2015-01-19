package com.ruptech.firefighting.check;

import android.app.ProgressDialog;
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

import com.ruptech.firefighting.App;
import com.ruptech.firefighting.R;
import com.ruptech.firefighting.main.MainActivity;
import com.ruptech.firefighting.maintain.MaintainActivity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

public class PlanListFragment extends ListFragment {
    private static final String TAG = PlanListFragment.class.getName();

    private List<Map<String, Object>> plans;
    private String type;

    public static PlanListFragment newInstance(List<Map<String, Object>> plans, String type) {
        PlanListFragment fragment = new PlanListFragment();
        Bundle args = new Bundle();
        args.putSerializable(MaintainActivity.EXTRA_TASK, (Serializable) plans);
        args.putString(MainActivity.EXTRA_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        plans = (List<Map<String, Object>>) getArguments().get(MaintainActivity.EXTRA_TASK);
        type = getArguments().getString(MainActivity.EXTRA_TYPE);
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

    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> plan = (Map<String, Object>) getListAdapter().getItem(position);

        String planId = plan.get("SId").toString();
        new ItemListBackgroundTask(planId, type).execute();

    }

    private void openItemList(List<Map<String, Object>> items, List<Map<String, Object>> sum) {

        Intent intent = new Intent(getActivity(), ItemListActivity.class);
        intent.putExtra(ItemListActivity.EXTRA_ITEMS, (Serializable) items);
        intent.putExtra(ItemListActivity.EXTRA_SUM, (Serializable) sum);
        intent.putExtra(MainActivity.EXTRA_TYPE, type);
        startActivity(intent);
    }

    private class ItemListBackgroundTask extends AsyncTask<Void, Void, Map<String, Object>> {

        private final String planId;
        private final String type;
        private ProgressDialog progressDialog;

        public ItemListBackgroundTask(String taskId, String type) {
            this.planId = taskId;
            this.type = type;
        }

        @Override
        protected Map<String, Object> doInBackground(Void... params) {
            try {
                return App.getHttpServer().getItemList(planId, type);
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
            openItemList(items, sum);
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
