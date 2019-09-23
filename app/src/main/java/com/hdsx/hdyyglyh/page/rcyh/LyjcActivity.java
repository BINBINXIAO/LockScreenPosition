package com.hdsx.hdyyglyh.page.rcyh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdsx.hdyyglyh.BaseActivity;
import com.hdsx.hdyyglyh.R;
import com.hdsx.hdyyglyh.utils.RealPathFromUriUtils;
import com.hdsx.hdyyglyh.utils.ToastUtil;
import com.hdsx.hdyyglyh.view.CheckPersonDialog;
import com.hdsx.hdyyglyh.view.dateview.CustomDatePicker;
import com.hdsx.hdyyglyh.view.dateview.DateFormatUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LyjcActivity extends BaseActivity {

    @BindView(R.id.check_date)
    LinearLayout checkDate;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_jcr)
    TextView tvJcr;
    @BindView(R.id.tv_fj)
    TextView tvFj;
    @BindView(R.id.ll_jcr)
    LinearLayout llJcr;
    @BindView(R.id.ll_fj)
    LinearLayout llFj;
    private CustomDatePicker mDatePicker;
    private CheckPersonDialog checkPersonDialog;
    private String title = "";

    @Override
    protected int getContentView() {
        return R.layout.activity_lyjc;
    }

    @Override
    protected String getTitleId() {
        if ("".equals(title))
            return "";
        else
            return title;
    }

    @Override
    protected void initView() {
        initTimerPicker();
    }

    @Override
    protected void initData() {
        title = getIntent().getStringExtra("title");
    }

    private void initTimerPicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2009-05-01", false);
        long endTimestamp = System.currentTimeMillis();

        tvDate.setText(DateFormatUtils.long2Str(endTimestamp, false));

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                String s = DateFormatUtils.long2Str(timestamp, false);
                tvDate.setText(s);
            }
        }, beginTimestamp, endTimestamp);
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(false);
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePicker.setScrollLoop(false);
        // 不允许滚动动画
        mDatePicker.setCanShowAnim(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatePicker.onDestroy();
    }

    @OnClick({R.id.check_date, R.id.ll_jcr,R.id.ll_fj})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.check_date:
                // 日期格式为yyyy-MM-dd
                mDatePicker.show(tvDate.getText().toString());
                break;
            case R.id.ll_jcr:
                if (null == checkPersonDialog) {
                    checkPersonDialog = new CheckPersonDialog(this);
                    checkPersonDialog.setSaveTrackDataListener(new CheckPersonDialog.SaveTrackDataListener() {
                        @Override
                        public void onSaveTrackData(String text) {
                            tvJcr.setText(text);
                        }
                    });
                }
                checkPersonDialog.show();
                break;
            case R.id.ll_fj:
                Intent intent  = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,111);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==111 && resultCode == Activity.RESULT_OK) {
            String path = RealPathFromUriUtils.getPath(this, data.getData());
            File file = new File(path);
            String name = file.getName();
            tvFj.setText(name);
        }
    }
}
