package com.zcy.valine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zcy.valine.R;
import com.zcy.valine.base.BaseActivity;
import com.zcy.valine.bean.CommentItemBean;
import com.zcy.valine.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

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

    //模拟数据
    private List<CommentItemBean> commentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        context = this;
        handler = new Handler();
        btn_back = findViewById(R.id.btn_back);
        listView_comment = findViewById(R.id.listView_comment);
        new Thread(new Runnable() {//网络请求都要放进线程
            @Override
            public void run() {
                AVQuery<AVObject> query = new AVQuery<>("Comment");
                query.findInBackground().subscribe(new Observer<List<AVObject>>() {
                    public void onSubscribe(Disposable disposable) {
                    }

                    public void onNext(List<AVObject> comments) {
                        Log.d(TAG, "a= " + JSONObject.toJSONString(comments));
                        setData(JSONObject.toJSONString(comments));
                    }

                    public void onError(Throwable throwable) {
                    }

                    public void onComplete() {
                    }
                });
            }
        }).start();

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
                //                Intent it = new Intent(context, CommentEditActivity.class);
                //                CommentItemBean bean = commentList.get(i);
                //                it.putExtra("time", bean.getTime());
                //                it.putExtra("title", bean.getTitle());
                //                it.putExtra("content", bean.getContent());
                //                startActivity(it);
            }
        });


    }

    /**
     * 处理数据
     *
     * @param comments
     */
    private void setData(String comments) {
        JSONArray commentsArr = JSONObject.parseArray(comments);
        for (int i = 0; i < commentsArr.size(); i++) {
            JSONObject a = commentsArr.getJSONObject(i);
            JSONObject serverData = a.getJSONObject("serverData");

            String comment = serverData.getString("comment");
            String createdAt = serverData.getString("createdAt");
            String updatedAt = serverData.getString("updatedAt");
            String QQAvatar = serverData.getString("QQAvatar");
            String insertedAt = serverData.getString("insertedAt");
            String ip = serverData.getString("ip");
            String link = serverData.getString("link");
            String mail = serverData.getString("mail");
            String nick = serverData.getString("nick");
            String ua = serverData.getString("ua");
            String url = serverData.getString("url");
            String objectId = serverData.getString("objectId");
            commentList.add(new CommentItemBean(comment, createdAt, updatedAt, QQAvatar, insertedAt, ip, link, mail, nick, ua, url, objectId));
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                listView_comment.setAdapter(new MyAdapter());
            }
        });

    }


    /**
     * 自定义适配器
     */
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return commentList.size();
        }

        @Override
        public Object getItem(int position) {
            return commentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            CommentItemBean bean = commentList.get(position);
            viewHolder.createdAt_1.setText(DateUtils.parseDate(bean.getCreatedAt()));

            viewHolder.comment.setText("comment：" + bean.getComment());
            viewHolder.createdAt.setText("createdAt：" + DateUtils.parseDate(bean.getCreatedAt()));
            viewHolder.nick.setText("nick：" + bean.getNick());
            viewHolder.objectId.setText("objectId：" + bean.getObjectId());
            viewHolder.updatedAt.setText("updatedAt：" + DateUtils.parseDate(bean.getUpdatedAt()));

            viewHolder.QQAvatar.setText("QQAvatar：" + bean.getQQAvatar());
            viewHolder.insertedAt.setText("insertedAt：" + bean.getInsertedAt());
            viewHolder.ip.setText("ip：" + bean.getIp());
            viewHolder.link.setText("link：" + bean.getLink());
            viewHolder.mail.setText("mail：" + bean.getMail());
            viewHolder.ua.setText("ua：" + bean.getUa());
            viewHolder.url.setText("url：" + bean.getUrl());
            return convertView;
        }

        public class ViewHolder {
            private TextView createdAt_1, comment, createdAt, updatedAt, QQAvatar, insertedAt, ip, link, mail, nick, ua, url, objectId;

            public ViewHolder(View rootView) {
                this.createdAt_1 = rootView.findViewById(R.id.createdAt_1);
                this.comment = rootView.findViewById(R.id.comment);
                this.createdAt = rootView.findViewById(R.id.createdAt);
                this.nick = rootView.findViewById(R.id.nick);
                this.objectId = rootView.findViewById(R.id.objectId);
                this.updatedAt = rootView.findViewById(R.id.updatedAt);

                this.QQAvatar = rootView.findViewById(R.id.QQAvatar);
                this.insertedAt = rootView.findViewById(R.id.insertedAt);
                this.ip = rootView.findViewById(R.id.ip);
                this.link = rootView.findViewById(R.id.link);
                this.mail = rootView.findViewById(R.id.mail);
                this.ua = rootView.findViewById(R.id.ua);
                this.url = rootView.findViewById(R.id.url);
            }
        }
    }

}
