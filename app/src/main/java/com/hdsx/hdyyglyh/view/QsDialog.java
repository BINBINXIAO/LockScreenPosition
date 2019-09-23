package com.hdsx.hdyyglyh.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hdsx.hdyyglyh.R;

public class QsDialog extends Dialog {


    private TextView tv_text;
    private Button btn;

    public QsDialog(@NonNull Context context) {
        super(context);
    }

    public QsDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected QsDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qs_dialog);
        initView();
    }


    private void initView() {
        btn = findViewById(R.id.btn);
        tv_text = findViewById(R.id.tv_text);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saveTrackDataListener != null) {
                    saveTrackDataListener.onSaveTrackData();
                }
                dismiss();
            }
        });
    }

    public void setText(String textTitle) {
        if (null != tv_text)
            tv_text.setText(textTitle);
    }

    public void checkBtnText(String text) {
        if (null != btn)
            btn.setText(text);
    }

    public String getCheckBtnText() {
        CharSequence text = btn.getText();
        return text.toString();
    }

    public SaveTrackDataListener saveTrackDataListener;

    public SaveTrackDataListener getSaveTrackDataListener() {
        return saveTrackDataListener;
    }

    public void setSaveTrackDataListener(SaveTrackDataListener saveTrackDataListener) {
        this.saveTrackDataListener = saveTrackDataListener;
    }

    public interface SaveTrackDataListener {
        void onSaveTrackData();
    }

}
