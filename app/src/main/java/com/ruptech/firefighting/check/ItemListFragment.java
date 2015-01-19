package com.ruptech.firefighting.check;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.ruptech.firefighting.App;
import com.ruptech.firefighting.DataType;
import com.ruptech.firefighting.R;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ItemListFragment extends ListFragment {
    public static final String EXTRA_ITEMS = "EXTRA_ITEMS";
    public static final String EXTRA_SUM = "EXTRA_SUM";
    private static final String TAG = ItemListFragment.class.getName();
    @InjectView(R.id.fragment_detail_item_top)
    TextView sumTextView;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    private List<Map<String, Object>> items;
    private List<Map<String, Object>> sum;

    public static ItemListFragment newInstance(List<Map<String, Object>> items, List<Map<String, Object>> sum) {
        ItemListFragment fragment = new ItemListFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_ITEMS, (Serializable) items);
        args.putSerializable(EXTRA_SUM, (Serializable) sum);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.fab)
    public void doAdd() {
        Toast.makeText(getActivity(), "Add Item", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        items = (List<Map<String, Object>>) getArguments().get(EXTRA_ITEMS);
        sum = (List<Map<String, Object>>) getArguments().get(EXTRA_SUM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_items, null);
        ButterKnife.inject(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.item_check_item,
                new String[]{"编码", "类型", "单位", "系统名称", "检查状态"},
                new int[]{R.id.item_check_item_no, R.id.item_check_item_type, R.id.item_check_item_company, R.id.item_check_item_system,
                        R.id.item_check_item_status});
        setListAdapter(adapter);

        fab.attachToListView(getListView());

        sumTextView.setText(sum.toString());

    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> item = (Map<String, Object>) getListAdapter().getItem(position);
        String itemId = item.get("ID").toString();

        new ItemDetailBackgroundTask(itemId, DataType.TYPE_CHECK).execute();

    }

    private void openItemDetail(Map<String, Object> item) {
        Intent intent = new Intent(getActivity(), ItemActivity.class);
        intent.putExtra(ItemActivity.ARG_ITEM, (Serializable) item);
        startActivity(intent);

    }

    private class ItemDetailBackgroundTask extends AsyncTask<Void, Void, Map<String, Object>> {

        private final String taskId;
        private final String type;

        public ItemDetailBackgroundTask(String taskId, String type) {
            this.taskId = taskId;
            this.type = type;
        }

        @Override
        protected Map<String, Object> doInBackground(Void... params) {
            try {
                return App.getHttpServer().getItemDetail(taskId, type);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Map<String, Object> result) {
            super.onPostExecute(result);

            // Tell the Fragment that the refresh has completed
            openItemDetail(result);
        }
    }

}