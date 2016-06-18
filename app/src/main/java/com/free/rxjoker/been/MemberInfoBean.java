package com.free.rxjoker.been;

import java.io.Serializable;

/**
 * Created by tanzhenxing
 * Date: 2016/6/11.
 * Description:参与成员信息
 */
public class MemberInfoBean implements Serializable {
    private String icon;
    private String name;
    private String companyName;
    private int age;
    private String jobName;
    //男：true，女：fasle
    private boolean sex;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
