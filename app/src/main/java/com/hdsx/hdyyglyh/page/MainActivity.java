package com.hdsx.hdyyglyh.page;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.hdsx.hdyyglyh.BaseActivity;
import com.hdsx.hdyyglyh.R;
import com.hdsx.hdyyglyh.page.jdwh.JdwhTypeActivity;
import com.hdsx.hdyyglyh.page.lhyh.LhyhActivity;
import com.hdsx.hdyyglyh.page.rcqs.RcqsActivity;
import com.hdsx.hdyyglyh.page.rcyh.lyjcTypeActivity;
import com.hdsx.hdyyglyh.page.yhxc.XcMapActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_lyjc)
    Button btnLyjc;
    @BindView(R.id.btn_yhxc)
    Button btnYhxc;
    @BindView(R.id.btn_rcqs)
    Button btnRcqs;
    @BindView(R.id.btn_lvyh)
    Button btnLvyh;
    @BindView(R.id.btn_jdwh)
    Button btnJdwh;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTitleId() {
        return "营运养护管理平台";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_lyjc, R.id.btn_yhxc, R.id.btn_rcqs, R.id.btn_lvyh,R.id.btn_jdwh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_lyjc:
                startActivity(new Intent(this, lyjcTypeActivity.class));
                break;
            case R.id.btn_yhxc:
                startActivity(new Intent(this, XcMapActivity.class));
                break;
            case R.id.btn_rcqs:
                startActivity(new Intent(this, RcqsActivity.class));
                break;
            case R.id.btn_lvyh:
                startActivity(new Intent(this, LhyhActivity.class));
                break;
            case R.id.btn_jdwh:
                startActivity(new Intent(this, JdwhTypeActivity.class));
                break;
        }
    }

}
