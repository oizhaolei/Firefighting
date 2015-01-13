package com.ruptech.firefighting.maintain;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ruptech.firefighting.R;
import com.ruptech.firefighting.dialog.DateTimePickerDialog;
import com.ruptech.firefighting.dialog.EditTextDialog;
import com.ruptech.firefighting.dialog.OnChangeListener;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class WorkHourActivity extends ActionBarActivity {
    public static final String ARG_ITEM = "ARG_DATA";
    @InjectView(R.id.activity_workhour_worker)
    TextView mWorkerTextView;
    @InjectView(R.id.activity_workhour_spend)
    TextView mSpendTextView;
    @InjectView(R.id.activity_workhour_start)
    TextView mStartTextView;
    @InjectView(R.id.activity_workhour_end)
    TextView mEndTextView;

    private Map<String, Object> workHour;

    @OnClick(R.id.activity_workhour_worker_layout)
    public void changeWorkHourWorker() {
        EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_workhour_worker),
                workHour.get("维修人员姓名").toString(),
                new OnChangeListener() {
                    @Override
                    public void onChange(Object oldValue, Object newValue) {
                        // TODO save to server
                        workHour.put("维修人员姓名", newValue);
                        displayData();
                    }
                });

        dialog.show(getFragmentManager(), getString(R.string.field_workhour_worker));
    }

    @OnClick(R.id.activity_workhour_start_layout)
    public void changeWorkHourStart() {
        DateTimePickerDialog dialog = DateTimePickerDialog.newInstance(getString(R.string.field_workhour_start),
                workHour.get("开始时间").toString(),
                new OnChangeListener() {
                    @Override
                    public void onChange(Object oldValue, Object newValue) {
                        // TODO save to server
                        workHour.put("开始时间", newValue);
                        displayData();
                    }
                });

        dialog.show(getFragmentManager(), getString(R.string.field_workhour_start));
    }

    @OnClick(R.id.activity_workhour_end_layout)
    public void changeWorkHourEnd() {
        DateTimePickerDialog dialog = DateTimePickerDialog.newInstance(getString(R.string.field_workhour_end),
                workHour.get("结束时间").toString(),
                new OnChangeListener() {
                    @Override
                    public void onChange(Object oldValue, Object newValue) {
                        // TODO save to server
                        workHour.put("结束时间", newValue);
                        displayData();
                    }
                });

        dialog.show(getFragmentManager(), getString(R.string.field_workhour_end));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workhour);
        ButterKnife.inject(this);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        workHour = (Map<String, Object>) getIntent().getSerializableExtra(ARG_ITEM);
        displayData();
    }

    private void displayData() {
        mWorkerTextView.setText((String) workHour.get("维修人员姓名"));
        mSpendTextView.setText((String) workHour.get("工时"));
        mStartTextView.setText((String) workHour.get("开始时间"));
        mEndTextView.setText((String) workHour.get("结束时间"));
    }
}
