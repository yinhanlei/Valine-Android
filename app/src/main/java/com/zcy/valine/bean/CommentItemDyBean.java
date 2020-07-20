package com.zcy.valine.bean;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by yhl
 * on 2020/7/20
 * 动态item的实体
 */
public class CommentItemDyBean implements Serializable {

    private String createdAt;
    private String  objectId;
    private JSONObject serverData;

    public CommentItemDyBean(String createdAt, String objectId, JSONObject serverData) {
        this.createdAt = createdAt;
        this.objectId = objectId;
        this.serverData = serverData;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public JSONObject getServerData() {
        return serverData;
    }

    public void setServerData(JSONObject serverData) {
        this.serverData = serverData;
    }
}
