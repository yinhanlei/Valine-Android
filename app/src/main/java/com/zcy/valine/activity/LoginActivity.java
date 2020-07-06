package com.zcy.valine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.zcy.valine.R;
import com.zcy.valine.base.BaseActivity;
import com.zcy.valine.config.MyConfig;

/**
 * Created by yinhanlei on 2020/7/5.
 */

public class LoginActivity extends BaseActivity {
    private Context context;
    private LinearLayout ll_register_pwd;
    private TextView btn_login, tvBtn_register, btn_back, btn_service;
    private EditText edit_login_uername, edit_login_pwd, edit_login_pwd_again;
    private ImageView btn_umane_del, btn_pwd_del, btn_pwd_del_again;
    private boolean isClickRegister = false;//ture时，表示是注册页面

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        btn_back = findViewById(R.id.btn_back);
        ll_register_pwd = findViewById(R.id.ll_register_pwd);
        tvBtn_register = findViewById(R.id.tvBtn_register);
        btn_login = findViewById(R.id.btn_login);
        edit_login_uername = findViewById(R.id.edit_login_uername);
        edit_login_pwd = findViewById(R.id.edit_login_pwd);
        edit_login_pwd_again = findViewById(R.id.edit_login_pwd_again);
        btn_umane_del = findViewById(R.id.btn_umane_del);
        btn_pwd_del = findViewById(R.id.btn_pwd_del);
        btn_pwd_del_again = findViewById(R.id.btn_pwd_del_again);
        btn_service = findViewById(R.id.btn_service);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClickRegister == false) {
                    //登  录
                    MyConfig.isLoginSuccess = true;
                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    //注  册，注册成功可默认登录成功
                }
            }
        });

        tvBtn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClickRegister == false) {
                    ll_register_pwd.setVisibility(View.VISIBLE);
                    btn_login.setText("注  册");
                    tvBtn_register.setText("登  录");
                    isClickRegister = true;
                } else {
                    ll_register_pwd.setVisibility(View.GONE);
                    btn_login.setText("登  录");
                    tvBtn_register.setText("注  册");
                    isClickRegister = false;
                }

            }
        });

        btn_umane_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_login_uername.setText("", TextView.BufferType.EDITABLE);
            }
        });
        btn_pwd_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_login_pwd.setText("", TextView.BufferType.EDITABLE);
            }
        });
        btn_pwd_del_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_login_pwd_again.setText("", TextView.BufferType.EDITABLE);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


}
