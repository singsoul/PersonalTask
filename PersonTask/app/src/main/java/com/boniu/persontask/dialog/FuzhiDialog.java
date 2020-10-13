package com.boniu.persontask.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.boniu.persontask.R;
import com.boniu.persontask.utils.AppPackageUtils;


public class FuzhiDialog extends Dialog {
    private String yaoqintxt;
    public FuzhiDialog(@NonNull Context context, String yaoqintxt) {
        super(context, R.style.CustomProgressDialog);
        this.yaoqintxt = yaoqintxt;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_fuzhi);
        AppPackageUtils.setClipboard(getContext(),yaoqintxt);
        findViewById(R.id.tv_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPackageUtils.startApp(getContext(),"com.tencent.mm","");
            }
        });

        findViewById(R.id.img_cha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}

