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
import android.widget.Toast;

import com.zcy.valine.activity.ApplicationAddActivity;
import com.zcy.valine.activity.CommentActivity;
import com.zcy.valine.activity.LoginActivity;
import com.zcy.valine.base.BaseActivity;
import com.zcy.valine.bean.ApplicationBean;
import com.zcy.valine.utils.PermissionsUtils;
import com.zcy.valine.utils.SDUtils;
import com.zcy.valine.utils.SerializableUtils;

import java.util.ArrayList;
import java.util.List;

import static com.zcy.valine.config.MyConfig.currentAppId;

/**
 * Created by yinhanlei on 2020/7/5.
 * 应用列表
 */

public class ApplicationActivity extends BaseActivity {

    private static final String TAG = "ApplicationActivity";

    public static String appListPath = "";
    public static List<ApplicationBean> applicationList = new ArrayList<>();

    public static Context context;
    private ListView listView_appliaction;
    private TextView btn_add;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_list);
        context = ApplicationActivity.this;
        listView_appliaction = findViewById(R.id.listView_appliaction);
        btn_add = findViewById(R.id.btn_add);
        PermissionsUtils.verifyStoragePermissions(ApplicationActivity.this);
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
                ApplicationBean bean = applicationList.get(i);//将要跳转的
                if (currentAppId.length() == 0) {
                    //当前没有登录过
                    Intent it = new Intent(context, LoginActivity.class);
                    it.putExtra("id", bean.getApplicationId());
                    it.putExtra("key", bean.getApplicationKey());
                    startActivity(it);
                } else {
                    //当前有登录
                    if (currentAppId.equals(bean.getApplicationId())) {
                        //当前登录的应用是点击的项，去评论页面
                        startActivity(new Intent(context, CommentActivity.class));
                    } else {
                        //当前登录的应用不是点击的项，说明用户可能要切换应用登录，将去登录页面
                        Intent it = new Intent(context, LoginActivity.class);
                        it.putExtra("id", bean.getApplicationId());
                        it.putExtra("key", bean.getApplicationKey());
                        startActivity(it);
                    }
                }
                //                if (MyConfig.loginMap != null && MyConfig.loginMap.size() > 0 && MyConfig.loginMap.containsKey(bean.getApplicationId()) && MyConfig.loginMap.get(bean.getApplicationId())) {
                //                    startActivity(new Intent(context, CommentActivity.class));
                //                } else {
                //                    Intent it = new Intent(context, LoginActivity.class);
                //                    it.putExtra("id", bean.getApplicationId());
                //                    it.putExtra("key", bean.getApplicationKey());
                //                    startActivity(it);
                //                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (applicationList.size() > 0)
            applicationList.clear();
        appListPath = SDUtils.getSDPath() + "/valine_application.txt";
        applicationList = (List<ApplicationBean>) SerializableUtils.getDeserializeObject(appListPath);
        if (applicationList == null || applicationList.size() == 0) {
            applicationList = new ArrayList<>();
            Toast.makeText(context, "请新增一个应用", Toast.LENGTH_SHORT).show();
        } else {
            if (myAdapter == null)
                myAdapter = new MyAdapter();
            listView_appliaction.setAdapter(myAdapter);
        }

    }

    /**
     * 自定义适配器
     */
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return applicationList.size();
        }

        @Override
        public Object getItem(int position) {
            return applicationList.get(position);
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
            final ApplicationBean bean = applicationList.get(position);
            viewHolder.name.setText("name：" + bean.getApplicationName());
            viewHolder.id.setText("id：" + bean.getApplicationId());
            viewHolder.key.setText("key：" + bean.getApplicationKey());
            viewHolder.btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除应用
                    applicationList.remove(bean);
                    myAdapter.notifyDataSetChanged();
                    SerializableUtils.serializableObjectToFile(applicationList, appListPath);
                    Toast.makeText(context, "删除应用成功", Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }

        public class ViewHolder {
            private TextView name, id, key, btn_del;

            public ViewHolder(View rootView) {
                this.name = rootView.findViewById(R.id.name);
                this.id = rootView.findViewById(R.id.id);
                this.key = rootView.findViewById(R.id.key);
                this.btn_del = rootView.findViewById(R.id.btn_del);
            }
        }
    }


}
