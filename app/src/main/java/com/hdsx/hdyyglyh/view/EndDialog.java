package com.hdsx.hdyyglyh.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.hdsx.hdyyglyh.R;

public class EndDialog extends Dialog {


    public EndDialog(@NonNull Context context) {
        super(context);
    }

    public EndDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected EndDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_dialog);
        initView();
    }


    private void initView() {
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saveTrackDataListener != null) {
                    saveTrackDataListener.onSaveTrackData();
                }
                dismiss();
            }
        });
    }

    public SaveTrackDataListener saveTrackDataListener;

    public SaveTrackDataListener getSaveTrackDataListener() {
        return saveTrackDataListener;
    }

    public void setSaveTrackDataListener(SaveTrackDataListener saveTrackDataListener) {
        this.saveTrackDataListener = saveTrackDataListener;
    }

    public interface SaveTrackDataListener{
        void onSaveTrackData();
    }

}
