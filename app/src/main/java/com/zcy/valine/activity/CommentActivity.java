package com.zcy.valine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zcy.valine.R;
import com.zcy.valine.base.BaseActivity;
import com.zcy.valine.bean.CommentListBean;
import com.zcy.valine.config.MyConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhl
 * on 2020/7/6
 * 评论列表
 */
public class CommentActivity extends BaseActivity {

    private Context context;
    private TextView btn_back;
    private ListView listView_comment;

    //模拟数据
    private List<CommentListBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        context = this;
        btn_back = findViewById(R.id.btn_back);
        listView_comment = findViewById(R.id.listView_comment);

        //模拟数据
        list.add(new CommentListBean("2019-10-01", "我的第一个评论", "<category android:name=\"android.intent.category.LAUNCHER\" />"));
        list.add(new CommentListBean("2020-07-06", "我的第二个评论", "<action android:name=\"android.intent.action.MAIN\" />"));

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
                CommentListBean bean = list.get(i);
                it.putExtra("time", bean.getTime());
                it.putExtra("title", bean.getTitle());
                it.putExtra("content", bean.getContent());
                startActivity(it);
            }
        });

        listView_comment.setAdapter(new MyAdapter());


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
                convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            CommentListBean bean = list.get(position);
            viewHolder.time.setText(bean.getTime());
            viewHolder.title.setText(bean.getTitle());
            viewHolder.content.setText(bean.getContent());

            return convertView;
        }

        public class ViewHolder {
            private TextView time, title, content;

            public ViewHolder(View rootView) {
                this.time = rootView.findViewById(R.id.time);
                this.title = rootView.findViewById(R.id.title);
                this.content = rootView.findViewById(R.id.content);
            }
        }
    }

}
