package com.zcy.valine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zcy.valine.activity.ApplicationAddActivity;
import com.zcy.valine.activity.CommentActivity;
import com.zcy.valine.activity.LoginActivity;
import com.zcy.valine.base.BaseActivity;
import com.zcy.valine.bean.ApplicationBean;
import com.zcy.valine.config.MyConfig;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVOSCloud;

/**
 * Created by yinhanlei on 2020/7/5.
 * 应用列表
 */

public class ApplicationActivity extends BaseActivity {

    private static final String TAG = "ApplicationActivity";

    private Context context;

    //模拟数据
    private List<ApplicationBean> list = new ArrayList<>();
    private ListView listView_appliaction;
    private TextView btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_list);
        context = this;
        listView_appliaction = findViewById(R.id.listView_appliaction);
        btn_add = findViewById(R.id.btn_add);

        //模拟数据
        list.add(new ApplicationBean("测试", "7yIoRlSmfX09vQCERsuWzFnx-MdYXbMMI", "3zCL5GFePTUjwbqLop44QFbr"));
        list.add(new ApplicationBean("name1", "id1", "key1"));

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itAdd = new Intent(context, ApplicationAddActivity.class);
                startActivity(itAdd);
            }
        });

        listView_appliaction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //点击跳转
                if (MyConfig.isLoginSuccess) {
                    startActivity(new Intent(context, CommentActivity.class));
                } else {
                    ApplicationBean bean = list.get(i);
                    Log.d(TAG, "id= " + bean.getApplicationId() + "  key= " + bean.getApplicationKey());
                    AVOSCloud.initialize(bean.getApplicationId(), bean.getApplicationKey());
                    startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });

        listView_appliaction.setAdapter(new MyAdapter());

    }

    /**
     * 自定义适配器
     */
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_application, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ApplicationBean bean = list.get(position);
            viewHolder.name.setText(bean.getApplicationName());
            viewHolder.id.setText(bean.getApplicationId());
            viewHolder.key.setText(bean.getApplicationKey());

            return convertView;
        }

        public class ViewHolder {
            private TextView name, id, key;

            public ViewHolder(View rootView) {
                this.name = rootView.findViewById(R.id.name);
                this.id = rootView.findViewById(R.id.id);
                this.key = rootView.findViewById(R.id.key);
            }
        }
    }


}
