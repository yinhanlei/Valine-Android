package com.zcy.valine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zcy.valine.R;
import com.zcy.valine.base.BaseActivity;

/**
 * Created by yhl
 * on 2020/7/6
 */
public class CommentEditActivity extends BaseActivity {

    private Context context;
    private TextView btn_back, tv_time, tv_title, tv_content;

    private String time;
    private String title;
    private String content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_edit);
        context = this;
        btn_back = findViewById(R.id.btn_back);
        tv_time = findViewById(R.id.tv_time);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);

        Intent intent = getIntent();
        time = intent.getStringExtra("time");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");

        tv_time.setText(time);
        tv_title.setText(title);
        tv_content.setText(content);
        tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, CommentContentActivity.class);
                it.putExtra("content", content);
                startActivity(it);
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
