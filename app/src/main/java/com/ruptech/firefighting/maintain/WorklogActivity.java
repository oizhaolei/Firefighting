package com.ruptech.firefighting.maintain;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.ruptech.firefighting.App;
import com.ruptech.firefighting.DataType;
import com.ruptech.firefighting.R;
import com.ruptech.firefighting.dialog.EditTextDialog;
import com.ruptech.firefighting.dialog.OnChangeListener;
import com.ruptech.firefighting.main.MainActivity;

import org.json.JSONException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class WorklogActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    public static final String EXTRA_WORKLOG = "EXTRA_WORKLOG";
    private static final String TAG = ItemActivity.class.getName();
    @InjectView(R.id.activity_worklog_name)
    TextView mNameTextView;
    @InjectView(R.id.activity_worklog_memo)
    TextView mMemoTextView;
    @InjectView(R.id.activity_worklog_workhours)
    ListView mWorkhourListView;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    String type;
    private Map<String, Object> worklog;

    @OnClick(R.id.fab)
    public void doAdd() {
        try {
            Map<String, Object> emptyWorkHour = App.getHttpServer().genEmptyWorkHour();
            openWorkHourDetail(emptyWorkHour);
            Toast.makeText(this, "Add WorkHour", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @OnClick(R.id.activity_worklog_name_layout)
    public void changeWorklogName() {
        EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_work_title),
                worklog.get("标题").toString(),
                new OnChangeListener() {
                    @Override
                    public void onChange(String oldValue, String newValue) {
                        new WorklogEditTask(worklog.get("ID").toString(), type, "标题", newValue).execute();
                        worklog.put("标题", newValue);
                        displayData();
                    }
                });

        dialog.show(getFragmentManager(), getString(R.string.field_work_title));
    }

    @OnClick(R.id.activity_worklog_memo_layout)
    public void changeWorklogMemo() {
        EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_work_memo),
                worklog.get("详细描述").toString(),
                new OnChangeListener() {
                    @Override
                    public void onChange(String oldValue, String newValue) {
                        new WorklogEditTask(worklog.get("ID").toString(), type, "详细描述", newValue).execute();
                        worklog.put("详细描述", newValue);
                        displayData();
                    }
                });
        dialog.setMultiLine(true);

        dialog.show(getFragmentManager(), getString(R.string.field_work_memo));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worklog);
        ButterKnife.inject(this);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        worklog = (Map<String, Object>) getIntent().getSerializableExtra(EXTRA_WORKLOG);
        type = getIntent().getStringExtra(MainActivity.EXTRA_TYPE);

        displayData();
    }

    private void displayData() {
        mNameTextView.setText((String) worklog.get("标题"));
        mMemoTextView.setText((String) worklog.get("详细描述"));

        List<Map<String, Object>> workhours = (List<Map<String, Object>>) worklog.get("workhours");
        if (workhours == null) {
            workhours = DataType.EMPTY_LIST;
        }
        SimpleAdapter adapter = new SimpleAdapter(this, workhours, R.layout.item_workhour,
                new String[]{"维修人员姓名", "工时", "开始时间", "结束时间"},
                new int[]{R.id.item_workhour_worker,
                        R.id.item_workhour_spend,
                        R.id.item_workhour_start,
                        R.id.item_workhour_end});
        mWorkhourListView.setAdapter(adapter);
        mWorkhourListView.setOnItemClickListener(this);

        fab.attachToListView(mWorkhourListView);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, Object> workhour = (Map<String, Object>) mWorkhourListView.getAdapter().getItem(position);
        // In single-pane mode, simply start the detail activity
        // for the selected item ID.
        openWorkHourDetail(workhour);
    }

    private void openWorkHourDetail(Map<String, Object> workhour) {
        Intent workhourIntent = new Intent(this, WorkHourActivity.class);
        workhourIntent.putExtra(WorkHourActivity.ARG_ITEM, (Serializable) workhour);
        startActivity(workhourIntent);
    }

    private class WorklogEditTask extends AsyncTask<Void, Void, Boolean> {

        private final String worklogId;
        private final String type;
        String[] columns;
        String[] values;
        private ProgressDialog progressDialog;

        public WorklogEditTask(String worklogId, String type, String column, String value) {
            this.worklogId = worklogId;
            this.type = type;
            columns = new String[]{column};
            values = new String[]{value};
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return App.getHttpServer().editWorklog(worklogId, type, columns, values);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(WorklogActivity.this, WorklogActivity.this.getString(R.string.progress_title), WorklogActivity.this.getString(R.string.progress_message), true, false);
        }
    }
}
