package com.zyq.parttime.form;

import java.io.Serializable;

public class AddDetail implements Serializable {
    private String telephone;
    private int r_id;//简历id
    private String date;//start_time、end_time传进来是用-连接的字符串，后端处理转Date
    private String title;
    private String content;
    private String category;

    @Override
    public String toString() {
        return "AddDetail{" +
                "telephone='" + telephone + '\'' +
                ", r_id=" + r_id +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
}
