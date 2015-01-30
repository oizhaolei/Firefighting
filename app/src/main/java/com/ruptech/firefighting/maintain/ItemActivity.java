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
import com.ruptech.firefighting.dialog.EditTextDialog;
import com.ruptech.firefighting.dialog.OnChangeListener;
import com.ruptech.firefighting.main.MainActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ItemActivity extends ActionBarActivity {
    public static final String EXTRA_ITEM = "EXTRA_ITEM";
    public static final String EXTRA_TASKID = "EXTRA_TASKID";
    public static final int RETURN_ITEM_CODE = 200;
    private static final String TAG = ItemActivity.class.getName();
    @InjectView(R.id.activity_maintain_item_no)
    TextView mNoTextView;
    @InjectView(R.id.activity_maintain_item_company)
    TextView mCompanyTextView;
    @InjectView(R.id.activity_maintain_item_name)
    TextView mNameTextView;
    @InjectView(R.id.activity_maintain_item_status)
    TextView mStatusTextView;
    @InjectView(R.id.activity_maintain_item_source)
    TextView mSourceTextView;
    @InjectView(R.id.activity_maintain_item_report_date)
    TextView mReportDateTextView;
    @InjectView(R.id.activity_maintain_item_end_date)
    TextView mEndDateTextView;
    @InjectView(R.id.activity_maintain_item_system)
    TextView mSystemTextView;
    @InjectView(R.id.activity_maintain_item_device)
    TextView mDeviceTextView;
    @InjectView(R.id.activity_maintain_item_error)
    TextView mErrorTextView;
    @InjectView(R.id.activity_maintain_item_resolve)
    TextView mResolveTextView;

    @InjectView(R.id.activity_maintain_item_no_layout)
    RelativeLayout mNoRelativeLayout;
    @InjectView(R.id.activity_maintain_item_company_layout)
    RelativeLayout mCompanyRelativeLayout;
    @InjectView(R.id.activity_maintain_item_name_layout)
    RelativeLayout mNameRelativeLayout;
    @InjectView(R.id.activity_maintain_item_status_layout)
    RelativeLayout mStatusRelativeLayout;
    @InjectView(R.id.activity_maintain_item_source_layout)
    RelativeLayout mSourceRelativeLayout;
    @InjectView(R.id.activity_maintain_item_report_date_layout)
    RelativeLayout mReportDateRelativeLayout;
    @InjectView(R.id.activity_maintain_item_end_date_layout)
    RelativeLayout mEndRelativeLayout;
    @InjectView(R.id.activity_maintain_item_system_layout)
    RelativeLayout mSystemRelativeLayout;
    @InjectView(R.id.activity_maintain_item_device_layout)
    RelativeLayout mDeviceRelativeLayout;
    @InjectView(R.id.activity_maintain_item_error_layout)
    RelativeLayout mErrorRelativeLayout;
    @InjectView(R.id.activity_maintain_item_resolve_layout)
    RelativeLayout mRelativeLayout;
    @InjectView(R.id.activity_maintain_item_no_next)
    ImageView mNoImageView;
    @InjectView(R.id.activity_maintain_item_company_next)
    ImageView mCompanyImageView;
    @InjectView(R.id.activity_maintain_item_name_next)
    ImageView mNameImageView;
    @InjectView(R.id.activity_maintain_item_status_next)
    ImageView mStatusImageView;
    @InjectView(R.id.activity_maintain_item_source_next)
    ImageView mSourceImageView;
    @InjectView(R.id.activity_maintain_item_report_date_next)
    ImageView mReportDateImageView;
    @InjectView(R.id.activity_maintain_item_end_date_next)
    ImageView mEndImageView;
    @InjectView(R.id.activity_maintain_item_system_next)
    ImageView mSystemImageView;
    @InjectView(R.id.activity_maintain_item_device_next)
    ImageView mDeviceImageView;
    @InjectView(R.id.activity_maintain_item_error_next)
    ImageView mErrorImageView;
    @InjectView(R.id.activity_maintain_item_resolve_next)
    ImageView mResolveImageView;
    String type;
    String taskId;
    boolean editable = true;
    private Map<String, Object> item;
    Map<Integer, String> devices;
    Map<Integer, String> errors;


    @OnClick(R.id.activity_maintain_item_no_layout)
    public void changeItemNo() {
        if(editable) {
            EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_item_no),
                    (String)item.get("受理编号"),
                    new OnChangeListener() {
                        @Override
                        public void onChange(String oldValue, String newValue) {
                            String itemId = (String)item.get("ID");
                            new ItemEditTask(itemId, type, "受理编号", newValue, taskId).execute();
                            item.put("受理编号", newValue);
                            displayData();
                        }
                    });

            dialog.show(getFragmentManager(), getString(R.string.field_item_no));
        }
    }

    @OnClick(R.id.activity_maintain_item_company_layout)
    public void changeItemCompany() {
        if(editable) {
            EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_item_company),
                    (String)item.get("单位ID"),
                    new OnChangeListener() {
                        @Override
                        public void onChange(String oldValue, String newValue) {
                            String itemId = (String)item.get("ID");
                            new ItemEditTask(itemId, type, "单位ID", newValue, taskId).execute();
                            item.put("单位ID", newValue);
                            displayData();
                        }
                    });

            dialog.show(getFragmentManager(), getString(R.string.field_item_company));
        }
    }

    @OnClick(R.id.activity_maintain_item_name_layout)
    public void changeItemName() {
        if(editable) {
            EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_item_name),
                    (String)item.get("中心ID"),
                    new OnChangeListener() {
                        @Override
                        public void onChange(String oldValue, String newValue) {
                            String itemId = (String)item.get("ID");
                            new ItemEditTask(itemId, type, "中心ID", newValue, taskId).execute();
                            item.put("中心ID", newValue);
                            displayData();
                        }
                    });

            dialog.show(getFragmentManager(), getString(R.string.field_item_name));
        }
    }

    @OnClick(R.id.activity_maintain_item_status_layout)
    public void changeItemStatus() {
        if(editable) {
            Map choices = DataType.getMaintainItemStatusMap();

            int status = -1;
            if(!("").equals((String)item.get("维修状态"))) {
                status = Integer.valueOf((String)item.get("维修状态"));
            }
            ChoiceDialog dialog = ChoiceDialog.newInstance(getString(R.string.field_item_status),
                    choices, status,
                    new OnChangeListener() {
                        @Override
                        public void onChange(String oldValue, String newValue) {
                            new ItemEditTask((String)item.get("ID"), type, "维修状态", newValue, taskId).execute();
                            item.put("维修状态", newValue);
                            item.put("维修状态名称", DataType.getMaintainItemStatus(Integer.valueOf(newValue)));
                            displayData();
                        }
                    });

            dialog.show(getFragmentManager(), getString(R.string.field_item_status));
        }
    }

    @OnClick(R.id.activity_maintain_item_source_layout)
    public void changeItemSource() {
        if(editable) {
            EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_item_source),
                    (String)item.get("部件报修来源"),
                    new OnChangeListener() {
                        @Override
                        public void onChange(String oldValue, String newValue) {
                            String itemId = (String)item.get("ID");
                            new ItemEditTask(itemId, type, "部件报修来源", newValue, taskId).execute();
                            item.put("部件报修来源", newValue);
                            displayData();
                        }
                    });

            dialog.show(getFragmentManager(), getString(R.string.field_item_source));
        }
    }

    @OnClick(R.id.activity_maintain_item_report_date_layout)
    public void changeItemReportDate() {
        if(editable) {
            DateTimePickerDialog dialog = DateTimePickerDialog.newInstance(getString(R.string.field_item_report_date),
                    (String) item.get("报修时间"),
                    new OnChangeListener() {
                        @Override
                        public void onChange(String oldValue, String newValue) {
                            String itemId = (String) item.get("ID");
                            new ItemEditTask(itemId, type, "报修时间", newValue, taskId).execute();
                            item.put("报修时间", newValue);
                            displayData();
                        }
                    });
            dialog.show(getFragmentManager(), getString(R.string.field_item_report_date));
        }
    }

    @OnClick(R.id.activity_maintain_item_end_date_layout)
    public void changeItemEndDate() {
        if(editable) {
            DateTimePickerDialog dialog = DateTimePickerDialog.newInstance(getString(R.string.field_item_end_date),
                    (String) item.get("结束时间"),
                    new OnChangeListener() {
                        @Override
                        public void onChange(String oldValue, String newValue) {
                            String itemId = (String) item.get("ID");
                            new ItemEditTask(itemId, type, "结束时间", newValue, taskId).execute();
                            item.put("结束时间", newValue);
                            displayData();
                        }
                    });
            dialog.show(getFragmentManager(), getString(R.string.field_item_end_date));
        }
    }

    @OnClick(R.id.activity_maintain_item_system_layout)
    public void changeItemSystem() {
        if(editable) {
            Map choices = DataType.getItemSystemMap();

            int system = -1;
            if (!("").equals((String) item.get("系统类型ID"))) {
                system = Integer.valueOf((String) item.get("系统类型ID"));
            }
            ChoiceDialog dialog = ChoiceDialog.newInstance(getString(R.string.field_item_system),
                    choices, system,
                    new OnChangeListener() {
                        @Override
                        public void onChange(String oldValue, String newValue) {
                            new ItemEditTask((String) item.get("ID"), type, "系统类型ID", newValue, taskId).execute();
                            item.put("系统类型ID", newValue);
                            devices = new HashMap<Integer, String>();
                            item.put("设备单项", "-1");
                            item.put("设备单项名称", "");
                            errors = new HashMap<Integer, String>();
                            item.put("故障单项", "-1");
                            item.put("故障单项名称", "");
                            displayData();
                        }
                    });

            dialog.show(getFragmentManager(), getString(R.string.field_item_system));
        }
    }

    @OnClick(R.id.activity_maintain_item_device_layout)
    public void changeItemDevice() {
        if(editable) {
            int system = -1;
            if (("").equals((String) item.get("系统类型ID"))) {
                system = -1;
            } else {
                system = Integer.valueOf((String) item.get("系统类型ID"));
            }
            if (system == -1) {
                Toast.makeText(ItemActivity.this, "请选择系统类型", Toast.LENGTH_SHORT).show();
                return;
            }
            devices = new HashMap<Integer, String>();
            new DeviceOptionTask(new Integer(system).toString()).execute();
        }
    }

    @OnClick(R.id.activity_maintain_item_error_layout)
    public void changeItemError() {
        if(editable) {
            int device = -1;
            if (("").equals((String) item.get("设备单项"))) {
                device = -1;
            } else {
                device = Integer.valueOf((String) item.get("设备单项"));
            }
            if (device == -1) {
                Toast.makeText(ItemActivity.this, "请选择设备单项", Toast.LENGTH_SHORT).show();
                return;
            }
            errors = new HashMap<Integer, String>();
            new ErrorOptionTask((String) item.get("系统类型ID"), new Integer(device).toString()).execute();
        }
    }

    @OnClick(R.id.activity_maintain_item_resolve_layout)
    public void changeItemResolve() {
        EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_item_resolve),
                (String)item.get("维修措施"),
                new OnChangeListener() {
                    @Override
                    public void onChange(String oldValue, String newValue) {
                        new ItemEditTask((String)item.get("ID"), type, "维修措施", newValue, taskId).execute();
                        item.put("维修措施", newValue);
                        displayData();
                    }
                });
        dialog.setMultiLine(true);

        dialog.show(getFragmentManager(), getString(R.string.field_item_resolve));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_item);
        ButterKnife.inject(this);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        item = (Map<String, Object>) getIntent().getSerializableExtra(EXTRA_ITEM);
        type = getIntent().getStringExtra(MainActivity.EXTRA_TYPE);
        taskId = getIntent().getStringExtra(ItemActivity.EXTRA_TASKID);

        displayData();
    }

    private void displayData() {
        mNoTextView.setText((String) item.get("受理编号"));
        mCompanyTextView.setText((String) item.get("单位ID"));
        mNameTextView.setText((String) item.get("中心ID"));
        String status = (String)item.get("维修状态");
        if(null != status && !("").equals(status) ) {
            String statusName = DataType.getMaintainItemStatus(Integer.valueOf(status));
            mStatusTextView.setText(statusName);
            item.put("维修状态名称", statusName);
        } else {
            mStatusTextView.setText("");
        }
        mSourceTextView.setText((String) item.get("部件报修来源"));
        mReportDateTextView.setText((String) item.get("报修时间"));
        mEndDateTextView.setText((String) item.get("结束时间"));
        String system = (String)item.get("系统类型ID");
        if(null != system && !("").equals(system) ) {
            mSystemTextView.setText(DataType.getItemSystem(Integer.valueOf(system)));
        } else {
            mSystemTextView.setText("");
        }
//        String device = (String)item.get("设备单项");
//        if(null != device && !("").equals(device)) {
//            mDeviceTextView.setText(DataType.getItemDevice(Integer.valueOf(device)));
//        } else {
//            mDeviceTextView.setText("");
//        }
        mDeviceTextView.setText((String)item.get("设备单项名称"));
//        String error = (String)item.get("故障单项");
//        if(null != error && !("").equals(error)) {
//            mErrorTextView.setText(DataType.getItemError(Integer.valueOf(error)));
//        } else {
//            mErrorTextView.setText("");
//        }
        mErrorTextView.setText((String)item.get("故障单项名称"));
        mResolveTextView.setText((String) item.get("维修措施"));

        if(!editable) {
            mNoRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mCompanyRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mNameRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mStatusRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mSourceRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mReportDateRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mEndRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mSystemRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mDeviceRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mErrorRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mNoImageView.setVisibility(View.INVISIBLE);
            mCompanyImageView.setVisibility(View.INVISIBLE);
            mNameImageView.setVisibility(View.INVISIBLE);
            mStatusImageView.setVisibility(View.INVISIBLE);
            mSourceImageView.setVisibility(View.INVISIBLE);
            mReportDateImageView.setVisibility(View.INVISIBLE);
            mEndImageView.setVisibility(View.INVISIBLE);
            mSystemImageView.setVisibility(View.INVISIBLE);
            mDeviceImageView.setVisibility(View.INVISIBLE);
            mErrorImageView.setVisibility(View.INVISIBLE);
            mResolveImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.putExtra(ItemListFragment.RETURN_ITEM, (Serializable)item);
            //设置结果信息
            setResult(Activity.RESULT_OK,intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    private class ItemEditTask extends AsyncTask<Void, Void, String> {

        private final String id;
        private final String parentid;
        private final String type;
        String[] columns;
        String[] values;
        private ProgressDialog progressDialog;

        public ItemEditTask(String id, String type, String column, String value, String taskid) {
            this.id = id;
            this.parentid = taskid;
            this.type = type;
            columns = new String[]{column};
            values = new String[]{value};
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                return App.getHttpServer().editItem(id, type, columns, values, parentid);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return "";
        }

        @Override
        protected void onPostExecute(final String newId) {
            if(!("").equals(newId)) {
                item.put("ID",newId);
            }
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ItemActivity.this, ItemActivity.this.getString(R.string.progress_title), ItemActivity.this.getString(R.string.progress_message), true, false);
        }
    }

    private class DeviceOptionTask extends AsyncTask<Void, Void, Map<Integer, String>> {

        private final String system;
        private ProgressDialog progressDialog;

        public DeviceOptionTask(String system) {
            this.system = system;

        }

        @Override
        protected Map<Integer, String> doInBackground(Void... params) {
            try {
                return App.getHttpServer().getOption("device", system, "");
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                return new HashMap<Integer, String>();
            }
        }

        @Override
        protected void onPostExecute(final Map<Integer, String> result) {
            devices = result;
            openDeviceOptionChoiceDialog();
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ItemActivity.this, ItemActivity.this.getString(R.string.progress_title), ItemActivity.this.getString(R.string.progress_message), true, false);
        }
    }

    private void openDeviceOptionChoiceDialog() {
        int device = -1;
        if(("").equals((String)item.get("设备单项"))) {
            device = -1;
        } else {
            device = Integer.valueOf((String)item.get("设备单项"));
        }
        ChoiceDialog dialog = ChoiceDialog.newInstance(getString(R.string.field_item_device),
                devices, device,
                new OnChangeListener() {
                    @Override
                    public void onChange(String oldValue, String newValue) {
                        new ItemEditTask((String)item.get("ID"), type, "设备单项", newValue, taskId).execute();
                        item.put("设备单项", newValue);
                        item.put("设备单项名称", devices.get(Integer.valueOf(newValue)));
                        errors = new HashMap<Integer, String>();
                        item.put("故障单项", "-1");
                        item.put("故障单项名称", "");
                        displayData();
                    }
                });

        dialog.show(getFragmentManager(), getString(R.string.field_item_device));
    }

    private class ErrorOptionTask extends AsyncTask<Void, Void, Map<Integer, String>> {

        private final String system;
        private final String device;
        private ProgressDialog progressDialog;

        public ErrorOptionTask(String system, String device) {
            this.system = system;
            this.device = device;

        }

        @Override
        protected Map<Integer, String> doInBackground(Void... params) {
            try {
                return App.getHttpServer().getOption("error", system, device);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                return new HashMap<Integer, String>();
            }
        }

        @Override
        protected void onPostExecute(final Map<Integer, String> result) {
            errors = result;
            openErrorOptionChoiceDialog();
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ItemActivity.this, ItemActivity.this.getString(R.string.progress_title), ItemActivity.this.getString(R.string.progress_message), true, false);
        }
    }

    private void openErrorOptionChoiceDialog() {
        int error = -1;
        if(("").equals((String)item.get("故障单项"))) {
            error = -1;
        } else {
            error = Integer.valueOf((String)item.get("故障单项"));
        }
        ChoiceDialog dialog = ChoiceDialog.newInstance(getString(R.string.field_item_device),
                errors, error,
                new OnChangeListener() {
                    @Override
                    public void onChange(String oldValue, String newValue) {
                        new ItemEditTask((String)item.get("ID"), type, "故障单项", newValue, taskId).execute();
                        item.put("故障单项", newValue);
                        item.put("故障单项名称", errors.get(Integer.valueOf(newValue)));
                        displayData();
                    }
                });

        dialog.show(getFragmentManager(), getString(R.string.field_item_error));
    }

}
