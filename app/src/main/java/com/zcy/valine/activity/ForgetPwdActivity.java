package com.zcy.valine.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.zcy.valine.R;
import com.zcy.valine.base.BaseActivity;
import com.zcy.valine.bean.CommentItemDyBean;

import cn.leancloud.AVUser;

import static com.zcy.valine.config.MyConfig.threadPoolExecutor;

/**
 * Created by yhl
 * on 2020/7/21
 */
public class ForgetPwdActivity extends BaseActivity {

    private Context context;
    private Handler handler;
    private TextView btn_save, btn_back;
    private EditText edit_register_email;
    private ImageView btn_email_del;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);
        context = this;
        handler = new Handler();
        btn_save = findViewById(R.id.btn_save);
        edit_register_email = findViewById(R.id.edit_register_email);
        btn_email_del = findViewById(R.id.btn_email_del);
        btn_back = findViewById(R.id.btn_back);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edit_register_email.getText().toString();
                if (email.length() == 0) {
                    Toast.makeText(context, "请填写邮箱", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!email.contains("@") || !email.contains(".")) {
                    Toast.makeText(context, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AVUser.requestPasswordResetInBackground(email).blockingSubscribe();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        btn_email_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_register_email.setText("", TextView.BufferType.EDITABLE);
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
