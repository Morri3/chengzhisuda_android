package com.zyq.parttime.form;

import java.io.Serializable;

public class EditProjectDto implements Serializable {
    private String telephone;
    private int rd_id;
    private String title;
    private String old_title;//更新前的标题
    private String content;
    private String start_time;
    private String end_time;

    @Override
    public String toString() {
        return "EditProjectDto{" +
                "telephone='" + telephone + '\'' +
                ", rd_id=" + rd_id +
                ", title='" + title + '\'' +
                ", old_title='" + old_title + '\'' +
                ", content='" + content + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                '}';
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOld_title() {
        return old_title;
    }

    public void setOld_title(String old_title) {
        this.old_title = old_title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}