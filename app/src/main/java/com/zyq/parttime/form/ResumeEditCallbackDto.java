package com.zyq.parttime.form;

import java.io.Serializable;

public class ResumeEditCallbackDto implements Serializable {
    private String telephone;
    private Resume info;
    private String memo;

    @Override
    public String toString() {
        return "ResumeEditCallbackDto{" +
                "telephone='" + telephone + '\'' +
                ", info=" + info +
                ", memo='" + memo + '\'' +
                '}';
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Resume getInfo() {
        return info;
    }

    public void setInfo(Resume info) {
        this.info = info;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
