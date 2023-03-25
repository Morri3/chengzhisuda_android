package com.zyq.parttime.form;

import java.io.Serializable;

public class Student implements Serializable {
    private String stu_name;
    private int gender;
    private String sno;
    private String telephone;
    private String grade;
    private int age;
    private String emails;
    private String school_name;

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

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stu_name='" + stu_name + '\'' +
                ", gender=" + gender +
                ", sno='" + sno + '\'' +
                ", telephone='" + telephone + '\'' +
                ", grade='" + grade + '\'' +
                ", age=" + age +
                ", emails='" + emails + '\'' +
                ", school_name='" + school_name + '\'' +
                '}';
    }
}
