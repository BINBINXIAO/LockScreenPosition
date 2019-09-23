package com.hdsx.hdyyglyh.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hdsx.hdyyglyh.R;

public class SbsjPopup extends PopupWindow {

    TextView entry;
    TextView cancel;

    public SbsjPopup(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 主界面
        View mFilterView = inflater.inflate(R.layout.sbsj_popup, null);
        entry = mFilterView.findViewById(R.id.entry);
        cancel = mFilterView.findViewById(R.id.cancel);

        // 设置的View
        this.setContentView(mFilterView);
        // 设置弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体可点击
        this.setFocusable(true);
        // 设置弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom1);
        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable();
//        dw.setColor(R.color.colorAccent);
        // 设置弹出窗体的背景
//        this.setBackgroundDrawable(dw);
        // mFilterView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
//        mFilterView.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//
//                int height = mFilterView.findViewById(R.id.pop_layout).getTop();
//                int y = (int) event.getY();// 返回此事件的Y坐标为给定的指针索引
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y < height) {
//                        dismiss();
//                    }
//                }
//                return true;
//            }
//        });

        entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=onClickEntryListener){
                    onClickEntryListener.onClick(entry);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private OnClickEntryListener onClickEntryListener;

    public void setOnClickEntryListener(OnClickEntryListener setClickEntryListener){
        onClickEntryListener = setClickEntryListener;
    }

    public interface OnClickEntryListener{
        void onClick(TextView textView);
    }

}
