package com.zyq.parttime.form;

import java.io.Serializable;

public class CreateResume implements Serializable {
    private String telephone;
    private String upload_time;

    @Override
    public String toString() {
        return "CreateResume{" +
                "telephone='" + telephone + '\'' +
                ", upload_time='" + upload_time + '\'' +
                '}';
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }
}
