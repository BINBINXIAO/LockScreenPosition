package com.hdsx.hdyyglyh.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hdsx.hdyyglyh.R;

public class MenuPopupWindow extends PopupWindow {

    private ListView mListView;
    private final LayoutInflater inflater;

    public MenuPopupWindow(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // 主界面
        View view = inflater.inflate(R.layout.menu_popup_item, null); // 主界面

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int xpos = manager.getDefaultDisplay().getWidth() / 3;
        // 设置的View
        this.setContentView(view);
        // 设置弹出窗体的宽
        this.setWidth(xpos);
        // 设置弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出窗体可点击
        this.setFocusable(true);
        // 设置弹出窗体动画效果
        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
//        this.setBackgroundDrawable(dw);
    }

    private setDataListener setDataListener;

    public void setDataListener(setDataListener setDataListener) {
        this.setDataListener = setDataListener;
    }

    public interface setDataListener {
        void onDataChangeListener(String content, String code);
    }
}
