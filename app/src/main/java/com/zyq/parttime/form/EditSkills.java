package com.zyq.parttime.form;

import java.io.Serializable;

public class EditSkills implements Serializable {
    private String telephone;
    private int rd_id;
    private String content;

    private String memo;
    private String rd_status;

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
        return "EditSkills{" +
                "telephone='" + telephone + '\'' +
                ", rd_id=" + rd_id +
                ", content='" + content + '\'' +
                ", memo='" + memo + '\'' +
                ", rd_status='" + rd_status + '\'' +
                '}';
    }
}
