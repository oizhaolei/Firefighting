package com.ruptech.firefighting.maintain;

import android.app.ProgressDialog;
import android.app.admin.DeviceAdminInfo;
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
import com.ruptech.firefighting.DataType;
import com.ruptech.firefighting.R;
import com.ruptech.firefighting.main.MainActivity;

import org.json.JSONArray;
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
    public static final String ARG_WORK_TASK_ID = "ARG_WORK_TASK_ID";
    public static final String RETURN_WORKLOG = "RETURN_WORKLOG";

    private static final String TAG = WorklogListFragment.class.getName();

    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.fragment_detail_worklog_bottom)
    TextView workhourSumTextView;
    private List<Map<String, Object>> worklogs;
    private List<Map<String, Object>> workhoursum;
    private String type;
    private String taskId;
    private List<Map<String, Object>> workers;
    private int currentWorkLog;

    public static WorklogListFragment newInstance(List<Map<String, Object>> worklogs, List<Map<String, Object>> workhoursum, String type, String taskId, List<Map<String, Object>> workers) {
        WorklogListFragment fragment = new WorklogListFragment();
        Bundle args = new Bundle();
        args.putSerializable(WorklogListFragment.ARG_WORK_LOGS, (Serializable) worklogs);
        args.putSerializable(WorklogListFragment.ARG_WORK_HOUR_SUM, (Serializable) workhoursum);
        args.putString(MainActivity.EXTRA_TYPE, type);
        args.putString(WorklogListFragment.ARG_WORK_TASK_ID, taskId);
        args.putSerializable(MainActivity.EXTRA_WORKERS, (Serializable) workers);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.fab)
    public void doAdd() {
        try {
            Map<String, Object> emptyWorklog = App.getHttpServer().genEmptyWorklog(taskId, type);
            openDetail(emptyWorklog, true, workers);

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        currentWorkLog = worklogs.size();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        worklogs = (List<Map<String, Object>>) getArguments().get(ARG_WORK_LOGS);
        workhoursum = (List<Map<String, Object>>) getArguments().get(ARG_WORK_HOUR_SUM);
        type = getArguments().getString(MainActivity.EXTRA_TYPE);
        taskId = (String) getArguments().get(ARG_WORK_TASK_ID);
        workers = (List<Map<String, Object>>) getArguments().get(MainActivity.EXTRA_WORKERS);
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

        String workhour_sum = "";
        for(Map<String, Object> workhour : workhoursum) {
            workhour_sum += (String)workhour.get("姓名")
                    + "(" + (String)workhour.get("工时") + ")"
                    + " ";
        }
        if(workhoursum.size() > 0) {
            workhour_sum = workhour_sum.substring(0,workhour_sum.length()-1);
        }
        workhourSumTextView.setText(workhour_sum);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == WorklogActivity.RETURN_WORKLOG_CODE) {
            boolean needRefresh = false;
            Map<String, Object> worklog = (Map<String, Object>) data.getSerializableExtra(RETURN_WORKLOG);
            if (currentWorkLog == worklogs.size()) {
                // 新增部件
                if( !(("").equals((String)worklog.get("标题"))
                        && ("").equals((String)worklog.get("详细描述"))
                        && (("").equals((String)worklog.get("是否提交")) || ("0").equals((String)worklog.get("是否提交")))
                    ) ) {
                    worklogs.add(0, worklog);
                    needRefresh = true;
                }
            } else {
                worklogs.remove(currentWorkLog);
                worklogs.add(currentWorkLog, worklog);
                needRefresh = true;
            }
            if(needRefresh) {
                ((SimpleAdapter) getListAdapter()).notifyDataSetChanged();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> worklog = (Map<String, Object>) getListAdapter().getItem(position);

        String worklogId = (String)worklog.get("ID");

        if(position == 0) {

            // 只能修改最后一条工作记录
            new DetailBackgroundTask(worklogId, type, true, workers).execute();
        } else {

            // 只能修改最后一条工作记录
            new DetailBackgroundTask(worklogId, type, false, workers).execute();
        }
        currentWorkLog = position;
    }

    private void openDetail(Map<String, Object> worklog, boolean editable, List<Map<String, Object>> workers) {
        Intent intent = new Intent(getActivity(), WorklogActivity.class);
        intent.putExtra(WorklogActivity.EXTRA_WORKLOG, (Serializable) worklog);
        intent.putExtra(WorklogActivity.EXTRA_EDITABLE, new Boolean(editable));
        intent.putExtra(MainActivity.EXTRA_TYPE, type);
        intent.putExtra(MainActivity.EXTRA_WORKERS, (Serializable)workers);
        startActivityForResult(intent, WorklogActivity.RETURN_WORKLOG_CODE);

    }

    private class DetailBackgroundTask extends AsyncTask<Void, Void, Map<String, Object>> {

        private final String workLogId;
        private final String type;
        private final boolean editable;
        private final List<Map<String, Object>> workers;
        private ProgressDialog progressDialog;

        public DetailBackgroundTask(String workLogId, String type, boolean editable, List<Map<String, Object>> workers) {
            this.workLogId = workLogId;
            this.type = type;
            this.editable = editable;
            this.workers = workers;
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
            openDetail(result, this.editable, workers);
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
