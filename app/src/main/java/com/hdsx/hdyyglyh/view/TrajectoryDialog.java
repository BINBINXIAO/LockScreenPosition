package com.hdsx.hdyyglyh.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.hdsx.hdyyglyh.R;

public class TrajectoryDialog extends Dialog {

    public TrajectoryDialog(@NonNull Context context) {
        super(context);
    }

    public TrajectoryDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected TrajectoryDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_trajectory);
        initView();
    }


    private void initView() {
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
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

    public interface SaveTrackDataListener {
        void onSaveTrackData();
    }

}
