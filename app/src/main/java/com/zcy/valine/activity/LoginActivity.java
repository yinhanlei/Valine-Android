package com.zcy.valine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.zcy.valine.ApplicationActivity;
import com.zcy.valine.R;
import com.zcy.valine.base.BaseActivity;
import com.zcy.valine.config.MyConfig;

import cn.leancloud.AVOSCloud;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.zcy.valine.config.MyConfig.currentAppId;
import static com.zcy.valine.config.MyConfig.threadPoolExecutor;

/**
 * Created by yinhanlei on 2020/7/5.
 * 只允许当前登录一个应用。如果要登录下一个应用，则要先把之前登录的账号退出。
 */
public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    private Context context;
    private Handler handler;
    private LinearLayout ll_register_pwd, ll_register_service, ll_register_email;
    private TextView btn_login, tvBtn_register, btn_back, btn_service, btn_forgetPwd;
    private EditText edit_login_uername, edit_login_pwd, edit_login_pwd_again, edit_register_email;
    private ImageView btn_umane_del, btn_pwd_del, btn_pwd_del_again, btn_email_del;
    private boolean isClickRegister = false;//ture时，表示是注册页面
    private String id;
    private String key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        handler = new Handler();
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
        ll_register_service = findViewById(R.id.ll_register_service);

        ll_register_email = findViewById(R.id.ll_register_email);
        edit_register_email = findViewById(R.id.edit_register_email);
        btn_email_del = findViewById(R.id.btn_email_del);
        btn_forgetPwd = findViewById(R.id.btn_forgetPwd);

        id = getIntent().getStringExtra("id");
        key = getIntent().getStringExtra("key");

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClickRegister == false) {
                    try {
                        //登  录
                        final String uName = edit_login_uername.getText().toString();
                        final String pwd = edit_login_pwd.getText().toString();
                        if (uName.length() == 0 || pwd.length() == 0) {
                            Toast.makeText(context, "请填账户密码", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.d(TAG, "uName= " + uName + "  pwd= " + pwd);

                        //初始化
                        AVOSCloud.initialize(id, key);
                        //已登账户将退出
                        AVUser currentUser = AVUser.getCurrentUser();
                        if (currentUser != null) {
                            Log.d(TAG, "已登账户将退出，uName= " + currentUser.getUsername());
                            AVUser.logOut();
                        }
                        //登录当前应用的账号
                        if (uName.contains("@") && uName.contains(".com")) {
                            threadPoolExecutor.execute(new Runnable() {//网络请求都要放进线程
                                @Override
                                public void run() {
                                    AVUser.loginByEmail(uName, pwd).subscribe(new Observer<AVUser>() {
                                        public void onSubscribe(Disposable disposable) {
                                        }

                                        public void onNext(AVUser user) {
                                            // 登录成功
                                            currentAppId = id;
                                            Log.d(TAG, "邮箱登录成功");
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            startActivity(new Intent(context, CommentActivity.class));
                                            finish();
                                        }

                                        public void onError(Throwable throwable) {
                                            // 登录失败（可能是密码错误）
                                            final String error = throwable.getMessage();
                                            Log.d(TAG, "邮箱登录失败= " + error);
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(context, "失败：" + error, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                        public void onComplete() {
                                        }
                                    });
                                }
                            });
                        } else {
                            threadPoolExecutor.execute(new Runnable() {//网络请求都要放进线程
                                @Override
                                public void run() {
                                    AVUser.logIn(uName, pwd).subscribe(new Observer<AVUser>() {
                                        public void onSubscribe(Disposable disposable) {
                                        }

                                        public void onNext(AVUser user) {
                                            currentAppId = id;
                                            // 登录成功
                                            Log.d(TAG, "账户登录成功");
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            startActivity(new Intent(context, CommentActivity.class));
                                            finish();
                                        }

                                        public void onError(Throwable throwable) {
                                            // 登录失败（可能是密码错误）
                                            final String error = throwable.getMessage();
                                            Log.d(TAG, "账户登录失败= " + error);
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(context, "失败：" + error, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                        public void onComplete() {
                                        }
                                    });
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        final String uName = edit_login_uername.getText().toString();
                        final String pwd = edit_login_pwd.getText().toString();
                        final String pwdAg = edit_login_pwd_again.getText().toString();
                        final String email = edit_register_email.getText().toString();
                        if (uName.length() == 0 || pwd.length() == 0 || pwdAg.length() == 0 || email.length() == 0) {
                            Toast.makeText(context, "请填写完整", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!email.contains("@") || !email.contains(".")) {
                            Toast.makeText(context, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (pwd.length() < 6) {
                            Toast.makeText(context, "密码长度需要大于6", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!pwd.equals(pwdAg)) {
                            Toast.makeText(context, "两次密码不一致", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //初始化
                        AVOSCloud.initialize(id, key);
                        threadPoolExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                // 创建实例
                                AVUser user = new AVUser();
                                user.setUsername(uName);
                                user.setPassword(pwdAg);
                                user.setEmail(email);

                                user.signUpInBackground().subscribe(new Observer<AVUser>() {
                                    public void onSubscribe(Disposable disposable) {
                                    }

                                    public void onNext(AVUser user) {
                                        // 注册成功
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                    public void onError(Throwable throwable) {
                                        // 注册失败（通常是因为用户名已被使用）
                                        final String error = throwable.getMessage();
                                        Log.d(TAG, "注册失败= " + error);
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(context, "失败：" + error, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                    public void onComplete() {
                                    }
                                });
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        tvBtn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClickRegister == false) {
                    ll_register_pwd.setVisibility(View.VISIBLE);
                    ll_register_email.setVisibility(View.VISIBLE);
                    btn_login.setText("注  册");
                    tvBtn_register.setText("登  录");
                    ll_register_service.setVisibility(View.VISIBLE);
                    isClickRegister = true;
                } else {
                    ll_register_pwd.setVisibility(View.GONE);
                    ll_register_email.setVisibility(View.GONE);
                    btn_login.setText("登  录");
                    tvBtn_register.setText("注  册");
                    ll_register_service.setVisibility(View.GONE);
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
        btn_forgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //忘记密码
                startActivity(new Intent(context, ForgetPwdActivity.class));
            }
        });

        btn_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


}
