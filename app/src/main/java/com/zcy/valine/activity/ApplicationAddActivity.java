package com.zcy.valine.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zcy.valine.R;
import com.zcy.valine.base.BaseActivity;

/**
 * Created by yinhanlei on 2020/7/5.
 */

public class ApplicationAddActivity extends BaseActivity {
    private Context context;
    private TextView btn_save,btn_back;
    private EditText edit_name, edit_id, edit_key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_add);
        context = this;
        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);
        edit_name = findViewById(R.id.edit_name);
        edit_id = findViewById(R.id.edit_id);
        edit_key = findViewById(R.id.edit_key);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
