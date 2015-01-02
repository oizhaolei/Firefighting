package com.ruptech.firefighting.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ruptech.firefighting.R;
import com.ruptech.firefighting.item.ItemActivity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ItemFragment extends ListFragment {

    private List<Map<String, Object>> items;

    public static ItemFragment newInstance(List<Map<String, Object>> items) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailActivity.ARG_ITEM, (java.io.Serializable) items);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        items = (List<Map<String, Object>>) getArguments().get(DetailActivity.ARG_ITEM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_items, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.item_item,
                new String[]{"部件ID", "单位名称", "系统名称", "维修状态", "部件报修来源", "报修时间", "结束时间"},
                new int[]{R.id.item_item_no, R.id.item_item_company, R.id.item_item_name, R.id.item_item_status,
                        R.id.item_item_source, R.id.item_item_report_date, R.id.item_item_end_date});
        setListAdapter(adapter);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> item = (Map<String, Object>) getListAdapter().getItem(position);
        // In single-pane mode, simply start the detail activity
        // for the selected item ID.
        Intent intent = new Intent(getActivity(), ItemActivity.class);
        intent.putExtra(ItemActivity.ARG_ITEM, (Serializable) item);
        startActivity(intent);
    }
}
