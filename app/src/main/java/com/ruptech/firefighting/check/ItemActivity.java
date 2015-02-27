package com.ruptech.firefighting.check;

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
    public static final String EXTRA_PLANID = "EXTRA_PLANID";
    public static final int RETURN_ITEM_CODE = 400;
    private static final String TAG = ItemActivity.class.getName();


    @InjectView(R.id.activity_check_item_no)
    TextView mNoTextView;
    @InjectView(R.id.activity_check_item_company)
    TextView mCompanyTextView;
    @InjectView(R.id.activity_check_item_company_address)
    TextView mCompanyAddressTextView;
    @InjectView(R.id.activity_check_item_float)
    TextView mFloatTextView;
    @InjectView(R.id.activity_check_item_address)
    TextView mAddressTextView;
    @InjectView(R.id.activity_check_item_code)
    TextView mCodeTextView;
    @InjectView(R.id.activity_check_item_category)
    TextView mCategoryTextView;
    @InjectView(R.id.activity_check_item_status)
    TextView mStatusTextView;
    @InjectView(R.id.activity_check_item_system)
    TextView mSystemTextView;
    @InjectView(R.id.activity_check_item_device)
    TextView mDeviceTextView;
    @InjectView(R.id.activity_check_item_error)
    TextView mErrorTextView;
    @InjectView(R.id.activity_check_item_content)
    TextView mContentTextView;

    @InjectView(R.id.activity_check_item_no_layout)
    RelativeLayout mNoRelativeLayout;
    @InjectView(R.id.activity_check_item_company_layout)
    RelativeLayout mCompanyRelativeLayout;
    @InjectView(R.id.activity_check_item_company_address_layout)
    RelativeLayout mCompanyAddressRelativeLayout;
    @InjectView(R.id.activity_check_item_float_layout)
    RelativeLayout mFloatRelativeLayout;
    @InjectView(R.id.activity_check_item_address_layout)
    RelativeLayout mAddressRelativeLayout;
    @InjectView(R.id.activity_check_item_code_layout)
    RelativeLayout mCodeLayout;
    @InjectView(R.id.activity_check_item_category_layout)
    RelativeLayout mCategoryLayout;
    @InjectView(R.id.activity_check_item_status_layout)
    RelativeLayout mStatusRelativeLayout;
    @InjectView(R.id.activity_check_item_system_layout)
    RelativeLayout mSystemRelativeLayout;
    @InjectView(R.id.activity_check_item_device_layout)
    RelativeLayout mDeviceRelativeLayout;
    @InjectView(R.id.activity_check_item_error_layout)
    RelativeLayout mErrorRelativeLayout;
    @InjectView(R.id.activity_check_item_content_layout)
    RelativeLayout mContentRelativeLayout;

    @InjectView(R.id.activity_check_item_no_next)
    ImageView mNoImageView;
    @InjectView(R.id.activity_check_item_status_next)
    ImageView mStatusImageView;
    @InjectView(R.id.activity_check_item_system_next)
    ImageView mSystemImageView;
    @InjectView(R.id.activity_check_item_device_next)
    ImageView mDeviceImageView;
    @InjectView(R.id.activity_check_item_error_next)
    ImageView mErrorImageView;
    @InjectView(R.id.activity_check_item_content_next)
    ImageView mContentImageView;

    private String type;
    private boolean editable;
    private Map<String, Object> item;
    private String planId;
    private Map<Integer, String> devices;
    private Map<Integer, String> errors;

    @OnClick(R.id.activity_check_item_no_layout)
    public void changeItemNo() {
        if(editable) {
            EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_item_no),
                    (String)item.get("企业编码"),
                    new OnChangeListener() {
                        @Override
                        public void onChange(String oldValue, String newValue) {
                            String itemId = (String)item.get("PartId");
                            new ItemEditTask(itemId, type, "企业编码", newValue, planId).execute();

                            // TODO
                            item.put("企业编码", newValue);
                            displayData();
                        }
                    });

            dialog.show(getFragmentManager(), getString(R.string.field_item_no));
        }
    }

    @OnClick(R.id.activity_check_item_status_layout)
    public void changeItemStatus() {
        if(editable) {
            Map choices = DataType.getCheckItemStatusMap();

            int status = -1;
            if(!("").equals((String)item.get("检查状态"))) {
                status = Integer.valueOf((String)item.get("检查状态"));
            }
            ChoiceDialog dialog = ChoiceDialog.newInstance(getString(R.string.field_item_check_status),
                    choices, status,
                    new OnChangeListener() {
                        @Override
                        public void onChange(String oldValue, String newValue) {
                            new ItemEditTask((String) item.get("PartId"), type, "状态", newValue, planId).execute();
                            item.put("检查状态", newValue);
                            item.put("检查状态名称", DataType.getCheckItemStatus(Integer.valueOf(newValue)));
                            displayData();
                        }
                    });

            dialog.show(getFragmentManager(), getString(R.string.field_item_check_status));
        }
    }


    @OnClick(R.id.activity_check_item_system_layout)
    public void changeItemSystem() {
        if(editable) {
            Map choices = DataType.getItemSystemMap();

            int system = -1;
            if (!("").equals((String) item.get("实际系统类型ID"))) {
                system = Integer.valueOf((String) item.get("实际系统类型ID"));
            }
            ChoiceDialog dialog = ChoiceDialog.newInstance(getString(R.string.field_item_system),
                    choices, system,
                    new OnChangeListener() {
                        @Override
                        public void onChange(String oldValue, String newValue) {
                            new ItemEditTask((String) item.get("PartId"), type, "实际系统类型ID", newValue, planId).execute();
                            item.put("实际系统类型ID", newValue);
                            devices = new HashMap<Integer, String>();
                            item.put("实际设备单项", "-1");
                            item.put("设备单项名称", "");
                            errors = new HashMap<Integer, String>();
                            item.put("实际故障单项", "-1");
                            item.put("故障单项名称", "");
                            displayData();
                        }
                    });

            dialog.show(getFragmentManager(), getString(R.string.field_item_system));
        }
    }

    @OnClick(R.id.activity_check_item_device_layout)
    public void changeItemDevice() {
        if(editable) {
            int system = -1;
            if (("").equals((String) item.get("实际系统类型ID"))) {
                system = -1;
            } else {
                system = Integer.valueOf((String) item.get("实际系统类型ID"));
            }
            if (system == -1) {
                Toast.makeText(ItemActivity.this, "请选择系统类型", Toast.LENGTH_SHORT).show();
                return;
            }
            devices = new HashMap<Integer, String>();
            new DeviceOptionTask(new Integer(system).toString()).execute();
        }
    }

    @OnClick(R.id.activity_check_item_error_layout)
    public void changeItemError() {
        if(editable) {
            int device = -1;
            if (("").equals((String) item.get("实际设备单项"))) {
                device = -1;
            } else {
                device = Integer.valueOf((String) item.get("实际设备单项"));
            }
            if (device == -1) {
                Toast.makeText(ItemActivity.this, "请选择设备单项", Toast.LENGTH_SHORT).show();
                return;
            }
            errors = new HashMap<Integer, String>();
            new ErrorOptionTask((String) item.get("实际系统类型ID"), new Integer(device).toString()).execute();
        }
    }

    @OnClick(R.id.activity_check_item_content_layout)
    public void changeItemContent() {
        EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_item_resolve),
                (String)item.get("故障内容"),
                new OnChangeListener() {
                    @Override
                    public void onChange(String oldValue, String newValue) {
                        new ItemEditTask((String)item.get("PartId"), type, "备注", newValue, planId).execute();
                        item.put("故障内容", newValue);
                        displayData();
                    }
                });
        dialog.setMultiLine(true);

        dialog.show(getFragmentManager(), getString(R.string.field_item_resolve));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_item);
        ButterKnife.inject(this);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        item = (Map<String, Object>) getIntent().getSerializableExtra(EXTRA_ITEM);
        type = getIntent().getStringExtra(MainActivity.EXTRA_TYPE);
        editable = getIntent().getBooleanExtra(MainActivity.EXTRA_EDITABLE, false);
        planId = getIntent().getStringExtra(EXTRA_PLANID);

        displayData();
    }

    private void displayData() {
        if(!editable) {
            mNoRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mStatusRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mSystemRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mDeviceRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mErrorRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mContentRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mNoImageView.setVisibility(View.INVISIBLE);
            mStatusImageView.setVisibility(View.INVISIBLE);
            mSystemImageView.setVisibility(View.INVISIBLE);
            mDeviceImageView.setVisibility(View.INVISIBLE);
            mErrorImageView.setVisibility(View.INVISIBLE);
            mContentImageView.setVisibility(View.INVISIBLE);
        } else {
            if(null == (String)item.get("PartId") || ("").equals((String)item.get("PartId"))) {
                // 新增
                mCompanyRelativeLayout.setVisibility(View.GONE);
                mCompanyAddressRelativeLayout.setVisibility(View.GONE);
                mFloatRelativeLayout.setVisibility(View.GONE);
                mAddressRelativeLayout.setVisibility(View.GONE);
                mCodeLayout.setVisibility(View.GONE);
                mCategoryLayout.setVisibility(View.GONE);
            } else {
                // 编辑
                mNoRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
                mNoImageView.setVisibility(View.INVISIBLE);
            }
        }
        mNoTextView.setText((String) item.get("企业编码"));
        mCompanyTextView.setText((String) item.get("单位名称"));
        mCompanyAddressTextView.setText((String) item.get("单位地址"));
        mFloatTextView.setText((String) item.get("楼层"));
        mAddressTextView.setText((String) item.get("安装地址"));
        mCodeTextView.setText((String) item.get("部件编码"));
        mCategoryTextView.setText((String) item.get("部件类型名称"));
        int checkItemStatus = -1;
        String status = (String)item.get("检查状态");
        if(null != status && !("").equals(status) ) {
            checkItemStatus = Integer.valueOf((String)item.get("检查状态"));
            String statusName = DataType.getCheckItemStatus(Integer.valueOf(status));
            mStatusTextView.setText(statusName);
            item.put("检查状态名称", statusName);
        } else {
            mStatusTextView.setText("");
        }
        displayOptions(checkItemStatus);

        String system = (String)item.get("实际系统类型ID");
        if(null != system && !("").equals(system) ) {
            String systemName = DataType.getItemSystem(Integer.valueOf(system));
            mSystemTextView.setText(systemName);
            item.put("系统类型名称", systemName);
        } else {
            mSystemTextView.setText("");
        }
        mDeviceTextView.setText((String)item.get("设备单项名称"));
        mErrorTextView.setText((String)item.get("故障单项名称"));
        mContentTextView.setText((String) item.get("故障内容"));
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

    private void displayOptions(int checkItemStatus) {
        if (checkItemStatus == 1) { //1:故障
            mSystemRelativeLayout.setVisibility(View.VISIBLE);
            mDeviceRelativeLayout.setVisibility(View.VISIBLE);
            mErrorRelativeLayout.setVisibility(View.VISIBLE);
            mContentRelativeLayout.setVisibility(View.VISIBLE);
        } else {
            mSystemRelativeLayout.setVisibility(View.GONE);
            mDeviceRelativeLayout.setVisibility(View.GONE);
            mErrorRelativeLayout.setVisibility(View.GONE);
            mContentRelativeLayout.setVisibility(View.GONE);
        }
    }

    private class ItemEditTask extends AsyncTask<Void, Void, String> {

        private final String id;
        private final String planId;
        private final String type;
        String[] columns;
        String[] values;
        private ProgressDialog progressDialog;

        public ItemEditTask(String id, String type, String column, String value, String planId) {
            this.id = id;
            this.planId = planId;
            this.type = type;
            columns = new String[]{column};
            values = new String[]{value};
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                return App.getHttpServer().editItem(id, type, columns, values, planId);
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
        this.devices = devicesMap;
        if(null == devicesMap) {
            Toast.makeText(ItemActivity.this, getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            return;
        }
        int device = -1;
        if(("").equals((String)item.get("实际设备单项"))) {
            device = -1;
        } else {
            device = Integer.valueOf((String)item.get("实际设备单项"));
        }
        ChoiceDialog dialog = ChoiceDialog.newInstance(getString(R.string.field_item_device),
                devices, device,
                new OnChangeListener() {
                    @Override
                    public void onChange(String oldValue, String newValue) {
                        new ItemEditTask((String)item.get("PartId"), type, "实际设备单项", newValue, planId).execute();
                        item.put("实际设备单项", newValue);
                        item.put("设备单项名称", devices.get(Integer.valueOf(newValue)));
                        errors = new HashMap<Integer, String>();
                        item.put("实际故障单项", "-1");
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
        this.errors = errorsMap;
        if(null == errorsMap) {
            Toast.makeText(ItemActivity.this, getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            return;
        }
        int error = -1;
        if(("").equals((String)item.get("实际故障单项"))) {
            error = -1;
        } else {
            error = Integer.valueOf((String)item.get("实际故障单项"));
        }
        ChoiceDialog dialog = ChoiceDialog.newInstance(getString(R.string.field_item_device),
                errors, error,
                new OnChangeListener() {
                    @Override
                    public void onChange(String oldValue, String newValue) {
                        new ItemEditTask((String)item.get("PartId"), type, "实际故障单项", newValue, planId).execute();
                        item.put("实际故障单项", newValue);
                        item.put("故障单项名称", errors.get(Integer.valueOf(newValue)));
                        displayData();
                    }
                });

        dialog.show(getFragmentManager(), getString(R.string.field_item_error));
    }
}
