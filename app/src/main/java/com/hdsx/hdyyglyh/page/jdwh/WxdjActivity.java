package com.hdsx.hdyyglyh.page.jdwh;


import com.hdsx.hdyyglyh.BaseActivity;
import com.hdsx.hdyyglyh.R;

public class WxdjActivity extends BaseActivity {

    private String title = "";
    @Override
    protected int getContentView() {
        return R.layout.activity_wxdj;
    }

    @Override
    protected String getTitleId() {
        return title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        title = getIntent().getStringExtra("name");
    }
}
