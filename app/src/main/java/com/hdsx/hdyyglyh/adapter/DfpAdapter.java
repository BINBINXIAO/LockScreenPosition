package com.hdsx.hdyyglyh.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hdsx.hdyyglyh.R;
import com.hdsx.hdyyglyh.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class DfpAdapter extends BaseAdapter {

    private List<String> items = new ArrayList<>();
    private LayoutInflater inflate;

    public DfpAdapter(Context context) {
        inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflate.inflate(R.layout.item_dfp, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.num.setText("序号"+position + "");
        holder.text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("分配任务"+position);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView text;
        TextView num;

        ViewHolder(View view) {
            text = (TextView) view.findViewById(R.id.text);
            num = (TextView) view.findViewById(R.id.num);
        }
    }

}
