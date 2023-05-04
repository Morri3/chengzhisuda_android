package com.zyq.parttime.form;

import java.io.Serializable;
import java.util.Date;

public class StudentInfo implements Serializable {
    private int age;
    private String birth_year;
    private String birth_month;
    private String emails;
    private int gender;
    private String grade;
    private String school_name;
    private String sno;
    private String stu_name;
    private String telephone;
    private Date entrance_date;
    private Date graduation_date;

    @Override
    public String toString() {
        return "StudentInfo{" +
                "age=" + age +
                ", birth_year='" + birth_year + '\'' +
                ", birth_month='" + birth_month + '\'' +
                ", emails='" + emails + '\'' +
                ", gender=" + gender +
                ", grade='" + grade + '\'' +
                ", school_name='" + school_name + '\'' +
                ", sno='" + sno + '\'' +
                ", stu_name='" + stu_name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", entrance_date=" + entrance_date +
                ", graduation_date=" + graduation_date +
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

    public Date getEntrance_date() {
        return entrance_date;
    }

    public void setEntrance_date(Date entrance_date) {
        this.entrance_date = entrance_date;
    }

    public Date getGraduation_date() {
        return graduation_date;
    }

    public void setGraduation_date(Date graduation_date) {
        this.graduation_date = graduation_date;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
