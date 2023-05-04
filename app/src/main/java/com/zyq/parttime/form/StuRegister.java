package com.zyq.parttime.form;

import java.io.Serializable;

public class StuRegister implements Serializable {
    private String stu_name;
    private int gender;
    private String telephone;
    private String emails;
    private String pwd;
    private String pwd2;
    private int age;
    private String birth_year;//出生年份
    private String birth_month;//出生月份
    private String school_name;
    private String sno;
    private String entrance_date;
    private String graduation_date;
    private String reg_date;

    @Override
    public String toString() {
        return "StuRegister{" +
                "stu_name='" + stu_name + '\'' +
                ", gender=" + gender +
                ", telephone='" + telephone + '\'' +
                ", emails='" + emails + '\'' +
                ", pwd='" + pwd + '\'' +
                ", pwd2='" + pwd2 + '\'' +
                ", age=" + age +
                ", birth_year='" + birth_year + '\'' +
                ", birth_month='" + birth_month + '\'' +
                ", school_name='" + school_name + '\'' +
                ", sno='" + sno + '\'' +
                ", entrance_date='" + entrance_date + '\'' +
                ", graduation_date='" + graduation_date + '\'' +
                ", reg_date='" + reg_date + '\'' +
                '}';
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

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd2() {
        return pwd2;
    }

    public void setPwd2(String pwd2) {
        this.pwd2 = pwd2;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
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

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }
}
