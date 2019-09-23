package com.hdsx.hdyyglyh.page.jdwh;

import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.hdsx.hdyyglyh.BaseActivity;
import com.hdsx.hdyyglyh.R;

public class JdwhActivity extends BaseActivity {
    private String title = "";

    @Override
    protected int getContentView() {
        return R.layout.activity_jdwh;
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

    }

    @Override
    protected void initData() {
        title = getIntent().getStringExtra("title");
    }
}
