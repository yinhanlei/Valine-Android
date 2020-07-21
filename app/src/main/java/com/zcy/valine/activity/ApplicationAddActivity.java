package com.zcy.valine.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.zcy.valine.R;
import com.zcy.valine.base.BaseActivity;
import com.zcy.valine.bean.ApplicationBean;
import com.zcy.valine.utils.SerializableUtils;

import static com.zcy.valine.ApplicationActivity.appListPath;
import static com.zcy.valine.ApplicationActivity.applicationList;

/**
 * Created by yinhanlei on 2020/7/5.
 */

public class ApplicationAddActivity extends BaseActivity {

    private static final String TAG = "ApplicationAddActivity";

    private Context context;
    private Handler handler;
    private TextView btn_save, btn_back;
    private EditText edit_name, edit_id, edit_key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_add);
        context = this;
        handler = new Handler();
        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);
        edit_name = findViewById(R.id.edit_name);
        edit_id = findViewById(R.id.edit_id);
        edit_key = findViewById(R.id.edit_key);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String name = edit_name.getText().toString();
                    String id = edit_id.getText().toString();
                    String key = edit_key.getText().toString();
                    if (name.length() == 0 || id.length() == 0 || key.length() == 0) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "请填写完整", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                    ApplicationBean bean = new ApplicationBean(name, id, key);
                    boolean isCon = false;//是否已存在
                    if (applicationList != null && applicationList.size() > 0) {
                        for (ApplicationBean bean1 : applicationList) {
                            if (id.equals(bean1.getApplicationId())) {
                                isCon = true;
                                break;
                            }
                        }
                    }
                    if (isCon) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "应用已存在", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                    applicationList.add(bean);
                    SerializableUtils.serializableObjectToFile(applicationList, appListPath);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "应用添加成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "Exception" + e.getMessage());
                }
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
