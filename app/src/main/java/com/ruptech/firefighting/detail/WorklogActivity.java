package com.ruptech.firefighting.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.ruptech.firefighting.DataType;
import com.ruptech.firefighting.R;
import com.ruptech.firefighting.dialog.EditTextDialog;
import com.ruptech.firefighting.dialog.OnChangeListener;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class WorklogActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    public static final String ARG_ITEM = "ARG_DATA";
    @InjectView(R.id.activity_worklog_name)
    TextView mNameTextView;
    @InjectView(R.id.activity_worklog_memo)
    TextView mMemoTextView;
    @InjectView(R.id.activity_worklog_workhours)
    ListView mWorkhourListView;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    private Map<String, Object> worklog;

    @OnClick(R.id.fab)
    public void doAdd() {
        Toast.makeText(this, "Add Workhour", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.activity_worklog_name_layout)
    public void changeWorklogName() {
        EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_work_title),
                worklog.get("标题").toString(),
                new OnChangeListener() {
                    @Override
                    public void onChange(Object oldValue, Object newValue) {
                        // TODO save to server
                        worklog.put("标题", newValue);
                        displayData();
                    }
                });

        dialog.show(getFragmentManager(), getString(R.string.field_work_title));
    }

    @OnClick(R.id.activity_worklog_memo_layout)
    public void changeWorklogMemo() {
        EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_work_memo),
                worklog.get("维修工作描述").toString(),
                new OnChangeListener() {
                    @Override
                    public void onChange(Object oldValue, Object newValue) {
                        // TODO save to server
                        worklog.put("维修工作描述", newValue);
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

        worklog = (Map<String, Object>) getIntent().getSerializableExtra(ARG_ITEM);
        displayData();
    }

    private void displayData() {
        mNameTextView.setText((String) worklog.get("标题"));
        mMemoTextView.setText((String) worklog.get("维修工作描述"));

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
        Intent workhourIntent = new Intent(this, WorkHourActivity.class);
        workhourIntent.putExtra(WorkHourActivity.ARG_ITEM, (Serializable) workhour);
        startActivity(workhourIntent);
    }
}
