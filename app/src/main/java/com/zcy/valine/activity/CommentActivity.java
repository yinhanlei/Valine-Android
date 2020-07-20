package com.zcy.valine.activity;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zcy.valine.R;
import com.zcy.valine.base.BaseActivity;
import com.zcy.valine.bean.CommentItemBean;
import com.zcy.valine.bean.CommentItemDyBean;
import com.zcy.valine.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yhl
 * on 2020/7/6
 * 评论列表
 */
public class CommentActivity extends BaseActivity {
    private static final String TAG = "CommentActivity";
    private Context context;
    private Handler handler;
    private TextView btn_back;
    private ListView listView_comment;

    public static List<CommentItemDyBean> dynamicComments = new ArrayList<>();//item里的组件是动态的
    //    public static List<CommentItemBean> commentList = new ArrayList<>();//item里的组件是固定的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        context = this;
        handler = new Handler();
        btn_back = findViewById(R.id.btn_back);
        listView_comment = findViewById(R.id.listView_comment);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView_comment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //点击跳转
                Intent it = new Intent(context, CommentEditActivity.class);
                CommentItemDyBean bean = dynamicComments.get(i);
                it.putExtra("createdAt", bean.getCreatedAt());
                it.putExtra("objectId", bean.getObjectId());
                it.putExtra("serverData", JSONObject.toJSONString(bean.getServerData()));
                startActivity(it);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //        if (commentList.size() > 0)
        //            commentList.clear();
        if (dynamicComments.size() > 0)
            dynamicComments.clear();

        /**
         * 请求数据
         */
        new Thread(new Runnable() {//网络请求都要放进线程
            @Override
            public void run() {
                AVQuery<AVObject> query = new AVQuery<>("Comment");//Comment就是存储的表名
                query.orderByDescending("createdAt");
                query.findInBackground().subscribe(new Observer<List<AVObject>>() {
                    public void onSubscribe(Disposable disposable) {
                    }

                    public void onNext(List<AVObject> comments) {
                        Log.d(TAG, "comments= " + JSONObject.toJSONString(comments));
                        //                        setData(JSONObject.toJSONString(comments));
                        setData1(comments);
                    }

                    public void onError(Throwable throwable) {
                    }

                    public void onComplete() {
                    }
                });
            }
        }).start();


    }

    /**
     * 动态的
     *
     * @param comments
     */
    private void setData1(List<AVObject> comments) {
        for (AVObject comment : comments) {
            //            Log.d(TAG, "comment= " + comment);
            JSONObject commentJson = JSONObject.parseObject(JSONObject.toJSONString(comment));
            JSONObject serverData = commentJson.getJSONObject("serverData");
            dynamicComments.add(new CommentItemDyBean(DateUtils.parseDate(comment.getCreatedAtString()), comment.getObjectId(), serverData));
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                listView_comment.setAdapter(new MyAdapter1());
            }
        });
    }

    /**
     * 自定义适配器，动态
     */
    private class MyAdapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            return dynamicComments.size();
        }

        @Override
        public Object getItem(int position) {
            return dynamicComments.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_comment_dynamic, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            /**
             * 动态给布局添加组件
             */
            viewHolder.ll_item.removeAllViews();
            CommentItemDyBean bean = dynamicComments.get(position);
            String createdAt = bean.getCreatedAt();
            JSONObject serverData = bean.getServerData();
            viewHolder.createdAt_1.setText(createdAt);
            Set<String> keySet = serverData.keySet();//keySet()方法获取key的Set集合
            for (String key : keySet) {
                //对Set集合遍历,打印出jsonObject中的子元素
                //                Log.d(TAG, "key= " + key + "  vaule= " + serverData.getString(key));
                TextView textView = new TextView(context);
                textView.setTextColor(getResources().getColor(R.color.light_gray5));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                textView.setText(key + "：" + serverData.getString(key));
                viewHolder.ll_item.addView(textView);
            }

            return convertView;
        }

        public class ViewHolder {
            private LinearLayout ll_item;//里面的项是动态的
            private TextView createdAt_1;

            public ViewHolder(View rootView) {
                this.ll_item = rootView.findViewById(R.id.ll_item);
                this.createdAt_1 = rootView.findViewById(R.id.createdAt_1);
            }
        }
    }


    //    /**
    //     * 处理数据
    //     *
    //     * @param comments
    //     */
    //    private void setData(String comments) {
    //        JSONArray commentsArr = JSONObject.parseArray(comments);
    //        for (int i = 0; i < commentsArr.size(); i++) {
    //            JSONObject a = commentsArr.getJSONObject(i);
    //            JSONObject serverData = a.getJSONObject("serverData");
    //
    //            String comment = serverData.getString("comment");
    //            String createdAt = serverData.getString("createdAt");
    //            String updatedAt = serverData.getString("updatedAt");
    //            String QQAvatar = serverData.getString("QQAvatar");
    //            String insertedAt = serverData.getString("insertedAt");
    //            String ip = serverData.getString("ip");
    //            String link = serverData.getString("link");
    //            String mail = serverData.getString("mail");
    //            String nick = serverData.getString("nick");
    //            String ua = serverData.getString("ua");
    //            String url = serverData.getString("url");
    //            String objectId = serverData.getString("objectId");
    //            commentList.add(new CommentItemBean(comment, DateUtils.parseDate(createdAt), DateUtils.parseDate(updatedAt), QQAvatar, insertedAt, ip, link, mail, nick, ua, url, objectId));
    //        }
    //        handler.post(new Runnable() {
    //            @Override
    //            public void run() {
    //                listView_comment.setAdapter(new MyAdapter());
    //            }
    //        });
    //
    //    }

    //    /**
    //     * 自定义适配器
    //     */
    //    private class MyAdapter extends BaseAdapter {
    //
    //        @Override
    //        public int getCount() {
    //            return commentList.size();
    //        }
    //
    //        @Override
    //        public Object getItem(int position) {
    //            return commentList.get(position);
    //        }
    //
    //        @Override
    //        public long getItemId(int position) {
    //            return position;
    //        }
    //
    //        @Override
    //        public View getView(final int position, View convertView, ViewGroup parent) {
    //            final ViewHolder viewHolder;
    //            if (convertView == null) {
    //                convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
    //                viewHolder = new ViewHolder(convertView);
    //                convertView.setTag(viewHolder);
    //            } else {
    //                viewHolder = (ViewHolder) convertView.getTag();
    //            }
    //            CommentItemBean bean = commentList.get(position);
    //            viewHolder.createdAt_1.setText(bean.getCreatedAt());
    //            viewHolder.comment.setText("comment：" + bean.getComment());
    //            viewHolder.createdAt.setText("createdAt：" + bean.getCreatedAt());
    //            viewHolder.nick.setText("nick：" + bean.getNick());
    //            viewHolder.objectId.setText("objectId：" + bean.getObjectId());
    //            viewHolder.updatedAt.setText("updatedAt：" + bean.getUpdatedAt());
    //            viewHolder.QQAvatar.setText("QQAvatar：" + bean.getQQAvatar());
    //            viewHolder.insertedAt.setText("insertedAt：" + bean.getInsertedAt());
    //            viewHolder.ip.setText("ip：" + bean.getIp());
    //            viewHolder.link.setText("link：" + bean.getLink());
    //            viewHolder.mail.setText("mail：" + bean.getMail());
    //            viewHolder.ua.setText("ua：" + bean.getUa());
    //            viewHolder.url.setText("url：" + bean.getUrl());
    //            return convertView;
    //        }
    //
    //        public class ViewHolder {
    //            private LinearLayout ll_item;//里面的项是动态的
    //            private TextView createdAt_1, comment, createdAt, updatedAt, QQAvatar, insertedAt, ip, link, mail, nick, ua, url, objectId;
    //
    //            public ViewHolder(View rootView) {
    //                this.createdAt_1 = rootView.findViewById(R.id.createdAt_1);
    //                this.comment = rootView.findViewById(R.id.comment);
    //                this.createdAt = rootView.findViewById(R.id.createdAt);
    //                this.nick = rootView.findViewById(R.id.nick);
    //                this.objectId = rootView.findViewById(R.id.objectId);
    //                this.updatedAt = rootView.findViewById(R.id.updatedAt);
    //
    //                this.QQAvatar = rootView.findViewById(R.id.QQAvatar);
    //                this.insertedAt = rootView.findViewById(R.id.insertedAt);
    //                this.ip = rootView.findViewById(R.id.ip);
    //                this.link = rootView.findViewById(R.id.link);
    //                this.mail = rootView.findViewById(R.id.mail);
    //                this.ua = rootView.findViewById(R.id.ua);
    //                this.url = rootView.findViewById(R.id.url);
    //            }
    //        }
    //    }


}
