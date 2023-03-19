package com.zyq.parttime.form;

import java.io.Serializable;
import java.util.Date;

public class Position implements Serializable {
    private int p_id;
    private int op_id;
    private String position_name;
    private String category;
    private String salary;
    private String area;
    private String exp;
    private String content;
    private String requirement;
    private Date signup_ddl;
    private String slogan;
    private String work_time;
    private String settlement;
    private String position_status;
    private Date create_time;
    private Date update_time;

    public Position() {
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public int getOp_id() {
        return op_id;
    }

    public void setOp_id(int op_id) {
        this.op_id = op_id;
    }

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public Date getSignup_ddl() {
        return signup_ddl;
    }

    public void setSignup_ddl(Date signup_ddl) {
        this.signup_ddl = signup_ddl;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    public String getPosition_status() {
        return position_status;
    }

    public void setPosition_status(String position_status) {
        this.position_status = position_status;
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

//    @Override
//    public String toString() {
//        return "{" +
//                "\"p_id\"=" + p_id +
//                ", \"op_id\"=" + op_id +
//                ", \"position_name\"=\"" + position_name + '\"' +
//                ", \"category\"=\"" + category + '\"' +
//                ", \"salary\"=\"" + salary + '\"' +
//                ", \"area\"=\"" + area + '\"' +
//                ", \"exp\"=\"" + exp + '\"' +
//                ", \"content\"=\"" + content + '\"' +
//                ", \"requirement\"=\"" + requirement + '\"' +
//                ", \"signup_ddl\"=" + signup_ddl +
//                ", \"slogan\"=\"" + slogan + '\"' +
//                ", \"work_time\"=\"" + work_time + '\"' +
//                ", \"settlement\"=\"" + settlement + '\"' +
//                ", \"position_status\"=\"" + position_status + '\"' +
//                ", \"create_time\"=" + create_time +
//                ", \"update_time\"=" + update_time +
//                '}';
//    }

    @Override
    public String toString() {
        return "Position{" +
                "p_id=" + p_id +
                ", op_id=" + op_id +
                ", position_name='" + position_name + '\'' +
                ", category='" + category + '\'' +
                ", salary='" + salary + '\'' +
                ", area='" + area + '\'' +
                ", exp='" + exp + '\'' +
                ", content='" + content + '\'' +
                ", requirement='" + requirement + '\'' +
                ", signup_ddl=" + signup_ddl +
                ", slogan='" + slogan + '\'' +
                ", work_time='" + work_time + '\'' +
                ", settlement='" + settlement + '\'' +
                ", position_status='" + position_status + '\'' +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}
