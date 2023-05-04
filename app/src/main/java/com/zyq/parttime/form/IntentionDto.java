package com.zyq.parttime.form;

import java.io.Serializable;

public class IntentionDto implements Serializable {
    private int i_id;
    private String stu_id;
    private String content;//意向兼职的名称
    private String memo;

    @Override
    public String toString() {
        return "IntentionDto{" +
                "i_id=" + i_id +
                ", stu_id='" + stu_id + '\'' +
                ", content='" + content + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }

    public int getI_id() {
        return i_id;
    }

    public void setI_id(int i_id) {
        this.i_id = i_id;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
