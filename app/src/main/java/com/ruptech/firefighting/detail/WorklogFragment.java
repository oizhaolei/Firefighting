package com.ruptech.firefighting.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.ruptech.firefighting.R;
import com.ruptech.firefighting.worklog.WorklogActivity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class WorklogFragment extends ListFragment {

    @InjectView(R.id.fab)
    FloatingActionButton fab;
    private List<Map<String, Object>> worklogs;

    public static WorklogFragment newInstance(List<Map<String, Object>> worklogs) {
        WorklogFragment fragment = new WorklogFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailActivity.ARG_ITEM, (java.io.Serializable) worklogs);
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
        worklogs = (List<Map<String, Object>>) getArguments().get(DetailActivity.ARG_ITEM);
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

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), worklogs, R.layout.item_worklog, new String[]{"标题", "维修工作描述"}, new int[]{R.id.item_worklog_name,
                R.id.item_worklog_memo});
        setListAdapter(adapter);

        fab.attachToListView(getListView());
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
