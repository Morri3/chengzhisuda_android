package com.zyq.parttime.sp;

import java.io.Serializable;

public class EmpInfo implements Serializable {
    private String emp_name;
    private int gender ;
    private String emails;
    private int age ;
    private String telephone;
    private String jno ;
    private String unit_name ;
    private String unit_descriptions ;
    private String unit_loc ;
    private int job_nums;

    @Override
    public String toString() {
        return "EmpInfo{" +
                "emp_name='" + emp_name + '\'' +
                ", gender=" + gender +
                ", emails='" + emails + '\'' +
                ", age=" + age +
                ", telephone='" + telephone + '\'' +
                ", jno='" + jno + '\'' +
                ", unit_name='" + unit_name + '\'' +
                ", unit_descriptions='" + unit_descriptions + '\'' +
                ", unit_loc='" + unit_loc + '\'' +
                ", job_nums=" + job_nums +
                '}';
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getJno() {
        return jno;
    }

    public void setJno(String jno) {
        this.jno = jno;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getUnit_descriptions() {
        return unit_descriptions;
    }

    public void setUnit_descriptions(String unit_descriptions) {
        this.unit_descriptions = unit_descriptions;
    }

    public String getUnit_loc() {
        return unit_loc;
    }

    public void setUnit_loc(String unit_loc) {
        this.unit_loc = unit_loc;
    }

    public int getJob_nums() {
        return job_nums;
    }

    public void setJob_nums(int job_nums) {
        this.job_nums = job_nums;
    }
}
