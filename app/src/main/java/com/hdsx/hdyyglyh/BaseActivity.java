package com.hdsx.hdyyglyh;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public abstract class BaseActivity extends CheckPermissionsActivity {

    protected Toolbar toolbar;
    protected TextView title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.title);
        ButterKnife.bind(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        init(savedInstanceState);
        initView();
        initData();
        if (title != null) {
            title.setText(getTitleId());
        }
    }

    protected void init(Bundle savedInstanceState) {
    }

    protected abstract int getContentView();

    protected abstract String getTitleId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public BaseActivity hide(View view) {
        view.setVisibility(GONE);
        return this;
    }

    public BaseActivity show(View view) {
        view.setVisibility(VISIBLE);
        return this;
    }

    public  boolean getInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) App.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasData(List<?> datas) {
        return datas != null && datas.size() > 0;
    }

//    @Override
//    public void overridePendingTransition(int enterAnim, int exitAnim) {
//        overridePendingTransition(R.anim.login_no, R.anim.login_close);
//    }
}