package com.zcy.valine.bean;

import java.io.Serializable;

/**
 * Created by yinhanlei on 2020/7/5.
 * 应用列表的实体
 */

public class ApplicationBean implements Serializable {

    private String applicationName;
    private String applicationId;
    private String applicationKey;

    public ApplicationBean(String applicationName, String applicationId, String applicationKey) {
        this.applicationName = applicationName;
        this.applicationId = applicationId;
        this.applicationKey = applicationKey;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationKey() {
        return applicationKey;
    }

    public void setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
    }
}
