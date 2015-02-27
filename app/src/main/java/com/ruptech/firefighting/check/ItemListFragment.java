package com.ruptech.firefighting.check;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.ruptech.firefighting.App;
import com.ruptech.firefighting.R;
import com.ruptech.firefighting.dialog.ComfirmDialog;
import com.ruptech.firefighting.main.MainActivity;
import com.ruptech.firefighting.utils.DialogTool;

import org.json.JSONException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ItemListFragment extends ListFragment {
    public static final String ARGS_ITEMS = "ARGS_ITEMS";
    public static final String ARGS_SUM = "ARGS_SUM";
    public static final String ARGS_PLANID = "ARGS_PLANID";
    public static final String RETURN_ITEM = "RETURN_ITEM";
    private static final String TAG = ItemListFragment.class.getName();
    @InjectView(R.id.fragment_detail_item_status1)
    TextView statusTextView1;
    @InjectView(R.id.fragment_detail_item_status2)
    TextView statusTextView2;
    @InjectView(R.id.fragment_detail_item_status3)
    TextView statusTextView3;
//    @InjectView(R.id.fab)
//    FloatingActionButton fab;
    @InjectView(R.id.fragment_detail_item_add_btn)
    Button addItemBtn;
    private List<Map<String, Object>> items;
    private List<Map<String, Object>> sum;
    private String type;
    private boolean editable;
    private String planId;
    private int currentItem = -1;

    public static ItemListFragment newInstance(List<Map<String, Object>> items, List<Map<String, Object>> sum, String type, String planId, boolean editable) {
        ItemListFragment fragment = new ItemListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_ITEMS, (Serializable) items);
        args.putSerializable(ARGS_SUM, (Serializable) sum);
        args.putString(MainActivity.EXTRA_TYPE, type);
        args.putBoolean(MainActivity.EXTRA_EDITABLE, editable);
        args.putString(ARGS_PLANID, planId);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.fragment_detail_item_add_btn)
    public void doAdd() {
        try {
            Map<String, Object> emptyItem = App.getHttpServer().genEmptyItem(type, planId);
            openDetail(emptyItem);
            currentItem = items.size();
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        items = (List<Map<String, Object>>) getArguments().get(ARGS_ITEMS);
        sum = (List<Map<String, Object>>) getArguments().get(ARGS_SUM);
        type = getArguments().getString(MainActivity.EXTRA_TYPE);
        editable = getArguments().getBoolean(MainActivity.EXTRA_EDITABLE);
        planId = getArguments().getString(ARGS_PLANID);
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

//        SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.item_check_item,
//                new String[]{"企业编码", "安装地址", "检查状态", "故障内容"},
//                new int[]{R.id.item_check_item_no, R.id.item_check_item_address, R.id.item_check_item_status,
//                        R.id.item_check_item_content});
        ArrayAdapter adapter = new ItemListArrayAdapter(getActivity());
        adapter.addAll(items);
        setListAdapter(adapter);

        final OnClickListener positiveBtnListener = new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new DeleteBackgroundTask((String)((Map<String, Object>)items.get(currentItem)).get("PartId"), type, currentItem).execute();
            }
        };

//        final OnClickListener negativeBtnListener = new OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // TODO Auto-generated method stub
//                Toast.makeText(getActivity(), "CANCEL", Toast.LENGTH_SHORT).show();
//            }
//        };
//
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
                currentItem = pos;
//                Dialog dialog = DialogTool.createConfirmDialog(getActivity(), getString(R.string.confirm_title), getString(R.string.confirm_delete_message),
//                        getString(R.string.ok), getString(R.string.cancel), positiveBtnListener, null, DialogTool.NO_ICON);
//                dialog.show();
                ComfirmDialog dialog = ComfirmDialog.newInstance(
                        getString(R.string.confirm_title),
                        getString(R.string.confirm_delete_message),
                        positiveBtnListener);
                dialog.show(getActivity().getFragmentManager(), getString(R.string.confirm_title));
                return true;
            }
        });

        displayData();

    }

    private void displayData() {

        if(!editable) {
//            fab.setVisibility(View.GONE);
            addItemBtn.setVisibility(View.GONE);
        }

//        fab.attachToListView(getListView());

        boolean isOk = false;
        for(Map<String, Object> status : sum) {
            if(("2").equals((String)status.get("code"))) {
                // 待检
                statusTextView1.setText((String)status.get("option")
                        + "(" + (String)status.get("count") + ")");
                if(("0").equals((String)status.get("count"))) {
                    isOk = true;
                }
            } else if(("1").equals((String)status.get("code"))) {
                // 故障
                statusTextView2.setText((String)status.get("option")
                        + "(" + (String)status.get("count") + ")");
            } else if(("0").equals((String)status.get("code"))) {
                // 正常
                statusTextView3.setText((String)status.get("option")
                        + "(" + (String)status.get("count") + ")");
            }
        }

        if(isOk) {
            // 全部修好
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.LEFT);
            lp.weight = 3.0f;
            statusTextView3.setLayoutParams(lp);
            statusTextView3.setText("全部完成");
            statusTextView1.setVisibility(View.GONE);
            statusTextView2.setVisibility(View.GONE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ItemActivity.RETURN_ITEM_CODE) {
            boolean needRefresh = false;
            Map<String, Object> item = (Map<String, Object>) data.getSerializableExtra(RETURN_ITEM);
            if (currentItem == items.size()) {
                // 新增部件
                if( !("").equals((String)item.get("企业编码"))
                        || !("").equals((String)item.get("检查状态"))
                        || !("").equals((String)item.get("实际系统类型ID"))
                        || !("").equals((String)item.get("实际设备单项"))
                        || !("").equals((String)item.get("实际故障单项"))
                        || !("").equals((String)item.get("故障内容"))
                        ) {
                    items.add(0, item);
                    needRefresh = true;
                }
            } else {
                items.remove(currentItem);
                items.add(currentItem, item);
                needRefresh = true;
            }
            if(needRefresh) {
                ArrayAdapter adapter = ((ItemListArrayAdapter) getListAdapter());
                adapter.clear();
                adapter.addAll(items);
                setListAdapter(adapter);
//                ((ItemListArrayAdapter) getListAdapter()).notifyDataSetChanged();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> item = (Map<String, Object>) getListAdapter().getItem(position);
        String itemId = (String)item.get("PartId");
        currentItem = position;
        //new DetailBackgroundTask(itemId, DataType.TYPE_CHECK).execute();
        new DetailBackgroundTask(itemId, type).execute();

    }

    private void openDetail(Map<String, Object> item) {
        if(null == item) {
            Toast.makeText(getActivity(), getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(getActivity(), ItemActivity.class);
        intent.putExtra(ItemActivity.EXTRA_ITEM, (Serializable) item);
        intent.putExtra(MainActivity.EXTRA_TYPE, type);
        intent.putExtra(MainActivity.EXTRA_EDITABLE, editable);
        intent.putExtra(ItemActivity.EXTRA_PLANID, planId);
        startActivityForResult(intent, ItemActivity.RETURN_ITEM_CODE);

    }

    private class DetailBackgroundTask extends AsyncTask<Void, Void, Map<String, Object>> {

        private final String id;
        private final String type;
        private ProgressDialog progressDialog;

        public DetailBackgroundTask(String id, String type) {
            this.id = id;
            this.type = type;
        }

        @Override
        protected Map<String, Object> doInBackground(Void... params) {
            try {
                return App.getHttpServer().getItemDetail(id, type);
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

    private void deleteItem(Boolean success, int pos) {
        if(null == success) {
            Toast.makeText(getActivity(), getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            return;
        }
        items.remove(pos);
        ArrayAdapter adapter = ((ItemListArrayAdapter) getListAdapter());
        adapter.clear();
        adapter.addAll(items);
        setListAdapter(adapter);
//                ((ItemListArrayAdapter) getListAdapter()).notifyDataSetChanged();
    }

    private class DeleteBackgroundTask extends AsyncTask<Void, Void, Boolean> {

        private final String id;
        private final String type;
        private final int pos;
        private ProgressDialog progressDialog;

        public DeleteBackgroundTask(String id, String type, int pos) {
            this.id = id;
            this.type = type;
            this.pos = pos;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return App.getHttpServer().deleteItem(id, type);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            // Tell the Fragment that the refresh has completed
            deleteItem(result, pos);
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
