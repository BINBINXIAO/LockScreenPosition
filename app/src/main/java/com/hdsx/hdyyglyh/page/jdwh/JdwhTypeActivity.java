package com.hdsx.hdyyglyh.page.jdwh;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hdsx.hdyyglyh.BaseActivity;
import com.hdsx.hdyyglyh.R;
import com.hdsx.hdyyglyh.adapter.ItemAdapter;
import com.hdsx.hdyyglyh.page.jdwh.DfpActivity;
import com.hdsx.hdyyglyh.page.jdwh.JdwhActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class JdwhTypeActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.listview)
    ListView listview;

    private ItemAdapter itemAdapter;
    private ArrayList<String> list;

    @Override
    protected int getContentView() {
        return R.layout.activity_jdwh_type;
    }

    @Override
    protected String getTitleId() {
        return "机电维护";
    }

    @Override
    protected void initData() {

        list.add("日常巡检");
        list.add("故障报修");
        list.add("待分配维修任务单");
        list.add("维修任务单");
        list.add("维修费用");
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
        Intent intent = null;
        String s = list.get(position);
        switch (position){
            case 0:
            case 1:
                intent = new Intent(this, JdwhActivity.class);//机电维修
                break;
            case 2:
            case 3:
                intent = new Intent(this, DfpActivity.class);//待分配
                break;
            case 4:
                intent = new Intent(this, WxfyActivity.class);//待分配
                break;
        }
        intent.putExtra("title", s);
        startActivity(intent);
    }
}
