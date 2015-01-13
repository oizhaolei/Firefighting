package com.ruptech.firefighting.maintain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.ruptech.firefighting.R;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class WorklogListFragment extends ListFragment {
    public static final String ARG_WORK_LOGS = "ARG_WORK_LOGS";
    public static final String ARG_WORK_HOUR_SUM = "ARG_WORK_HOUR_SUM";

    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.fragment_detail_worklog_bottom)
    TextView workhourSumTextView;
    private List<Map<String, Object>> worklogs;
    private List<Map<String, Object>> workhoursum;

    public static WorklogListFragment newInstance(List<Map<String, Object>> worklogs, List<Map<String, Object>> workhoursum) {
        WorklogListFragment fragment = new WorklogListFragment();
        Bundle args = new Bundle();
        args.putSerializable(WorklogListFragment.ARG_WORK_LOGS, (java.io.Serializable) worklogs);
        args.putSerializable(WorklogListFragment.ARG_WORK_HOUR_SUM, (java.io.Serializable) workhoursum);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.fab)
    public void doAdd() {
        Toast.makeText(getActivity(), "Add Worklog", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        worklogs = (List<Map<String, Object>>) getArguments().get(ARG_WORK_LOGS);
        workhoursum = (List<Map<String, Object>>) getArguments().get(ARG_WORK_HOUR_SUM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_worklogs, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab.attachToListView(getListView());

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), worklogs, R.layout.item_worklog,
                new String[]{"标题", "详细描述"}, new int[]{R.id.item_worklog_name,
                R.id.item_worklog_memo});
        setListAdapter(adapter);

        workhourSumTextView.setText(workhoursum.toString());
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> worklog = (Map<String, Object>) getListAdapter().getItem(position);
        // In single-pane mode, simply start the detail activity
        // for the selected item ID.
        Intent worklogIntent = new Intent(getActivity(), WorklogActivity.class);
        worklogIntent.putExtra(WorklogActivity.ARG_ITEM, (Serializable) worklog);
        startActivity(worklogIntent);
    }
}
