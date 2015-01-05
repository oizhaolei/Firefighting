package com.ruptech.firefighting.worklog;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.ruptech.firefighting.R;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class WorklogActivity extends ActionBarActivity {
    public static final String ARG_ITEM = "ARG_ITEM";
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
        Toast.makeText(this, "activity_worklog_name_layout", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.activity_worklog_memo_layout)
    public void changeWorklogMemo() {
        Toast.makeText(this, "activity_worklog_memo_layout", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worklog);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        worklog = (Map<String, Object>) getIntent().getSerializableExtra(ARG_ITEM);
        displayData();
    }

    private void displayData() {
        mNameTextView.setText((String) worklog.get("标题"));
        mMemoTextView.setText((String) worklog.get("维修工作描述"));

        List<Map<String, Object>> workhours = (List<Map<String, Object>>) worklog.get("workhours");
        SimpleAdapter adapter = new SimpleAdapter(this, workhours, R.layout.item_workhour,
                new String[]{"维修人员姓名", "工时", "开始时间", "结束时间"},
                new int[]{R.id.item_workhour_worker,
                        R.id.item_workhour_spend,
                        R.id.item_workhour_start,
                        R.id.item_workhour_end});
        mWorkhourListView.setAdapter(adapter);

        fab.attachToListView(mWorkhourListView);
    }

}
