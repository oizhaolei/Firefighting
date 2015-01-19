package com.ruptech.firefighting.maintain;

import android.app.ProgressDialog;
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
import com.ruptech.firefighting.R;
import com.ruptech.firefighting.main.MainActivity;

import org.json.JSONException;

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
    private String type;

    public static ItemListFragment newInstance(List<Map<String, Object>> items, List<Map<String, Object>> sum, String type) {
        ItemListFragment fragment = new ItemListFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_ITEMS, (Serializable) items);
        args.putSerializable(EXTRA_SUM, (Serializable) sum);
        args.putString(MainActivity.EXTRA_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.fab)
    public void doAdd() {
        try {
            Map<String, Object> emptyItem = App.getHttpServer().genEmptyItem(type);
            openDetail(emptyItem);
            Toast.makeText(getActivity(), "Add Item", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        items = (List<Map<String, Object>>) getArguments().get(EXTRA_ITEMS);
        sum = (List<Map<String, Object>>) getArguments().get(EXTRA_SUM);
        type = getArguments().getString(MainActivity.EXTRA_TYPE);
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

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.item_maintain_item,
                new String[]{"序号", "单位", "系统名称", "维修状态", "部件报修来源", "报修时间", "结束时间"},
                new int[]{R.id.item_maintain_item_no, R.id.item_maintain_item_company, R.id.item_maintain_item_name, R.id.item_maintain_item_status,
                        R.id.item_maintain_item_source, R.id.item_maintain_item_report_date, R.id.item_maintain_item_end_date});
        setListAdapter(adapter);

        fab.attachToListView(getListView());

        sumTextView.setText(sum.toString());

    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> item = (Map<String, Object>) getListAdapter().getItem(position);
        String itemId = item.get("ID").toString();

        new DetailBackgroundTask(itemId, type).execute();

    }

    private void openDetail(Map<String, Object> item) {
        Intent intent = new Intent(getActivity(), ItemActivity.class);
        intent.putExtra(ItemActivity.EXTRA_ITEM, (Serializable) item);
        intent.putExtra(MainActivity.EXTRA_TYPE, type);
        startActivity(intent);

    }

    private class DetailBackgroundTask extends AsyncTask<Void, Void, Map<String, Object>> {

        private final String itemId;
        private final String type;
        private ProgressDialog progressDialog;

        public DetailBackgroundTask(String itemId, String type) {
            this.itemId = itemId;
            this.type = type;
        }

        @Override
        protected Map<String, Object> doInBackground(Void... params) {
            try {
                return App.getHttpServer().getItemDetail(itemId, type);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Map<String, Object> result) {
            super.onPostExecute(result);

            // Tell the Fragment that the refresh has completed
            openDetail(result);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), getActivity().getString(R.string.progress_title), getActivity().getString(R.string.progress_message), true, false);
        }


    }

}
