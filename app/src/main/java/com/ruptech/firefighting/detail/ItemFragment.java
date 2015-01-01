package com.ruptech.firefighting.detail;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.SimpleAdapter;

import com.ruptech.firefighting.R;

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

    // BEGIN_INCLUDE (setup_views)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.item_item,
                new String[]{"部件ID", "单位名称", "系统名称", "维修状态", "部件报修来源", "报修时间", "结束时间"},
                new int[]{R.id.item_item_no, R.id.item_item_company, R.id.item_item_name, R.id.item_item_status,
                        R.id.item_item_source, R.id.item_item_report_date, R.id.item_item_end_date});
        setListAdapter(adapter);
    }
}
