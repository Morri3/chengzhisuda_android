package com.zyq.parttime.form;

import java.io.Serializable;
import java.util.Date;

public class Signup implements Serializable {
    private int s_id;
    private String stu_id;
    private int p_id;
    private String signup_status;
    private Date create_time;
    private Date update_time;

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getSignup_status() {
        return signup_status;
    }

    public void setSignup_status(String signup_status) {
        this.signup_status = signup_status;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "Signup{" +
                "s_id=" + s_id +
                ", stu_id='" + stu_id + '\'' +
                ", p_id=" + p_id +
                ", signup_status='" + signup_status + '\'' +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}