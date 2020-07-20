package com.zcy.valine.bean;

import java.io.Serializable;

/**
 * Created by yhl
 * on 2020/7/6
 */
public class CommentItemBean implements Serializable {

    private String comment;
    private String createdAt;
    private String updatedAt;

    private String QQAvatar;
    private String insertedAt;
    private String ip;

    private String link;
    private String mail;
    private String nick;

    private String ua;
    private String url;
    private String objectId;

    public CommentItemBean(String comment, String createdAt, String updatedAt, String QQAvatar, String insertedAt, String ip, String link, String mail, String nick, String ua, String url, String objectId) {
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.QQAvatar = QQAvatar;
        this.insertedAt = insertedAt;
        this.ip = ip;
        this.link = link;
        this.mail = mail;
        this.nick = nick;
        this.ua = ua;
        this.url = url;
        this.objectId = objectId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getQQAvatar() {
        return QQAvatar;
    }

    public void setQQAvatar(String QQAvatar) {
        this.QQAvatar = QQAvatar;
    }

    public String getInsertedAt() {
        return insertedAt;
    }

    public void setInsertedAt(String insertedAt) {
        this.insertedAt = insertedAt;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
