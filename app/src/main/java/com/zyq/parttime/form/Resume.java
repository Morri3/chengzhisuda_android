package com.zyq.parttime.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Resume implements Serializable {
    private String telephone;
    private String current_area;
    private String exp;
    private Date upload_time;
    private List<EditCampus> campusExpList;
    private List<EditEducation> educationBgList;
    private List<EditProject> projectExpList;
    private List<EditSkills> professionalSkillList;
    private String status;
    private String memo;
    private int r_id;

    @Override
    public String toString() {
        return "Resume{" +
                "telephone='" + telephone + '\'' +
                ", current_area='" + current_area + '\'' +
                ", exp='" + exp + '\'' +
                ", upload_time=" + upload_time +
                ", campusExpList=" + campusExpList +
                ", educationBgList=" + educationBgList +
                ", projectExpList=" + projectExpList +
                ", professionalSkillList=" + professionalSkillList +
                ", status='" + status + '\'' +
                ", memo='" + memo + '\'' +
                ", r_id=" + r_id +
                '}';
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCurrent_area() {
        return current_area;
    }

    public void setCurrent_area(String current_area) {
        this.current_area = current_area;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public Date getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(Date upload_time) {
        this.upload_time = upload_time;
    }

    public List<EditCampus> getCampusExpList() {
        return campusExpList;
    }

    public void setCampusExpList(List<EditCampus> campusExpList) {
        this.campusExpList = campusExpList;
    }

    public List<EditEducation> getEducationBgList() {
        return educationBgList;
    }

    public void setEducationBgList(List<EditEducation> educationBgList) {
        this.educationBgList = educationBgList;
    }

    public List<EditProject> getProjectExpList() {
        return projectExpList;
    }

    public void setProjectExpList(List<EditProject> projectExpList) {
        this.projectExpList = projectExpList;
    }

    public List<EditSkills> getProfessionalSkillList() {
        return professionalSkillList;
    }

    public void setProfessionalSkillList(List<EditSkills> professionalSkillList) {
        this.professionalSkillList = professionalSkillList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
