package com.ruptech.firefighting.check;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.ruptech.firefighting.dialog.OnChangeListener;
import com.ruptech.firefighting.main.MainActivity;
import com.ruptech.firefighting.maintain.MaintainActivity;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class TaskFragment extends Fragment {

    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.fragment_check_task_scrollview)
    ObservableScrollView scrollView;

    @InjectView(R.id.fragment_check_task_name)
    TextView nameTextView;
    @InjectView(R.id.fragment_check_task_company)
    TextView managerTelTextView;
    @InjectView(R.id.fragment_check_task_sender)
    TextView senderTextView;
    @InjectView(R.id.fragment_check_task_send_date)
    TextView sendDateTextView;
    @InjectView(R.id.fragment_check_task_status)
    TextView statusTextView;
    private Map<String, Object> task;
    private String type;

    public static TaskFragment newInstance(Map<String, Object> task, String type) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(MaintainActivity.EXTRA_TASK, (java.io.Serializable) task);
        args.putString(MainActivity.EXTRA_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.fab)
    public void doAdd() {
        Toast.makeText(getActivity(), "Apply Task", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fragment_check_task_status_layout)
    public void changeTaskStatus() {
        Map choices = DataType.getCheckTaskStatusMap();
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

        task = (Map<String, Object>) getArguments().get(MaintainActivity.EXTRA_TASK);
        type = getArguments().getString(MainActivity.EXTRA_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check_task, container, false);
        ButterKnife.inject(this, rootView);

        displayData();
        fab.attachToScrollView(scrollView);

        return rootView;
    }

    private void displayData() {
        nameTextView.setText(task.get("任务名称").toString());
        managerTelTextView.setText(task.get("单位联系人电话").toString());
        senderTextView.setText(task.get("派单人姓名").toString());
        sendDateTextView.setText(task.get("派单时间").toString());
        statusTextView.setText(DataType.getCheckTaskStatus(Integer.valueOf(task.get("任务状态").toString())));
    }

}
