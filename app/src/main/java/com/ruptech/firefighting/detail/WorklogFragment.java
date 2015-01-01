package com.ruptech.firefighting.detail;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.ruptech.firefighting.R;

import java.util.List;
import java.util.Map;

public class WorklogFragment extends ListFragment {

    private List<Map<String, Object>> worklogs;

    public static WorklogFragment newInstance(List<Map<String, Object>> worklogs) {
        WorklogFragment fragment = new WorklogFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailActivity.ARG_ITEM, (java.io.Serializable) worklogs);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        worklogs = (List<Map<String, Object>>) getArguments().get(DetailActivity.ARG_ITEM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_worklogs, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), worklogs, R.layout.item_worklog, new String[]{"标题", "维修工作描述"}, new int[]{R.id.item_worklog_name,
                R.id.item_worklog_memo});
        setListAdapter(adapter);
    }


}
