package com.zyq.parttime.sp;

import java.io.Serializable;

public class EditInfo implements Serializable {
    private String telephone;
    private int gender;
    private int age;
    private String emails;
    private String entrance_date;
    private String graduation_date;

    @Override
    public String toString() {
        return "EditInfo{" +
                "telephone='" + telephone + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", emails='" + emails + '\'' +
                ", entrance_date='" + entrance_date + '\'' +
                ", graduation_date='" + graduation_date + '\'' +
                '}';
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getEntrance_date() {
        return entrance_date;
    }

    public void setEntrance_date(String entrance_date) {
        this.entrance_date = entrance_date;
    }

    public String getGraduation_date() {
        return graduation_date;
    }

    public void setGraduation_date(String graduation_date) {
        this.graduation_date = graduation_date;
    }
}
