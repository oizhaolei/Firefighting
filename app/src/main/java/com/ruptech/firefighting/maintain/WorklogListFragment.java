package com.ruptech.firefighting.maintain;

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
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.ruptech.firefighting.App;
import com.ruptech.firefighting.R;
import com.ruptech.firefighting.main.MainActivity;

import org.json.JSONException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class WorklogListFragment extends ListFragment {
    public static final String ARG_WORK_LOGS = "ARG_WORK_LOGS";
    public static final String ARG_WORK_HOUR_SUM = "ARG_WORK_HOUR_SUM";
    private static final String TAG = WorklogListFragment.class.getName();

    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.fragment_detail_worklog_bottom)
    TextView workhourSumTextView;
    private List<Map<String, Object>> worklogs;
    private List<Map<String, Object>> workhoursum;
    private String type;

    public static WorklogListFragment newInstance(List<Map<String, Object>> worklogs, List<Map<String, Object>> workhoursum, String type) {
        WorklogListFragment fragment = new WorklogListFragment();
        Bundle args = new Bundle();
        args.putSerializable(WorklogListFragment.ARG_WORK_LOGS, (java.io.Serializable) worklogs);
        args.putSerializable(WorklogListFragment.ARG_WORK_HOUR_SUM, (java.io.Serializable) workhoursum);
        args.putString(MainActivity.EXTRA_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.fab)
    public void doAdd() {
        try {
            Map<String, Object> emptyWorklog = App.getHttpServer().genEmptyWorklog();
            openDetail(emptyWorklog);
            Toast.makeText(getActivity(), "Add WorkHour", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        worklogs = (List<Map<String, Object>>) getArguments().get(ARG_WORK_LOGS);
        workhoursum = (List<Map<String, Object>>) getArguments().get(ARG_WORK_HOUR_SUM);
        type = getArguments().getString(MainActivity.EXTRA_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_worklogs, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab.attachToListView(getListView());

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), worklogs, R.layout.item_worklog,
                new String[]{"标题", "详细描述"}, new int[]{R.id.item_worklog_name,
                R.id.item_worklog_memo});
        setListAdapter(adapter);

        workhourSumTextView.setText(workhoursum.toString());
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> worklog = (Map<String, Object>) getListAdapter().getItem(position);

        String worklogId = worklog.get("ID").toString();

        new DetailBackgroundTask(worklogId, type).execute();
    }

    private void openDetail(Map<String, Object> worklog) {
        Intent intent = new Intent(getActivity(), WorklogActivity.class);
        intent.putExtra(WorklogActivity.EXTRA_WORKLOG, (Serializable) worklog);
        startActivity(intent);

    }

    private class DetailBackgroundTask extends AsyncTask<Void, Void, Map<String, Object>> {

        private final String workLogId;
        private final String type;
        private ProgressDialog progressDialog;

        public DetailBackgroundTask(String workLogId, String type) {
            this.workLogId = workLogId;
            this.type = type;
        }

        @Override
        protected Map<String, Object> doInBackground(Void... params) {
            try {
                return App.getHttpServer().getWorkLogDetail(workLogId, type);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Map<String, Object> result) {
            super.onPostExecute(result);

            // Tell the Fragment that the refresh has completed
            openDetail(result);
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
