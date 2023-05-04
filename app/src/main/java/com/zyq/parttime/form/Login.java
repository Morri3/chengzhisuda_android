package com.zyq.parttime.form;

import java.io.Serializable;


public class Login implements Serializable {
    private String telephone;//账号
    private String pwd;//密码

    @Override
    public String toString() {
        return "Login{" +
                "telephone='" + telephone + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}