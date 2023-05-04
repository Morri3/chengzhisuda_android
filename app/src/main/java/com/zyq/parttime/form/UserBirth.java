package com.zyq.parttime.form;

import java.io.Serializable;

public class UserBirth implements Serializable {
    private String telephone;//账号
    private String birth_year;//年份
    private String birth_month;//月份

    @Override
    public String toString() {
        return "UserBirth{" +
                "telephone='" + telephone + '\'' +
                ", birth_year='" + birth_year + '\'' +
                ", birth_month='" + birth_month + '\'' +
                '}';
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(String birth_year) {
        this.birth_year = birth_year;
    }

    public String getBirth_month() {
        return birth_month;
    }

    public void setBirth_month(String birth_month) {
        this.birth_month = birth_month;
    }
}
