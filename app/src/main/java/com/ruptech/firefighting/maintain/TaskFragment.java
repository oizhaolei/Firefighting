package com.ruptech.firefighting.maintain;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;
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


public class TaskFragment extends Fragment {
    private static final String TAG = TaskFragment.class.getName();

    @InjectView(R.id.fragment_maintain_task_name)
    TextView nameTextView;
    @InjectView(R.id.fragment_maintain_task_company)
    TextView companyTextView;
    @InjectView(R.id.fragment_maintain_task_manager_name)
    TextView managerTextView;
    @InjectView(R.id.fragment_maintain_task_manager_tel)
    TextView managerTelTextView;
    @InjectView(R.id.fragment_maintain_task_sender)
    TextView senderTextView;
    @InjectView(R.id.fragment_maintain_task_send_date)
    TextView sendDateTextView;
    @InjectView(R.id.fragment_maintain_task_status)
    TextView statusTextView;
    private Map<String, Object> task;
    private String type;
    private boolean editable;
    @InjectView(R.id.fragment_maintain_task_manager_name_layout)
    RelativeLayout mManagerNameRelativeLayout;
    @InjectView(R.id.fragment_maintain_task_manager_tel_layout)
    RelativeLayout mManagerTelRelativeLayout;
    @InjectView(R.id.fragment_maintain_task_status_layout)
    RelativeLayout mStatusRelativeLayout;
    @InjectView(R.id.fragment_maintain_task_manager_name_next)
    ImageView mManagerNameImageView;
    @InjectView(R.id.fragment_maintain_task_manager_tel_next)
    ImageView mManagerTelImageView;
    @InjectView(R.id.fragment_maintain_task_status_next)
    ImageView mStatusImageView;

    public static TaskFragment newInstance(Map<String, Object> task, String type, boolean editable) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(MaintainActivity.EXTRA_TASK, (java.io.Serializable) task);
        args.putString(MainActivity.EXTRA_TYPE, type);
        args.putBoolean(MainActivity.EXTRA_EDITABLE, editable);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.fragment_maintain_task_manager_name_layout)
    public void changeManagerName() {
        EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_task_manager_name), (String)task.get("单位联系人"), new OnChangeListener() {
            @Override
            public void onChange(String oldValue, String newValue) {
                new TaskEditTask((String)task.get("ID"), type, "单位联系人", newValue).execute();
                task.put("单位联系人", newValue);
                displayData();
            }
        });

        dialog.show(getActivity().getFragmentManager(), getString(R.string.field_task_manager_name));
    }

    @OnClick(R.id.fragment_maintain_task_manager_tel_layout)
    public void changeManagerTel() {
        EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_task_manager_tel), (String)task.get("单位联系人电话"), new OnChangeListener() {
            @Override
            public void onChange(String oldValue, String newValue) {
                new TaskEditTask((String)task.get("ID"), type, "单位联系人电话", newValue).execute();
                task.put("单位联系人电话", newValue);
                displayData();
            }
        });
        dialog.setInputType(InputType.TYPE_CLASS_PHONE);

        dialog.show(getActivity().getFragmentManager(), getString(R.string.field_task_manager_tel));
    }

    @OnClick(R.id.fragment_maintain_task_status_layout)
    public void changeTaskStatus() {
        int status = -1;
        if(!("").equals((String)task.get("任务状态"))) {
            status = Integer.valueOf((String)task.get("任务状态"));
        }
        Map choices = DataType.getMaintainTaskStatusMap(status);
        ChoiceDialog dialog = ChoiceDialog.newInstance(getString(R.string.field_task_status), choices, Integer.valueOf(status),
                new OnChangeListener() {
            @Override
            public void onChange(String oldValue, String newValue) {
                new TaskEditTask((String)task.get("ID"), type, "任务状态", newValue).execute();
                task.put("任务状态", newValue);
                displayData();
            }
        });

        dialog.show(getActivity().getFragmentManager(), getString(R.string.field_task_status));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        task = (Map<String, Object>) getArguments().get(MaintainActivity.EXTRA_TASK);
        type = getArguments().getString(MainActivity.EXTRA_TYPE);
        editable = getArguments().getBoolean(MainActivity.EXTRA_EDITABLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maintain_task, container, false);
        ButterKnife.inject(this, rootView);

        displayData();

        return rootView;
    }

    private void displayData() {
        String status = (String)task.get("任务状态");
        if(("5").equals(status)) {
            editable = false;
        }
        if(!editable) {
            mManagerNameRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mManagerNameImageView.setVisibility(View.INVISIBLE);
            mManagerTelRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mManagerTelImageView.setVisibility(View.INVISIBLE);
            mStatusRelativeLayout.setBackgroundColor(DataType.readOnlyBackgroundColor);
            mStatusImageView.setVisibility(View.INVISIBLE);
        }

        nameTextView.setText((String)task.get("任务名称"));
        companyTextView.setText((String)task.get("单位名称"));
        managerTextView.setText((String)task.get("单位联系人"));
        managerTelTextView.setText((String)task.get("单位联系人电话"));
        senderTextView.setText((String)task.get("派单人姓名"));
        sendDateTextView.setText((String)task.get("派单时间"));

        if(null != status && !("").equals(status) ) {
            String statusName = DataType.getMaintainTaskStatus(Integer.valueOf(status));
            statusTextView.setText(statusName);
        } else {
            statusTextView.setText("");
        }

    }

    private class TaskEditTask extends AsyncTask<Void, Void, Boolean> {
        private final String taskId;
        private final String type;
        String[] columns;
        String[] values;
        private ProgressDialog progressDialog;

        public TaskEditTask(String taskId, String type, String column, String value) {
            this.taskId = taskId;
            this.type = type;
            columns = new String[]{column};
            values = new String[]{value};
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return App.getHttpServer().editTask(taskId, type, columns, values);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), getActivity().getString(R.string.progress_title), getActivity().getString(R.string.progress_message), true, false);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            afterTaskEdit(success);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

    }

    private void afterTaskEdit(Boolean edit) {
        if(null == edit) {
            Toast.makeText(getActivity(), getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            return;
        }
    }

}
