package com.ruptech.firefighting.maintain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;
import com.ruptech.firefighting.DataType;
import com.ruptech.firefighting.R;
import com.ruptech.firefighting.dialog.ChoiceDialog;
import com.ruptech.firefighting.dialog.EditTextDialog;
import com.ruptech.firefighting.dialog.OnChangeListener;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class TaskFragment extends Fragment {

    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.fragment_maintain_task_scrollview)
    ObservableScrollView scrollView;

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

    public static TaskFragment newInstance(Map<String, Object> task) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(MaintainActivity.ARG_DATA, (java.io.Serializable) task);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.fab)
    public void doAdd() {
        Toast.makeText(getActivity(), "Apply Task", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fragment_maintain_task_manager_name_layout)
    public void changeManagerName() {
        EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_task_manager_name), task.get("单位联系人").toString(), new OnChangeListener() {
            @Override
            public void onChange(Object oldValue, Object newValue) {
                // TODO save to server
                task.put("单位联系人", newValue);
                displayData();
            }
        });

        dialog.show(getActivity().getFragmentManager(), getString(R.string.field_task_manager_name));
    }

    @OnClick(R.id.fragment_maintain_task_manager_tel_layout)
    public void changeManagerTel() {
        EditTextDialog dialog = EditTextDialog.newInstance(getString(R.string.field_task_manager_tel), task.get("单位联系人电话").toString(), new OnChangeListener() {
            @Override
            public void onChange(Object oldValue, Object newValue) {
                // TODO save to server
                task.put("单位联系人电话", newValue);
                displayData();
            }
        });
        dialog.setInputType(InputType.TYPE_CLASS_PHONE);

        dialog.show(getActivity().getFragmentManager(), getString(R.string.field_task_manager_tel));
    }

    @OnClick(R.id.fragment_maintain_task_status_layout)
    public void changeTaskStatus() {
        Map choices = DataType.getTaskStatusMap();
        ChoiceDialog dialog = ChoiceDialog.newInstance(getString(R.string.field_task_status), choices, Integer.valueOf(task.get("任务状态").toString()), new OnChangeListener() {
            @Override
            public void onChange(Object oldValue, Object newValue) {
                // TODO save to server
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

        task = (Map<String, Object>) getArguments().get(MaintainActivity.ARG_DATA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maintain_task, container, false);
        ButterKnife.inject(this, rootView);

        displayData();
        fab.attachToScrollView(scrollView);

        return rootView;
    }

    private void displayData() {
        nameTextView.setText(task.get("任务名称").toString());
        companyTextView.setText(task.get("单位名称").toString());
        managerTextView.setText(task.get("单位联系人").toString());
        managerTelTextView.setText(task.get("单位联系人电话").toString());
        senderTextView.setText(task.get("派单人姓名").toString());
        sendDateTextView.setText(task.get("派单时间").toString());
        statusTextView.setText(DataType.getTaskStatus(Integer.valueOf(task.get("任务状态").toString())));
    }

}
