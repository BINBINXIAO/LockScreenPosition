package com.hdsx.hdyyglyh.page.jdwh;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.hdsx.hdyyglyh.BaseActivity;
import com.hdsx.hdyyglyh.R;
import com.hdsx.hdyyglyh.adapter.DfpAdapter;
import com.hdsx.hdyyglyh.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;

public class DfpActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.listview)
    ListView listview;
    private String title = "";
    private DfpAdapter dfpAdapter;

    private Button wxdj, wxys, bjly, fjrk, wxfy;

    @Override
    protected int getContentView() {
        return R.layout.activity_dfp;
    }

    @Override
    protected String getTitleId() {
        if ("".equals(title))
            return null;
        else
            return title;
    }

    @Override
    protected void initView() {
        dfpAdapter = new DfpAdapter(this);
        listview.setAdapter(dfpAdapter);

        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        dfpAdapter.setData(list);
    }

    @Override
    protected void initData() {
        title = getIntent().getStringExtra("title");

        if (!"待分配维修任务单".equals(title)) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_header, null);
            wxdj = view.findViewById(R.id.wxdj);
            wxys = view.findViewById(R.id.wxys);
            bjly = view.findViewById(R.id.bjly);
            fjrk = view.findViewById(R.id.fjrk);
            wxfy = view.findViewById(R.id.wxfy);

            wxdj.setOnClickListener(this);
            wxys.setOnClickListener(this);
            bjly.setOnClickListener(this);
            fjrk.setOnClickListener(this);
            wxfy.setOnClickListener(this);
            listview.addHeaderView(view);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.wxdj://维修登记
                ToastUtil.showToast("维修登记");
                intent = new Intent(this,WxdjActivity.class);
                intent.putExtra("name","维修登记");
                startActivity(intent);
                break;
            case R.id.wxys://维修验收
                ToastUtil.showToast("维修验收");
                intent = new Intent(this,WxdjActivity.class);
                intent.putExtra("name","维修验收");
                startActivity(intent);
                break;
            case R.id.bjly://备件领用
                ToastUtil.showToast("备件领用");
                intent = new Intent(this,BjlyActivity.class);
                intent.putExtra("name","备件领用");
                startActivity(intent);
                break;
            case R.id.fjrk://废件入库申请
                ToastUtil.showToast("废件入库申请");
                break;
            case R.id.wxfy://维修费用结算
                ToastUtil.showToast("维修费用结算");
                break;
        }
    }
}
