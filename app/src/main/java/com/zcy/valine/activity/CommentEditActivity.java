package com.zcy.valine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.zcy.valine.R;
import com.zcy.valine.base.BaseActivity;

import java.util.Set;

import cn.leancloud.AVObject;

import static com.zcy.valine.config.MyConfig.threadPoolExecutor;

/**
 * Created by yhl
 * on 2020/7/6
 */
public class CommentEditActivity extends BaseActivity {
    private static final String TAG = "CommentEditActivity";

    public static final int commentCode = 10001;

    private Handler handler;
    private Context context;
    private TextView btn_back, btn_del, createdAt_1;
    private ScrollView scrollView;
    private LinearLayout ll_item;
    private String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_edit_dynamic);
        context = CommentEditActivity.this;
        handler = new Handler();
        btn_back = findViewById(R.id.btn_back);
        btn_del = findViewById(R.id.btn_del);

        scrollView = findViewById(R.id.scrollView);
        ll_item = findViewById(R.id.ll_item);

        createdAt_1 = findViewById(R.id.createdAt_1);

        Intent intent = getIntent();
        JSONObject serverData = JSONObject.parseObject(intent.getStringExtra("serverData"));
        objectId = intent.getStringExtra("objectId");
        createdAt_1.setText(intent.getStringExtra("createdAt"));

        dynamicSetData(serverData);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String objectIdStr = objectId;
                Log.d(TAG, "del objectIdStr= " + objectIdStr);
                //1 删除接口
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AVObject comment = AVObject.createWithoutData("Comment", objectIdStr);
                            comment.delete();
                            //2 删除成功后，返回评论管理，并更新数据源和适配器
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d(TAG, "Exception= " + e.getMessage());
                        }
                    }
                });
            }
        });

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // 判断是在顶部、底部
                        if (scrollView.getScrollY() <= 0) {
                            //                            Toast.makeText(context, "到达顶部", Toast.LENGTH_SHORT).show();
                            break;
                        } else if (scrollView.getChildAt(0).getMeasuredHeight() <=
                                scrollView.getHeight() + scrollView.getScrollY()) {
                            Toast.makeText(context, "到达底部", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
        if (requestCode == commentCode && data != null) {
            JSONObject serverData = JSONObject.parseObject(data.getStringExtra("serverData"));
            //更新
            ll_item.removeAllViews();
            dynamicSetData(serverData);
        }
    }

    /**
     * 动态显示数据
     *
     * @param serverData
     */
    private void dynamicSetData(final JSONObject serverData) {
        Set<String> keySet = serverData.keySet();//keySet()方法获取key的Set集合
        for (final String key : keySet) {
            //对Set集合遍历,打印出jsonObject中的子元素
            final String value = serverData.getString(key);
            //            Log.d(TAG, "key= " + key + "  value= " + value);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(10, 5, 0, 0);//4个参数按bai顺序分别是左du上右下

            TextView keyView = new TextView(context);
            keyView.setTextColor(getResources().getColor(R.color.light_gray3));
            keyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            keyView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            keyView.setText(key);
            //            keyView.setLayoutParams(layoutParams);
            ll_item.addView(keyView);

            TextView valueView = new TextView(context);
            if (key.equals("objectId") || key.equals("createdAt") || key.equals("updatedAt")){
                valueView.setTextColor(getResources().getColor(R.color.light_gray4));
            }else {
                valueView.setTextColor(getResources().getColor(R.color.black));
            }
            valueView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            valueView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            valueView.setText(serverData.getString(key));
            //            valueView.setLayoutParams(layoutParams);
            ll_item.addView(valueView);

            View line = new View(context);
            layoutParams.height = 1;
            line.setLayoutParams(layoutParams);
            line.setBackgroundResource(R.color.light_gray1);
            ll_item.addView(line);

            keyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (key.equals("objectId") || key.equals("createdAt") || key.equals("updatedAt")) {
                        Toast.makeText(context, "该项不可编辑", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent it = new Intent(context, CommentContentActivity.class);
                    it.putExtra("key", key);
                    it.putExtra("value", value);
                    it.putExtra("serverData", JSONObject.toJSONString(serverData));
                    it.putExtra("objectId", objectId);
                    startActivityForResult(it, 10001);
                }
            });

            valueView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (key.equals("objectId") || key.equals("createdAt") || key.equals("updatedAt")) {
                        Toast.makeText(context, "该项不可编辑", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent it = new Intent(context, CommentContentActivity.class);
                    it.putExtra("key", key);
                    it.putExtra("value", value);
                    it.putExtra("serverData", JSONObject.toJSONString(serverData));
                    it.putExtra("objectId", objectId);
                    CommentEditActivity.this.startActivityForResult(it, 10001);
                }
            });
        }
    }

}
