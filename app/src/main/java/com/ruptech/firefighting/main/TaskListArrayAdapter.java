/**
 *
 */
package com.ruptech.firefighting.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ruptech.firefighting.App;
import com.ruptech.firefighting.DataType;
import com.ruptech.firefighting.R;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TaskListArrayAdapter extends ArrayAdapter<Map<String, String>> {
    private static final int resource = R.layout.item_task; // xml布局文件
    private final LayoutInflater mInflater;

    public TaskListArrayAdapter(Context context) {
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
        holder.listitem_item_task_name.setText(item.get("任务名称"));
        holder.listitem_item_task_status.setText(item.get("任务状态"));
        holder.listitem_item_task_date.setText(item.get("派单时间"));
        holder.listitem_item_task_type.setText(item.get("任务类型"));

        if (DataType.TYPE_MAINTAIN.equals(item.get("任务类型"))) {
            view.setBackgroundColor(App.mContext.getResources().getColor(
                    R.color.maintain_back_color));
        } else {
            view.setBackgroundColor(App.mContext.getResources().getColor(
                    R.color.check_back_color));
        }

        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.item_task_name)
        TextView listitem_item_task_name;
        @InjectView(R.id.item_task_status)
        TextView listitem_item_task_status;
        @InjectView(R.id.item_task_date)
        TextView listitem_item_task_date;
        @InjectView(R.id.item_task_type)
        TextView listitem_item_task_type;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}