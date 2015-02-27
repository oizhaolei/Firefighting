/**
 *
 */
package com.ruptech.firefighting.check;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ruptech.firefighting.App;
import com.ruptech.firefighting.R;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ItemListArrayAdapter extends ArrayAdapter<Map<String, String>> {
    private static final int resource = R.layout.item_check_item; // xml布局文件
    private final LayoutInflater mInflater;

    public ItemListArrayAdapter(Context context) {
        super(context, resource);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view;
        final ViewHolder holder;
        if (convertView == null) {
            view = mInflater.inflate(resource, parent, false);

            holder = new ViewHolder(view);

            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();

        }

        Map<String, String> item = getItem(position);
        holder.noTextView.setText(item.get("企业编码"));
        holder.addressTextView.setText(item.get("安装地址"));
        holder.statusTextView.setText(item.get("检查状态"));
        holder.contentTextView.setText(item.get("故障内容"));

        String status = item.get("检查状态");
        // 0: 正常，1：故障，2：待检
        if (("2").equals(status)) {
            view.setBackgroundColor(App.mContext.getResources().getColor(
                    R.color.item_status_color_doing));
        } else if (("1").equals(status)) {
            view.setBackgroundColor(App.mContext.getResources().getColor(
                    R.color.item_status_color_error));
        } else if (("0").equals(status)) {
            view.setBackgroundColor(App.mContext.getResources().getColor(
                    R.color.item_status_color_well));
        }

        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.item_check_item_no)
        TextView noTextView;
        @InjectView(R.id.item_check_item_address)
        TextView addressTextView;
        @InjectView(R.id.item_check_item_status)
        TextView statusTextView;
        @InjectView(R.id.item_check_item_content)
        TextView contentTextView;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}