package com.ruptech.firefighting.maintain;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ruptech.firefighting.App;
import com.ruptech.firefighting.DataType;
import com.ruptech.firefighting.R;
import com.ruptech.firefighting.dialog.ChoiceDialog;
import com.ruptech.firefighting.dialog.DateTimePickerDialog;
import com.ruptech.firefighting.dialog.OnChangeListener;
import com.ruptech.firefighting.main.MainActivity;
import com.ruptech.firefighting.utils.Utils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class WorkHourActivity extends ActionBarActivity {
    public static final String EXTRA_WORKHOUR = "EXTRA_WORKHOUR";
    public static final String EXTRA_WORKLOGID = "EXTRA_WORKLOGID";
    public static final String EXTRA_WORKERS = "EXTRA_WORKERS";
    public static final int RETURN_WORKHOUR_CODE = 100;
    @InjectView(R.id.activity_workhour_worker)
    TextView mWorkerTextView;
    @InjectView(R.id.activity_workhour_spend)
    TextView mSpendTextView;
    @InjectView(R.id.activity_workhour_start)
    TextView mStartTextView;
    @InjectView(R.id.activity_workhour_end)
    TextView mEndTextView;
    @InjectView(R.id.activity_workhour_worker_layout)
    RelativeLayout mWorkerRelativeLayout;
    @InjectView(R.id.activity_workhour_start_layout)
    RelativeLayout mStartRelativeLayout;
    @InjectView(R.id.activity_workhour_end_layout)
    RelativeLayout mEndRelativeLayout;
    @InjectView(R.id.activity_workhour_worker_next)
    ImageView mWorkerNextImage;
    @InjectView(R.id.activity_workhour_start_next)
    ImageView mStartNextImage;
    @InjectView(R.id.activity_workhour_end_next)
    ImageView mEndNextImage;

    private String type;
    private Map<String, Object> workHour;
    private boolean editable;
    private String worklogId;
    private List<Map<String, Object>> workers;
    private static final String TAG = WorkHourActivity.class.getName();

    @OnClick(R.id.activity_workhour_worker_layout)
    public void changeWorkHourWorker() {
        if(editable) {
            Map<Integer, String> choices = DataType.getWorkerMap(workers);
            int userid = -1;
            if(!("").equals((String)workHour.get("维修人员ID"))) {
                userid = Integer.valueOf((String)workHour.get("维修人员ID"));
            }
            ChoiceDialog dialog = ChoiceDialog.newInstance(
                    getString(R.string.field_workhour_worker)
                    , choices, userid
                    , new OnChangeListener() {
                @Override
                public void onChange(String oldValue, String newValue) {

                    if(null != worklogId && !("").equals(worklogId)) {
                        String id = (String)workHour.get("ID");
                        if (DataType.TYPE_MAINTAIN.equals(type)) {
                            if (null == id || ("").equals(id)) {
                                new WorkhourEditTask("", type, "维修人员ID,维修过程ID", newValue + "," + worklogId).execute();
                            } else {
                                new WorkhourEditTask((String)workHour.get("ID"), type, "维修人员ID", newValue).execute();
                            }
                        } else if (DataType.TYPE_CHECK.equals(type)) {
                            if (null == id || ("").equals(id)) {
                                new WorkhourEditTask("", type, "维修人员ID,计划检查过程ID,CId,FId,SId",
                                        newValue + "," + worklogId + "," + (String)workHour.get("CId") + "," + (String)workHour.get("FId") + "," + (String)workHour.get("SId")
                                ).execute();
                            } else {
                                new WorkhourEditTask((String)workHour.get("ID"), type, "维修人员ID", newValue).execute();
                            }
                        }
                    }

                    workHour.put("维修人员ID", newValue);
                    workHour.put("维修人员姓名", DataType.getWorker(Integer.valueOf(newValue)));
                    displayData();
                }
            });

            dialog.show(getFragmentManager(), getString(R.string.field_workhour_worker));
        }
    }

    @OnClick(R.id.activity_workhour_start_layout)
    public void changeWorkHourStart() {
        if(editable) {
            if(("").equals((String)workHour.get("维修人员ID"))) {
                Toast.makeText(WorkHourActivity.this, "请先选择维修人员", Toast.LENGTH_SHORT).show();
                return;
            }
            DateTimePickerDialog dialog = DateTimePickerDialog.newInstance(getString(R.string.field_workhour_start),
                    (String)workHour.get("开始时间"),
                new OnChangeListener() {
                @Override
                public void onChange(String oldValue, String newValue) {
                    newValue = newValue.substring(0,newValue.length() - 2) + "00";
                    // 计算工时
                    SimpleDateFormat sdf = new SimpleDateFormat( DataType.DATETIMEFORMAT );
                    String hours = "0.0";
                    try {
                        if(!("").equals((String)workHour.get("结束时间"))) {
                            long interval_seconds = ( sdf.parse((String)workHour.get("结束时间")).getTime() - sdf.parse(newValue).getTime() ) / 1000;
                            if(interval_seconds > 0) {
                                hours = Utils.getWorkHour(interval_seconds);
                            } else {
                                Toast.makeText(WorkHourActivity.this, "开始时间不能比结束时间晚。", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    } catch (ParseException e) {
                    }

                    // 保存数据
                    if(null != worklogId && !("").equals(worklogId)) {
                        String id = (String)workHour.get("ID");
                        if (DataType.TYPE_MAINTAIN.equals(type)) {
                            if (null == id || ("").equals(id)) {
                                new WorkhourEditTask("", type, "开始时间,维修过程ID,工时", (newValue + "," + worklogId + "," + hours) ).execute();
                            } else {
                                new WorkhourEditTask((String)workHour.get("ID"), type, "开始时间,工时", (newValue + "," + hours)).execute();
                            }
                        } else if (DataType.TYPE_CHECK.equals(type)) {
                            if (null == id || ("").equals(id)) {
                                new WorkhourEditTask("", type, "开始时间,计划检查过程ID,CId,FId,SId,工时",
                                        (newValue + "," + worklogId + "," + (String)workHour.get("CId") + "," + (String)workHour.get("FId") + "," + (String)workHour.get("SId") + "," + hours)
                                ).execute();
                            } else {
                                new WorkhourEditTask((String)workHour.get("ID"), type, "开始时间,工时", (newValue + "," + hours)).execute();
                            }
                        }
                    }

                    workHour.put("开始时间", newValue);
                    workHour.put("工时", hours);
                    displayData();
                }
            });

            dialog.show(getFragmentManager(), getString(R.string.field_workhour_start));
        }
    }

    @OnClick(R.id.activity_workhour_end_layout)
    public void changeWorkHourEnd() {
        if(editable) {
            if(("").equals((String)workHour.get("维修人员ID"))) {
                Toast.makeText(WorkHourActivity.this, "请先选择维修人员", Toast.LENGTH_SHORT).show();
                return;
            }
            DateTimePickerDialog dialog = DateTimePickerDialog.newInstance(getString(R.string.field_workhour_end),
                    (String)workHour.get("结束时间"),
                new OnChangeListener() {
                    @Override
                    public void onChange(String oldValue, String newValue) {
                        newValue = newValue.substring(0,newValue.length() - 2) + "00";
                        // 计算工时
                        SimpleDateFormat sdf = new SimpleDateFormat( DataType.DATETIMEFORMAT );
                        String hours = "0";
                        try {
                            if(!("").equals((String)workHour.get("开始时间"))) {
                                long interval_seconds = (sdf.parse(newValue).getTime() - sdf.parse((String)workHour.get("开始时间")).getTime()) / 1000;
                                if(interval_seconds > 0) {
                                    hours = Utils.getWorkHour(interval_seconds);
                                } else {
                                    Toast.makeText(WorkHourActivity.this, "结束时间不能比开始时间早。", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        } catch (ParseException e) {
                        }

                        // 保存数据
                        if(null != worklogId && !("").equals(worklogId)) {
                            String id = (String)workHour.get("ID");
                            if (DataType.TYPE_MAINTAIN.equals(type)) {
                                if (null == id || ("").equals(id)) {
                                    new WorkhourEditTask("", type, "结束时间,维修过程ID,工时", (newValue + "," + worklogId + "," + hours) ).execute();
                                } else {
                                    new WorkhourEditTask((String)workHour.get("ID"), type, "结束时间,工时", (newValue + "," + hours)).execute();
                                }
                            } else if (DataType.TYPE_CHECK.equals(type)) {
                                if (null == id || ("").equals(id)) {
                                    new WorkhourEditTask("", type, "结束时间,计划检查过程ID,CId,FId,SId,工时",
                                            (newValue + "," + worklogId + "," + (String)workHour.get("CId") + "," + (String)workHour.get("FId") + "," + (String)workHour.get("SId") + "," + hours)
                                    ).execute();
                                } else {
                                    new WorkhourEditTask((String)workHour.get("ID"), type, "结束时间,工时", (newValue + "," + hours)).execute();
                                }
                            }
                        }

                        workHour.put("结束时间", newValue);
                        workHour.put("工时", hours);
                        displayData();
                    }
                });
            dialog.show(getFragmentManager(), getString(R.string.field_workhour_end));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workhour);
        ButterKnife.inject(this);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        type = getIntent().getStringExtra(MainActivity.EXTRA_TYPE);
        workHour = (Map<String, Object>) getIntent().getSerializableExtra(EXTRA_WORKHOUR);
        editable = getIntent().getBooleanExtra(MainActivity.EXTRA_EDITABLE, false);
        worklogId = getIntent().getStringExtra(EXTRA_WORKLOGID);
        workers = (List<Map<String, Object>>) getIntent().getSerializableExtra(EXTRA_WORKERS);
        displayData();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.putExtra(WorkLogActivity.RETURN_WORKHOUR, (Serializable)workHour);
            //设置结果信息
            setResult(Activity.RESULT_OK,intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void displayData() {
        mWorkerTextView.setText((String) workHour.get("维修人员姓名"));
        mSpendTextView.setText((String) workHour.get("工时"));
        mStartTextView.setText((String) workHour.get("开始时间"));
        mEndTextView.setText((String) workHour.get("结束时间"));

        if(!editable) {
            mWorkerRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mStartRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mEndRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mWorkerNextImage.setVisibility(View.GONE);
            mStartNextImage.setVisibility(View.GONE);
            mEndNextImage.setVisibility(View.GONE);
        }
    }

    private class WorkhourEditTask extends AsyncTask<Void, Void, String> {

        private final String workhourId;
        private final String type;
        String[] columns;
        String[] values;
        private ProgressDialog progressDialog;

        public WorkhourEditTask(String workhourId, String type, String column, String value) {
            this.workhourId = workhourId;
            this.type = type;
            this.columns = new String[]{column};
            this.values = new String[]{value};
        }


        @Override
        protected String doInBackground(Void... params) {
            try {
                return App.getHttpServer().editWorkHour(workhourId, type, columns, values);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(final String newId) {
            afterWorkHourEdit(newId);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(WorkHourActivity.this, WorkHourActivity.this.getString(R.string.progress_title), WorkHourActivity.this.getString(R.string.progress_message), true, false);
        }
    }

    private void afterWorkHourEdit(String newId) {
        if(null == newId) {
            Toast.makeText(WorkHourActivity.this, getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            return;
        } else if(!("").equals(newId)) {
            workHour.put("ID",newId);
        }
    }
}
