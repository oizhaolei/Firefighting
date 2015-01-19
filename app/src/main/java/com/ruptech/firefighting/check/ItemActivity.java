package com.ruptech.firefighting.check;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.ruptech.firefighting.App;
import com.ruptech.firefighting.DataType;
import com.ruptech.firefighting.R;
import com.ruptech.firefighting.dialog.ChoiceDialog;
import com.ruptech.firefighting.dialog.EditTextDialog;
import com.ruptech.firefighting.dialog.OnChangeListener;
import com.ruptech.firefighting.main.MainActivity;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ItemActivity extends ActionBarActivity {
    public static final String EXTRA_ITEM = "EXTRA_ITEM";
    private static final String TAG = ItemActivity.class.getName();
    @InjectView(R.id.activity_check_item_no)
    TextView mNoTextView;
    @InjectView(R.id.activity_check_item_company)
    TextView mCompanyTextView;
    @InjectView(R.id.activity_check_item_name)
    TextView mNameTextView;
    @InjectView(R.id.activity_check_item_status)
    TextView mStatusTextView;
    @InjectView(R.id.activity_check_item_system)
    TextView mSystemTextView;
    @InjectView(R.id.activity_check_item_device)
    TextView mDeviceTextView;
    @InjectView(R.id.activity_check_item_error)
    TextView mErrorTextView;
    String type;
    private Map<String, Object> item;

    @OnClick(R.id.activity_check_item_system_layout)
    public void changeItemSystem() {
        Map choices = DataType.getItemSystemMap();

        ChoiceDialog dialog = ChoiceDialog.newInstance(getString(R.string.field_item_system),
                choices, Integer.valueOf(item.get("系统类型ID").toString()),
                new OnChangeListener() {
                    @Override
                    public void onChange(String oldValue, String newValue) {
                        new ItemEditTask(item.get("ID").toString(), type, "系统类型ID", newValue).execute();
                        item.put("系统类型ID", newValue);
                        displayData();
                    }
                });

        dialog.show(getFragmentManager(), getString(R.string.field_item_system));
    }

    @OnClick(R.id.activity_check_item_device_layout)
    public void changeItemDevice() {
        Map choices = DataType.getItemDeviceMap();

        ChoiceDialog dialog = ChoiceDialog.newInstance(getString(R.string.field_item_device),
                choices, Integer.valueOf(item.get("设备单项").toString()),
                new OnChangeListener() {
                    @Override
                    public void onChange(String oldValue, String newValue) {
                        new ItemEditTask(item.get("ID").toString(), type, "设备单项", newValue).execute();
                        item.put("设备单项", newValue);
                        displayData();
                    }
                });

        dialog.show(getFragmentManager(), getString(R.string.field_item_device));
    }

    @OnClick(R.id.activity_check_item_error_layout)
    public void changeItemError() {
        Map choices = DataType.getItemErrorMap();

        ChoiceDialog dialog = ChoiceDialog.newInstance(getString(R.string.field_item_error),
                choices, Integer.valueOf(item.get("故障单项").toString()),
                new OnChangeListener() {
                    @Override
                    public void onChange(String oldValue, String newValue) {
                        new ItemEditTask(item.get("ID").toString(), type, "故障单项", newValue).execute();
                        item.put("故障单项", newValue);
                        displayData();
                    }
                });

        dialog.show(getFragmentManager(), getString(R.string.field_item_error));
    }

    @OnClick(R.id.activity_check_item_resolve_layout)
    public void changeItemResolve() {
        EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_item_resolve),
                item.get("故障内容").toString(),
                new OnChangeListener() {
                    @Override
                    public void onChange(String oldValue, String newValue) {
                        new ItemEditTask(item.get("ID").toString(), type, "故障内容", newValue).execute();
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        item = (Map<String, Object>) getIntent().getSerializableExtra(EXTRA_ITEM);
        type = getIntent().getStringExtra(MainActivity.EXTRA_TYPE);

        displayData();
    }

    private void displayData() {
        mNoTextView.setText((String) item.get("部件ID"));
        mCompanyTextView.setText((String) item.get("单位名称"));
        mNameTextView.setText((String) item.get("系统名称"));
        mStatusTextView.setText((String) item.get("维修状态"));
        mSystemTextView.setText(DataType.getItemSystem(Integer.valueOf(item.get("系统类型ID").toString())));
        mDeviceTextView.setText(DataType.getItemDevice(Integer.valueOf(item.get("设备单项").toString())));
        mErrorTextView.setText(DataType.getItemError(Integer.valueOf(item.get("故障单项").toString())));
    }

    private class ItemEditTask extends AsyncTask<Void, Void, Boolean> {

        private final String taskId;
        private final String type;
        String[] columns;
        String[] values;
        private ProgressDialog progressDialog;

        public ItemEditTask(String taskId, String type, String column, String value) {
            this.taskId = taskId;
            this.type = type;
            columns = new String[]{column};
            values = new String[]{value};
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return App.getHttpServer().editItem(taskId, type, columns, values);
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
            progressDialog = ProgressDialog.show(ItemActivity.this, ItemActivity.this.getString(R.string.progress_title), ItemActivity.this.getString(R.string.progress_message), true, false);
        }
    }

}
