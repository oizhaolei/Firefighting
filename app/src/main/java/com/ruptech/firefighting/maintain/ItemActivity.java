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
    @InjectView(R.id.activity_maintain_item_center)
    TextView mCenterTextView;
    @InjectView(R.id.activity_maintain_item_company)
    TextView mCompanyTextView;
    @InjectView(R.id.activity_maintain_item_company_address)
    TextView mCompanyAddressTextView;
    @InjectView(R.id.activity_maintain_item_float)
    TextView mFloatTextView;
    @InjectView(R.id.activity_maintain_item_address)
    TextView mAddressTextView;
    @InjectView(R.id.activity_maintain_item_no)
    TextView mNoTextView;
    @InjectView(R.id.activity_maintain_item_category)
    TextView mCategoryTextView;
    @InjectView(R.id.activity_maintain_item_type)
    TextView mTypeTextView;
    @InjectView(R.id.activity_maintain_item_report_date)
    TextView mReportDateTextView;
    @InjectView(R.id.activity_maintain_item_content)
    TextView mContentTextView;
    @InjectView(R.id.activity_maintain_item_send_date)
    TextView mSendDateTextView;
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
    @InjectView(R.id.activity_maintain_item_status)
    TextView mStatusTextView;
    @InjectView(R.id.activity_maintain_item_report_type)
    TextView mReportTypeTextView;
    @InjectView(R.id.activity_maintain_item_contact)
    TextView mContactTextView;
    @InjectView(R.id.activity_maintain_item_contact_tel)
    TextView mContactTelTextView;
    @InjectView(R.id.activity_maintain_item_source)
    TextView mSourceTextView;
    @InjectView(R.id.activity_maintain_item_recieve_no)
    TextView mRecieveNoTextView;
    @InjectView(R.id.activity_maintain_item_check_no)
    TextView mCheckNoTextView;

    @InjectView(R.id.activity_maintain_item_center_layout)
    RelativeLayout mCenterRelativeLayout;
    @InjectView(R.id.activity_maintain_item_company_layout)
    RelativeLayout mCompanyRelativeLayout;
    @InjectView(R.id.activity_maintain_item_company_address_layout)
    RelativeLayout mCompanyAddressRelativeLayout;
    @InjectView(R.id.activity_maintain_item_float_layout)
    RelativeLayout mFloatRelativeLayout;
    @InjectView(R.id.activity_maintain_item_address_layout)
    RelativeLayout mAddressRelativeLayout;
    @InjectView(R.id.activity_maintain_item_no_layout)
    RelativeLayout mNoRelativeLayout;
    @InjectView(R.id.activity_maintain_item_category_layout)
    RelativeLayout mCategoryLayout;
    @InjectView(R.id.activity_maintain_item_type_layout)
    RelativeLayout mTypeLayout;
    @InjectView(R.id.activity_maintain_item_report_date_layout)
    RelativeLayout mReportDateLayout;
    @InjectView(R.id.activity_maintain_item_content_layout)
    RelativeLayout mContentRelativeLayout;
    @InjectView(R.id.activity_maintain_item_send_date_layout)
    RelativeLayout mSendRelativeLayout;
    @InjectView(R.id.activity_maintain_item_end_date_layout)
    RelativeLayout mEndRelativeLayout;
    @InjectView(R.id.activity_maintain_item_system_layout)
    RelativeLayout mSystemRelativeLayout;
    @InjectView(R.id.activity_maintain_item_device_layout)
    RelativeLayout mDeviceRelativeLayout;
    @InjectView(R.id.activity_maintain_item_error_layout)
    RelativeLayout mErrorRelativeLayout;
    @InjectView(R.id.activity_maintain_item_resolve_layout)
    RelativeLayout mResolveRelativeLayout;
    @InjectView(R.id.activity_maintain_item_status_layout)
    RelativeLayout mStatusRelativeLayout;
    @InjectView(R.id.activity_maintain_item_report_type_layout)
    RelativeLayout mReportTypeRelativeLayout;
    @InjectView(R.id.activity_maintain_item_contact_layout)
    RelativeLayout mContactRelativeLayout;
    @InjectView(R.id.activity_maintain_item_contact_tel_layout)
    RelativeLayout mContactTelRelativeLayout;
    @InjectView(R.id.activity_maintain_item_source_layout)
    RelativeLayout mSourceRelativeLayout;
    @InjectView(R.id.activity_maintain_item_recieve_no_layout)
    RelativeLayout mRecieveNoRelativeLayout;
    @InjectView(R.id.activity_maintain_item_check_no_layout)
    RelativeLayout mCheckNoRelativeLayout;

    @InjectView(R.id.activity_maintain_item_no_next)
    ImageView mNoImageView;
    @InjectView(R.id.activity_maintain_item_content_next)
    ImageView mContentImageView;
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
    @InjectView(R.id.activity_maintain_item_status_next)
    ImageView mStatusImageView;

    private String type;
    private String taskId;
    boolean editable;
    private Map<String, Object> item;
    private Map<Integer, String> devices;
    private Map<Integer, String> errors;


    @OnClick(R.id.activity_maintain_item_no_layout)
    public void changeItemNo() {
        if(editable) {
            EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_item_no),
                    (String)item.get("企业编码"),
                    new OnChangeListener() {
                        @Override
                        public void onChange(String oldValue, String newValue) {
                            String itemId = (String)item.get("ID");
                            // TODO
                            // 企业编码校验
                            item.put("企业编码", newValue);
                            displayData();
                        }
                    });

            dialog.show(getFragmentManager(), getString(R.string.field_item_no));
        }
    }

    @OnClick(R.id.activity_maintain_item_content_layout)
    public void changeItemContent() {
        if(editable) {
            EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_item_content),
                    (String)item.get("故障内容"),
                    new OnChangeListener() {
                        @Override
                        public void onChange(String oldValue, String newValue) {
                            String itemId = (String)item.get("ID");
                            new ItemEditTask(itemId, type, "故障内容", newValue, taskId).execute();
                            item.put("故障内容", newValue);
                            displayData();
                        }
                    });

            dialog.show(getFragmentManager(), getString(R.string.field_item_content));
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

    @OnClick(R.id.activity_maintain_item_status_layout)
    public void changeItemStatus() {
        if(editable) {
            Map choices = DataType.getMaintainItemStatusMap();

            int status = -1;
            if(!("").equals((String)item.get("维修状态"))) {
                status = Integer.valueOf((String)item.get("维修状态"));
            }
            ChoiceDialog dialog = ChoiceDialog.newInstance(getString(R.string.field_item_maintain_status),
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

            dialog.show(getFragmentManager(), getString(R.string.field_item_maintain_status));
        }
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
        editable = getIntent().getBooleanExtra(MainActivity.EXTRA_EDITABLE, false);

        displayData();
    }

    private void displayData() {
        if(!editable) {
            mNoRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mContentRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mEndRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mSystemRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mDeviceRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mErrorRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mResolveRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mStatusRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);

            mNoImageView.setVisibility(View.INVISIBLE);
            mContentImageView.setVisibility(View.INVISIBLE);
            mEndImageView.setVisibility(View.INVISIBLE);
            mSystemImageView.setVisibility(View.INVISIBLE);
            mDeviceImageView.setVisibility(View.INVISIBLE);
            mErrorImageView.setVisibility(View.INVISIBLE);
            mResolveImageView.setVisibility(View.INVISIBLE);
            mStatusImageView.setVisibility(View.INVISIBLE);
        } else {
            if(null == (String)item.get("ID") || ("").equals((String)item.get("ID"))) {
                // 新增
                mCenterRelativeLayout.setVisibility(View.GONE);
                mCompanyRelativeLayout.setVisibility(View.GONE);
                mCompanyAddressRelativeLayout.setVisibility(View.GONE);
                mFloatRelativeLayout.setVisibility(View.GONE);
                mAddressRelativeLayout.setVisibility(View.GONE);
                mCategoryLayout.setVisibility(View.GONE);
                mTypeLayout.setVisibility(View.GONE);
                mReportDateLayout.setVisibility(View.GONE);
                mSendRelativeLayout.setVisibility(View.GONE);
                mReportTypeRelativeLayout.setVisibility(View.GONE);
                mContactRelativeLayout.setVisibility(View.GONE);
                mContactTelRelativeLayout.setVisibility(View.GONE);
                mSourceRelativeLayout.setVisibility(View.GONE);
                mRecieveNoRelativeLayout.setVisibility(View.GONE);
                mCheckNoRelativeLayout.setVisibility(View.GONE);
            } else {
                // 编辑
                mNoRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
                mNoImageView.setVisibility(View.INVISIBLE);
            }
        }

        mCenterTextView.setText((String) item.get("中心名称"));
        mCompanyTextView.setText((String) item.get("单位名称"));
        mCompanyAddressTextView.setText((String) item.get("单位地址"));
        mFloatTextView.setText((String) item.get("楼层"));
        mAddressTextView.setText((String) item.get("安装地址"));
        mNoTextView.setText((String) item.get("企业编码"));
        mCategoryTextView.setText((String) item.get("部件类型名称"));
        mTypeTextView.setText((String) item.get("部件型号"));
        mReportDateTextView.setText((String) item.get("报修时间"));
        mContentTextView.setText((String) item.get("故障内容"));
        mSendDateTextView.setText((String) item.get("派单时间"));
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
        String status = (String)item.get("维修状态");
        if(null != status && !("").equals(status) ) {
            String statusName = DataType.getMaintainItemStatus(Integer.valueOf(status));
            mStatusTextView.setText(statusName);
            item.put("维修状态名称", statusName);
        } else {
            mStatusTextView.setText("");
        }
        mReportTypeTextView.setText((String) item.get("报修类型"));
        mContactTextView.setText((String) item.get("维修联系人"));
        mContactTelTextView.setText((String) item.get("维修联系电话"));
        mSourceTextView.setText((String) item.get("部件报修来源名称"));
        mRecieveNoTextView.setText((String) item.get("受理编号"));
        mCheckNoTextView.setText((String) item.get("检查编号"));
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
                return null;
            }
        }

        @Override
        protected void onPostExecute(final String newId) {
            afterItemEdit(newId);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ItemActivity.this, ItemActivity.this.getString(R.string.progress_title), ItemActivity.this.getString(R.string.progress_message), true, false);
        }
    }

    private void afterItemEdit(String newId) {
        if(null == newId) {
            Toast.makeText(ItemActivity.this, getString(R.string.connection_error), Toast.LENGTH_LONG).show();
        } else if(!("").equals(newId)) {
            item.put("PartId",newId);
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
                return null;
            }
        }

        @Override
        protected void onPostExecute(final Map<Integer, String> result) {
            openDeviceOptionChoiceDialog(result);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ItemActivity.this, ItemActivity.this.getString(R.string.progress_title), ItemActivity.this.getString(R.string.progress_message), true, false);
        }
    }

    private void openDeviceOptionChoiceDialog(Map<Integer, String> devicesMap) {
        devices = devicesMap;
        if(null == devicesMap) {
            Toast.makeText(ItemActivity.this, getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            return;
        }
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
                return null;
            }
        }

        @Override
        protected void onPostExecute(final Map<Integer, String> result) {
            errors = result;
            openErrorOptionChoiceDialog(result);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ItemActivity.this, ItemActivity.this.getString(R.string.progress_title), ItemActivity.this.getString(R.string.progress_message), true, false);
        }
    }

    private void openErrorOptionChoiceDialog(Map<Integer, String> errorsMap) {
        errors = errorsMap;
        if(null == errorsMap) {
            Toast.makeText(ItemActivity.this, getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            return;
        }
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
