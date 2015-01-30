package com.ruptech.firefighting.maintain;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class WorklogActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    public static final String EXTRA_WORKLOG = "EXTRA_WORKLOG";
    public static final String EXTRA_EDITABLE = "EXTRA_EDITABLE";
    public static final String RETURN_WORKHOUR = "RETURN_WORKHOUR";
    public static final int RETURN_WORKLOG_CODE = 300;
    private static final String TAG = WorklogActivity.class.getName();
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
    private boolean editable;
    private List<Map<String, Object>> workers;
    private int currentWorkHour = -1;
    List<Map<String, Object>> workhours;

    @InjectView(R.id.activity_worklog_name_layout)
    RelativeLayout mNameRelativeLayout;
    @InjectView(R.id.activity_worklog_memo_layout)
    RelativeLayout mMemoRelativeLayout;
    @InjectView(R.id.activity_worklog_name_next)
    ImageView mNameImageView;
    @InjectView(R.id.activity_worklog_memo_next)
    ImageView mMemoImageView;
    @InjectView(R.id.activity_worklog_save_temp)
    Button mTemporarySaveBtn;
    @InjectView(R.id.activity_worklog_save)
    Button mSaveBtn;

    @OnClick(R.id.fab)
    public void doAdd() {
        if (("").equals((String)worklog.get("标题"))) {
            Toast.makeText(WorklogActivity.this, "请先填写标题", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String worklogId = (String)worklog.get("ID");
            if(DataType.TYPE_MAINTAIN.equals(type)) {
                Map<String, Object> emptyWorkHour = App.getHttpServer().genEmptyWorkHour(
                        type, "" , "", "");
                openWorkHourDetail(emptyWorkHour, worklogId, workers);
            } else if(DataType.TYPE_CHECK.equals(type)) {
                Map<String, Object> emptyWorkHour = App.getHttpServer().genEmptyWorkHour(
                        type, (String)worklog.get("CId")
                        , (String)worklog.get("FId"), (String)worklog.get("SId"));
                openWorkHourDetail(emptyWorkHour, worklogId, workers);
            }
            currentWorkHour = workhours.size();

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @OnClick(R.id.activity_worklog_name_layout)
    public void changeWorklogName() {
        if(editable) {
            EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_work_title),
                    (String)worklog.get("标题"),
                    new OnChangeListener() {
                        @Override
                        public void onChange(String oldValue, String newValue) {
                            String worklogId = (String)worklog.get("ID");
                            if(("").equals(worklogId)) {
                                // 新增
                                if ((DataType.TYPE_MAINTAIN).equals(type)) {
                                    String values = (String)worklog.get("维修任务ID") + ","
                                            + newValue;
                                    new WorklogEditTask("", type,
                                            "维修任务ID,标题",
                                            values, workhours).execute();
                                } else if ((DataType.TYPE_CHECK).equals(type)){
                                    String values = worklog.get("CId") + ","
                                            + newValue + ","
                                            + (String)worklog.get("FId") + ","
                                            + (String)worklog.get("SId") + ""
                                            ;
                                    new WorklogEditTask("", type,
                                            "CId,标题,FId,SId",
                                            values,workhours).execute();
                                }
                            } else {
                                new WorklogEditTask((String) worklog.get("ID"), type, "标题", newValue).execute();
                            }
                            worklog.put("标题", newValue);
                            displayData();
                        }
                    });

            dialog.show(getFragmentManager(), getString(R.string.field_work_title));
        }
    }

    @OnClick(R.id.activity_worklog_memo_layout)
    public void changeWorklogMemo() {
        if(editable) {
            EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_work_memo),
                    (String)worklog.get("详细描述"),
                    new OnChangeListener() {
                        @Override
                        public void onChange(String oldValue, String newValue) {
                            String worklogId = (String) worklog.get("ID");
                            if(("").equals(worklogId)) {
                                // 新增
                                if ((DataType.TYPE_MAINTAIN).equals(type)) {
                                    String values = (String) worklog.get("维修任务ID") + ","
                                            + newValue;
                                    new WorklogEditTask("", type,
                                            "维修任务ID,维修工作描述",
                                            values, workhours).execute();
                                } else if ((DataType.TYPE_CHECK).equals(type)) {
                                    String values = worklog.get("CId") + ","
                                            + newValue + ","
                                            + (String) worklog.get("FId") + ","
                                            + (String) worklog.get("SId") + "";
                                    new WorklogEditTask("", type,
                                            "CId,计划检查工作描述,FId,SId",
                                            values, workhours).execute();
                                }
                            } else {
                                if ((DataType.TYPE_MAINTAIN).equals(type)) {
                                    new WorklogEditTask((String) worklog.get("ID"), type, "维修工作描述", newValue).execute();
                                } else {
                                    new WorklogEditTask((String) worklog.get("ID"), type, "计划检查工作描述", newValue).execute();
                                }
                            }
                            worklog.put("详细描述", newValue);
                            displayData();
                        }
                    });
            dialog.setMultiLine(true);

            dialog.show(getFragmentManager(), getString(R.string.field_work_memo));
        }
    }

    @OnClick(R.id.activity_worklog_save_temp)
    public void doTemporarySave() {
        String worklogId = (String)worklog.get("ID");
        if(null == worklogId || ("").equals(worklogId)) {
            // 新增
            if ((DataType.TYPE_MAINTAIN).equals(type)) {
                String newValue = (String)worklog.get("维修任务ID") + ","
                        + (String)worklog.get("标题") + ","
                        + (String)worklog.get("详细描述") + ","
                        + App.readUser().get编号() + ","
                        + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + ","
                        + "0";
                new WorklogEditTask("", type,
                        "维修任务ID,标题,维修工作描述,录入人员信息ID,录入时间,是否提交",
                        newValue, workhours).execute();
            } else if ((DataType.TYPE_CHECK).equals(type)){
                String newValue = worklog.get("CId") + ","
                        + (String)worklog.get("标题") + ","
                        + (String)worklog.get("详细描述") + ","
                        + App.readUser().get编号() + ","
                        + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + ","
                        + "0" + ","
                        + (String)worklog.get("FId") + ","
                        + (String)worklog.get("SId") + ""
                        ;
                new WorklogEditTask("", type,
                        "CId,标题,计划检查工作描述,录入人员信息ID,录入时间,是否提交,FId,SId",
                        newValue,workhours).execute();
            }
        } else {
            // 编辑
            new WorklogEditTask((String)worklog.get("ID"), type, "是否提交", "0").execute();
        }
        worklog.put("是否提交", "0");
    }

    @OnClick(R.id.activity_worklog_save)
    public void doSave() {
        String worklogId = (String)worklog.get("ID");
        if(null == worklogId || ("").equals(worklogId)) {
            // 新增
            if ((DataType.TYPE_MAINTAIN).equals(type)) {
                String newValue = (String)worklog.get("维修任务ID") + ","
                        + (String)worklog.get("标题") + ","
                        + (String)worklog.get("详细描述") + ","
                        + App.readUser().get编号() + ","
                        + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + ","
                        + "1";
                new WorklogEditTask("", type,
                        "维修任务ID,标题,维修工作描述,录入人员信息ID,录入时间,是否提交",
                        newValue, workhours).execute();
            } else if ((DataType.TYPE_CHECK).equals(type)){
                String newValue = worklog.get("CId") + ","
                        + (String)worklog.get("标题") + ","
                        + (String)worklog.get("详细描述") + ","
                        + App.readUser().get编号() + ","
                        + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + ","
                        + "1" + ","
                        + (String)worklog.get("FId") + ","
                        + (String)worklog.get("SId") + ""
                        ;
                new WorklogEditTask("", type,
                        "CId,标题,计划检查工作描述,录入人员信息ID,录入时间,是否提交,FId,SId",
                        newValue,workhours).execute();
            }
        } else {
            // 编辑
            new WorklogEditTask((String)worklog.get("ID"), type, "是否提交", "1").execute();
        }
        worklog.put("是否提交","1");
        editable = false;
        displayData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worklog);
        ButterKnife.inject(this);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        worklog = (Map<String, Object>) getIntent().getSerializableExtra(EXTRA_WORKLOG);
        type = getIntent().getStringExtra(MainActivity.EXTRA_TYPE);
        editable = ((Boolean) getIntent().getBooleanExtra(EXTRA_EDITABLE, false)).booleanValue();
        workers = (List<Map<String, Object>>) getIntent().getSerializableExtra(MainActivity.EXTRA_WORKERS);
        workhours = (List<Map<String, Object>>) worklog.get("workhours");
        displayData();
    }

    private void displayData() {
        mNameTextView.setText((String) worklog.get("标题"));
        mMemoTextView.setText((String) worklog.get("详细描述"));

        if(("1").equals((String)worklog.get("是否提交"))) {
            editable = false;
            mTemporarySaveBtn.setVisibility(View.INVISIBLE);
            mSaveBtn.setVisibility(View.INVISIBLE);
        }
        if(!editable) {
            mNameRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mMemoRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mNameImageView.setVisibility(View.GONE);
            mMemoImageView.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
        }

        if (workhours == null) {
            workhours = new ArrayList<Map<String, Object>>();
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
        openWorkHourDetail(workhour, (String)worklog.get("ID"), workers);
        currentWorkHour = position;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == WorkHourActivity.RETURN_WORKHOUR_CODE) {
            boolean needRefresh = false;
            Map<String, Object> workhour = (Map<String, Object>) data.getSerializableExtra(RETURN_WORKHOUR);
            if (currentWorkHour == workhours.size()) {
                // 新增工时
                if( !("").equals((String)workhour.get("维修人员ID"))
                        || !("").equals((String)workhour.get("开始时间"))
                        || !("").equals((String)workhour.get("结束时间"))
                        ) {
                    workhours.add(workhour);
                    needRefresh = true;
                }
            } else {
                workhours.remove(currentWorkHour);
                workhours.add(currentWorkHour, workhour);
                needRefresh = true;
            }
            if(needRefresh) {
                displayData();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void openWorkHourDetail(Map<String, Object> workhour, String worklogId, List<Map<String, Object>> workers) {
        Intent workhourIntent = new Intent(this, WorkHourActivity.class);
        workhourIntent.putExtra(WorkHourActivity.EXTRA_WORKHOUR, (Serializable) workhour);
        workhourIntent.putExtra(WorkHourActivity.EXTRA_EDITABLE, new Boolean(editable));
        workhourIntent.putExtra(WorkHourActivity.EXTRA_WORKLOGID, worklogId);
        workhourIntent.putExtra(WorkHourActivity.EXTRA_WORKERS, (Serializable) workers);
        workhourIntent.putExtra(MainActivity.EXTRA_TYPE, type);
        startActivityForResult(workhourIntent, WorkHourActivity.RETURN_WORKHOUR_CODE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.putExtra(WorklogListFragment.RETURN_WORKLOG, (Serializable)worklog);
            //设置结果信息
            setResult(Activity.RESULT_OK,intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WorklogEditTask extends AsyncTask<Void, Void, String> {

        private final String worklogId;
        private final String type;
        String[] columns;
        String[] values;
        List<Map<String, Object>> workhours = null;
        private ProgressDialog progressDialog;

        public WorklogEditTask(String worklogId, String type, String column, String value, List<Map<String, Object>> workhours) {
            this.worklogId = worklogId;
            this.type = type;
            this.columns = new String[]{column};
            this.values = new String[]{value};
            this.workhours = workhours;
        }

        public WorklogEditTask(String worklogId, String type, String column, String value) {
            this.worklogId = worklogId;
            this.type = type;
            this.columns = new String[]{column};
            this.values = new String[]{value};
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                return App.getHttpServer().editWorklog(worklogId, type, columns, values, workhours);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return "";
        }

        @Override
        protected void onPostExecute(final String newId) {
            if(!("").equals(newId)) {
                worklog.put("ID",newId);
            }
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
