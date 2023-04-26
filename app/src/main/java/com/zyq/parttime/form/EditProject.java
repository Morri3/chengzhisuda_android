package com.zyq.parttime.form;

import java.io.Serializable;

public class EditProject implements Serializable {
    private String telephone;
    private int rd_id;
    private String title;
    private String date;
    private String content;
    private String memo;
    private String rd_status;
    private int r_id;
    private int initial;//判断是原来就有的resumedetail，还是通过增加一条新的得到的

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public int getInitial() {
        return initial;
    }

    public void setInitial(int initial) {
        this.initial = initial;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRd_status() {
        return rd_status;
    }

    public void setRd_status(String rd_status) {
        this.rd_status = rd_status;
    }

    @Override
    public String toString() {
        return "EditProject{" +
                "telephone='" + telephone + '\'' +
                ", rd_id=" + rd_id +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                ", memo='" + memo + '\'' +
                ", rd_status='" + rd_status + '\'' +
                ", r_id=" + r_id +
                ", initial=" + initial +
                '}';
    }
}
