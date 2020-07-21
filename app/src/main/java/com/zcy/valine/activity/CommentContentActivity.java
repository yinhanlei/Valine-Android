package com.zcy.valine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.zcy.valine.R;
import com.zcy.valine.base.BaseActivity;

import cn.leancloud.AVObject;

import static com.zcy.valine.config.MyConfig.threadPoolExecutor;

/**
 * Created by yhl
 * on 2020/7/6
 */
public class CommentContentActivity extends BaseActivity {
    private static final String TAG = "CommentContentActivity";

    private Handler handler;
    private Context context;
    private TextView btn_back, btn_save, edit_title;
    private EditText ed_content;
    private String key;
    private JSONObject serverData;
    private String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_edit_content);
        context = this;
        handler = new Handler();
        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);
        edit_title = findViewById(R.id.edit_title);
        ed_content = findViewById(R.id.ed_content);

        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        serverData = JSONObject.parseObject(intent.getStringExtra("serverData"));
        objectId = intent.getStringExtra("objectId");
        edit_title.setText("编辑" + key);
        ed_content.setText(intent.getStringExtra("value"));
        //        switch (key) {
        //            case "comment":
        //                ed_content.setText(data);
        //                break;
        //        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1上传修改内容
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String modifyValue = ed_content.getText().toString();
                            Log.d(TAG, "objectId= " + objectId + "  key=" + key + "  modifyValue= " + modifyValue);
                            AVObject todo = AVObject.createWithoutData("Comment", objectId);//Comment就是存储的表名
                            todo.put(key, modifyValue);
                            todo.save();
                            serverData.put(key, modifyValue);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "编辑成功", Toast.LENGTH_SHORT).show();
                                }
                            });

                            //2上传成功后，返回编辑页面，并带值回去
                            Intent i = new Intent();
                            i.putExtra("serverData", JSONObject.toJSONString(serverData));
                            setResult(CommentEditActivity.commentCode, i);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d(TAG, "Exception= " + e.getMessage());
                        }
                    }
                });
            }
        });

    }
}
