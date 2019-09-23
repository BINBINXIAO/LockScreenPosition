package com.hdsx.hdyyglyh.page.lhyh;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hdsx.hdyyglyh.BaseActivity;
import com.hdsx.hdyyglyh.R;
import com.hdsx.hdyyglyh.utils.ToastUtil;
import com.hdsx.hdyyglyh.view.LhDialog;
import com.hdsx.hdyyglyh.view.QsDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LhyhActivity extends BaseActivity {


    private LhDialog dialog;

    @BindView(R.id.btn)
    Button btn;

    @Override
    protected int getContentView() {
        return R.layout.activity_lhyh;
    }

    @Override
    protected String getTitleId() {
        return "绿化养护";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn:
                if (null == dialog) {
                    dialog = new LhDialog(this);
                }
                dialog.setSaveTrackDataListener(new LhDialog.SaveTrackDataListener() {
                    @Override
                    public void onSaveTrackData() {
                        String checkBtnText = dialog.getCheckBtnText();
                        if ("开始绿化".equals(checkBtnText)) {//开始绿化
                            dialog.checkBtnText("完成绿化");
                            dialog.setText("绿化中");
                            btn.setText("完成绿化");
                            ToastUtil.showToast("绿化中...");
                        } else {
                            //点击完成绿化
                            dialog.setText("日常绿化");
                            dialog.checkBtnText("开始绿化");
                            btn.setText("开始绿化");
                            ToastUtil.showToast("绿化完成了。");
                        }
                    }
                });
                dialog.show();
                break;
        }
    }
}
