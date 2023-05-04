package com.zyq.parttime.form;

import java.io.Serializable;

public class SignupWithUser implements Serializable {
    private String stu_id;
    private int canSignup;//值为1-6，6为可以报名

    @Override
    public String toString() {
        return "SignupWithUser{" +
                "stu_id='" + stu_id + '\'' +
                ", canSignup=" + canSignup +
                '}';
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public int getCanSignup() {
        return canSignup;
    }

    public void setCanSignup(int canSignup) {
        this.canSignup = canSignup;
    }

    //    private String stu_id;//账号
//    private int p_id;//兼职的id
//    private String p_name;//兼职名称
//    private boolean canSignup;//是否能报名
//
//    @Override
//    public String toString() {
//        return "SignupWithUser{" +
//                "stu_id='" + stu_id + '\'' +
//                ", p_id=" + p_id +
//                ", p_name='" + p_name + '\'' +
//                ", canSignup=" + canSignup +
//                '}';
//    }
//
//    public String getStu_id() {
//        return stu_id;
//    }
//
//    public void setStu_id(String stu_id) {
//        this.stu_id = stu_id;
//    }
//
//    public int getP_id() {
//        return p_id;
//    }
//
//    public void setP_id(int p_id) {
//        this.p_id = p_id;
//    }
//
//    public String getP_name() {
//        return p_name;
//    }
//
//    public void setP_name(String p_name) {
//        this.p_name = p_name;
//    }
//
//    public boolean isCanSignup() {
//        return canSignup;
//    }
//
//    public void setCanSignup(boolean canSignup) {
//        this.canSignup = canSignup;
//    }
}
