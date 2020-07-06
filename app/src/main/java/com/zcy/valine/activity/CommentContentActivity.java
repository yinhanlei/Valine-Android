package com.zcy.valine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.zcy.valine.R;
import com.zcy.valine.base.BaseActivity;

/**
 * Created by yhl
 * on 2020/7/6
 */
public class CommentContentActivity extends BaseActivity {
    private Context context;
    private TextView btn_back, btn_save;
    private EditText ed_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_edit_content);
        context = this;
        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);
        ed_content = findViewById(R.id.ed_content);

        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        ed_content.setText(content);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
