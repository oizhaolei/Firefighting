package com.ruptech.firefighting.item;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.ruptech.firefighting.R;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ItemActivity extends ActionBarActivity {
    public static final String ARG_ITEM = "ARG_ITEM";
    @InjectView(R.id.activity_item_no)
    TextView mNoTextView;
    @InjectView(R.id.activity_item_company)
    TextView mCompanyTextView;
    @InjectView(R.id.activity_item_name)
    TextView mNameTextView;
    @InjectView(R.id.activity_item_status)
    TextView mStatusTextView;
    @InjectView(R.id.activity_item_source)
    TextView mSourceTextView;
    @InjectView(R.id.activity_item_report_date)
    TextView mReportDateTextView;
    @InjectView(R.id.activity_item_end_date)
    TextView mEndDateTextView;
    @InjectView(R.id.activity_item_system)
    TextView mSystemTextView;
    @InjectView(R.id.activity_item_device)
    TextView mDeviceTextView;
    @InjectView(R.id.activity_item_error)
    TextView mErrorTextView;
    @InjectView(R.id.activity_item_resolve)
    TextView mResolveTextView;
    private Map<String, Object> item;

    @OnClick(R.id.activity_item_system_layout)
    public void changeItemSystem() {
        Toast.makeText(this, "activity_item_system_layout", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.activity_item_device_layout)
    public void changeItemDevice() {
        Toast.makeText(this, "activity_item_device_layout", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.activity_item_error_layout)
    public void changeItemError() {
        Toast.makeText(this, "activity_item_error_layout", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.activity_item_resolve_layout)
    public void changeItemResolve() {
        Toast.makeText(this, "activity_item_resolve_layout", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        item = (Map<String, Object>) getIntent().getSerializableExtra(ARG_ITEM);
        displayData();
    }

    private void displayData() {
        mNoTextView.setText((String) item.get("部件ID"));
        mCompanyTextView.setText((String) item.get("单位名称"));
        mNameTextView.setText((String) item.get("系统名称"));
        mStatusTextView.setText((String) item.get("维修状态"));
        mSourceTextView.setText((String) item.get("部件报修来源"));
        mReportDateTextView.setText((String) item.get("报修时间"));
        mEndDateTextView.setText((String) item.get("结束时间"));
        mSystemTextView.setText((String) item.get("系统类型ID"));
        mDeviceTextView.setText((String) item.get("设备单项"));
        mErrorTextView.setText((String) item.get("故障单项"));
        mResolveTextView.setText((String) item.get("故障内容"));
    }

}
