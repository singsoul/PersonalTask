package com.boniu.persontask.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.boniu.persontask.R;

//完成任务
public class FinishTaskDialog extends Dialog {

    private TextView tvPrice;
    private TextView tvRight;

    private String price;
    public FinishTaskDialog(@NonNull Context context, String price) {
        super(context, R.style.CustomProgressDialog);
        this.price = price;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_finish_task);
        tvPrice = ((TextView) findViewById(R.id.tv_price));
        tvRight = ((TextView) findViewById(R.id.tv_right));
        tvPrice.setText(price+"");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
