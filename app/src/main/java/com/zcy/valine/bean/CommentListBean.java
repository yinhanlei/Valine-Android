package com.zcy.valine.bean;

/**
 * Created by yhl
 * on 2020/7/6
 */
public class CommentListBean {

    private String time;
    private String title;
    private String content;

    public CommentListBean(String time, String title, String content) {
        this.time = time;
        this.title = title;
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
