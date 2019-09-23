package com.hdsx.hdyyglyh.page.rcyh;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hdsx.hdyyglyh.BaseActivity;
import com.hdsx.hdyyglyh.R;
import com.hdsx.hdyyglyh.adapter.ItemAdapter;
import com.hdsx.hdyyglyh.page.rcyh.LyjcActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class lyjcTypeActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.listview)
    ListView listview;

    private ItemAdapter itemAdapter;
    private ArrayList<String> list;

    @Override
    protected int getContentView() {
        return R.layout.activity_lyjc_type;
    }

    @Override
    protected String getTitleId() {
        return "履约";
    }

    @Override
    protected void initData() {

        list.add("施工单位履约检查表");
        list.add("进场准备情况检查表");
        itemAdapter.setData(list);
        listview.setOnItemClickListener(this);
    }

    @Override
    protected void initView() {
        itemAdapter = new ItemAdapter(this);
        list = new ArrayList<>();
        listview.setAdapter(itemAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String s = list.get(position);
        Intent intent = new Intent(this, LyjcActivity.class);
        intent.putExtra("title", s);
        startActivity(intent);
    }
}
