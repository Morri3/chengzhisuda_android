package com.zyq.parttime.form;

import java.io.Serializable;

public class ResumeDetail implements Serializable {
    private String telephone;
    private int rd_id;
    private int r_id;
    private String time;//起止时间
    private String title;
    private String content;
    private String category;
    private int hasContent;//0表示该DTO有内容，1表示该DTO无内容
    private String status;
    private String memo;//若不存在简历，该字段为”请填写简历“

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getRd_id() {
        return rd_id;
    }

    public void setRd_id(int rd_id) {
        this.rd_id = rd_id;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getHasContent() {
        return hasContent;
    }

    public void setHasContent(int hasContent) {
        this.hasContent = hasContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "ResumeDetail{" +
                "telephone='" + telephone + '\'' +
                ", rd_id=" + rd_id +
                ", r_id=" + r_id +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", hasContent=" + hasContent +
                ", status='" + status + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
