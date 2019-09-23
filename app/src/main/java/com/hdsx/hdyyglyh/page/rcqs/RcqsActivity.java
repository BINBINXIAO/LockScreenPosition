package com.hdsx.hdyyglyh.page.rcqs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hdsx.hdyyglyh.BaseActivity;
import com.hdsx.hdyyglyh.R;
import com.hdsx.hdyyglyh.utils.ToastUtil;
import com.hdsx.hdyyglyh.view.QsDialog;
import com.hdsx.hdyyglyh.view.SjDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RcqsActivity extends BaseActivity {

    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.btn_cf)
    Button btn_cf;

    private QsDialog qsDialog;
    private SjDialog cfDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_rcqs;
    }

    @Override
    protected String getTitleId() {
        return "日常清扫";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn, R.id.btn_cf})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                if (null == qsDialog) {
                    qsDialog = new QsDialog(this);
                }
                qsDialog.setSaveTrackDataListener(new QsDialog.SaveTrackDataListener() {
                    @Override
                    public void onSaveTrackData() {
                        String checkBtnText = qsDialog.getCheckBtnText();
                        if ("开始清扫".equals(checkBtnText)) {//开始清扫
                            qsDialog.checkBtnText("完成清扫");
                            qsDialog.setText("清扫中");
                            btn.setText("完成清扫");
                            ToastUtil.showToast("清扫中...");
                        } else {
                            //点击完成清扫
                            qsDialog.setText("日常清扫");
                            qsDialog.checkBtnText("开始清扫");
                            btn.setText("开始清扫");
                            ToastUtil.showToast("清扫完成了。");
                        }
                    }
                });
                qsDialog.show();
                break;
            case R.id.btn_cf:
                if (null == cfDialog) {
                    cfDialog = new SjDialog(this);
                }
                cfDialog.setSaveTrackDataListener(new SjDialog.SaveTrackDataListener() {
                    @Override
                    public void onSaveTrackData() {
                        String checkBtnText = cfDialog.getCheckBtnText();
                        if ("出发".equals(checkBtnText)) {//出发
                            cfDialog.checkBtnText("到达现场");
                            cfDialog.setText("出发中");
                            btn_cf.setText("到达现场");
                            ToastUtil.showToast("出发中...");
                        } else if ("到达现场".equals(checkBtnText)) {
                            //到达现场开始清扫
                            cfDialog.checkBtnText("完成清扫");
                            cfDialog.setText("清扫中");
                            btn_cf.setText("完成清扫");
                            ToastUtil.showToast("清扫中...");
                        } else {
                            //点击完成清扫
                            cfDialog.checkBtnText("出发");
                            cfDialog.setText("事件清扫");
                            btn_cf.setText("出发");
                            ToastUtil.showToast("清扫完成了。");
                        }
                    }
                });
                cfDialog.show();
                break;
        }
    }
}
