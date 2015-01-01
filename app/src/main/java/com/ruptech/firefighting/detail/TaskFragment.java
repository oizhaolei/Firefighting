
package com.ruptech.firefighting.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruptech.firefighting.R;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class TaskFragment extends Fragment {

    @InjectView(R.id.fragment_detail_task_name)
    TextView nameTextView;
    @InjectView(R.id.fragment_detail_task_company)
    TextView companyTextView;
    @InjectView(R.id.fragment_detail_task_manager)
    TextView managerTextView;
    @InjectView(R.id.fragment_detail_task_manager_tel)
    TextView managerTelTextView;
    @InjectView(R.id.fragment_detail_task_sender)
    TextView senderTextView;
    @InjectView(R.id.fragment_detail_task_send_date)
    TextView sendDateTextView;
    @InjectView(R.id.fragment_detail_task_status)
    TextView statusTextView;

    private Map<String, Object> task;

    public static TaskFragment newInstance(Map<String, Object> task) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailActivity.ARG_ITEM, (java.io.Serializable) task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_task, container, false);
        ButterKnife.inject(this, rootView);

        task = (Map<String, Object>) getArguments().get(DetailActivity.ARG_ITEM);
        setupComponent();

        return rootView;
    }

    private void setupComponent() {
        nameTextView.setText(task.get("标题").toString());
        companyTextView.setText(task.get("单位ID").toString());
        managerTextView.setText(task.get("单位联系人").toString());
        managerTelTextView.setText(task.get("单位联系人电话").toString());
        senderTextView.setText(task.get("派单人姓名").toString());
        sendDateTextView.setText(task.get("派单时间").toString());
        statusTextView.setText(task.get("任务状态").toString());

    }
}
